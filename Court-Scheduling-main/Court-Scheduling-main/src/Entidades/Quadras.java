package Entidades;

import java.util.ArrayList;

public class Quadras {
    private int id;
    private String nome;
    private String endereco;
    private final ArrayList<Dia> horarios;

    public Quadras(int id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.horarios = new ArrayList<>();
    }

    public int getId(){return id;}
    public void setId(int id ){this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    public ArrayList<Dia> getHorarios() {return horarios;}
}