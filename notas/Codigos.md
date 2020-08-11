
# Pseudocódigos

## 1 Limite Central
1. Generar N numeros aleatorios con media Mu y varianza Sigma del tipo seleccionado.
2. Construir Y = X_1 + X_2 +...+ X_N.
3. Dibujar la gaussiana.
4. Dibujar el histograma de frecuencias.

### Widgets necesarios


### Funciones necesarias

```python
import random
random.seed(123)

def Genera_Dado():
    result = random.randint(1,6)
    return result

def Genera_Moneda():
    result = random.randint(0,1)
    return result

def Genera_Continuo():
    result = random.uniform(0,1)
    return result

def Generar_Variable_Suma(N, Tipo):
    Y = 0
    if Tipo == 'Dado':
        for i in range(N):
            X = Genera_Dado()
            Y += X
    if Tipo == 'Moneda':
        for i in range(N):
            X = Genera_Moneda()
            Y += X
    if Tipo == 'Continuo':
        for i in range(N):
            X = Genera_Continuo()
            Y += X
    return Y
```

## 2. Anillo
1. Generar N casillas.
2. Disponer M sitios al azar.
3. Establecer cc periodicas.
4. Establecer ley de cambio de casilla al pasar por un sitio.
5. Mover casillas.

```python
# Imports
import numpy as np
import random

import matplotlib.pyplot as plt
import matplotlib.animation as animation
from matplotlib import style

# Definamos funciones
def Genera_Anillo(N, M, condicion = "random"):
    X = []
    S = (random.sample(range(1,N), M))
    for i in range(0, N):
        X.append(bool(random.getrandbits(1)))
        # comprobar que no se repitan sitios, si se repite uno, volver a generarlo
    return X, S

def Anillo_Step(X, S):
    X_s = []
    for i in range(0, len(X)):
        if i in S:
            X_s.append(not X[i-1])
        else:
            X_s.append(X[i-1])
    return X_s

# Probemos a plotearlo
fig = plt.figure(figsize=(5,10))
ax = plt.subplot(111, projection = 'polar')

ax.set_theta_zero_location('N')
ax.set_yticklabels([])

def Plot_Anillo(X, X_t, S):
    #fig = plt.figure(figsize=(5,10))
    #ax = plt.subplot(111, projection = 'polar')

    ax.clear()

    r = 1
    theta = 2 * np.pi / len(X)

    ax.set_xticks(np.arange(0, 2*np.pi, theta))
    ax.set_xticklabels(range(1,N+1))

    for i in range(0, len(X)):
        if X[i] == True:
            plt.polar(theta*i, 1, 'bo')
        elif X[i] == False:
            plt.polar(theta*i, 1, 'ro')

        if X_t[i] == True:
            plt.polar(theta*i, 0.8, 'bo')
        elif X_t[i] == False:
            plt.polar(theta*i, 0.8, 'ro')

    for i in range(0, len(S)):
        plt.polar(theta*S[i] - theta / 2, 0.9, 'go')
    plt.show()

# Probemoslo
N = 50
M = 5

X, S = Genera_Anillo(N, M)
Plot_Anillo(X, X, S)

X_1 = Anillo_Step(X, S)
Plot_Anillo(X, X_1, S)
```

## 3. Transformaciones
### Panadero
1. Dada una imagen:
   1. Contrae y expande cada eje correspondientemente
   2. Corta y pega la región que queda fuera.
   3. Mostrar la nueva imagen.
2. Si se pulsa **ITERA**, vuelve a 1.
3. Añadir una opción para iterar automáticamente, que pare cuando los puntos empiecen a colapsar.
### Arnold
1. Dada una imagen y unos valores:
   1. Calcular el cuarto valor.
   2. Aplicar la transformación.
   3. Mostrar la nueva imagen.
2. Si se pulsa **ITERA**, vuelve a 1.
3. Añadir una opción para iterar automáticamente, que pare cuando los puntos empiecen a colapsar.

## 4. Osciladores
1. Genera N osciladores (N frecuencias y N fases) con la distribución seleccionada.
2. Dibuja el círculo y la gráfica de la entropía.
3. Evoluciona el sistema.
4. Dibuja los cambios y vuelve a 3.
5. Si se pulsa **HISTOGRAMA** para la evolución y muestra la distribución de frecuencias frente a la entropía en escala logarítmica.

## 5. Ising 1
(Ver 11. Ising 2)
1. Dibujar los calores específicos exactos de las redes 1 y 2 dimensionales infinitas.
2. Dibujar el calor para la red elegida.

## 6. Diatómicas
1. Dibujar lineas verticales para las temperaturas de rotación y de vibración.
2. Dibujar las lineas horizontales.
3. Calcular las contribuciones al calor específico y dibujarlas.

## 7. Debye
1. Para una temperatura de Debye, obtener la función del calor específico.
2. Dibujar en la gráfica con los ejes bien ajustados.

## 8. Fluctuaciones
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

## 9. Macrocanónica
1. Para N y T, simular gas...
2. Para la región seleccionada contar el número de partículas.

## 10. Estadísticas
1. Simulación de Monte Carlo de bosones y fermiones.
2. **HISTOGRAMA** muestra las ocupaciones de niveles energéticos.

## 11- Ising 2
1. Genera la red, aleatoria o todos los espines iguales.
2. Evoluciona el modelo:
   1. Simulación de Monte Carlo, comprobar contribuciones del resto de espines.
   2. Cambiar (o no, dependiendo de la T) la orientación de los sitios.
   3. Calcula la magnetización
3. Repite 2.
