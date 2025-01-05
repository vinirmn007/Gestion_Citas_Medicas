from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

diagnostico_route = Blueprint('diagnostico_route', __name__)

URL = "http://localhost:8080/myapp/"

@diagnostico_route.route('/diagnosticos')
def diagnosticoList():
    r = requests.get(URL + 'diagnostico/list')
    data = r.json().get('data')
    return render_template('parts/diagnostico/diagnostico.html', diagnosticos=data)

@diagnostico_route.route('/diagnostico/<id>')
def diagnostico(id):
    r = requests.get(URL + 'diagnostico/get/' + id)
    data = r.json().get('data')
    return render_template('parts/diagnostico/diagnosticoDetalle.html', diagnostico=data)

@diagnostico_route.route('/diagnostico/registro')
def registrarDiagnostico():
    return render_template('parts/diagnostico/registrar_diagnostico.html')

@diagnostico_route.route('/diagnostico/save', methods=['POST'])
def saveDiagnostico():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataform = {"descripcion": form["descripcion"], "citaM": int(form["idCitaMedica"])}
    r = requests.post(URL + 'diagnostico/save', data=json.dumps(dataform), headers=headers)
    data = r.json()
    print(data)

    if r.status_code == 200:
        flash('Se guardo correctamente el diagnostico', category = 'info')
        return redirect (url_for('router.diagnosticoList'))
    else:
        print("Error al guardar el diagnostico")
        flash(str(data["data"]), category = 'error')
        return redirect (url_for('router.registrarDiagnostico'))




#PARA VER LOS RECURSOS DEL TEMPLATE
@diagnostico_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@diagnostico_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')