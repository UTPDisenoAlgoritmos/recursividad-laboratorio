package pe.edu.utp.algoritmos.app;

import pe.edu.utp.algoritmos.algorithms.Metrics;
import java.util.function.Function;

/** Mide solo el algoritmo: la lectura del CSV queda fuera del cronómetro. */
public final class Runner {
    private Runner() { }
    public record Measured<T>(T answer, Metrics metrics, long nanos) { }
    public static <T> Measured<T> measure(Function<Metrics,T> implementation) {
        Metrics m = new Metrics();
        long start = System.nanoTime();
        T output = implementation.apply(m);
        return new Measured<>(output, m, System.nanoTime() - start);
    }
    public static <T> void compare(Measured<T> recursive, Measured<T> iterative) {
        System.out.printf("%-15s %12s %15s %14s %12s %12s%n", "Variante", "Tiempo(ms)", "Visitas", "Comparaciones", "Prof.max.", "Podas");
        print("Recursiva", recursive); print("Iterativa", iterative);
        System.out.println("¿Mismo resultado? " + recursive.answer().equals(iterative.answer()));
        System.out.println("NOTA: una sola medición no es un benchmark concluyente; comparar también Big O y pila.");
    }
    private static <T> void print(String type, Measured<T> r) {
        Metrics m=r.metrics();
        System.out.printf("%-15s %12.4f %15d %14d %12d %12d%n",type,r.nanos()/1_000_000.0,
                m.visits(),m.comparisons(),m.maxDepth(),m.prunes());
    }
}
