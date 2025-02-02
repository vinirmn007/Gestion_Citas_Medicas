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
    recetas = requests.get(URL + 'receta/list')
    examenes = requests.get(URL + 'examen/list')
    data = r.json().get('data')
    rdata = recetas.json().get('data')
    edata = examenes.json().get('data')
    recetas_filtradas = [receta for receta in rdata if receta.get('idDiagnostico') == int(id)]
    examenes_filtrados = [examen for examen in edata if examen.get('idDiagnostico') == int(id)]
    print("Recetas")
    print(rdata)
    print("Recetas filtradas")
    print(recetas_filtradas)
    return render_template('parts/diagnostico/diagnosticoDetalle.html', diagnostico=data, recetas=recetas_filtradas, examenes=examenes_filtrados)

@diagnostico_route.route('/diagnostico/registro/<int:id>')
def registrarDiagnostico(id):
    return render_template('parts/diagnostico/registrar_diagnostico.html', id=id)

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
        return redirect (url_for('diagnostico_route.diagnosticoList'))
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