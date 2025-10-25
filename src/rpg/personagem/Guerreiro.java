package rpg.personagem;

import java.util.Random;

public class Guerreiro extends Personagem {

    public Guerreiro(String nome, int nivel) {
        super(nome, 120 + nivel * 10, 18 + nivel * 2, 12 + nivel, nivel, "humano");
    }

    @Override
    public int atacar(Personagem defensor) {
        double multiplicador = TipoUtil.calcularMultiplicador(this.getTipo(), defensor.getTipo());
        boolean critico = Math.random() < 0.2; // 20% de chance de crítico
        int danoBase = (int) (this.ataque * multiplicador);
        return critico ? danoBase * 2 : danoBase;
    }

    @Override
    public void recuperarVida() {
        this.pontosVida += 5; // 
        if (this.pontosVida > this.maxPontosVida) {
            this.pontosVida = this.maxPontosVida; 
        }
        System.out.println(this.nome + " recuperou " + vidaRecuperada + " pontos de vida!");
    }
}