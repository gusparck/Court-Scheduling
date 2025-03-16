package Entidades;

import java.util.ArrayList;

public class Clientes extends Usuarios{
    private String email;
    private String telefone;
    private int pontuacao;
    private final ArrayList<Reservas> reservas;
    private final ArrayList<Desafios> desafios;

    public Clientes(int id, String nome, String senha, String email, String telefone) {
        super(id, nome, senha);
        this.email = email;
        this.telefone = telefone;
        this.pontuacao = 0;
        this.reservas = new ArrayList<>();
        this.desafios = new ArrayList<>();
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public int getPontuacao() {return pontuacao;}
    public void setPontuacao(int pontuacao) {this.pontuacao += pontuacao;}

    public ArrayList<Reservas> getReservas() {return reservas;}
    public void setReservas(Reservas reserva) {this.reservas.add(reserva);}

    public ArrayList<Desafios> getDesafios() {return desafios;}
    public void setDesafios (Desafios desafios) {this.desafios.add(desafios);}
}