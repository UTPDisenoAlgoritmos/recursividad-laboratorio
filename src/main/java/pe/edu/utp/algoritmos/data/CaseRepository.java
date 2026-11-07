package pe.edu.utp.algoritmos.data;

import pe.edu.utp.algoritmos.domain.Models;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Mapea los datos CSV a registros de dominio. Los algoritmos no conocen CSV. */
public final class CaseRepository {
    private CaseRepository() { }
    private static String field(Map<String,String> r, String name) {
        String v = r.get(name);
        if (v == null || v.isBlank()) throw new IllegalArgumentException("Campo ausente/vacío: " + name);
        return v;
    }
    private static int number(Map<String,String> r, String name) {
        try { return Integer.parseInt(field(r, name)); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Número inválido en " + name + ": " + r.get(name), ex); }
    }
    public static List<Models.Inventory> inventory(Path file) throws IOException {
        List<Models.Inventory> result = new ArrayList<>();
        for (var r: CsvReader.read(file)) result.add(new Models.Inventory(field(r,"id"),field(r,"producto"),
                number(r,"cantidad"),number(r,"minimo"),number(r,"precio_centimos")));
        return result;
    }
    public static List<Models.Order> orders(Path file) throws IOException {
        List<Models.Order> result = new ArrayList<>();
        for (var r: CsvReader.read(file)) result.add(new Models.Order(field(r,"id"),field(r,"cliente"),
                number(r,"monto_centimos"),field(r,"estado")));
        return result;
    }
    public static List<Models.Temperature> temperatures(Path file) throws IOException {
        List<Models.Temperature> result = new ArrayList<>();
        for (var r: CsvReader.read(file)) result.add(new Models.Temperature(field(r,"timestamp"),
                field(r,"sensor"),number(r,"temp_decimas")));
        return result;
    }
    public static List<Models.Production> production(Path file) throws IOException {
        List<Models.Production> result = new ArrayList<>();
        for (var r: CsvReader.read(file)) result.add(new Models.Production(number(r,"secuencia"),
                field(r,"fase"),field(r,"lote")));
        return result;
    }
    public static List<Models.Initiative> initiatives(Path file) throws IOException {
        List<Models.Initiative> result = new ArrayList<>();
        for (var r: CsvReader.read(file)) {
            int hours = number(r,"horas"), value = number(r,"valor");
            if (hours <= 0 || value < 0) throw new IllegalArgumentException("Horas deben ser >0 y valor >=0");
            result.add(new Models.Initiative(field(r,"id"),field(r,"iniciativa"),hours,value));
        }
        return result;
    }
}
