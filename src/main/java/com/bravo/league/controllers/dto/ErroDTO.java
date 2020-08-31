package com.bravo.league.controllers.dto;

public class ErroDTO {

    private String erro;

    private String property;

    public ErroDTO(String erro, String property) {
        this.erro = erro;
        this.property = property;
    }

    public ErroDTO(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public String getProperty() {
        return property;
    }

}
