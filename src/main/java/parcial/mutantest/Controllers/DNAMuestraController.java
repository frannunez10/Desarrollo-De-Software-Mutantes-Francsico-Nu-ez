package parcial.mutantest.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parcial.mutantest.Services.DNAMuestraService;

import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/mutant")
public class DNAMuestraController {
    @Autowired
    private DNAMuestraService dnaMuestraService;
    @PostMapping()
    public ResponseEntity<?> verificarDNAMuestra(@RequestBody Map<String, String[]> dnaMuestra){
        String[] dna = dnaMuestra.get("dna");
        if (dna == null || dna.length == 0) {
            return ResponseEntity.badRequest().build();
        }

        boolean isMutant = dnaMuestraService.isMutante(dna);

        if (isMutant) {
            return ResponseEntity.ok().body("{\"mensaje\":\"El DNA es de un Mutante.\"}");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"mensaje\":\"El DNA es humano.\"}");
        }
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> estadisticas = dnaMuestraService.obtenerEstadisticas();
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
}
