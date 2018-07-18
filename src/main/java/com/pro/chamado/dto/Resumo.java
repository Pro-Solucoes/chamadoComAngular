package com.pro.chamado.dto;

import java.io.Serializable;

public class Resumo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer montanteNovo;
    private Integer montanteResolvido;
    private Integer montanteAprovado;
    private Integer montanteDisaprovado;
    private Integer montanteAtribuido;
    private Integer montanteFechado;

    public Integer getMontanteNovo() {
        return montanteNovo;
    }

    public void setMontanteNovo(Integer montanteNovo) {
        this.montanteNovo = montanteNovo;
    }

    public Integer getMontanteResolvido() {
        return montanteResolvido;
    }

    public void setMontanteResolvido(Integer montanteResolvido) {
        this.montanteResolvido = montanteResolvido;
    }

    public Integer getMontanteAprovado() {
        return montanteAprovado;
    }

    public void setMontanteAprovado(Integer montanteAprovado) {
        this.montanteAprovado = montanteAprovado;
    }

    public Integer getMontanteDisaprovado() {
        return montanteDisaprovado;
    }

    public void setMontanteDisaprovado(Integer montanteDisaprovado) {
        this.montanteDisaprovado = montanteDisaprovado;
    }

    public Integer getMontanteAtribuido() {
        return montanteAtribuido;
    }

    public void setMontanteAtribuido(Integer montanteAtribuido) {
        this.montanteAtribuido = montanteAtribuido;
    }

    public Integer getMontanteFechado() {
        return montanteFechado;
    }

    public void setMontanteFechado(Integer montanteFechado) {
        this.montanteFechado = montanteFechado;
    }
}
