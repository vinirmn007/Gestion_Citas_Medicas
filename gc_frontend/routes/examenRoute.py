from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

examen_route = Blueprint('examen_route', __name__)

URL = "http://localhost:8080/myapp/"

@examen_route.route('/examen')
def examens():
    r = requests.get(URL + 'examen/list')
    data = r.json().get('data')
    return render_template('parts/examen/examen.html', examens=data)

#PARA VER LOS RECURSOS DEL TEMPLATE
@examen_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@examen_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')