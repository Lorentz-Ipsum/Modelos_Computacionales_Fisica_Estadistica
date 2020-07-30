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

que es la distribución de Poisson de parámetro $zV / \Lambda^3.

En el applet que proponemos se trata de comprobar que la distribución (5) se verifica en un gas ideal. En el panel de la izquierda se simula la evolución de un gas en el que se puede seleccionar con el ratón una región (que sería nuestro sistema, siendo el área no seleccionada el foco o alrededores). En el área seleccionada se verificará que la probabilidad de que haya $N$ partículas está dada por (5). En el panel de la derecha se dibuja la gráfica con el número de partículas en función del tiempo. El botón **HISTOGRAMA** abre una nueva ventana con el histograma calculado (en rojo) y el dado por la distribución de Poisson (en azul).

Para seleccionar la región con el ratón se debe pinchar y arrastrar con el botón izquierdo pulsado. Al liberar el botón, se visualizará la región. Asimismo, conviene hacerla con el botón **PAUSA** pulsado. Una vez seleccionada un área, se debe pulsar el botón **INICIA** para que el histograma se reinicie. Si no se hace así, el histograma almacena toda la evolución temporal con el límite $t=10000$.

Puede observarse que para regiones pequeñas la distribución sigue, efectivamente, la distribución de Poisson dada en (5). Por el contrario, si la región es grande, la distribución (5) deja de ser válida puesto que no se verifican las hipótesis hechas para la obtención de la colectividad macrocanónica (1), en concreto, $N_F \cancel{\gg} N$. El caso más extremo se obtiene cuando se selecciona con el ratón toda la superficie y, por tanto, el sistema siempre tiene el número de partículas total, esto es, $p(N) = \delta(N - N_\text{part})$.
