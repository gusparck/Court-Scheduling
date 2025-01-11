import java.util.ArrayList;
import java.util.List;

public class Cadastro {
    private List<Usuarios> usuarios;

    // Construtor para inicializar a lista e adicionar um usuário inicial
    public Cadastro(List<Usuarios> usuarios) {
        this.usuarios = usuarios != null ? usuarios : new ArrayList<>(); // Inicializa lista se nula
    }

    // Método para adicionar novos usuários
    public void adicionarUsuario(String nome, String senha) {
        Usuarios usuario = new Usuarios(nome, senha);
        usuarios.add(usuario);
        listarUsuarios();
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
