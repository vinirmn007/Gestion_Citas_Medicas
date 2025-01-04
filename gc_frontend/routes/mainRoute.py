from flask import Blueprint, json, render_template, request, redirect, url_for, flash, session
import requests

main_route = Blueprint('main_route', __name__)

URL = "http://localhost:8080/myapp/"

@main_route.route('/')
def presentation():
    return render_template('login/login.html')

@main_route.route('/home')
def home():
    if 'usuario' not in session:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')
    return render_template('home.html', usuario=session['usuario'])

@main_route.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        headers = {'Content-Type': 'application/json'}
        usuario = request.form.get('usuario')
        contrasena = request.form.get('contrasena')

        if not usuario or not contrasena:
            flash('Usuario y contraseña son requeridos', category='error')
            return redirect('/login')

        try:
            r = requests.get(f'{URL}cuenta/list', headers=headers)
            usuarios_data = r.json()
            usuario_encontrado = next(
                (user for user in usuarios_data.get('data', []) if user['usuario'] == usuario), None
            )

            if usuario_encontrado and usuario_encontrado['contrasena'] == contrasena:
                session['usuario'] = usuario_encontrado['usuario']
                session['rol'] = usuario_encontrado.get('rol')  # Opcional
                flash('Login exitoso', category='info')
                return redirect('/home')
            else:
                flash('Usuario o contraseña incorrectos', category='error')
                return redirect('/login')

        except requests.exceptions.RequestException as e:
            flash(f"Error al conectar con el backend: {str(e)}", category='error')
            return redirect('/login')

    return render_template('login/login.html')

@main_route.route('/logout')
def logout():
    session.clear()
    flash('Has cerrado sesión correctamente', category='info')
    return redirect('/login')

@main_route.route('/cuenta/registrar')
def registrarCuenta():
    try:
        r_rols = requests.get(f'{URL}rol/list')

        if r_rols.status_code == 200:
            roles_data = r_rols.json()
            lista_roles = roles_data.get('data', [])
        else:
            lista_roles = []
            flash('Error al obtener la lista de roles del servidor', category='error')
    except Exception as e:
        lista_roles = []
        flash(f'Error al conectar con el servicio: {e}', category='error')

    return render_template('login/registrar.html', lista_roles=lista_roles)

@main_route.route('/cuenta/save', methods=['POST'])
def saveCuenta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "usuario": form["usuario"],
        "contrasena": form["contrasena"],
        "rol": {"id": form["id"]},
    }

    try:
        r = requests.post(f'{URL}cuenta/save', data=json.dumps(dataF), headers=headers)
        if r.status_code == 200:
            flash('Cuenta registrada con éxito', category='success')
            return redirect('/login')
        else:
            error_message = r.json().get('message', 'Error al registrar la cuenta')
            flash(error_message, category='error')
    except requests.exceptions.RequestException as e:
        flash(f'Error al conectar con el backend: {str(e)}', category='error')

    return redirect('/cuenta/registrar')
