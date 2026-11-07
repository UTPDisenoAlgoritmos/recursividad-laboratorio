package pe.edu.utp.algoritmos.app;

import pe.edu.utp.algoritmos.algorithms.*;
import pe.edu.utp.algoritmos.data.CaseRepository;
import pe.edu.utp.algoritmos.domain.Models;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** CLI pedagógica. Archivo CSV (I/O) y algoritmos (cálculo) se mantienen separados. */
public final class Main {
    private Main() { }
    public static void main(String[] args) throws Exception {
        Map<String,String> flags=parse(args);
        String selected=flags.get("--case");
        if (selected==null) {
            System.out.println("LABORATORIO UTP / Semana 13 - Sesión 2");
            System.out.println("1 Inventario | 2 Pedidos | 3 Temperaturas | 4 Producción | 5 Selección óptima | all");
            System.out.print("Elegir caso: ");
            Scanner scanner = new Scanner(System.in);
            selected=scanner.nextLine().trim();
            if (selected.isEmpty()) selected="all";
        }
        if (selected.equalsIgnoreCase("all")) {
            if(flags.containsKey("--file")) throw new IllegalArgumentException("--file solo se admite al elegir un caso");
            for(int n=1;n<=5;n++) execute(n,flags);
        } else {
            int id=Integer.parseInt(selected);
            if(id<1||id>5)throw new IllegalArgumentException("Caso entre 1 y 5");
            execute(id,flags);
        }
    }
    private static Map<String,String> parse(String[] args) {
        Map<String,String> flags=new HashMap<>();
        for(int i=0;i<args.length;i+=2) {
            if(i+1>=args.length || !args[i].startsWith("--")) throw new IllegalArgumentException("Uso: --case 1 [--file ruta.csv] [--target PED-0240] [--capacity 45]");
            flags.put(args[i],args[i+1]);
        }
        return flags;
    }
    private static Path file(int id,Map<String,String> f) {
        String[] names={"","inventario.csv","pedidos.csv","temperaturas.csv","produccion.csv","iniciativas.csv"};
        return Path.of(f.getOrDefault("--file","datasets/"+names[id]));
    }
    private static void execute(int id, Map<String,String> f) throws Exception {
        Path path=file(id,f);
        System.out.println("\n=======================================================");
        System.out.println("CASO "+id+" / CSV: "+path);
        switch (id) {
            case 1 -> {
                List<Models.Inventory> data=CaseRepository.inventory(path);
                var r=Runner.measure(m->InventoryAlgorithms.recursive(data,m));
                var it=Runner.measure(m->InventoryAlgorithms.iterative(data,m));
                System.out.printf("Filas: %d | Unidades: %d | Valor: S/ %.2f | Bajo mínimo: %d%n",data.size(),
                        r.answer().units(),r.answer().cents()/100.0,r.answer().alerts());
                Runner.compare(r,it);
            }
            case 2 -> {
                List<Models.Order> data=CaseRepository.orders(path);
                String target=f.getOrDefault("--target","PED-0240");
                var r=Runner.measure(m->SearchAlgorithms.recursive(data,target,m));
                var it=Runner.measure(m->SearchAlgorithms.iterative(data,target,m));
                System.out.println("Filas: "+data.size()+" | ID: "+target+" | Índice 0-based: "+r.answer());
                if(r.answer()>=0)System.out.println("Pedido: "+data.get(r.answer()));
                Runner.compare(r,it);
            }
            case 3 -> {
                List<Models.Temperature> data=CaseRepository.temperatures(path);
                var r=Runner.measure(m->TemperatureAlgorithms.recursive(data,m));
                var it=Runner.measure(m->TemperatureAlgorithms.iterative(data,m));
                System.out.println("Filas: "+data.size()+" | Índice de máximo: "+r.answer());
                if(r.answer()>=0) System.out.println("Mayor temperatura: "+data.get(r.answer()).deciCelsius()/10.0+" °C | "+data.get(r.answer()));
                Runner.compare(r,it);
            }
            case 4 -> {
                List<Models.Production> data=CaseRepository.production(path);
                var r=Runner.measure(m->ProductionAlgorithms.recursive(data,m));
                var it=Runner.measure(m->ProductionAlgorithms.iterative(data,m));
                System.out.println("Filas: "+data.size()+" | Primera anomalía (índice 0-based): "+r.answer());
                if(r.answer()>=0)System.out.println("Evento: "+data.get(r.answer()));
                Runner.compare(r,it);
            }
            case 5 -> {
                List<Models.Initiative> data=CaseRepository.initiatives(path);
                int capacity=Integer.parseInt(f.getOrDefault("--capacity","45"));
                var r=Runner.measure(m->SelectionAlgorithms.recursive(data,capacity,m));
                var it=Runner.measure(m->SelectionAlgorithms.iterative(data,capacity,m));
                System.out.printf("Filas: %d | Capacidad: %d h | Valor máximo: %d puntos | Uso: %d h%n",data.size(),capacity,
                        r.answer().value(),r.answer().hours());
                System.out.print("Elegidas: ");
                for(int i=0;i<data.size();i++) if((r.answer().mask() & (1<<i))!=0) System.out.print(data.get(i).id()+" ");
                System.out.println();
                Runner.compare(r,it);
            }
            default -> throw new IllegalArgumentException("Caso desconocido");
        }
        System.out.println("Se mide cálculo; leer y mapear el CSV NO se incluye en el tiempo.");
    }
}
