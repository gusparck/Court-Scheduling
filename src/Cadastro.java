import java.util.ArrayList;
import java.util.List;

public class Cadastro {
    List<Usuarios> usuarios = new ArrayList<>();

    // Construtor para inicializar a lista e adicionar um usuário inicial
    public Cadastro() {
        usuarios = new ArrayList<>();
    }

    // Método para adicionar novos usuários
    public void adicionarUsuario(String nome, String senha) {
        Usuarios usuario = new Usuarios(nome, senha);
        usuarios.add(usuario);
       // listarUsuarios();
    }

    // Método para exibir os usuários cadastrados (apenas para teste)
    public void listarUsuarios() {
        System.out.println("Usuários cadastrados:");
        for (Usuarios usuario : usuarios) {
            System.out.println("Nome: " + usuario.getNome());
        }
    }

    // Método para acessar a lista de usuários
    public List<Usuarios> getUsuarios() {
        return usuarios;
    }
}
