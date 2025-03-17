package Entidades;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    
    
    public boolean diasdeNFuncionamento(LocalDate dia){

        DayOfWeek diasdeNFuncionamento = dia.getDayOfWeek();

        return diasdeNFuncionamento == DayOfWeek.MONDAY || diasdeNFuncionamento == DayOfWeek.SUNDAY;
    }

    public boolean horariodeNFuncionamento(LocalTime horario){
        LocalTime abertura = LocalTime.of(7, 0);
        LocalTime fechamento = LocalTime.of(21, 0);

        return horario.isBefore(abertura) || horario.isAfter(fechamento);
    }

    public boolean estaDisponivel(LocalDate dia, LocalTime horario){
        if(diasdeNFuncionamento(dia) || horariodeNFuncionamento(horario)){
            return false;
        } else{
            return true;}
    }
}