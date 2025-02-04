from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests
from . import mainRoute

receta_route = Blueprint('receta_route', __name__)

URL = "http://localhost:8070/myapp/"

sesion = mainRoute.get_session()

@receta_route.context_processor
def inject_session():
    return dict(sesion_templates=sesion)

@receta_route.route('/receta')
def recetas():
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:
        r = requests.get(URL + 'receta/list')
        data = r.json().get('data')
        return render_template('parts/receta/receta.html', recetas=data)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@receta_route.route('/receta/<id>')
def receta(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:     
        r = requests.get(URL + 'receta/get/' + id)
        data = r.json().get('data')
        return render_template('parts/receta/recetaDetalle.html', receta=data)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@receta_route.route('/receta/registro/<int:id>')
def registro(id):  
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    return render_template('parts/receta/registrar_receta.html', id=id)

@receta_route.route('/receta/save', methods=['POST'])
def save():
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:
        form = request.form
        idDiagnostico = form["idDiagnostico"]
        data = {
            "prescripcion": form["prescripcion"],
            "medicamentos": form["medicamentos"],
            "idDiagnostico": int(idDiagnostico)
        }
        headers = {'Content-Type': 'application/json'}
        r = requests.post(URL + 'receta/save', headers=headers, data=json.dumps(data))
        print(r)
        if r.status_code == 200:
            flash('Receta registrada exitosamente', category='info')
            return redirect(url_for('diagnostico_route.diagnostico', id=idDiagnostico))
        else:
            flash('Error al registrar receta', category='error')
            return redirect(url_for('receta_route.registro', id=data['idDiagnostico']))
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)
