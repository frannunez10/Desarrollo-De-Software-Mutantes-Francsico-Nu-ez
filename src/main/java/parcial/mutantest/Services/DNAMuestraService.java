package parcial.mutantest.Services;

import org.springframework.stereotype.Service;
import parcial.mutantest.Entities.DNAMuestra;
import parcial.mutantest.Repositories.DNAMuestraRepository;

import java.util.*;

@Service
public class DNAMuestraService {
    private final DNAMuestraRepository repositorioDna;
    private long contadorDnaMutante = 0;
    private long contadorDnaHumano = 0;
    public DNAMuestraService(DNAMuestraRepository repositorioDna) {
        this.repositorioDna = repositorioDna;
    }

    public boolean isMutante(String[] secuenciaDna) {
        // Convertir el array en una cadena simple
        String dnaComoCadena = String.join("", secuenciaDna);

        // Verificar si el ADN ya está registrado
        if (repositorioDna.existsByDnaMuestra(dnaComoCadena)) {
            throw new IllegalArgumentException("El ADN ya ha sido registrado.");
        }

        // Validar formato de la secuencia
        if (!esSecuenciaValida(secuenciaDna)) {
            throw new IllegalArgumentException("El ADN tiene un formato inválido.");
        }

        // Verificar si es mutante
        boolean esMutante = verificarSiEsMutante(secuenciaDna);

        // Guardar la muestra de ADN en la base de datos
        DNAMuestra muestraDna = new DNAMuestra();
        muestraDna.setDnaMuestra(dnaComoCadena);
        muestraDna.setEsMutante(esMutante);
        repositorioDna.save(muestraDna);

        // Actualizar contadores
        if (esMutante) {
            contadorDnaMutante++;
        } else {
            contadorDnaHumano++;
        }

        return esMutante;
    }

    private boolean esSecuenciaValida(String[] secuenciaDna) {
        int tamano = secuenciaDna.length;

        for (String fila : secuenciaDna) {
            if (fila.isEmpty() || fila.length() != tamano || !fila.matches("[ATCG]+")) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Object> obtenerEstadisticas() {
        System.out.println("Mutante Count: " + contadorDnaMutante);
        System.out.println("Humano Count: " + contadorDnaHumano);

        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", contadorDnaMutante);
        stats.put("count_human_dna", contadorDnaHumano);

        double ratio = (contadorDnaHumano == 0) ? 1.0 : (double) contadorDnaMutante / contadorDnaHumano;
        stats.put("ratio", ratio);

        return stats;
    }


    public boolean verificarSiEsMutante(String[] secuenciaDna) {
        int secuenciasEncontradas = 0;
        int tamano = secuenciaDna.length;

        for (int fila = 0; fila < tamano; fila++) {
            for (int columna = 0; columna < tamano; columna++) {
                if (columna <= tamano - 4 && verificarSecuencia(secuenciaDna, fila, columna, 0, 1)) {
                    secuenciasEncontradas++;
                }
                if (fila <= tamano - 4 && verificarSecuencia(secuenciaDna, fila, columna, 1, 0)) {
                    secuenciasEncontradas++;
                }
                if (fila <= tamano - 4 && columna <= tamano - 4 && verificarSecuencia(secuenciaDna, fila, columna, 1, 1)) {
                    secuenciasEncontradas++;
                }
                if (fila <= tamano - 4 && columna >= 3 && verificarSecuencia(secuenciaDna, fila, columna, 1, -1)) {
                    secuenciasEncontradas++;
                }

                if (secuenciasEncontradas >= 2) return true;
            }
        }
        return false;
    }

    private boolean verificarSecuencia(String[] secuenciaDna, int fila, int columna, int direccionFila, int direccionColumna) {
        return verificarRecursivamente(secuenciaDna, fila, columna, direccionFila, direccionColumna, secuenciaDna[fila].charAt(columna), 1);
    }

    private boolean verificarRecursivamente(String[] secuenciaDna, int fila, int columna, int direccionFila, int direccionColumna, char objetivo, int contador) {
        if (contador == 4) return true;  // Se encontró una secuencia de 4 caracteres iguales
        if (fila + direccionFila >= secuenciaDna.length || columna + direccionColumna >= secuenciaDna.length || fila + direccionFila < 0 || columna + direccionColumna < 0) {
            return false;
        }
        if (secuenciaDna[fila + direccionFila].charAt(columna + direccionColumna) != objetivo) {
            return false;
        }

        return verificarRecursivamente(secuenciaDna, fila + direccionFila, columna + direccionColumna, direccionFila, direccionColumna, objetivo, contador + 1);
    }
}
