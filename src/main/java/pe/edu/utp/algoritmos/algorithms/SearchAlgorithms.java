package pe.edu.utp.algoritmos.algorithms;

import pe.edu.utp.algoritmos.domain.Models;
import java.util.List;

/** Caso 2: búsqueda lineal. Devuelve índice 0-based o -1. */
public final class SearchAlgorithms {
    private SearchAlgorithms() { }
    public static int recursive(List<Models.Order> rows, String target, Metrics m) {
        return search(rows, target, 0, 1, m);
    }
    private static int search(List<Models.Order> rows, String target, int i, int depth, Metrics m) {
        m.visit(depth);
        if (i == rows.size()) return -1;
        m.comparison();
        if (rows.get(i).id().equalsIgnoreCase(target)) return i;
        return search(rows, target, i + 1, depth + 1, m);
    }
    public static int iterative(List<Models.Order> rows, String target, Metrics m) {
        for (int i = 0; i < rows.size(); i++) {
            m.visit(1); m.comparison();
            if (rows.get(i).id().equalsIgnoreCase(target)) return i;
        }
        return -1;
    }
}
