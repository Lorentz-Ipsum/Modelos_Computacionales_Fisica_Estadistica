Como breve resumen, he empezado con los códigos de las simulaciones, los mayores problemas son la optimización del código para aumentar la velocidad de procesado (estoy usando una librería llamada Cython, pero me han recomendado otras alternativas) y plotear el estado de la simulación correctamente.

- Códigos en Java
- Centrar la memoria en Ising / Macrocanónica / Transiciones de Fase
- Hosting
- Más simulaciones con entropía (Parrondo Paradox?)
- Generación de números en JS
- Biblioteca de visualización
-
## Repaso de física estadística
Estoy siguiendo [este curso de Stanford de Leonard Sussking](https://www.youtube.com/playlist?list=PL_IkS0viawhr3HcKH607rXbVqy28W_gB7) para recordar conceptos que tengo algo olvidados y sacar ideas.
> ¿Algún otro curso que pueda ser de utilidad?

- ¿Por qué tienen relevancia las transformaciones del panadero y de arnold en el contexto de los applets?
  - Posible relación con los de los gases. Mitad del recipiente...
  -

### Referencias bibliográficas
- K. Huang.

## Dudas de las explicaciones
4. Osciladores:
  - De dónde sale la fórmula de Einstein?

5. Ising
  - Cómo dibujo las gráficas?
  - Intento desarrollar matemáticamente una expresión?

---
## Aspectos técnicos
### Códigos originales

### Pseudocódigos
Estoy trabajando en el pseudocódigo de todas las simulaciones, especialmente para entender los pasos a seguir en las más complicadas. Creo que sería buena idea implementarlo en la página.

### Archivos .html

### Servidor

---
## Sobre la estructura de la memoria y los applets
### Modelo de Ising y mejora de la velocidad con Cython
Hay dos applets que tratan sobre el modelo de Ising, y he pensado que sería interesante juntarlos ambos en uno y explicarlo todo junto, como un aspecto central del TFG.Siguiendo [este tutorial](http://jakevdp.github.io/blog/2017/12/11/live-coding-cython-ising-model/) he recreado un modelo de Ising que se ejecuta en bastante rápido, incluso para redes grandes como 500x500. Aún no he jugado mucho con él pero planeo implementar más sliders y la opción p
