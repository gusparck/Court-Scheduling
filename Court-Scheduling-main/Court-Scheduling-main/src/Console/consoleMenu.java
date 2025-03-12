package Console;

import Controle.Controle;
import Entidades.Dia;
import Entidades.Usuarios;
import Entidades.Quadras;
import Entidades.Reservas;
import Entidades.Admins;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class consoleMenu {
    private final Scanner input;
    private final Controle ctrl;

    int optionInt;
    String optionString;

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
        String nome, senha, email, telefone;
        System.out.println("Protótipo - Menu\n");
        System.out.println("""
                Selecione uma opção:
                1-Cadastrar usuário
                2-Fazer login
                3-Cadastrar Admin 
                4-Fazer Login como administrador
                5-Ranking de jogadores
                6-Sair
                """);
        optionInt = input.nextInt();
        switch (optionInt) {
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
                Usuarios usuario = new Usuarios(numeroGrande.intValue(), nome, senha, email, telefone, "Cliente");

                if (ctrl.adicionarUsuario(usuario)== 0){
                    System.out.println("\nUsuário criado com sucesso!");
                } else if (ctrl.adicionarUsuario(usuario)==1) {
                    System.out.println("\nPreencha todos os campos!");
                }
                else{
                    System.out.println("\nJá existe um usuário com este nome! Insira outro.");
                }

            }

            case 2 -> {
                input.nextLine(); //limpando o buffer
                System.out.println("\nFazendo login.\n");
                System.out.print("\nDigite seu nome: ");
                nome = input.nextLine();
                System.out.print("\nDigite sua senha: ");
                senha = input.nextLine();

                Usuarios usuario = ctrl.autenticacaoLogin(nome, senha, "Cliente");
                if (usuario!=null){
                    System.out.println("\nLogin bem-sucedido! Bem-vindo, " + usuario.getNome() + "!");
                    boolean ativo = true;
                    while(ativo){
                        ativo = menuCliente(usuario);
                    }
                }
                else{
                    System.out.println("\nOcorreu um erro durante a autenticação. Tente novamente.\n");
                }
            }

            case 3 ->{
                
                Random random = new Random();
                BigInteger numeroGrande = new BigInteger(1024, random); 

                input.nextLine(); //limpando o buffer
                System.out.println("Realizando cadastro.\n");
                System.out.println("Digite um nome do usuario administrador: ");
                nome = input.nextLine();
                System.out.println("\nDigite uma senha: ");
                senha = input.nextLine();
                System.out.println("\nDigite um e-mail para cadastro: ");
                email = input.nextLine();
                System.out.println("\nDigite um telefone válido: ");
                telefone = input.nextLine();

                Usuarios admin = new Admins(numeroGrande.intValue(), nome, senha, email, telefone, "Administrador");

                if (ctrl.adicionarUsuario(admin)== 0){
                    System.out.println("\nUsuário criado com sucesso!");
                } else if (ctrl.adicionarUsuario(admin) ==1) {
                    System.out.println("\nPreencha todos os campos!");
                }
                else{
                    System.out.println("\nJá existe um usuário com este nome! Insira outro.");
                }
            }

            case 4 -> {
                input.nextLine(); //limpando o buffer
                System.out.println("\nFazendo login.\n");
                System.out.print("\nDigite seu nome: ");
                nome = input.nextLine();
                System.out.print("\nDigite sua senha: ");
                senha = input.nextLine();

                Usuarios usuario = ctrl.autenticacaoLogin(nome, senha, "Administrador");
                if (usuario!=null){
                    System.out.println("\nLogin bem-sucedido! Bem-vindo, " + usuario.getNome() + "!");
                    boolean ativo = true;
                    while(ativo){
                        ativo = menuAdmin(usuario);
                    }
                }
                else{
                    System.out.println("\nOcorreu um erro durante a autenticação. Tente novamente.\n");
                }

            }

            case 5 -> System.out.println("\nConferindo ranking dos melhores jogadores.\n");
            //adicionar metodo ranking

            case 6 -> {
                return false;
            }

            default -> System.out.println("\nDigite uma opcao valida.\n");
        }
        return true;
    }

    public boolean menuCliente(Usuarios usuario) {
        String senha, email, telefone;
        System.out.println("""
                \nSelecione uma opção:
                1-Locar quadra
                2-Verificar quadras locadas
                3-Meus dados
                4-Desafiar jogadores
                5-Sair
                """);
        optionInt = input.nextInt();
        switch (optionInt) {
            case 1:
                //ctrl.adicionarQuadra(new Quadras(2, "Quadra 2", "Bairro: Loanda"));
                System.out.println("\nSelecione uma quadra:\n");
                ArrayList <Quadras> quadrasDisponiveis = ctrl.exibirQuadras();
                for (Quadras x : quadrasDisponiveis){
                    System.out.println("\n[1]" + x.getNome());
                }
                optionInt = input.nextInt();
                Quadras q = quadrasDisponiveis.get(optionInt-1);

                System.out.println("\nDigite o dia no qual deseja locar a quadra");
                optionString = input.nextLine();

                if (ctrl.exibirDisponibilidadeQuadra(q,optionString)!=null) {
                    for (String x : ctrl.exibirDisponibilidadeQuadra(q, optionString)) {
                        System.out.println("\n[1] " + x);
                    }
                    optionInt = input.nextInt();
                    String horario = ctrl.exibirDisponibilidadeQuadra(q,optionString).get(optionInt-1);

                    if (ctrl.reservarQuadra(usuario,q,optionString,horario)) {
                        System.out.println("\nQuadra locada com sucesso!");
                    }
                    else {
                        System.out.println("\nErro. Quadra não locada!");
                    }
                }
                else{
                    System.out.println("\nEste dia está indisponível!");
                }
                break;

            case 2:
                ArrayList<Dia> locacoes = ctrl.exibirLocacoes(usuario);
                for (Dia x : locacoes){
                    System.out.println(x.getQuadraPertencente() + " - " + x.getHorasDisponiveis());
                }
                break;

            case 3:
                exibirInfo(usuario); //por algum motivo email e telefone não aparecem aqui

                System.out.println("\nDeseja editar algum campo?");
                System.out.println("""
                        "[1] Sim" +
                        "[2] Não"
                        """);
                optionInt = input.nextInt();
                if(optionInt==1){
                    System.out.println("\nQual campo deseja alterar?");
                    optionString = (input.nextLine()).toLowerCase();
                    switch(optionString){
                        case "senha":
                            System.out.println("\nDigite sua nova senha: ");
                            senha = input.nextLine();
                            ctrl.atualizarDados(usuario,senha,"","",1);
                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfo(usuario);
                            break;

                        case "email":
                        case "e-mail":
                        case "e mail":
                            System.out.println("\nDigite um novo e-mail para cadastro: ");
                            email = input.nextLine();
                            ctrl.atualizarDados(usuario,"", email,"",2);
                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfo(usuario);
                            break;

                        case "telefone":
                            System.out.println("\nDigite um novo telefone válido: ");
                            telefone = input.nextLine();
                            ctrl.atualizarDados(usuario,"","",telefone,3);
                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfo(usuario);
                            break;

                        default:
                            System.out.println("\nDigite uma opcao valida.");
                            break;
                    }

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

    public boolean menuAdmin(Usuarios admin){

        System.out.println("""
            \nSelecione uma opção:
            1-Adicionar quadra
            2-Verificar quadras locadas
            3-Cancelar Locacao
            4-Meus dados
            5-Excluir usuarios
            6-Sair
            """);
        optionInt = input.nextInt();
        switch (optionInt) {  
            case 1:
                System.out.println("Escolha um ID para a quadra: ");
                int id = input.nextInt();
                System.out.println("Escreva o nome da quadra: ");
                String nomeQuadra = input.nextLine();
                System.out.println("Digite o endereço da quadra: ");
                String enderecoQuadra = input.nextLine();
                ctrl.adicionarQuadra(new Quadras(id, nomeQuadra, enderecoQuadra));
            break;
            case 2:
            ArrayList<Dia> locacoes = ctrl.exibirLocacoes(admin);
            for (Dia x : locacoes){
                System.out.println(x.getQuadraPertencente() + " - " + x.getHorasDisponiveis());
            }
            break;
            case 3: 
                System.out.println("Cancelamentos de agendamentos: ");
                System.out.println("Digite o nome do cliente: ");
                String nomeCliente = input.nextLine();
                ctrl.pesquisarUsuarios(admin);

            break;

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
        System.out.println("Pontuação: " + usuario.getPontuacao() + ".");
        //System.out.println("Posição no ranking: " + usuario.getPosicaoRanking() + ".");
    }
}