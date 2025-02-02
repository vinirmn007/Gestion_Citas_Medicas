from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

receta_route = Blueprint('receta_route', __name__)

URL = "http://localhost:8080/myapp/"

@receta_route.route('/receta')
def recetas():
    r = requests.get(URL + 'receta/list')
    data = r.json().get('data')
    return render_template('parts/receta/receta.html', recetas=data)

@receta_route.route('/receta/<id>')
def receta(id):
    r = requests.get(URL + 'receta/get/' + id)
    data = r.json().get('data')
    return render_template('parts/receta/recetaDetalle.html', receta=data)

@receta_route.route('/receta/registro/<int:id>')
def registro(id):  
    return render_template('parts/receta/registrar_receta.html', id=id)

@receta_route.route('/receta/save', methods=['POST'])
def save():
    form = request.form
    data = {
        "prescripcion": form["prescripcion"],
        "medicamentos": form["medicamentos"],
        "idDiagnostico": int(form["idDiagnostico"])
    }
    headers = {'Content-Type': 'application/json'}
    r = requests.post(URL + 'receta/save', headers=headers, data=json.dumps(data))
    print(r)
    if r.status_code == 200:
        flash('Receta registrada exitosamente', category='info')
        return redirect(url_for('receta_route.recetas'))
    else:
        flash('Error al registrar receta', category='error')
        return redirect(url_for('receta_route.registro', id=data['idDiagnostico']))





#PARA VER LOS RECURSOS DEL TEMPLATE
@receta_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@receta_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')