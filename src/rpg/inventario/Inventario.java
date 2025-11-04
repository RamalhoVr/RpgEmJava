package rpg.inventario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import rpg.item.Item;

public class Inventario implements Cloneable {
    private final List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    // Construtor de cópia
    public Inventario(Inventario outro) {
        this.itens = new ArrayList<>();
        for (Item it : outro.itens) {
            this.itens.add(it.clone());
        }
    }

    // Adiciona: se item já existir (pelo equals), soma quantidades
    public void adicionarItem(Item novo) {
        if (novo == null) return;
        for (Item it : itens) {
            if (it.equals(novo)) {
                it.setQuantidade(it.getQuantidade() + novo.getQuantidade());
                return;
            }
        }
        itens.add(novo.clone());
    }

    // Remove 1 unidade do item. Retorna true se conseguiu remover
    public boolean removerItem(Item alvo) {
        if (alvo == null) return false;
        Iterator<Item> iterator = itens.iterator();
        while (iterator.hasNext()) {
            Item it = iterator.next();
            if (it.equals(alvo)) {
                if (it.getQuantidade() > 1) {
                    it.setQuantidade(it.getQuantidade() - 1);
                } else {
                    iterator.remove();
                }
                return true;
            }
        }
        return false;
    }

    public List<Item> listarItensOrdenados() {
        List<Item> copia = new ArrayList<>();
        for (Item it : itens) {
            copia.add(it.clone());
        }
        Collections.sort(copia);
        return copia;
    }

    public boolean vazio() { 
        return itens.isEmpty(); 
    }

    @Override
    public Inventario clone() {
        return new Inventario(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Inventário:\n");
        for (Item it : listarItensOrdenados()) {
            sb.append(" - ").append(it).append("\n");
        }
        if (itens.isEmpty()) sb.append(" (vazio)\n");
        return sb.toString();
    }
}
