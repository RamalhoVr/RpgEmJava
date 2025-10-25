package rpg.origem;

import java.utils.Collections;
import java.util.List;

public class OrigemHumano implements Origem{
    private final int bonusFisico;

    public OrigemHumano(){
        this(2);
    }


    @Override
    public String getNome(){
        return "Humano";
    }

    @Override
    public String getDescricao(){
        return "Humanos são versáteis e tem uma facilidade em acertar ataques"
    }

    @Override
    public List<String> getPoderes() {
        return Collections.emptyList();
    }

    @Override
    public int modificarAtaqueBase(int ataqueBase) {
        return ataqueBase + bonusFisico;
    }
}