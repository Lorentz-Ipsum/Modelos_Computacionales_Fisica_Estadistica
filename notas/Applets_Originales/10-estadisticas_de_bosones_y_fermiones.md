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

El applet Java que presentamos realiza una simulación [Monte Carlo](adicional/monte_carlo.md) de un sistema compuesto por $N$ bosones (izquierda) y $N$ fermiones (derecha) (la elección de los colores es totalmente arbitraria). Los niveles cuánticos están equiespaciados con distancia 1 en las unidades de la simulación, esto es, $\epsilon_i=i,\ i=0,1,2,\dots$. Se puede variar el número de partículas de 0 a 20 y la temperatura de 0.1 a 10 (hemos tomado $k_B=1$). Pulsando los botones **HISTOGRAMA BOSONES** e **HISTOGRAMA FERMIONES** se dibujan las ocupaciones de los niveles energéticos, que obedecen las ecuaciones (3.b) y (3.a) respectivamente.

Puede observarse que para temperaturas suficientemente bajas, los bosones se concentran en el estado fundamental, dando lugar a un pico en la ocupación de dicho nivel. Este fenómeno se conoce como *condensación de Bose*.

Por el contrario, los fermiones ocupan a temperaturas bajas los $N$ primeros niveles, con pocos saltos a niveles superiores. En nuestro caso, donde los niveles están equiespaciados comenzando con $\epsilon_0 = 0$, el nivel de Fermi estaría situado en $\epsilon_F = N-1$.
