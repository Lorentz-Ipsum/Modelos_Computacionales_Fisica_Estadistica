


---
## Objetivos
El objetivo de este trabajo es investigar la posibilidad de utilizar herramientas computacionales como un apoyo docente para transmitir ciertos conceptos de una forma más clara.

Para ello, se han realizado 10 simulaciones o experimentos interactivos que profundizan
sobre ciertos conceptos de Física Estadística.

### Requerimientos
Estos son los requisitos mínimos que se han  considerado importantes para determinar que las
se han completado satisfactoriamente:

Facilidad de uso, y que sean accesibles desde cualquier ordenador.

Codigo libre o abierto, para que sea sencillo complementar el aprendizaje con los aspectos técnicos en caso de que fuera necesario.

Tambien queríamos desarrollar un método fácilmente ampliable, por si en el futuro alguien quisiera continuar con el proyecto.

Y finalmente que las explicaciones sean claras y sencillas, pero con suficiente nivel para que los estudiantes tengan que reflexionar sobre las ideas presentadas.

### Funcional
En cuanto al aspecto técnico del código, elegimos programación funcional, por ser uno de los paradigmas más sencillosy que más similitudes tiene con la física:

Definimos unas variables, disponibles en todo momento, que vamos tratando, haciéndolas pasar por funciones, hasta obtener unos datos que podemos representar gráficamente.

### Posibilidades
Con el avance de las tecnolgías, el número de posibilidades para programar unas simulaciones de este tipo era enorme,

Inicialmente empezamos a programarlas en Jupyter Notebooks, que son una herramienta muy común actualmente, pero nos encontramos con varias dificultades. Es difícil hacerlos interactivos, y requieren de cierto conocimiento técnico para usarlos correctamente.

Así que, se decidió portar los códigos a JavaScript y acabar de realizar la página y las explicaciones en HTML.

### En la web

El resultado está disponible en la URL que se incluye en la memoria, y creemos que es lo suficientemente atractivo
como para despertar el cierto interés en los estudiantes.

---
## Aspectos técnicos

Los dibujos de las simulaciones en sí se han hecho directamente sobre un marco de HTML especificando las posiciones.







---
## Repaso de Física Estadística
La motivación detrásde la creación de estas simulaciones esta directamente relacionado con algunas de las caracteristicas fundamentales de la física estadística:

El gran número de grados de libertad de los sistemas que estudia la Física Estadística hacen difícil visualizar los conceptos directamente, y además requiere introducir ciertas aproximaciones e hipótesis complejas que pueden resultar difícil de entender, por ejemplo:

Como se compatibilizan las descripciones microscópica reversible y macroscópica irreversible.

Que impllica introducir la hipotesis del caos molecular

Como afecta la manera de tratar macroscopicamente un sistema a la forma en que se expresa la entropía

### eq vs no eq
Tenemos, además, otra capa de complejidad, y es que es diferente la fises del eq y la del no eq

Por un lado, en el eq, que es la que se estudia en la carrera, tenemos un paradigma general con el que tratar los problemas,
atendiendo a la manera en que podemos expresar las variables macroscópicas, pudiendo estudiarlos según ciertas colectividades.

Por otro lado, en la físes del no eq, dicho paradigma deja de ser efectivo, haciendose necesario tratar cada problema de forma
distinta. Es donde la Física Computacional toma mayor relevancia.


### conceptos
Con todo esto, Estos son algunos de los conceptos que consideramos susceptibles de confundir a un estudiante novel.

La hippótesis del caos molecular, La definición y maximización de entropía, la ergodicidad, el límite termodinámico...







---
## Applets
Estos son los modelos que se han simulado finalmente. Los he agrupado por temática general y en cierta medida por la dificultad que plantea, pero podrían ordenarse de otras formas.

Para asegurarme de que funcionen, estas diapositivas usan gifs, al final de la presentación están las simulaciones pra ejecutarlas en directo y probar parámetros.

### estructura
Hemos estructurado las explicaciones de forma que la simulación esté al final, para que se pueda empezar a usarla con conocimiento de causa.

### Pres
en esta presentación no daría tiempo de explicar cada modelo en profundidad, así que hemos elegido dos que son característicos, mientras que los demás nos limitaremos a dar unas pinceladas generales.








---
### Limite Central
El teorema del límite central se usa ampliamente en física y es la razón por la que la distribución gaussiana tiene un papel tan importante.

