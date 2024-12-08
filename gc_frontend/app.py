from flask import Flask
from routes.citasRoute import citas_route
from routes.turnosRoute import turnos_route
from routes.mainRoute import main_route

def create_app():
    app = Flask(__name__, instance_relative_config=True)

    with app.app_context():
        app.register_blueprint(citas_route)
        app.register_blueprint(turnos_route)
        app.register_blueprint(main_route)

    return app