
---
## Introducción

La física Estadística es un campo de estudio de especial dificultad. Para tratar los sistemas que

  El problema que queremos tratar en este trabajo tiene que ver con ciertas dificultades que pueden presentarse al estudiante a la hora de estudiar por primera vez los conceptos la Física Estadística.

---

Estos problemas tienen que ver con el enfoque que se toma en Física Estdística: Al tratar sistemas con muchos grados de libertad hay que introducir aproximaciones muy específicas.

El gran número de grados de libertad de los sistemas que estudia hacen difícil visualizar los conceptos directamente *de cabeza*, y hacen los cálculos *bastante difíciles*.

En Física Estadística se utilizan modelos computacionales constantemente. Por ello, familiarizarse con el entorno informático es imprescindible.

Además de resolución numérica, las simulaciones por ordenador ayudan a comprender los entresijos de ciertos modelos.

Esto puede ser una gran ayuda, tanto para el estudiante como para el docente, a la hora de comprender los conceptos más complicados.

Más allá de explicar con pizarra y tiza, usar herramientas de este tipo para que el estudiante, ya sea desde casa o en clase, se familiarice con los modelo, es algo cada vez más interesante.
Queda patente con los sucesos de este año.

Planteamos 10 simulaciones en las que se ilustran algunos conceptos que hemos considerado más delicados.

---


El gran número de grados de libertad de los sistemas que estudia la Física Estadística hacen difícil visualizar los conceptos directamente,
y hacen los cálculos más complicados.
En Física Estadística se utilizan modelos computacionales constantemente. Por ello, familiarizarse con el entorno informático es imprescindible.
Además de resolución numérica, las simulaciones por ordenador ayudan a comprender los entresijos de ciertos modelos.
Esto puede ser una gran ayuda, tanto para el estudiante como para el docente, a la hora de comprender los conceptos más complicados.
Más allá de explicar con pizarra y tiza, usar herramientas de este tipo para que el estudiante, ya
sea desde casa o en clase, se familiarice con los modelo, es algo cada vez más interesante. Queda patente con los sucesos de este año.
Planteamos 10 simulaciones en las que se ilustran algunos conceptos que hemos considerado adecuados, y susceptibles de simular sin muchas dificultades por un estudiante interesado en la programación.





---
## Motivación

Es decir, lo que queremos es investigar la posibilidad de utilizar herramientas computacionales para ayudar a los docentes a transmitir ciertos conceptos de una forma más clara.

---
### Objetivos

Para ello, se han realizado una serie de simulaciones interactivas que ilustran algunos de estos conceptos que se han considerado de mayor dificultad.

---
### Requerimientos

Estos son requisitos mínimos que consideramos importantes.

Facilidad de uso, que no sea necesario instalar nada.

Codigo libre, para que sea sencillo ver el comportamiento de las simulaciones usando el código.

Queríamos desarrollar un método sencillo de ampliar y extender una vez listo, por si en el futuro alguien quisiera continuar con el proyecto.

Y que las explicaciones sean claras y sencillas, pero con suficiente nivel para que los estudiantes tuvieran que reflexionar sobre las ideas presentadas.

El paso evidente era crear una página web en la cual alojar las simulaciones. De esta forma estarían accesibles siempre y desde cualquier tipo de dispositivo.

---
### Posibilidades
Con el avance de las tecnolgías, el número de posibilidades para programar unas simulaciones de este tipo era enorme, así que nos restringimos a estas:

Los Jupyter Notebooks son una herramienta muy común actualmente. Inicialmente empezamos a programar en Python con ellos, pero
 nos encontramos con varias dificultades. Aunque hay formas de subirlos a la web, es difícil hacerlos interactivos,
 y requieren de cierto conocimiento técnico para usarlos correctamente.

Entonces se pensó en subir directamente los códigos de Python usando Flask, una librería que permite crear una interfazz gráfica y desplegarla en un servidor, pero eso chocaba con uno de los objetivos del proyecto, el de usar el menor número de librerías posible.

Finalmente, se decidió portar los códigos a JavaScript y realizar la página y las explicaciones en HTML.

### En la web

El resultado está disponible en la URL que se incluye en la memoria.

---
## Aspectos técnicos

Los dibujos de las simulaciones en sí se han hecho directamente sobre un marco de HTML especificando las posiciones.







---
## Repaso de Física Estadística
Bueno, antes de pasar a las simulaciones es conveniente hablar de cuál es la motivación detrás
de la creación de estas simulaciones

¿Cómo pueden emerger fenómenos irreversibles a partir de modelos microscópicos reversibles?

### Características de la Física Estadística<

- Muchas partículas
- Aproximaciones complicadas
- Hipótesis complejas

---
### Física Estadística del Equilibrio

