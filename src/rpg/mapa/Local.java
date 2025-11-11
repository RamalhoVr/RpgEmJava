package rpg.mapa;

import java.util.ArrayList;
import java.util.List;

public class Local implements Cloneable {
    private String nome;
    private String descricao;
    private boolean visitado;
    private List<String> conexoes;
    private List<Evento> eventos;

    public Local(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.visitado = false;
        this.conexoes = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public Local() {
        this("Local Desconhecido", "Um lugar misterioso.");
    }

    public Local(Local outro) {
        this.nome = outro.nome;
        this.descricao = outro.descricao;
        this.visitado = outro.visitado;
        this.conexoes = new ArrayList<>(outro.conexoes);
        this.eventos = new ArrayList<>(outro.eventos);
    }

    public void adicionarEvento(Evento evento) {
        this.eventos.add(evento);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public List<String> getConexoes() {
        return new ArrayList<>(conexoes);
    }

    public List<Evento> getEventos() {
        return new ArrayList<>(eventos);
    }
    
    public boolean temEventosPendentes() {
        for (Evento e : eventos) {
            if (!e.jaOcorreu()) {
                return true;
            }
        }
        return false;
    }

    public Evento getProximoEvento() {
        for (Evento e : eventos) {
            if (!e.jaOcorreu()) {
                return e;
            }
        }
        return null;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

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
        String evento = temEventosPendentes() ? " [!]" : "";
        
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
