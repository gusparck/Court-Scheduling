package Entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Quadras {
    private int id;
    private String nome;
    private String endereco;
    private List<Periodo> indisponibilidades;
    
    //private ArrayList<Horarios> disponibilidade; //não sei como implementar

    public Quadras(int id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.indisponibilidades = new ArrayList<>();
    }

    public Quadras(){

    }

    public int getId(){return id;}
    public void setId(int id ){this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public List<Periodo> getIndisponibilidades() {return indisponibilidades;}

    public void adicionarIndisponibilidade(LocalDateTime inicio, LocalDateTime fim) {
        indisponibilidades.add(new Periodo(inicio, fim));
    }

    public boolean isDisponivel(LocalDateTime horario) {
        // Verifica se o horário está dentro de algum período de indisponibilidade
        return indisponibilidades.stream()
                .noneMatch(periodo -> !horario.isBefore(periodo.getInicio()) && !horario.isAfter(periodo.getFim()));
    }
}


