import base64
import os
from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests
from . import mainRoute

examen_route = Blueprint('examen_route', __name__)

URL = "http://localhost:8070/myapp/"
UPLOAD_FOLDER = 'media/examenes'
os.makedirs(UPLOAD_FOLDER, exist_ok=True) 

sesion = mainRoute.get_session()

@examen_route.context_processor
def inject_session():
    return dict(sesion_templates=sesion)

@examen_route.route('/examen')
def examens():
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:
        r = requests.get(URL + 'examen/list')
        data = r.json().get('data')
        return render_template('parts/examen/examen.html', examens=data)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@examen_route.route('/examen/<id>')
def examen(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:
        r = requests.get(URL + 'examen/get/' + id)
        data = r.json().get('data')
        return render_template('parts/examen/examenDetalle.html', examen=data)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@examen_route.route('/examen/registro/<int:id>')
def registro_examen(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:
        r = requests.get(URL + 'examen/listTiposExamenes/')
        data = r.json().get('data')
        print (data)
        return render_template('parts/examen/registrar_examen.html', id=id, tipos_examen=data)
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)

@examen_route.route('/examen/save', methods=['POST'])
def save_examen():
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    try:
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
    except Exception as e:
        flash(f'Error: {str(e)}', category='error')
        return redirect(request.referrer)