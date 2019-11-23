/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.dto;

import br.com.xlo.model.Opcionais;
import br.com.xlo.model.Veiculo;

/**
 *
 * @author William Fávaro
 */
public class AnuncioDTO {
    private Veiculo veiculo;
    private Opcionais opcionais;

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Opcionais getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(Opcionais opcionais) {
        this.opcionais = opcionais;
    }
    
    
}
