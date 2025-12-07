package rpg.ui;

import rpg.personagem.*;
import rpg.personagem.pokemon.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Tela de criação de personagem com interface gráfica.
 * Permite escolher entre Humano (Guerreiro/Arqueiro) ou Pokémon.
 */
public class TelaCriacaoPersonagem extends JDialog {
    private Personagem jogadorCriado;
    private Personagem amigoCriado;
    private boolean criouPersonagem = false;
    
    private JTextField txtNomeJogador;
    private JTextField txtNomeAmigo;
    private JComboBox<String> comboTipoJogador;
    private JComboBox<String> comboTipoAmigo;
    private JComboBox<String> comboClasseJogador;
    private JComboBox<String> comboClasseAmigo;
    private JTextArea areaDescricao;
    
    public TelaCriacaoPersonagem(Frame parent) {
        super(parent, "Criação de Personagem", true);
        
        setSize(700, 600);
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
        areaDescricao = new JTextArea(5, 40);
        areaDescricao.setEditable(false);
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDescricao.setText("Escolha o tipo e classe do seu personagem e do seu amigo.");
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
        
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtNome = new JTextField(20);
        painel.add(txtNome, gbc);
        
        if (isJogador) {
            txtNomeJogador = txtNome;
        } else {
            txtNomeAmigo = txtNome;
        }
        
        // Tipo (Humano ou Pokémon)
        gbc.gridx = 0; gbc.gridy = 1;
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
        
        // Classe/Espécie
        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Classe:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> comboClasse = new JComboBox<>();
        atualizarComboClasse(comboClasse, "Humano");
        painel.add(comboClasse, gbc);
        
        if (isJogador) {
            comboClasseJogador = comboClasse;
        } else {
            comboClasseAmigo = comboClasse;
        }
        
        // Listener para mudar as opções quando muda o tipo
        comboTipo.addActionListener(e -> {
            String tipoSelecionado = (String) comboTipo.getSelectedItem();
            atualizarComboClasse(comboClasse, tipoSelecionado);
        });
        
        return painel;
    }
    
    private void atualizarComboClasse(JComboBox<String> combo, String tipo) {
        combo.removeAllItems();
        
        if ("Humano".equals(tipo)) {
            combo.addItem("Guerreiro");
            combo.addItem("Arqueiro");
        } else {
            combo.addItem("Água");
            combo.addItem("Fogo");
            combo.addItem("Planta");
            combo.addItem("Pedra");
            combo.addItem("Elétrico");
            combo.addItem("Psíquico");
        }
    }
    
    private void criarPersonagens() {
        // Validar nomes
        String nomeJogador = txtNomeJogador.getText().trim();
        String nomeAmigo = txtNomeAmigo.getText().trim();
        
        if (nomeJogador.isEmpty() || nomeAmigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, preencha os nomes dos personagens!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Criar jogador
        String tipoJogador = (String) comboTipoJogador.getSelectedItem();
        String classeJogador = (String) comboClasseJogador.getSelectedItem();
        jogadorCriado = criarPersonagem(nomeJogador, tipoJogador, classeJogador);
        
        // Criar amigo
        String tipoAmigo = (String) comboTipoAmigo.getSelectedItem();
        String classeAmigo = (String) comboClasseAmigo.getSelectedItem();
        amigoCriado = criarPersonagem(nomeAmigo, tipoAmigo, classeAmigo);
        
        criouPersonagem = true;
        dispose();
    }
    
    private Personagem criarPersonagem(String nome, String tipo, String classe) {
        if ("Humano".equals(tipo)) {
            if ("Guerreiro".equals(classe)) {
                return new Guerreiro(nome.isEmpty() ? "Guerreiro" : nome, 1);
            } else {
                return new Arqueiro(nome.isEmpty() ? "Arqueiro" : nome, 1);
            }
        } else {
            // Pokémon - criar diretamente baseado no tipo escolhido
            String nomePokemon = nome.isEmpty() ? gerarNomePokemonPorTipo(classe) : nome;
            int nivelInicial = 5;
            
            switch (classe) {
                case "Água":
                    return new PokemonAgua(nomePokemon, nivelInicial);
                case "Fogo":
                    return new PokemonFogo(nomePokemon, nivelInicial);
                case "Planta":
                    return new PokemonPlanta(nomePokemon, nivelInicial);
                case "Pedra":
                    return new PokemonPedra(nomePokemon, nivelInicial);
                case "Elétrico":
                    return new PokemonEletrico(nomePokemon, nivelInicial);
                case "Psíquico":
                    return new PokemonPsiquico(nomePokemon, nivelInicial);
                default:
                    return new PokemonAgua(nomePokemon, nivelInicial);
            }
        }
    }
    
    private String gerarNomePokemonPorTipo(String tipo) {
        String[][] nomes = {
            {"Squirtle", "Vaporeon", "Gyarados"},      // Água
            {"Charmander", "Flareon", "Arcanine"},     // Fogo
            {"Bulbasaur", "Leafeon", "Venusaur"},      // Planta
            {"Onix", "Geodude", "Rhydon"},             // Pedra
            {"Pikachu", "Jolteon", "Electabuzz"},      // Elétrico
            {"Abra", "Espeon", "Alakazam"}             // Psíquico
        };
        
        int indice = 0;
        switch (tipo) {
            case "Água": indice = 0; break;
            case "Fogo": indice = 1; break;
            case "Planta": indice = 2; break;
            case "Pedra": indice = 3; break;
            case "Elétrico": indice = 4; break;
            case "Psíquico": indice = 5; break;
        }
        
        Random random = new Random();
        return nomes[indice][random.nextInt(nomes[indice].length)];
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
