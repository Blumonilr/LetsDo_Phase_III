import xml.sax
import xml.etree.ElementTree as et
from numpy import long


@DeprecationWarning
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
		# if name=='root':
		# 	print("wuwu : ",self.allTags)
		pass

	def endElement(self, name):
		if name=='root':
			self.allColors.append(self.color)
			self.color = []
			self.allPoints.append(self.coordinate)
			self.coordinate=[]
			if self.tags!=[]:
				# print("haha : ",self.tags)
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
		elif self.currentTag=='title':
			self.title=content
		elif self.currentTag=='value':
			self.value=content
			tmptag=[self.title,self.value]
			self.tags.append(tmptag)





class XMLParser():
	def __init__(self):
		self.projectId = 0
		self.publisherId = 0
		self.pictureId = 0
		self.pictureWidth = 0
		self.pictureHeight = 0

		self.userId = []
		self.times = []
		self.clicks = []
		self.deletes = []
		self.coordinate = []  # 两层，记录单个worker的坐标，每个点的结构：[x,y,|dx|,|dy|]
		self.allPoints = []  # 三层列表，记录每个worker的每个方框的坐标
		self.tempCategory = []  # 1,
		self.categories = []  # 2,
		self.tags = []  # 2
		self.allTags = []  # 3
		self.color = []  # 1,int(r,g,b)
		self.allColors = []  # 2

		self.r = 0
		self.g = 0
		self.b = 0
		self.x1 = 0
		self.y1 = 0
		self.x2 = 0
		self.y2 = 0
		self.title = ''
		self.value = ''

		pass

	def parse(self,xml_content):
		self.tree = et.fromstring(xml_content)
		'''ElementTree 解析string时返回的就是root
		只有从文件中解析xml才要getroot()
		'''
		for son1 in self.tree:
			# print(son1.tag)
			if son1.tag == 'projectId':
				self.projectId = long(son1.text)
			elif son1.tag == 'publisherId':
				self.publisherId = long(son1.text)
			elif son1.tag == 'userId':
				self.userId.append(long(son1.text))
			elif son1.tag == 'pictureId':
				self.pictureId = long(son1.text)
			else:
				for son2 in son1:
					# print(son2.tag)
					if son2.tag == 'width':
						self.pictureWidth = int(son2.text)
					elif son2.tag=='height':
						self.pictureHeight=int(son2.text)
					elif son2.tag=='time':
						self.times.append(float(son2.text))
					elif son2.tag=='click':
						self.clicks.append(int(son2.text))
					elif son2.tag=='delete':
						self.deletes.append(int(son2.text))
					elif son2.tag=='points':
						pass
					else:
						for son3 in son2:
							# print(son3.tag)
							if son3.tag=='category':
								self.tempCategory.append(son3.text)
							elif son3.tag=='points':
								self.x1=int(son3.get('x1'))
								self.y1=int(son3.get('y1'))
								self.x2=int(son3.get('x2'))
								self.y2=int(son3.get('y2'))
								dx = self.x2 - self.x1
								dy = self.y2 - self.y1
								if dx < 0:
									self.x1 = self.x1 + dx
									dx = -dx
								if dy > 0:
									self.y1 = self.y1 + dy
								else:
									dy = -dy
								self.coordinate.append([self.x1, self.y1, dx, dy])

							else:
								for son4 in son3:
									# print(son4.tag)
									# if son4.tag=='x1':
									# 	self.x1=int(son4.text)
									# elif son4.tag=='y1':
									# 	self.y1=int(son4.text)
									# elif son4.tag == 'x2':
									# 	self.x2 = int(son4.text)
									# elif son4.tag == 'y2':
									# 	self.y2 = int(son4.text)
									if son4.tag=='R':
										self.r=int(son4.text)
									elif son4.tag=='G':
										self.g=int(son4.text)
									elif son4.tag == 'B':
										self.b = int(son4.text)
										rgb = int(self.b + (self.g << 8) + (self.r << 16))
										self.color.append(rgb)
									elif son4.tag=='tag':
										self.title=son4.get('title')
										self.value=son4.text
										tmptag = [self.title, self.value]
										self.tags.append(tmptag)
									# else:
									# 	for son5 in son4:
									# 		if son5.tag=='title':
									# 			self.title=son5.text
									# 		elif son5.tag=='value':
									# 			self.value = son5.text


		self.allColors.append(self.color)
		self.color = []
		self.allPoints.append(self.coordinate)
		self.coordinate = []
		self.allTags.append(self.tags)
		self.tags = []
		self.categories.append(self.tempCategory)
		self.tempCategory = []
		self.allTags.append(self.tags)
		self.tags = []

	'''tags is 2'''
	def setAllTags(self,tags):
		# print(len(tags))
		iter=self.tree.iter()
		for ele in iter:
			for tag in tags:
				if ele.tag=='tag' and ele.get('title')==tag[0]:
					ele.text=tag[1]



	def to_string(self):
		et.ElementTree(self.tree).write('tmp.xml',encoding='UTF-8')
		return open('tmp.xml','r',encoding='UTF-8').read()


if __name__=='__main__':
	handler=XMLParser()
	content=open('area.xml','r',encoding='UTF-8').read()


	handler.parse(content)
	# print(handler.allPoints)
	# for ele in handler.tree.iter():
	# 	print(ele.tag,' / ',ele.text)
	tags=[['肥瘦', '非常肥'], ['种类', '不牛'], ['大小', '不大'], ['性别', '不雄']]
	handler.setAllTags(tags)
	print(handler.to_string())


