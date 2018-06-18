
from flask import request
from service import AnswerService as controller
from app import app


@app.route("/")
def index():
    return "hello"

@app.route("/hello")
def test():
    controller.work(121,1)
    return "hello world"


@app.route("/postImage", methods=['POST' ])
def postImage():
    str=request.get_data()

    str=str.decode()


    image_id=int(str.split("_")[0])

    markMode=str.split("_")[1]
    if markMode=='AREA':
        markMode=1
    else:
        markMode=0

    controller.work(image_id,markMode)

    return "hello"

if __name__=="__main__":
    controller.work(121,1)