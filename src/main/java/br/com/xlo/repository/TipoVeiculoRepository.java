package br.com.xlo.repository;

import br.com.xlo.model.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Davidson
 */
public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Long> {

    @Override
    TipoVeiculo save(TipoVeiculo tipoVeiculo);
    
    TipoVeiculo findByTipoNome(String tipoNome);
}
