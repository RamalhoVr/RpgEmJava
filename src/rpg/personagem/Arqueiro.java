package rpg.personagem;

public class Arqueiro extends Personagem {

    public Arqueiro(String nome, int nivel) {
        super(nome, 100 + nivel * 8, 20 + nivel * 3, 10 + nivel, nivel, "humano");
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
        Random rand = new Random();
        int vidaRecuperada = 8; // Recupera 8 de vida por padrão
        if (rand.nextDouble() < 0.3) { // 30% de chance de recuperar um bônus adicional
            vidaRecuperada += 5;
        }
        this.pontosVida += vidaRecuperada;
        if (this.pontosVida > this.maxPontosVida) {
            this.pontosVida = this.maxPontosVida;
        }
        System.out.println(this.nome + " recuperou " + vidaRecuperada + " pontos de vida!");
    }
}