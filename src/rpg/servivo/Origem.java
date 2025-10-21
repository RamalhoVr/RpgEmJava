package rpg.servivo;

import java.util.List;

public interface Origem{
    String getNome();
    String getDescricao();
    List<string> getPoderes();

    default int modificarAtaqueBase(int ataque) {return ataqueBase;}
    default int modificarDefesaBase(int defesa) {return defesaBase;}

}