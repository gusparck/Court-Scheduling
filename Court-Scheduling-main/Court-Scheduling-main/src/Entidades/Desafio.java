package Entidades;

import java.util.ArrayList;

public class Desafio {
    
    

    private  int idDesafio;
    private int idDesafiador;

    private int idDesafiante;
  
    private Usuarios usuario;
    private boolean statusDesafio;
    private ArrayList<Dia> locacoes;

    public Desafio(int idDesafio, int idDesafiante,int idDesafiador, Usuarios usuario, boolean statusDesafio) {
        this.idDesafio = idDesafio;
        this.idDesafiante = idDesafiante;
        this.idDesafiador = idDesafiador;
        this.usuario = usuario;
        this.statusDesafio = statusDesafio;
        this.locacoes = new ArrayList<>();
   
    }

    public int getIdDesafio() {
        return idDesafio;
    }
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setStatusDesafio(boolean statusDesafio) {
        this.statusDesafio = statusDesafio;
    }
    
    public boolean isStatusDesafio() {
        return statusDesafio;
    }

    public void setIdDesafio(int idDesafio) {
        this.idDesafio = idDesafio;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    public void setIdDesafiador(int idDesafiador) {
        this.idDesafiador = idDesafiador;
    }

    public int getIdDesafiador() {
        return idDesafiador;
    }

    public void setIdDesafiante(int idDesafiante) {
        this.idDesafiante = idDesafiante;
    }

    public int getIdDesafiante() {
        return idDesafiante;
    }
    public ArrayList<Dia> getLocacoes() {return locacoes;}
    public void setLocacoes(Dia locacoes) {this.locacoes.add(locacoes);}
  
}
