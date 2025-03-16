package Entidades;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Usuarios {

    private int id;
    private String nome;
    private String senha;
    private String email;
    private String telefone;
    private int pontuacao;
    private String tipoConta;
    //private int posicaoRanking;
    private ArrayList<Dia> locacoes;
    private boolean desafiado;

    

    public Usuarios(int id, String nome, String senha, String email, String telefone, String tipoConta, boolean desafiado) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.pontuacao = 0;
        this.tipoConta = tipoConta;
        this.locacoes = new ArrayList<>();
        this.desafiado = desafiado;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

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

    public String getTipoConta(){ return tipoConta;}

    public boolean isDesafiado() {
        return desafiado;
    }

    public void setDesafiado(boolean desafiado) {
        this.desafiado = desafiado;
    }
    
    
    //public int getPosicaoRanking(){};
    //public void setPosicaoRanking(){};

    public ArrayList<Dia> getLocacoes() {return locacoes;}
    public void setLocacoes(Dia locacoes) {this.locacoes.add(locacoes);}
}
