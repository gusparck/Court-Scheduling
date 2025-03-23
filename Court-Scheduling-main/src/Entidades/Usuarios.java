package Entidades;

import java.io.Serial;
import java.io.Serializable;

public class Usuarios implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String senha;

    public Usuarios(int id, String nome, String senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "nome. " + nome;
    }

    public int getId() { return id; };
    public void setId(int id) { this.id = id; };

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}