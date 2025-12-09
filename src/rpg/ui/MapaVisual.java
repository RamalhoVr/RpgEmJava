package rpg.ui;

import rpg.mapa.Local;
import rpg.mapa.Mapa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

/**
 * Componente visual que desenha o mapa do jogo com fog of war.
 * Locais não visitados aparecem ocultos até serem descobertos.
 */
public class MapaVisual extends JPanel {
    private Mapa mapa;
    private Map<String, NodeMapa> nodes;
    private NodeMapa nodeAtual;
    private NodeMapa nodeHover;
    private LocalSelectionListener listener;
    
    // Cores do tema
    private static final Color COR_FUNDO = new Color(20, 20, 30);
    private static final Color COR_VISITADO = new Color(100, 200, 100);
    private static final Color COR_ATUAL = new Color(255, 215, 0);
    private static final Color COR_DISPONIVEL = new Color(150, 150, 255);
    private static final Color COR_BLOQUEADO = new Color(80, 80, 80);
    private static final Color COR_NAO_DESCOBERTO = new Color(40, 40, 50);
    private static final Color COR_CONEXAO = new Color(100, 100, 120);
    private static final Color COR_CONEXAO_DISPONIVEL = new Color(150, 150, 200);
    private static final Color COR_HOVER = new Color(255, 255, 150);
    
    public MapaVisual(Mapa mapa) {
        this.mapa = mapa;
        this.nodes = new HashMap<>();
        
        setPreferredSize(new Dimension(800, 600));
        setBackground(COR_FUNDO);
        
        inicializarNodes();
        configurarEventos();
    }
    
    /**
     * Interface para receber eventos de seleção de local
     */
    public interface LocalSelectionListener {
        void onLocalSelecionado(String nomeLocal);
    }
    
    public void setLocalSelectionListener(LocalSelectionListener listener) {
        this.listener = listener;
    }
    
    /**
     * Classe interna que representa um nó no mapa visual
     */
    private static class NodeMapa {
        String chave;
        String nomeExibicao;
        int x, y;
        int raio = 30;
        boolean visitado;
        boolean atual;
        boolean disponivel;
        List<String> conexoes;
        
        public NodeMapa(String chave, String nome, int x, int y) {
            this.chave = chave;
            this.nomeExibicao = nome;
            this.x = x;
            this.y = y;
            this.visitado = false;
            this.atual = false;
            this.disponivel = false;
            this.conexoes = new ArrayList<>();
        }
        
        public boolean contem(int px, int py) {
            double distancia = Math.sqrt(Math.pow(px - x, 2) + Math.pow(py - y, 2));
            return distancia <= raio;
        }
        
        public Color getCor() {
            if (atual) return COR_ATUAL;
            if (!visitado) return COR_NAO_DESCOBERTO;
            if (disponivel) return COR_DISPONIVEL;
            return COR_VISITADO;
        }
    }
    
    /**
     * Inicializa as posições dos nós no mapa visual
     */
    private void inicializarNodes() {
        // Layout do mapa - posições em um grid visual interessante
        // Estrutura:
        //     Torre    Ruínas   Floresta  Caverna
        //       |        |         |         |
        //   Fortaleza    |      Entrada      |
        //       |        |         |         |
        //     Ponte    Campo     Lago    Pântano
        
        int centerX = 400;
        int centerY = 300;
        int espacamento = 150;
        
        // Linha superior
        nodes.put("torre", new NodeMapa("torre", "Torre", centerX - 3*espacamento/2, centerY - espacamento));
        nodes.put("ruinas", new NodeMapa("ruinas", "Ruínas", centerX - espacamento/2, centerY - espacamento));
        nodes.put("floresta", new NodeMapa("floresta", "Floresta", centerX + espacamento/2, centerY - espacamento));
        nodes.put("caverna", new NodeMapa("caverna", "Caverna", centerX + 3*espacamento/2, centerY - espacamento));
        
        // Linha do meio
        nodes.put("fortaleza", new NodeMapa("fortaleza", "Fortaleza", centerX - 3*espacamento/2, centerY));
        nodes.put("entrada", new NodeMapa("entrada", "Entrada", centerX + espacamento/2, centerY));
        
        // Linha inferior
        nodes.put("ponte", new NodeMapa("ponte", "Ponte", centerX - 3*espacamento/2, centerY + espacamento));
        nodes.put("campo", new NodeMapa("campo", "Campo", centerX - espacamento/2, centerY + espacamento));
        nodes.put("lago", new NodeMapa("lago", "Lago", centerX + espacamento/2, centerY + espacamento));
        nodes.put("pantano", new NodeMapa("pantano", "Pântano", centerX + 3*espacamento/2, centerY + espacamento));
        
        // Definir conexões (baseado no Mapa.java)
        nodes.get("entrada").conexoes.addAll(Arrays.asList("floresta", "lago", "campo"));
        nodes.get("floresta").conexoes.addAll(Arrays.asList("entrada", "ruinas", "caverna"));
        nodes.get("caverna").conexoes.addAll(Arrays.asList("floresta", "pantano"));
        nodes.get("lago").conexoes.addAll(Arrays.asList("entrada", "campo", "pantano"));
        nodes.get("ruinas").conexoes.addAll(Arrays.asList("floresta", "torre", "campo"));
        nodes.get("pantano").conexoes.addAll(Arrays.asList("caverna", "lago"));
        nodes.get("campo").conexoes.addAll(Arrays.asList("entrada", "lago", "ruinas", "ponte"));
        nodes.get("torre").conexoes.addAll(Arrays.asList("ruinas", "fortaleza"));
        nodes.get("ponte").conexoes.addAll(Arrays.asList("campo", "fortaleza"));
        nodes.get("fortaleza").conexoes.addAll(Arrays.asList("torre", "ponte"));
        
        atualizarEstadoNodes();
    }
    
