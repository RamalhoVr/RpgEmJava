package rpg.origem;

import java.util.List;

public interface Origem {
    String getNome();
    String getDescricao();
    List<String> getPoderes();

    default int modificarAtaqueBase(int ataqueBase) { return ataqueBase; }
    default int modificarDefesaBase(int defesaBase) { return defesaBase; }
}