# Material Adicional

## Transformaciones de Arnold no Ergódicas

Cuando la matriz $T$ tiene sus autovalores complejos, la transformación de Arnold pierde sus propiedades de ergodicidad. Por ejemplo, la matriz:

(1)
$$
T=\left(\begin{array}{cc}
{1} & {-1} \\
{1} & {0}
\end{array}\right)
$$

de determinante 1, tiene un par de autovalores complejos conjugados:

$$
\lambda_{\pm}=\frac{1 \pm i \sqrt{3}}{2}=e^{\pm i \pi / 3}
$$

esto es, son rotaciones de $\pi / 3$, o de $60º$, de forma que al pulsar 6 veces sobre el botón **ITERA**, se recupera el dato inicial, sin deformaciones y sin, por supuesto, cubrir el espacio.

Otras matrices con autovalores complejos son las que tienen la forma:

(2)
$$
T=\left(\begin{array}{cc}
{\cos \phi} & {-\sin \phi} \\
{\sin \phi} & {\cos \phi}
\end{array}\right)
$$

que no es más que una rotación de ángulo $\phi$.

Existe otro tipo de matrices que son particularmente patológicas, aunque sus autovalores sean reales: aquellas que no son diagonalizables. Dos ejemplos:

(3)
$$
T=\left(\begin{array}{ll}
{2} & {0} \\
{1} & {\frac{1}{2}}
\end{array}\right), \quad T=\left(\begin{array}{ll}
{2} & {1} \\
{0} & {\frac{1}{2}}
\end{array}\right)
$$

Los autovalores de estas matrices son $\lambda_1 =2$ y $\lambda_2 = 1/2$ , pero debido a que no se pueden diagonalizar, muestran un comportamiento extraño.

¡Prueba el applet con estas matrices!
