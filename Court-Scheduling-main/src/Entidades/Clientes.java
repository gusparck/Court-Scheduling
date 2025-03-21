package Entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class Clientes extends Usuarios implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private String email;
    private String telefone;
    private int pontuacao;
    Cartao cartaoCliente;
    
    private final ArrayList<Reservas> reservas;
    private final ArrayList<Desafios> desafios;
    private final ArrayList<Cartao> cartoes;

 
    public Clientes(int id, String nome, String senha, String email, String telefone, Cartao cartaoCliente) {
        super(id, nome, senha);
        this.email = email;
        this.telefone = telefone;
        this.pontuacao = 0;
        this.reservas = new ArrayList<>();
        this.desafios = new ArrayList<>();
        this.cartoes = new ArrayList<>();
        this.cartaoCliente = cartaoCliente;
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

    public ArrayList<Cartao> getCartoes() {return cartoes;}
    public void setCartoes (Cartao cartoes){ this.cartoes.add(cartoes);}

    public Cartao getCartaoCliente() { return cartaoCliente; }
    public void setCartaoCliente(Cartao cartaoCliente) {this.cartaoCliente = cartaoCliente;}
}
