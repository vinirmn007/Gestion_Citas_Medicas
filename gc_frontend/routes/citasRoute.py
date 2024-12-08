from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

citas_route = Blueprint('citas_route', __name__)

URL = "http://localhost:8080/myapp/"

@citas_route.route('/')
def home():
    return render_template('home.html')

@citas_route.route('/citas')
def citas_medicas():
    r = requests.get(URL + 'citasMedicas')
    data = r.json().get('data')
    return render_template('parts/citas/citas.html', citas=data)

@citas_route.route('/turno/registrar')
def registrar_turno():
    return render_template('parts/turnos/registrar.html')

#PARA VER LOS RECURSOS DEL TEMPLATE
@citas_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@citas_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')