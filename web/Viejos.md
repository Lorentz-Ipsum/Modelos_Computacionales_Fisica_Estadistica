---
layout: post
title: Applets originales
print_background: true
export_on_save:
  html: true
---

# Todos los applets originales juntos.

[TOC]

# Experimento 1: Teorema del límite central

Supongamos que se genera un número grande $N$ de variables aleatorias independientes $X_i, i=1,...,N$, todas distribuidas con las misma distribución de probabilidad, de media $\mu$ y varianza $\sigma^2$. El Teorema del Límite Central nos dice que la variable aleatoria $Y$, construida como la suma de las anteriores:

(1)
$$
Y=X_1 + X_2 +...+X_N
$$

se distribuye de forma gaussiana de media $N_\mu$ y de varianza $\sigma^2 /N$, independientemente de la distribución de las variables $X_i$. Entonces, la probabilidad de que la variable aleatoria $Y$ tome el valor $y$ viene dada por:

(2)
$$
P_{Y}(y)=\frac{1}{\sqrt{2 \pi N \sigma^{2}}} \exp \left[-\frac{(y-N \mu)^{2}}{2 N \sigma^{2}}\right]
$$

Este teorema no deja de ser sorprendente: dice que *sea cual sea* la distribución con la que generamos ciertas variables aleatorias, su suma **siempre es gaussiana**, y tanto más estrecha cuantas más variables sumemos. Es por este teorema por el cual la distribución gaussiana juega un papel tan importante en Física: el efecto cooperativo de muchas factores aleatorios da como resultado una distribución gaussiana.

Para ilustrarlo, hemos creado un applet Java en el cual se generan variables aleatorias, cuyo número se puede elegir, distribuidas de tres formas posibles:
1) Uniformemente entre 1, 2, 3, 4, 5 ó 6 (como si fueran dados)
2) Uniformemente en el intervalo real [0,1]
3) Uniformemente entre 0 ó 1

Pulsando el botón **INICIA** se comienzan a generar variables $Y=\sum_i X_i$ y se construye el histograma de frecuencias de la variable $Y$ normalizado a la unidad, esto es, $P_Y (y)$, representado en color rojo. A la vez se dibuja en azul la distribución predicha por el teorema del límite central, ecuación (2).

Es fácil ver que para $N \gg 1$ la distribución de la variable $Y$ se acerca a la distribución gaussiana dada por la ecuación (2), sea cual sea la distribución de $X_i$. No sucede así, lógicamente, si el número de variables, $N$, es pequeño.

Este applet está inspirado en uno creado por Todd Ogden, de la Universidad de South Carolina.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/01-limite_central.mp4" type="video/mp4">
</video>

# Experimento 2: Anillo de Kac

El anillo de Kac es un excelente modelo de un sistema dinámico que ilustra aspectos como el tiempo de recurrencia de Poincaré, las hipótesis estadísticas y, en general, cómo un sistema que es reversible puede mostrar comportamiento irreversible. Fue propuesto por Marc Kac en conexión con la ecuación de Boltzmann.

El modelo consta de un anillo en el que hay $N$ casillas, ocupadas cada una de ellas por una bola de color azul o rojo (normalmente $N \gg 1$). Sobre el anillo se disponen $M$ sitios al azar (con $M\leq N$). La dinámica es muy sencilla: de forma síncrona, a cada unidad de tiempo, las bolas saltan a la casilla contígua en el sentido de las agujas del reloj. Si una bola cruza uno de los $M$ sitios elegidos, cambia su color.

La cuestión que nos interesa es: ¿Cuántas bolas de cada color hay tras $t$ pasos de tiempo?

La dinámica de este modelo es reversible y periódica de periodo (al menos) $T=2N$, porque tras $T$ pasos de tiempo cada partícula ha dado dos vueltas al anillo, ha cruzado por $2M$ sitios marcados y ha sufrido $2M$ cambios de color, que por ser un número par implica que ha recuperado su color inicial. Por tanto, el tiempo de recurrencia de Poincaré es $2N$.

Vamos a escribir unas ecuaciones del movimiento para este sistema. Para ello, denotamos por $N_A (t)$ y $N_R (t)$ el número total de bolas azules y rojas en el tiempo $t$ respectivamente. Asimismo, definimos $n_A (t)$ y $n_R (t)$ como el número total de bolas azules y rojas que tienen delante un sitio marcado y que, por tanto, van a cambiar de color. Con estas definiciones, es fácil ver que las ecuaciones de evolución se pueden escribir como:

(1)
$$
N_A (t+1) = N_A(t) - n_A (t) + n_R (t)
$$

$$
N_R (t+1) = N_R(t) - n_R (t) + n_A (t)
$$

Estas ecuaciones no se pueden resolver de manera general. Sin embargo, podemos hacer una hipótesis muy sencilla, que supone que la probabilidad de que una partícula encuentre un sitio marcado es el número total de sitios dividido por el número de casillas:

(2)
$$
\frac{n_A(t)}{N_A(t)} = \frac{n_R(t)}{N_R(t)} = \frac{N}{M}
$$

Con esta hipótesis podemos resolver las ecuaciones (1), sin más que restarlas entre sí e introduciendo (2) para eliminar $n_A(t)$ y $n_R(t)$:

$$
\begin{aligned}
N_{A}(t)-N_{R}(t) &=\left(1-2 \frac{M}{N}\right)\left[N_{A}(t-1)-N_{R}(t-1)\right] \\
&=\left(1-2 \frac{M}{N}\right)^{t}\left[N_{A}(0)-N_{R}(0)\right]
\end{aligned}
$$

El análisis de esta solución nos dice que la diferencia entre el número de bolas azules y rojas tiende a 0 *siempre*, y, por tanto, el sistema no es periódico ni reversible, en clara contradicción con el modelo de partida.

La discrepancia proviene de la hipótesis probabilística realizada en (2). Esta hipótesis, que hemos supuesto *válida para todo tiempo* elimina las correlaciones que se crean en el sistema. En el lenguaje de la ecuación de Boltzmann, la hipótesis (2) es equivalente a la hipótesis del Caos Molecular o *Stosszahlansatz*.

El applet Java implementa el anillo de Kac. En en panel de la izquierda se visualiza el anillo, con los sitios marcados representados por una línea verde. El anillo exterior mantiene la condición inicial, mientras que el interior muestra la evolución temporal. El panel de la derecha dibuja la diferencia entre el número de bolas azules y el de bolas rojas en función del tiempo. Se puede elegir el número total de bolas, el número de sitios marcados o sectores, así como la disposición de los colores de las bolas entre aleatorios, aleatorios con una distribución de 70% azules y 30% rojas, todos azules o bien de manera alternada.

En el caso de condiciones iniciales con 70/30 o todos azules se dibuja una línea azul que es la solución (3), que, como se observa, es válida únicamente a tiempos muy cortos. Con ello se comprueba que la hipótesis (2) no es válida a todo tiempo, sino únicamente a tiempos cortos. La dinámica de las partículas crea correlaciones que invalidan dicha hipótesis.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/02-kac.mp4" type="video/mp4">
</video>

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

