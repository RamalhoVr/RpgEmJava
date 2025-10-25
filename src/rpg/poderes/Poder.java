package rpg.poderes;

public final class Poder {
    private final String id;
    private final String nome;
    private final String descricao;
    private final int nivelMinimo;

    public Poder(String id, String nome, String descricao, int nivelMinimo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.nivelMinimo = nivelMinimo;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getNivelMinimo() { return nivelMinimo; }

    @Override
    public String toString() {
        return String.format("%s (Nível Mínimo: %d) — %s", nome, nivelMinimo, descricao);
    }
}