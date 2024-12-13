from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

diagnostico_route = Blueprint('diagnostico_route', __name__)

URL = "http://localhost:8080/myapp/"

@diagnostico_route.route('/diagnostico')
def citas_medicas():
    r = requests.get(URL + 'diagnostico/list')
    data = r.json().get('data')
    return render_template('parts/citas/diagnosticos.html', diagnositcos=data)
