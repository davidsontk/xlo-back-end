/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.dto;

import br.com.xlo.model.Opcionais;
import br.com.xlo.model.Usuario;
import br.com.xlo.model.Veiculo;
import java.util.List;

/**
 *
 * @author davidson ferreira
 */
public class AnuncioComOpcionaisDTO {

    private Veiculo veiculo;
    private List<Opcionais> opcionais;

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public List<Opcionais> getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(List<Opcionais> opcionais) {
        this.opcionais = opcionais;
    }
    
}
