package parcial.mutantest.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DNAMuestra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dnaMuestra;
    private boolean esMutante;
    public String getDnaMuestra() {
        return dnaMuestra;
    }

    public void setDnaMuestra(String dnaMuestra) {
        this.dnaMuestra = dnaMuestra;
    }

    public boolean isEsMutante() {
        return esMutante;
    }

    public void setEsMutante(boolean esMutante) {
        this.esMutante = esMutante;
    }
}
