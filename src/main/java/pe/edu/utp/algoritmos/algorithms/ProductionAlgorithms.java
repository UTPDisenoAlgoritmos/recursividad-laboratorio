package pe.edu.utp.algoritmos.algorithms;

import pe.edu.utp.algoritmos.domain.Models;
import java.util.List;

/** Caso 4: recursión indirecta genuina: esperaEnsamble llama esperaInspeccion y viceversa. */
public final class ProductionAlgorithms {
    private ProductionAlgorithms() { }
    public static int recursive(List<Models.Production> rows, Metrics m) {
        return expectAssembly(rows, 0, 1, m);
    }
    private static int expectAssembly(List<Models.Production> rows, int i, int depth, Metrics m) {
        m.visit(depth);
        if (i >= rows.size()) return -1;
        m.comparison();
        if (!rows.get(i).phase().equalsIgnoreCase("ENSAMBLE")) return i;
        return expectInspection(rows, i + 1, depth + 1, m);
    }
    private static int expectInspection(List<Models.Production> rows, int i, int depth, Metrics m) {
        m.visit(depth);
        if (i >= rows.size()) return -1;
        m.comparison();
        if (!rows.get(i).phase().equalsIgnoreCase("INSPECCION")) return i;
        return expectAssembly(rows, i + 1, depth + 1, m);
    }
    public static int iterative(List<Models.Production> rows, Metrics m) {
        for (int i = 0; i < rows.size(); i++) {
            m.visit(1); m.comparison();
            String expected = (i % 2 == 0) ? "ENSAMBLE" : "INSPECCION";
            if (!rows.get(i).phase().equalsIgnoreCase(expected)) return i;
        }
        return -1;
    }
}
