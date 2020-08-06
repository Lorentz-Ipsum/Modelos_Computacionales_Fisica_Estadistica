# Experimento 3: Transformaciones del Panadero y de Arnold

Muchas propiedades del espacio de las fases $\Gamma$ pueden entenderse con modelos dinámicos sencillos, como los que introduciremos en este experimento. Son las llamadas *Transformación del Panadero (baker's transformation)* y *Transformación de Arnold (o Arnold's cat map)*. Ambas actúan sobre un espacio de fases bidimensional, transformando un punto $(x,y)$ sobre el cuadrado unidad, en un punto transformado $(x', y')$ con una aplicación que conserva la medida en el espacio de las fases $\Gamma$. Ambas transformaciones son ergódicas.

## Transformación del Panadero

Esta transformación actúa sobre la región $[0,1] \times [0,1]$, contrayendo la dirección $y$ en un factor 1/2 y expandiendo la dirección $x$ en un factor 2. A continuación, la región con $x >1$ se corta y se coloca en la parte superior del intervalo $[0,1]$. La transformación de un punto $(x,y)$ es:

$$
\begin{array}{l}
{x^{\prime}=2 x(\bmod 1)} \\
{y^{\prime}=\left\{\begin{array}{ll}
{y / 2} & {\text { si } x<1 / 2} \\
{y / 2+1 / 2} & {\text { si } x>1 / 2}
\end{array}\right.}
\end{array}
$$

Puede verificarse de manera sencilla que esta transformación conserva el área del espacio $\Gamma$.

El applet que incluimos aquí ilustra la transformación del panadero. Puede escogerse como dato incial para la iteración la cara de un gato, un círculo o una nube de puntos aleatorios en el cuadrado $[0, 0.3] \times [0, 0.3]$. Se elige el dato incial y se pulsa el botón **INICIA**. Para aplicar la transformación del Panadero, pulse el botón **ITERA**. Después de una cuantas iteraciones, los puntos que componen las figuras se han distribuido de manera uniforme sobre el espacio, llenándolo en su totalidad. Esta transformación es ergódica y mixing.

Cuando se pulsa el botón repetidas veces (unas 50 veces), se observa que los puntos ya no cubren el espacio de maera uniforme, sino que se forman bandas o regiones que evolucionan hasta el origen. Ello se debe a la [precisión finita](adicional/precision_finita.md) con la que se realizan los cálculos numéricos.

## Transformación de Arnold

La transformación de Arnold forma parte de una clase más complicada de transformaciones llamadas *automorfismo torales*. Son transformaciones lineales del cuadrado unidad en sí mismo con condiciones de contorno periódicas, definidas a través de una matriz $T$, que transforma el punto $(x, y)$ en el  $(x', y')$ según:

(1)
$$
\left(\begin{array}{l}
{x^{\prime}} \\
{y^{\prime}}
\end{array}\right)=T\left(\begin{array}{l}
{x} \\
{y}
\end{array}\right)=\left(\begin{array}{ll}
{t_{11}} & {t_{12}} \\
{t_{21}} & {t_{22}}
\end{array}\right)\left(\begin{array}{l}
{x} \\
{y}
\end{array}\right) \quad(\bmod 1)
$$

La matriz $T$ debe tener determinante 1, esto es, $\det (T) = t_{11} t_{22} - t_{12} t_{21} = 1$ que nos garantiza que dicha transformación conserve el área en el espacio $\Gamma$, dado que el determinante no es más que el Jacobiano de la transformación de $(x, y) \rightarrow (x', y')$. Para que la transformación (1) sea ergódica es necesario que los autovalores de $T$ sean reales y diferentes de 1. Para garantizarlo, se suele exigir que los elementos de $T$ sean enteros positivos. Bajo estas condiciones, los autovectores de $T$ son ortogonales, y existe un autovector mayor que 1 y otro menor que 1. La dirección asociada al autovalor mayor que 1 se expande y la asociada al menor que 1 se contrae. Ciertas matrices $T$ que no satisfacen estas condiciones dan comportamientos no ergódicos ([pulsa aquí](adicional/arnold_no_ergodicas.md).

Todo ello se observa en este Applet, similar en su funcionamiento al de la transformación del panadero. Aquí, sin embargo, se pueden elegir tres elementos de la matriz $T$ (el cuarto viene fijado por la condición $\det(T)=1$ ). Los valores $t_{11} = 2, t_{12} = 1$ y $t_{21} = 1$ (que implican $t_{22} = 1$) son los usuales que introdujo Arnold.

De nuevo puede observarse como los puntos se reparten uniformemente sobre el cuadrado $[0,1] \times [0,1]$ tras unas pocas iteraciones, aunque el número de las mismas depende de los valores de los elementos de la matriz $T$. De nuevo, tras un número elevado de iteraciones, los puntos colapsan al $(0,0)$ debido a redondeos causados por la precisión finita de los cálculos.

Bibliografía: J.R. Dorfman, An Introduction to Chaos in Nonequilibrium Statistical Mechanics, Cambridge Lecture Notes in Physics (1999).
