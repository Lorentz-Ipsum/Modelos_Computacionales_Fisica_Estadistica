# Objetivos:
# 1. Generar N numeros aleatorios con media Mu y varianza Sigma
# 2. Construir Y = X_1 + X_2 +...+ X_N
# 3. Calcular P_Y (y)
"""
import random

N = 10
X = []

for i in range(N):
    X.append(random.randrange(1, N) )

print(X)
"""

import math
from random import gauss

N = 100
media = 0
varianza = 10
t = 20
ye = []


import matplotlib.pyplot as plt
import matplotlib.animation as animation
from matplotlib import style

style.use('fivethirtyeight')

fig = plt.figure()
ax1 = fig.add_subplot(1,1,1)

def animate(i):
    xs = []
    ys = []
    for k in range(t):
        xs.append(k)
        ys.append(ye[k])
    ax1.clear()
    ax1.plot(xs, ys)

"""
def animate(i):
    graph_data = open('example.txt','r').read()
    lines = graph_data.split('\n')
    xs = []
    ys = []
    for line in lines:
        if len(line) > 1:
            x, y = line.split(',')
            xs.append(float(x))
            ys.append(float(y))
    ax1.clear()
    ax1.plot(xs, ys)
"""
for j in range(t):
    X = [gauss(media, math.sqrt(varianza)) for i in range(N)]

    Y = 0
    for i in range(N):
        Y = Y + X[i]
    #print(Y)
    ye.append(Y)

print(ye)

ani = animation.FuncAnimation(fig, animate, interval=1000)
plt.show()
