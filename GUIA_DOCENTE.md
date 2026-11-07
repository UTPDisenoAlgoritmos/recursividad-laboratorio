# Guía docente / Metodología presencial UTP

**Unidad 3 — Semana 13 — Sesión 2 (sesión 26 del sílabo).** Tema oficial: conceptos de recursividad, métodos recursivos, pila de llamadas, recursividad indirecta, recursión frente a iteración. El backtracking formal/selección óptima aparece en la semana 14; caso 5 se marca como ampliación.

## Logro verbal y escrito
«Al finalizar la sesión, el estudiante implementa y contrasta, en Java 17 con Maven, soluciones recursivas e iterativas para un caso de negocio basado en CSV, identificando condición de parada, funcionamiento de la pila, corrección mediante pruebas y costo algorítmico, trabajando colaborativamente».

## Secuencia de 90 minutos (adaptable)

| Minutos | Etapa | Acción del docente | Evidencia del estudiante |
|---|---|---|---|
| 0-4 | Logro y desafío | Presentar verbalmente el logro y preguntar por el criterio de éxito | Explica qué entregará |
| 4-9 | Dudas sesión 13.1 | Pedir a dos personas trazar `f(3)` en pizarra y responder dudas | Intervenciones en pizarra |
| 9-14 | Conocimientos previos | Votación: elegir caso base, resultado y riesgos de `f(n-1)` | Justifica respuesta |
| 14-19 | Utilidad del tema | Pregunta «¿qué procesos reales requieren subdividir, recorrer o explorar alternativas?» | Sugiere aplicación profesional |
| 19-29 | Modelado guiado | Mostrar pila para `suma(3)` y diferenciar espacio de `for` | Explica cuándo retorna cada marco |
| 29-35 | Arquitectura + seguridad | Enseñar `CsvReader` separado y precauciones de StackOverflow y entrada CSV | Ubica responsabilidad por clase |
| 35-70 | Práctica autónoma en 5 equipos | Repartir un caso por equipo; recorridos de acompañamiento, preguntas analíticas y retroalimentación | Código ejecutable + pruebas |
| 70-81 | Socialización / peer review | Cada equipo muestra salida y defiende la variante adecuada | Tabla compartida y preguntas |
| 81-90 | Cierre construido con estudiantes | Actividad 3-2-1: 3 ideas, 2 diferencias, 1 caso donde evitar recursión | Síntesis verbal en pizarra |

> Si hay menos de cinco equipos, asignar dos casos a uno o convertir el caso 5 en ampliación individual. No se espera implementar los cinco casos desde cero en 90 minutos: el proyecto contiene implementaciones base para análisis y modificación.

## Roles rotativos (grupos de 3–5)
- Analista: enuncia entradas, salidas, caso base y complejidad.
- Programador(a): modifica recursión/iteración sin tocar la capa de CSV.
- QA: agrega `@Test` y compara casos extremos.
- Relator(a): explica trazas y toma métricas. Intercambiar roles a los 15 minutos.

## Claves de verificación con los CSV entregados
- Caso 1: obtener mismos totales con ambas versiones; precio total se calcula en céntimos y se transforma a soles solo al mostrar.
- Caso 2: `PED-0240` está en índice **239** (0-based), debe encontrarse exactamente allí.
- Caso 3: se insertó el máximo **46.7 °C** en índice **188** para poder verificarlo.
- Caso 4: primera anomalía en índice **45**, secuencia CSV **46**.
- Caso 5: exactitud de ambas variantes con 45 h y mismos IDs seleccionados; consultar consola para resultado calculado.

## Rúbrica presencial: evidencias para aspirar a nivel «Estándar esperado»
- **Logro:** verbalizarlo, no solo mostrarlo (1,5).
- **Dudas:** resolver dudas previas a través de participación de estudiantes (1,0).
- **Previos:** diagnóstico activo previo a explicación (1,0).
- **Utilidad:** los estudiantes dicen su aplicación explícita (1,5).
- **Secuencia:** consignas claras y progresivas (2,0).
- **Desarrollo:** preguntas desafiantes + análisis participativo (3,5).
- **Espacio autónomo:** práctica propia en caso real/simulado alineado al logro (3,5).
- **Cierre:** síntesis construida en conjunto (2,0).
- **Discurso:** velocidad, pronunciación y volumen comprensibles durante toda la sesión (4,0).

La suma de esos indicadores de la rúbrica de curso presencial es **20 puntos**. La obtención depende de la ejecución real en aula, no únicamente del archivo PPT.

## Minirrúbrica formativa opcional para evaluar el caso (20 pts)
- Exactitud y validación con al menos 2 JUnit nuevos: 5 pts.
- Implementación iterativa y recursiva con caso base correcto: 5 pts.
- Separación de CSV / arquitectura y legibilidad: 4 pts.
- Análisis de pila, complejidad y métricas: 4 pts.
- Defensa colaborativa del resultado: 2 pts.

## Retroalimentación concreta
1. Fortaleza observable: «Ambas implementaciones dan el mismo resultado y el test del caso borde pasa».
2. Mejora: «Tu método recursivo no termina en CSV vacío; define caso base antes de llamar recursivamente».
3. Acción: «Agrega `@Test` para lista vacía, vuelve a ejecutar y muestra la traza con profundidad».

**Referencias de base:** Sílabo UTP Análisis y Diseño de Algoritmos 2026-2 (p. 2, 6); Rúbrica de metodología de enseñanza 2026, modalidad presencial (pp. 1–2); bibliografía Cairó y Joyanes indicada en el propio sílabo.
