package com.eletronico.cardviewbreno;

public class User {

    private long id;
    private String usuario;
    private String senha;

    public User(long id, String usuario, String senha) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }

    public long getId() { return id ; }

    public String getUsuario() { return usuario; }

    public String getSenha() { return senha; }
}
