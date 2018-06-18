
import os
import xml
import numpy as np
from matplotlib.path import Path
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from sqlalchemy import update

import utils.DBHandler as db
import utils.xmlParser as xp
import utils.cluster as clu


def work(imageId,markmode):
	session=db.setup_db()
	image=session.query(db.Image).filter(db.Image.id==imageId).one()

	# get workers' answers
	handler,userIds=getAnswerFromTags(imageId)
	usr_ans_rects=handler.allPoints
	usr_ans_tags=handler.allTags

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
			res_centers,res_labels,label_accuracy=generateResult(handler,markmode)
			if markmode==0:

				accuracy=[]
				for ans in usr_ans_rects:
					tmp=clu.cal_rect_accuracy(ans,res_centers)
					accuracy.append(tmp)

				# update commit event & user ability
				for i in range(len(accuracy)):
					accuracy[i]=0.8*accuracy[i]+0.2*label_accuracy[i]
				updateAccuracyAndAbility(imageId,userIds,accuracy)

				#generate tag object
				max_rect_accuracy=0.0
				ptr=-1
				for i in range(len(accuracy)):
					if accuracy[i]>max_rect_accuracy:
						max_rect_accuracy=accuracy[i]
						ptr=i
				if ptr!=-1:
					session.query(db.Tag).filter(db.tag.worker_id==userIds[ptr])\
						.update({db.tag.is_result:True})

				pass
			elif markmode==1:
				pass

			# calculate accuracy and update the db
			# modify CommitEvent
			# modify Ability
			# set Image to finished
			# set Tag to isResult
			pass
		except Exception:
			print('generate failed')

			pass
		pass

	session.close()


def updateAccuracyAndAbility(imageId,userIds,accuracy):
	session=db.setup_db()
	commits=session.query(db.CommitEvent).filter(db.CommitEvent.imageid==imageId).all()
	for commit in commits:
		for i in range(len(userIds)):
			if commit.workerid==userIds[i]:
				# commit.accuracy=accuracy[i]
				session.query(db.CommitEvent)\
					.filter(db.CommitEvent.imageid==imageId and db.CommitEvent.workerid==userIds[i])\
					.update({db.CommitEvent.accuracy:accuracy[i]})

	'''根据projectId获得项目对象的label
	转化为string列表
	'''
	projectId=session.query(db.Image).filter(db.Image.id==imageId).one().project_id
	labels=session.query(db.Project_Label).filter(db.Project_Label.project_id==projectId).all()
	labels=[x.labels for x in labels]

	for i in range(len(userIds)):
		abilities=session.query(db.Ability).filter(db.Ability.user_id ==userIds[i]
	                                           and db.Ability.label_name in labels).all()
		for ability in abilities:
			for label in labels:
				if ability.label_name==label:
					total=ability.label_history_num*ability.accuracy+accuracy[i]
					count=ability.label_history_num+1
					total=total/ability.label_history_num
					session.query(db.Ability).filter(db.Ability.user_id==userIds[i]
						and db.Ability.label_name==label).update({db.Ability.accuracy:total,
					                                              db.Ability.label_history_num:count})

	session.close()

def getAnswerFromTags(imageId):
	session=db.setup_db()
	tags=session.query(db.Tag).filter(db.Tag.image_id==imageId).all()
	userIds=[]

	handler=xp.XMLParser()

	for tag in tags:
		xml_string = tag.xml_file
		handler.parse(xml_string)
		userIds.append(tag.worker_id)

	session.close()
	return handler,userIds

def generateResult(handler,markmode):
	res_centers=[]
	res_labels=[]
	label_accuracy=[]

	if markmode==0:
		# square
		points=handler.allPoints
		res_centers=clu.cal_rec(clu.preprocess_data(points))
		res_labels,label_accuracy=generateTextLabel(handler.allTags)
		pass
	elif markmode==1:
		# area
		res_centers=[]
		res_labels,label_accuracy=generateTextLabel(handler.allTags)
		pass
	return res_centers,res_labels,label_accuracy

def generateTextLabel(labels):
	# remove [] in the labels
	while [] in labels:
		labels.remove([])
	# print(labels)
	names=[]
	values=[]
	for x in labels:
		for y in x:
			names.append(y[0])
			values.append(y[1])
	names=list(set(names))
	values=list(set(values))

	new_labels=[]
	for x in labels:
		nx=[]
		for y in x:
			nx.append([names.index(y[0]),values.index(y[1])])
		new_labels.append(nx)

	center=clu.cal_rec(clu.preprocess_data(new_labels))

	res=[]
	for x in center:
		res.append([names[int(x[0])],values[int(x[1])]])

	# calculate accuracy
	accuracy=[]
	for x in new_labels:
		accuracy.append(clu.cal_label_accuracy(x,res))

	return res,accuracy


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
	# coordinates=[[[0,0,5,10],[6,7,8,13]],[[0,1,6,7],[6,6,7,15]],[[1,0,9,8],[5,7,7,16]]\
	# 	,[[0,-1,4,10],[7,7,9,12]],[[-1,0,7,9],[6,8,10,10]],[[2,3,100,100],[9,10,100,100]]]
	# coordinates=clu.preprocess_data(coordinates)
	# coordinates,user_accuracy=clu.cal_rec(coordinates)
	# print(coordinates)
	# generateTag(coordinates,30,30)


	t=[[['性别', '雌'], ['种类', '耕牛'], ['肥瘦', '肥'], ['大小', '大']],\
	   [['性别', '雄'], ['种类', '母猪'], ['肥瘦', '肥'], ['大小', '大']], \
	   [['性别', '雄'], ['种类', '耕牛'], ['肥瘦', '瘦'], ['大小', '大']], \
	   [['性别', '雄'], ['种类', '耕牛'], ['肥瘦', '肥'], ['大小', '小']]]

	generateTextLabel(t)

	pass