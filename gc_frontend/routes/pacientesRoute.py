from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

pacientes_route = Blueprint('pacientes_route', __name__)

URL = "http://localhost:8080/myapp/"

@pacientes_route.route('/pacientes')
def pacientes():
    r = requests.get(URL + 'pacientes')
    data = r.json().get('data')
    return render_template('parts/pacientes/pacientesList.html', pacientes=data)