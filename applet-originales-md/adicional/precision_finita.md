# Material adicional

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