Con él, ilustramos cómo el comportamiento colectivo de muchas variables puede llevar a comportamientos en principio contraintuitivos.

Ver cómo las variables se acumulan literalmente en la gráica es una buena forma de cerciorarse de la efectividad del teorema.

### Anillo de Kac
El Anillo de Kac es un modelo sencillo con el que bocetamos una primera idea de la irreversibilidad macroscópica y la hipótesis del caos molecular, ademas sirve como una introducción a la ergodicidad y al periodo de recurrencia de Poincaré.

Hace especial hincapié em cómo se resuelve la paradoja de Lochsmidt antes mencionada, ilustrando la compatibilidad macroirrev microrrev, ya que la dinamica micro es periodica pero para resolver el movimiento debemos introducir una hipotesis macro, que nos da una solucion irrevers.

El problema es la hipótesis que hemos tomado, que elimina las correlaciones en el sistema, del mismo modo que la hipótesis del Caos Molecular, nos permite obtener una solución a las ecuaciones de evolución, pero en este caso deja de ser válida al cabo de poco tiempo. Para mantener su validez necesitaríamos un sistema mucho más grande, o deberíamos modificar el modelo, por ejemplo haciendo que las marcas cambien de posición pasado cierto tiempo.


### Osciladores Armónicos
Con esta simulación ilustraremos el concepto de entropía, y veremos que depende de la elección macroscópica con que estudiamos nuestro sistema, el llamado coarse-graining. Además, ilustra la causa de la tendencia al equilibrio y a estados de máxima entropía

veamoslo en profundidad:

---

Tenemos N osciladores armónicos, cada uno determinado por su frecuencia y su fase inicial.

Si las frecuencias son conmensurables, es decir, su fracción es un número racional, el sistema será periódico. Nos interesa el caso ergódico, de forma que el sistema tienda a un estado de equilibrio o de entropía máxima, en el que las fluctuaciones de entropía serán mínimas. Por ello, nos importa que las freq sean inconmensurable

Para analizar la entropía del sistema necesitamos observarlo con cierto grado de incertidumbre. ya que Si todos los estados son únicos, no habrá ninguno con mayor entropía que otro.

Entonces, en este caso, dividimos la circunferencia en que se mueven los osciladores en M sectores, y contabilizamos cúantos osciladores hay en cada uno en cada instanste de tiempo. El marcoestado de nuestro sistema serán los M números alpha_i que nos indican la concentración o densidad por sectores de osciladores.

Así, podemos definir la entropía en función de estas variables alfa, y sabiendo que el estado de entropía máxima será aquel en que los osciladores están uniformememente deistribuidos, deducimos que dependerá de  N y M.

---

La simulación nos permite ilustrar esto perfectamente.

Si partimos de un estado de entropía máxima haciendo las fases aleatorias, no importa cómo elijamos las frecuencias, las fluctuaciones de la entropía serán pequeñas.

Por otro lado, si partimos de un estado ordenado en que las fases son nulas, tenemos dos comportamientos muy marcados:

Si las frecuencias son conmensurables el sistema es periódico, y las fluctuaciones de entropía son muy grandes. Además, el máximo de entropía no se manifiesta, pudiendo distribuirse todos los osciladores uniformemente digamos, de forma perfecta.

Elegir frecuancias aleatorias es en buena medida equivalente a tomarlas inconmensurables, y así vemos que efectivamente el sistema evoluciona hasta un estado de equilibrio con fluctuaciones pequeñas.

---

la forma en que estudiamos el sistema afecta a la entropía máxima.
Para ver mejor el máximo de entropía es conveniente elegir M grande.

para frecuencias equiespaciadas variar M no cambia la entropía máxima, sino la forma de la oscilación colectiva.

Se confirma una de las ideas de bolzman sobre la entropía: la tendencia a estados de más entropía es consecuencia de partir de estados ordenados.

### Transformaciones en el Espacio de Fases
Estas dos simulaciones siguientes son muy similares, y por eso las hemos agrupado en la misma sección.

Ambas ilustran el concepto de caos determinista, y funciones de evolución en el espacio de fases.

Los casos que veremos consisten en transformaciones que actúan sobre el cuadrado unidad y conservan el area. Además, son mixing, es decir, redistribuyen los puntos el estado inicial hasta llegar a un estado homogéneo.

