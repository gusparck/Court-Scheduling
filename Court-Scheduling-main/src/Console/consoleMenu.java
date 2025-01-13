package Console;

import Controle.Controle;
import Entidades.Usuarios;

import java.util.Scanner;

public class consoleMenu {
    private Scanner input;
    private Controle ctrl;

    public consoleMenu() {
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
               /*caso fizermos interface é melhor criar uma classe dentro de console para ler do
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

                Usuarios usuario = ctrl.autenticacaoLogin(nome, senha);
                if (usuario!=null){
                    System.out.println("Login bem-sucedido! Bem-vindo, " + usuario.getNome() + "!");
                    boolean ativo = true;
                    while(ativo){
                        ativo = menuCliente(usuario);
                    }
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

    public boolean menuCliente(Usuarios usuario) {
        int option;
        System.out.println("""
                \nSelecione uma opção:
                1-Locar quadra
                2-Verificar quadras locadas
                3-Meus dados
                4-Desafiar jogadores
                5-Sair
                """);
        option = input.nextInt();
        switch (option) {
            case 1:
                //implementar locação de quadras
                break;

            case 2:
                //implementar verificação de quadra locada
                break;

            case 3:
                //implementar menu de edição

            case 4:
                //implementar esquema letz play
                break;

            case 5:
                return false;

            default:
                System.out.println("Digite uma opcao valida.\n");
                break;
        }
        return true;
    }
}