package rpg.servivo;

import java.util.Random;

public class Guerreiro extends Personagem{

   public Guerreiro(String nome int nivel){
    super(nome, 120 + nivel * 10, 18 + nivel * 2, 12 + nivel, nivel);
   } 

   @Override
   public int atacar(Random rand){
    return ((int)((max - min + 1)* Math.random())) + min;
   }
   
}