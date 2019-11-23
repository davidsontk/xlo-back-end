/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.xlo.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author davidson ferreira
 */
@Entity
@IdClass(OpcionaisVeiculoPK.class)
@Table(name = "opcionais_veiculo")
public class OpcionaisVeiculo implements Serializable{
    
    @Id
    @ManyToOne
    @JoinColumn(name = "id_veiculo", referencedColumnName = "id")
    private Veiculo idVeiculo;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_opcionais", referencedColumnName = "id")
    private Opcionais idOpcionais;

    public Veiculo getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Veiculo idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Opcionais getIdOpcionais() {
        return idOpcionais;
    }

    public void setIdOpcionais(Opcionais idOpcionais) {
        this.idOpcionais = idOpcionais;
    }
    
    static class OpcionaisVeiculoPK implements Serializable{
        protected Veiculo idVeiculo;
        protected Opcionais idOpcionais;
        
        public OpcionaisVeiculoPK() {}
        
        public OpcionaisVeiculoPK(Veiculo idVeiculo, Opcionais idOpcionais) {
            this.idVeiculo = idVeiculo;
            this.idOpcionais = idOpcionais;
        }
    }
}
