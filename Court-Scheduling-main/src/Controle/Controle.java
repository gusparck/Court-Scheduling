package Controle;

import Entidades.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/*classe de controle, aqui estarão todos métodos do programa, evitem usar aqui entrada
e saida de dados*/
public class Controle
{
    // lista de usuarios/Quadras
    private ArrayList <Clientes> clientes;
    private ArrayList<Quadras> quadras;
    private ArrayList <Clientes> ranking;

    public ArrayList<Clientes> getClientes() { return clientes; }
    public void setClientes(ArrayList <Clientes> clientes) { this.clientes = clientes; }
    public void addClientes(Clientes cliente) { clientes.add(cliente);}

    public ArrayList<Quadras> getQuadras() { return quadras; }
    public void setQuadras(ArrayList<Quadras> quadras) { this.quadras = quadras; }
    public void addQuadras(Quadras quadra) { quadras.add(quadra); }

    public ArrayList<Clientes> getRanking() { return ranking; }
    public void setRanking() { ranking = clientes; }

    Admins adm = new Admins(0, "adm01", "es25");
    public Admins getAdmin() { return adm; }

    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Controle () {
        clientes = new ArrayList<>();
        quadras = new ArrayList<>();
    }

    // Metodo para adicionar novos usuarios
    public int adicionarUsuario(Clientes cliente){

        if (cliente.getNome().isEmpty() || cliente.getSenha().isEmpty()
                || cliente.getEmail().isEmpty() || cliente.getTelefone().isEmpty()) {
            return 1; //verifica caso campos estejam em branco
        }
        else {
            //pesquisa um usuario com o nome fornecido para evitar duplicatas
            if (!clientes.isEmpty())
            {
                for (Clientes x : clientes) {
                    if (x.getNome().equals(cliente.getNome())) { return 2; }
                }
                
            }
            addClientes(cliente);
            return 0;
        }
    }

    //metodo de autenticacao de login
    public Clientes autenticacaoLogin(String nome, String senha) {
        if (clientes.isEmpty()) {
            return null;
        }

        for(Clientes x : clientes){
            if (x.getNome().equals(nome) && x.getSenha().equals(senha)) {
                return x;
            }
        }

        return null;
    }

    public Clientes pesquisarUsuarios(int id){
        if (clientes.isEmpty()) {
            return null;
        }
            for (Clientes x : clientes) {
                if (x.getId() == id) {
                    return x;
                }
            }
        return null;
    }

    public Quadras pesquisarQuadras(int id){
        for (Quadras x : quadras) {
            if (x.getId() == id) {
                return x;
            }
        }
        return null;
    }

    public void reservarQuadra(Quadras quadra, Clientes cliente, LocalDateTime horario){
        Reservas novaReserva = new Reservas(cliente,quadra,horario);
        quadra.getReservas().add(novaReserva);
        cliente.getReservas().add(novaReserva);
    }

    public void cancelarReserva(Clientes cliente, Reservas reserva){
        cliente.getReservas().remove(reserva);
        Quadras quadra = reserva.getQuadra();
        quadra.getReservas().remove(reserva);
    }

    public void enviarDesafio(Clientes desafiante, Clientes desafiado,
                              Quadras quadraDesafio, LocalDateTime horario){
        Desafios desafio = new Desafios(desafiante,desafiado,quadraDesafio,horario);
        desafiante.setDesafios(desafio);
        desafiado.setDesafios(desafio);
    }

    public LocalDateTime stringToDate (String stringDate){
        try {
            return LocalDateTime.parse(stringDate, formatterDate);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }

    public String dateToString (LocalDateTime date){
        try{
            return date.format(formatterDate);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }
}