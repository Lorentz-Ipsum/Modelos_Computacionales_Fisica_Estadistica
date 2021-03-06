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

El applet que hemos presentado simula un sistema de partículas con interacción tipo disco duro. Sin embargo, todas las consideraciones anteriores se aplican a un sistema compuesto por [partículas sin interacción](adicional/particulas_sin_interaccion.md).

Incluso, se puede construir un modelo [mucho más sencillo](adicional/fluctuaciones_en_equilibrio_modelo_aleatorio.md) con las mismas propiedades.
