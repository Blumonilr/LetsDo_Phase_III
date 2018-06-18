import cv2
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity as cs
from matplotlib import pyplot as plt

def cal_similarity(tag1,tag2,width,height):
    img1=cv2.imdecode(np.fromstring(tag1.data,np.uint8),0)
    image1=cv2.resize(img1,(64,64),interpolation=cv2.INTER_AREA)
    greyimg1 = []
    for i in range(0, image1.shape[0]):
        for j in range(0, image1.shape[1]):
            greyimg1.append(image1[i, j])
    img2=cv2.imdecode(np.fromstring(tag2.data,np.uint8),0)
    image2 = cv2.resize(img2, (64, 64), interpolation=cv2.INTER_AREA)
    greyimg2 = []
    for i in range(0, image2.shape[0]):
        for j in range(0, image2.shape[1]):
            greyimg2.append(image2[i, j])
    return cs([greyimg1,greyimg2])[0][1]

# if __name__=="__main__":
#     img1=cv2.imread("test3.png",0)
#     bytearray1=bytearray(img1)
#     img1=np.array(bytearray1).reshape(img1.shape[0],img1.shape[1])
#     image1=cv2.resize(img1, (64, 64), interpolation=cv2.INTER_AREA)
#     greyimg1=[]
#     for i in range(0,image1.shape[0]):
#         for j in range(0,image1.shape[1]):
#             greyimg1.append(image1[i,j])
#     img2 = cv2.imread("test2.png", 0)
#     image2 = cv2.resize(img2, (64,64), interpolation=cv2.INTER_AREA)
#     greyimg2 = []
#     for i in range(0, image2.shape[0]):
#         for j in range(0, image2.shape[1]):
#             greyimg2.append(image2[i, j])
#
#     print(cs([greyimg1,greyimg2])[0][1])