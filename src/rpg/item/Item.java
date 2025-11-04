package rpg.item;

public class Item implements Comparable<Item>, Cloneable {
    private String nome;
    private String descricao;
    private String efeito;
    private int quantidade;

    // Construtor completo
    public Item(String nome, String descricao, String efeito, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.quantidade = quantidade > 0 ? quantidade : 0;
    }

    // Construtor padrão
    public Item() {
        this("Item", "Sem descrição", "nenhum", 0);
    }

    // Construtor de cópia
    public Item(Item outro) {
        this(outro.nome, outro.descricao, outro.efeito, outro.quantidade);
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getEfeito() { return efeito; }
    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade > 0 ? quantidade : 0;
    }

    public void adicionarUnidades(int qtd) {
        if (qtd > 0) this.quantidade += qtd;
    }

    // Retorna true se conseguiu consumir 1 unidade
    public boolean usar() {
        if (quantidade > 0) {
            quantidade--;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item)) return false;
        Item i = (Item) o;
        return this.nome.equalsIgnoreCase(i.nome) && this.efeito.equalsIgnoreCase(i.efeito);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode() + efeito.toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Item o) {
        int cmp = this.nome.compareToIgnoreCase(o.nome);
        if (cmp != 0) return cmp;
        return this.efeito.compareToIgnoreCase(o.efeito);
    }

    @Override
    public Item clone() {
        return new Item(this);
    }

    @Override
    public String toString() {
        return String.format("%s x%d - %s (Efeito: %s)", nome, quantidade, descricao, efeito);
    }
}
