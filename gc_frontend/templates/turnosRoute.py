from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests
from datetime import datetime
from . import mainRoute

turnos_route = Blueprint('turnos_route', __name__)

URL = "http://localhost:8070/myapp/"

sesion = mainRoute.get_session()

@turnos_route.context_processor
def inject_session():
    return dict(sesion_templates=sesion)

@turnos_route.route('/turno/registrar')
def registrar_turno():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    if sesion['rol'] == 3:
        paciente = sesion['persona']
        r = requests.get(URL + 'medicos')
        data = r.json().get('data')
        return render_template('parts/turnos/registrar_pac.html', medicos=data, paciente=paciente)
    elif sesion['rol'] == 2 or sesion['rol'] == 1:
        r = requests.get(URL + 'medicos')
        data = r.json().get('data')
        return render_template('parts/turnos/registrar_med.html', medicos=data)
    return redirect('/login')

@turnos_route.route('/agenda')
def ver_agenda():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    return render_template('parts/turnos/agenda.html')

#PARA PACIENTE
@turnos_route.route('/turno/pac/save', methods=['POST'])
def guardar_turno_pac():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    try:
        headers = {'Content-Type': 'application/json'}
        form = request.form

        fecha_str = form["fecha"]
        fecha_obj = datetime.strptime(fecha_str, "%Y-%m-%d")
        fecha_format = fecha_obj.strftime("%d-%m-%Y")

        dataForm = {"fecha": fecha_format,
                    "hora": form["hora"],
                    "idPaciente": sesion['persona'].get('id'),
                    "idMedico": int(form["medicoId"])}
        r = requests.post(URL + 'turno/create', data=json.dumps(dataForm), headers=headers)
        data = r.json().get('data')
        print(dataForm)

        if r.status_code == 200:
            flash('Se ha guardado correctamente', category='info')
        else:
            flash('No se ha podido guardar: ' + str(data), category='error')
        return redirect('/turno/registrar')
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

#PARA MEDICO
@turnos_route.route('/turno/med/save', methods=['POST'])
def guardar_turno_med():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    if sesion['rol'] != 2:
        flash("No tienes permisos para acceder a esta página", "danger")
        return redirect('/home')
    try:
        headers = {'Content-Type': 'application/json'}
        form = request.form

        fecha_str = form["fecha"]
        fecha_obj = datetime.strptime(fecha_str, "%Y-%m-%d")
        fecha_format = fecha_obj.strftime("%d-%m-%Y")

        dataForm = {"fecha": fecha_format,
                    "hora": form["hora"],
                    "idPaciente": int(form["paciente_id"]),
                    "idMedico": int(form["medicoId"])}
        r = requests.post(URL + 'turno/create', data=json.dumps(dataForm), headers=headers)
        print(dataForm)

        data = r.json().get('data')

        if r.status_code == 200:
            flash('Se ha guardado correctamente', category='info')
        else:
            flash('No se ha podido guardar: ' + str(data), category='error')
        return redirect('/turno/registrar')
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@turnos_route.route('/turno/reservados/all')
def turnos_reservados():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    try:
        if sesion['rol'] == 3:
            r = requests.get(URL + 'turno/getByEstado/pac/' + sesion['persona'].get() +'/RESERVADO')
            if r.status_code == 200:
                data = r.json().get('data')

                for turno in data:
                    turno['nombrePaciente'] = sesion['persona'].get('nombres') + " " + sesion['persona'].get('apellidos')
                for turno in data:
                    medico_id = turno.get('idMedico')
                    if medico_id: 
                        try:
                            response = requests.get(f"{URL}medicos/get/{medico_id}")
                            if response.status_code == 200:
                                medico_data = response.json().get('data')
                                turno['nombreMedico'] = medico_data.get('nombres') + " " + medico_data.get('apellidos')
                            else:
                                turno['nombreMedico'] = "No encontrado"
                        except requests.RequestException:
                            turno['nombreMedico'] = "Error en la consulta"
                return render_template('parts/turnos/turnos_res.html', turnos=data)
            else:
                flash('No se encontraron turnos', category='error')
                return redirect(request.referrer)
        else:
            print("ENTREEEEEEEEEEEEEEEEEEEEEEEE")
            print(URL + 'turno/getByEstado/RESERVADO')
            r = requests.get(URL + 'turno/getByEstado/RESERVADO')

            if r.status_code == 200:
                data = r.json().get('data')
                for turno in data:
                    paciente_id = turno.get('idPaciente')
                    if paciente_id:
                        try:
                            response = requests.get(f"{URL}persona/get/{paciente_id}")
                            if response.status_code == 200:
                                paciente_data = response.json().get('data')
                                turno['nombrePaciente'] = paciente_data.get('nombres') + " " + paciente_data.get('apellidos')
                            else:
                                turno['nombrePaciente'] = "No encontrado"
                        except requests.RequestException:
                            turno['nombrePaciente'] = "Error en la consulta"
                for turno in data:
                    medico_id = turno.get('idMedico')
                    if medico_id: 
                        try:
                            response = requests.get(f"{URL}medicos/get/{medico_id}")
                            if response.status_code == 200:
                                medico_data = response.json().get('data')
                                turno['nombreMedico'] = medico_data.get('nombres') + " " + medico_data.get('apellidos')
                            else:
                                turno['nombreMedico'] = "No encontrado"
                        except requests.RequestException:
                            turno['nombreMedico'] = "Error en la consulta"

                return render_template('parts/turnos/turnos_res.html', turnos=data)
            else:
                flash('No se encontraron turnos', category='error')
                return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@turnos_route.route('/turno/espera/all')
