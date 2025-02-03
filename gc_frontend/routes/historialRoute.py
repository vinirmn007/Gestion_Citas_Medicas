from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

hsto_route = Blueprint('hsto_route', __name__)

URL = "http://localhost:8070/myapp/"

#HISTORIAL MEDICO

@hsto_route.route('/historial/<id>')
def historial_medico(id):
    try:
        r = requests.get(URL + 'historialMedico/get/'+ id)
        data = r.json().get('data')

        pacienteId = data.get('pacienteId')
        r2 = requests.get(URL + 'pacientes/get/'+ str(pacienteId))
        data2 = r2.json().get('data')

        rAge = requests.get(URL + 'pacientes/age/'+ str(pacienteId))
        edad = rAge.json().get('data')

        historialId = data.get('id')
        r3 = requests.get(URL + 'citasMedicas/searchByHistId/'+ str(historialId))
        data3 = r3.json().get('data')

        if r.status_code == 200 and r2.status_code == 200 and r3.status_code == 200:
            return render_template('parts/historial/historial.html', historial=data, paciente=data2, citas=data3, edad=edad)
        elif r.status_code != 200:
            flash('No se ha podido cargar el historial: ' + str(data), category='error')
            return redirect(request.referrer)
        elif r2.status_code != 200:
            flash('No se ha podido cargar el paciente: '+str(data2), category='error')
            return redirect(request.referrer)
        elif r3.status_code != 200:
            flash('No se ha podido cargar las citas: '+str(data3), category='error')
            return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@hsto_route.route('/historial/registro')
def historial_registro():
    s = requests.get(URL + 'historialMedico/bloodType')
    bloodTypes = s.json().get('data')
    return render_template('parts/historial/registro.html', tipos_sangre=bloodTypes)

@hsto_route.route('/historial/save', methods=['POST'])
def historial_save():
    try:
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataForm = {"tipo_sangre": form["tipo_sangre"],
                    "hijos": int(form["hijos"]),
                    "discapacidad": form["discapacidad"],
                    "alergias": form["alergias"],
                    "antecedentes": form["antecendentes"],
                    "medicacion": form["medicacion"],
                    "patologias": form["patologias"],
                    "pacienteId": int(form["paciente_id"])}
        print(dataForm)
        r = requests.post(URL + 'historialMedico/save', data=json.dumps(dataForm), headers=headers)
        data = r.json().get('data')

        if r.status_code == 200:
            flash('Se ha guardado correctamente', category='info')
            return redirect('/home')
        else:
            flash('No se ha podido guardar, error: ' + str(data), category='error')
            return redirect('/historial/registro')   
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect('/historial/registro')