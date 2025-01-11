package Controle;
/*classe de controle, aqui estarão todos métodos do programa, evitem usar aqui entrada
e saida de dados*/
import Entidades.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class Controle
{
    // lista de usuarios
    private ArrayList <Usuarios> usuarios;
    public Controle () {
        usuarios = new ArrayList<>();
    }

    // Metodo para adicionar novos usuarios
    public void adicionarUsuario(String nome, String senha) {
        //necessita de metodo para impedir usuarios com mesmo nome
        Usuarios usuario = new Usuarios(nome, senha);
        usuarios.add(usuario);
    }

    //metodo de autenticacao de login
    public boolean autenticacaoLogin(String nome, String senha){
        if (usuarios.isEmpty()) {
            return false;
        }

        boolean autenticado = false;
        for(Usuarios x : usuarios){
            if (x.getNome().equals(nome) && x.getSenha().equals(senha)) {
                autenticado = true;
                break;
            }
        }

        if (autenticado) {
            return true;
        } else {
            return false;
        }
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
