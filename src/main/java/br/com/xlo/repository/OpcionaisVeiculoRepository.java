/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.repository;

import br.com.xlo.model.Opcionais;
import br.com.xlo.model.OpcionaisVeiculo;
import br.com.xlo.model.Veiculo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author davidson ferreira
 */
public interface OpcionaisVeiculoRepository extends JpaRepository<OpcionaisVeiculo, Long> {
    
    OpcionaisVeiculo findByIdVeiculo(Integer id);
    
    OpcionaisVeiculo save(OpcionaisVeiculo opcionaisVeiculo);
    
    @Query("SELECT opcionaisVeiculo.idOpcionais from OpcionaisVeiculo opcionaisVeiculo "
            + " WHERE opcionaisVeiculo.idVeiculo.id = ?1 "
            + "ORDER BY opcionaisVeiculo.idOpcionais.nome ")
    List<Opcionais> buscarOpcionaisPorVeiculo(Integer idVeiculo);
}
