package pe.edu.utp.algoritmos.algorithms;

import pe.edu.utp.algoritmos.domain.Models;
import java.util.List;

/** Caso 3: máximo por dividir y combinar vs recorrido iterativo. */
public final class TemperatureAlgorithms {
    private TemperatureAlgorithms() { }
    public static int recursive(List<Models.Temperature> rows, Metrics m) {
        if (rows.isEmpty()) return -1;
        return max(rows, 0, rows.size(), 1, m);
    }
    private static int max(List<Models.Temperature> rows, int from, int to, int depth, Metrics m) {
        m.visit(depth);
        if (to - from == 1) return from;
        int middle = from + (to - from) / 2;
        int a = max(rows, from, middle, depth + 1, m);
        int b = max(rows, middle, to, depth + 1, m);
        m.comparison();
        return rows.get(a).deciCelsius() >= rows.get(b).deciCelsius() ? a : b;
    }
    public static int iterative(List<Models.Temperature> rows, Metrics m) {
        if (rows.isEmpty()) return -1;
        int best = 0;
        for (int i = 0; i < rows.size(); i++) {
            m.visit(1);
            if (i == 0) continue;
            m.comparison();
            if (rows.get(i).deciCelsius() > rows.get(best).deciCelsius()) best = i;
        }
        return best;
    }
}
