/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.controller;

import br.com.xlo.dto.OpcionaisVeiculoDTO;
import br.com.xlo.dto.VeiculoDTO;
import br.com.xlo.model.Opcionais;
import br.com.xlo.model.OpcionaisVeiculo;
import br.com.xlo.model.Usuario;
import br.com.xlo.model.Veiculo;
import br.com.xlo.repository.OpcionaisRepository;
import br.com.xlo.repository.OpcionaisVeiculoRepository;
import br.com.xlo.repository.UsuarioRepository;
import br.com.xlo.repository.VeiculoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author William Fávaro
 */
@RestController
@RequestMapping("/anuncio/")
public class AnuncioController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private OpcionaisRepository opcionaisRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OpcionaisVeiculoRepository opcionaisVeiculoRepository;

    /**
     * Lista veiculos por usuario
     */
    @GetMapping("buscaVeiculoPorUsuario/{username}")
    public List<Veiculo> listarVeiculosPorUsuario(@PathVariable String username) {
        return veiculoRepository.findVehiclesByUserName(username);
    }

    /**
     * Lista todos os opcionais
     *
     * @return
     */
    @GetMapping("opcionais")
    public List<Opcionais> listarOpcionais() {
        return opcionaisRepository.findAll();
    }

    @PostMapping
    @GetMapping("cadastro-anuncio")
    public void salvarVeiculoEOpcionais(@RequestBody VeiculoDTO veiculo, @RequestBody List<OpcionaisVeiculoDTO> opcionaisVeiculo) {
        VeiculoDTO veiculoDTO = veiculo;
        Usuario usuario = usuarioRepository.findById(veiculoDTO.getIdUsuario());
        Veiculo v = new Veiculo();
        v.setIdUsuario(usuario);
        v.setDescricao(veiculo.getDescricao());
        v.setPreco(veiculo.getPreco());
        v.setKmRodado(veiculo.getKm());
        v.setAno(veiculo.getAno());

        v = veiculoRepository.save(v);

        salvarOpcionalVeiculo(v, opcionaisVeiculo);

    }

    private void salvarOpcionalVeiculo(Veiculo veiculo, List<OpcionaisVeiculoDTO> opcionaisVeiculo) {
        for (OpcionaisVeiculoDTO opcional : opcionaisVeiculo) {
            Opcionais opcionais = opcionaisRepository.findById(opcional.getIdOpcionais());
            OpcionaisVeiculo op = new OpcionaisVeiculo();
            op.setIdOpcionais(opcionais);
            op.setIdVeiculo(veiculo);
            op = opcionaisVeiculoRepository.save(op);
        }
    }

    
    //veiculo e lista de opcionaisVeiculo
    @GetMapping("detalhes")
    public List<OpcionaisVeiculo> listaOpcionaisVeiculo() {
        return opcionaisVeiculoRepository.findAll();
    }
}
