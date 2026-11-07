package pe.edu.utp.algoritmos.domain;

/** Modelos tipados: no contienen lectura de archivos ni lógica algorítmica. */
public final class Models {
    private Models() { }
    public record Inventory(String id, String product, int quantity, int minimum, int priceCents) { }
    public record Order(String id, String customer, int amountCents, String status) { }
    public record Temperature(String timestamp, String sensor, int deciCelsius) { }
    public record Production(int sequence, String phase, String batch) { }
    public record Initiative(String id, String name, int hours, int value) { }
    public record InventorySummary(long units, long cents, int alerts) { }
    public record Selection(int value, int hours, int mask) { }
}
