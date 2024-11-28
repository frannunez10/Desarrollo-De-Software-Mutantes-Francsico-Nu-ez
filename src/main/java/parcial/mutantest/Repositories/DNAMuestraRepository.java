package parcial.mutantest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parcial.mutantest.Entities.DNAMuestra;
@Repository
public interface DNAMuestraRepository extends JpaRepository<DNAMuestra, Long> {
    boolean existsByDnaMuestra(String dnaMuestra);
}
