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

    public Inventario(Inventario outro) {
        this.itens = new ArrayList<>();
        for (Item it : outro.itens) {
            this.itens.add(it.clone());
        }
    }

    public void adicionarItem(Item novo) {
        for (Item it : itens) {
            if (it.equals(novo)) {
                it.setQuantidade(it.getQuantidade() + novo.getQuantidade());
                return;
            }
        }
        itens.add(novo);
    }

    public boolean removerItem(Item alvo) {
        Iterator<Item> it = itens.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.equals(alvo)) {
                if (i.getQuantidade() > 1) {
                    i.setQuantidade(i.getQuantidade() - 1);
                } else {
                    it.remove();
                }
                return true;
            }
        }
        return false;
    }

    public List<Item> listarItensOrdenados() {
        List<Item> copia = new ArrayList<>(itens);
        Collections.sort(copia);
        return copia;
    }

    @Override
    public Inventario clone() {
        return new Inventario(this);
    }
}
