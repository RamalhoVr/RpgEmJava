package rpg.util;

public class TipoUtil {

    public static double calcularMultiplicador(String tipoAtacante, String tipoDefensor) {
        // Fraquezas entre tipos de Pokémon
        if (tipoAtacante.equals("água") && tipoDefensor.equals("fogo")) {
            return 2.0;
        } else if (tipoAtacante.equals("fogo") && tipoDefensor.equals("planta")) {
            return 2.0;
        } else if (tipoAtacante.equals("planta") && tipoDefensor.equals("água")) {
            return 2.0;
        } else if (tipoAtacante.equals("pedra") && tipoDefensor.equals("fogo")) {
            return 2.0;
        } else if (tipoAtacante.equals("elétrico") && tipoDefensor.equals("água")) {
            return 2.0;
        } else if (tipoAtacante.equals("psíquico") && tipoDefensor.equals("pedra")) {
            return 2.0;
        }

        // Fraquezas de humanos
        if (tipoDefensor.equals("humano")) {
            if (tipoAtacante.equals("fogo") || tipoAtacante.equals("elétrico") || tipoAtacante.equals("psíquico")) {
                return 2.0; // Humanos são fracos contra fogo, elétrico e psíquico
            }
        }

        // Vantagens de humanos
        if (tipoAtacante.equals("humano")) {
            if (tipoDefensor.equals("pedra") || tipoDefensor.equals("planta")) {
                return 2.0;
            }
        }

        // Resistências
        if (tipoDefensor.equals("água") && tipoAtacante.equals("fogo")) {
            return 0.5;
        } else if (tipoDefensor.equals("fogo") && tipoAtacante.equals("planta")) {
            return 0.5;
        } else if (tipoDefensor.equals("planta") && tipoAtacante.equals("água")) {
            return 0.5;
        } else if (tipoDefensor.equals("fogo") && tipoAtacante.equals("pedra")) {
            return 0.5;
        } else if (tipoDefensor.equals("água") && tipoAtacante.equals("elétrico")) {
            return 0.5;
        } else if (tipoDefensor.equals("pedra") && tipoAtacante.equals("psíquico")) {
            return 0.5;
        }

        if (tipoDefensor.equals("humano")) {
            if (tipoAtacante.equals("pedra") || tipoAtacante.equals("planta")) {
                return 0.5; 
            }
        }

        return 1.0; // Sem vantagem ou desvantagem
    }
}