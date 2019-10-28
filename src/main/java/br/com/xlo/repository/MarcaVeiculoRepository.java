package br.com.xlo.repository;

import br.com.xlo.model.MarcaVeiculo;
import br.com.xlo.model.TipoVeiculo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Davidson
 */
@Repository
public interface MarcaVeiculoRepository extends JpaRepository<MarcaVeiculo, Long> {

    @Override
    MarcaVeiculo save(MarcaVeiculo marcaVeiculo);
    
    @Query("SELECT mv FROM MarcaVeiculo mv "
            + "JOIN  TipoVeiculo tv ON mv.tipoVeiculo.id = tv.id "
            + "WHERE mv.tipoVeiculo.tipoNome = ?1 "
            + "ORDER BY mv.marca")
    List<MarcaVeiculo> findByTipoVeiculoNome(String tipoVeiculo);
    
}
