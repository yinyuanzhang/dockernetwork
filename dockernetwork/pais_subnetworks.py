# @Time    : 2020/8/15 10:17
# @Author  : yinyuanzhang@nudt.edu.cn

import os
from numpy import *
import pandas as pd
import sys
import copy




def gain_pairs_subnets(coreimage1,coreimage2):           #获得sub-network的点集

    global j,subnet,nodes,subnet_set

    for i in range(len(image)):
        if (image[i][1] == coreimage1 or image[i][1] == coreimage2):
            subnet[j][0] = image[i][0]
            subnet[j][1] = image[i][1]
            j = j + 1
            nodes.add(image[i][1])
            nodes.add(image[i][0])



def add_subnet_coreimage(nodes):               # 获取节点之间的边
    global j
    for i in range(len(image) - 1):
        if (image[i][1] in nodes and image[i][0] in nodes):
            subnet[j][0] = image[i][0]
            subnet[j][1] = image[i][1]
            j = j + 1






dataframe = pd.read_csv('A:\\pythonproject2\\dockerfile_prase_notage.csv')         # 读取所有镜像节点信息
print(dataframe)
print(type(dataframe))
image = dataframe.values.tolist()
print(type(image))
print(len(image[10]))




dataframe2 = pd.read_csv('A:\\pythonproject2\\pagerank_degree.csv')      # 读取core image 信息
pagerank = dataframe2.values.tolist()
edges = pagerank[0:100]
coreimages = set()
for i in range(len(edges)):
    coreimages.add(edges[i][0])
print(coreimages)






subnet = [["1" for col in range(2)] for row in range(100000)]

m = 0
j = 0


t = 0
for coreimage1 in coreimages:
    set1 = {coreimage1}
    print(set1)
    coreimages2 = coreimages - set1
    for coreimage2 in coreimages2:
        j = 0
        nodes = set()
        gain_pairs_subnets(coreimage1, coreimage2)  # t 代表第多少对关系
        add_subnet_coreimage(nodes)
        t = t + 1

        sub_net = [[1 for col in range(2)] for row in range(j)]  # 将多余的空的 子网络中的节点去掉
        for q in range(j):
            sub_net[q][0] = subnet[q][0]
            sub_net[q][1] = subnet[q][1]

        column = ['source', 'target']  # 一个子网络中的节点和边生成一个文件
        test = pd.DataFrame(columns=column, data=sub_net)
        test.to_csv('A://pythonproject2//pairs_subnetworks//' + coreimage1 + '_' + coreimage2 + '.csv', index=False)

    coreimages = coreimages2


