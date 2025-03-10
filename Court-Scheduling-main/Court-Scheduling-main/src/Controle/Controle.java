package Controle;

import Entidades.Dia;
import Entidades.Quadras;
import Entidades.Reservas;
import Entidades.Usuarios;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*classe de controle, aqui estarão todos métodos do programa, evitem usar aqui entrada
e saida de dados*/
public class Controle
{
    // lista de usuarios/Quadras
    private final ArrayList <Usuarios> usuarios;
    private final ArrayList<Quadras> quadras;
    private final ArrayList<Reservas> reservas;

    DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");

    public Controle () {
        usuarios = new ArrayList<>();
        quadras = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    // Metodo para adicionar novos usuarios
    public int adicionarUsuario(int id, String nome, String senha, String email, String telefone){
        if (nome.isEmpty() || senha.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            return 1; //verifica caso campos estejam em branco
        }
        else {
            //pesquisa um usuario com o nome fornecido para evitar duplicatas
            if (pesquisarUsuarios(nome,"","",1) != null ||
                pesquisarUsuarios("",email,"",2)!= null ||
                pesquisarUsuarios("","",telefone,3) != null)
            {
                return 2;
            }
            Usuarios usuario = new Usuarios(id, nome, senha, email, telefone);
            usuarios.add(usuario);
            return 0;
        }
    }

    //metodo de autenticacao de login
    public Usuarios autenticacaoLogin(String nome, String senha) {
        if (usuarios.isEmpty()) {
            return null;
        }

        for(Usuarios x : usuarios){
            if (x.getNome().equals(nome) && x.getSenha().equals(senha)) {
                return x;
            }
        }

        return null;
    }

    public void atualizarDados(Usuarios usuario,String novaSenha, String novoEmail,
                                  String novoTelefone, int op){
        if (op==1){
            usuario.setSenha(novaSenha);
        } else if (op==2) {
            usuario.setEmail(novoEmail);
        }
        else if (op==3){
            usuario.setTelefone(novoTelefone);
        }
    }

    public Usuarios pesquisarUsuarios(String nome, String email, String telefone, int op){
        if (usuarios.isEmpty()) {
            return null;
        }
        if (op==1) {
            for (Usuarios x : usuarios) {
                if (x.getNome().equals(nome)) {
                    return x;
                }
            }
        } else if (op==2) {
            for (Usuarios x : usuarios) {
                if (x.getEmail().equals(email)) {
                    return x;
                }
            }
        }
        else if (op==3) {
            for (Usuarios x : usuarios) {
                if (x.getTelefone().equals(telefone)) {
                    return x;
                }
            }
        }
        return null;
    }

    public ArrayList<Quadras> exibirQuadras(){
        return quadras;
    }

    public ArrayList<String> exibirDisponibilidadeQuadra(Quadras quadra, String dia){
        LocalDateTime diaFormatado = LocalDateTime.parse(dia, formatterDay);
        Dia d = null;
        for (Dia x : quadra.getHorarios()){
            if (x.getDia().equals(diaFormatado)) {
                d = x;
                break;
            }
        }
        if (d == null)
            return null;

        ArrayList<String> horariosDisponiveis = new ArrayList<>();
        for (Dia.Hora h : d.getHorasDisponiveis()) {
            String horaFormatadaInicio = h.getHoraInicio().format(formatterHour);
            String horaFormatadaFim = h.getHoraFim().format(formatterHour);
            String horaDisponivel = horaFormatadaInicio + " - " + horaFormatadaFim;
            horariosDisponiveis.add(horaDisponivel);
        }
        if(horariosDisponiveis.isEmpty())
            return null;

        return horariosDisponiveis;
    }

    public boolean reservarQuadra(Usuarios usuario, Quadras quadra, String dia, String hora) {
        LocalDateTime diaFormatado = LocalDateTime.parse(dia, formatterDay);
        Dia d = null;
        for (Dia x : quadra.getHorarios()){
            if (x.getDia().equals(diaFormatado)) {
                d = x;
                break;
            }
        }
        if (d == null)
            return false;

        String[] horario = hora.split(" - ", 2);
        LocalDateTime horaInicioFormatada = LocalDateTime.parse(horario[0], formatterHour);
        LocalDateTime horaFimFormatada = LocalDateTime.parse(horario[1], formatterHour);
        Dia.Hora hI = null;
        Dia.Hora hF = null;
        for (Dia.Hora x : d.getHorasDisponiveis()){
            if (x.getHoraInicio().equals(horaInicioFormatada) && x.getHoraFim().equals(horaFimFormatada)) {
                hI = x;
                hF = x;
                break;
            }
        }
        if (hI == null)
            return false;

        d.getHorasDisponiveis().remove(hI);
        d.getHorasDisponiveis().remove(hF);

        usuario.setLocacoes(d);
        return true;
    }

    public void adicionarQuadra(Quadras quadra) {
        quadras.add(quadra);
    }

    public ArrayList<Dia> exibirLocacoes(Usuarios usuario){
        return usuario.getLocacoes();
    }
    

    // metodo para exibir os usuários cadastrados (apenas para teste)
    //nao pode estar nesta classe
//    public void listarUsuarios() {
//        System.out.println("Usuários cadastrados:\n");
//        for (Usuarios i : usuarios) {
//            System.out.println("Nome: " + i.getNome());
//        }
//    }
}
