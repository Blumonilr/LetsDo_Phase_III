
from flask import request
from service import AnswerService as controller
from app import app


@app.route("/")
def index():
    return "hello"

@app.route("/hello")
def test():
    controller.work(204,0)
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

    return "evaluate answer"

@app.route("/evaluateTestAnswer",methods=['POST'])
def evaluateTestAnswer():
	str=request.get_data()
	str=str.decode()
	userId=int(str.split("_"))[0]
	imageId=int(str.split("_"))[1]
	markMode=str.split("_")[2]
	if markMode == 'AREA':
		markMode = 1
	else:
		markMode = 0
	controller.workOne(userId,imageId,markMode)

	return "evaluate test answer"

if __name__=="__main__":
    controller.work(204,0)