### Física Estadística del No Equilibrio
Colectividades dejan de ser efectivas
Es donde la Física Computacional toma mayor relevancia, permitiendo por ejemplo encontrar solución a ecuaciones que de otro modo serían imposibles de resolver.















---
## Applets

Estos son los modelos que se han simulado finalmente. He tenido que quitar dos que en principio iba a incluir por falta de espacio.

Para asegurarme de que funcionen, estas diapositivas usan gifs, al final de la presentación están las simulaciones pra ejecutarlas en directo y probar parámetros.










---
### Limite Central
El teorema del límite central se usa ampliamente en física y es la razón por la que la distribución gaussiana tiene un papel tan importante.
Nos servirá para ilustrar cómo el comportamiento colectivo de muchas variables puede llevar a comportamientos contraintuitivos.

Este es el enunciado del teorema: Tenemos N variables aleatorias, todas siguiendo la misma distribución de probabilidad, de media y varianza finitas.
El teorema del limite central nos dice que una variable Y definidad como la suma de las anteriores seguirá una distribución gaussiana de media $\mu N$ y varianza $\sigma^2 /N$.

En la simulación podemos elegir N y la distribución con que se generan las variables X.
Si sólo tenemos una variable se puede ver cómo la distribución individual es equiprobable, y al aumentar N obtenemos, efectivamente, una gaussiana.

Una simulación como esta es la mejor forma de comprobar la efectividad del teorema.

Es posible ampliar la simulación añadiendo distribuciones asimétricas.


### Anillo de Kac
El Anillo de Kac es un modelo sencillo con el que bocetaremos una primera idea de la irreversibilidad macroscópica y la hipótesis del caos molecular, así como una introducción al periodo de recurrencia de Poincaré.

El modelo consiste en un anillo dividido en N casillas, cada una ocupada por una bolita roja o azul. En cada paso temporal movemos las bolitas a la casilla contigua en el sentido de las agujas del reloj. Entre las casillas elejimos M sitios y los marcamos, de forma que cuando una bolita pasa por uno de estas marcas, cambia de color.

La dinámica es, entonces, reversible y periódica. Pasados 2N instantes, cada bolita ha cambiado de color un número par de veces y ha vuelto a su posición inicial.

Para estudiar el sistema, definimos unas funciones que cuantifican el número de bolitas de cada color, expresadas por letra mayúscula, y el número de bolitas de cada color que tienen un sitio marcado delante, y por tanto que cambiarán de color en el siguiente instante de tiempo.

Obtenemos estas ecuaciones de evolución, que no son resolubles.

Para resolverlas necesitamos una solución adicional: Como la fracción total de bolitas con un sitio marcado delante permanece constante, consideraremos que esta fracción será constante también si sólo miramos a las bolitas de un color específico.

Así, podemos resolver las ecuaciones restandolas entre sí y obtenemos una solución que no es periódica ni reversible, en contradicción con el modelo de partida.

Antes de resolver esta cuestión veamos la simulación.

Podemos elegir N y M, así como los colores iniciales de las bolitas.

Al principio de la evolución el sistema sigue la solución obtenida, pero al dejarlo evolucionar aparece la solución periódica.

El problema es la hipótesis que hemos tomado, que elimina las correlaciones en el sistema, del mismo modo que la hipótesis del Caos Molecular, nos permite obtener una solución a las ecuaciones de evolución, pero en este caso deja de ser válida al cabo de poco tiempo. Para mantener su validez necesitaríamos un sistema mucho más grande, o deberíamos modificar el modelo, por ejemplo haciendo que las marcas cambien de posición pasado cierto tiempo.


### Osciladores Armónicos

la forma en que estudiamos el sistema afecta a la entropía máxima. para frecuencias equiespaciadas variar M no cambia la entropía máxima, sino la forma de la oscilación colectiva.

---

Con esta simulación ilustraremos el concepto de entropía, y veremos que depende de la elección macroscópica con que estudiamos nuestro sistema, el llamado coarse-graining.

Tenemos N osciladores armónicos, cada uno determinado por su frecuencia y su fase inicial.

Si las frecuencias son inconmensurables, es decir, su fracción no es un número racional, el sistema no será periódico, tendiendo por tanto a un estado de equilibrio o entropía máxima, en el que las fluctuaciones de entropía serán mínimas.

Para definir la entropía de un sistema necesitamos observarlo con cierto grado de incertidumbre. Si todos los estados son únicos, no habrá ninguno con mayor entropía que otro.

Para ello, en este caso, dividimos la circunferencia en que se mueven los osciladores en M sectores, y contabilizamos cúantos osciladores hay en cada uno en cada instanste de tiempo. El marcoestado de nuestro sistema serán los M números que nos indican la concentración de osciladores.

