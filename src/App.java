import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner ler = new Scanner(System.in);
        
        int option = 0;
        Cadastro cadastro = new Cadastro();
            while(option != 4){
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
                    Login login = new Login();
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
