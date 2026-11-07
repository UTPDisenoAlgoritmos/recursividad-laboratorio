# UTP - Semana 13, sesión 2 | Recursividad, iteración y backtracking

Laboratorio Java 17 + Maven. Cinco casos con CSV independientes, algoritmos recursivos e iterativos, métricas y pruebas JUnit 5.

## Requisitos
- JDK 17 o superior (el bytecode se genera para Java 17)
- Apache Maven 3.8.6+ y terminal ubicada en la carpeta que contiene `pom.xml`

## Ejecución

```bash
mvn clean test
mvn -q -DskipTests compile
mvn -q exec:java -Dexec.args="--case all"
mvn -q exec:java -Dexec.args="--case 2 --target PED-0240"
mvn -q exec:java -Dexec.args="--case 5 --capacity 45"
mvn -q exec:java -Dexec.args="--case 1 --file datasets/inventario.csv"
```

También se puede ejecutar sin argumentos para mostrar el menú. La ruta relativa del CSV se interpreta desde el directorio de trabajo. En Maven, el ejecutable está en `pe.edu.utp.algoritmos.app.Main`.

## Arquitectura (separación solicitada)

```text
Archivo CSV (datasets/) → data/CsvReader → data/CaseRepository → domain/Models
                                                                   ↓
                   algorithms/<Caso>Algorithms ←── Java List<Modelo>
                              ↓
                        app/Runner (métricas)
                              ↓
                        app/Main (consola)
```

- `CsvReader`: lee archivo UTF-8 CSV (coma o punto y coma, comillas y comillas dobles escapadas). No analiza datos ni ejecuta algoritmos. Los ejemplos no usan campos con saltos de línea internos.
- `CaseRepository`: comprueba columnas y convierte celdas a modelos Java (`record`).
- `Models`: datos tipados y resultados, sin lectura de CSV.
- `algorithms`: **solo métodos algorítmicos** con variantes `recursive` / `iterative`.
- `Runner`: cronometra exclusivamente cómputo, cuenta visitas, comparaciones, profundidad máxima y podas.
- `Main`: adapta la interacción por consola; permite dataset propio para cada caso.
- `src/test`: pruebas unitarias con JUnit 5, incluidos casos borde y parser CSV.

## Interpretación correcta de las métricas
- `Visitas` = número de invocaciones recursivas o iteraciones instrumentadas, según variante; no siempre son equivalentes 1:1.
- `Comparaciones` = operaciones de decisión instrumentadas; son una estimación pedagógica y dependen del método.
- `Prof.max.` = profundidad de llamadas controlada por el algoritmo (recursivo); en iterativo se marca 1 como profundidad conceptual; **no** mide bytes reales de pila.
- `Podas` = ramas descartadas en el caso 5 o combinaciones iterativas que superaron el límite.
- `Tiempo (ms)` = ejecución aislada de la función, **sin** lectura del CSV. Una medida no es un benchmark científico (JIT/GC/instrumentación pueden afectar).
- Para un estudio real usar repeticiones, tamaños crecientes y JMH.

## Casos

| Caso | CSV | Concepto central | Salida |
|---|---|---|---|
| 1 Inventario | `inventario.csv` (500 filas) | Caso base y suma recursiva lineal | unidades, valor S/, alertas |
| 2 Pedidos | `pedidos.csv` (300 filas) | Búsqueda recursiva vs iterativa | índice y pedido |
| 3 Sensores | `temperaturas.csv` (256 filas) | Divide y vencerás, pila O(log n) | lectura máxima e índice |
| 4 Producción | `produccion.csv` (80 filas) | Recursión indirecta mutua | primera anomalía de alternancia |
| 5 Portafolio | `iniciativas.csv` (12 filas) | Backtracking 0/1 y comparación exhaustiva | valor máximo, horas, iniciativas |

> Nota curricular: el caso 5 es una **ampliación** al contenido de semana 14; se usa como introducción opcional durante la semana 13.

Ver `CASOS_DE_ESTUDIO.md` para consignas y `GUIA_DOCENTE.md` para la conducción y claves de verificación.

## Seguridad y robustez
- Para evitar `StackOverflowError`, no aplicar la versión recursiva lineal a CSV con cientos de miles de registros sin cambiar la estrategia. Comparar con la alternativa iterativa.
- Datos monetarios expresados en céntimos, evitando redondeo de `double`; el acumulado usa `long`.
- La selección 0/1 se limita a 20 proyectos para evitar explosión combinatoria y desbordamientos de bitmask. Dataset predeterminado: 12 proyectos.
- Cambiar un solo componente por práctica: mantener `CsvReader` intacto y modificar solo la clase del algoritmo asignado.