Cuando se pulsa el botón repetidas veces (unas 50 veces), se observa que los puntos ya no cubren el espacio de maera uniforme, sino que se forman bandas o regiones que evolucionan hasta el origen. Ello se debe a la [precisión finita](#precisión-finita) con la que se realizan los cálculos numéricos.

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

La matriz $T$ debe tener determinante 1, esto es, $\det (T) = t_{11} t_{22} - t_{12} t_{21} = 1$ que nos garantiza que dicha transformación conserve el área en el espacio $\Gamma$, dado que el determinante no es más que el Jacobiano de la transformación de $(x, y) \rightarrow (x', y')$. Para que la transformación (1) sea ergódica es necesario que los autovalores de $T$ sean reales y diferentes de 1. Para garantizarlo, se suele exigir que los elementos de $T$ sean enteros positivos. Bajo estas condiciones, los autovectores de $T$ son ortogonales, y existe un autovector mayor que 1 y otro menor que 1. La dirección asociada al autovalor mayor que 1 se expande y la asociada al menor que 1 se contrae. Ciertas matrices $T$ que no satisfacen estas condiciones dan comportamientos no ergódicos ([pulsa aquí](#transformaciones-de-arnold-no-ergódicas)).

Todo ello se observa en este Applet, similar en su funcionamiento al de la transformación del panadero. Aquí, sin embargo, se pueden elegir tres elementos de la matriz $T$ (el cuarto viene fijado por la condición $\det(T)=1$ ). Los valores $t_{11} = 2, t_{12} = 1$ y $t_{21} = 1$ (que implican $t_{22} = 1$) son los usuales que introdujo Arnold.

De nuevo puede observarse como los puntos se reparten uniformemente sobre el cuadrado $[0,1] \times [0,1]$ tras unas pocas iteraciones, aunque el número de las mismas depende de los valores de los elementos de la matriz $T$. De nuevo, tras un número elevado de iteraciones, los puntos colapsan al $(0,0)$ debido a redondeos causados por la precisión finita de los cálculos.

Bibliografía: J.R. Dorfman, An Introduction to Chaos in Nonequilibrium Statistical Mechanics, Cambridge Lecture Notes in Physics (1999).

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/03-transformaciones.mp4" type="video/mp4">
</video>

# Experimento 4: Ergodicidad y Entropía en un conjunto de osciladores

Consideremos un conjunto de $N$ osciladores independientes de frecuencias $\omega_i$, $i = 1,2,...,N$, definidas por las variables ángulo $\phi_i (t) \in [0,2\pi)$, que evolucionan de la forma $\phi_i (t) = \phi_i (0) + \omega_i t$. El estado del sistema se puede representar por $N$ puntos sobre la circunferencia de radio unidad.

Como es lógico, la evolución de estos osciladores viene dado en buena medida por la elección de las frecuencias $\omega_i$ y de las fases iniciales $\phi_i (0)$.

Se puede demostrar que si las frecuencias son inconmensurables, esto es, si $\omega_i / \omega_j$ es irracional para todo $i, j$, entonces el sistema es ergódico y su distribución de probabilidad microcanónica es por tanto:

$$
\rho (x) = \frac{1}{(2\pi)^N}
$$

que no es más que el volumen del toro $N$-dimensional donde se mueven las variables $\phi$.

Dividamos ahora la circunferencia en $M$ sectores, definiendo el conjunto $\{\alpha_j\}^M_{j=1}$ como la fracción de osciladores en el sector $j$-ésimo, que en el lenguaje de Física Estadística no es más que un macroestado. Se puede comprobar que la entropía de dicho macroestado es:

(1)
$$
S=-N k_{B} \sum_{j} \alpha_{j} \ln \alpha_{j}
$$

válida sin $N \gg 1, M \gg 1$ y $N \gg M$.

La entropía máxima (que corresponde a la situación de equilibrio), corresponde a $\alpha_j = 1/M$ para todo $j$, que conduce a

$$
S_{\mathrm{máx}}=N k_{B} \ln M
$$

La Física Estadística nos dice que si el sistema es ergódico debe alcanzar este estado de entropía máximo. Por el contrario, si no hay ergodicidad, el sistema puede no ir al estado de entropía máxima, sino convertirse en periódico y su entropía puede crecer o decrecer.

Todo ello se ilustra en el applet que mostramos a continuación. Dicho applet realiza la simulación de los osciladores en los que se pueden elegir las frecuencias, $\omega_i$ de manera:

1. aleatorias, con lo que $\omega_i / \omega_j$ es, en buena aproximación inconmensurable.
2. casi iguales, que son aleatorias pero próximas entre sí
3. equiespaciadas, por lo que $\omega_i / \omega_j = i/j$ y, por tanto, conmensurables y no ergódico.

También se pueden elegir las fases, o bien todas iguales (los osciladores comienzan en el mismo punto), o bien aleatorias en el círculo. Finalmente, podemos elegir el número de osciladores $N$ y el número de sectores $M$.

Al pinchar el botón **INICIA** comienza la simulación, que dibuja en el panel izquierdo la configuración instantánea de los osciladores como líneas azules. En el panel derecho, se calcula la entropía según la fórmula (1), dividida por $S_{máx}$, de forma que el estado de máxima entropía corresponde al valor 1.

El botón **HISTOGRAM** calcula el histograma de la distribución del logaritmo de las frecuencias, mostrando que se comportan de manera exponencial, según la fórmula de Einstein $S = \exp(S/k_b)$.

Puede comprobarse cómo para frecuencias inciales aleatorias y fases nulas, la entropía crece rápidamente desde un estado de $S=0$ hasta un valor máximo y ya nunca decrece salvo pequeñas fluctuaciones, que son más pequeñas cuanto mayor es el número de partículas. Por el contrario, cuando las frecuencias son conmensurables, la entropía comienza creciendo, pero tras unos instantes de tiempo comienza a decrecer hasta su valor inicial.

Si partimos de un estado de entropía máxima eligiendo las fases de manera aleatoria, es muy difícil que la entropía decrezca más allá del nivel de las fluctuaciones, sean como sean las frecuencias. Ello confirma una de las ideas de Boltzmann acerca de la irreversibilidad, que afirma que vemos evolución temporal a estados de desorden porque partimos de sistemas muy ordenados.

Applet y texto elaborado sobre una idea original y material de [Juan M.R. Parrondo](http://valbuena.fis.ucm.es/expint/html/frame.html).

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/04-osciladores.mp4" type="video/mp4">
</video>

# Experimento 5: Dimensionalidad y Límite termodinámico en el Modelo de Ising

Este experimento quiere ilustrar de manera muy sencilla dos conceptos muy importantes en Física. Uno de ellos, muy general, es la *dimensionalidad*. El otro, muy relevante en Física Estadística, es el de *Límite Termodinámico*.

Para ello, tomamos el modelo de Ising, cuyo hamiltoniano, viene dado por:

(1)
$$
H\left(\left\{s_{i}\right\}\right)=-J \sum_{\langle i, j\rangle} s_{i} s_{j}
$$

donde $s_i$ es la variable de espín (+1 ó -1) del sitio $i$-ésimo y el símbolo $\langle i, j \rangle$ denota próximos vecinos. La variable $J$ es la energía de interacción.

Este modelo ha sido muy bien estudiado porque es uno de los pocos que admite una solución exacta que presenta una transición de fase. Una transición de fase se produce cuando en un valor de la temperatura u otro parámetro, alguno de los potenciales termodinámico del sistema es no analítico. Ello tiene como consecuencia que alguna magnitud medible (el calor específico en nuestro caso) es discontínua, divergente, etc.

En el modelo de Ising existe una transición entre una fase desordenada o paramagnética a altas temperaturas y una fase ordenada o ferromagnética a bajas temperaturas. La transición se produce a la *temperatura crítica* $T_C$.

La **dimensionalidad** juega un papel muy relevante: la transición no existe en sistemas unidimensionales sino sólo en dimensión 2 y superiores.

El **Límite Termodinámico** consiste en tomar un límite en el que el número de grados de libertad en el sistema tiende a infinito, pero manteniendo la densidad constante. El límite termodinámico es necesario para que exista una no analiticidad, porque si sumamos un número finito de términos analíticos para calcular la función de partición ésta es necesariamente analítica. Sólo cuando sumamos infinitos términos podemos tener una función no analítica.

Nuestra propuesta para ilustrar ambos conceptos se basa en el estudio del *calor específico* de un modelo de Ising calculado de manera **exacta** en redes de tamaño $N_x \times N_y$. La variación del tamaño y forma de las redes a través de $N_x$ y$N_y$ permite jugar con dichos aspectos. Cuando, por ejemplo, $N_x=1$ tenemos una red unidimensional. Si $N_x$ y $N_y$ son diferentes de 1, la red es bidimensional, tanto más cuanto más cercanos estén $N_x$ y $N_y$. Aumentando tanto $N_x$ como $N_y$ , nos acercaremos al límite termodinámico. Las redes más grandes que el applet permite calcular son aquellas en las que $N_xN_y\leq 25$.

El applet permite introducir el tamaño de la red a través de los números $N_x$ y $N_y$. Pulsando el botón **CALCULAR**, dibuja el calor específico por partícula $C_V$. El applet dibuja, además los calores específicos exactos de las redes 1 y 2 dimensionales infinitas. Como se observa en estas últimas, el calor específico unidimensional es un función analítica, mientras que el bidimensional tiene una divergencia en $T_C \approx 2.269$. Las unidades de simulación son $J/k_B = 1$.

Sugerimos hacer varias experiencias:
1. calcular todas las redes cuadradas, desde $2 \times 2$ hasta $5 \times 5$, para ver cómo el máximo de $c_V$ se acerca a $T_C$ (aunque $c_V$ es continuo, finito y analítico en todas ellas),
2. Redes con un número similar de espines, como por ejemplo $1 \times 24$, $2 \times 12$, $3 \times 8$, $4 \times 6$ y $5 \times 5$, para observar cómo se pasa de un comportamiento tipo unidimensional a otro tipo bidimensional,
3. redes con $N_x$ fijo y $N_y$ creciente.

[Pincha aquí](#experimento-11-modelo-de-ising) para ver otro experimento relacionado con el modelo de Ising bidimensional que incluye el cálculo [Monte Carlo](#método-de-monte-carlo) de la magnetización.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/05-dimensionalidad.mp4" type="video/mp4">
</video>

# Experimento 6: Moléculas Diatómicas
El objeto de esta práctica es el estudio de las propiedades de un gas de moléculas diatómicas, cuyo hamiltomiano para el par de átomos (1 y 2) que componen la molécula es:

(1)
$$
H_{12}=\frac{\mathbf{p}_{1}^{2}}{2 m}+\frac{\mathbf{p}_{2}^{2}}{2 m}+V\left(\left|\mathbf{r}_{1}-\mathbf{r}_{2}\right|\right)
$$

Este Hamiltoniano puede separarse en dos partes: la parte debida al movimiento del centro de masas de la molécula y la parte debida al movimiento de los átomos respecto al centro de masas. Para ello utilizamos la transformación a coordenadas relativas:

(2)
$$
P = p_1 + p_2; \quad p = p_1 - p_2; \quad r = |r_1 - r_2|
$$

con lo que el hamiltoniano (1) se reescribe como:

(3)
$$
\begin{array}{l}
H_{12}&=\frac{\mathbf{P}^{2}}{2 M}+\frac{\mathbf{L}^{2}}{2 \mu r^{2}}+{\frac{p^{2}}{2 \mu}+V(r)} \\
H_{12}&=H_{T}+H_{rot}+H_{vib}
\end{array}
$$

donde $M = 2m$ es la masa total de la molécula y $\mu = m/2$ es la masa reducida. Así que para la función de partición de una molécula tenemos que:

$$
Z_{12} = Z_T \cdot Z_{rot} \cdot Z_{vib}
$$

y la de $N$ moléculas se puede escribir como:

$$
Z(\beta, N, V) = \frac{1}{N!} Z_{12}^N
$$

A partir de esta función de partición se calcula el calor específico por partícula como:

$$
c_{v}=\frac{1}{N} \frac{\partial E}{\partial T} ; \quad E=\frac{1}{\beta} \frac{\partial \ln Z}{\partial \beta}=\frac{1}{\beta}\left(\frac{\partial \ln Z_{T}}{\partial \beta}+\frac{\partial \ln Z_{r o t}}{\partial \beta}+\frac{\partial \ln Z_{vib}}{\partial \beta}\right)
$$

donde las derivadas parciales que aparecen se deben tomar a $V$ y $N$ constante.

Pasemos a calcular las tres funciones de partición que aparecen. La función de partición traslacional $Z_T$ es la de una partícula libre en 3 dimensiones:

$$
Z_{T}=\frac{h^{3}}{\Lambda^{3}}, \quad \Lambda^{3}=\sqrt{2 \pi M k_{B} T}
$$

que da una contribución al calor específico:

$$
c_v = \frac{3}{2} k_B
$$

### Vibración

La parte vibracional $Z_{vib}$ incluye el potencial de interacción $V(r)$ interatómico. Si suponemos que los átomos vibran alrededor de las posición de equilibrio $r_0$ con pequeña amplitud (lo que hemos asumido implícitamente en (3)), podemos desarrollar el potencial en serie de potencias alrededor de $r = r_0$, obteniendo:

$$
V(r) \simeq V\left(r_{0}\right)+\frac{d V}{d r}\left(r-r_{0}\right)+\frac{1}{2} \frac{d^{2} V}{d r^{2}}\left(r-r_{0}\right)^{2}+\cdots
$$

donde el término en derivada primera, $dV/dr$ es nulo porque está evaluada en $r_0$, que es la posición de equilibrio en la que $V(r)$ es mínimo. Así pues, el potencial $V$ se puede aproximar por un potencial armónico de frecuencia $\omega^2= \frac{d^2 V}{dr^2}\frac{1}{\mu}$. Por tanto, el hamiltoniano de rotación se reduce a:

$$
H_{r o t}=\frac{p^{2}}{2 \mu}+\frac{1}{2} \mu \omega^{2}\left(r-r_{0}\right)^{2}
$$

cuyos niveles de energía cuantizados según:

$$
\epsilon_{vib}=\left(n+\frac{1}{2}\right) \hbar \omega,\quad n=0,1,2, \cdots
$$

y la función de partición (que se puede calcular):

(4)
$$
Z_{vib}=\sum_{n=0}^{\infty} \exp \left(-\beta \hbar \omega\left(n+\frac{1}{2}\right)\right)=\frac{\exp \left(\Theta_{V} / 2 T\right)}{1-\exp \left(\Theta_{V} / T\right)}
$$

donde hemos definido la *temperatura característica de vibración* como:

$$
\Theta_V \equiv \frac{\hbar \omega}{k_B}
$$

que suele ser del orden de cientos a miles de kelvins. Las contribuciones al calor específico son:

$$
c_{v}=\left\{\begin{array}{ll}
k_{B}\left(\Theta_{V} / T\right)^{2} e^{\Theta_{V} / T} & \text { si } T \ll \Theta_{V} \\
k_{B} & \text { si } T \gg \Theta_{V}
\end{array}\right.
$$


### Rotación

La parte rotacional, $Z_{rot}$ incluye el operador momento angular $L^2$, cuyas autofunciones son los armónicos esféricos, $Y_m^l$ con ecuación de autovalores: $\epsilon_{rot} = l(l+1) \hbar^2 / 2 \mu r_0^2, l = 0,1,2, \cdots$, con degeneración $g(\epsilon) = 2l+1$. Entonces la función de partición es:

(5)
$$
Z_{r o t}=\sum_{l=0}^{\infty}(2 l+1) \exp \left(-l(l+1) \frac{\beta \hbar^{2}}{2 \mu r_{0}^{2}}\right)
$$

de donde podemos definir la *temperatura característica de rotación* como:

$$
\Theta_R \equiv \frac{\hbar^2}{2k_B\mu r_0^2}
$$

que varía entre décimas de kelvin a unos pocos kelvins (excepto el hidrógeno). Para temperaturas bajas comparadas con $\Theta_R$, $T\ll \Theta_R$, podemos calcular $Z_{rot} porque sólo los primeros términos de la serie (5) son importantes. Por el contrario, a temperaturas altas, $T\gg \Theta_R$, todos los términos en (5) deben ser sumados.

En estos límites las contribuciones al calor específico pueden ser calculadas, obteniéndose:

$$
c_{v}=\left\{\begin{array}{ll}
12 k_{B}\left(\Theta_{R} / T\right)^{2} e^{-2 \Theta_{R} / T} & \text { si } T \ll \Theta_{R} \\
k_{B} & \text { si } T \gg \Theta_{R}
\end{array}\right.
$$


El applet en Java permite introducir las temperaturas de rotación y traslación y el programa calcula el calor específico por partícula (en unidades de $k_B$) representado frente a $\log T$. El applet dibuja dos líneas verticales, correspondientes a $\Theta_R$ y  $\Theta_V$y tres horizontales, que son las contribuciones de la parte traslacional (3/2), la rotacional (1) y la vibracional (1) cuando las temperaturas son mucho mayores que la rotacional y la vibracional respectivamente.

Si las temperaturas características están bien separadas, como sucede en la moléculas diatómicas reales, pueden observarse claramente las contribuciones de los tres hamiltonianos. El calor específico debido a la rotación muestra un máximo alrededor de $\Theta_R$.

Las temperaturas características para algunas moléculas diatómicas son:

$$
\begin{array}{ccccccccc}
 &  H &	N &	CO &	NO &	O &	Cl &	Br &	K \\
\Theta_R &  85 &	2.9 &	2.8 &	2.4 &	2.1 &	0.35 &	0.12 &	0.081 \\
\Theta_V  & 6200 &	3340 &	3070 &	2690 &	2230 &	810 &	470 &	140
\end{array}
$$

Fuente: Statistical Thermodynamics, J.F.Lee, F.W.Sears y D.L.Turcotte, Addison-Wesley (1963).

Pulsa [aquí](http://valbuena.fis.ucm.es/expint/html/fises/diatom/exp.html) para ver una gráfica con las medidas experimentales del calor específico a presión constante de los gases HD, HT y DT, donde D representa el deuterio y T el tritio, frente a la temperatura. La relación entre $C_p$ y $C_V$ para gases ideales es: $C_p = C_V + Nk_B$ y, por ello, los resultados para $C_p$ están desplazados una unidad.

En esta [gráfica](http://valbuena.fis.ucm.es/expint/html/fises/diatom/exp1.html) se comparan los resultados de la contribución vibracional al calor específico, Ec. (4) en función de $T/\Theta_V$ para varios gases.

Para moléculas triatómicas el análisis anterior se complica notablemente. Por ejemplo, el $CO_2$ tiene 4 modos normales de vibración. El texto de J.Kestin y J.R.Dorfman (A Course in Statistical Thermodynamics, Academic Press, 1971) incluye una [gráfica](http://valbuena.fis.ucm.es/expint/html/fises/diatom/exp3.html) con el calor específico de dicha molécula.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/06-diatomicas.mp4" type="video/mp4">
</video>

# Experimento 7: Vibraciones en sólidos cristalinos; Teoría de Debye

El estudio del calor específico en sólidos fue uno de los primeros éxitos de la teoría cuántica aplicada a sistemas macroscópicos. Más en concreto, permitió explicar las las desviaciones respecto a la ley de Dulong y Petit a bajas temperaturas que habían sido medidas experimentalmente.

Para estudiar la teoría de Debye, debemos plantear el hamiltoniano de las partículas que componen el sólido, que consta de la parte cinética y de la parte de energía potencial:

(1)
$$
H=\sum_{i} \frac{\mathbf{p}_{i}^{2}}{2 m}+\sum_{i<j} V\left(\left|\mathbf{r}_{i}-\mathbf{r}_{j}\right|\right) \equiv \sum_{i} \frac{\mathbf{p}_{i}^{2}}{2 m}+\Phi
$$

Como estamos interesados en el estudio del sólido, supondremos que los átomos no se desvían mucho de sus posiciones de equilibrio creadas por el potencial de interacción de los otros átomos. Así, el potencial total que afecta al átomo $i$-ésimo es: $V_i = \sum_i V(|r_i - r_j|)$, y la posición de equilibrio de dicho átomo $r_i^0$ es aquella que hace $\partial V_i/\partial r_i = 0$. Podemos desarrollar la energía de interacción en serie de Taylor alrededor de estas posiciones de equilibrio como:

(2)
$$
\Phi=\sum_{i<j} V\left(\left|\mathbf{r}_{i}^{0}-\mathbf{r}_{j}^{0}\right|\right)+\sum_{i<j} \frac{1}{2}\left(\mathbf{r}_{i}-\mathbf{r}_{i}^{0}\right) \frac{\partial^{2} V}{\partial \mathbf{r}_{i} \partial \mathbf{r}_{j}}\left(\mathbf{r}_{j}-\mathbf{r}_{j}^{0}\right)
$$

desarrollo en el que no aparecen las primeras devivadas del potencial donde las derivadas están sustituidas en las posiciones de equilibrio $r_i^0$. Esta energía de interacción tiene forma cuadrática como la de un oscilador armónico, y por ello se llama aproximación armónica. Por el hecho de ser cuadrática en las variables posición, existe una transformación a otra base en la que la energía de interacción es diagonal, llamando a los elementos diagonales $m \omega_i$:

(3)
$$
\Phi=\sum_{i} \frac{1}{2} m \omega_{i}^{2} \xi_{i}^{2}
$$

A las coordenadas $\xi$ se les llama *modos normales* de vibración desde un punto de vista clásico o *fonones* desde el punto de vista cuántico. Esta transformación de las variables $r$ a las $\xi$ deja invariante la forma de la parte cinética del hamiltoniano. Así pues, el hamiltoniano en variables $\xi$ es:

(4)
$$
H=\sum_{i}\left(\frac{1}{2} m \dot{\xi}_{i}^{2}+\frac{1}{2} m \omega_{i}^{2} \xi_{i}^{2}\right)
$$

que es el hamiltoniano de un conjunto de osciladores armónicos de frecuencias $\omega_i$. Los niveles cuánticos de cada uno de los osciladores son:

(5)
$$
\epsilon_n = \hbar \omega_{i}\left(n+\frac{1}{2}\right), n=0,1,2, \ldots
$$

El cálculo de la función de partición es ahora sencillo, dado que el hamiltoniano (4) se puede descomponer en una suma de $3N$ osciladores independientes:

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
podía calcular suponiendo que la relación de dispersión que relaciona $\omega$ (frecuencia) con $k$ (vector de ondas) está dada por $\omega=c k,$ donde $c$ es la velocidad de propagación. Con esta hipótesis,

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

A partir de esta expresión podemos calcular el calor especifico, derivando $\ln Z$ respecto a $\beta$ para obtener la energía y posteriormente respecto a $T$ para obtener el calor especifico. Tras algunos cálculos obtenemos que:

(10)
$$
C_{V}=9 N k_{B}\left(\frac{T}{\Theta_{D}}\right)^{3} \int_{0}^{\Theta_{D} / T} d x \frac{x^{4} e^{x}}{\left(e^{x}-1\right)^{2}}
$$

donde $\Theta_{D}$ es la temperatura de Debye, definida a partir de la
frecuencia de Debye como: $\Theta_{D} \equiv \hbar \omega_{D} / k_{B}$. Es importante darse cuenta de que el calor especifico depende de la temperatura a través del cociente $T / \Theta_{D}$

Los limites de altas y bajas temperaturas respecto a ${\Theta}_{D}$ de (10) se pueden calcular fácilmente, con el resultado:

$$
C_{V} / N \simeq\left\{\begin{array}{ll}
{\frac{12}{5} \pi^{4} k_{B}\left(\frac{T}{\Theta_{D}}\right)^{3},} & {\text { si } T \ll \Theta_{D}} \\
{3 k_{B},} & {\text { si } T>\theta_{D}}
\end{array}\right.
$$

El valor de $C_V = 3Nk_B$, independiente de la temperatura, se conoce como la ley de Dulong y Petit.

El applet que mostramos permite introducir la temperatura de Debye, $\Theta_D$ y dibuja el calor específico por partícula, integrando la ecuación (10).

Las temperaturas de Debye para algunos sólidos son:

K 	Cu 	Al 	Fe 	B 	C(diamante) 	FLi 	ClNa

100 	315 	394 	420 	1250 	1860 	730 	321

$$
\begin{array}{ccccccccc}
 &K &	Cu &	Al &	Fe &	B &	C(diamante) &	FLi &	ClNa \\
\Theta_D & 100 & 315 &	394 &	420 &	1250 &	1860 &	730 &	321
\end{array}
$$

Pulsa [aquí](http://valbuena.fis.ucm.es/expint/html/fises/debye/exp.html) para ver una gráfica con las medidas experimentales del calor específico para varias sustancias (aluminio, cobre, plata y plomo) frente a $T/\Theta_D$. En esta representación, $C_V$ es una función universal como puede verse en la ecuación (10).

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/07-solidos.mp4" type="video/mp4">
</video>

# Experimento 8: Irreversibilidad y Fluctuaciones en Equilibrio

La expansión libre de una gas es un experimento tradicional en las materias de Termodinámica y Física Estadística útil para explicar el concepto de irreversibilidad. En dicho experimento se toma un recipiente que contiene un gas que se mantiene en una de las mitades del mismo mediante un pistón o una pared. En un instante determinado se rompe la pared o se libera el pistón y se estudia la evolución del gas. La experiencia nos dice que el gas se expande hasta ocupar todo el recinto, y que nunca se observa que el gas se vuelva a concentrar en una parte del recipiente. Se argumenta así que la evolución temporal nos conduce de un estado inicial con el gas concentrado en una región a un estado final con el gas ocupando todo el recinto, pero nunca a la inversa. Por ello, la secuencia de estados tiene una dirección temporal determinada y se dice que el proceso es irreversible.

En este experimento interactivo queremos estudiar este sistema, con el objeto de verificar que una de las condiciones necesarias para que se produzca un comportamiento irreversible es que el sistema esté compuesto por un número elevado de grados de libertad. En otras palabras, *si el sistema está compuesto por pocas partículas, podríamos ver la secuencia inversa*, junto con la secuencia ordinaria. No habría, por tanto una caracterización bien definida de irreversibilidad.

También pretendemos discutir las fluctuaciones de densidad que se producen en el equilibrio.

Para ello, el applet que presentamos a continuación simula el experimento descrito anteriormente. Se parte de un recipiente bidimensional en el que hay un gas en la mitad izquierda del mismo y por tanto la mitad derecha está vacía. Pulsando **INICIA** comienza la evolución en la que el gas se expande y llena la totalidad del recipiente. Las moléculas del gas interaccionan con un potencial de discos duros. En el panel de la derecha se dibuja la diferencia entre el número de partículas de la izquierda y de la derecha.

---

El botón **HISTOGRAMA** abre una ventana con la distribución de la diferencia entre el número de partículas en la zona izquierda y derecha. Volveremos sobre esta distribución al final del texto.

Puede observarse que si el gas consta, por ejemplo, de 20 partículas, en número de partículas en ambas regiones se equilibra rápidamente (momento en el que se habría alcanzado una "situación de equilibrio"), y luego comienza a fluctuar alrededor de 0, situación en la que hay el mismo número de partículas en ambas regiones. La amplitud de las fluctuaciones puede llegar a ser de 10 partículas, y entonces hay 5 partículas en una región y 15 en la otra. Sin embargo, muy raramente se verá que las 20 partículas están en una región y la otra se ha quedado vacía.

Si ahora disminuimos el número de partículas hasta 6 observaremos la misma evolución incial en la cual algunas partículas pasan a la parte derecha. La diferencia en el número de partículas se mueve en torno a cero, esto es, vemos frecuentemente que la diferencia toma los valores +2 o -2 y +4 o -4. Sin embargo no es raro en absoluto ver que todas las partículas se concentren en un lado de la caja, y, por tanto, se observa un proceso imposible desde el punto de la Termodinámica: una proceso en el que se invierte el evolución temporal en el que la entropía crece. Las partículas, de manera natural, pasan de llenar todo el volumen accesible, a concentrarse en una región, dejando otra vacía.

Para hacer un análisis más cuantitativo de estos procesos, podemos considerar las partículas independientes entre sí y estudiar la probabilidad de que haya $N_d$ en el lado derecho y $N_i$ en el lado izquierdo. Dado que ambas regiones tienen el mismo volumen y que las partículas son independientes, la probabilidad de que cada una de las partícula se encuentre en uno de los lados es $p_{1/2}$. Entonces, la probabilidad de encontrar $N_d$ y $N_i$ viene dada por la distribución binomial:

(1)
$$
p\left(N_{d}, N_{i}\right)=\left(\begin{array}{c}
{N_{d}+N_{i}} \\
{N_{d}}
\end{array}\right) p^{N_{d}}(1-p)^{N_{i}}=\left(\begin{array}{c}
{N} \\
{N_{d}}
\end{array}\right)\left(\frac{1}{2}\right)^{N}
$$

donde hemos utilizado que $N_{d}+N_{i}=N$ y que $p=1 / 2$.

De esta expresión ya podemos extraer consecuencias importantes. Dado que el número combinatorio $N$ sobre $N_d$ es máximo cuando $N_{d}=N / 2,$ encontramos que la situación **más probable** es aquella en la que $N_{d}=N / 2$, esto es, en la que
el mismo número de particulas en ambos lados. Igualmente, dado que el número combinatorio $N$ sobre $N_{d}$ es mínimo cuando $N_{d}=0$ ó $N_{d}=N$, estas situaciones son las **más improbables**, que corresponden a uno de los lados vacíos y todas las particulas en el otro lado.

¿Cuán grande es la diferencia entre ambas probabilides? Para ello, calculamos el cociente entre $p(N, 0)$ y $p(N / 2, N / 2)$:

(2)
$$
\frac{p(N, 0)}{p(N / 2, N / 2)}=\frac{N ! /(N ! 0 !)}{N ! /[(N / 2) !(N / 2) !]}=\frac{[(N / 2) !]^{2}}{N !}
$$

Para evaluar este cociente, utilizamos la aproximación de Stirling para $n ! \simeq n^{n} e^{-n} \sqrt{2 \pi n},$ válida con un error menor del 1% para $n \geq 5$. Obtenemos entonces que:

(3)
$$
\frac{p(N, 0)}{p(N / 2, N / 2)} \simeq \sqrt{\frac{\pi N}{2}} 2^{-N}
$$

Observamos que la probabilidad de encontrar todo el gas concentrado en una región, disminuye *exponencialmente con el número de partículas*. Por ejemplo, para $N=6$, evaluación de la fórmula (2) nos da un valor:

$$
\frac{p(6,0)}{p(3,3)} = 0.05
$$

esto es, un 5% de las veces observamos una concentración de partículas. Este número es pequeño pero todavía apreciable en el applet. Sin embargo, para $N=20$, el valor disminuye hasta:

$$
\frac{p(20,0)}{p(10,10)} = 5.54\times10^{-6}
$$

Para el número máximo de partículas que el applet puede simular, $N=100$:

$$
\frac{p(100,0)}{p(50,50)} = 9.9\times10^{-30}
$$

y para $N=300$,

$$
\frac{p(300,0)}{p(150,150)} = 6.8\times10^{-90}
$$

Aunque estos números son extraordinariamente pequeños, $N=300$ todavía está infinitamente lejos del número de Avogadro de particulas, $N=6.023 \times 10^{23}$

Asi pues, este cálculo tan sencillo muestra que la irreversibilidad aparece como consecuencia del enorme número de particulas que componen un sistema macroscópico, 100 en nuestro experimento y del orden del número de Avogadro en un sistema real.

La ecuación (1) nos permite calcular las fluctuaciones respecto al estado más probable, $N_{d}=N_{i}=N / 2$. Para ello, definimos $x$ como la desviación relativa respecto a dicho estado: $x=\left(N_{d}-N_{i}\right) / N$, que junto con la relación $N_{d}+N_{i}=N$, nos permite escribir que: $N_{d}=N(1+x) / 2$. Sustituyendo esta expresión en la ecuación (1) y utilizando el desarrollo de Stirling obtenemos que la probabilidad de encontrar una desviación $x$ sigue una distribución gaussiana:

(4)
$$
p_{N}(x) \simeq \sqrt{\frac{2}{\pi N}} e^{-(N-1) z^{2} / 2}
$$

de dispersión $\sigma^{2}=1 /(N-1),$ esto es, más estrecha cuantas más particulas haya en el sistema. Asi pues, una fluctuación es tanto más improbable cuanto más grande sea el sistema.

La ecuación (4) se puede volver a escribir en función de $N_{d}-N_{i}=\Delta,$ obteniéndose que:

(5)
$$
p_{N}(\Delta) \simeq \sqrt{\frac{2}{\pi N}} e^{-\Delta^{2} /(2 N)}
$$

así pues, la dispersión en la variable $\Delta$ es: $\sigma_{\Delta}^{2}=N$

Finalmente, en el applet hemos dibujado con dos lineas horizontales el valor de $\pm \sqrt{\sigma_{\Delta}^{2}}$ (entre el que $x$ se encuentra el
$68 \%$ de las veces) y el valor $\pm 3 \sqrt{\sigma_{\Delta}^{2}}$ (99.7 de las veces).

El applet que hemos presentado simula un sistema de partículas con interacción tipo disco duro. Sin embargo, todas las consideraciones anteriores se aplican a un sistema compuesto por [partículas sin interacción](#partículas-sin-interacción).

Incluso, se puede construir un modelo [mucho más sencillo](#fluctuaciones-en-equilibrio-en-un-modelo-aleatorio) con las mismas propiedades.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/08-con_interaccion.mp4" type="video/mp4">
</video>

# Experimento 9: Colectividad Macrocanónica

La descripción de sistemas físicos que intercambian energía y partículas con los alrededores (esto es, un foco térmico y de partículas), se realiza mediante la **colectividad macrocanónica**, que nos indica la probabilidad de que el sistema tenga energía $E$ y número de partículas $N$. Para su derivación, se parte del sistema total, compuesto por el sistema que vamos a estudiar (con energía $E$ y $N$ partículas) y los alrededores (o foco) con un número muy grande de partículas, $N_F \gg N$ y una energía grande, $E_F \gg E$.

Con estas hipótesis se puede demostrar que la probabilidad está dada por:

(1)
$$
p(E, N) \sim \omega(E, N, V) z^{N} e^{-\beta E}=z^{N} e^{-\beta H\left(\theta_{0}\right)}
$$

donde $H(q, p)$ es el hamiltoniano del sistema que estamos estudiando, y $\omega$ es el volumen en el espacio de las fases de la hoja caracterizada por $E$, $N$ y $V$. La constante $\beta$ es el inverso de la temperatura: $\beta=1 / k_{B} T$ y $z$ es la *fugacidad* del sistema, que es igual a: $z=\exp (\beta \mu)$ con $\mu$ el potencial quimico. Escribimos el simbolo $\sim$ porque la probabilidad $p(E, N)$ no está normalizada. La constante de normalización se llama *función de partición macrocanónica o función de macropartición*, denotada por $Q$:

(2)
$$
Q(\beta, z, V)=\sum_{N} \int d E \omega(E, N, V) z^{N} e^{-\beta E}
$$

En este caso nos preguntamos únicamente por la probabilidad de encontrar $N$ particulas en el sistema, sin importarnos su energia. Para ello, debemos integrar la ecuación (1) (debidamente normalizada dividiendo por $Q$) sobre todas las
energías posibles:

(3)
$$
p(N)=\frac{1}{Q} z^{N} \int d E \omega(E, N, V) e^{e^{-\beta E}}=\frac{1}{Q} z^{N} Z(N, \beta, V)
$$

En esta ecuación, $Z(N, \beta, V)$ en la función de partición del sistema, $Z(N, \beta, V)=\int d E \omega(E, N, V) e^{-\beta E}=\int d q \ d p \ e^{-\beta H(q,p)}$.

Podemos particularizar esto cálculos para un **gas ideal**, en el que:

(4)
$$
Z(N, \beta, V)=\frac{1}{N !}\left(\frac{V}{\Lambda^{3}}\right)^{N}, \quad Q(z, \beta, V)=e^{x V / \Lambda^{3}}
$$

por tanto, la probabilidad de encontrar $N$ particulas en el sistema resulta ser:

(5)
$$
p(N)=\frac{1}{N !}\left(\frac{z V}{\Lambda^{3}}\right)^{N} e^{-z V / \Lambda^{2}}
$$

que es la distribución de Poisson de parámetro $zV / \Lambda^3$.

En el applet que proponemos se trata de comprobar que la distribución (5) se verifica en un gas ideal. En el panel de la izquierda se simula la evolución de un gas en el que se puede seleccionar con el ratón una región (que sería nuestro sistema, siendo el área no seleccionada el foco o alrededores). En el área seleccionada se verificará que la probabilidad de que haya $N$ partículas está dada por (5). En el panel de la derecha se dibuja la gráfica con el número de partículas en función del tiempo. El botón **HISTOGRAMA** abre una nueva ventana con el histograma calculado (en rojo) y el dado por la distribución de Poisson (en azul).

Para seleccionar la región con el ratón se debe pinchar y arrastrar con el botón izquierdo pulsado. Al liberar el botón, se visualizará la región. Asimismo, conviene hacerla con el botón **PAUSA** pulsado. Una vez seleccionada un área, se debe pulsar el botón **INICIA** para que el histograma se reinicie. Si no se hace así, el histograma almacena toda la evolución temporal con el límite $t=10000$.

Puede observarse que para regiones pequeñas la distribución sigue, efectivamente, la distribución de Poisson dada en (5). Por el contrario, si la región es grande, la distribución (5) deja de ser válida puesto que no se verifican las hipótesis hechas para la obtención de la colectividad macrocanónica (1), en concreto, $N_F \cancel{\gg} N$. El caso más extremo se obtiene cuando se selecciona con el ratón toda la superficie y, por tanto, el sistema siempre tiene el número de partículas total, esto es, $p(N) = \delta(N - N_\text{part})$.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/09-macrocanonica.mp4" type="video/mp4">
</video>

# Experimento 10: Estadísticas de Bosones y Fermiones

Las propiedades de simetría de la función de ondas de un conjunto de partículas conducen a comportamientos físicos muy diferentes incluso aunque no haya interacción entre las mismas. Los **fermiones** (partículas con espín semiimpar como los electrones, protones, neutrones, $He^3$, etc) son partículas descritas por una función de onda *antisimétrica* bajo las permutaciones de los argumentos de la función de onda. Como consecuencia de ello, no puede haber dos fermiones con los mismos números cuánticos. A este hecho se le llama *principio de exclusión de Pauli*. Por el contrario, los **bosones** (partículas con espín entero, como fotones, núcleos de $He^4$, etc o cuasi-partículas como fonones) están descritos por una función de onda *simétrica* que no impide que dos o más bosones ocupen estados con los mismos números cuánticos.

En la descripción estadística de estos sistemas se utiliza la colectividad *macrocanónica*, en la que se construye la función de macropartición para un sistema con niveles de energía $\epsilon_i$, con $i=0,1,2,...$ como:

(1)
$$
\ln Q(\beta,V,z) = -\prod_i \sum_{n_i}[z \exp (-\beta \epsilon_i)]^{n_i}
$$

donde $\beta$ es el inverso de la temperatura $\beta=1 /\left(k_{B} T\right)$ y $V$ es el volumen. La variable $z$ es la fugacidad, que se determina por la condición de que el número de partículas $N$ ha de ser igual a: $N=z \frac{\partial \ln Q}{\partial x}$

Las variables $n_{i}$ en la ecuación (1) varían entre 0 y el *número máximo de particulas en el nivel i-ésimo*. Tenemos entonces que para fermiones, en aplicación del principio de exclusión de Pauli, cada nivel puede estar vacio ($n_{i}=0$) o contener 1 particula ($n_{i}=1$). No puede haber $2,3,$ etc particulas. Sin embargo, para bosones, puede haber *cualquier* número de particulas, $n_{i}=0,1,2,3, ...$. Por tanto:

(2)
$$
\begin{array}{l}
&\text{ fermiones:} \quad n_{i}=0,1 \quad &\Longrightarrow \quad \ln Q_{F} &= \prod\left[1+z \exp \left(-\beta \epsilon_{i}\right)\right] \\
&\text { bosones: } \quad n_{i}=0,1,2, ... \quad &\Longrightarrow \quad \ln Q_{B} &=\prod_{i}^{i} \frac{1}{\left[1-z \exp \left(-\beta \epsilon_{i}\right)\right]}
\end{array}
$$

A partir de estas expresiones podemos calcular cuántas particulas en promedio hay en cada nivel, $\langle n_{i}\rangle$ según:

(3)
$$
\left\langle n_{i}\right\rangle=-\frac{1}{\beta} \frac{\partial \ln Q}{\partial \epsilon_{i}},
\left\{\begin{array}{l}{\left\langle n_{i}\right\rangle_{F}=\frac{1}{z^{-1} \exp \left(\beta \epsilon_{i}\right)+1} \quad \text { fermiones }} \\ {\left\langle n_{i}\right\rangle_{B}=\frac{1}{z^{-1} \exp \left(\beta \epsilon_{i}\right)-1} \quad \text { bosones }}\end{array}\right.
$$

Como es lógico en aplicación del principio de Pauli, la ocupación de un nivel en fermiones no puede ser nunca superior a 1, y ello está garantizado porque el denominador en (3.a) es siempre mayor que la unidad, debido al factor +1. Por el contrario, en el gas de bosones no hay esta restricción, y debido al factor -1 podemos encontrar ocupaciones (muy) superiores a la unidad.

---

El applet Java que presentamos realiza una simulación [Monte Carlo](#método-de-monte-carlo) de un sistema compuesto por $N$ bosones (izquierda) y $N$ fermiones (derecha) (la elección de los colores es totalmente arbitraria). Los niveles cuánticos están equiespaciados con distancia 1 en las unidades de la simulación, esto es, $\epsilon_i=i,\ i=0,1,2,\dots$. Se puede variar el número de partículas de 0 a 20 y la temperatura de 0.1 a 10 (hemos tomado $k_B=1$). Pulsando los botones **HISTOGRAMA BOSONES** e **HISTOGRAMA FERMIONES** se dibujan las ocupaciones de los niveles energéticos, que obedecen las ecuaciones (3.b) y (3.a) respectivamente.

Puede observarse que para temperaturas suficientemente bajas, los bosones se concentran en el estado fundamental, dando lugar a un pico en la ocupación de dicho nivel. Este fenómeno se conoce como *condensación de Bose*.

Por el contrario, los fermiones ocupan a temperaturas bajas los $N$ primeros niveles, con pocos saltos a niveles superiores. En nuestro caso, donde los niveles están equiespaciados comenzando con $\epsilon_0 = 0$, el nivel de Fermi estaría situado en $\epsilon_F = N-1$.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/10-estadisticas.mp4" type="video/mp4">
</video>

# Experimento 11: Modelo de Ising

El ferromagnetismo, que aparece en muchos metales ordinarios como el hierro y el níquel, es la presencia de magnetización espontánea incluso cuando no hay campo magnético externo. Se debe a que una fracción importante de los momentos magnéticos (o espines) de los átomos se alinean en la misma dirección debido a la interacción entre los mismos, dando lugar a que la muestra se imane. Este alineamiento se produce únicamente a temperaturas bajas, por debajo de una temperatura característica llamada *temperatura de Curie*, $T_C$.

Por encima de dicha temperatura los espines están orientados al azar, de forma que no hay un campo magnético neto. En la temperatura de Curie, $T_C$, como en toda transición, aparece una fenomenología diferente: por ejemplo, el calor específico es divergente y la energía y la magnetización tienen derivada discontínua.

El modelo de Ising es un modelo sencillo para el estudio de la transición ferromagnética, que es resoluble analíticamente. Se parte de una red regular, que imita la red cristalina del hierro o níquel, en cuyos sitios se colocan un momento magnético o espín $s_i$ que puede tomar los valores +1 ó -1. La energía de interacción entre los espines $i$ y $j$ (supuesto que están en sitios contiguos de la red) es de la forma:

(1)
$$
H_{ij} = -J s_i s_j
$$

en la que $J$ es la energía de interacción, supuesta positiva, $J>0$. La forma del hamiltoniano es tal que favorece que los espines estén alineados, porque si $s_i = s_j$ entonces la energía disminuye en una cantidad $J$. Si existe un campo magnético externo, $B$, los momentos magnéticos interaccionan con él con una energía: $H_B = -B s_i$. El hamiltoniano total es entonces:

(2)
$$
H(\{s_i\}) = -J \sum_{\langle i, j \rangle}s_i s_j - B\sum_i s_i
$$

donde la notación $\langle i, j \rangle$ indica suma a espines contiguos.

¿Por qué se produce la transición de fase? Si sólo tuviéramos en cuenta la energía y tratáramos de minimizarla, entonces la fase del sistema sería una fase perfectamente ordenada, y, por tanto, ferromagnética. Sin embargo, existe el efecto de la temperatura que provoca un efecto aleatorio en el que los espines pueden cambiar su valor al azar. Este efecto es más alto cuánto más alta es la temepratura, y por ello, temperaturas altas dan lugar a fases desordenadas. Dependiendo de cuál sea el efecto dominante entre ambos (energía versus temperatura), aparecerán fases ferromagnéticas o no.

La magnetización del sistema es el valor medio de la suma de los valores de los momentos magnéticos:

(3)
$$
M = \left \langle \sum_i s_i \right \rangle
$$

Esta magnetización puede calcularse utilizando la colectividad canónica por medio de la función de partición

(4)
$$
Z(T,B) = \sum_{s_1} \sum_{s_2}...\sum_{s_N} \exp \left( -\frac{H(\{s_i\})}{k_B T} \right)
$$

a partir de la cual se puede calcular la magnetización como:

(5)
$$
M = - \frac{1}{k_B T} \left( \frac{\partial \ln Z}{\partial B} \right)_ {B=0}
$$

Se puede comprobar fácilmente que la expresión (5) coincide con (3).

Para sistemas en dimensión 1, se puede calcular (4) y (5) de forma sencilla, comprobándose que *no existe transición de fase*. En dimensión 1 los efectos del desorden inducido por la temperatura son siempre dominantes. Sin embargo, en dimensión 2 y superiores sí que existe la transición de fase. En dos dimensiones, el cálculo de (4) y (5) es posible aunque muy complicado (ver el libro de Huang). Se encuentra que la temperatura de transición (cuando $B=0$) está en:

$$
\frac{J}{k_B T_C} \equiv j_C = 0.4407
$$

En esta ecuación hemos definido $j \equiv \frac{J}{k_B T_C}$, que es el único parámetro relevante al tomar $B=0$.

El applet que hemos preparado realiza una simulación [Monte Carlo](#método-de-monte-carlo) del modelo de Ising bidimensional. Se puede elegir el tamaño de la red desde $8\times 8$ hasta $128 \times 128$, así como el dato inicial (espines +1 ó -1 de forma aleatoria o bien todos fijados a -1), y la constante de interacción $j$. Al pulsar **GENERA RED** comienza la simulación. En el panel de la izquierda se muestran los espines ($s_i = +1$ en amarillo y $s_i = -1$ en azul), y en el de la derecha la magnetización. La línea azul está localizada en la temperatura crítica $T_C$.

Los simulaciones realizadas a temperaturas altas ($j$ pequeño) relajan rápidamente a su valor de equilibrio. Sin embargo, a temperaturas bajas ($j$ grande) puede tardar más, salvo que se parta de una configuración con todos los espines con valor -1. En temperaturas cercanas a la crítica, $T \simeq T_C$ ó $j \simeq j_C$, la relajación es también muy lenta, dado que $T_C$ es un punto crítico, con correlaciones de largo alcance y fluctuaciones muy importantes.

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/11-ising.mp4" type="video/mp4">
</video>

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


## Fluctuaciones en Equilibrio en un Modelo Aleatorio

Un modelo muy sencillo que captura parte de la Física descrita en el experimento [Irreversibilidad y Fluctuaciones en Equilibrio](#experimento-8-irreversibilidad-y-fluctuaciones-en-equilibrio) de esta serie. En el presente experimento se parte de la misma configuración inicial, esto es, todas las partículas en la parte izquierda del recinto. La dinámica consiste en elegir, *al azar*, una de las partículas y cambiarla al otro lado de la caja. La dinámica de las fluctuaciones sigue la misma ley que la descrita anteriormente, esto es, se distribuye de forma gaussiana con dispersión $\sigma^2 = 1/(N-1)$.


## Método de Monte Carlo

Bajo el nombre de método de Monte Carlo se agrupan una serie de algoritmos que permiten generar números aleatorios $\{X_i\}$ con una distribución especificada $f(X)$, que son especialmente útiles en la evaluación de integrales multidimensionales.

De entre estos algoritmos, el más usual en Física Estadística es el llamado algoritmo de Metropolis, que crea una secuencia ordenada $\{X_i\}$ en la que cada elemento $X_n$ de la lista se genera a partir del anterior, $X_{n-1}$.

Para ello, se procede de la forma siguiente. Se elige un punto de prueba, $X_{p}$, 'cercano' al punto $X_{n-1}$. Este punto pasa a formar parte de la secuencia de aleatorios según sea el cociente:

(1)
$$
r= \frac{f(X_p)}{f(X_{n-1})}
$$

Si $r$ es mayor que 1, entonces el punto de prueba se acepta, esto es, $X_{n} = X_p$. Por el contrario, si $r$ es menor que 1, aceptamos $X_p$ con probabilidad $r$. Para ello, generamos un número aleatorio, $\xi$, distribuido uniformemente entre 0 y 1. Si $\xi < r$, aceptamos el punto de prueba: $X_{n} = X_p$, y si sucede lo contrario, tomamos como nuevo punto de la secuencia el anterior: $X_{n} = X_{n-1}$. Una vez que tenemos el nuevo punto repetimos el procedimiento para encontrar el punto $X_{n-1}$.

El punto de inicio de la secuencia, $X_0$, puede elegirse de forma aleatoria. Su influencia es menor cuanto más larga es la secuencia $\{X_i\}$.

En su aplicación en Física Estadística, el método de Monte Carlo se utiliza para evaluar valores medios en alguna colectividad, usualmente la colectividad canónica. Por ello, se elige la función $f(X)$ (la distribución deseada) como:

(2)
$$
f(X) = \frac{\exp(-\beta H(X))}{Z}
$$

donde $Z$ es la función de partición (es necesario incluirla para que $f$ esté debidamente normalizada, pero que en los cálculos no aparece porque $r$ es el cociente de dos funciones $f$, según la ecuación (1)). Con la función definida en (2) el factor $r$ es:

(3)
$$
r = \exp (-\beta [H(X_p) - H(X_{n-1})])
$$

Así pues, el método de Monte Carlo acepta un punto de prueba si su energía es menor que el punto anterior; disminuciones de energía son siempre posibles. Por el contrario aumentos de energía son posibles con una probabilidad que depende del incremento de la misma y de la temperatura.

La ecuación (2) nos nuestra también que las variables $X$ son las variables del hamiltoniano y que, por tanto, la secuencia de puntos $\{X_i\}$ genera una trayectoria sobre el espacio de las fases $\Gamma$.

## Partículas sin interacción

<video width="800" height="600" controls="controls" style="display:block; margin: 0 auto;">
  <source src="Videos/08-sin_interaccion.mp4" type="video/mp4">
</video>

## Precisión Finita

Cuando se iteran las transformaciones del panadero y de Arnold muchas veces, se observa que los puntos comienzan a mostrar cierta regularidad y, finalmente, se concentran en el origen. Ello es debido a la precisión finita con la que se realizan los cálculos y, más concretamente, al uso de la operación (módulo 1).

Este efecto es fácil de ilustrar en la transformación del panadero, y para ello necesitamos escribir los números en sistema binario. Por ejemplo, $x= 0.75$ se escribe en binario como:

$$
x=0.75 \Rightarrow x_{B}=0.11=1 \times \frac{1}{2}+1 \times \frac{1}{2^{2}}
$$

En la transofrmación del panadero en la coordenada $x$ se multiplica por 2 y posteriormente se toma el (módulo 1) de la misma. La aplicación de la multiplicación por 2 en la representación binaria no es más que despazar el punto decimal un lugar hacia la derecha:

$$
2 \times x = 10 \times x_B = 1.1
$$

Posteriormente, la aplicación de la operación (módulo 1) elimina los dígitos a la izquierda del punto decimal:

$$
1.1(\text{modulo 1}) = 0.1
$$

Como vemos, una aplicación de la transformación del panadero nos ha eliminado un dígito. Como los números reales se representan en los ordenadores mediante un número finito de dígitos, la aplicación sucesiva de la transformación *nos deja sin dígitos significativos*. Cualquier número representado por 8 bytes (como el tipo `long` de JAVA) tiene en binario como máximo 64 ceros y unos. Así pues, la aplicación de la transformación del panadero 64 veces deja al número sin dígitos significativos.

Por ejemplo, consideremos un valor de $x$ cuya precisión en binario es de 9 dígitos. Llamemos a estos 9 dígitos $b_1, b_2, ..., b_9$. Por tanto, en representación binaria es:

$$
x_{B}=0 . b_{1} b_{2} b_{3} b_{4} b_{5} b_{6} b_{7} b_{8} b_{9}
$$

Entonces, tras $N$ aplicaciones de la transformación del panadero tendremos:

$$
\begin{array}{ll}
{\text { inicial }:} & {x_{B}=0 . b_{1} b_{2} b_{3} b_{4} b_{3} b_{8} b_{7} b_{8} b_{9}} \\
{N=5:} & {x_{B}=0 . b_{6} b_{7} b_{8} b_{0} 00000} \\
{N=8:} & {x_{B}=0.6_{9} 00000000} \\
{N=10:} & {x_{B}=0.000000000}
\end{array}
$$

Como se oberva, cualquier coordenada $x$ acaba en $x=0$ tras múltiples aplicaciones de la transformación del panadero.

Algo similar sucede en la dirección $y$: la división por 2 en binario simplemente mueve el punto decimal un lugar hacia la izquierda y los dígitos significativos se pierden por la izquierda.
