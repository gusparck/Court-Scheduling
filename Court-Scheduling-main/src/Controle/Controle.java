package Controle;
import Entidades.Quadras;
import Entidades.Reservas;
/*classe de controle, aqui estarão todos métodos do programa, evitem usar aqui entrada
e saida de dados*/
import Entidades.Usuarios;

import java.time.LocalTime;
import java.util.ArrayList;

public class Controle
{
    // lista de usuarios/Quadras
    private ArrayList <Usuarios> usuarios;
    private ArrayList<Quadras> quadras;
    private ArrayList<Reservas> reservas;

    public Controle () {
        usuarios = new ArrayList<>();
        quadras = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    // Metodo para adicionar novos usuarios
    public void adicionarUsuario(int id, String nome, String senha, String email, String telefone){
        //necessita de metodo para impedir usuarios com mesmo nome
        Usuarios usuario = new Usuarios(id, nome, senha, email, telefone);
        usuarios.add(usuario);
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

    public boolean atualizarDados(String novaSenha, String novoEmail, String novoTelefone){

        for (Usuarios usuario : usuarios) {
                if (novaSenha != null && !novaSenha.isEmpty()) {
                    usuario.setSenha(novaSenha);
                }
                if (novoEmail != null && !novoEmail.isEmpty()) {
                    usuario.setEmail(novoEmail);
                }
                if (novoTelefone != null && !novoTelefone.isEmpty()) {
                    usuario.setTelefone(novoTelefone);
                }
                System.out.println("Dados do usuário atualizados com sucesso!");
                return true;
            }
        return false;
    }

    public boolean reservarQuadra(int usuarioId, int quadraId, LocalTime horario) {
    Quadras quadra = quadras.stream()
        .filter(q -> q.getId() == quadraId)
        .findFirst()
        .orElse(null);

    Usuarios usuario = usuarios.stream()
        .filter(u -> u.getId() == usuarioId)
        .findFirst()
        .orElse(null);

    // Verifica se a quadra já está reservada no horário
    boolean conflito = reservas.stream()
        .anyMatch(r -> r.getQuadra().getId() == quadraId && r.getHorario().equals(horario));

    if (quadra != null && usuario != null && !conflito) {
        reservas.add(new Reservas(reservas.size() + 1, usuario, quadra, horario));
        System.out.println("Reserva feita com sucesso!");
        return true;
    }

    System.out.println("Não foi possível realizar a reserva. Quadra já reservada nesse horário.");
    return false;
}

public void adicionarQuadra(Quadras quadra) {
    quadras.add(quadra);
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
