
from operator import itemgetter

import numpy as np
from sklearn.cluster import DBSCAN
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt

'''
receives a ndarray containing every rectangle's left upper coordinate

returns an array of classified cluster's coordinates 
'''


def cal_rec(coordinates,min_samples_=3):
	if type(coordinates) != np.ndarray:
		print("coordinates' type is not a ndarray!")
	else:
		#   first of all, remove the unusual points using DBSCA
		plt.scatter(coordinates[:, 0], coordinates[:, 1])
		clusters = DBSCAN(eps=20, min_samples=min_samples_).fit_predict(
			coordinates[:,[0,1]])  # cluster id for each point, -1 if eliminated
		n_clusters_ = len(set(clusters)) - (1 if -1 in clusters else 0)  # n clusters

		'''
		计算每个目标的平均大小
		后来发现KMeans就可以做这件事……
		
		# sizes=coordinates[:,[2,3]]
		# avg_sizes=[]
		# for i in range(n_clusters_):
		# 	# i stands for class id
		# 	sum=[0,0];count=0
		# 	for j in range(len(sizes)):
		# 		if clusters[j]==i:
		# 			sum[0]+=sizes[j][0]
		# 			sum[1]+=sizes[j][1]
		# 			count+=1
		#
		# 	sum[0]= int(round(sum[0]/count))
		# 	sum[1]=int(round(sum[1]/count))
		# 	avg_sizes.append(sum)
		'''

		filtered_coordinates = []

		for i in range(len(clusters)):
			if clusters[i] > -1:
				filtered_coordinates.append(coordinates[i])
		#   transform filtered_coordinates from list to ndarray
		filtered_coordinates = np.array(filtered_coordinates)
		# print(n_clusters_)
		# print(clusters)
		#print(filtered_coordinates)
		plt.scatter(filtered_coordinates[:, 0], filtered_coordinates[:, 1])

		#   use KMeans to cluster
		center_coordinates = KMeans(n_clusters=n_clusters_).fit(filtered_coordinates).cluster_centers_
		# plt.scatter(center_coordinates[:, 0], center_coordinates[:, 1])
		# plt.show()
		'''
		center_coordinates is a n*4 ndarray
		map every element of it to int
		'''
		center_coordinates=(lambda x:[ [round(j) for j in i] for i in x])(center_coordinates)
		return center_coordinates

	pass

def cal_rect_accuracy(user_ans,results):
	# print('ans : ',user_ans)
	# print('res : ',results)
	data=np.array(results)
	idex=np.lexsort([data[:,0]])
	sorted_data=data[idex,:]
	print(sorted_data)
	accuracy=[]
	for i in range(0,len(user_ans)):
		x1=user_ans[i][0]
		y1=user_ans[i][1]
		x2=user_ans[i][0]+user_ans[i][2]
		y2=user_ans[i][1]-user_ans[i][3]
		m1=sorted_data[i][0]
		n1=sorted_data[i][1]
		m2=sorted_data[i][0]+sorted_data[i][2]
		n2=sorted_data[i][1]-sorted_data[i][3]
		r1=(x1 if x1>m1 else m1)
		e1=(y1 if y1<n1 else n1)
		r2=(x2 if x2<m2 else m2)
		e2=(y2 if y2>n2 else n2)
		area0=sorted_data[i][2]*sorted_data[i][3]
		area2=(r2-r1)*(e1-e2)
		area1 = user_ans[i][2] * user_ans[i][3]-area2
		re=(((area2-area1*0.1)*1.0/area0) if ((area2-area1)*1.0/area0)>0 else 0)
		accuracy.append(re)
	return np.array(accuracy).sum()/len(accuracy)
	pass


def cal_label_accuracy(usr_ans,res):
	num=0
	for i in range(0,len(usr_ans)):
		if usr_ans[i][1]==res[0][i]:
			num=num+1
	return num*1.0/len(usr_ans)
	pass


'''
points is 3d array
change it to ndarray
'''
def preprocess_data(points):
	coordinates = []
	for user in points:
		for square in user:
			coordinates.append(square)
	# coordinates = np.ndarray(shape=(len(coordinates), 2), buffer=np.array(coordinates))
	coordinates=np.array(coordinates)
	return coordinates
	pass

if __name__=='__main__':
	cal_label_accuracy([[1,1],[2,3]],[[1,1],[2,3]])