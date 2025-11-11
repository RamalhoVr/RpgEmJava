package rpg.mapa;

public class Evento {
    private String tipo; // "pista", "combate", "item", "armadilha", "dialogo", "descanso"
    private String descricao;
    private String detalhes;
    private boolean ocorreu;

    public Evento(String tipo, String descricao, String detalhes) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.detalhes = detalhes;
        this.ocorreu = false;
    }

    public String getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public String getDetalhes() { return detalhes; }
    public boolean jaOcorreu() { return ocorreu; }
    public void marcarComoOcorrido() { this.ocorreu = true; }

    @Override
    public String toString() {
        String status = ocorreu ? "[COMPLETO]" : "[PENDENTE]";
        return status + " " + descricao;
    }
}
