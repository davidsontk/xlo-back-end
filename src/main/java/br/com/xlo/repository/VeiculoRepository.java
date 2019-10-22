package br.com.xlo.repository;

import br.com.xlo.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Davidson
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}
