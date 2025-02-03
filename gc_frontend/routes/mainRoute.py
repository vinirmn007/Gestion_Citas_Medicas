from flask import Blueprint, app, json, render_template, request, redirect, url_for, flash, session
import requests
from datetime import datetime
main_route = Blueprint('main_route', __name__)

def get_session():
    return session

@main_route.context_processor
def inject_session():
    return dict(sesion_templates=session)

URL = "http://localhost:8070/myapp/"

@main_route.route('/')
def presentation():
    return render_template('presentation.html')

@main_route.route('/login')
def load_login():
    return render_template('login/login.html')  

@main_route.route('/home')
def home():
    if 'usuario' not in session:
        flash('Debes iniciar sesión para acceder a esta página', category='error')
        return redirect('/login')

    usuario = session['usuario']
    rol = session.get('rol', 3)  # Si no está en sesión, se asume paciente (rol 3)

    return render_template('home.html', usuario=usuario, rol=rol)


@main_route.route('/admin/roles')
def administrar_roles():
    if 'usuario' not in session or session.get('rol') != 1:  # Solo admin puede acceder
        flash('No tienes permisos para acceder a esta página', category='error')
        return redirect('/home')

    # Obtener la lista de usuarios para asignar roles
    try:
        r = requests.get('http://localhost:8070/myapp/cuenta/list')
        data = r.json()
        lista_usuarios = data.get("data", [])
    except requests.exceptions.RequestException as e:
        flash(f'Error al obtener usuarios: {str(e)}', category='error')
        return redirect('/home')

    return render_template('admin/admin_roles.html', lista_usuarios=lista_usuarios)

@main_route.route('/admin/roles/asignar', methods=['POST'])
def asignar_rol():
    if 'usuario' not in session or session.get('rol') != 1:  # Solo admin puede cambiar roles
        flash('No tienes permisos para realizar esta acción', category='error')
        return redirect('/home')

    usuario = request.form.get('usuario')
    nuevo_rol = request.form.get('rol')

    if not usuario or not nuevo_rol:
        flash('Faltan datos para asignar el rol', category='error')
        return redirect('/admin/roles')

    # Construir los datos en el formato esperado por la API de Java
    data = {
        "id": usuario,  # Aquí debes enviar el 'id' de la cuenta, no el nombre de usuario
        "rol": {
            "id": int(nuevo_rol)  # El ID del rol que se quiere asignar
        }
    }

    try:
        headers = {'Content-Type': 'application/json'}
        r = requests.post('http://localhost:8070/myapp/cuenta/update', json=data, headers=headers)

        if r.status_code == 200:
            flash('Rol asignado correctamente', category='info')
        else:
            flash('Error al asignar el rol', category='error')

    except requests.exceptions.RequestException as e:
        flash(f'Error al conectar con el servidor: {str(e)}', category='error')

    return redirect('/admin/roles')

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
            # Realiza la solicitud POST al backend de Java
            url = 'http://localhost:8070/myapp/cuenta/login'  # Asegúrate de que la URL sea la correcta
            data = {
                "usuario": usuario,
                "contrasena": contrasena
            }
            response = requests.post(url, json=data, headers=headers)

            if response.status_code == 200:
                # Si el login es exitoso, recibe los datos de respuesta
                response_data = response.json()
                session['usuario'] = response_data['usuario']
                session['rol'] = response_data['rol']
                
                cuenta_id = response_data['id']
                r2 = requests.get(f'{URL}persona/binarySearch/cuentaId/{cuenta_id}')
                persona_data = r2.json().get('data', None)
                session['persona'] = persona_data
                
                flash('Login exitoso', category='info')

                # Redirigir según el rol del usuario
                if session['rol'] == 1:
                    return redirect('/admin/roles')  # Admin va a la página de administración
                print("SESIOOOOOOOOOOOON: ", session)
                return redirect('/home')  # Otros usuarios van a home
            
            else:
                # Si la respuesta no es 200, muestra el mensaje de error
                response_data = response.json()
                flash(f"Error: {response_data.get('data', 'Usuario o contraseña incorrectos')}", category='error')
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

@main_route.route('/cuenta/persona/registrar')
def registrarPersona():
    r=requests.get('http://localhost:8070/myapp/persona/list')
    data = r.json()
    return render_template('login/registrar.html', lista = data["data"])


@main_route.route('/login/cuenta/save', methods=['POST'])
def savePersona():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Convertir la fecha de nacimiento a formato dd-MM-yyyy
    try:
        fecha_nacimiento = datetime.strptime(form["fechaNacimiento"], "%Y-%m-%d")  # Convierte de yyyy-mm-dd a datetime
        fecha_nacimiento_str = fecha_nacimiento.strftime("%d-%m-%Y")  # Convierte a dd-MM-yyyy
    except ValueError:
        flash('Fecha de nacimiento en formato incorrecto.', category='error')
        return redirect('/login')

    dataF = {
        "cuenta": {
            "usuario": form["usuario"],
            "contrasena": form["contrasena"],
            "id_rol": 3  # Por defecto, paciente
        },
        "persona": {
            "nombre": form["nombre"],
            "email": form["email"],
            "celular": form["celular"],
            "fechaNacimiento": fecha_nacimiento_str,  # Usar la fecha formateada
            "genero": form["genero"],
            "numeroIdentificacion": form["numeroIdentificacion"],
            "tipoIdentificacion": form["tipoIdentificacion"]
        }
    }

    try:
        # Realiza la solicitud POST al servidor Java
        r = requests.post('http://localhost:8070/myapp/persona/saveP', data=json.dumps(dataF), headers=headers)
        dat = r.json()

        # Verifica el código de estado
        if r.status_code == 200:
            flash('Usuario registrado con éxito', category='info')
        else:
            error_message = dat.get('data', 'Error al registrar la persona')
            flash(error_message, category='error')

        # Redirige a la lista después de procesar la solicitud
        return redirect('/login')

    except requests.exceptions.RequestException as e:
        # Maneja excepciones de la solicitud
        flash(f'Error al conectar con el servicio: {str(e)}', category='error')
        return redirect('/login')
