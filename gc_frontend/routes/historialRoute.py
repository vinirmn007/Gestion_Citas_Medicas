from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests
from . import mainRoute

hsto_route = Blueprint('hsto_route', __name__)

URL = "http://localhost:8070/myapp/"

sesion = mainRoute.get_session()

#HISTORIAL MEDICO

@hsto_route.route('/historial/<id>')
def historial_medico(id):
    if 'usuario' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:
        r = requests.get(URL + 'historialMedico/get/'+ id)
        if r.status_code != 200:
            data = r.json().get('data')
            flash('No se ha podido cargar el historial: ' + str(data), category='error')
            return redirect(request.referrer)
        data = r.json().get('data')

        print("PASEEEEEEEEEEE LA BUSQUEDA DE HISTORIAL")

        pacienteId = data.get('pacienteId')
        r2 = requests.get(URL + 'persona/get/'+ str(pacienteId))
        if r2.status_code != 200:
            data2 = r2.json().get('data')
            flash('No se ha podido cargar el paciente: '+str(data2), category='error')
            return redirect(request.referrer)
        data2 = r2.json().get('data')
        print("PASEEEEEEEEEEE LA BUSQUEDA DE PACIENTE")

        rAge = requests.get(URL + 'persona/age/'+ str(pacienteId))
        edad = rAge.json().get('data')

        historialId = data.get('id')
        r3 = requests.get(URL + 'citasMedicas/searchByHistId/'+ str(historialId))
        if r3.status_code != 200:
            data3 = r3.json().get('data')
            flash('No se ha podido cargar las citas: '+str(data3), category='error')
            return redirect(request.referrer)
        data3 = r3.json().get('data')

        if r.status_code == 200 and r2.status_code == 200 and r3.status_code == 200:
            return render_template('parts/historial/historial.html', historial=data, paciente=data2, citas=data3, edad=edad)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@hsto_route.route('/historial/registro')
def historial_registro():
    print("SESIOOOOOOOOOOOON: ", sesion)
    if 'usuario' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion['rol'].get('nombre') != 'Medico' and sesion['rol'].get('nombre') != 'Administrador':
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    s = requests.get(URL + 'historialMedico/bloodType')
    bloodTypes = s.json().get('data')
    return render_template('parts/historial/registro.html', tipos_sangre=bloodTypes)

@hsto_route.route('/historial/save', methods=['POST'])
def historial_save():
    if 'usuario' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion.get('rol').get('nombre') != 'Medico' or sesion.get('rol').get('nombre') != 'Administrador':
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
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