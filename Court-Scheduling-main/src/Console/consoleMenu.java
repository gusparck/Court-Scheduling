package Console;

import Controle.*;
import Entidades.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class consoleMenu {
    private final Scanner input;
    private final Controle ctrl;
    private final Disco disc;

    Quadras quadra;

    int optionInt;
    String optionString;

    public consoleMenu() {
        input = new Scanner(System.in);
        ctrl = new Controle();
        disc = new Disco(ctrl);
    }

    //pode ser util usar um metodo desse caso a gente mexa com bd ou disco
    public void inicializador() {
        disc.carregarClientes();
        disc.carregarQuadras();
        boolean ativo = true;
        while (ativo) {
            ativo = menuPrincipal();
        }
        disc.salvarClientes();
        disc.salvarQuadras();
        System.out.println("Encerrando...");
    }

    public boolean menuPrincipal() {
        String nome, senha, email, telefone;
        System.out.println("\nMenu");
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

                usuario = new Clientes(numeroGrande.intValue(), nome, senha, email, telefone,
                        null);

                if (ctrl.adicionarUsuario(usuario) == 0) {
                    System.out.println("Usuário criado com sucesso!");
                    disc.salvarClientes();
                } else if (ctrl.adicionarUsuario(usuario) == 1) {
                    System.out.println("Preencha todos os campos!");
                } else if (ctrl.adicionarUsuario(usuario) == 2) {
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
                    System.out.println("Nome ou senha não encontrados/ incorretos. Tente novamente.");
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
                \nSelecione uma opção:
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
                input.nextLine(); // limpando o buffer
                optionString = input.nextLine();

                LocalDateTime dataHora = ctrl.stringToDate(optionString);
                if(dataHora==null){
                    System.out.println("Digite uma data no padrão!");
                    break;
                }
                LocalDate dia = dataHora.toLocalDate();
                LocalTime horario = dataHora.toLocalTime();

                if (dia.isBefore(LocalDate.now()) ||
                        (dia.isEqual(LocalDate.now()) && horario.isBefore(LocalTime.now())))
                {
                    System.out.println("Não é permitido escolher este horário, " +
                            "a não ser que você seja um viajante do tempo! ;)");
                    break;
                }

                if (quadra.estaDisponivel(dia, horario)) {
                    if (pagamentoLocacao(usuario)) {
                        ctrl.reservarQuadra(quadra, usuario, dataHora);
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
                            x.getQuadra().getNome() + " - " + ctrl.dateToString(x.getHorario()));
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
                        Quadras quadraCancelamento = usuario.getReservas()
                                .get(optionInt - 1).getQuadra();

                        if (LocalDateTime.now().plusDays(1).isAfter(horarioCancelamento)) {
                            for (Desafios x : usuario.getDesafios()){
                                if (x.getQuadraDesafio().equals(quadraCancelamento)
                                && x.getHorarioDesafio().equals(horarioCancelamento)) {
                                    x.setStatusDesafio(false);
                                }
                            }
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
                            disc.salvarClientes();
                            break;

                        case 2:
                            System.out.println("Digite um novo e-mail: ");
                            input.nextLine(); //limpando o buffer
                            email = input.nextLine();
                            usuario.setEmail(email); //verificacao email duplicado

                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfoCliente(usuario);
                            disc.salvarClientes();
                            break;

                        case 3:
                            System.out.println("Digite um novo telefone: ");
                            telefone = input.nextLine(); //verificar telefone duplicado
                            input.nextLine(); //limpando o buffer
                            usuario.setTelefone(telefone);

                            System.out.println("Novos dados do usuario" + usuario.getNome() + ".");
                            exibirInfoCliente(usuario);
                            disc.salvarClientes();
                            break;

                        default:
                            System.out.println("Digite uma opcao válida.");
                            break;

                    }

                }
                break;

            case 4:
                input.nextLine(); //limpando o buffer
               
                if (ctrl.getClientes().size() == 1 ||  ctrl.getQuadras().isEmpty()) {
                    System.out.println("Não existem quadras ou usuários suficientes na plataforma.");
                    break;
                }
                
                System.out.println("Digite o nome do jogador que deseja desafiar: ");
                optionString = input.nextLine();

                int idDesafiado = 0;
                for (Clientes x : ctrl.getClientes()) {
                    if (x.getNome().equals(optionString) && !x.equals(usuario)) {
                        idDesafiado = x.getId();
                        break;
                    }
                }

                if (ctrl.pesquisarUsuarios(idDesafiado) == null) {
                    System.out.println("Jogador não encontrado!");
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
                optionString = input.nextLine();

                LocalDateTime dataHoraD = ctrl.stringToDate(optionString);
                if(dataHoraD==null){
                    System.out.println("Digite uma data no padrão!");
                    break;
                }

                LocalDate diaD = dataHoraD.toLocalDate();
                LocalTime horarioD = dataHoraD.toLocalTime();

                if (qDesafio.estaDisponivel(diaD, horarioD)) {
                    ctrl.enviarDesafio(usuario, cDesafiado, qDesafio, dataHoraD);
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
                boolean haDesafiosPendentes = false;
                for (Desafios x : usuario.getDesafios()) {
                    if (x.getDesafiado().equals(usuario) && x.getStatusDesafio()==null) {
                        haDesafiosPendentes = true;
                        mensagemDesafio(x);
                    }
                }
                if(!haDesafiosPendentes){
                    System.out.println("Não há desafios pendentes.");
                }
                break;

            case 6:
                input.nextLine(); //limpando o buffer
                if (usuario.getDesafios().isEmpty()) {
                    System.out.println("Nenhum desafio ativo.");
                    break;
                }

                ArrayList <Desafios> finalizados = new ArrayList<>();
                for (Desafios x : usuario.getDesafios()) {
                    if (x.getStatusDesafio()==null){
                        System.out.println("Status: desafio não respondido");
                        System.out.println(x.getQuadraDesafio() + " - "
                                + ctrl.dateToString(x.getHorarioDesafio()));
                    }
                    else if (!x.getStatusDesafio()){
                        System.out.println("Status: desafio recusado");
                        System.out.println("Quadra: " + x.getQuadraDesafio() + " - "
                                + "Horário: " + ctrl.dateToString(x.getHorarioDesafio()));
                        finalizados.add(x);
                    }
                    else {
                        System.out.println("Status: desafio aceito");
                        System.out.println("Quadra: " + x.getQuadraDesafio() + " - "
                                + "Horário: " + ctrl.dateToString(x.getHorarioDesafio()));

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
                \nSelecione uma opção:
                1-Adicionar quadra
                2-Editar quadra
                3-Ver quadras
                4-Sair
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
                
                String nomeQuadra = input.nextLine();

                System.out.println("Digite o endereço: ");
               
                String enderecoQuadra = input.nextLine();

                ctrl.addQuadras(new Quadras(id, nomeQuadra, enderecoQuadra));
                disc.salvarQuadras();

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
                        """);
                optionInt = input.nextInt();
                switch (optionInt) {
                    case 1:
                        System.out.println("Digite o novo nome:");
                        input.nextLine(); //limpando o buffer
                        optionString = input.nextLine();
                        quadra.setNome(optionString);

                        System.out.println("Novos dados do quadra " + quadra.getNome());
                        disc.salvarQuadras();
                        break;

                    case 2:
                        System.out.println("Digite o novo endereço:");
                        optionString = input.nextLine();
                        input.nextLine(); //limpando o buffer
                        quadra.setEndereco(optionString);

                        System.out.println("Novos dados do quadra " + quadra.getEndereco());
                        disc.salvarQuadras();
                        break;
                }
                break;

            case 3:
                if (ctrl.getQuadras().isEmpty()) {
                    System.out.println("Adicione uma quadra primeiro!");
                    break;
                }
                exibirLocacoes();
                break;

            case 4:
                return false;

            default:
                System.out.println("Digite uma opcao valida.");
                break;
        }
        return true;
    }

    public void exibirLocacoes(){
        System.out.println("Locações -");
        for (Quadras x : ctrl.getQuadras()) {
            System.out.println("\nID: " + x.getId() + " " + x.getNome());
            if (!x.getReservas().isEmpty()) {
                for (Reservas y : x.getReservas()){
                    System.out.println("Cliente: " + y.getCliente().getNome() +
                            "Horário: " + y.getHorario());
                }
            }
            else{
                System.out.println("Sem locações realizadas.");
            }
        }
    }

    public void exibirInfoCliente(Clientes usuario) {
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Senha: " + usuario.getSenha());
        System.out.println("E-mail: " + usuario.getEmail());
        System.out.println("Telefone: " + usuario.getTelefone());
        System.out.println("Pontuação: " + usuario.getPontuacao());
    }

    public boolean pagamentoLocacao(Clientes usuario) {
        System.out.println("Como deseja pagar a locação?");
        System.out.println("""
                [1] Cartão
                [2] Pix
                """);
        optionInt = input.nextInt();
        switch (optionInt) {
            case 1:
                int escolhaCartao = 0;
                while (true) {
                    System.out.println("""
                    [1] - Selecionar Cartão
                    [2] - Cadastrar Cartao
                    [3] - Sair
                    """);
                    escolhaCartao = input.nextInt();
                    int count = 1;
                    if (escolhaCartao == 1) {
                        if (!usuario.getCartoes().isEmpty()) {
                            System.out.println("Seus cartões(Numeros dos cartoes):");
                            for (Cartao x : usuario.getCartoes()) {
                                System.out.println("[" + count + "]" + x.getNumCartao());
                                count++;
                            }
                            System.out.println("Escolha uma das opcoes de cartao: ");
                            int escolhendoCartao = input.nextInt();
                            count = 1;
                            while (count <= escolhendoCartao) {

                                if (count == escolhendoCartao) {
                                    for (Cartao x : usuario.getCartoes()) {
                                        if (x.getSaldo() > 50 * usuario.getReservas().size()) {
                                            System.out.println("Pagamento feito com sucesso!");
                                            x.sangriaPagamento(50 * usuario.getReservas().size());
                                        }else{
                                            System.out.println("Não há saldo suficiente no cartão!");
                                        }
                                        count++;
                                    }
                                }
                                count++;
                            }

                        } else{
                            System.out.println("Não ha cartoes cadastrados!");
                        }
                    }

                    else if (escolhaCartao == 2) {
                        System.out.println("Digite seu nome(o mesmo que esta no cartão): ");
                        String nomeCartao = input.nextLine();

                        input.nextLine();
                        System.out.println("Digite o numero do cartão: ");
                        String numCartao = input.nextLine();

                        System.out.println("Digite o CVC: ");
                        String cvcCartao = input.nextLine();

                        System.out.println("Digite a data de validade: ");
                        String dataValidade = input.nextLine();

                        Cartao cartaoCliente = new Cartao(nomeCartao, numCartao, cvcCartao, dataValidade, 2000.00);
                        usuario.setCartaoCliente(cartaoCliente);
                        usuario.setCartoes(cartaoCliente);
                        System.out.println("Cartao cadastrado com sucesso!");

                    }

                    else {
                        return false;
                    }
                }

            case 2:
                System.out.println("Leia o QR CODE:");
                String data = "HELLO"; // Texto simples para gerar QR Code
                gerarQRCodeSimples(data);
            break;

            default:
                System.out.println("Digite uma opção válida.");
                break;
        }
        return true;
    }

    public void mensagemDesafio(Desafios desafio) {
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            System.out.println("Você recebeu um desafio!");

            System.out.println("Desafiante: " + desafio.getDesafiante());
            System.out.println("Quadra: " + desafio.getQuadraDesafio());
            System.out.println("Horário: " + ctrl.dateToString(desafio.getHorarioDesafio()));
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

    public  void gerarQRCodeSimples(String data) {
        int size = 10; // Define um tamanho fixo para o QR Code

        // Geração de QR Code Fake (Apenas um padrão visual)
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if ((x + y) % 2 == 0) { // Padrão alternado
                    System.out.print("██");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
