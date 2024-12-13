from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

citas_route = Blueprint('citas_route', __name__)

URL = "http://localhost:8080/myapp/"

@citas_route.route('/citas')
def citas_medicas():
    return render_template('parts/citas/registrar_citas.html')

@citas_route.route('/citas/all')
def citas_medicas_all():
    r = requests.get(URL + 'citasMedicas')
    data = r.json().get('data')
    return render_template('parts/citas/citas.html', citas=data)

@citas_route.route('/historial/registro')
def historial_registro():
    return render_template('parts/citas/registrar_historial.html')

#PARA VER LOS RECURSOS DEL TEMPLATE
@citas_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@citas_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')