/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.controller;

import br.com.xlo.model.MarcaVeiculo;
import br.com.xlo.model.TipoVeiculo;
import br.com.xlo.repository.MarcaVeiculoRepository;
import br.com.xlo.repository.TipoVeiculoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Davidson
 */
@RestController
@RequestMapping("/campos/")
public class CamposController {

    @Autowired
    private MarcaVeiculoRepository marcaVeiculoRepository;

    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;

    @GetMapping("tipo-veiculo")
    public List<TipoVeiculo> getTipoVeiculo() {
        System.out.println("Voltando lista de tipo de veiculos");
        return tipoVeiculoRepository.findAll();
    }

    @GetMapping("marca-veiculo/{idTipo}")
    public List<MarcaVeiculo> getMarcaVeiculos(@PathVariable Integer idTipo) {
        System.out.println("Voltando lista de marca de veiculos");
        return marcaVeiculoRepository.findAll();
    }

}
