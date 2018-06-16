#coding=utf-8
import DBHandler
import numpy
import math

def calculateProjectDif(project_id):
    test_images=DBHandler.get_test_project_images(project_id)
    dif=[]
    for image in test_images:
        commits=DBHandler.get_image_commit(image.id)
        accuracies=[]
        for commit in commits:
            accuracies.append(commit.accuracy);
        array=numpy.array(accuracies)
        e=array.sum()/len(array)
        var = (array * array).sum() / len(array) + 1
        array.sort()
        median = array[len(array) // 2]
        if median < e:
            flag = -1
        else:
            flag = 1
            D = e / 0.2 + flag * 0.1 * (2 / (1 + math.e ** (-0.7 * (len(array) / var))) - 1)
        dif.append(D)
    return numpy.array(dif).sum()/len(len(dif))



if __name__=='__main__':
    accuracies=[0.11,0.22,0.2,0.3,0.2]
    array = numpy.array(accuracies)
    e = array.sum() / len(array)
    var = (array * array).sum() / len(array)+1
    array.sort()
    median=array[len(array)//2]
    print(e,var,median)
    if median<e:
        flag=-1
    else:
        flag=1
    D = e/0.2+flag*0.1*(2/(1+math.e**(-0.7*(len(array)/var)))-1)
    print(5-int(D))
