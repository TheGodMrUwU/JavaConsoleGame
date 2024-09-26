package game.enemies;
import game.player.Jugador;

public class Orco extends Enemigos {
    public Orco(int x, int y) {
        super(30, 12, x, y); // Vida de 8 y ataque de 5
    }

    @Override
    public void atacar(Jugador jugador) {
        jugador.recibirDanio(ataque);
        System.out.println("El Orco ataca al jugador y causa " + ataque + " puntos de daño.");
    }

    @Override
    public char getSimbolo() {
        return 'O'; // Símbolo para Goblin
    }
}