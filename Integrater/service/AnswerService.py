import os
import xml
import numpy as np
from matplotlib.path import Path
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import utils.DBHandler as db
import utils.xmlParser as xp
import utils.cluster as clu


def work(imageId,markmode):
	session=db.setup_db()
	image=session.query(db.Image).filter(db.Image.id==imageId).one()

	if image.is_finished:
		# get the answer
		tag=session.query(db.Tag).filter(db.Tag.image_id==imageId and db.Tag.is_result==True).one()
		# calculate accuracy and update the db

		pass
	else:
		try:
			# generate answer

			# calculate accuracy and update the db
			pass
		except Exception:
			pass
		pass




'''
生成image对象的答案
imageId : long
markmode : int 
'''
def generateAnswer(imageId,markmode):
	session=db.setup_db()
	image=session.query(db.Image).filter(db.Image.id==imageId).one()
	if (image.current_num>=image.min_numv) and not image.is_finished:
		if(markmode==0):
			# square
			handler=getSquareModeData(imageId)
			generateSquareModeAnswer(handler)

		elif markmode==1:
			# area
			getAreaModeData(imageId)


	session.close()

path=os.getcwd()+'\\tmp.xml'

def getSquareModeData(imageId):
	session=db.setup_db()
	tags = session.query(db.Tag).filter(db.Tag.image_id == imageId).all()

	parser = xml.sax.make_parser()
	parser.setFeature(xml.sax.handler.feature_namespaces, 0)
	handler = xp.SquareParser()
	parser.setContentHandler(handler)

	for tag in tags:
		xml_string=tag.xml_file
		with open(path,'w+',encoding='UTF-8') as f:
			f.write(xml_string)
		parser.parse(path)
	session.close()
	# return [handler.projectId,handler.publisherId,handler.pictureId,[handler.pictureWidth,\
	#     handler.pictureHeight]],handler.allPoints,handler.allSizes,handler.allTags,handler.userId\
	# 	,handler.categories,[handler.times,handler.clicks,handler.deletes]
	return handler

def getAreaModeData(imageId):
	session = db.setup_db()
	tags = session.query(db.Tag).filter(db.Tag.image_id == imageId).all()

	parser = xml.sax.make_parser()
	parser.setFeature(xml.sax.handler.feature_namespaces, 0)
	handler = xp.AreaParser()
	parser.setContentHandler(handler)

	for tag in tags:
		xml_string = tag.xml_file
		with open(path, 'w+', encoding='UTF-8') as f:
			f.write(xml_string)
		parser.parse(path)
	session.close()
	# return [handler.projectId,handler.publisherId,handler.pictureId,[handler.pictureWidth,\
	#     handler.pictureHeight]],handler.allPoints,handler.allSizes,handler.allTags,handler.userId\
	# 	,handler.categories,[handler.times,handler.clicks,handler.deletes]
	return handler

def generateSquareModeAnswer(handler):
	points=handler.allPoints
	width=handler.pictureWidth
	height=handler.pictureHeight
	coordinates=clu.preprocess_data(points)

	centers=[]
	try:
		centers=clu.cal_rec(coordinates=coordinates)
		generateTag(centers,width,height)

	except Exception:
		pass

	# 目前只能实现单目标的答案整合
	pass

def generateTag(points,width,height):
	fig=plt.figure()
	ax=fig.add_subplot(111)
	for x in points:
		verts=[(x[0],height-x[1]),
		       (x[0]+x[2],height-x[1]),
		       (x[0]+x[2],height-x[1]-x[3]),
		       (x[0],height-x[1]-x[3]),
		       (x[0],height-x[1])]
		codes=[Path.MOVETO,
		       Path.LINETO,
		       Path.LINETO,
		       Path.LINETO,
		       Path.CLOSEPOLY]
		route=Path(verts,codes)
		patch=patches.PathPatch(route,lw=2)
		ax.add_patch(patch)
	ax.set_xlim(0,width)
	ax.set_ylim(0,height)
	plt.show()
	pass

if __name__=='__main__':
	coordinates=[[[0,0,5,10],[6,7,8,13]],[[0,1,6,7],[6,6,7,15]],[[1,0,9,8],[5,7,7,16]]\
		,[[0,-1,4,10],[7,7,9,12]],[[-1,0,7,9],[6,8,10,10]],[[2,3,100,100],[9,10,100,100]]]
	# sizes=[[[5,10],[8,13]],[[6,7],[7,15]],[[9,8],[7,16]],[[4,10],[9,12]],[[7,9],[10,10]],[[100,100],[100,100]]]
	# coordinates=[[[139, 71, 127, 71], [145, 233, 152, 233], [446, 123, 108, 123]]]
	coordinates=clu.preprocess_data(coordinates)
	# sizes=clu.preprocess_data(sizes)
	#
	coordinates,user_accuracy=clu.cal_rec(coordinates)
	print(coordinates)
	generateTag(coordinates,30,30)

	pass