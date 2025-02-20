from flask import Flask
from routes.citasRoute import citas_route
from routes.historialRoute import hsto_route
from routes.signosVitales import signos_route
from routes.turnosRoute import turnos_route
from routes.mainRoute import main_route
from routes.diagnosticoRoute import diagnostico_route
from routes.recetaRoute import receta_route
from routes.examenRoute import examen_route
from routes.pacientesRoute import pacientes_route


def create_app():
    app = Flask(__name__, instance_relative_config=True)

    with app.app_context():
        app.register_blueprint(citas_route)
        app.register_blueprint(turnos_route)
        app.register_blueprint(main_route)
        app.register_blueprint(diagnostico_route)
        app.register_blueprint(receta_route)   
        app.register_blueprint(examen_route)
        
        app.register_blueprint(pacientes_route)
        app.register_blueprint(hsto_route)
        app.register_blueprint(signos_route)

    return app