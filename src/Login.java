import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Login {

private String nome;
private String senha;
private List<Usuarios> usuarios;

   public Login(){
    this.nome = "default";
    this.senha = "default";
    this.usuarios = new ArrayList<>();
    
   }
   public Login(List<Usuarios> usuarios) {
    this.nome = "default";
    this.senha = "default";
    this.usuarios = usuarios != null ? usuarios : new ArrayList<>(); // Inicializa lista se nula
}

   public String getSenha() {
    return senha;
}

public void setSenha(String senha) {
    this.senha = senha;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}
    Scanner sc = new Scanner(System.in);
    

    //Capturando as informações para LOGIN
    public void SistemaLogin(){
        System.out.print("Digite seu nome: ");
        setNome(sc.nextLine());
        System.out.print("Digite sua senha: ");
        setSenha(sc.nextLine());

        Autenticacao();
    }

    public void Autenticacao(){

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário registrado no sistema.");
            return;
        }
        boolean autenticado = false;
        for(Usuarios x : usuarios ){
            if (x.getNome().equals(nome) && x.getSenha().equals(senha)) {
                autenticado = true;
                break;
            }
        }

        if (autenticado) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + nome + "!");
        } else {
            System.out.println("Credenciais inválidas. Tente novamente.");
        }
    }
}

