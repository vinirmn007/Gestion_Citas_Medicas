from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

turnos_route = Blueprint('turnos_route', __name__)

URL = "http://localhost:8080/myapp/"

@turnos_route.route('/turno/registrar')
def registrar_turno():
    return render_template('parts/turnos/registrar.html')