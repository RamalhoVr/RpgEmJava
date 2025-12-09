package rpg.ui;

import rpg.JogoController;
import rpg.JogoController.*;
import rpg.item.Item;
import rpg.personagem.Inimigo;
import rpg.personagem.Personagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.List;

/**
 * Interface gráfica principal do jogo usando Swing.
 * Substitui o modo texto por uma janela interativa.
 */
public class JogoUI extends JFrame {
    private JogoController controller;
    private Personagem jogador;
    private Personagem amigo;
    
    // Componentes da interface
    private JTextArea areaNarrativa;
    private JScrollPane scrollNarrativa;
    private JLabel labelNomeJogador;
    private JLabel labelVida;
    private JLabel labelNivel;
    private JLabel labelPistas;
    private JLabel labelXP;
    private JProgressBar barraVida;
    private JProgressBar barraXP;
    private MapaVisual mapaVisual;
    private JDialog dialogoMapa;
    private boolean batalhaFinalEmAndamento = false;
    
    // Botões de ação
    private JButton btnExplorar;
    private JButton btnStatus;
    private JButton btnInventario;
    private JButton btnMapa;
    private JButton btnViajar;
    private JButton btnSair;
    
    public JogoUI(Personagem jogador, Personagem amigo) {
        this.jogador = jogador;
        this.amigo = amigo;
        this.controller = new JogoController(jogador, amigo);
        
        configurarJanela();
        criarComponentes();
        iniciarJogo();
    }
    
    /**
     * Configura as propriedades básicas da janela principal
     */
    private void configurarJanela() {
        setTitle("RPG Pokémon - Resgate do Amigo");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new BorderLayout(10, 10));
        
