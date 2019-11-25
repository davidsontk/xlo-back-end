/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.dto;

import br.com.xlo.controller.UserController;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author davidson ferreira
 */
public class AnuncioTelaInicialDTO {

    @Value("${xlo.disco.raiz}")
    private String raiz;

    @Value("${xlo.disco.diretorio-carros}")
    private String diretorioImagens;

    private String descricao;
    private Double preco;
    private String pathImage;

    private Logger log = LogManager.getLogger(AnuncioTelaInicialDTO.class);

    public AnuncioTelaInicialDTO(String descricao, Double preco, Integer idVeiculo) {
        this.descricao = descricao;
        this.preco = preco;
        Path diretorioPath = Paths.get(this.raiz, this.diretorioImagens, idVeiculo + "/");
        File file = new File(diretorioPath.toString());
        File[] arquivos = file.listFiles();

        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(arquivos[0].toString()));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            this.pathImage = encodedString;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Erro ao buscar imagem do idVeiculo =" + idVeiculo, e);
        }
    }

    public AnuncioTelaInicialDTO() {

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

}
