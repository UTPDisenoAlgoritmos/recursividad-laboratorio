package pe.edu.utp.algoritmos.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Capa exclusivamente de entrada CSV. Nunca ejecuta algoritmos de negocio. */
public final class CsvReader {
    private CsvReader() { }

    public static List<Map<String, String>> read(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        if (lines.isEmpty()) throw new IllegalArgumentException("CSV vacío: " + path);
        String header = lines.get(0).replace("\uFEFF", "");
        char delimiter = detectDelimiter(header);
        List<String> columns = split(header, delimiter);
        if (columns.isEmpty()) throw new IllegalArgumentException("CSV sin encabezados");
        if (columns.stream().map(String::trim).distinct().count() != columns.size()) {
            throw new IllegalArgumentException("Encabezados duplicados en " + path);
        }
        List<Map<String, String>> records = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) continue;
            List<String> values = split(lines.get(i), delimiter);
            if (values.size() != columns.size()) {
                throw new IllegalArgumentException("Línea " + (i + 1) + ": se esperan "
                        + columns.size() + " columnas y se recibieron " + values.size());
            }
            Map<String, String> record = new LinkedHashMap<>();
            for (int c = 0; c < columns.size(); c++) {
                record.put(columns.get(c).trim(), values.get(c).trim());
            }
            records.add(record);
        }
        return records;
    }

    private static char detectDelimiter(String header) {
        // Los cinco datasets usan coma; se permite también punto y coma en archivos propios.
        int commas = 0, semicolons = 0;
        boolean quoted = false;
        for (int i = 0; i < header.length(); i++) {
            char c = header.charAt(i);
            if (c == '"') {
                if (quoted && i + 1 < header.length() && header.charAt(i + 1) == '"') i++;
                else quoted = !quoted;
            } else if (!quoted) {
                if (c == ',') commas++;
                if (c == ';') semicolons++;
            }
        }
        return semicolons > commas ? ';' : ',';
    }

    private static List<String> split(String line, char delimiter) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (quoted && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"'); i++;
                } else quoted = !quoted;
            } else if (c == delimiter && !quoted) {
                fields.add(current.toString()); current.setLength(0);
            } else {
                current.append(c);
            }
        }
        if (quoted) throw new IllegalArgumentException("Comillas sin cerrar en fila CSV: " + line);
        fields.add(current.toString());
        return fields;
    }
}
