from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

medicamento_route = Blueprint('medicamento_route', __name__)

URL = "http://localhost:8080/myapp/"

@medicamento_route.route('/medicamento')
def medicamentos():
    r = requests.get(URL + 'medicamento/list')
    data = r.json().get('data')
    return render_template('parts/medicamento/medicamento.html', medicamentos=data)

#PARA VER LOS RECURSOS DEL TEMPLATE
@medicamento_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@medicamento_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')