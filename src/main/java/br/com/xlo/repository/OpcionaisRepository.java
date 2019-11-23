/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.repository;

import br.com.xlo.model.Opcionais;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author davidson ferreira
 */
@Repository
public interface OpcionaisRepository extends JpaRepository<Opcionais, Long> {
    
    @Override
    List<Opcionais> findAll();
    
    Opcionais findById(Integer id);
    
}
