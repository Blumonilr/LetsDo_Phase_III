#coding=utf-8
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

	# get workers' answers
	handler=getAnswerFromTags(imageId)

	if image.is_finished:
		# get the answer
		tag=session.query(db.Tag).filter(db.Tag.image_id==imageId and db.Tag.is_result==True).one()
		# calculate accuracy and update the db
		print('already has result , calculate accuracy')
		print('update db')

		pass
	else:
		print('calculate the result , calculate accuracy')
		print('update db')
		try:
			# generate answer
			res_centers,res_labels=generateResult(handler,markmode)

			# calculate accuracy and update the db
			# modify CommitEvent
			# modify Ability
			# set Image to finished
			# set Tag to isResult
			pass
		except Exception:
			pass
		pass

	session.close()


def getAnswerFromTags(imageId):
	session=db.setup_db()
	tags=session.query(db.Tag).filter(db.Tag.image_id==imageId).all()

	parser = xml.sax.make_parser()
	parser.setFeature(xml.sax.handler.feature_namespaces, 0)
	handler = xp.XmlParser()
	parser.setContentHandler(handler)

	for tag in tags:
		xml_string = tag.xml_file
		with open(path, 'w+', encoding='UTF-8') as f:
			f.write(xml_string)
		parser.parse(path)

	session.close()
	return handler

def generateResult(handler,markmode):
	res_centers=[]
	res_labels=[]

	if markmode==0:
		# square
		points=handler.allPoints
		res_centers=clu.cal_rec(clu.preprocess_data(points))
		res_labels=generateTextLabel(handler.allTags)
		pass
	elif markmode==1:
		# area
		res_centers=[]
		res_labels=generateTextLabel(handler.allTags)
		pass
	return res_centers,res_labels
	pass

def generateTextLabel(labels):
	print(labels)
	pass

def calculateAccuracy():
	pass


path=os.getcwd()+'\\tmp.xml'

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
	coordinates=clu.preprocess_data(coordinates)
	coordinates,user_accuracy=clu.cal_rec(coordinates)
	print(coordinates)
	generateTag(coordinates,30,30)

	# parser = xml.sax.make_parser()
	# parser.setFeature(xml.sax.handler.feature_namespaces, 0)
	# handler = xp.XmlParser()
	# parser.setContentHandler(handler)
	# parser.parse('area.xml')
	#
	# generateTextLabel(handler.allTags)

	pass