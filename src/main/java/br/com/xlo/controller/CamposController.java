/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.controller;

import br.com.xlo.model.MarcaVeiculo;
import br.com.xlo.model.TipoVeiculo;
import br.com.xlo.model.Veiculo;
import br.com.xlo.repository.MarcaVeiculoRepository;
import br.com.xlo.repository.TipoVeiculoRepository;
import br.com.xlo.repository.VeiculoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("tipo-veiculo")
    public List<TipoVeiculo> getTipoVeiculo() {
        System.out.println("Voltando lista de tipo de veiculos");
        return tipoVeiculoRepository.findAll();
    }

    @GetMapping("marca-veiculo/{tipoVeiculo}")
    public List<MarcaVeiculo> getMarcaVeiculos(@PathVariable String tipoVeiculo) {
        System.out.println("Voltando lista de marca de veiculos");
        return marcaVeiculoRepository.findByTipoVeiculoNome(tipoVeiculo);
    }

//    @GetMapping("buscarVeiculos/{marcaVeiculo}/{campoDinamico}")
//    public List<Veiculo> getVeiculos(@PathVariable String marcaVeiculo, @PathVariable String campoDinamico) {
//        System.out.println("Enviando lista de veiculos");
//        if (campoDinamico != null || (!campoDinamico.equals(""))) {
//            return veiculoRepository.findByMarca(marcaVeiculo);
//        } else {
//            return veiculoRepository.findAll();
//        }
//    }
    @GetMapping("buscarVeiculos/{tipoVeiculo}/{marcaVeiculo}/{campoDinamico}/{page}/{pageSize}")
    public ResponseEntity<Page> getVeiculos(@PathVariable String tipoVeiculo, @PathVariable String marcaVeiculo, @PathVariable String campoDinamico, @PathVariable int page, @PathVariable int pageSize) {
        System.out.println("Enviando lista de veiculos");
        Pageable pageable;
        if (campoDinamico.equals("null") || campoDinamico.equals("")) {
            // pesquisando apenas pela marca do veiculo
            pageable = PageRequest.of(page, pageSize, Sort.by("descricao"));

            return new ResponseEntity<>(veiculoRepository.findByMarcaPage(tipoVeiculo, marcaVeiculo, pageable), HttpStatus.OK);
        } else {
            // pesquisando pelo campo dinamico
            pageable = PageRequest.of(page, pageSize, Sort.by("descricao"));

            return new ResponseEntity<>(veiculoRepository.findByMarcaAndDescricaoPage(tipoVeiculo, marcaVeiculo, campoDinamico, pageable), HttpStatus.OK);
        }
    }

    @GetMapping("buscarTodosVeiculos/{page}/{pageSize}")
    public ResponseEntity<Page> getVeiculos(@PathVariable int page, @PathVariable int pageSize) {
        System.out.println("Enviando lista completa de veiculos");
        Pageable pageable;

        pageable = PageRequest.of(page, pageSize, Sort.by("descricao"));
        return new ResponseEntity<>(veiculoRepository.findAll(pageable), HttpStatus.OK);
    }
}
