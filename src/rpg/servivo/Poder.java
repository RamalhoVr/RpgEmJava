package rpg.servivo;

public final class Poder {
    private final String id;
    private final String nome;
    private final String descricao;

    public Poder(String id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return nome + " — " + descricao;
    }


}