    /**
     * Atualiza o estado dos nós baseado no mapa real
     */
    public void atualizarEstadoNodes() {
        String localAtualNome = mapa.getNomeLocalAtual();
        Local localAtual = mapa.getLocalAtual();
        
        // Resetar estados
        for (NodeMapa node : nodes.values()) {
            node.atual = false;
            node.disponivel = false;
        }
        
        // Marcar nó atual
        if (nodes.containsKey(localAtualNome)) {
            nodeAtual = nodes.get(localAtualNome);
            nodeAtual.atual = true;
            nodeAtual.visitado = true;
        }
        
        // Marcar locais visitados e disponíveis
        for (Map.Entry<String, NodeMapa> entry : nodes.entrySet()) {
            String chave = entry.getKey();
            NodeMapa node = entry.getValue();
            
            Local local = mapa.getLocal(chave);
            if (local != null && local.isVisitado()) {
                node.visitado = true;
            }
        }
        
        // Marcar locais disponíveis (conectados ao local atual e visitados)
        List<Local> conectados = mapa.getLocaisConectados();
        for (Local loc : conectados) {
            String chaveLocal = getNomeChaveLocal(loc.getNome());
            if (chaveLocal != null && nodes.containsKey(chaveLocal)) {
                NodeMapa node = nodes.get(chaveLocal);
                node.disponivel = true;
                node.visitado = true; // Revelar locais conectados
            }
        }
        
        // Revelar locais adjacentes aos visitados (fog of war parcial)
        Set<String> revelar = new HashSet<>();
        for (NodeMapa node : nodes.values()) {
            if (node.visitado) {
                revelar.addAll(node.conexoes);
            }
        }
        for (String chave : revelar) {
            if (nodes.containsKey(chave)) {
                // Locais adjacentes ficam visíveis mas ainda não completamente explorados
                // Não muda o estado visitado, apenas permite que sejam desenhados
            }
        }
        
        repaint();
    }
    
    /**
     * Converte nome completo do local para chave
     */
    private String getNomeChaveLocal(String nomeCompleto) {
        Map<String, String> mapeamento = new HashMap<>();
        mapeamento.put("Entrada da Floresta", "entrada");
        mapeamento.put("Floresta Sombria", "floresta");
        mapeamento.put("Caverna Misteriosa", "caverna");
        mapeamento.put("Lago Cristalino", "lago");
        mapeamento.put("Ruínas Antigas", "ruinas");
        mapeamento.put("Pântano Venenoso", "pantano");
        mapeamento.put("Campo de Flores", "campo");
        mapeamento.put("Torre Abandonada", "torre");
        mapeamento.put("Ponte de Pedra", "ponte");
        mapeamento.put("Fortaleza do Chefe", "fortaleza");
        
        return mapeamento.get(nomeCompleto);
    }
    
