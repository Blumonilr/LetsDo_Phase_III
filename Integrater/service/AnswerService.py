import sys
import xml

import utils.DBHandler as db
import utils.xmlParser as xp
import os

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
			getSquareModeData(imageId)
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

if __name__=='__main__':
	handler=getSquareModeData(1)
	print(handler.projectId)

	pass