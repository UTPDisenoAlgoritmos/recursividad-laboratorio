package pe.edu.utp.algoritmos;

import org.junit.jupiter.api.Test;
import pe.edu.utp.algoritmos.algorithms.*;
import pe.edu.utp.algoritmos.data.CsvReader;
import pe.edu.utp.algoritmos.domain.Models;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LaboratorioTest {
    @Test void inventarioAmbosConcuerdanYSeDetectaBajoMinimo() {
        var rows=List.of(new Models.Inventory("1","A",3,5,250),new Models.Inventory("2","B",7,5,100));
        var recursive=InventoryAlgorithms.recursive(rows,new Metrics());
        assertEquals(new Models.InventorySummary(10,1450,1),recursive);
        assertEquals(recursive,InventoryAlgorithms.iterative(rows,new Metrics()));
    }
    @Test void busquedaExisteYNoExiste() {
        var rows=List.of(new Models.Order("A","Ana",100,"OK"),new Models.Order("B","Beto",100,"OK"));
        assertEquals(1,SearchAlgorithms.recursive(rows,"B",new Metrics()));
        assertEquals(-1,SearchAlgorithms.iterative(rows,"X",new Metrics()));
    }
    @Test void maximoConEmpateTomaPrimero() {
        var rows=List.of(new Models.Temperature("t1","S1",31),new Models.Temperature("t2","S1",71),new Models.Temperature("t3","S1",71));
        assertEquals(1,TemperatureAlgorithms.recursive(rows,new Metrics()));
        assertEquals(1,TemperatureAlgorithms.iterative(rows,new Metrics()));
    }
    @Test void indirectaMarcaPrimerError() {
        var rows=List.of(new Models.Production(1,"ENSAMBLE","L"),new Models.Production(2,"INSPECCION","L"),new Models.Production(3,"INSPECCION","L"));
        assertEquals(2,ProductionAlgorithms.recursive(rows,new Metrics()));
        assertEquals(2,ProductionAlgorithms.iterative(rows,new Metrics()));
    }
    @Test void optimizacionEsExacta() {
        var rows=List.of(new Models.Initiative("A","A",5,8),new Models.Initiative("B","B",4,7),new Models.Initiative("C","C",6,9));
        var r=SelectionAlgorithms.recursive(rows,10,new Metrics());
        assertEquals(16,r.value());
        assertEquals(10,r.hours());
        assertEquals(r,SelectionAlgorithms.iterative(rows,10,new Metrics()));
    }
    @Test void csvConComillasEscapadasYSeparador() throws Exception {
        var file=Files.createTempFile("utp-csv", ".csv");
        try {
            Files.writeString(file,"id,producto\\n1,\"Teclado, mecánico\"\\n2,\"Pantalla \"\"premium\"\"\"\\n".replace("\\n","\n"));
            var rows=CsvReader.read(file);
            assertEquals("Teclado, mecánico",rows.get(0).get("producto"));
            assertEquals("Pantalla \"premium\"",rows.get(1).get("producto"));
        } finally { Files.deleteIfExists(file); }
    }
}