        // Define ícone da janela (opcional)
        try {
            // Você pode adicionar um ícone aqui depois
            // setIconImage(new ImageIcon("icon.png").getImage());
        } catch (Exception e) {
            // Ignora se não houver ícone
        }
    }
    
    /**
     * Cria todos os componentes da interface
     */
    private void criarComponentes() {
        // Painel superior - Status do jogador
        JPanel painelStatus = criarPainelStatus();
        add(painelStatus, BorderLayout.NORTH);
        
        // Painel central - Área de narrativa
        JPanel painelNarrativa = criarPainelNarrativa();
        add(painelNarrativa, BorderLayout.CENTER);
        
        // Painel inferior - Botões de ação
        JPanel painelAcoes = criarPainelAcoes();
        add(painelAcoes, BorderLayout.SOUTH);
    }
    
    /**
     * Cria o painel superior com informações do jogador
     */
    private JPanel criarPainelStatus() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 1, 5, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Status do Jogador"));
        painel.setBackground(new Color(240, 240, 240));
        
        // Linha 1: Nome e Nível
        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha1.setBackground(new Color(240, 240, 240));
        labelNomeJogador = new JLabel("Jogador: " + jogador.getNome());
        labelNomeJogador.setFont(new Font("Arial", Font.BOLD, 16));
        labelNivel = new JLabel("Nível: " + jogador.getNivel());
        labelNivel.setFont(new Font("Arial", Font.PLAIN, 14));
        labelPistas = new JLabel("Pistas: 0/3");
        labelPistas.setFont(new Font("Arial", Font.PLAIN, 14));
        
        linha1.add(labelNomeJogador);
        linha1.add(Box.createHorizontalStrut(20));
        linha1.add(labelNivel);
        linha1.add(Box.createHorizontalStrut(20));
        linha1.add(labelPistas);
        
        // Linha 2: Barra de vida
        JPanel linha2 = new JPanel(new BorderLayout(5, 0));
        linha2.setBackground(new Color(240, 240, 240));
        labelVida = new JLabel("Vida: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
        labelVida.setFont(new Font("Arial", Font.PLAIN, 14));
        
        barraVida = new JProgressBar(0, jogador.getMaxPontosVida());
        barraVida.setValue(jogador.getPontosVida());
        barraVida.setStringPainted(true);
        barraVida.setForeground(new Color(76, 175, 80)); // Verde
        
        linha2.add(labelVida, BorderLayout.WEST);
        linha2.add(barraVida, BorderLayout.CENTER);
        
        // Linha 3: Barra de XP
        JPanel linha3 = new JPanel(new BorderLayout(5, 0));
        linha3.setBackground(new Color(240, 240, 240));
        labelXP = new JLabel("EXP: " + jogador.getExperiencia() + "/" + jogador.getExpParaProximoNivel());
        labelXP.setFont(new Font("Arial", Font.PLAIN, 14));
        
        barraXP = new JProgressBar(0, jogador.getExpParaProximoNivel());
        barraXP.setValue(jogador.getExperiencia());
        barraXP.setStringPainted(true);
        barraXP.setForeground(new Color(33, 150, 243)); // Azul
        
        linha3.add(labelXP, BorderLayout.WEST);
        linha3.add(barraXP, BorderLayout.CENTER);
        
        painel.add(linha1);
        painel.add(linha2);
        painel.add(linha3);
        
        return painel;
    }
    
    /**
     * Cria o painel central com a área de texto da narrativa
     */
    private JPanel criarPainelNarrativa() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("História"));
        
        areaNarrativa = new JTextArea();
        areaNarrativa.setEditable(false);
        areaNarrativa.setLineWrap(true);
        areaNarrativa.setWrapStyleWord(true);
        areaNarrativa.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaNarrativa.setMargin(new Insets(10, 10, 10, 10));
        
        scrollNarrativa = new JScrollPane(areaNarrativa);
        scrollNarrativa.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        painel.add(scrollNarrativa, BorderLayout.CENTER);
        
        return painel;
    }
    
    /**
     * Cria o painel inferior com os botões de ação
     */
    private JPanel criarPainelAcoes() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(2, 3, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Criar botões
        btnExplorar = criarBotao("🗺️ Explorar", e -> acaoExplorar());
        btnStatus = criarBotao("📊 Ver Status", e -> acaoVerStatus());
        btnInventario = criarBotao("🎒 Inventário", e -> acaoInventario());
        btnMapa = criarBotao("🗺️ Mapa", e -> acaoMapa());
        btnViajar = criarBotao("🚶 Viajar", e -> acaoViajar());
        btnSair = criarBotao("🚪 Sair", e -> acaoSair());
        
        // Adicionar botões ao painel
        painel.add(btnExplorar);
        painel.add(btnStatus);
        painel.add(btnInventario);
        painel.add(btnMapa);
        painel.add(btnViajar);
        painel.add(btnSair);
        
        return painel;
    }
    
    /**
     * Cria um botão estilizado com ação
     */
    private JButton criarBotao(String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setFocusPainted(false);
        botao.addActionListener(acao);
        return botao;
    }
    
    /**
     * Adiciona texto à área de narrativa
     */
    private void adicionarTexto(String texto) {
        areaNarrativa.append(texto + "\n");
        // Auto-scroll para o final
        areaNarrativa.setCaretPosition(areaNarrativa.getDocument().getLength());
    }
    
    /**
     * Limpa a área de narrativa
     */
    private void limparTexto() {
        areaNarrativa.setText("");
    }
    
    /**
     * Atualiza as informações de status na interface
     */
    private void atualizarStatus() {
        labelNomeJogador.setText("Jogador: " + jogador.getNome());
        labelNivel.setText("Nível: " + jogador.getNivel());
        labelVida.setText("Vida: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
        labelPistas.setText("Pistas: " + controller.getPistasEncontradas() + "/" + controller.getPistasNecessarias());
        labelXP.setText("EXP: " + jogador.getExperiencia() + "/" + jogador.getExpParaProximoNivel());
        
        barraVida.setMaximum(jogador.getMaxPontosVida());
        barraVida.setValue(jogador.getPontosVida());
        
        barraXP.setMaximum(jogador.getExpParaProximoNivel());
        barraXP.setValue(jogador.getExperiencia());
        
        // Mudar cor da barra conforme a vida
        int vidaPercentual = (jogador.getPontosVida() * 100) / jogador.getMaxPontosVida();
        if (vidaPercentual > 60) {
            barraVida.setForeground(new Color(76, 175, 80)); // Verde
        } else if (vidaPercentual > 30) {
            barraVida.setForeground(new Color(255, 193, 7)); // Amarelo
        } else {
            barraVida.setForeground(new Color(244, 67, 54)); // Vermelho
        }
        
        // Verificar se pode iniciar batalha final
        if (controller.podeIniciarBatalhaFinal() && !controller.isEmBatalha()) {
            adicionarTexto("\n⚠️ AVISO: Você tem pistas suficientes para enfrentar Giovanni!");
            adicionarTexto("Use o botão 'Explorar' para iniciar a batalha final!\n");
        }
        
        // Verificar se o jogador morreu
        if (!jogador.estaVivo()) {
            desabilitarBotoes();
            adicionarTexto("\n💀 GAME OVER!");
            adicionarTexto("Você foi derrotado...");
            adicionarTexto("Seu amigo " + amigo.getNome() + " continuará prisioneiro.");
        }
    }
    
    private void desabilitarBotoes() {
        btnExplorar.setEnabled(false);
        btnViajar.setEnabled(false);
        btnInventario.setEnabled(false);
    }
    
    private void habilitarBotoes() {
        btnExplorar.setEnabled(true);
        btnViajar.setEnabled(true);
        btnInventario.setEnabled(true);
    }
    
    /**
     * Inicia o jogo mostrando a mensagem de boas-vindas
     */
    private void iniciarJogo() {
        adicionarTexto("==============================================");
        adicionarTexto("        BEM-VINDO AO RPG POKÉMON!");
        adicionarTexto("==============================================");
        adicionarTexto("");
        adicionarTexto("Você é: " + jogador.getNome() + " (" + jogador.getTipo() + ")");
        adicionarTexto("Ataque: " + jogador.getAtaque() + " | Defesa: " + jogador.getDefesa());
        adicionarTexto("");
        adicionarTexto("Seu melhor amigo " + amigo.getNome() + " (" + amigo.getTipo() + ")");
        adicionarTexto("foi capturado pela Equipe Rocket!");
        adicionarTexto("");
        adicionarTexto("Você precisa encontrar pistas e derrotar inimigos para resgatá-lo!");
        adicionarTexto("");
        adicionarTexto("Use os botões abaixo para jogar. Boa sorte!");
        adicionarTexto("==============================================");
    }
    
    // ========== AÇÕES DOS BOTÕES ==========
    
    private void acaoExplorar() {
        if (!jogador.estaVivo()) {
            adicionarTexto("\n❌ Você não pode explorar - está morto!");
            return;
        }
        
        // Verificar se deve iniciar batalha final
        if (controller.podeIniciarBatalhaFinal()) {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "Você tem pistas suficientes para enfrentar Giovanni!\nDeseja iniciar a batalha final?",
                "Batalha Final",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (resposta == JOptionPane.YES_OPTION) {
                iniciarBatalhaFinal();
                return;
            }
        }
        
        adicionarTexto("\n" + "=".repeat(50));
        adicionarTexto("Explorando...");
        adicionarTexto("=".repeat(50));
        
        ResultadoExploracao resultado = controller.explorar();
        adicionarTexto("\n" + resultado.mensagem);
        
        // Mostrar popups baseado no tipo de evento
        switch (resultado.tipo) {
            case PISTA:
                NotificacaoPopup.mostrarInfo(
                    this,
                    "🔍 Pista Encontrada!",
                    "Você descobriu informações valiosas!\n" +
                    "Pistas: " + controller.getPistasEncontradas() + "/" + controller.getPistasNecessarias()
                );
                break;
                
            case ITEM:
                if (resultado.item != null) {
                    NotificacaoPopup.mostrarItemGanho(
                        this,
                        resultado.item.getNome(),
                        resultado.item.getQuantidade()
                    );
                }
                break;
                
            case ARMADILHA:
                NotificacaoPopup.mostrarAlerta(
                    this,
                    "💥 Armadilha!",
                    "Você caiu em uma armadilha e perdeu vida!"
                );
                break;
                
            case ROCKET:
            case POKEMON_SELVAGEM:
                // Iniciou batalha
                iniciarBatalha(resultado.inimigo);
                break;
        }
        
        atualizarStatus();
    }
    
    private void iniciarBatalha(Inimigo inimigo) {
        adicionarTexto("\n⚔️  BATALHA INICIADA!");
        adicionarTexto("Você: " + jogador.toString());
        adicionarTexto("Inimigo: " + inimigo.toString());
        adicionarTexto("");
        
        // Desabilitar alguns botões durante batalha
        btnViajar.setEnabled(false);
        btnMapa.setEnabled(false);
        
        // Mostrar diálogo de batalha
        mostrarDialogoBatalha(inimigo);
    }
    
    private void mostrarDialogoBatalha(Inimigo inimigo) {
        while (controller.isEmBatalha() && jogador.estaVivo() && inimigo.estaVivo()) {
            // Opções de batalha dependem se é Pokemon ou não
            String[] opcoes;
            if (jogador.getOrigem().equals("pokemon") && !jogador.getPoderes().isEmpty()) {
                opcoes = new String[]{"⚔️ Atacar", "✨ Poder", "📦 Item", "🏃 Fugir"};
            } else {
                opcoes = new String[]{"⚔️ Atacar", "📦 Item", "🏃 Fugir"};
            }
            
            int escolha = JOptionPane.showOptionDialog(
                this,
                "O que deseja fazer?\n\n" +
                "Sua vida: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida() + "\n" +
                inimigo.getNome() + ": " + inimigo.getPontosVida() + "/" + inimigo.getMaxPontosVida(),
                "Batalha em Andamento",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );
            
            if (escolha == -1) escolha = opcoes.length - 1; // X = Fugir
            
            if (escolha == 0) {
                // Atacar
                ResultadoTurno turno = controller.atacar();
                adicionarTexto(turno.mensagem);
                atualizarStatus();
                
                if (turno.vitoria) {
                    ResultadoBatalha resultadoBatalha = controller.finalizarBatalhaVitoria();
                    adicionarTexto("\n" + resultadoBatalha.mensagem);
                    
                    // Popup de Level Up (antes dos itens)
                    if (resultadoBatalha.subiuNivel) {
                        NotificacaoPopup.mostrarSucesso(
                            this,
                            "⭐ LEVEL UP!",
                            "Você subiu para o nível " + jogador.getNivel() + "!\n" +
                            "HP, Ataque e Defesa aumentaram!"
                        );
                    }
                    
                    // Mostrar itens ganhos com popups
                    if (!resultadoBatalha.itensGanhos.isEmpty()) {
                        for (Item item : resultadoBatalha.itensGanhos) {
                            NotificacaoPopup.mostrarItemGanho(this, item.getNome(), item.getQuantidade());
                        }
                    }
                    
                    // Popup de vitória
                    NotificacaoPopup.mostrarSucesso(
                        this,
                        "🎉 Vitória!",
                        "Você derrotou " + inimigo.getNome() + "!\n" +
                        "EXP ganho: " + resultadoBatalha.expGanha
                    );
                    
                    atualizarStatus();
                    break;
                } else if (turno.derrota) {
                    adicionarTexto("\n💀 Você foi derrotado!");
                    atualizarStatus();
                    break;
                }
            } else if (jogador.getOrigem().equals("pokemon") && !jogador.getPoderes().isEmpty() && escolha == 1) {
                // Poder (só Pokemon)
                List<rpg.poderes.Poder> poderes = jogador.getPoderes();
                String[] nomesPoderes = poderes.stream()
                        .map(p -> p.getNome() + " (Nv" + p.getNivelMinimo() + ")")
                        .toArray(String[]::new);
                
                String poderEscolhido = (String) JOptionPane.showInputDialog(
                    this,
                    "Escolha um poder:",
                    "Usar Poder",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    nomesPoderes,
                    nomesPoderes[0]
                );
                
                if (poderEscolhido != null) {
                    int idx = java.util.Arrays.asList(nomesPoderes).indexOf(poderEscolhido);
                    rpg.poderes.Poder poder = poderes.get(idx);
                    
                    ResultadoTurno turno = controller.usarPoder(poder);
                    adicionarTexto(turno.mensagem);
                    atualizarStatus();
                    
                    if (turno.vitoria) {
                        ResultadoBatalha resultadoBatalha = controller.finalizarBatalhaVitoria();
                        adicionarTexto("\n" + resultadoBatalha.mensagem);
                        
                        if (resultadoBatalha.subiuNivel) {
                            NotificacaoPopup.mostrarSucesso(
                                this,
                                "⭐ LEVEL UP!",
                                "Você subiu para o nível " + jogador.getNivel() + "!\n" +
                                "HP, Ataque e Defesa aumentaram!"
                            );
                        }
                        
                        if (!resultadoBatalha.itensGanhos.isEmpty()) {
                            for (Item item : resultadoBatalha.itensGanhos) {
                                NotificacaoPopup.mostrarItemGanho(this, item.getNome(), item.getQuantidade());
                            }
                        }
                        
                        NotificacaoPopup.mostrarSucesso(
                            this,
                            "🎉 Vitória!",
                            "Você derrotou " + inimigo.getNome() + "!\n" +
                            "EXP ganho: " + resultadoBatalha.expGanha
                        );
                        
                        atualizarStatus();
                        break;
                    } else if (turno.derrota) {
                        adicionarTexto("\n💀 Você foi derrotado!");
                        atualizarStatus();
                        break;
                    }
                }
                continue; // Volta ao menu se cancelou
                
            } else if ((jogador.getOrigem().equals("pokemon") && !jogador.getPoderes().isEmpty() && escolha == 2) ||
                       ((!jogador.getOrigem().equals("pokemon") || jogador.getPoderes().isEmpty()) && escolha == 1)) {
                // Item
                List<Item> itens = jogador.getInventario().listarItensOrdenados();
                if (itens.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Você não tem itens no inventário!",
                        "Sem Itens",
                        JOptionPane.WARNING_MESSAGE
                    );
                    continue;
                }
                
                String[] nomesItens = itens.stream()
                        .map(i -> i.getNome() + " x" + i.getQuantidade())
                        .toArray(String[]::new);
                
                String itemEscolhido = (String) JOptionPane.showInputDialog(
                    this,
                    "Escolha um item:",
                    "Usar Item",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    nomesItens,
                    nomesItens[0]
                );
                
                if (itemEscolhido != null) {
                    int idx = java.util.Arrays.asList(nomesItens).indexOf(itemEscolhido);
                    Item item = itens.get(idx);
                    
                    String resultado = controller.usarItem(item, true);
                    adicionarTexto("\n" + resultado);
                    atualizarStatus();
                    
                    // Turno do inimigo após usar item
                    if (controller.isEmBatalha() && inimigo.estaVivo()) {
                        ResultadoTurno turnoInimigo = new ResultadoTurno("");
                        // Simular contra-ataque do inimigo
                        java.util.Random rand = new java.util.Random();
                        int dadoInimigo = rand.nextInt(6) + 1;
                        int ataqueInimigo = inimigo.atacar() + dadoInimigo;
                        int defesaJogador = jogador.getDefesa();
                        
                        StringBuilder msg = new StringBuilder();
                        msg.append("\n--- Turno do inimigo ---\n");
                        msg.append("🎲 ").append(inimigo.getNome()).append(" rolou ").append(dadoInimigo).append("\n");
                        
                        if (ataqueInimigo > defesaJogador) {
                            int dano = ataqueInimigo - defesaJogador;
                            jogador.receberDano(dano);
                            msg.append("💔 Você recebeu ").append(dano).append(" de dano!\n");
                        } else {
                            msg.append("🛡️ Você defendeu!\n");
                        }
                        
                        adicionarTexto(msg.toString());
                        atualizarStatus();
                        
                        if (!jogador.estaVivo()) {
                            adicionarTexto("\n💀 Você foi derrotado!");
                            break;
                        }
                    }
                }
                continue;
                
            } else {
                // Fugir
                ResultadoBatalha resultado = controller.tentarFugir();
                adicionarTexto("\n" + resultado.mensagem);
                if (resultado.fugiu) {
                    break;
                }
            }
        }
        
        // Reabilitar botões
        btnViajar.setEnabled(true);
        btnMapa.setEnabled(true);
        atualizarStatus();
        
        // Reabrir mapa se estava navegando (exceto na batalha final)
        if (!batalhaFinalEmAndamento && dialogoMapa != null && !dialogoMapa.isVisible() && jogador.estaVivo()) {
            SwingUtilities.invokeLater(() -> {
                mapaVisual.atualizarEstadoNodes();
                dialogoMapa.setVisible(true);
            });
        }
    }
    
    private void iniciarBatalhaFinal() {
        batalhaFinalEmAndamento = true;
        
        adicionarTexto("\n" + "=".repeat(50));
        adicionarTexto("🏆 BATALHA FINAL - LÍDER DA EQUIPE ROCKET!");
        adicionarTexto("=".repeat(50));
        adicionarTexto("\nVocê chegou ao esconderijo da Equipe Rocket!");
        adicionarTexto("Giovanni, o líder, está à sua frente com seu amigo " + amigo.getNome() + " preso!");
        adicionarTexto("\nGiovanni: 'Impressionante que chegou até aqui... mas sua jornada termina AGORA!'");
        adicionarTexto("");
        
        Inimigo giovanni = controller.iniciarBatalhaFinal();
        iniciarBatalha(giovanni);
        
        // Verificar se venceu
        if (!giovanni.estaVivo() && jogador.estaVivo()) {
            String mensagemVitoria = controller.finalizarBatalhaFinalVitoria();
            adicionarTexto("\n" + mensagemVitoria);
            desabilitarBotoes();
            
            JOptionPane.showMessageDialog(
                this,
                "Parabéns! Você completou o jogo!\n" +
                "Seu amigo " + amigo.getNome() + " foi resgatado!",
                "🏆 VITÓRIA!",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        batalhaFinalEmAndamento = false;
    }
    
    private void acaoVerStatus() {
        limparTexto();
        adicionarTexto("==============================================");
        adicionarTexto("           STATUS DO PERSONAGEM");
        adicionarTexto("==============================================");
        adicionarTexto("Nome: " + jogador.getNome());
        adicionarTexto("Tipo: " + jogador.getTipo());
        adicionarTexto("Nível: " + jogador.getNivel());
        adicionarTexto("Vida: " + jogador.getPontosVida() + "/" + jogador.getMaxPontosVida());
        adicionarTexto("Ataque: " + jogador.getAtaque());
        adicionarTexto("Defesa: " + jogador.getDefesa());
        adicionarTexto("");
        adicionarTexto("Inventário:");
        if (jogador.getInventario().listarItensOrdenados().isEmpty()) {
            adicionarTexto("  (vazio)");
        } else {
            jogador.getInventario().listarItensOrdenados().forEach(item -> 
                adicionarTexto("  - " + item.getNome() + " x" + item.getQuantidade())
            );
        }
        adicionarTexto("");
        adicionarTexto("Poderes:");
        if (jogador.getPoderes().isEmpty()) {
            adicionarTexto("  (nenhum poder aprendido)");
        } else {
            jogador.getPoderes().forEach(poder -> adicionarTexto("  - " + poder.getNome()));
        }
        adicionarTexto("==============================================");
    }
    
    private void acaoInventario() {
        List<Item> itens = jogador.getInventario().listarItensOrdenados();
        
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Seu inventário está vazio!",
                "Inventário",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        // Criar lista de nomes de itens
        String[] nomesItens = new String[itens.size()];
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            nomesItens[i] = item.getNome() + " x" + item.getQuantidade() + " - " + item.getDescricao();
        }
        
        String escolha = (String) JOptionPane.showInputDialog(
            this,
            "Escolha um item para usar:",
            "Inventário",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nomesItens,
            nomesItens[0]
        );
        
        if (escolha != null) {
            // Encontrar o item selecionado
            String nomeItem = escolha.split(" x")[0];
            for (Item item : itens) {
                if (item.getNome().equals(nomeItem)) {
                    String resultado = controller.usarItem(item);
                    adicionarTexto("\n" + resultado);
                    
                    // Mostrar popup de uso do item
                    if (resultado.contains("Recuperou") || resultado.contains("usou")) {
                        NotificacaoPopup.mostrarInfo(
                            this,
                            "💊 Item Usado",
                            resultado
                        );
                    }
                    
                    atualizarStatus();
                    break;
                }
            }
        }
    }
    
    private void acaoMapa() {
        mostrarMapaVisual();
    }
    
    /**
     * Mostra o mapa visual interativo em uma janela de diálogo
     */
    private void mostrarMapaVisual() {
        if (dialogoMapa == null) {
            dialogoMapa = new JDialog(this, "🗺️ Mapa do Mundo", false);
            dialogoMapa.setSize(850, 700);
            dialogoMapa.setLocationRelativeTo(this);
            
            mapaVisual = new MapaVisual(controller.getMapa());
            mapaVisual.setLocalSelectionListener(chaveLocal -> {
                viajarParaLocal(chaveLocal);
            });
            
            JPanel painelMapa = new JPanel(new BorderLayout());
            painelMapa.add(mapaVisual, BorderLayout.CENTER);
            
            // Painel de informações
            JPanel painelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
            painelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel labelInfo = new JLabel("💡 Clique em um local destacado para viajar até ele!");
            labelInfo.setFont(new Font("Arial", Font.PLAIN, 12));
            painelInfo.add(labelInfo);
            
            painelMapa.add(painelInfo, BorderLayout.SOUTH);
            
            dialogoMapa.add(painelMapa);
        }
        
        // Atualizar o mapa antes de mostrar
        mapaVisual.atualizarEstadoNodes();
        dialogoMapa.setVisible(true);
    }
    
    /**
     * Viaja para um local específico
     */
    private void viajarParaLocal(String chaveLocal) {
        if (controller.getMapa().moverPara(chaveLocal)) {
            adicionarTexto("\n🚶 Você viajou para: " + controller.getMapa().getLocalAtual().getNome());
            adicionarTexto("📖 " + controller.getMapa().getLocalAtual().getDescricao());
            
            // Atualizar mapa visual
            mapaVisual.atualizarEstadoNodes();
            
            // Processar evento do local
            processarEventoLocal();
            
            atualizarStatus();
        } else {
            JOptionPane.showMessageDialog(
                dialogoMapa,
                "Não é possível viajar para este local!",
                "Erro ao Viajar",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Processa eventos que ocorrem ao chegar em um local
     */
    private void processarEventoLocal() {
        rpg.mapa.Local localAtual = controller.getMapa().getLocalAtual();
        String tipoEvento = localAtual.getTipoEvento();
        
        // Auto-explorar o local ao chegar
        adicionarTexto("\n--- Explorando o local ---");
        ResultadoExploracao resultado = controller.explorar();
        adicionarTexto(resultado.mensagem);
        
        // Processar resultado baseado no tipo
        switch (resultado.tipo) {
            case ROCKET, POKEMON_SELVAGEM:
                // Pausar navegação e iniciar batalha
                adicionarTexto("\n⚠️ Você precisa enfrentar este desafio antes de continuar!");
                if (dialogoMapa != null) {
                    dialogoMapa.setVisible(false);
                }
                iniciarBatalha(resultado.inimigo);
                break;
                
            case PISTA:
                NotificacaoPopup.mostrarInfo(
                    this,
                    "🔍 Pista Encontrada!",
                    "Você descobriu informações sobre seu amigo!\n" +
                    "Pistas: " + controller.getPistasEncontradas() + "/" + controller.getPistasNecessarias()
                );
                break;
                
            case ITEM:
                if (resultado.item != null) {
                    NotificacaoPopup.mostrarItemGanho(
                        this,
                        resultado.item.getNome(),
                        resultado.item.getQuantidade()
                    );
                }
                break;
                
            case ARMADILHA:
                NotificacaoPopup.mostrarAlerta(
                    this,
                    "💥 Armadilha!",
                    "Você caiu em uma armadilha e perdeu vida!"
                );
                break;
        }
        
        atualizarStatus();
    }
    
    private void acaoViajar() {
        mostrarMapaVisual();
    }
    
    private void acaoSair() {
        int resposta = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente sair do jogo?",
            "Confirmar Saída",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (resposta == JOptionPane.YES_OPTION) {
            adicionarTexto("\n==============================================");
            adicionarTexto("Obrigado por jogar! Até a próxima aventura!");
            adicionarTexto("==============================================");
            System.exit(0);
        }
    }
}
