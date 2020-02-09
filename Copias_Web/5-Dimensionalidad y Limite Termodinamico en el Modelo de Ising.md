# Experimento 5: Dimensionalidad y Límite termodinámico en el Modelo de Ising

Este experimento quiere ilustrar de manera muy sencilla dos conceptos muy importantes en Física. Uno de ellos, muy general, es la dimensionalidad. El otro, muy relevante en Física Estadística, es el de Límite Termodinámico.

Para ello, tomamos el modelo de Ising, cuyo hamiltoniano, viene dado por:
   (1)

donde es la variable de espín (+1 ó -1) del sitio -ésimo y el símbolo denota próximos vecinos. La variable es la energía de interacción.

Este modelo ha sido muy bien estudiado porque es uno de los pocos que admite una solución exacta que presenta una transición de fase. Una transición de fase se produce cuando en un valor de la temperatura u otro parámetro, alguno de los potenciales termodinámico del sistema es no analítico. Ello tiene como consecuencia que alguna magnitud medible (el calor específico en nuestro caso) es discontínua, divergente, etc.

En el modelo de Ising existe una transición entre una fase desordenada o paramagnética a altas temperaturas y una fase ordenada o ferromagnética a bajas temperaturas. La transición se produce a la temperatura crítica .

La dimensionalidad juega un papel muy relevante: la transición no existe en sistemas unidimensionales sino sólo en dimensión 2 y superiores.

El Límite Termodinámico consiste en tomar un límite en el que el número de grados de libertad en el sistema tiende a infinito, pero manteniendo la densidad constante. El límite termodinámico es necesario para que exista una no analiticidad, porque si sumamos un número finito de términos analíticos para calcular la función de partición ésta es necesariamente analítica. Sólo cuando sumamos infinitos términos podemos tener una función no analítica.

Nuestra propuesta para ilustrar ambos conceptos se basa en el estudio del calor específico de un modelo de Ising calculado de manera exacta en redes de tamaño . La variación del tamaño y forma de las redes a través de y permite jugar con dichos aspectos. Cuando, por ejemplo, =1 tenemos una red unidimensional. Si y son diferentes de 1, la red es bidimensional, tanto más cuanto más cercanos estén y . Aumentando tanto como , nos acercaremos al límite termodinámico. Las redes más grandes que el applet permite calcular son aquellas en las que .

El applet permite introducir el tamaño de la red a través de los números y . Pulsando el botón , dibuja el calor específico por partícula . El applet dibuja, además los calores específicos exactos de las redes 1 y 2 dimensionales infinitas. Como se observa en estas últimas, el calor específico unidimensional es un función analítica, mientras que el bidimensional tiene una divergencia en . Las unidades de simulación son .

Sugerimos hacer varias experiencias:
1) calcular todas las redes cuadradas, desde hasta , para ver cómo el máximo de se acerca a (aunque es continuo, finito y analítico en todas ellas),
2) Redes con un número similar de espines, como por ejemplo , , , y , para observar cómo se pasa de un comportamiento tipo unidimensional a otro tipo bidimensional,
3) redes con fijo y creciente.

[Pincha aquí](/11-Modelo_de_Ising.md) para ver otro experimento relacionado con el modelo de Ising bidimensional que incluye el cálculo [Monte Carlo](valbuena.fis.ucm.es/expint/html/fises/MonteCarlo/MonteCarlo.html) de la magnetización. 
