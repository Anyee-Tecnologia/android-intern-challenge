package com.desafiojoke.joke.Models;

import java.util.List;

/*
* @Object Joke na api existem 'category', 'icon_url', 'id', 'url' e 'value'
* como a API fornece os valores randomicamente, os atributos que serão necessários
* são 'value' com o conteúdo (piada) e o 'icon_url' que fornece o link para imagem.
* @method getValue vai obter o 'value' da api
* @method getIcon_url vai obter o 'icon_url' da api utilizada.
* */
public class Joke {
    private String[] category;
    private String value;
    private String icon_url;

    public Joke() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

}
