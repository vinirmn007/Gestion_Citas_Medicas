from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

citas_route = Blueprint('citas_route', __name__)

URL = "http://localhost:8080/myapp/"

#CITAS MEDICAS

@citas_route.route('/cita/<id>')
def citas_medicas(id):
    return render_template('parts/citas/citaDetalle.html')

@citas_route.route('/cita/all')
def citas_medicas_all():
    r = requests.get(URL + 'citasMedicas')
    data = r.json().get('data')
    return render_template('parts/citas/citas.html', citas=data)

@citas_route.route('/cita/registro/<id>')
def citas_medicas_registro(id):
    try:
        print(id)
        r = requests.get(URL + 'turno/get/' + id)
        print(r)
        data = r.json().get('data')

        pacienteId = data.get('idPaciente')
        r2 = requests.get(URL + 'pacientes/get/' + str(pacienteId))
        print(r2)
        data2 = r2.json().get('data')

        r3 = requests.get(URL + 'pacientes/age/' + str(pacienteId))
        print(r3)
        edad = r3.json().get('data')

        if r.status_code == 200 and r2.status_code == 200 and r3.status_code == 200:
            return render_template('parts/citas/registro.html', turnoId=id, paciente=data2, edad=edad)
        elif r.status_code != 200:
            flash('No se ha podido cargar el turno: '+str(data), category='error')
            return redirect(request.referrer)
        elif r2.status_code != 200:
            flash('No se ha podido cargar el paciente: '+str(data2), category='error')
            return redirect(request.referrer)
        elif r3.status_code != 200:
            flash('No se ha podido cargar la edad: '+str(edad), category='error')
            return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@citas_route.route('/cita/save', methods=['POST'])
def citas_medicas_save():
    try:
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataForm = {"motivo": form["motivo"],
                    "observaciones": form["observaciones"],
                    "motivo": form["motivo"],
                    "turnoId": int(form["turnoId"])}
        r = requests.post(URL + 'citasMedicas/save', data=json.dumps(dataForm), headers=headers)
        data = r.json().get('data')

        if r.status_code == 200:
            flash('Se ha guardado correctamente', category='info')
            return redirect('/registrarDiagnostico/' + str(data.get('id')))
        else:
            flash('No se ha podido guardar: ' + str(data), category='error')
            return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)        

#PARA VER LOS RECURSOS DEL TEMPLATE
@citas_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@citas_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')