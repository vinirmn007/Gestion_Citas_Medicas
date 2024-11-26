from flask import Blueprint, json, render_template, request, redirect, url_for, flash
import requests

router = Blueprint('router', __name__)

@router.route('/')
def home():
    return render_template('home/index.html')