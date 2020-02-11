# Experimento 7: Vibraciones en sólidos cristalinos; Teoría de Debye

El estudio del calor específico en sólidos fue uno de los primeros éxitos de la teoría cuántica aplicada a sistemas macroscópicos. Más en concreto, permitió explicar las las desviaciones respecto a la ley de Dulong y Petit a bajas temperaturas que habían sido medidas experimentalmente.

Para estudiar la teoría de Debye, debemos plantear el hamiltoniano de las partículas que componen el sólido, que consta de la parte cinética y de la parte de energía potencial:

(1)
$$
H=\sum_{i} \frac{\mathbf{p}_{i}^{2}}{2 m}+\sum_{i<j} V\left(\left|\mathbf{r}_{i}-\mathbf{r}_{j}\right|\right) \equiv \sum_{i} \frac{\mathbf{p}_{i}^{2}}{2 m}+\Phi
$$

Como estamos interesados en el estudio del sólido, supondremos que los átomos no se desvían mucho de sus posiciones de equilibrio creadas por el potencial de interacción de los otros átomos. Así, el potencial total que afecta al átomo -ésimo es: , y la posición de equilibrio de dicho átomo es aquella que hace . Podemos desarrollar la energía de interacción en serie de Taylor alrededor de estas posiciones de equilibrio como:

(2)
$$
\Phi=\sum_{i<j} V\left(\left|\mathbf{r}_{i}^{0}-\mathbf{r}_{j}^{0}\right|\right)+\sum_{i<j} \frac{1}{2}\left(\mathbf{r}_{i}-\mathbf{r}_{i}^{0}\right) \frac{\partial^{2} V}{\partial \mathbf{r}_{i} \partial \mathbf{r}_{j}}\left(\mathbf{r}_{j}-\mathbf{r}_{j}^{0}\right)
$$

desarrollo en el que no aparecen las primeras devivadas del potencial donde las derivadas están sustituidas en las posiciones de equilibrio . Esta energía de interacción tiene forma cuadrática como la de un oscilador armónico, y por ello se llama aproximación armónica. Por el hecho de ser cuadrática en las variables posición, existe una transformación a otra base en la que la energía de interacción es diagonal, llamando a los elementos diagonales :

(3)
$$
\Phi=\sum_{i} \frac{1}{2} m \omega_{i}^{2} \xi_{i}^{2}
$$

A las coordenadas se les llama modos normales de vibración desde un punto de vista clásico o fonones desde el punto de vista cuántico. Esta transformación de las variables a las deja invariante la forma de la parte cinética del hamiltoniano. Así pues, el hamiltoniano en variables es:

(4)
$$
H=\sum_{i}\left(\frac{1}{2} m \dot{\xi}_{i}^{2}+\frac{1}{2} m \omega_{i}^{2} \xi_{i}^{2}\right)
$$

que es el hamiltoniano de un conjunto de osciladores armónicos de frecuencias . Los niveles cuánticos de cada uno de los osciladores es:

(5)
$$
\epsilon=\hbar \omega_{i}\left(n+\frac{1}{2}\right), n=0,1,2, \ldots
$$

El cálculo dela función de partición es ahora sencillo, dado que el hamiltoniano (4) se puede descomponer en una suma de 3 osciladores independientes:

(6)
$$
Z(T, N)=\prod_{i=1}^{3 N} Z\left(\omega_{i}, T\right)
$$

donde cada una de las funciones de partición es:

(7)
$$
Z\left(\omega_{i}, T\right)=\sum_{n=0}^{\infty} \exp \left[-\beta \hbar \omega_{i}(n+1 / 2)\right]=\frac{\exp \left(-\beta \hbar \omega_{i} / 2\right)}{1-\exp \left(-\beta \hbar \omega_{i}\right)}
$$

Podemos ahora calcular el logaritmo de la función de partición:

(8)
$$
\ln Z(T, N)=\sum_{i=1}^{3 N} \ln Z\left(\omega_{i}, T\right) \simeq \int d \omega g(\omega) \ln Z\left(\omega_{i}, T\right)
$$

donde hemos pasado de la suma sobre $\omega$ a una integral sobre las mismas. El factor $g(\omega) d \omega$ nos da el número de modos en (4) con frecuencias entre $\omega$ y $\omega+d \omega$. Debye supuso que $g(\omega)$ se
podía calcular suponiendo que la relación de dispersión que relaciona $\omega$ (frecuencia) con $k$ (vector de ondas) está dada por $\omega=\alpha,$ donde $c$ es la velocidad de propagación. Con esta hipótesis,

$$
g(\omega)=\frac{3 V}{2 \pi^{2} c^{3}} \omega^{2}
$$

y la frecuencia máxima se obtiene imponiendo que el número total de modos coincida con el de grados de libertad $(3 N)$:

$$
3 N=\int_{0} \omega_{D} \frac{3 V}{2 \pi^{2} c^{3}} \omega^{2} d \omega=\frac{V \omega_{D}^{3}}{2 \pi^{2} c^{2}} \Rightarrow \omega_{D}^{2}=\frac{6 N \pi^{2} c^{2}}{V}
$$

La frecuencia máxima, $\omega_{D}$ se denomina frcuencia de Debye.
Consecuentemente, tenemos que la función de partición es:

(9)
$$
\ln Z(T, N)=\int_{0}^{\omega_{D}} \frac{3 V \omega^{2}}{2 \pi^{2} c^{3}} \frac{\exp (-\beta \hbar \omega / 2)}{1-\exp (-\beta \hbar \omega)}
$$

A partir de esta expresión podemos calcular el calor especifico, derivando ln $Z$ respecto a $\beta$ para obtener la energía y posteriormente respecto a $T$ para obtener el calor especifico. Tras algunos cálculos obtenemos que:

(10)
$$
C_{V}=9 N k_{B}\left(\frac{T}{\Theta_{D}}\right)^{3} \int_{0}^{\Theta_{D} / T} d x \frac{x^{4} e^{x}}{\left(e^{x}-1\right)^{2}}
$$
donde $\boldsymbol{\theta}_{D}$ es la temperatura de Debye, definida a partir de la
frecuencia de Debye como: $\theta_{D} \equiv \hbar \omega_{D} / k_{B} .$ Es importante darse
cuenta de que el calor especifico depende de la temperatura a través del cociente $T / \Theta_{D}$

Los limites de altas y bajas temperaturas respecto a $\boldsymbol{\theta}_{D}$ de
(10) se pueden calcular fácilmente, con el resultado:

$$
C_{V} / N \simeq\left\{\begin{array}{ll}
{\frac{12}{5} \pi^{4} k_{B}\left(\frac{T}{\Theta_{D}}\right)^{3},} & {\text { si } T \ll \Theta_{D}} \\
{3 k_{B},} & {\text { si } T>\theta_{D}}
\end{array}\right.
$$

El valor de , independiente de la temperatura, de conoce como la ley de Dulong y Petit.

El applet que mostramos permite introducir la temperatura de Debye, y dibuja el calor específico por partícula, integrando la ecuación (10).

Las temperaturas de Debye para algunos sólidos son:

K 	Cu 	Al 	Fe 	B 	C(diamante) 	FLi 	ClNa

100 	315 	394 	420 	1250 	1860 	730 	321

Pulsa aquí para ver una gráfica con las medidas experimentales del calor específico para varias sustancias (aluminio, cobre, plata y plomo) frente a . En esta representación, es una función universal como puede verse en la ecuación (10).
