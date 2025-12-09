package rpg.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe para mostrar notificações popup estilizadas no jogo
 */
public class NotificacaoPopup extends JDialog {
    private static final int LARGURA = 350;
    private static final int ALTURA = 150;
    private static final Color COR_FUNDO_ITEM = new Color(70, 130, 180);
    private static final Color COR_FUNDO_SUCESSO = new Color(76, 175, 80);
    private static final Color COR_FUNDO_INFO = new Color(33, 150, 243);
    private static final Color COR_FUNDO_ALERTA = new Color(255, 152, 0);
    
    public enum TipoNotificacao {
        ITEM, SUCESSO, INFO, ALERTA
    }
    
    private NotificacaoPopup(JFrame parent, String titulo, String mensagem, TipoNotificacao tipo) {
        super(parent, titulo, true);
        
        setUndecorated(true);
        setSize(LARGURA, ALTURA);
        setLocationRelativeTo(parent);
        
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Cor de fundo baseada no tipo
        Color corFundo = switch (tipo) {
            case ITEM -> COR_FUNDO_ITEM;
            case SUCESSO -> COR_FUNDO_SUCESSO;
            case ALERTA -> COR_FUNDO_ALERTA;
            default -> COR_FUNDO_INFO;
        };
        painel.setBackground(corFundo);
        
        // Ícone
        JLabel labelIcone = new JLabel(getIconePorTipo(tipo), SwingConstants.CENTER);
        labelIcone.setFont(new Font("Arial", Font.BOLD, 48));
        labelIcone.setForeground(Color.WHITE);
        painel.add(labelIcone, BorderLayout.WEST);
        
        // Conteúdo
        JPanel painelConteudo = new JPanel(new BorderLayout(5, 5));
        painelConteudo.setOpaque(false);
        
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitulo.setForeground(Color.WHITE);
        painelConteudo.add(labelTitulo, BorderLayout.NORTH);
        
        JTextArea areaMensagem = new JTextArea(mensagem);
        areaMensagem.setFont(new Font("Arial", Font.PLAIN, 12));
        areaMensagem.setForeground(Color.WHITE);
        areaMensagem.setBackground(new Color(0, 0, 0, 0));
        areaMensagem.setEditable(false);
        areaMensagem.setLineWrap(true);
        areaMensagem.setWrapStyleWord(true);
        painelConteudo.add(areaMensagem, BorderLayout.CENTER);
        
        painel.add(painelConteudo, BorderLayout.CENTER);
        
        // Botão OK
        JButton btnOk = new JButton("OK");
        btnOk.setFont(new Font("Arial", Font.BOLD, 12));
        btnOk.setFocusPainted(false);
        btnOk.setBackground(Color.WHITE);
        btnOk.setForeground(corFundo);
        btnOk.addActionListener(e -> dispose());
        
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.setOpaque(false);
        painelBotao.add(btnOk);
        painel.add(painelBotao, BorderLayout.SOUTH);
        
        add(painel);
        
        // Fechar com ESC
        getRootPane().registerKeyboardAction(
            e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        
        // Timer para fechar automaticamente após 3 segundos
        Timer timer = new Timer(3000, e -> dispose());
        timer.setRepeats(false);
        timer.start();
    }
    
    private String getIconePorTipo(TipoNotificacao tipo) {
        return switch (tipo) {
            case ITEM -> "🎁";
            case SUCESSO -> "✅";
            case ALERTA -> "⚠️";
            default -> "ℹ️";
        };
    }
    
    /**
     * Mostra uma notificação de item ganho
     */
    public static void mostrarItemGanho(JFrame parent, String nomeItem, int quantidade) {
        String mensagem = String.format("Você ganhou:\n%s x%d", nomeItem, quantidade);
        NotificacaoPopup notificacao = new NotificacaoPopup(
            parent, 
            "✨ Item Obtido!", 
            mensagem, 
            TipoNotificacao.ITEM
        );
        notificacao.setVisible(true);
    }
    
    /**
     * Mostra uma notificação de sucesso
     */
    public static void mostrarSucesso(JFrame parent, String titulo, String mensagem) {
        NotificacaoPopup notificacao = new NotificacaoPopup(
            parent, 
            titulo, 
            mensagem, 
            TipoNotificacao.SUCESSO
        );
        notificacao.setVisible(true);
    }
    
    /**
     * Mostra uma notificação informativa
     */
    public static void mostrarInfo(JFrame parent, String titulo, String mensagem) {
        NotificacaoPopup notificacao = new NotificacaoPopup(
            parent, 
            titulo, 
            mensagem, 
            TipoNotificacao.INFO
        );
        notificacao.setVisible(true);
    }
    
    /**
     * Mostra uma notificação de alerta
     */
    public static void mostrarAlerta(JFrame parent, String titulo, String mensagem) {
        NotificacaoPopup notificacao = new NotificacaoPopup(
            parent, 
            titulo, 
            mensagem, 
            TipoNotificacao.ALERTA
        );
        notificacao.setVisible(true);
    }
}
