from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests
from . import mainRoute

pacientes_route = Blueprint('pacientes_route', __name__)

URL = "http://localhost:8070/myapp/"

sesion = mainRoute.get_session()

@pacientes_route.context_processor
def inject_session():
    return dict(sesion_templates=sesion)

@pacientes_route.route('/paciente/all')
def pacientes():
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion['rol'] == 3:
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    r = requests.get(URL + 'persona/list')
    data = r.json().get('data')
    for paciente in data:
        paciente["edad"] = requests.get(URL + 'persona/age/' + str(paciente["id"])).json().get('data')
    
    return render_template('parts/pacientes/pacientesList.html', pacientes=data)

@pacientes_route.route('/paciente/<id>')
def paciente_view(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    r = requests.get(URL + 'persona/get/' + id)
    data = r.json().get('data')
    rAge = requests.get(URL + 'persona/age/' + id)
    edad = rAge.json().get('data')
    return render_template('parts/pacientes/pacienteInfo.html', paciente=data, edad=edad)

@pacientes_route.route('/medico/<id>')
def medico_view(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion['rol'] != 2:
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    r = requests.get(URL + 'medicos/get/' + id)
    data = r.json().get('data')
    rAge = requests.get(URL + 'medicos/age/' + id)
    edad = rAge.json().get('data')
    return render_template('parts/pacientes/medicoInfo.html', medico=data, edad=edad)

@pacientes_route.route('/paciente/editar/<id>')
def update_familias_view(id):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    r = requests.get(URL + 'persona/get/'+ id)
    data = r.json().get('data')
    return render_template('parts/pacientes/editar.html', paciente=data)
    
@pacientes_route.route('/paciente/update', methods=["POST"])
def update_familia():
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataForm = {"direccion": form["direccion"],
                "email": form["email"],
                "telefono": form["telefono"],
                "id": int(form["id"])}
    r = requests.post(URL + 'persona/update', data=json.dumps(dataForm), headers=headers)
    id = form["id"]
    print(dataForm)
    if r.status_code == 200:
        flash('Se ha actualizado correctamente', category='info')
    else:
        data = r.json().get('data')
        flash('No se pudo actualizar: '+str(data), category='error')
    return redirect('/paciente/'+id)

@pacientes_route.route('/paciente/all/ordenar/<criterio>/<orden>')
def pacientes_ordenar(criterio, orden):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion['rol'] == 3:
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    r = requests.get(URL + 'persona/orderBy/' + criterio + '/' + orden)
    data = r.json().get('data')
    flash('Ordenado por ' + criterio + ' ' + orden, category='info')
    return render_template('parts/pacientes/pacientesList.html', pacientes=data)

@pacientes_route.route('/paciente/all/buscar/<atributo>/<valor>')
def pacientes_buscar(atributo, valor):
    if 'rol' not in sesion:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    if sesion['rol'] == 3:
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')
    r = requests.get(URL + 'persona/linealSearch/' + atributo + '/' + valor)
    data = r.json().get('data')
    if r.status_code == 200:
        flash('Resultados de la busqueda', category='info')
        return render_template('parts/pacientes/pacientesList.html', pacientes=data)
    else:
        flash('No se encontraron resultados', category='error')
        return redirect('/paciente/all')
