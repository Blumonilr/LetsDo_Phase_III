import numpy as np
from sklearn.cluster import DBSCAN
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt

'''
receives a ndarray containing every rectangle's left upper coordinate

returns an array of classified cluster's coordinates 
'''


def cal_rec_center(coordinates, min_samples_=3):
	if type(coordinates) != np.ndarray:
		print("coordinates' type is not a ndarray!")
	else:
		#   first of all, remove the unusual points using DBSCA
		plt.scatter(coordinates[:, 0], coordinates[:, 1])
		clusters = DBSCAN(eps=0.18, min_samples=min_samples_).fit_predict(
			coordinates)  # cluster id for each point, -1 if eliminated
		n_clusters_ = len(set(clusters)) - (1 if -1 in clusters else 0)  # n clusters

		filtered_coordinates = []
		for i in range(len(clusters)):
			if clusters[i] > -1:
				filtered_coordinates.append(coordinates[i])
		#   transform filtered_coordinates from list to ndarray
		filtered_coordinates = np.ndarray(shape=(len(filtered_coordinates), 2), buffer=np.array(filtered_coordinates))
		plt.scatter(filtered_coordinates[:, 0], filtered_coordinates[:, 1])

		#   use KMeans to cluster
		center_coordinates = KMeans(n_clusters=n_clusters_).fit(filtered_coordinates).cluster_centers_
		plt.scatter(center_coordinates[:, 0], center_coordinates[:, 1])
		plt.show()
		return center_coordinates
	pass


def print_ndarray(arr):
	plt.scatter(arr[:, 0], arr[:, 1])
	plt.show()
	pass


def preprocess_data():
	pass

