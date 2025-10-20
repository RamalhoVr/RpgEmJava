package rpg.servivo;

public abstract class Personagem{

    protected String nome;
    protected int pontosVida;
    protected int maxPontosVida;
    protected int ataque;
    protected int defesa;
    protected int nivel;
    protected Inventario inventario;
    

    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.maxPontosVida = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = new Inventario();
    }

    public String getNome() {
        return nome;
    }

    public int getPontosVida(){
        return pontosVida;
    }
    public int getAtaque(){
        return ataque;
    }
    public int getDefesa(){
        return defesa;
    }
    public int getNivel(){
        return nivel;
    }

    public void receberDano(int dano){
        pontosVida -= dano;
        if (pontosVida > 0) pontosVida = 0;
    }

    public void curarVida(int cura){
        pontosVida += cura;
        if(pontosVida > maxPontosVida) pontosVida = maxPontosVida;

    }

    public abstract int atacar();
    
    @Override
    public String toString() {
        return String.format("%s (Nível %d) HP: %d/%d ATQ: %d DEF: %d",
                nome, nivel, pontosVida, maxPontosVida, ataque, defesa);
    }

}