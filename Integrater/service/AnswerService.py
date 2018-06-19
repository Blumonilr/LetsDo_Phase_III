
import os
from matplotlib.path import Path
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import Integrater.utils.DBHandler as db
import Integrater.utils.xmlParser as xp
import Integrater.utils.cluster as clu
import Integrater.utils.cal_similarity as cs
import Integrater.utils.calculateProjectDif as pd

def workOne(userId,imageId,markmode):
	# get the answer
	session = db.setup_db()
	image = session.query(db.Image).filter(db.Image.id == imageId).one()
	tag = session.query(db.Tag).filter(db.Tag.image_id == imageId, db.Tag.is_result == True).one()
	usrTag=session.query(db.Tag).filter(db.Tag.image_id==imageId,db.Tag.worker_id==userId).one()
	# calculate accuracy and update the db
	print('already has result , calculate accuracy')
	print('update db')

	handler = xp.XMLParser()
	handler.parse(tag.xml_file)
	pointsAnswer = handler.allPoints
	print(pointsAnswer)
	labelAnswer = handler.allTags
	handler=xp.XMLParser()
	handler.parse(usrTag.xml_file)
	accuracy = []
	userIds=[]
	userIds.append(userId)
	efficiency = []
	projectId=-1
	if image.is_test:
		projectId = image.project_id
	else:
		id = session.query(db.Image).filter(db.Image.id == imageId).one().project_id
		projectId = session.query(db.Project).filter(db.Project.id == id).one().test_project_id
	dif = pd.calculateProjectDif(projectId)
	efficiency.append(handler.times[0]/dif)
	if markmode==0:
		usr_ans_rects=handler.allPoints
		labels=labelAnswer
		# print(labels)
		while [] in labels:
			labels.remove([])
		names = []
		values = []
		for x in labels:
			for y in x:
				names.append(y[0])
				values.append(y[1])
		names = list(set(names))
		values = list(set(values))
		new_labels = []
		for x in labels:
			nx = []
			for y in x:
				nx.append([names.index(y[0]), values.index(y[1])])
			new_labels.append(nx)
		labels=handler.allTags
		while [] in labels:
			labels.remove([])
		names = []
		values = []
		for x in labels:
			for y in x:
				names.append(y[0])
				values.append(y[1])
		names = list(set(names))
		values = list(set(values))
		usr_labels = []
		for x in labels:
			nx = []
			for y in x:
				nx.append([names.index(y[0]), values.index(y[1])])
			usr_labels.append(nx)
		acc = clu.cal_rect_accuracy(usr_ans_rects[0], pointsAnswer[0]) * 0.8 + clu.cal_label_accuracy(usr_labels[0],new_labels[0])
		accuracy.append(acc)
		updateAccuracyAndAbility(imageId, userIds, accuracy,efficiency)
		return [accuracy[0], efficiency[0]]
	elif markmode==1:
		usr_ans_rects = handler.allPoints
		labels = labelAnswer
		# print(labels)
		while [] in labels:
			labels.remove([])
		names = []
		values = []
		for x in labels:
			for y in x:
				names.append(y[0])
				values.append(y[1])
		names = list(set(names))
		values = list(set(values))
		new_labels = []
		for x in labels:
			nx = []
			for y in x:
				nx.append([names.index(y[0]), values.index(y[1])])
			new_labels.append(nx)
		labels = handler.allTags
		while [] in labels:
			labels.remove([])
		names = []
		values = []
		for x in labels:
			for y in x:
				names.append(y[0])
				values.append(y[1])
		names = list(set(names))
		values = list(set(values))
		usr_labels = []
		for x in labels:
			nx = []
			for y in x:
				nx.append([names.index(y[0]), values.index(y[1])])
			usr_labels.append(nx)
		acc = cs.cal_similarity(usrTag, tag,handler.pictureWidth,handler.pictureHeight) * 0.8 + clu.cal_label_accuracy(usr_labels[0],new_labels[0])
		accuracy.append(acc)
		updateAccuracyAndAbility(imageId, userIds, accuracy, efficiency)
		return [accuracy[0], efficiency[0]]
	pass

