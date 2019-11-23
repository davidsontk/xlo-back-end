/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author William Fávaro
 */
public class OpcionaisVeiculoPK implements Serializable{
    protected Veiculo idVeiculo;
    protected Opcionais idOpcionais;

    public OpcionaisVeiculoPK() {}

    public OpcionaisVeiculoPK(Veiculo idVeiculo, Opcionais idOpcionais) {
        this.idVeiculo = idVeiculo;
        this.idOpcionais = idOpcionais;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idVeiculo);
        hash = 89 * hash + Objects.hashCode(this.idOpcionais);
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        return true;
    }
}
