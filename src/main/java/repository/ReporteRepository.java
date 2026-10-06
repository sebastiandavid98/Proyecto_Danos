package repository;

import com.universidad.reportedanos.modelo.Reporte;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, UUID> {
    // Spring Data genera la implementacion automaticamente
}
