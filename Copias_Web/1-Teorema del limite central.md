# Experimento 1: Teorema del límite central

Supongamos que se genera un número grande $N$ de variables aleatorias independientes $X_i, i=1,...,N$, todas distribuidas con las misma distribución de probabilidad, de media $\mu$ y varianza $\sigma^2$. El Teorema del Límite Central nos dice que la variable aleatoria $Y$, construida como la suma de las anteriores:

$$
Y=X_1 + X_2 +...+X_N
$$

se distribuye de forma gaussiana de media $N_\mu$ y de varianza $\sigma^2 /N$, independientemente de la distribución de las variables $X_i$. Entonces, la probabilidad de que la variable aleatoria $Y$ tome el valor $y$ viene dada por:

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
