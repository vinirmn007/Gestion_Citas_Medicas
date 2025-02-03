from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

signos_route = Blueprint('signos_route', __name__)

URL = "http://localhost:8070/myapp/"

#SIGNOS VITALES

@signos_route.route('/signosVitales/registro/<id>')
def signos_vitales_registro(id):
    return render_template('parts/signos/registro.html', turnoId=id)

@signos_route.route('/signosVitales/save', methods=['POST'])
def signos_vitales_save():
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