Así, podemos definir la entropía en función de estas variables alfa, y sabiendo que el estado de entropía máxima será aquel en que los osciladores están uniformememente deistribuidos, deducimos que dependerá de  N y M.

La simulación nos permite ilustrar esto perfectamente.

Si las frecuencias son conmensurables el sistema es periódico, y las fluctuaciones de entropía son muy grandes.

Elegir frecuancias aleatorias es en buena medida equivalente a tomarlas inconmensurables, y así vemos que efectivamente el sistema evoluciona hasta un estado de equilibrio con fluctuaciones no muy grandes.

Para ver mejor el máximo de entropía es conveniente elegir M grande.


### Transformaciones en el Espacio de Fases
Las dos simulaciones siguientes son muy similares, y por eso las hemos agrupado en la misma sección.

Ambas ilustran el concepto de caos determinista, relativo a las funciones de evolución en el espacio de fases.---

Los casos que veremos consisten en transformaciones que actúan sobre el cuadrado unidad y conservan el area. Además, son mixing, lo que quiere decir que redistribuyen los puntos el estado inicial hasta llegar a un estado homogéneo.

#### Transformación del panadero
La primera, y más sencilla, es la transformación del panadero.
#### Transformación de Arnold




### Gas ideal
De los primeros modelos estudiados, el gas ideal es el que primero en que se produjeron avances en este tema.

#### Irreversibilidad
El modelo es por todos conocido, un recipiente dividido en dos mitades por una pared. Una de las mitades contiene un gas, que a partir del instante inicial es libre de moverse por todo el recinto.

La experiencia nos dice que el gas se expande hasta llenar todo el recipiente y nunca ocurre la secuencia inversa. Pero, ¿por qué? Lo demostraremos usando combinatoria.

Empezamos considerando la probabilidad general de encontrar Nd particulas en el lado derecho y Ni en el izquierdo. Esta probabilidad sigue una distribución binomial.

Si comparamos la posibilidad de encontrar todas las partículas concentradas en una región frente al estado más probable de la mitad de partículas en cada lado tenemos, usando la aproximación de Stirling, que la probabilidad de encontrar todas las partículas en la misma mitad disminuye exponencialmente con el número de partículas.

#### Colectividad Macrocanónica


### Bosones y fermiones


### Modelo de Ising
Modelo ideal para explicar las transiciones de fase.
#### Magnet

#### LT








# Conclusiones
Para concluir,

las herramientas interactivas de este tipo son muy útiles para transmitir conceptos complicados de todo tipo.
De hecho, ya que gran parte, por no decir todos, de los avances que se realizan en física actualmente
tienen que ver de alguna forma con la computación, sería interesante ver papers o libros de física interactivos
que vayan más allá del simple movimiento en caida libre, tan comúnmente extendido.

Como queda demostrado por la relativa abundancia de este tipo de simulaciones, permiten extender la imaginación
del estudiante, y por tanto, la aumentar su comprensión del tema.

El hecho de informarse sobre un modelo e intentar simularlo constituye una tarea muy estimulante y que
contribuye a desarrollar el pensamiento científico, al obligar al programador a plantearse muy a fondo la teoría.

Sería interesante que este tipo de trabajos se popularizara, en ciertos casos podría permitir al propio investigador
a encontrarse con comportamientos inesperados.

## Trabajo futuro
Finalmente, como extensión a este trabajo, se podría...

Adaptar las simulaciones para que sean funcionales en todo tipo de dispositivos,
ya que no he conseguido aún que algunas partes escalen bien con el tamaño de la ventana.

Al no ser un experto en programación, algunos códigos pueden resultar algo confusos en este momento.
Habría que limpiarlos y refactorizarlos para que sirvan mejor de ejemplo.
Se ha intentado añadir unos pseudocódigos de las simulaciones,
pero por falta de tiempo no se han podido completar satisfactoriamente.

Formular explicaciones adecuadas para el nivel justo de conocimientos de un tema
que tiene la audiencia es complicado. En este trabajo las hemos pasado muy por encima,
pero queda mucho espacio para la ampliacion, de forma que sean más rigurosas
y acorde con los contenidos de la asignatura.

Evidentemente, en este trabajo nos hemos centrado en física estadística,
pero el enfoque podría ampliarse a todos los campos de la física.


# Cosas

(Si tardo mucho) Bueno, es la primera vez que hago una presentación de esta forma telemática así que quizá me haya trabado un poco.

Importante decir que la temperatura está reescalada, ya que estamos tomando kB = 1 y la temperatura dependerá de cómo elijamos las unidades de la velocidad.

El enorme número de partículas con que se trata en física estadística hace muy dificil comprender lo que está pasando en el sistema. Estos experimentos interactivos pretenden sar luz sobre estos conceptos. No se trata por tanto de demostrarlos, sino de ilustrarlos.
