import os
from numpy import *
import pandas as pd
import sys
import copy


def gain_subnet_coreimage(coreimage):           #获得sub-network的点集

    global j,subnet,nodes,subnet_set

    for i in range(len(image)):
        if (image[i][1] == coreimage):
            subnet[j][0] = image[i][0]
            subnet[j][1] = image[i][1]
            j = j + 1
            nodes.add(image[i][1])
            nodes.add(image[i][0])
    nodes = nodes - {coreimage}


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



subnet = [["1" for col in range(2)] for row in range(50000)]
sub_net_edges = [[1 for col in range(2)] for row in range(100)]
m = 0
for coreimage in coreimages:
    j = 0
    nodes = set()
    gain_subnet_coreimage(coreimage)
    add_subnet_coreimage(nodes)
    print(j)

    sub_net = [[1 for col in range(2)] for row in range(j)]      # 将多余的空的 子网络中的节点去掉
    for q in range(j):
        sub_net[q][0] = subnet[q][0]
        sub_net[q][1] = subnet[q][1]

    column = ['source', 'target']                      # 一个子网络中的节点和边生成一个文件
    test1 = pd.DataFrame(columns=column, data=sub_net)
    test1.to_csv('A://pythonproject2//subnet_coreimage//' + coreimage + '.csv',index=False)


    sub_net_edges[m][0] = coreimage               # 获取总的边数  累计
    sub_net_edges[m][1] = j
    m = m + 1


column = ['coreimage', 'edges']
test1 = pd.DataFrame(columns=column, data=sub_net_edges)
test1.to_csv('A://pythonproject2//sub_network_edges.csv',index=False)




