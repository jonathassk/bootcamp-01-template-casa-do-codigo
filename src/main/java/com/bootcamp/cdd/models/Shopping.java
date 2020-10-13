package com.bootcamp.cdd.models;

import javax.persistence.*;

@Entity
public class Shopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String nome;
    private String sobrenome;
    private String documento;
    private String endereco;
    private String complemento;
    private String cidade;
    @ManyToOne
    private State estado;
    @ManyToOne
    private Country pais;
    private String telefone;
    private String cep;

    public Shopping(String email, String nome, String sobrenome, String documento, String endereco, String complemento, String cidade, String telefone, String cep) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.documento = documento;
        this.endereco = endereco;
        this.complemento = complemento;
        this.cidade = cidade;
        this.telefone = telefone;
        this.cep = cep;
    }

    public void setEstadoAndPais(State estado, Country pais) {
        this.estado = estado;
        this.pais = pais;
    }
}