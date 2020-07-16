# 1 Limite Central
1. Generar N numeros aleatorios con media Mu y varianza Sigma del tipo seleccionado.
2. Construir Y = X_1 + X_2 +...+ X_N.
3. Dibujar la gaussiana.
4. Dibujar el histograma de frecuencias.

# 2. Anillo
1. Generar N casillas.
2. Disponer M sitios al azar.
3. Establecer cc periodicas.
4. Establecer ley de cambio de casilla al pasar por un sitio.
5. Mover casillas.

# 3. Transformaciones
#### Panadero
1. Dada una imagen:
   1. Contrae y expande cada eje correspondientemente
   2. Corta y pega la región que queda fuera.
   3. Mostrar la nueva imagen.
2. Si se pulsa **ITERA**, vuelve a 1.
3. Añadir una opción para iterar automáticamente, que pare cuando los puntos empiecen a colapsar.
##### Arnold
1. Dada una imagen y unos valores:
   1. Calcular el cuarto valor.
   2. Aplicar la transformación.
   3. Mostrar la nueva imagen.
2. Si se pulsa **ITERA**, vuelve a 1.
3. Añadir una opción para iterar automáticamente, que pare cuando los puntos empiecen a colapsar.

# 4. Osciladores
1. Genera N osciladores (N frecuencias y N fases) con la distribución seleccionada.
2. Dibuja el círculo y la gráfica de la entropía.
3. Evoluciona el sistema.
4. Dibuja los cambios y vuelve a 3.
5. Si se pulsa **HISTOGRAMA** para la evolución y muestra la distribución de frecuencias frente a la entropía en escala logarítmica.

# 5. Ising 1
(Ver 11. Ising 2)
1. Dibujar los calores específicos exactos de las redes 1 y 2 dimensionales infinitas.
2. Dibujar el calor para la red elegida.

# 6. Diatómicas
1. Dibujar lineas verticales para las temperaturas de rotación y de vibración.
2. Dibujar las lineas horizontales.
3. Calcular las contribuciones al calor específico y dibujarlas.

# 7. Debye
1. Para una temperatura de Debye, obtener la función del calor específico.
2. Dibujar en la gráfica con los ejes bien ajustados.

# 8. Fluctuaciones
#### Sin interacción
1. Para N y T, simular gas:
   1. La velocidad viene dada por una distribución gaussiana que viene dada por la temperatura.
   2. Cada partícula evoluciona hasta chocar con una pared, y entonces rebota elásticamente.
2. Mostrar número de partículas en cada lado de la caja.
3. Mostrar las lineas horizontales relacionadas con la gaussiana,
4. El botón **HISTOGRAMA** muestra la distribución de partícular.
5. Añadir una opción para evolucionar rápidamente.

#### Con interacción
1. Para N y T, simular gas:
   1. La velocidad viene dada por una distribución gaussiana que viene dada por la temperatura.
   2. Cada partícula evoluciona hasta chocar con una pared o con otra partícula, y entonces rebota elásticamente.
2. Mostrar número de partículas en cada lado de la caja.
3. Mostrar las lineas horizontales relacionadas con la gaussiana,
4. El botón **HISTOGRAMA** muestra la distribución de partícular.
5. Añadir una opción para evolucionar rápidamente.

# 9. Macrocanónica
1. Para N y T, simular gas...
2. Para la región seleccionada contar el número de partículas.

# 10. Estadísticas
1. Simulación de Monte Carlo de bosones y fermiones.
2. **HISTOGRAMA** muestra las ocupaciones de niveles energéticos.

# 11- Ising 2
1. Genera la red, aleatoria o todos los espines iguales.
2. Evoluciona el modelo:
   1. Simulación de Monte Carlo, comprobar contribuciones del resto de espines.
   2. Cambiar (o no, dependiendo de la T) la orientación de los sitios.
   3. Calcula la magnetización
3. Repite 2.
