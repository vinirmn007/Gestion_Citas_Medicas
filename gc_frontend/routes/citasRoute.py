from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests
from . import mainRoute

citas_route = Blueprint('citas_route', __name__)

URL = "http://localhost:8070/myapp/"

sesion = mainRoute.get_session()

@citas_route.context_processor
def inject_session():
    return dict(sesion_templates=sesion)

#CITAS MEDICAS

@citas_route.route('/cita/<id>')
def citas_medicas(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesion para acceder a esta página', category='error')
        return redirect('/login')
    try:
        r = requests.get(URL + 'citasMedicas/binarySearch/turnoId/' + id)
        if r.status_code == 200:
            print("Cita encontrada")
            data = r.json().get('data')

            r4 = requests.get(URL + 'turno/get/' + str(data.get('turnoId')))
            turno = r4.json().get('data')
            print("Turno encontrado")

            print(data)
            print("URL: " + URL + 'persona/get/' + str(turno.get('idPaciente')))
            r2 = requests.get(URL + 'persona/get/' + str(turno.get('idPaciente')))
            if r2.status_code != 200:
                data2 = r2.json().get('data')
                flash('No se ha podido cargar el paciente: '+str(data2), category='error')
                return redirect(request.referrer)
            data2 = r2.json().get('data')
            print("Paciente encontrado")

            r3 = requests.get(URL + 'persona/age/' + str(turno.get('idPaciente')))
            edad = r3.json().get('data')
            print("Edad encontrada")

            r5 = requests.get(URL + 'medicos/get/' + str(turno.get('idMedico')))
            medico = r5.json().get('data')
            print("Medico encontrado")

            r6 = requests.get(URL + 'signosVitales/get/' + str(turno.get('idSignosVitales')))
            signos = r6.json().get('data')
            print("Signos encontrados")

            return render_template('parts/citas/citaDetalle.html', cita=data, paciente=data2, edad=edad, turno=turno, medico=medico, signos=signos)
        else:
            flash('No se ha encontrado la cita', category='error')
            return redirect(request.referrer)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@citas_route.route('/cita/all')
def citas_medicas_all():
    r = requests.get(URL + 'citasMedicas')
    data = r.json().get('data')
    return render_template('parts/citas/citas.html', citas=data)

@citas_route.route('/cita/registro/<id>')
def citas_medicas_registro(id):
    if 'rol' not in sesion or sesion.get('rol') != 2: 
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    try:
        print(id)
        r = requests.get(URL + 'turno/get/' + id)
        print(r)
        data = r.json().get('data')

        pacienteId = data.get('idPaciente')
        r2 = requests.get(URL + 'persona/get/' + str(pacienteId))
        print(r2)
        data2 = r2.json().get('data')

        r3 = requests.get(URL + 'persona/age/' + str(pacienteId))
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
    if 'rol' not in sesion or sesion.get('rol') != 2: 
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
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

