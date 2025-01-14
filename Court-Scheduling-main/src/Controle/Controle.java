package Controle;
/*classe de controle, aqui estarão todos métodos do programa, evitem usar aqui entrada
e saida de dados*/
import Entidades.Usuarios;
import java.util.ArrayList;

public class Controle
{
    // lista de usuarios
    private ArrayList <Usuarios> usuarios;
    public Controle () {
        usuarios = new ArrayList<>();
    }

    // Metodo para adicionar novos usuarios
    public void adicionarUsuario(String nome, String senha, String email, String telefone){
        //necessita de metodo para impedir usuarios com mesmo nome
        Usuarios usuario = new Usuarios(nome, senha, email, telefone);
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

    

    // metodo para exibir os usuários cadastrados (apenas para teste)
    //nao pode estar nesta classe
//    public void listarUsuarios() {
//        System.out.println("Usuários cadastrados:\n");
//        for (Usuarios i : usuarios) {
//            System.out.println("Nome: " + i.getNome());
//        }
//    }
}
