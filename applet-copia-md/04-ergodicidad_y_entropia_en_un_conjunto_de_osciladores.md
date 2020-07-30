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
