from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

citas_route = Blueprint('citas_route', __name__)

URL = "http://localhost:8080/myapp/"

#CITAS MEDICAS

@citas_route.route('/cita/<id>')
def citas_medicas(id):
    return render_template('parts/citas/citaDetalle.html', id=id)

@citas_route.route('/cita/all')
def citas_medicas_all():
    r = requests.get(URL + 'citasMedicas')
    data = r.json().get('data')
    return render_template('parts/citas/citas.html', citas=data)

@citas_route.route('/cita/registro')
def citas_medicas_registro():
    return render_template('parts/citas/registrar_citas.html')

@citas_route.route('/cita/save', methods=['POST'])
def citas_medicas_save():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"motivo": form["motivo"],
                "observaciones": form["observaciones"],
                "motivo": form["motivo"],
                "turnoId": int(form["turnoId"])}
    r = requests.post(URL + 'citasMedicas/save', data=json.dumps(dataForm), headers=headers)
    data = r.json()

    if r.status_code == 200:
        flash('Se ha guardado correctamente', category='info')
    else:
        flash('No se ha podido guardar', category='error')
    return redirect('/cita/registro')    

#HISTORIAL MEDICO

@citas_route.route('/historial/<id>')
def historial_medico(id):
    r = requests.get(URL + 'historialMedico/get/'+ id)
    data = r.json().get('data')
    return render_template('parts/citas/historial.html', historial=data)

@citas_route.route('/historial/registro')
def historial_registro():
    s = requests.get(URL + 'historialMedico/bloodType')
    bloodTypes = s.json().get('data')
    return render_template('parts/citas/registrar_historial.html', tipos_sangre=bloodTypes)

@citas_route.route('/historial/save', methods=['POST'])
def historial_save():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"motivo": form["motivo"],
                "observaciones": form["observaciones"],
                "motivo": form["motivo"],
                "turnoId": int(form["turnoId"])}
    r = requests.post(URL + 'citasMedicas/save', data=json.dumps(dataForm), headers=headers)
    data = r.json()

    if r.status_code == 200:
        flash('Se ha guardado correctamente', category='info')
    else:
        flash('No se ha podido guardar', category='error')
    return redirect('/cita/registro')    

#SIGNOS VITALES

@citas_route.route('/signosVitales/registro')
def signos_vitales_registro():
    return render_template('parts/citas/registrar_signos.html')

@citas_route.route('/signosVitales/save', methods=['POST'])
def signos_vitales_save():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"motivo": form["motivo"],
                "observaciones": form["observaciones"],
                "motivo": form["motivo"],
                "turnoId": int(form["turnoId"])}
    r = requests.post(URL + 'citasMedicas/save', data=json.dumps(dataForm), headers=headers)
    data = r.json()

    if r.status_code == 200:
        flash('Se ha guardado correctamente', category='info')
    else:
        flash('No se ha podido guardar', category='error')
    return redirect('/cita/registro')    

#PARA VER LOS RECURSOS DEL TEMPLATE
@citas_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@citas_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')