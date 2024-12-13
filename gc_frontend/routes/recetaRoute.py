from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

receta_route = Blueprint('receta_route', __name__)

URL = "http://localhost:8080/myapp/"

@receta_route.route('/receta')
def recetas():
    r = requests.get(URL + 'receta/list')
    data = r.json().get('data')
    return render_template('parts/receta/receta.html', recetas=data)

#PARA VER LOS RECURSOS DEL TEMPLATE
@receta_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@receta_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')