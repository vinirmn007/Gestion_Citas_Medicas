import base64
import os
from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

examen_route = Blueprint('examen_route', __name__)

URL = "http://localhost:8070/myapp/"
UPLOAD_FOLDER = 'media/examenes'
os.makedirs(UPLOAD_FOLDER, exist_ok=True) 

@examen_route.route('/examen')
def examens():
    r = requests.get(URL + 'examen/list')
    data = r.json().get('data')
    return render_template('parts/examen/examen.html', examens=data)

@examen_route.route('/examen/<id>')
def examen(id):
    r = requests.get(URL + 'examen/get/' + id)
    data = r.json().get('data')
    return render_template('parts/examen/examenDetalle.html', examen=data)

#PARA VER LOS RECURSOS DEL TEMPLATE
@examen_route.route('/tablas')
def tablas():
    return render_template('tables/tables.html')

@examen_route.route('/form_v')
def form_():
    return render_template('forms/form_validation.html')

@examen_route.route('/examen/registro/<int:id>')
def registro_examen(id):
    r = requests.get(URL + 'examen/listTiposExamenes/')
    data = r.json().get('data')
    print (data)
    return render_template('parts/examen/registrar_examen.html', id=id, tipos_examen=data)

@examen_route.route('/examen/save', methods=['POST'])
def save_examen():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    file = request.files.get('archivo')
    idDiagnostico = form["idDiagnostico"]

    if not idDiagnostico or not file:
        print ("Error al guardar el examen")
        flash('Error al guardar el examen', category='error')
        return redirect(url_for('examen_route.registro_examen', id=idDiagnostico))
    
    identificador = 1
    while os.path.exists(os.path.join(UPLOAD_FOLDER, f'examen{idDiagnostico}{identificador}.pdf')):
        identificador += 1

    filename = f'examen{idDiagnostico}{identificador}.pdf'
    file_path = os.path.join(UPLOAD_FOLDER, filename)

    dataForm = {"descripcion": form["descripcion"],
                "tipoExamen": form["tipoExamen"],
                "nombreArchivo": filename,
                "diagnosticoC": idDiagnostico}
    r = requests.post(URL + 'examen/save', data=json.dumps(dataForm), headers=headers)
    print(r)
    print("Datos Enviados:" + str(dataForm))
    if r.status_code == 200:
        file.save(file_path)
        flash('Examen guardado correctamente', category='success')
        return redirect(url_for('examen_route.examens', id=idDiagnostico))
    else:
        flash('Error al guardar el examen', category='error')
        return redirect(url_for('examen_route.registro_examen', id=idDiagnostico))