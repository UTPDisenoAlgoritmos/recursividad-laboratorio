package pe.edu.utp.algoritmos.algorithms;

/** Contadores didácticos: no equivalen a memoria real consumida. */
public final class Metrics {
    private long visits;
    private long comparisons;
    private long prunes;
    private int maxDepth;
    public void visit(int depth) { visits++; maxDepth = Math.max(maxDepth, depth); }
    public void comparison() { comparisons++; }
    public void prune() { prunes++; }
    public long visits() { return visits; }
    public long comparisons() { return comparisons; }
    public long prunes() { return prunes; }
    public int maxDepth() { return maxDepth; }
}
