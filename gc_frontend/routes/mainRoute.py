from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

main_route = Blueprint('main_route', __name__)

URL = "http://localhost:8080/myapp/"

@main_route.route('/')
def presentation():
    return render_template('presentation.html')

@main_route.route('/login')
def login():
    return render_template('login/login.html')

@main_route.route('/home')
def home():
    return render_template('home.html')
