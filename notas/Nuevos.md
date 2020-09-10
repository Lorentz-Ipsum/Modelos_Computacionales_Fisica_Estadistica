---
layout: post
title: Applets nuevos todos
print_background: true
export_on_save:
  html: true
---

# Textos nuevos de todos los applets

[TOC]

# Experimento 1: Teorema del límite central


El teorema del límite central (CLT, por sus siglas en inglés) establece que la suma de variables aleatorias sigue una distribución normal (siempre que el número de variables sumadas sea suficientemente grande). La única condición es que las variables que se suman sean independientes y generadas por la misma distribución de probabilidad, de valor esperado y varianza finitas.

> Sean $X_i, i = 1,\dots, N$ un conjunto de $N$ variables aleatorias independientes, todas distribuidas según la misma distribución de probabilidad de media $\mu$ y varianza $\sigma^2 \neq 0$ finitas.
> Entonces, cuando $N$ es suficientemente grande (de forma rigurosa, tendiendo a infinito), la probabilidad de que la variable aleatoria $Y$ definida como la suma de las anteriores ($Y = X_1 + X_2 + \dots + X_N$) tome el valor $y$ sigue una distribución gaussiana de media $\mu_Y = N \mu$ y varianza $\sigma_Y^2 = \sigma^2/n$:
>
> $$
P_{Y}(y)=\frac{1}{\sqrt{2 \pi N \sigma^{2}}} \exp \left[-\frac{(y-N \mu)^{2}}{2 N \sigma^{2}}\right]
$$

Hay otras versiones del teorema más generales. Por ejemplo en la de Lyapunov se permite que las variables $X_i$ no estén distribuidas idénticamente, pero se imponen ciertas condiciones sobre los momentos de órden superior de las distribuciones individuales.

Es un ejemplo de la aplicación de la ley de los grandes números de la teoría de la probabilidad.

En el applet se puede elegir el número de variables aleatorias y la distribución de éstas.

En la versión original se podía elegir la distribución con que se generan las variables aleatorias $X_i$ entre tres opciones: Como un dado (del 1 al 6), como una moneda (0 ó 1) y uniformemente distribuidas entre 0 y 1 (ambos incluidos).

En esta nueva versión he añadido otras dos opciones, para ilustrar que no importa que la distribución de partida no sea uniforme: una distribución triangular y una distribución de Poisson ambas normalizadas entre 0 y 1.

[Posible figura: Dos columnas, 5 filas. En cada fila a la izquierda está la distribucion de las X y a la derecha el histograma normalizado de Y para N = 25, tras 20 tiradas.]

Aplicaciones del CLT.
