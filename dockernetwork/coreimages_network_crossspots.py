# @Time    : 2020/8/10 17:19
# @Author  : yinyuanzhang@nudt.edu.cn


import os
from numpy import *
import pandas as pd
import sys
import copy


def gain_coreimages_linktimes(coreimage1, coreimage2, t):
    p = 0
    global linktimes, d_pairs
    subnet_coreimage1 = gain_subnet_coreimage(coreimage1)  # 调用获取子网络的集合
    subnet_coreimage2 = gain_subnet_coreimage(coreimage2)
    for i in range(len(image)):
        if (image[i][0] in subnet_coreimage1 and image[i][1] == coreimage2):
            p = p + 1

        if (image[i][0] in subnet_coreimage2 and image[i][1] == coreimage1):
            p = p + 1

    linktimes[t][0] = coreimage1
    linktimes[t][1] = coreimage2
    linktimes[t][2] = p
    linktimes[t][3] = len(subnet_coreimage1) + len(subnet_coreimage2)
    linktimes[t][4] = d_pairs[coreimage1]
    linktimes[t][5] = d_pairs[coreimage2]
    linktimes[t][6] = linktimes[t][4] + "-" + linktimes[t][5]
    print(linktimes[t][6])

def gain_subnet_coreimage(coreimage):  # 获得sub-network的点集

    global subnet, nodes
    nodes = set()
    nodes.add(coreimage)
    for i in range(len(image) - 1):
        if (image[i][1] == coreimage):
            nodes.add(image[i][1])
            nodes.add(image[i][0])
    return nodes


dataframe = pd.read_csv('A:\\pythonproject2\\pagerank_degree.csv')  # 读取core image 信息
pagerank = dataframe.values.tolist()
edges = pagerank[0:100]
coreimages = set()
for i in range(len(edges)):
    coreimages.add(edges[i][0])
print(coreimages)

dataframe2 = pd.read_csv('A:\\pythonproject2\\dockerfile_prase_notage.csv')
image = dataframe2.values.tolist()
print(len(image))

dataframe3 = pd.read_csv('A:\\pythonproject2\\key-value.csv')  # 读取core image 信息
imagepairs = dataframe3.values.tolist()
# print(imagepairs)
# print(type(imagepairs))

k = list()
v = list()
for i in range(len(imagepairs)):
    k.append(imagepairs[i][0])
    v.append(imagepairs[i][1])

d_pairs = dict(zip(k, v))
print(d_pairs)


j = 0
linktimes = [[1 for col in range(7)] for row in range(int(100 * 99 / 2))]
t = 0
for coreimage1 in coreimages:
    set1 = {coreimage1}
    print(set1)
    coreimages2 = coreimages - set1
    for coreimage2 in coreimages2:
        gain_coreimages_linktimes(coreimage1, coreimage2, t)  # t 代表第多少对关系
        t = t + 1
    coreimages = coreimages2

column = ['coreimage1', 'coreimage2', 'linktimes', 'nodes', 'image1type', 'image2type','type-pairs']
test = pd.DataFrame(columns=column, data=linktimes)
test.to_csv('A://pythonproject2//' + 'coreimages_network_crossspots.csv')