    /**
     * Configura eventos de mouse
     */
    private void configurarEventos() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NodeMapa nodeClicado = getNodeNaPosicao(e.getX(), e.getY());
                if (nodeClicado != null && nodeClicado.disponivel && listener != null) {
                    listener.onLocalSelecionado(nodeClicado.chave);
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                NodeMapa novoHover = getNodeNaPosicao(e.getX(), e.getY());
                if (novoHover != nodeHover) {
                    nodeHover = novoHover;
                    setCursor(nodeHover != null && nodeHover.disponivel ? 
                             Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : 
                             Cursor.getDefaultCursor());
                    repaint();
                }
            }
        };
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }
    
    /**
     * Retorna o nó na posição clicada
     */
    private NodeMapa getNodeNaPosicao(int x, int y) {
        for (NodeMapa node : nodes.values()) {
            if (node.visitado && node.contem(x, y)) {
                return node;
            }
        }
        return null;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Anti-aliasing para gráficos mais suaves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Desenhar título
        g2d.setColor(new Color(200, 200, 220));
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        String titulo = "🗺️ MAPA DO MUNDO";
        FontMetrics fm = g2d.getFontMetrics();
        int tituloX = (getWidth() - fm.stringWidth(titulo)) / 2;
        g2d.drawString(titulo, tituloX, 40);
        
        // Desenhar legenda
        desenharLegenda(g2d);
        
        // Desenhar conexões primeiro
        desenharConexoes(g2d);
        
        // Desenhar nós
        desenharNodes(g2d);
    }
    
    /**
     * Desenha as conexões entre locais
     */
    private void desenharConexoes(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        
        Set<String> desenhadas = new HashSet<>();
        
        for (NodeMapa node : nodes.values()) {
            if (!node.visitado) continue;
            
            for (String conexaoChave : node.conexoes) {
                String par = node.chave.compareTo(conexaoChave) < 0 ? 
                            node.chave + "-" + conexaoChave : 
                            conexaoChave + "-" + node.chave;
                
                if (desenhadas.contains(par)) continue;
                desenhadas.add(par);
                
                NodeMapa nodeConexao = nodes.get(conexaoChave);
                if (nodeConexao != null && nodeConexao.visitado) {
                    // Cor da conexão depende da disponibilidade
                    if (node.disponivel || nodeConexao.disponivel) {
                        g2d.setColor(COR_CONEXAO_DISPONIVEL);
                    } else {
                        g2d.setColor(COR_CONEXAO);
                    }
                    
                    g2d.drawLine(node.x, node.y, nodeConexao.x, nodeConexao.y);
                }
            }
        }
    }
    
    /**
     * Desenha os nós do mapa
     */
    private void desenharNodes(Graphics2D g2d) {
        for (NodeMapa node : nodes.values()) {
            // Só desenhar se visitado ou adjacente a visitado
            boolean deveDesenhar = node.visitado;
            if (!deveDesenhar) {
                for (NodeMapa other : nodes.values()) {
                    if (other.visitado && other.conexoes.contains(node.chave)) {
                        deveDesenhar = true;
                        break;
                    }
                }
            }
            
            if (!deveDesenhar) continue;
            
            // Cor do nó
            Color cor = node.getCor();
            if (node == nodeHover && node.disponivel) {
                cor = COR_HOVER;
            }
            
            // Desenhar círculo externo (borda)
            if (node.atual) {
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(4));
                g2d.drawOval(node.x - node.raio - 3, node.y - node.raio - 3, 
                           (node.raio + 3) * 2, (node.raio + 3) * 2);
            }
            
            // Desenhar círculo principal
            g2d.setColor(cor);
            g2d.fillOval(node.x - node.raio, node.y - node.raio, node.raio * 2, node.raio * 2);
            
            // Borda do círculo
            g2d.setColor(cor.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(node.x - node.raio, node.y - node.raio, node.raio * 2, node.raio * 2);
            
            // Desenhar ícone ou inicial
            if (node.visitado) {
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                String inicial = node.nomeExibicao.substring(0, 1).toUpperCase();
                FontMetrics fm = g2d.getFontMetrics();
                int textX = node.x - fm.stringWidth(inicial) / 2;
                int textY = node.y + fm.getAscent() / 2 - 2;
                g2d.drawString(inicial, textX, textY);
            } else {
                // Desenhar "?" para locais não visitados mas revelados
                g2d.setColor(new Color(100, 100, 100));
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = g2d.getFontMetrics();
                String texto = "?";
                int textX = node.x - fm.stringWidth(texto) / 2;
                int textY = node.y + fm.getAscent() / 2 - 2;
                g2d.drawString(texto, textX, textY);
            }
            
            // Desenhar nome do local (se visitado)
            if (node.visitado) {
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                FontMetrics fm = g2d.getFontMetrics();
                int nameX = node.x - fm.stringWidth(node.nomeExibicao) / 2;
                int nameY = node.y + node.raio + 20;
                
                // Sombra do texto
                g2d.setColor(Color.BLACK);
                g2d.drawString(node.nomeExibicao, nameX + 1, nameY + 1);
                
                g2d.setColor(node.atual ? COR_ATUAL : Color.WHITE);
                g2d.drawString(node.nomeExibicao, nameX, nameY);
            }
            
            // Indicador de local disponível
            if (node.disponivel && !node.atual) {
                g2d.setColor(new Color(255, 255, 255, 150));
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                                             0, new float[]{5, 5}, 0));
                g2d.drawOval(node.x - node.raio - 8, node.y - node.raio - 8, 
                           (node.raio + 8) * 2, (node.raio + 8) * 2);
            }
        }
    }
    
    /**
     * Desenha a legenda do mapa
     */
    private void desenharLegenda(Graphics2D g2d) {
        int x = 20;
        int y = getHeight() - 120;
        int tamanho = 15;
        int espacamento = 25;
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Atual
        g2d.setColor(COR_ATUAL);
        g2d.fillOval(x, y, tamanho, tamanho);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Localização Atual", x + tamanho + 10, y + 12);
        
        // Disponível
        y += espacamento;
        g2d.setColor(COR_DISPONIVEL);
        g2d.fillOval(x, y, tamanho, tamanho);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Pode Viajar (clique)", x + tamanho + 10, y + 12);
        
        // Visitado
        y += espacamento;
        g2d.setColor(COR_VISITADO);
        g2d.fillOval(x, y, tamanho, tamanho);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Já Visitado", x + tamanho + 10, y + 12);
        
        // Não descoberto
        y += espacamento;
        g2d.setColor(COR_NAO_DESCOBERTO);
        g2d.fillOval(x, y, tamanho, tamanho);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Não Descoberto", x + tamanho + 10, y + 12);
    }
}
