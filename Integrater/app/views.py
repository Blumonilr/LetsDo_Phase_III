from flask import request
import requests

from app import app


@app.route("/")
def index():
    return "hello"


@app.route("/postImage", methods=['POST' ])
def postImage():
    image_id=request.get_data()
    return "hello"