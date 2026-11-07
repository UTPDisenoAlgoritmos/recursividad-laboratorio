package pe.edu.utp.algoritmos.algorithms;

import pe.edu.utp.algoritmos.domain.Models;
import java.util.List;

/** Caso 5: selección óptima 0/1, backtracking exacto vs enumeración iterativa exacta. */
public final class SelectionAlgorithms {
    private SelectionAlgorithms() { }
    private static void validate(List<Models.Initiative> rows, int capacity) {
        if (capacity < 0 || rows.size() > 20) throw new IllegalArgumentException("Capacidad >=0 y máximo 20 iniciativas");
    }
    private static boolean better(int value, int hours, int mask, Models.Selection best) {
        return value > best.value() || (value == best.value() && hours < best.hours())
                || (value == best.value() && hours == best.hours() && mask < best.mask());
    }
    public static Models.Selection recursive(List<Models.Initiative> rows, int capacity, Metrics m) {
        validate(rows, capacity);
        int[] remainingValue = new int[rows.size() + 1];
        for (int i = rows.size() - 1; i >= 0; i--) {
            remainingValue[i] = remainingValue[i + 1] + rows.get(i).value();
        }
        return new Solver(rows, capacity, remainingValue, m).solve();
    }
    private static final class Solver {
        private final List<Models.Initiative> rows;
        private final int capacity;
        private final int[] optimistic;
        private final Metrics metrics;
        private Models.Selection best = new Models.Selection(0,0,0);
        Solver(List<Models.Initiative> rows,int capacity,int[] optimistic,Metrics metrics) {
            this.rows=rows;this.capacity=capacity;this.optimistic=optimistic;this.metrics=metrics;
        }
        Models.Selection solve() { dfs(0,0,0,0,1); return best; }
        private void dfs(int i, int usedHours, int value, int mask, int depth) {
            metrics.visit(depth);
            metrics.comparison();
            if (usedHours > capacity) { metrics.prune(); return; }
            if (value + optimistic[i] < best.value()) { metrics.prune(); return; }
            if (i == rows.size()) {
                if (better(value, usedHours, mask, best)) best = new Models.Selection(value,usedHours,mask);
                return;
            }
            dfs(i+1, usedHours, value, mask, depth+1); // omitir proyecto i
            Models.Initiative project=rows.get(i);
            dfs(i+1, usedHours + project.hours(), value + project.value(), mask | (1 << i), depth+1);
        }
    }
    public static Models.Selection iterative(List<Models.Initiative> rows, int capacity, Metrics m) {
        validate(rows, capacity);
        Models.Selection best = new Models.Selection(0,0,0);
        int combinations = 1 << rows.size();
        for (int mask=0;mask<combinations;mask++) {
            m.visit(1);
            int hours=0,value=0;
            for (int i=0;i<rows.size();i++) {
                if ((mask & (1 << i)) != 0) {
                    m.comparison();
                    hours+=rows.get(i).hours();
                    if (hours>capacity) { m.prune(); break; }
                    value+=rows.get(i).value();
                }
            }
            if (hours<=capacity && better(value,hours,mask,best)) best = new Models.Selection(value,hours,mask);
        }
        return best;
    }
}
