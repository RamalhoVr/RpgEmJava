package rpg.servivo;

import java.util.Arraylist;
import java.util.Collections;
import java.util.List;

public class OrigemPokemon implements Origem{

    private final List<String> poderes;
    private final int bonusMagico;

    public OrigemPokemon() {
        this(5, list.of("Ataque A Distância", "Utilização de Poderes"));
    }

    public OrigemPokemon(int bonusMagico, List<String> poderes){
        this.bonusMagico = bonusMagico;
        this.poderes = new Arraylist<>(poderes);
    }

        @Override
    public String getNome() {
        return "Pokemon";
    }

    @Override
    public String getDescricao() {
        return "Pokemon possuem poderes inatos e Ataques a Distância";
    }

    @Override
    public List<String> getPoderes() {
        return Collections.unmodifiableList(poderes);
    }

    @Override
    public int modificarDefesaBase(int defesaBase) {
        
        return defesaBase;
    }

    @Override
    public int modificarAtaqueBase(int ataqueBase) {
       
        return ataqueBase;
    }

    public int getBonusMagico() {
        return bonusMagico;
    }
}

