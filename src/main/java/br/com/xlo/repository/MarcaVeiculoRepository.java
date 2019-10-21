package br.com.xlo.repository;

import br.com.xlo.model.MarcaVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Davidson
 */
@Repository
public interface MarcaVeiculoRepository extends JpaRepository<MarcaVeiculo, Long> {

    @Override
    MarcaVeiculo save(MarcaVeiculo marcaVeiculo);
}