def work(imageId,markmode):
	print('imageID : ',imageId)
	print('markMode : ',markmode)
	session=db.setup_db()
	image=session.query(db.Image).filter(db.Image.id==imageId).one()
	# get workers' answers
	handler,userIds=getAnswerFromTags(imageId)
	usr_ans_rects=handler.allPoints
	usr_ans_tags=handler.allTags
	width=handler.pictureWidth
	height=handler.pictureHeight
	usr_ans_eff=handler.times
	efficiency=[]
	projectId=-1
	if image.is_test==True:
		projectId = session.query(db.Image).filter(db.Image.id == imageId).one().project_id
	else:
		id = session.query(db.Image).filter(db.Image.id == imageId).one().project_id
		projectId = session.query(db.Project).filter(db.Project.id == id).one().test_project_id
	dif = pd.calculateProjectDif(projectId)
	for time in usr_ans_eff:
		efficiency.append(time/(6-dif))
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
			updateAccuracyAndAbility(imageId,userIds,accuracy,efficiency)
			#generate tag object
			max_rect_accuracy=0.0
			ptr=-1
			for i in range(len(accuracy)):
				if accuracy[i]>max_rect_accuracy:
					max_rect_accuracy=accuracy[i]
					ptr=i
			if ptr!=-1:
				session.query(db.Tag).filter(db.Tag.worker_id==userIds[ptr],db.Tag.image_id==imageId)\
					.update({db.Tag.is_result:True})
			pass
		elif markmode==1:
			max_rect_accuracy = 0.0
			ptr = -1
			for i in range(len(label_accuracy)):
				if label_accuracy[i] > max_rect_accuracy:
					max_rect_accuracy = label_accuracy[i]
					ptr = i
			if ptr!=-1:
				session.query(db.Tag).filter(db.Tag.worker_id==userIds[ptr] , db.Tag.image_id==imageId).update({db.Tag.is_result:True})
			tags=session.query(db.Tag).filter(db.Tag.image_id==imageId).all()
			answerTag=db.Tag
			for tag in tags:
				if tag.worker_id==userIds[ptr]:
					answerTag=tag
					break
			accuracy=[]
			for j in range(0,len(tags)):
				acc=cs.cal_similarity(tags[j],answerTag,width,height)
				accuracy.append(acc*0.8+label_accuracy[j]*0.2)

			updateAccuracyAndAbility(imageId, userIds, accuracy,efficiency)
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


def updateAccuracyAndAbility(imageId,userIds,accuracy,efficiency):
	session=db.setup_db()
	commits=session.query(db.CommitEvent).filter(db.CommitEvent.imageid==imageId).all()
	for commit in commits:
		for i in range(len(userIds)):
			if commit.workerid==userIds[i]:
				# commit.accuracy=accuracy[i]
				acc=accuracy[i]
				uid=userIds[i]
				session.query(db.CommitEvent)\
					.filter(db.CommitEvent.workerid==uid , db.CommitEvent.imageid==imageId)\
					.update({"accuracy":float(acc),"efficiency":float(efficiency[i])})
	'''根据projectId获得项目对象的label
	转化为string列表
	'''
	projectId=session.query(db.Image).filter(db.Image.id==imageId).one().project_id
	labels=session.query(db.Project_Label).filter(db.Project_Label.project_id==projectId).all()
	labels=[x.labels for x in labels]

	for label in labels:
		for i in range(len(userIds)):
			abilities=session.query(db.Ability).filter(db.Ability.user_id ==userIds[i],db.Ability.label_name == label).all()
			for ability in abilities:
				if ability.label_name==label:
					total=ability.label_history_num*ability.accuracy+accuracy[i]
					eff=ability.efficiency*ability.label_history_num+efficiency[i]
					count=ability.label_history_num+1
					total=total/count
					eff=eff/count
					session.query(db.Ability).filter(db.Ability.user_id==userIds[i]
						,db.Ability.label_name==label).update({db.Ability.accuracy:float(total),
					                                              db.Ability.label_history_num:int(count),
																  db.Ability.efficiency:float(eff)})

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
	print(new_labels)
	center=clu.cal_text_rec(clu.preprocess_data(new_labels))
	res=[]
	for x in center:
		res.append([names[int(x[0])],values[int(x[1])]])
	# calculate accuracy
	accuracy=[]
	for x in new_labels:
		accuracy.append(clu.cal_label_accuracy(x,center))

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
	workOne(30,7,0)
	# session=db.setup_db()
	# session.query(db.CommitEvent).filter(db.CommitEvent.workerid==14 and db.CommitEvent.imageid==147).update({"accuracy":0.8736823349736977})
	# session.close
	pass