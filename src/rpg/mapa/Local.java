package rpg.mapa;

import rpg.item.Item;
import rpg.personagem.Inimigo;
import java.util.ArrayList;
import java.util.List;

public class Local implements Cloneable {
    private String nome;
    private String descricao;
    private boolean visitado;
    private List<String> conexoes; // Nomes dos locais conectados
    private boolean temPista;
    private boolean temInimigo;
    private boolean temItem;
    private String tipoEvento; // "combate", "item", "pista", "armadilha", "vazio"

    // Construtor completo
    public Local(String nome, String descricao, String tipoEvento) {
        this.nome = nome;
        this.descricao = descricao;
        this.visitado = false;
        this.conexoes = new ArrayList<>();
        this.tipoEvento = tipoEvento;
        this.temPista = tipoEvento.equals("pista");
        this.temInimigo = tipoEvento.equals("combate");
        this.temItem = tipoEvento.equals("item");
    }

    // Construtor padrão
    public Local() {
        this("Local Desconhecido", "Um lugar misterioso.", "vazio");
    }

    // Construtor de cópia
    public Local(Local outro) {
        this.nome = outro.nome;
        this.descricao = outro.descricao;
        this.visitado = outro.visitado;
        this.conexoes = new ArrayList<>(outro.conexoes);
        this.temPista = outro.temPista;
        this.temInimigo = outro.temInimigo;
        this.temItem = outro.temItem;
        this.tipoEvento = outro.tipoEvento;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public boolean isVisitado() { return visitado; }
    public List<String> getConexoes() { return new ArrayList<>(conexoes); }
    public boolean temPista() { return temPista; }
    public boolean temInimigo() { return temInimigo; }
    public boolean temItem() { return temItem; }
    public String getTipoEvento() { return tipoEvento; }

    // Setters
    public void setVisitado(boolean visitado) { this.visitado = visitado; }
    public void setPistaEncontrada() { this.temPista = false; }
    public void setInimigoVencido() { this.temInimigo = false; }
    public void setItemColetado() { this.temItem = false; }

    // Adicionar conexão
    public void adicionarConexao(String nomeLocal) {
        if (!conexoes.contains(nomeLocal)) {
            conexoes.add(nomeLocal);
        }
    }

    @Override
    public Local clone() {
        return new Local(this);
    }

    @Override
    public String toString() {
        String status = visitado ? "[VISITADO]" : "[NOVO]";
        String evento = "";
        
        if (temPista) evento += " 🔍";
        if (temInimigo) evento += " ⚔️";
        if (temItem) evento += " 💎";
        
        return String.format("%s %s%s - %s", status, nome, evento, descricao);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Local)) return false;
        Local l = (Local) o;
        return this.nome.equalsIgnoreCase(l.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }
}