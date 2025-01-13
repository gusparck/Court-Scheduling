package Entidades;

public class Usuarios {

    private String nome;
    private String senha;
    private String email;
    private String telefone;
    private int pontuacao;

    public Usuarios(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.email = "";
        this.telefone = "";
        this.pontuacao = 0;
    }
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public int getPontuacao() {return pontuacao;}
    public void setPontuacao(int pontuacao) {this.pontuacao = pontuacao;}
}
