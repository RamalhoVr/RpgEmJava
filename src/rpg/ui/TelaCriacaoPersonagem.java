package rpg.ui;

import rpg.personagem.*;
import rpg.personagem.pokemon.*;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de criação de personagem com interface gráfica.
 * Permite escolher entre Humano (Guerreiro/Arqueiro) ou Pokémon.
 */
public class TelaCriacaoPersonagem extends JDialog {
    private Personagem jogadorCriado;
    private Personagem amigoCriado;
    private boolean criouPersonagem = false;
    
    private JComboBox<String> comboTipoJogador;
    private JComboBox<String> comboTipoAmigo;
    private JComboBox<String> comboPersonagemJogador;
    private JComboBox<String> comboPersonagemAmigo;
    private JTextArea areaDescricao;
    
    public TelaCriacaoPersonagem(Frame parent) {
        super(parent, "Criação de Personagem", true);
        
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        criarComponentes();
    }
    
    private void criarComponentes() {
        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel lblTitulo = new JLabel("🎮 Crie Seu Personagem", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Painel de formulário
        JPanel painelForm = new JPanel(new GridLayout(2, 1, 10, 10));
        
        // Seção do Jogador
        JPanel painelJogador = criarPainelPersonagem("👤 SEU PERSONAGEM", true);
        painelForm.add(painelJogador);
        
        // Seção do Amigo
        JPanel painelAmigo = criarPainelPersonagem("💫 SEU AMIGO (que será capturado)", false);
        painelForm.add(painelAmigo);
        
        painelPrincipal.add(painelForm, BorderLayout.CENTER);
        
        // Área de descrição
        JPanel painelDescricao = new JPanel(new BorderLayout());
        painelDescricao.setBorder(BorderFactory.createTitledBorder("Informações"));
        areaDescricao = new JTextArea(4, 40);
        areaDescricao.setEditable(false);
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDescricao.setText("Escolha o tipo e personagem do jogador e do amigo.");
        JScrollPane scrollDesc = new JScrollPane(areaDescricao);
        painelDescricao.add(scrollDesc, BorderLayout.CENTER);
        painelPrincipal.add(painelDescricao, BorderLayout.SOUTH);
        
        add(painelPrincipal, BorderLayout.CENTER);
        
        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnCriar = new JButton("✅ Começar Aventura");
        btnCriar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCriar.addActionListener(e -> criarPersonagens());
        
        JButton btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancelar.addActionListener(e -> {
            criouPersonagem = false;
            dispose();
        });
        
        painelBotoes.add(btnCriar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelPersonagem(String titulo, boolean isJogador) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder(titulo));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Tipo (Humano ou Pokémon)
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        String[] tipos = {"Humano", "Pokémon"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        painel.add(comboTipo, gbc);
        
        if (isJogador) {
            comboTipoJogador = comboTipo;
        } else {
            comboTipoAmigo = comboTipo;
        }
        
        // Personagem (Guerreiro/Arqueiro ou Nome do Pokémon)
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel labelPersonagem = new JLabel("Classe:");
        painel.add(labelPersonagem, gbc);
        
        gbc.gridx = 1;
        JComboBox<String> comboPersonagem = new JComboBox<>();
        atualizarComboPersonagem(comboPersonagem, "Humano");
        painel.add(comboPersonagem, gbc);
        
        if (isJogador) {
            comboPersonagemJogador = comboPersonagem;
        } else {
            comboPersonagemAmigo = comboPersonagem;
        }
        
        // Listener para mudar as opções quando muda o tipo
        comboTipo.addActionListener(e -> {
            String tipoSelecionado = (String) comboTipo.getSelectedItem();
            atualizarComboPersonagem(comboPersonagem, tipoSelecionado);
            // Atualizar label
            if ("Humano".equals(tipoSelecionado)) {
                labelPersonagem.setText("Classe:");
            } else {
                labelPersonagem.setText("Pokémon:");
            }
        });
        
        return painel;
    }
    
    private void atualizarComboPersonagem(JComboBox<String> combo, String tipo) {
        combo.removeAllItems();
        
        if ("Humano".equals(tipo)) {
            combo.addItem("Guerreiro");
            combo.addItem("Arqueiro");
        } else {
            // Pokémons de Água
            combo.addItem("Squirtle");
            combo.addItem("Vaporeon");
            combo.addItem("Gyarados");
            
            // Pokémons de Fogo
            combo.addItem("Charmander");
            combo.addItem("Flareon");
            combo.addItem("Arcanine");
            
            // Pokémons de Planta
            combo.addItem("Bulbasaur");
            combo.addItem("Leafeon");
            combo.addItem("Venusaur");
            
            // Pokémons de Pedra
            combo.addItem("Onix");
            combo.addItem("Geodude");
            combo.addItem("Rhydon");
            
            // Pokémons de Elétrico
            combo.addItem("Pikachu");
            combo.addItem("Jolteon");
            combo.addItem("Electabuzz");
            
            // Pokémons de Psíquico
            combo.addItem("Abra");
            combo.addItem("Espeon");
            combo.addItem("Alakazam");
        }
    }
    
    private void criarPersonagens() {
        // Criar jogador
        String tipoJogador = (String) comboTipoJogador.getSelectedItem();
        String personagemJogador = (String) comboPersonagemJogador.getSelectedItem();
        jogadorCriado = criarPersonagem(tipoJogador, personagemJogador);
        
        // Criar amigo
        String tipoAmigo = (String) comboTipoAmigo.getSelectedItem();
        String personagemAmigo = (String) comboPersonagemAmigo.getSelectedItem();
        amigoCriado = criarPersonagem(tipoAmigo, personagemAmigo);
        
        criouPersonagem = true;
        dispose();
    }
    
    private Personagem criarPersonagem(String tipo, String nomePersonagem) {
        if ("Humano".equals(tipo)) {
            if ("Guerreiro".equals(nomePersonagem)) {
                return new Guerreiro("Guerreiro", 1);
            } else {
                return new Arqueiro("Arqueiro", 1);
            }
        } else {
            // Pokémon - criar baseado no nome escolhido dos presets com níveis corretos
            
            // Pokémons de Água
            if ("Squirtle".equals(nomePersonagem)) {
                return new PokemonAgua("Squirtle", 5);
            } else if ("Vaporeon".equals(nomePersonagem)) {
                return new PokemonAgua("Vaporeon", 15);
            } else if ("Gyarados".equals(nomePersonagem)) {
                return new PokemonAgua("Gyarados", 20);
            }
            // Pokémons de Fogo
            else if ("Charmander".equals(nomePersonagem)) {
                return new PokemonFogo("Charmander", 5);
            } else if ("Flareon".equals(nomePersonagem)) {
                return new PokemonFogo("Flareon", 15);
            } else if ("Arcanine".equals(nomePersonagem)) {
                return new PokemonFogo("Arcanine", 20);
            }
            // Pokémons de Planta
            else if ("Bulbasaur".equals(nomePersonagem)) {
                return new PokemonPlanta("Bulbasaur", 5);
            } else if ("Leafeon".equals(nomePersonagem)) {
                return new PokemonPlanta("Leafeon", 15);
            } else if ("Venusaur".equals(nomePersonagem)) {
                return new PokemonPlanta("Venusaur", 20);
            }
            // Pokémons de Pedra
            else if ("Onix".equals(nomePersonagem)) {
                return new PokemonPedra("Onix", 5);
            } else if ("Geodude".equals(nomePersonagem)) {
                return new PokemonPedra("Geodude", 15);
            } else if ("Rhydon".equals(nomePersonagem)) {
                return new PokemonPedra("Rhydon", 20);
            }
            // Pokémons de Elétrico
            else if ("Pikachu".equals(nomePersonagem)) {
                return new PokemonEletrico("Pikachu", 5);
            } else if ("Jolteon".equals(nomePersonagem)) {
                return new PokemonEletrico("Jolteon", 15);
            } else if ("Electabuzz".equals(nomePersonagem)) {
                return new PokemonEletrico("Electabuzz", 20);
            }
            // Pokémons de Psíquico
            else if ("Abra".equals(nomePersonagem)) {
                return new PokemonPsiquico("Abra", 5);
            } else if ("Espeon".equals(nomePersonagem)) {
                return new PokemonPsiquico("Espeon", 15);
            } else if ("Alakazam".equals(nomePersonagem)) {
                return new PokemonPsiquico("Alakazam", 20);
            }
            
            // Padrão
            return new PokemonAgua("Squirtle", 5);
        }
    }
    
    public boolean criouPersonagem() {
        return criouPersonagem;
    }
    
    public Personagem getJogador() {
        return jogadorCriado;
    }
    
    public Personagem getAmigo() {
        return amigoCriado;
    }
}
