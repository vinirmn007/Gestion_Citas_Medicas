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
    return render_template('parts/citas/citaDetalle.html', id=id)

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

