package Console;

import Controle.Controle;
import Entidades.Usuarios;

import java.util.Scanner;

public class ConsoleMenu {
    private Scanner input;
    private Controle ctrl;

    public ConsoleMenu() {
        input = new Scanner(System.in);
        ctrl = new Controle();
    }

    //pode ser util usar um metodo desse caso a gente mexa com bd ou disco
    public void inicializador(){
        boolean ativo = true;
        while(ativo){
            ativo = menuPrincipal();
        }

        System.out.println("\nEncerrando...");
    }

    public boolean menuPrincipal() {
        int option;
        String nome, senha;
        System.out.println("Protótipo - Menu\n");
        System.out.println("""
                Selecione uma opção:
                1-Cadastrar usuário
                2-Fazer login
                3-Ranking de jogadores
                4-Sair
                """);
        option = input.nextInt();
        switch (option) {
            case 1:
               /*caso fizermos interface e melhor criar uma classe dentro de console para ler do
               teclado e printar*/
                input.nextLine(); //limpando o buffer
                System.out.println("Realizando cadastro.\n");
                System.out.println("Digite um nome de usuario: ");
                nome = input.nextLine();
                System.out.println("\nDigite uma senha: ");
                senha = input.nextLine();
                ctrl.adicionarUsuario(nome, senha);
                break;

            case 2:
                input.nextLine(); //limpando o buffer
                System.out.println("Fazendo login.\n");
                System.out.print("Digite seu nome: ");
                nome = input.nextLine();
                System.out.print("Digite sua senha: ");
                senha = input.nextLine();
                if (ctrl.autenticacaoLogin(nome, senha)){
                    System.out.println("Login bem-sucedido! Bem-vindo, " + nome + "!");
                    //prosseguir para outro menu ainda nao criado
                    return false;
                }
                else{
                    System.out.println("Ocorreu um erro durante a autenticação. Tente novamente.\n");
                }
                break;

            case 3:
                System.out.println("Conferindo ranking dos melhores jogadores.\n");
                //adicionar metodo ranking
                break;

            case 4:
                return false;

            default:
                System.out.println("Digite uma opcao valida.\n");
                break;
        }
        return true;
    }
}