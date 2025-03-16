package Entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Quadras {
    private int id;
    private String nome;
    private String endereco;
    private final ArrayList<LocalDateTime> reservas; //exibe horarios que estão reservados na quadra

    public Quadras(int id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.reservas = new ArrayList<>();
    }

    public int getId(){return id;}
    public void setId(int id ){this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    public ArrayList<LocalDateTime> getReservas() {return reservas;}

    public boolean estaDisponivel(LocalDateTime dataHorario){return !reservas.contains(dataHorario);}
}