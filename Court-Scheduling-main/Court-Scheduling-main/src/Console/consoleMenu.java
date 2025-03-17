package Console;

import Controle.Controle;
import Entidades.*;

import java.util.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class consoleMenu {
    private final Scanner input;
    private final Controle ctrl;

    Quadras quadra;

    int optionInt;
    String optionString;

    public consoleMenu() {
        input = new Scanner(System.in);
        ctrl = new Controle();
    }

    //pode ser util usar um metodo desse caso a gente mexa com bd ou disco
    public void inicializador() {
        boolean ativo = true;
        while (ativo) {
            ativo = menuPrincipal();
        }

        System.out.println("Encerrando...");
    }

    public boolean menuPrincipal() {
        String nome, senha, email, telefone;
        System.out.println("Protótipo - Menu");
        System.out.println("""
                Selecione uma opção:
                1-Cadastrar usuário
                2-Fazer login
                3-Ranking de jogadores
                4-Sair
                """);
        optionInt = input.nextInt();
        Clientes usuario;
        switch (optionInt) {
            case 1:
                Random random = new Random();
                BigInteger numeroGrande = new BigInteger(1024, random);

                input.nextLine(); //limpando o buffer
                System.out.println("Realizando cadastro.");

                System.out.println("Digite um nome de usuario: ");
                nome = input.nextLine();

                System.out.println("Digite uma senha: ");
                senha = input.nextLine();

                System.out.println("Digite um e-mail para cadastro: ");
                email = input.nextLine();

                System.out.println("Digite um telefone válido: ");
                telefone = input.nextLine();

                usuario = new Clientes(numeroGrande.intValue(), nome, senha, email, telefone);

                if (ctrl.adicionarUsuario(usuario) == 0) {
                    System.out.println("Usuário criado com sucesso!");
                } else if (ctrl.adicionarUsuario(usuario) == 1) {
                    System.out.println("Preencha todos os campos!");
                } else {
                    System.out.println("Já existe um usuário com este nome! Insira outro.");
                }
                break;

            case 2:
                input.nextLine(); //limpando o buffer

                System.out.println("Fazendo login.");

                System.out.print("Digite seu nome: ");
                nome = input.nextLine();

                System.out.print("Digite sua senha: ");
                senha = input.nextLine();

                if (ctrl.getAdmin().getNome().equals(nome) && ctrl.getAdmin().getSenha().equals(senha)) {
                    System.out.println("Login bem-sucedido! Bem-vindo, Administrador!");
                    boolean ativo = true;
                    while (ativo) {
                        ativo = menuAdmin(ctrl.getAdmin());
                    }
                    break;
                }

                usuario = ctrl.autenticacaoLogin(nome, senha);
                if (usuario != null) {
                    System.out.println("Login bem-sucedido! Bem-vindo, " + usuario.getNome() + "!");
                    boolean ativo = true;
                    while (ativo) {
                        ativo = menuCliente(usuario);
                    }
                } else {
                    System.out.println("Ocorreu um erro durante a autenticação. Tente novamente.");
                }
                break;

            case 3:
                input.nextLine(); //limpando o buffer

                if (ctrl.getClientes().isEmpty()){
                    System.out.println("Não existem usuários cadastrados.");
                    break;
                }
                System.out.println("Conferindo ranking dos melhores jogadores...");

                ctrl.setRanking();
                ctrl.getRanking().sort((c1, c2) -> Integer.compare(c2.getPontuacao(),
                        c1.getPontuacao()));

                int i = 0;
                for (Clientes x : ctrl.getRanking()) {
                    i++;
                    System.out.println(i + "°. " + x.getNome() + " - Pontuação. " + x.getPontuacao());
                }
                break;

            case 4:
                return false;

            default:
                System.out.println("Digite uma opção valida.");
                break;
        }
        return true;
    }

    public boolean menuCliente(Clientes usuario) {
        String senha, email, telefone;
        System.out.println("""
                Selecione uma opção:
                1-Locar quadra
                2-Verificar quadras locadas
                3-Meus dados
                4-Desafiar jogadores
                5-Desafios recebidos
                6-Desafios ativos
                7-Sair
                """);
        optionInt = input.nextInt();
        switch (optionInt) {
            case 1:
                input.nextLine(); //limpando o buffer

                if (ctrl.getQuadras().isEmpty()) {
                    System.out.println("Não existem quadras registradas. Tente mais tarde.");
                    break;
                }

                System.out.println("Digite o ID de uma quadra:");
                for (Quadras x : ctrl.getQuadras()) {
                    System.out.println("ID: " + x.getId() + " - " + x.getNome());
                }
                optionInt = input.nextInt();

                if (ctrl.pesquisarQuadras(optionInt)==null){
                    System.out.println("Digite um ID válido!");
                    break;
                }
                quadra = ctrl.pesquisarQuadras(optionInt);

                System.out.println("Digite o dia e horário no qual deseja locar a quadra:");
                System.out.println("*Digite no formato (dd/MM/yyyy HH:mm)");
                //cliente pode digitar fora do padrão
                input.nextLine(); //limpando o buffer
                optionString = input.nextLine();
                LocalDateTime horario = ctrl.stringToDate(optionString);

                if (quadra.estaDisponivel(horario)) {
                    if (pagamentoLocacao(usuario)) {
                        ctrl.reservarQuadra(quadra, usuario, horario);
                        System.out.println("Quadra locada com sucesso!");
                    } else {
                        System.out.println("Erro. Quadra não locada!");
                    }
                } else {
                    System.out.println("Este horário está indisponível!");
                }
                break;

            case 2:
                input.nextLine(); //limpando o buffer

                if (usuario.getReservas().isEmpty()){
                    System.out.println("Você ainda não locou nenhuma quadra!");
                    break;
                }

                int i = 0;
                for (Reservas x : usuario.getReservas()) {
                    i++;
                    System.out.println("[" + i + "]" +
                            x.getQuadra().getNome() + " - " + x.getHorario());
                }
                System.out.println("Deseja cancelar alguma locação?");
                System.out.println("""
                        [1] Sim
                        [2] Não
                        """);
                optionInt = input.nextInt();
                switch (optionInt) {
                    case 1:
                        System.out.println("Qual locação deseja cancelar?");
                        optionInt = input.nextInt();

                        LocalDateTime horarioCancelamento = usuario.getReservas()
                                .get(optionInt - 1).getHorario();

                        if (LocalDateTime.now().plusDays(1).isAfter(horarioCancelamento)) {
                            ctrl.cancelarReserva(usuario, usuario.getReservas().get(optionInt - 1));
                        } else {
                            System.out.println("Não é possível realizar o cancelamento" +
                                    "pois a data é muito próxima.");
                        }
                        break;

                    case 2:
                        break;

                    default:
                        System.out.println("Digite uma opcao valida.");
                        break;
                }
                break;

            case 3:
                input.nextLine(); //limpando o buffer

                exibirInfoCliente(usuario);

                System.out.println("Deseja editar algum campo?");
                System.out.println("""
                        [1] Sim
                        [2] Não
                        """);
                optionInt = input.nextInt();
                if (optionInt == 1) {
                    System.out.println("Qual campo deseja alterar?");
                    System.out.println("""
                                    [1] Senha
                                    [2] Telefone
                                    [3] Email
                            """);
                    optionInt = input.nextInt();
                    switch (optionInt) {
                        case 1:
                            System.out.println("Digite sua nova senha: ");
                            input.nextLine(); //limpando o buffer
                            senha = input.nextLine();
                            usuario.setSenha(senha);

                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfoCliente(usuario);
                            break;

                        case 2:
                            System.out.println("Digite um novo e-mail: ");
                            input.nextLine(); //limpando o buffer
                            email = input.nextLine();
                            usuario.setEmail(email); //verificacao email duplicado

                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfoCliente(usuario);
                            break;

                        case 3:
                            System.out.println("Digite um novo telefone: ");
                            telefone = input.nextLine(); //verificar telefone duplicado
                            input.nextLine(); //limpando o buffer
                            usuario.setTelefone(telefone);

                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfoCliente(usuario);
                            break;

                        default:
                            System.out.println("Digite uma opcao válida.");
                            break;
                    }

                }
                break;

            case 4:
                input.nextLine(); //limpando o buffer

                if (ctrl.getClientes().size() <= 1 || ctrl.getQuadras().isEmpty()) {
                    System.out.println("Não existem quadras ou usuários suficientes na plataforma.");
                    break;
                }

                System.out.println("Digite o ID do jogador que deseja desafiar: ");
                int idDesafiado = input.nextInt();
                if (ctrl.pesquisarUsuarios(idDesafiado) == null) {
                    System.out.println("ID de jogador não encontrado!");
                    break;
                }
                Clientes cDesafiado = ctrl.pesquisarUsuarios(idDesafiado);

                System.out.println("Digite o id da quadra escolhida:");
                for (Quadras x : ctrl.getQuadras()) {
                    System.out.println("ID: " + x.getId() + " - " + x.getNome());
                }
                optionInt = input.nextInt();

                if (ctrl.pesquisarQuadras(optionInt) == null) {
                    System.out.println("Digite um id válido!");
                }
                Quadras qDesafio = ctrl.pesquisarQuadras(optionInt);

                System.out.println("Digite o dia e horário no qual deseja locar a quadra:");
                System.out.println("*Digite no formato (dd/MM/yyyy HH:mm)");
                input.nextLine(); //limpando o buffer
                optionString = input.nextLine(); //adc verificacao de padrao do horario

                LocalDateTime hDesafio = ctrl.stringToDate(optionString);

                if (qDesafio.estaDisponivel(hDesafio)) {
                    ctrl.enviarDesafio(usuario, cDesafiado, qDesafio, hDesafio);
                    System.out.println("O desafio foi enviado! O desafiante deve aceita-lo.");
                } else {
                    System.out.println("Horário indisponível!");
                }
                break;

            case 5:
                input.nextLine(); //limpando o buffer

                if (usuario.getDesafios().isEmpty()) {
                    System.out.println("Nenhum desafio pendente.");
                    break;
                }
                for (Desafios x : usuario.getDesafios()) {
                    if (x.getDesafiado().equals(usuario) && x.getStatusDesafio()==null) {
                        mensagemDesafio(x);
                    }
                }
                break;

            case 6:
                input.nextLine(); //limpando o buffer

                ArrayList <Desafios> finalizados = new ArrayList<>();
                for (Desafios x : usuario.getDesafios()) {
                    if (x.getStatusDesafio()==null){
                        System.out.println("Status: desafio não respondido");
                        System.out.println("Quadra: " + x.getQuadraDesafio() + " - "
                                + "Horário: " + x.getHorarioDesafio());
                    }
                    else if (!x.getStatusDesafio()){
                        System.out.println("Status: desafio recusado");
                        System.out.println("Quadra: " + x.getQuadraDesafio() + " - "
                                + "Horário: " + x.getHorarioDesafio());
                        finalizados.add(x);
                    }
                    else {
                        System.out.println("Status: desafio aceito");
                        System.out.println("Quadra: " + x.getQuadraDesafio() + " - "
                                + "Horário: " + x.getHorarioDesafio());

                        if (LocalDateTime.now().isAfter(x.getHorarioDesafio())){
                            while(true) {
                                System.out.println("Quem venceu este desafio?");
                                System.out.println("""
                                        [1] Eu
                                        [2] Ele(a)
                                        """);
                                optionInt = input.nextInt();
                                if (optionInt == 1) {
                                    usuario.setPontuacao(10);
                                    break;
                                }
                                else if (optionInt == 2) {
                                    if (usuario.equals(x.getDesafiante())){
                                        x.getDesafiado().setPontuacao(10);
                                    }
                                    else{
                                        x.getDesafiante().setPontuacao(10);
                                    }
                                    break;
                                }
                                else {
                                    System.out.println("Digite uma opção válida.");
                                }
                            }
                            finalizados.add(x);
                        }
                    }
                }
                if (!finalizados.isEmpty()) {
                    for (Desafios x : finalizados) {
                        if (usuario.equals(x.getDesafiante())) {
                            x.getDesafiado().getDesafios().remove(x);
                        }
                        else{
                            x.getDesafiante().getDesafios().remove(x);
                        }
                        usuario.getDesafios().remove(x);
                    }
                }
                break;

            case 7:
                return false;

            default:
                System.out.println("Digite uma opcao valida.");
                break;
        }
        return true;
    }

    public boolean menuAdmin(Admins adm) {
        System.out.println("""
                Selecione uma opção:
                1-Adicionar quadra
                2-Editar quadra
                3-Sair
                """);
        optionInt = input.nextInt();
        switch (optionInt) {
            case 1:
                input.nextLine(); //limpando o buffer

                System.out.println("Digite o ID: ");
                int id = input.nextInt();
                if(ctrl.pesquisarQuadras(id)!=null){
                    System.out.println("Já existe uma quadra com este id");
                    break;
                }
                input.nextLine(); //limpando o buffer

                System.out.println("Digite o nome: ");
                input.nextLine(); //limpando o buffer
                String nomeQuadra = input.nextLine();

                System.out.println("Digite o endereço: ");
                input.nextLine(); //limpando o buffer
                String enderecoQuadra = input.nextLine();

                //System.out.println("Digite o horario de funcionamento: ");
                //optionString = input.nextLine();
                //System.out.println("*Digite no formato (dd/MM/yyyy HH:mm)");
                //pensar em um jeito de implementar horario de funcionamento

                ctrl.setQuadras(new Quadras(id, nomeQuadra, enderecoQuadra));

                System.out.println("Quadra adicionada com sucesso!");
                break;

            case 2:
                input.nextLine(); //limpando o buffer

                if (ctrl.getQuadras().isEmpty()) {
                    System.out.println("Adicione uma quadra primeiro!");
                    break;
                }

                for (Quadras q : ctrl.getQuadras()) {
                    System.out.println("ID: " + q.getId() + " - " + q.getNome());
                }
                System.out.println("Digite o ID da quadra que deseja editar");
                optionInt = input.nextInt();

                if (ctrl.pesquisarQuadras(optionInt)==null){
                    System.out.println("Digite um ID válido!");
                    break;
                }
                Quadras quadra = ctrl.pesquisarQuadras(optionInt);

                System.out.println("Qual campo deseja alterar?");
                System.out.println("""
                        [1] Nome
                        [2] Endereço
                        [3] Horário de funcionamento
                        """);
                optionInt = input.nextInt();
                switch (optionInt) {
                    case 1:
                        System.out.println("Digite o novo nome:");
                        input.nextLine(); //limpando o buffer
                        optionString = input.nextLine();
                        quadra.setNome(optionString);

                        System.out.println("Novos dados do quadra " + quadra.getNome());
                        break;

                    case 2:
                        System.out.println("Digite o novo endereço:");
                        optionString = input.nextLine();
                        input.nextLine(); //limpando o buffer
                        quadra.setEndereco(optionString);

                        System.out.println("Novos dados do quadra " + quadra.getEndereco());
                        break;

                    case 3:
                        System.out.println("**implementar");
                        break;
                }
                break;

            case 3:
                return false;

            default:
                System.out.println("Digite uma opcao valida.");
                break;
        }
        return true;
    }

    public void exibirInfoCliente(Clientes usuario) {
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Senha: " + usuario.getSenha());
        System.out.println("E-mail: " + usuario.getEmail());
        System.out.println("Telefone: " + usuario.getTelefone());
        System.out.println("Pontuação: " + usuario.getPontuacao());
    }

    public boolean pagamentoLocacao(Usuarios usuario) {
        System.out.println("Como deseja pagar a locação?");
        System.out.println("""
                [1] Cartão
                [2] Pix
                """);
        optionInt = input.nextInt();
        switch (optionInt) {
            case 1:
                System.out.println("**implementar algo que simule pagamento com cartão");
                break;

            case 2:
                System.out.println("**implementar algo que simule pagamento com pix");
                break;

            default:
                System.out.println("Digite uma opcao válida.");
                break;
        }
        return true;
    }

    public void mensagemDesafio(Desafios desafio) {
        while (true) {
            System.out.println("Você recebeu um desafio!");

            System.out.println("Desafiante: " + desafio.getDesafiante());
            System.out.println("Quadra: " + desafio.getQuadraDesafio());
            System.out.println("Horário: " + desafio.getHorarioDesafio());
            System.out.println("Deseja aceitar o desafio?");
            System.out.println("""
                    [1] Sim
                    [2] Não
                    """);
            optionInt = input.nextInt();
            if (optionInt == 1) {
                desafio.setStatusDesafio(true);
                ctrl.reservarQuadra(desafio.getQuadraDesafio(), desafio.getDesafiante(),
                        desafio.getHorarioDesafio());
                System.out.println("Desafio aceito com sucesso!");
                return;
            } else if (optionInt == 2) {
                desafio.setStatusDesafio(false);
                System.out.println("Desafio recusado!");
                return;
            } else {
                System.out.println("Digite uma opção válida.");
            }
        }
    }

    public void listarClientes() {
        System.out.println("Clientes cadastrados:\n");
        for (Clientes x : ctrl.getClientes()) {
            System.out.println("Nome: " + x.getNome() + " ID: " + x.getId());
        }
    }
}