def turnos_espera():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    try:
        r = requests.get(URL + 'turno/getByEstado/EN_ESPERA')
        data = r.json().get('data')

        if r.status_code == 200:
            for turno in data:
                paciente_id = turno.get('idPaciente')
                if paciente_id:
                    try:
                        response = requests.get(f"{URL}persona/get/{paciente_id}")
                        if response.status_code == 200:
                            paciente_data = response.json().get('data')
                            turno['nombrePaciente'] = paciente_data.get('nombres') + " " + paciente_data.get('apellidos')
                        else:
                            turno['nombrePaciente'] = "No encontrado"
                    except requests.RequestException:
                        turno['nombrePaciente'] = "Error en la consulta"
            for turno in data:
                medico_id = turno.get('idMedico')
                if medico_id: 
                    try:
                        response = requests.get(f"{URL}medicos/get/{medico_id}")
                        if response.status_code == 200:
                            medico_data = response.json().get('data')
                            turno['nombreMedico'] = medico_data.get('nombres') + " " + medico_data.get('apellidos')
                        else:
                            turno['nombreMedico'] = "No encontrado"
                    except requests.RequestException:
                        turno['nombreMedico'] = "Error en la consulta"

            return render_template('parts/turnos/turnos_esp.html', turnos=data)
        else:
            flash('No se encontraron turnos', category='error')
            return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@turnos_route.route('/turno/finalizados/all')
def turnos_finalizados():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    try:
        r = requests.get(URL + 'turno/getByEstado/FINALIZADO')
        data = r.json().get('data')

        if r.status_code == 200:
            for turno in data:
                paciente_id = turno.get('idPaciente')
                if paciente_id:
                    try:
                        response = requests.get(f"{URL}persona/get/{paciente_id}")
                        if response.status_code == 200:
                            paciente_data = response.json().get('data')
                            turno['nombrePaciente'] = paciente_data.get('nombres') + " " + paciente_data.get('apellidos')
                        else:
                            turno['nombrePaciente'] = "No encontrado"
                    except requests.RequestException:
                        turno['nombrePaciente'] = "Error en la consulta"
            for turno in data:
                medico_id = turno.get('idMedico')
                if medico_id: 
                    try:
                        response = requests.get(f"{URL}medicos/get/{medico_id}")
                        if response.status_code == 200:
                            medico_data = response.json().get('data')
                            turno['nombreMedico'] = medico_data.get('nombres') + " " + medico_data.get('apellidos')
                        else:
                            turno['nombreMedico'] = "No encontrado"
                    except requests.RequestException:
                        turno['nombreMedico'] = "Error en la consulta"
            return render_template('parts/turnos/turnos_fin.html', turnos=data)
        else:
            flash('No se encontraron turnos', category='error')
            return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@turnos_route.route('/turno/edit/<id>')
def edit_turno(id):
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    try:
        print("IDDDD: "+id)
        r = requests.get(URL + 'turno/get/'+ id)
        print(URL + 'turno/get/'+ id)
        print(r)
        data = r.json().get('data')
        rMedicos = requests.get(URL + 'medicos')
        medicos = rMedicos.json().get('data')
        if r.status_code == 200:
            return render_template('parts/turnos/editar.html', turno=data, medicos=medicos)
        else:
            flash("Error al obtener turno:", category='error')
            return redirect('/turno/reservados/all')
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect('/turno/reservados/all')
    
@turnos_route.route('/turno/update', methods=["POST"])
def update_turno():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    try:
        headers = {'Content-Type': 'application/json'}
        form = request.form

        fecha_str = form["fecha"]
        fecha_obj = datetime.strptime(fecha_str, "%Y-%m-%d")
        fecha_format = fecha_obj.strftime("%d-%m-%Y")

        dataForm = {"id": int(form["id"]),
                    "fecha": fecha_format,
                    "hora": form["hora"],
                    "idMedico": int(form["medicoId"])}
        r = requests.post(URL + 'turno/update', data=json.dumps(dataForm), headers=headers)

        print(dataForm)

        if r.status_code == 200:
            flash('Se ha actualizado correctamente', category='info')
        else:
            data = r.json().get('data')
            flash('No se ha podido actualizar: ' + str(data), category='error')
        return redirect('/turno/reservados/all')
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@turnos_route.route('/turno/cancel', methods=["POST"])
def cancel():
    if 'rol' not in sesion:
        flash("Debes iniciar sesión", "danger")
        return redirect('/login')
    try:
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataForm = {"id": int(form["id"])}
        r = requests.post(URL + 'turno/cancel', data=json.dumps(dataForm), headers=headers)

        if r.status_code == 200:
            flash('Turno cancelado exitosamente', category='info')
        else:
            data = r.json().get('data')
            flash('No se pudo cancelar: '+str(data), category='error')
        return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)
