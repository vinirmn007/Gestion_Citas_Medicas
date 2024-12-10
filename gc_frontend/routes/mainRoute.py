from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

main_route = Blueprint('main_route', __name__)

URL = "http://localhost:8080/myapp/"

@main_route.route('/')
def presentation():
    return render_template('login/login.html')

@main_route.route('/home')
def home():
    return render_template('home.html')

@main_route.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        headers = {'Content-Type': 'application/json'}
        usuario = request.form.get('usuario')  # Usar .get() para evitar KeyError
        contrasena = request.form.get('contrasena')

        if not usuario or not contrasena:
            flash('Usuario y contraseña son requeridos', category='error')
            return redirect('/login')

        try:
            # Solicitar todos los usuarios al backend para verificar el usuario y la contraseña
            r = requests.get('http://localhost:8080/myapp/cuenta/list', headers=headers)
            usuarios_data = r.json()

            # Verificar si el usuario existe
            usuario_encontrado = None
            for user in usuarios_data.get('data', []):  # Usar .get() para evitar KeyError
                if user['usuario'] == usuario:
                    usuario_encontrado = user
                    break

            # Verificar si el usuario y la contraseña son correctos
            if usuario_encontrado and usuario_encontrado['contrasena'] == contrasena:
                flash('Login exitoso', category='info')
                return redirect('/home')  # Redirigir al dashboard o a la página principal
            else:
                flash('Usuario o contraseña incorrectos', category='error')
                return redirect('/login')  # Redirigir al formulario de login

        except requests.exceptions.RequestException as e:
            flash(f"Error al conectar con el backend: {str(e)}", category='error')
            return redirect('/login')

    # Si el método es GET, simplemente mostrar el formulario de login
    return render_template('login/login.html')


@main_route.route('/cuenta/registrar')
def registrarCuenta():
    try:
        # Obtener la lista de roles desde el servicio REST
        r_rols = requests.get('http://localhost:8080/myapp/rol/list')

        if r_rols.status_code == 200:   
            roles_data = r_rols.json()  # Decodificar la respuesta JSON
            lista_roles = roles_data.get('data', [])  # Obtener la lista de roles
            print(lista_roles)  # Depuración: Verifica los datos recibidos
        else:
            lista_roles = []
            flash('Error al obtener la lista de roles del servidor', category='error')
    except Exception as e:
        lista_roles = []
        flash(f'Error al conectar con el servicio: {e}', category='error')

    # Pasar la lista de roles al template
    return render_template('login/registrar.html', lista_roles=lista_roles)

   
@main_route.route('/cuenta/save', methods=['POST'])
def saveCuenta():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    # Construir el objeto `dataF` con los datos del formulario
    dataF = {
        "usuario": form["usuario"],
        "contrasena": form["contrasena"],
        "rol": {"id": form["id"]},  # ID de rol seleccionado
    }
    

    # Hacer la solicitud POST al servicio REST en Java
    r = requests.post('http://localhost:8080/myapp/cuenta/save', data=json.dumps(dataF), headers=headers)
    dat = r.json()
    # Procesar la respuesta del servicio REST
    if r.status_code == 200:
        flash('Cuenta registrado con éxito', category='success')
        return redirect('/login')
    else:
        error_message = r.json().get('data', 'Error al registrar el cuenta')
        flash(error_message, category='error')
        return redirect('/cuenta/registrar')

