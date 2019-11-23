/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.controller;

import br.com.xlo.model.Opcionais;
import br.com.xlo.model.Veiculo;
import br.com.xlo.repository.OpcionaisRepository;
import br.com.xlo.repository.VeiculoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author William Fávaro
 */
@RestController
@RequestMapping("/anuncio/")
public class AnuncioController {
    @Autowired
    private VeiculoRepository veiculoRepository;
    
    private OpcionaisRepository opcionaisRepository;
    
    /**
     * Lista veiculos por usuario
     */
    @GetMapping("buscaVeiculoPorUsuario/{username}")
    public List<Veiculo> listarVeiculosPorUsuario(@PathVariable String username) {
        return veiculoRepository.findVehiclesByUserName(username);
    }
    
    /**
     * Lista todos os opcionais
     * @return 
     */
    @GetMapping("opcionais")
    public List<Opcionais> listarOpcionais() {
        return opcionaisRepository.findAll();
    }
    
    
}
