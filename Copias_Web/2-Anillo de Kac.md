# Experimento 2: Anillo de Kac

El anillo de Kac es un excelente modelo de un sistema dinámico que ilustra aspectos como el tiempo de recurrencia de Poincaré, las hipótesis estadísticas y, en general, cómo un sistema que es reversible puede mostrar comportamiento irreversible. Fue propuesto por Marc Kac en conexión con la ecuación de Boltzmann.

El modelo consta de un anillo en el que hay $N$ casillas, ocupadas cada una de ellas por una bola de color azul o rojo (normalmente $N \gg 1$). Sobre el anillo se disponen $M$ sitios al azar (con $M\leq N$). La dinámica es muy sencilla: de forma síncrona, a cada unidad de tiempo, las bolas saltan a la casilla contígua en el sentido de las agujas del reloj. Si una bola cruza uno de los $M$ sitios elegidos, cambia su color.

La cuestión que nos interesa es: ¿Cuántas bolas de cada color hay tras $t$ pasos de tiempo?

La dinámica de este modelo es reversible y periódica de periodo (al menos) $T=2N$, porque tras $T$ pasos de tiempo cada partícula ha dado dos vueltas al anillo, ha cruzado por $2M$ sitios marcados y ha sufrido $2M$ cambios de color, que por ser un número par implica que ha recuperado su color inicial. Por tanto, el tiempo de recurrencia de Poincaré es $2N$.

Vamos a escribir unas ecuaciones del movimiento para este sistema. Para ello, denotamos por $N_A (t)$ y $N_R (t)$ el número total de bolas azules y rojas en el tiempo $t$ respectivamente. Asimismo, definimos $n_A (t)$ y $n_R (t)$ como el número total de bolas azules y rojas que tienen delante un sitio marcado y que, por tanto, van a cambiar de color. Con estas definiciones, es fácil ver que las ecuaciones de evolución se pueden escribir como:

$$
N_A (t+1) = N_A(t) - n_A (t) + n_R (t)
$$

$$
N_R (t+1) = N_R(t) - n_R (t) + n_A (t)
$$

Estas ecuaciones no se pueden resolver de manera general. Sin embargo, podemos hacer una hipótesis muy sencilla, que supone que la probabilidad de que una partícula encuentre un sitio marcado es el número total de sitios dividido por el número de casillas:

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
