# Material Adicional

# Método de Monte Carlo

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
