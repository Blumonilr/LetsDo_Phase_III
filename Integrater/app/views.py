from flask import request

from app import app


@app.route("/")
def index():
    return "hello"


@app.route("/postImage", methods=['POST' ])
def postImage():
    str=request.get_data()
    image_id=str.split("_")[0]
    markMode=int(str.split("_")[1])

    return "right"

