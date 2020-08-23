# Experimento 6: Moléculas Diatómicas
El objeto de esta práctica es el estudio de las propiedades de un gas de moléculas diatómicas, cuyo hamiltomiano para el par de átomos (1 y 2) que componen la molécula es:

(1)
$$
H_{12}=\frac{\mathbf{p}_{1}^{2}}{2 m}+\frac{\mathbf{p}_{2}^{2}}{2 m}+V\left(\left|\mathbf{r}_{1}-\mathbf{r}_{2}\right|\right)
$$

Este Hamiltoniano puede separarse en dos partes: la parte debida al movimiento del centro de masas de la molécula y la parte debida al movimiento de los átomos respecto al centro de masas. Para ello utilizamos la transformación a coordenadas relativas:

(2)
$$
P = p_1 + p_2; \quad p = p_1 - p_2; \quad r = |r_1 - r_2|
$$

con lo que el hamiltoniano (1) se reescribe como:

(3)
$$
\begin{array}{l}
H_{12}&=\frac{\mathbf{P}^{2}}{2 M}+\frac{\mathbf{L}^{2}}{2 \mu r^{2}}+{\frac{p^{2}}{2 \mu}+V(r)} \\
H_{12}&=H_{T}+H_{rot}+H_{vib}
\end{array}
$$

donde $M = 2m$ es la masa total de la molécula y $\mu = m/2$ es la masa reducida. Así que para la función de partición de una molécula tenemos que:

$$
Z_{12} = Z_T \cdot Z_{rot} \cdot Z_{vib}
$$

y la de $N$ moléculas se puede escribir como:

$$
Z(\beta, N, V) = \frac{1}{N!} Z_{12}^N
$$

A partir de esta función de partición se calcula el calor específico por partícula como:

$$
c_{v}=\frac{1}{N} \frac{\partial E}{\partial T} ; \quad E=\frac{1}{\beta} \frac{\partial \ln Z}{\partial \beta}=\frac{1}{\beta}\left(\frac{\partial \ln Z_{T}}{\partial \beta}+\frac{\partial \ln Z_{r o t}}{\partial \beta}+\frac{\partial \ln Z_{vib}}{\partial \beta}\right)
$$

donde las derivadas parciales que aparecen se deben tomar a $V$ y $N$ constante.

Pasemos a calcular las tres funciones de partición que aparecen. La función de partición traslacional $Z_T$ es la de una partícula libre en 3 dimensiones:

$$
Z_{T}=\frac{h^{3}}{\Lambda^{3}}, \quad \Lambda^{3}=\sqrt{2 \pi M k_{B} T}
$$

que da una contribución al calor específico:

$$
c_v = \frac{3}{2} k_B
$$

### Vibración

La parte vibracional $Z_{vib}$ incluye el potencial de interacción $V(r)$ interatómico. Si suponemos que los átomos vibran alrededor de las posición de equilibrio $r_0$ con pequeña amplitud (lo que hemos asumido implícitamente en (3)), podemos desarrollar el potencial en serie de potencias alrededor de $r = r_0$, obteniendo:

$$
V(r) \simeq V\left(r_{0}\right)+\frac{d V}{d r}\left(r-r_{0}\right)+\frac{1}{2} \frac{d^{2} V}{d r^{2}}\left(r-r_{0}\right)^{2}+\cdots
$$

donde el término en derivada primera, $dV/dr$ es nulo porque está evaluada en $r_0$, que es la posición de equilibrio en la que $V(r)$ es mínimo. Así pues, el potencial $V$ se puede aproximar por un potencial armónico de frecuencia $\omega^2= \frac{d^2 V}{dr^2}\frac{1}{\mu}$. Por tanto, el hamiltoniano de rotación se reduce a:

$$
H_{r o t}=\frac{p^{2}}{2 \mu}+\frac{1}{2} \mu \omega^{2}\left(r-r_{0}\right)^{2}
$$

cuyos niveles de energía cuantizados según:

$$
\epsilon_{vib}=\left(n+\frac{1}{2}\right) \hbar \omega,\quad n=0,1,2, \cdots
$$

y la función de partición (que se puede calcular):

(4)
$$
Z_{vib}=\sum_{n=0}^{\infty} \exp \left(-\beta \hbar \omega\left(n+\frac{1}{2}\right)\right)=\frac{\exp \left(\Theta_{V} / 2 T\right)}{1-\exp \left(\Theta_{V} / T\right)}
$$

donde hemos definido la *temperatura característica de vibración* como:

