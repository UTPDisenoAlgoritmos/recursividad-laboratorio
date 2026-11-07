package pe.edu.utp.algoritmos.algorithms;

import pe.edu.utp.algoritmos.domain.Models;
import java.util.List;

/** Caso 1: agregación lineal de inventario, O(n) en ambas variantes. */
public final class InventoryAlgorithms {
    private InventoryAlgorithms() { }
    public static Models.InventorySummary recursive(List<Models.Inventory> rows, Metrics metrics) {
        return sum(rows, 0, 1, metrics);
    }
    private static Models.InventorySummary sum(List<Models.Inventory> rows, int index, int depth, Metrics m) {
        m.visit(depth);
        if (index == rows.size()) return new Models.InventorySummary(0, 0, 0); // caso base
        Models.Inventory item = rows.get(index);
        Models.InventorySummary rest = sum(rows, index + 1, depth + 1, m);
        m.comparison();
        return new Models.InventorySummary(rest.units() + item.quantity(),
                rest.cents() + (long) item.quantity() * item.priceCents(),
                rest.alerts() + (item.quantity() < item.minimum() ? 1 : 0));
    }
    public static Models.InventorySummary iterative(List<Models.Inventory> rows, Metrics m) {
        long units = 0, cents = 0;
        int alerts = 0;
        for (Models.Inventory item: rows) {
            m.visit(1); m.comparison();
            units += item.quantity();
            cents += (long) item.quantity() * item.priceCents();
            if (item.quantity() < item.minimum()) alerts++;
        }
        return new Models.InventorySummary(units, cents, alerts);
    }
}
