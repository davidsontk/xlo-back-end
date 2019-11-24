/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.controller;

import br.com.xlo.dto.AnuncioDTO;
import br.com.xlo.dto.OpcionaisVeiculoDTO;
import br.com.xlo.dto.VeiculoDTO;
import br.com.xlo.model.MarcaVeiculo;
import br.com.xlo.model.Opcionais;
import br.com.xlo.model.OpcionaisVeiculo;
import br.com.xlo.model.TipoVeiculo;
import br.com.xlo.model.Usuario;
import br.com.xlo.model.Veiculo;
import br.com.xlo.repository.MarcaVeiculoRepository;
import br.com.xlo.repository.OpcionaisRepository;
import br.com.xlo.repository.OpcionaisVeiculoRepository;
import br.com.xlo.repository.TipoVeiculoRepository;
import br.com.xlo.repository.UsuarioRepository;
import br.com.xlo.repository.VeiculoRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private MarcaVeiculoRepository marcaRepository;

    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;
    
    @Value("${xlo.disco.raiz}")
    private String raiz;

    @Value("${xlo.disco.diretorio-carros}")
    private String diretorioImagens;

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

    @PostMapping("cadastro-anuncio")
    public Veiculo salvarVeiculoEOpcionais(@RequestBody AnuncioDTO anuncio) {
        Usuario usuario = usuarioRepository.findById(anuncio.getIdUsuario());
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findByTipoNome(anuncio.getVeiculo().getTipo());
        MarcaVeiculo marca = marcaRepository.findByMarcaAndTipoVeiculo(anuncio.getVeiculo().getMarca(), tipoVeiculo);
        Veiculo v = new Veiculo();
        v.setIdUsuario(usuario);
        v.setDescricao(anuncio.getVeiculo().getDescricao());
        v.setPreco(anuncio.getVeiculo().getPreco());
        v.setKmRodado(anuncio.getVeiculo().getKm());
        v.setAno(anuncio.getVeiculo().getAno());
        v.setMarca(marca);

        Veiculo v1 = veiculoRepository.save(v);

        salvarOpcionalVeiculo(v1, anuncio.getOpcionais());
        return v;
    }

    private void salvarOpcionalVeiculo(Veiculo veiculo, List<Integer> opcionaisVeiculo) {
        for (Integer opcional : opcionaisVeiculo) {
            Opcionais opcionais = opcionaisRepository.findById(opcional);
            OpcionaisVeiculo op = new OpcionaisVeiculo();
            op.setIdOpcionais(opcionais);
            op.setIdVeiculo(veiculo);
            opcionaisVeiculoRepository.save(op);
        }
    }

    //veiculo e lista de opcionaisVeiculo
    @GetMapping("detalhes")
    public List<Veiculo> listaVeiculoEOpcionais() {
        return veiculoRepository.listaVeiculoEOpcionais();
    }

    @PostMapping("cadastro-anuncio/salvarImagem")
    public void salvarImagensAnuncio(@RequestParam Integer veiculoId, @RequestParam List<MultipartFile> imagens) {
        Path diretorioPath = Paths.get(this.raiz, this.diretorioImagens, veiculoId.toString() + "/");
        for (MultipartFile foto : imagens) {
            Path arquivoPath = diretorioPath.resolve(foto.getOriginalFilename());
            try {
                Files.createDirectories(diretorioPath);
                foto.transferTo(arquivoPath.toFile());
            } catch (IOException e) {
                throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
            }
        }
    }
}
