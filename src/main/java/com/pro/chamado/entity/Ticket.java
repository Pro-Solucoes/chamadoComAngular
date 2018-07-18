package com.pro.chamado.entity;

import com.pro.chamado.enums.PrioridadeEnum;
import com.pro.chamado.enums.StatusEnum;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class Ticket {

    @Id
    private String id;

    @DBRef(lazy = true)
    private Usuario usuario;

    private Date date;

    private String titulo;

    private Integer numero;

    private StatusEnum status;
    private PrioridadeEnum prioridade;

    @DBRef(lazy = true)
    private Usuario  atribuidoUsuario;
    private String descricao;
    private String imagem;

    @Transient
    private List<AlteracaoStatus> alteracao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public PrioridadeEnum getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeEnum prioridade) {
        this.prioridade = prioridade;
    }

    public Usuario getAtribuidoUsuario() {
        return atribuidoUsuario;
    }

    public void setAtribuidoUsuario(Usuario atribuidoUsuario) {
        this.atribuidoUsuario = atribuidoUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<AlteracaoStatus> getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(List<AlteracaoStatus> alteracao) {
        this.alteracao = alteracao;
    }
}
