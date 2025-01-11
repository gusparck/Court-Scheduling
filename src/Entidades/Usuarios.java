package Entidades;

public class Usuarios {

    private String nome;
    private String senha;
    
    public Usuarios(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
    public String getNome() {
        return nome;
    }
    public String getSenha() {
        return senha;
    }

}
