package com.pro.chamado.enums;

public enum StatusEnum {
    New,
    Atribuido,
    Resolvido,
    Aprovado,
    Reprovado,
    Fechado;

    public static StatusEnum getStatus(String status){
        switch (status){
            case "New": return New;
            case "Atribuido": return Atribuido;
            case "Resolvido": return Resolvido;
            case "Aprovado": return Aprovado;
            case "Reprovado": return Reprovado;
            case "Fechado": return Fechado;
            default:return New;
        }
    }
}
