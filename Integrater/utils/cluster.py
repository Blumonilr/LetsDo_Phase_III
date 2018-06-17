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
		clusters = DBSCAN(eps=1, min_samples=min_samples_).fit_predict(
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
		# print(filtered_coordinates)
		# print(filtered_coordinates)
		plt.scatter(filtered_coordinates[:, 0], filtered_coordinates[:, 1])

		#   use KMeans to cluster
		center_coordinates = KMeans(n_clusters=n_clusters_).fit(filtered_coordinates).cluster_centers_
		plt.scatter(center_coordinates[:, 0], center_coordinates[:, 1])
		plt.show()
		'''
		center_coordinates is a n*4 ndarray
		map every element of it to int
		'''
		center_coordinates=(lambda x:[ [round(j) for j in i] for i in x])(center_coordinates)
		return center_coordinates,cal_accuracy(coordinates,center_coordinates)

	pass

def cal_accuracy(user_ans,results):
	print(user_ans)
	print(type(user_ans))
	print(results)
	print(type(results))

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

