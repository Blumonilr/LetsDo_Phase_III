import xml.sax

from numpy import long


class XmlParser(xml.sax.ContentHandler):
	def __init__(self):
		self.projectId=0
		self.publisherId=0
		self.pictureId=0
		self.pictureWidth=0
		self.pictureHeight=0

		self.userId=[]
		self.times=[]
		self.clicks=[]
		self.deletes=[]
		self.coordinate=[]  #两层，记录单个worker的坐标，每个点的结构：[x,y,|dx|,|dy|]
		self.allPoints=[]      #三层列表，记录每个worker的每个方框的坐标
		self.tempCategory=[]#1,
		self.categories=[]  #2,
		self.tags=[]        #2
		self.allTags=[]     #3
		self.color = []  # 1,int(r,g,b)
		self.allColors = []  # 2

		self.r = 0
		self.g = 0
		self.b = 0
		self.x1=0
		self.y1=0
		self.x2=0
		self.y2=0
		self.title=''
		self.value=''
		self.currentTag=""
		pass

	def startElement(self, name, attrs):
		self.currentTag=name
		if name=='root':
			print("wuwu : ",self.allTags)
		pass

	def endElement(self, name):
		if name=='root':
			self.allColors.append(self.color)
			self.color = []
			self.allPoints.append(self.coordinate)
			self.coordinate=[]
			if self.tags!=[]:
				print("haha : ",self.tags)
				self.allTags.append(self.tags)
			self.tags=[]
			self.categories.append(self.tempCategory)
			self.tempCategory=[]
			self.allTags.append(self.tags)
			self.tags=[]

		self.currentTag=''
		pass

	def characters(self, content):
		if self.currentTag=='projectId':
			self.projectId=long(content,10)
		elif self.currentTag=='publisherId':
			self.publisherId=long(content,10)
		elif self.currentTag=='userId':
			self.userId.append(long(content,10))
		elif self.currentTag=='pictureId':
			self.pictureId=long(content,10)
		elif self.currentTag=='width':
			self.pictureWidth=int(content,10)
		elif self.currentTag=='height':
			self.pictureHeight=int(content,10)
		elif self.currentTag=='time':
			self.times.append(float(content))
		elif self.currentTag=='click':
			self.clicks.append(int(content,10))
		elif self.currentTag=='delete':
			self.deletes.append(int(content,10))
		elif self.currentTag=='x1':
			self.x1=int(content)
		elif self.currentTag=='y1':
			self.y1=int(content)
		elif self.currentTag=='x2':
			self.x2=int(content)
		elif self.currentTag=='y2':
			dx=self.x2-self.x1
			dy=self.y2-self.y1
			if dx<0:
				self.x1=self.x1+dx
				dx=-dx
			if dy>0:
				self.y1=self.y1+dy
			else:
				dy=-dy
			# self.size.append([dx,dy])
			self.coordinate.append([self.x1,self.y1,dx,dy])
		elif self.currentTag=='category':
			self.tempCategory.append(content)
		elif self.currentTag=='title':
			self.title=content
		elif self.currentTag=='value':
			self.value=content
			self.tags.append([self.title,self.value])
		elif self.currentTag=='R':
			self.r=int(content)
			# print('r = ',self.r)
		elif self.currentTag=='G':
			self.g=int(content)
			# print('g = ',self.g)
		elif self.currentTag=='B':
			self.b=int(content)
			# print('b = ',self.b)
			rgb=int(self.b+(self.g<<8)+(self.r<<16))
			self.color.append(rgb)





class AreaParser(xml.sax.ContentHandler):
	def __init__(self):
		self.projectId=0
		self.publisherId=0
		self.pictureId=0
		self.pictureWidth=0
		self.pictureHeight=0

		self.userId=[]
		self.times=[]
		self.clicks=[]
		self.deletes=[]
		self.size=[]       #2层，记录每个worker的每个方框的大小
		self.allSizes=[]    #3
		self.tempCategory=[]#1,
		self.categories=[]  #2,
		self.tags=[]        #2
		self.allTags=[]     #3
		self.color=[]       #1,int(r,g,b)
		self.allColors=[]   #2

		self.r=0
		self.g=0
		self.b=0
		self.title=''
		self.value=''
		self.currentTag=""
		pass

	def startElement(self, name, attrs):
		self.currentTag=name
		pass

	def endElement(self, name):
		if name=='root':
			self.allColors.append(self.color)
			self.color=[]
			self.allSizes.append(self.size)
			self.size=[]
			self.allTags.append(self.tags)
			self.tags=[]
			self.categories.append(self.tempCategory)
			self.tempCategory=[]
			if len(self.tags)>0:
				self.allTags.append(self.tags)
			self.tags=[]

		self.currentTag=''
		pass

	def characters(self, content):
		if self.currentTag=='projectId':
			self.projectId=long(content,10)
		elif self.currentTag=='publisherId':
			self.publisherId=long(content,10)
		elif self.currentTag=='userId':
			self.userId.append(long(content,10))
		elif self.currentTag=='pictureId':
			self.pictureId=long(content,10)
		elif self.currentTag=='width':
			self.pictureWidth=int(content,10)
		elif self.currentTag=='height':
			self.pictureHeight=int(content,10)
		elif self.currentTag=='time':
			self.times.append(float(content))
		elif self.currentTag=='click':
			self.clicks.append(int(content,10))
		elif self.currentTag=='delete':
			self.deletes.append(int(content,10))
		elif self.currentTag=='R':
			self.r=int(content)
			# print('r = ',self.r)
		elif self.currentTag=='G':
			self.g=int(content)
			# print('g = ',self.g)
		elif self.currentTag=='B':
			self.b=int(content)
			# print('b = ',self.b)
			rgb=int(self.b+(self.g<<8)+(self.r<<16))
			self.color.append(rgb)
		elif self.currentTag=='category':
			self.tempCategory.append(content)
		elif self.currentTag=='title':
			self.title=content
		elif self.currentTag=='value':
			self.value=content
			self.tags.append([self.title,self.value])


if __name__=='__main__':
	parser=xml.sax.make_parser()
	parser.setFeature(xml.sax.handler.feature_namespaces,0)
	handler=XmlParser()
	parser.setContentHandler(handler)

	parser.parse('area.xml')
	# print(handler.allTags)
	# print(handler.allSizes)
	print(handler.allColors)
	# print(handler.categories)
	# print(handler.allColors)