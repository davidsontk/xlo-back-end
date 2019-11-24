/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.dto;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author davidson ferreira
 */
public class AnuncioDTO {

    private VeiculoDTO veiculo;
    private List<Integer> opcionais = new ArrayList<Integer>();
    private Integer idUsuario;

    public VeiculoDTO getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoDTO veiculo) {
        this.veiculo = veiculo;
    }

    public List<Integer> getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(List<Integer> opcionais) {
        this.opcionais = opcionais;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

}
