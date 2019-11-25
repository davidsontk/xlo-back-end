/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.controller;

import br.com.xlo.dto.AnuncioComOpcionaisDTO;
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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

/**
 *
 * @author William Fávaro
 */
@RestController
@RequestMapping("/anuncio/")
@CrossOrigin("*")
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
        v.setKmRodado(anuncio.getVeiculo().getKmRodado());
        v.setAno(anuncio.getVeiculo().getAno());
        v.setMarca(marca);

        Veiculo v1 = veiculoRepository.save(v);

        salvarOpcionalVeiculo(v1, anuncio.getOpcionais());
        return v;
    }
    
    @PostMapping("editar-anuncio")
    public void editarAnuncio(@RequestBody AnuncioDTO anuncio) {
        Usuario usuario = usuarioRepository.findById(anuncio.getIdUsuario());
        TipoVeiculo tipoVeiculo = tipoVeiculoRepository.findByTipoNome(anuncio.getVeiculo().getTipo());
        MarcaVeiculo marca = marcaRepository.findByMarcaAndTipoVeiculo(anuncio.getVeiculo().getMarca(), tipoVeiculo);
        Veiculo v = new Veiculo();
        v.setId(anuncio.getVeiculo().getId());
        v.setIdUsuario(usuario);
        v.setDescricao(anuncio.getVeiculo().getDescricao());
        v.setPreco(anuncio.getVeiculo().getPreco());
        v.setKmRodado(anuncio.getVeiculo().getKmRodado());
        v.setAno(anuncio.getVeiculo().getAno());
        v.setMarca(marca);

        veiculoRepository.save(v);

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

//    @PostMapping("salvarImagem")
    @RequestMapping(value = "salvarImagem", method = RequestMethod.POST)
    public void salvarImagensAnuncio(@RequestParam String veiculoId, @RequestParam MultipartFile imagens) {
        Path diretorioPath = Paths.get(this.raiz, this.diretorioImagens, veiculoId + "/");

        Path arquivoPath = diretorioPath.resolve(imagens.getOriginalFilename());
        try {
            Files.createDirectories(diretorioPath);
            imagens.transferTo(arquivoPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
        }
    }

    @GetMapping("/buscarDetalhesAnuncio/{idVeiculo}")
    public AnuncioComOpcionaisDTO buscarDetalhesAnuncio(@PathVariable Integer idVeiculo) {
        AnuncioComOpcionaisDTO anuncio = new AnuncioComOpcionaisDTO();
        Veiculo v = veiculoRepository.findById(idVeiculo);
        anuncio.setVeiculo(v);
        anuncio.setOpcionais(opcionaisVeiculoRepository.buscarOpcionaisPorVeiculo(v.getId()));

        return anuncio;
    }

    @RequestMapping(value = "/buscarDetalhesAnuncio/buscarImagens/{idVeiculo}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> buscarTodasImagens(@PathVariable Integer idVeiculo) throws IOException {
        List<String> listaPath = new ArrayList<String>();
        List<byte[]> listaEmByte = new ArrayList<byte[]>();
        Path diretorioPath = Paths.get(this.raiz, this.diretorioImagens, idVeiculo + "/");
        File file = new File(diretorioPath.toString());
        File[] arquivos = file.listFiles();

        for (File fileTmp : arquivos) {
            //InputStream in = getClass().getClassLoader().getResourceAsStream(fileTmp.toString());
            //InputStream in = new ByteArrayInputStream(Charset.forName("UTF-16").encode(fileTmp.toString()).array());
            byte[] fileContent = FileUtils.readFileToByteArray(new File(fileTmp.toString()));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            //InputStream in = new ByteArrayInputStream(fileTmp.toString().getBytes());

            listaPath.add(encodedString);
        }

        return listaPath;
    }

}
