from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests
from . import mainRoute

signos_route = Blueprint('signos_route', __name__)

URL = "http://localhost:8070/myapp/"

sesion = mainRoute.get_session()

@signos_route.context_processor
def inject_session():
    return dict(sesion_templates=sesion)

#SIGNOS VITALES

@signos_route.route('/signosVitales/registro/<id>')
def signos_vitales_registro(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion['rol'] != 2:
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    return render_template('parts/signos/registro.html', turnoId=id)

@signos_route.route('/signosVitales/save', methods=['POST'])
def signos_vitales_save():
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion['rol'] != 2:
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    try:
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataForm = {"altura": float(form["estatura"]),
                    "peso": float(form["peso"]),
                    "temperatura": float(form["temperatura"]),
                    "presionSistolica": float(form["presion_s"]),
                    "presionDiastolica": float(form["presion_d"]),
                    "turnoId": int(form["turnoId"])}
        r = requests.post(URL + 'signosVitales/save', data=json.dumps(dataForm), headers=headers)
        print(r.json())
        data = r.json().get('data')

        if r.status_code == 200:
            flash('Se ha guardado correctamente', category='info')
        else:
            flash('No se ha podido guardar: ' + str(data), category='error')
    except Exception as e:
        flash('Ocurrió un error: ' + str(e), category='error')
    return redirect('/turno/espera/all')