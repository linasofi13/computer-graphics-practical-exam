# Clipping en 3D - Cohen-Sutherland Extendido

## Información del Proyecto

**Materia:** Computación Gráfica ST0275  
**Profesor:** Helmuth Trefftz  
**Universidad:** EAFIT  
**Integrantes:**
- Alejandro Rios
- Lina Ballesteros

## Descripción

Este programa implementa el algoritmo **Cohen-Sutherland extendido a 3D** para recorte de líneas en un volumen cúbico. El programa visualiza:

- Una **casita (house)** dibujada en azul claro
- Un **cubo de recorte** dibujado en verde
- Un **segmento de línea** que cambia de color según el resultado del algoritmo de clipping:
  - **Azul**: Rechazo trivial (completamente fuera del cubo)
  - **Rojo**: Aceptado o recortado (dentro o intersecta el cubo)

## Requisitos Implementados

✅ Utilización del código base para dibujar la casita y proyectarla sobre el plano Z = -100  
✅ Extrapolación del algoritmo Cohen-Sutherland a 3D  
✅ Cubo de recorte definido por P0 <0,0,-200> y P1 <100,100,-300>  
✅ Cubo dibujado con segmentos de recta de color verde  
✅ Segmento de línea desde P2 <0,0,-100> (fijo) hasta P3 <50,50,-199> (móvil)  
✅ Evaluación del segmento con algoritmo Cohen-Sutherland  
✅ Color AZUL para rechazo trivial  
✅ Color ROJO para todos los otros casos  
✅ Proyección sobre el plano Z = -100  
✅ Movimiento del punto P3 con el teclado  

## Controles

- **A / D**: Decrementar / Incrementar coordenada X del punto P3
- **W / S**: Incrementar / Decrementar coordenada Y del punto P3
- **E / Q**: Decrementar / Incrementar coordenada Z del punto P3

## Compilación y Ejecución

### Prerrequisitos
- Java JDK instalado

### Pasos

1. Abrir terminal y navegar a la carpeta del proyecto:
```bash
cd "path/to/computer-graphics-practical-exam"
```

2. Compilar todos los archivos Java:
```bash
javac *.java
```

3. Ejecutar el programa:
```bash
java Main
```

## Estructura del Proyecto

### Archivos Java
- `Main.java` - Clase principal (ventana Swing)
- `Renderer.java` - Renderizado y dibujo de objetos
- `Cohen3D.java` - Implementación del algoritmo Cohen-Sutherland 3D
- `Point3D.java` - Clase para puntos 3D
- `Keyboard.java` - Manejo de entrada del teclado

### Archivos de Datos (.txt)
- `house3d.txt` - Coordenadas de la casita
- `cube.txt` - Coordenadas del cubo de recorte
- `points.txt` - Coordenadas iniciales de P2 y P3
- `clipping_volume.txt` - Límites del volumen de recorte

## Descripción Técnica

### Proyección
- Plano de proyección: Z = -100
- Cámara en el origen (0, 0, 0)
- Proyección perspectiva calculada mediante intersección de rayos

### Algoritmo Cohen-Sutherland 3D
El algoritmo utiliza códigos de región de 6 bits:
- **Bit 1 (LEFT)**: x < XMIN
- **Bit 2 (RIGHT)**: x > XMAX
- **Bit 3 (BELOW)**: y < YMIN
- **Bit 4 (ABOVE)**: y > YMAX
- **Bit 5 (FARZ)**: z < ZMIN
- **Bit 6 (NEARZ)**: z > ZMAX

**Casos:**
1. **Aceptación trivial**: Ambos puntos dentro (código 000000)
2. **Rechazo trivial**: Ambos puntos en el mismo lado fuera
3. **Caso mixto**: Cálculo iterativo de intersecciones con planos del cubo

## Distribución del Trabajo

### Lina Ballesteros
- Implementación del algoritmo Cohen-Sutherland extendido a 3D
- Renderizado y proyección
- Carga de datos desde archivos .txt
- Dibujo de la casita, cubo y segmento de línea
- Corrección y optimización del algoritmo

### Alejandro Rios
- Implementación del manejo de entrada del teclado
- Movimiento del punto P3 con las teclas
- Integración del KeyListener en la ventana principal

## Método Utilizado

Se utilizó estrictamente el método propuesto en el examen:
- Algoritmo Cohen-Sutherland extendido a 3D
- Códigos de región de 6 bits
- Detección de aceptación y rechazo trivial
- Cálculo iterativo de intersecciones
- Proyección sobre plano Z = -100

## Notas

- Implementación completa en Java usando Swing
- Todos los datos organizados en archivos .txt
- El algoritmo de clipping funciona correctamente
- El segmento cambia de color dinámicamente según su posición
