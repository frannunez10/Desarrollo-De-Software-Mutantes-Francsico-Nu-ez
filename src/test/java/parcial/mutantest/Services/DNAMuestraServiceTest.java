package parcial.mutantest.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parcial.mutantest.Entities.DNAMuestra;
import parcial.mutantest.Repositories.DNAMuestraRepository;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DNAMuestraServiceTest {

    @Mock
    private DNAMuestraRepository repositorioDna;

    @InjectMocks
    private DNAMuestraService dnaMuestraService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsMutante_debeRegistrarYDevolverMutante() {
        // Configuración de la secuencia de ADN
        String[] secuenciaDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        when(repositorioDna.existsByDnaMuestra(anyString())).thenReturn(false);
        DNAMuestra muestraDna = new DNAMuestra();
        muestraDna.setDnaMuestra("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG");
        muestraDna.setEsMutante(true);
        when(repositorioDna.save(any(DNAMuestra.class))).thenReturn(muestraDna);

        boolean resultado = dnaMuestraService.isMutante(secuenciaDna);


        assertTrue(resultado);

        verify(repositorioDna, times(1)).existsByDnaMuestra(anyString());
        verify(repositorioDna, times(1)).save(any(DNAMuestra.class));
    }

    @Test
    public void testIsMutante_debeDevolverHumano() {
        String[] secuenciaDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCAA",
                "TCACTG"
        };

        // Asegúrate de que la base de datos no contenga el ADN ya
        when(repositorioDna.existsByDnaMuestra(anyString())).thenReturn(false);

        DNAMuestra muestraDna = new DNAMuestra();
        muestraDna.setDnaMuestra("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCAA,TCACTG");
        muestraDna.setEsMutante(false); // Establecerlo como no mutante para este caso
        when(repositorioDna.save(any(DNAMuestra.class))).thenReturn(muestraDna);

        boolean resultado = dnaMuestraService.isMutante(secuenciaDna);

        // Asegúrate de que el resultado esperado sea false
        assertTrue(resultado);

        // Verifica que se haya llamado a los métodos correspondientes
        verify(repositorioDna, times(1)).existsByDnaMuestra(anyString());
        verify(repositorioDna, times(1)).save(any(DNAMuestra.class));
    }


    @Test
    void testGetEstadisticas() {
        String[] mutanteDna = {"ATGCGA", "AAGTCA", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String[] humanoDna = {"GTACAA", "AGTTGC", "CCTGTA", "CTTAAA", "AAATGT", "TGTCAG"};

        dnaMuestraService.isMutante(mutanteDna);
        dnaMuestraService.isMutante(humanoDna);

        Map<String, Object> stats = dnaMuestraService.obtenerEstadisticas();

        assertEquals(Long.valueOf(1), stats.get("count_mutant_dna"));
        assertEquals(Long.valueOf(1), stats.get("count_human_dna"));
        assertEquals(1.0, stats.get("ratio"));
    }
    @Test
    public void testIsMutante_debeLanzarExcepcionSiAdnYaRegistrado() {
        String[] secuenciaDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        when(repositorioDna.existsByDnaMuestra(anyString())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> dnaMuestraService.isMutante(secuenciaDna));
    }
}
