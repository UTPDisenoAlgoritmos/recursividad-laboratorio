# Guía del estudiante: 5 casos con preguntas y evidencia

**Logro:** Al finalizar la sesión, el estudiante implementa, ejecuta y compara versiones recursivas e iterativas en Java 17/Maven sobre CSV, justificando casos base, pila de llamadas, complejidad y corrección con pruebas.

**Consigna general:** En equipos, asignar un caso, abrir el CSV, formular el caso base y el paso recursivo, identificar equivalencia con un bucle, ejecutar ambas versiones y probar resultados. Entregar capturas de métricas, código y una explicación de elección técnica. `CsvReader` y `CaseRepository` deben permanecer desacoplados de los algoritmos.

## Caso 1 · Almacén: inventario y reposición (intermedio)
Empresa: almacén de repuestos de tecnología. `inventario.csv`: 500 productos, cantidad disponible, mínimo requerido y precio unitario en céntimos.

**Reto:** Calcular cantidad total de unidades, valor económico del inventario y número de referencias bajo mínimo. Usar recursión lineal con caso base `i == n` y compararla con `for` iterativo.

**Preguntas:** ¿Qué queda pendiente en cada marco de pila? ¿Qué pasa si el CSV crece a 100 000 filas? ¿Ambos métodos tienen O(n) de tiempo? ¿Cambian las necesidades de pila?

**Pruebas:** datos vacíos; producto con cantidad igual al mínimo (no debe disparar alerta); centavos monetarios; archivo alterado por el estudiante.

## Caso 2 · Comercio electrónico: localizar pedidos (básico-intermedio)
`pedidos.csv`: 300 pedidos en orden de registro; columnas `id,cliente,monto_centimos,estado`.

**Reto:** Buscar la primera coincidencia `PED-0240` sin ordenar ni utilizar `Collections.indexOf`. Versiones `search(i+1)` y `for`. Retornar índice 0-based; si no existe, `-1`.

**Preguntas:** ¿Qué detiene la recursión? ¿Cuántas comparaciones para el primer registro, último y uno inexistente? ¿Por qué no es búsqueda binaria?

**Pruebas:** primer ID, último, inexistente, mayúsculas/minúsculas.

## Caso 3 · IoT: máxima temperatura (intermedio)
`temperaturas.csv`: 256 muestras de cuatro sensores, almacenadas en décimas de °C para evitar redondeos.

**Reto:** Encontrar el registro de mayor temperatura. Recursiva: dividir en mitades, resolver y combinar los dos máximos. Iterativa: recorrer una vez. Si existe empate, reportar la primera aparición.

**Preguntas:** ¿Por qué la pila es O(log n) aun con 256 muestras? ¿Dónde se combinan subresultados? ¿Cuál variante gana por simplicidad? ¿Qué cambia si procesamos 1 millón de muestras?

**Pruebas:** un registro; dos iguales; valor negativo; archivo vacío (`-1`).

## Caso 4 · Control de calidad: secuencia alternada (avanzado)
`produccion.csv`: fases de producción que deben alternar `ENSAMBLE` y `INSPECCION`, comenzando por `ENSAMBLE`.

**Reto:** Detectar la **primera** posición fuera de patrón. En versión recursiva `expectAssembly` llama `expectInspection`, y esta vuelve a `expectAssembly` (recursión indirecta real); en versión iterativa, alternar lo esperado según `i % 2`.

**Preguntas:** ¿Por qué es indirecta? ¿Qué método contiene el caso base? ¿Cuál es el valor esperado si todos los registros son correctos? ¿Cómo se comporta al encontrar la primera anomalía?

**Pruebas:** secuencia correcta, error inicial, error medio, secuencia vacía.

## Caso 5 · PMO TI: selección de iniciativas (desafío / introducción al backtracking)
`iniciativas.csv`: 12 iniciativas con esfuerzo en horas y beneficio en puntos. Capacidad predeterminada: **45 horas**.

**Reto:** Seleccionar un subconjunto de iniciativas que maximice el beneficio total sin superar horas disponibles. Cada iniciativa se selecciona como máximo una vez. Comparar **backtracking recursivo** (incluir/excluir y poda por cota optimista) versus **búsqueda exhaustiva iterativa exacta** (máscaras binarias). No comparar con heurística voraz si se requiere optimalidad.

**Preguntas:** ¿En qué casos una rama puede podarse sin perder el óptimo? ¿Por qué la cota optimista es segura si los beneficios son no negativos? ¿Cuál es la complejidad máxima O(2^n)? ¿Qué cambia con 20 iniciativas?

**Pruebas:** capacidad 0, tres proyectos sencillos, soluciones empatadas; verificar selección idéntica entre ambos métodos.

## Entregables del equipo

1. Captura de ejecución y filas del CSV con resultado.
2. Identificación escrita del caso base y el paso recursivo.
3. Comparativa de visitas, comparaciones, profundidad, tiempo y resultado.
4. Al menos dos pruebas unitarias nuevas para su caso.
5. Conclusión técnica: ¿cuándo usaría recursión y cuándo iteración en producción?

**Desafío autónomo:** Modificar un CSV sin cambiar nombres de columnas, volver a ejecutar, documentar qué cambia en el resultado y contrastar ambas variantes.
