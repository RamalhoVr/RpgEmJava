public class Item implements Comparable<Item> {

    private String nome;
    private String descricao;
    private String efeito;
    private int quantidade;

    public Item(String nome, String descricao, String efeito, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public String getEfeito() {
        return efeito;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean usar() {
        if (quantidade > 0) {
            quantidade--;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return nome.equalsIgnoreCase(item.nome) && efeito.equalsIgnoreCase(item.efeito);
    }

    @Override
    public int compareTo(Item outro) {
        return this.nome.compareToIgnoreCase(outro.nome);
    }

    @Override
    public String toString() {
        return nome + " (" + descricao + ") - Efeito: " + efeito + " | Quantidade: " + quantidade;
    }
}
