from flask import Flask

def create_app():
    app = Flask(__name__, instance_relative_config=True)

    with app.app_context():
        from routes.citasRoute import citas_route
        app.register_blueprint(citas_route)

    return app