En la transformacion de arnold, además, al tener el caso discreto, aparece una periodicidad, que podría relacionarse con el per de recurr de Poin


### Gas ideal
Pasamos ahora a otras dos simulaciones similares, que tratan de uno De los primeros modelos estudiados por la fises, el gas ideal.

#### Irreversibilidad
Este en especial, de la expansion libre, es un ejemplo típico de las clases de termodinámica.

El modelo es por todos conocido, un recipiente dividido en dos mitades por una pared. Una de las mitades contiene un gas, que a partir del instante inicial es libre de moverse por todo el recinto.

La experiencia nos dice que el gas se expande hasta llenar todo el recipiente y nunca ocurre la secuencia inversa. Pero, ¿por qué? aquí Lo demostramos usando combinatoria.

Los resultados que deducimos son que la irrevesibilidad macroscópica también es consecuencia del gran número de grados de libertad que tenemos en un sistema. Para un gas de pocas partñiculas es más probable encontrarlas todas en un lado del recipiente, es decir, las fluctuaciones en el equilibrio dependen del tamaño del sistema.

#### Colectividad Macrocanónica
Esta simulación ayuda a comprender cómo el tamaño de un sistema en contacto con un foco térmico o de partículas afecta a su dinámica. Si no se cumple la condición de N>>NF, el formalismo macrocanónico no es aplicable.


### Bosones y fermiones
Con esta simulación introducimos explicitamente un método computacional de uso común, el de Monte Carlo. Y gracias a él podemos deducir el comportamiento de un conjunto de partículas cuánticas sin recurrir en nigún momento a la física estadística.

Es un ejemplo de cómo los métodos computacionales sirven para complementar los resultados teóricos y experimentales.

---

Tenemos dos sistemas, uno con bosones y otro con  fermiones.

Por las propiedades de simetría de la funcion de onda, sabemos que los niveles de energía bosónicos pueden estar ocupados por muchas partículas, y los fermiçónicos, solo por una.

La físes nos dice que la función de partición macrocanónica tiene esta forma, y podemos deducir que las estadísticas en los números de ocupación siguen estas distribuciones.

El algoritmo de metrópolis nos permite obener este comportamiento:
- Iteramos sobre las partículas, y comparamos el nivel de energía de una aprtícula con el de otro nivel elegido al azar.
- Si la energía es menor, cambiamos la partícula de sitio, si es menor, la cambiamos de sitio con probabilidad proporcional a la diferencia de energía, teniendo en cuenta la temperatura.

De esta forma, podemos cambiar el número de partícualas y niveles,

Y obtenemos la misma distribución de los niveles de ocupación que la calculada usando el formalismo.
Además, se reproduce el fenómeno de condensación de BE, sin necesitar añadir ningún codigo adicional.

### Modelo de Ising
El mod ising es un Modelo ideal para explicar las transiciones de fase.
Modelizamos un metal como una red de espines, y estudiamos la magnetización macroscópica del sistema. La física estadística nos dice que por debajo de cierta temperatura, aparecerá una magnetización espontánea, ya que el magnetismo entre espines será mas fuerte que las fluctuaciones térmicas, que tienden a desordenar los espines de la red.

#### Magnet
En este primer experimento presentamos explícitamente la simulación microscópica junto a la medición macroscópica, lo cual permite hacerse una buena idea de lo que ocurre en el modelo.

#### LT
Esta segunda experiencia del modelo de ising quiere analizar más a fondo dicha transición de fase. Resulta que para una dimensión, no existe. Lo ejemplificamos viendo que el calor específico en ese caso es contínuo. Para redes finitas, esto también es así. Sólo cuando tomamos el l´miite termodinámico, acercándonos a sistemas cada vez mas grandes, empieza a aparecer la discontinuidad característica de una transicion de fase.







# Conclusiones
Para concluir,

las herramientas interactivas de este tipo son muy útiles para transmitir conceptos complicados de todo tipo.
De hecho, ya que gran parte, por no decir todos, de los avances que se realizan en física actualmente
tienen que ver de alguna forma con la computación, sería interesante ver papers o libros de física interactivos
que vayan más allá del simple movimiento en caida libre, tan comúnmente extendido.

Más allá de explicar con pizarra y tiza, usar herramientas de este tipo para que el estudiante, ya sea desde casa o en clase, se familiarice con los modelo usando el ordenador, es algo cada vez más interesante. Queda patente por ejemplo con los sucesos de este año.

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
