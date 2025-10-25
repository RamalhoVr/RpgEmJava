package rpg.poderes;

import java.util.ArrayList;
import java.util.List;

public class PoderPresets {

    public static List<Poder> getPoderes() {
        List<Poder> poderes = new ArrayList<>();

        // Poderes de Água
        poderes.add(new Poder("agua1", "Jato d'Água", "Um jato de água que causa dano moderado.", 1));
        poderes.add(new Poder("agua2", "Hidro Bomba", "Um ataque de água extremamente poderoso.", 10));
        poderes.add(new Poder("agua3", "Redemoinho", "Cria um redemoinho que prende o inimigo.", 15));

        // Poderes de Fogo
        poderes.add(new Poder("fogo1", "Brasas", "Lança brasas que causam dano leve.", 1));
        poderes.add(new Poder("fogo2", "Lança-Chamas", "Um ataque de fogo intenso que queima o inimigo.", 10));
        poderes.add(new Poder("fogo3", "Explosão de Fogo", "Uma explosão de fogo devastadora.", 15));

        // Poderes de Planta
        poderes.add(new Poder("planta1", "Chicote de Cipó", "Golpeia o inimigo com cipós.", 1));
        poderes.add(new Poder("planta2", "Folha Navalha", "Lança folhas afiadas que cortam o inimigo.", 10));
        poderes.add(new Poder("planta3", "Raio Solar", "Um ataque poderoso que concentra energia solar.", 15));

        // Poderes de Pedra
        poderes.add(new Poder("pedra1", "Lançamento de Rocha", "Arremessa uma rocha no inimigo.", 1));
        poderes.add(new Poder("pedra2", "Deslizamento de Pedras", "Causa um deslizamento de pedras sobre o inimigo.", 10));
        poderes.add(new Poder("pedra3", "Terremoto", "Um ataque que faz o chão tremer violentamente.", 15));

        // Poderes de Elétrico
        poderes.add(new Poder("eletrico1", "Choque do Trovão", "Um choque elétrico que paralisa o inimigo.", 1));
        poderes.add(new Poder("eletrico2", "Trovão", "Um ataque elétrico devastador que pode causar paralisia.", 10));
        poderes.add(new Poder("eletrico3", "Relâmpago", "Um relâmpago rápido e poderoso.", 15));

        // Poderes de Psíquico
        poderes.add(new Poder("psiquico1", "Confusão", "Causa dano psíquico e confunde o inimigo.", 1));
        poderes.add(new Poder("psiquico2", "Poder Psíquico", "Um ataque psíquico extremamente poderoso.", 10));
        poderes.add(new Poder("psiquico3", "Campo Psíquico", "Cria um campo que amplifica ataques psíquicos.", 15));

        return poderes;
    }
}