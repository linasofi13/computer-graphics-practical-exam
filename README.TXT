==========================================
PUNTO 1: CLIPPING EN 3D (COHEN-SUTHERLAND)
==========================================

Computación Gráfica ST0275
Profesor: Helmuth Trefftz
Universidad EAFIT
Integrantes:
  - Alejandro Rios
  - Lina Ballesteros

DESCRIPCIÓN:
------------
Este programa implementa el algoritmo Cohen-Sutherland extendido a 3D para 
recorte de líneas en un volumen cúbico. El programa dibuja una casita (house), 
un cubo de recorte en verde, y un segmento de línea que cambia de color según 
el resultado del algoritmo de clipping.

REQUISITOS IMPLEMENTADOS:
--------------------------
✓ Utilización del código base para dibujar la casita y proyectarla sobre el 
  plano de proyección Z = -100
✓ Extrapolación del algoritmo Cohen-Sutherland a 3D
✓ Cubo de recorte definido por P0 <0,0,-200> y P1 <100,100,-300>
✓ Cubo dibujado con segmentos de recta de color verde
✓ Segmento de línea desde P2 <0,0,-100> (fijo) hasta P3 <50,50,-199> (móvil)
✓ Evaluación del segmento con algoritmo Cohen-Sutherland
✓ Color AZUL para rechazo trivial
✓ Color ROJO para todos los otros casos (aceptado o recortado)
✓ Proyección sobre el plano Z = -100

ARCHIVOS INCLUIDOS:
-------------------

Archivos Java:
- Main.java : Clase principal del programa (ventana Swing)
- Renderer.java : Clase que maneja el renderizado y dibujo de todos los objetos
- Cohen3D.java : Implementación completa del algoritmo Cohen-Sutherland extendido a 3D
- Point3D.java : Clase para representar puntos en 3D
- Keyboard.java : Clase para manejar la entrada del teclado (implementado por Lina Ballesteros)

Archivos de datos (.txt):
- house3d.txt : Coordenadas de los vértices y aristas de la casita (house)
- cube.txt : Coordenadas de los vértices y aristas del cubo de recorte
- points.txt : Coordenadas iniciales de los puntos P2 y P3 del segmento de línea
- clipping_volume.txt : Límites del volumen de recorte (P0 y P1)

Documentación:
- README.md : Documentación en formato Markdown
- README.TXT : Este archivo (documentación principal)

CONTROLES DEL TECLADO:
-----------------------
A / D : Decrementar / Incrementar coordenada X del punto P3
W / S : Incrementar / Decrementar coordenada Y del punto P3
E / Q : Decrementar / Incrementar coordenada Z del punto P3

El incremento/decremento es de 2.0 unidades por tecla presionada.

COMPILACIÓN Y EJECUCIÓN:
-------------------------
1) Abrir terminal (PowerShell o CMD) y situarse en la carpeta del proyecto:
   cd "path/to/computer-graphics-practical-exam"

2) Compilar todos los archivos Java:
   javac *.java

3) Ejecutar el programa:
   java Main

DESCRIPCIÓN TÉCNICA:
--------------------

1. PROYECCIÓN:
   - La proyección se realiza sobre el plano Z = -100
   - La cámara está ubicada en el origen (0,0,0)
   - Se calcula la intersección del rayo desde el origen hasta cada punto con 
     el plano de proyección
   - Fórmula: t = -100 / z, luego xp = t * x, yp = t * y

2. CUBO DE RECORTE:
   - Esquina mínima: P0 = (0, 0, -200)
   - Esquina máxima: P1 = (100, 100, -300)
   - El cubo se dibuja en color verde
   - Coordenadas cargadas desde cube.txt

3. CASITA (HOUSE):
   - Se carga desde house3d.txt
   - Se dibuja en color azul claro
   - Se traslada en Z por -150 unidades para posicionarla en la escena

4. SEGMENTO DE LÍNEA P2P3:
   - P2 = (0, 0, -100) - Punto fijo en el plano de proyección
   - P3 = (50, 50, -199) - Punto móvil (se puede mover con el teclado)
   - El segmento se evalúa con el algoritmo Cohen-Sutherland 3D

5. ALGORITMO COHEN-SUTHERLAND 3D:
   - Se calcula un código de región de 6 bits para cada extremo:
     * Bit 1 (LEFT): x < XMIN
     * Bit 2 (RIGHT): x > XMAX
     * Bit 3 (BELOW): y < YMIN
     * Bit 4 (ABOVE): y > YMAX
     * Bit 5 (FARZ): z < ZMIN
     * Bit 6 (NEARZ): z > ZMAX
   
   - Aceptación trivial: Si ambos puntos tienen código 000000 (ambos dentro)
   - Rechazo trivial: Si (codeA & codeB) != 0 (ambos en el mismo lado fuera)
   - Caso mixto: Se calculan intersecciones con los planos del cubo iterativamente
     hasta obtener aceptación trivial o rechazo trivial
   
   - COLORES:
     * AZUL: Rechazo trivial (segmento completamente fuera del cubo)
     * ROJO: Aceptado o recortado (segmento dentro o intersecta el cubo)


MÉTODO UTILIZADO:
-----------------
Se utilizó estrictamente el método propuesto en el examen:
- Algoritmo Cohen-Sutherland extendido a 3D
- Códigos de región de 6 bits
- Detección de aceptación y rechazo trivial
- Cálculo iterativo de intersecciones con planos del cubo
- Proyección sobre plano Z = -100

NOTAS ADICIONALES:
------------------
- El programa está implementado completamente en Java usando Swing para la GUI
- Todos los datos (coordenadas) están organizados en archivos .txt para facilitar 
  la modificación sin cambiar el código
- El algoritmo de clipping ha sido probado y funciona correctamente
- El segmento cambia de color dinámicamente según su posición respecto al cubo
- La proyección se realiza correctamente sobre el plano Z = -100
