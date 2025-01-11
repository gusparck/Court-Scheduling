import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner ler = new Scanner(System.in);
        List<Usuarios>usuarios = new ArrayList<>();
        
        int option = 0;
        Cadastro cadastro = new Cadastro(usuarios);
            while(true){
                System.out.println("Protótipo - Menu\n");
                System.out.println("Selecione uma opção:\n1-Cadastrar usuário\n2-Fazer login\n3-Ranking de jogadores");
                option = ler.nextInt();
                switch(option){
                    case 1: 
                    System.out.println("Realizando cadastro.\n");
                    
                    cadastro.adicionarUsuario("Thiago", "123");
                    //adicionar método CADASTRO
                    break;

                    case 2: 
                    System.out.println("Fazendo login.\n");
                    Login login = new Login(usuarios);
                    login.SistemaLogin();
                    break;
                    //adicionar método LOGIN
                    //uma vez feito o login o usuário pode buscar infos sobre outros usuários

                    case 3: 
                    System.out.println("Conferindo ranking dos melhores jogadores.\n");
                    //adicionar método RANKING
                    break;
                }
            }
    }
}
