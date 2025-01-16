package Console;

import Controle.Controle;
import Entidades.Usuarios;
import Entidades.Quadras;
import Entidades.Reservas;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        String nome, senha, email, telefone;
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
            case 1 -> {
                /*caso fizermos interface é melhor criar uma classe dentro de console para ler do
                teclado e printar*/

                Random random = new Random();
                BigInteger numeroGrande = new BigInteger(1024, random); 

                input.nextLine(); //limpando o buffer
                System.out.println("Realizando cadastro.\n");
                System.out.println("Digite um nome de usuario: ");
                nome = input.nextLine();
                System.out.println("\nDigite uma senha: ");
                senha = input.nextLine();
                System.out.println("\nDigite um e-mail para cadastro: ");
                email = input.nextLine();
                System.out.println("\nDigite um telefone válido: ");
                telefone = input.nextLine();
                ctrl.adicionarUsuario(numeroGrande.intValue(),nome, senha, email, telefone);

            }

            case 2 -> {
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
            }

            case 3 -> System.out.println("Conferindo ranking dos melhores jogadores.\n");
            //adicionar metodo ranking

            case 4 -> {
                return false;
            }

            default -> System.out.println("Digite uma opcao valida.\n");
        }
        return true;
    }

    public boolean menuCliente(Usuarios usuario) {
        int option;
        String senha, email, telefone;
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

                ctrl.adicionarQuadra(new Quadras(2, "Quadra 2", "Bairro: Loanda"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                System.out.print("Digite o ID da quadra que deseja jogar: ");
                int idQuadra = input.nextInt();
                input.nextLine();
                System.out.print("Digite o horario que deseja jogar(dd/MM/yyyy HH:mm): ");
                String inputHorario = input.nextLine();
                LocalDateTime horario = LocalDateTime.parse(inputHorario, formatter);
                ctrl.reservarQuadra(usuario.getId(), idQuadra, horario);
                break;

            case 2:
                //implementar verificação de quadra locada
                break;

            case 3:
                //exibirInfo(usuario); por algum motivo email e telefone não aparecem aqui
                System.out.println("Deseja atualizar seus dados?\n");
                System.out.println("""
                \nSelecione uma opção:
                1-Sim
                2-Não
                """);
                int ans = input.nextInt();
                if(ans == 1){
                input.nextLine(); 
                System.out.println("Edição de dados:\n");
                System.out.println("\nDigite sua nova senha: ");
                senha = input.nextLine();
                System.out.println("\nDigite um novo e-mail para cadastro: ");
                email = input.nextLine();
                System.out.println("\nDigite um novo telefone válido: ");
                telefone = input.nextLine();
                ctrl.atualizarDados(senha, email, telefone); //cabe otimização nesse método
                System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                exibirInfo(usuario);
                
                } else {
                    break;
                }
                break;

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

    public void exibirInfo(Usuarios usuario){
        System.out.println("Nome: " + usuario.getNome() + ".");
        System.out.println("Senha: " + usuario.getSenha() + ".");
        System.out.println("E-mail: " + usuario.getEmail() + ".");
        System.out.println("Telefone: " + usuario.getTelefone() + ".\n");
    }
}