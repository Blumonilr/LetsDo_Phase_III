from flask import request
import requests

from app import app


@app.route("/")
def index():
    return "hello"


@app.route("/hello", methods=['POST' ])
def hello():
    print(request.get_data())
    return "hello"