$$
\Theta_V \equiv \frac{\hbar \omega}{k_B}
$$

que suele ser del orden de cientos a miles de kelvins. Las contribuciones al calor específico son:

$$
c_{v}=\left\{\begin{array}{ll}
k_{B}\left(\Theta_{V} / T\right)^{2} e^{\Theta_{V} / T} & \text { si } T \ll \Theta_{V} \\
k_{B} & \text { si } T \gg \Theta_{V}
\end{array}\right.
$$


### Rotación

La parte rotacional, $Z_{rot}$ incluye el operador momento angular $L^2$, cuyas autofunciones son los armónicos esféricos, $Y_m^l$ con ecuación de autovalores: $\epsilon_{rot} = l(l+1) \hbar^2 / 2 \mu r_0^2, l = 0,1,2, \cdots$, con degeneración $g(\epsilon) = 2l+1$. Entonces la función de partición es:

(5)
$$
Z_{r o t}=\sum_{l=0}^{\infty}(2 l+1) \exp \left(-l(l+1) \frac{\beta \hbar^{2}}{2 \mu r_{0}^{2}}\right)
$$

de donde podemos definir la *temperatura característica de rotación* como:

$$
\Theta_R \equiv \frac{\hbar^2}{2k_B\mu r_0^2}
$$

que varía entre décimas de kelvin a unos pocos kelvins (excepto el hidrógeno). Para temperaturas bajas comparadas con $\Theta_R$, $T\ll \Theta_R$, podemos calcular $Z_{rot} porque sólo los primeros términos de la serie (5) son importantes. Por el contrario, a temperaturas altas, $T\gg \Theta_R$, todos los términos en (5) deben ser sumados.

En estos límites las contribuciones al calor específico pueden ser calculadas, obteniéndose:

$$
c_{v}=\left\{\begin{array}{ll}
12 k_{B}\left(\Theta_{R} / T\right)^{2} e^{-2 \Theta_{R} / T} & \text { si } T \ll \Theta_{R} \\
k_{B} & \text { si } T \gg \Theta_{R}
\end{array}\right.
$$


El applet en Java permite introducir las temperaturas de rotación y traslación y el programa calcula el calor específico por partícula (en unidades de $k_B$) representado frente a $\log T$. El applet dibuja dos líneas verticales, correspondientes a $\Theta_R$ y  $\Theta_V$y tres horizontales, que son las contribuciones de la parte traslacional (3/2), la rotacional (1) y la vibracional (1) cuando las temperaturas son mucho mayores que la rotacional y la vibracional respectivamente.

Si las temperaturas características están bien separadas, como sucede en la moléculas diatómicas reales, pueden observarse claramente las contribuciones de los tres hamiltonianos. El calor específico debido a la rotación muestra un máximo alrededor de $\Theta_R$.

Las temperaturas características para algunas moléculas diatómicas son:

$$
\begin{array}{ccccccccc}
 &  H &	N &	CO &	NO &	O &	Cl &	Br &	K \\
\Theta_R &  85 &	2.9 &	2.8 &	2.4 &	2.1 &	0.35 &	0.12 &	0.081 \\
\Theta_V  & 6200 &	3340 &	3070 &	2690 &	2230 &	810 &	470 &	140
\end{array}
$$

Fuente: Statistical Thermodynamics, J.F.Lee, F.W.Sears y D.L.Turcotte, Addison-Wesley (1963).

Pulsa [aquí](http://valbuena.fis.ucm.es/expint/html/fises/diatom/exp.html) para ver una gráfica con las medidas experimentales del calor específico a presión constante de los gases HD, HT y DT, donde D representa el deuterio y T el tritio, frente a la temperatura. La relación entre $C_p$ y $C_V$ para gases ideales es: $C_p = C_V + Nk_B$ y, por ello, los resultados para $C_p$ están desplazados una unidad.

En esta [gráfica](http://valbuena.fis.ucm.es/expint/html/fises/diatom/exp1.html) se comparan los resultados de la contribución vibracional al calor específico, Ec. (4) en función de $T/\Theta_V$ para varios gases.

Para moléculas triatómicas el análisis anterior se complica notablemente. Por ejemplo, el $CO_2$ tiene 4 modos normales de vibración. El texto de J.Kestin y J.R.Dorfman (A Course in Statistical Thermodynamics, Academic Press, 1971) incluye una [gráfica](http://valbuena.fis.ucm.es/expint/html/fises/diatom/exp3.html) con el calor específico de dicha molécula.