package game.enemies;
import game.player.Jugador;

public class Slime extends Enemigos {
    public Slime(int x, int y) {
        super(15, 3, x, y); // Vida de 8 y ataque de 5
    }

    @Override
    public void atacar(Jugador jugador) {
        jugador.recibirDanio(ataque);
        System.out.println("El Slime ataca al jugador y causa " + ataque + " puntos de daño.");
    }

    @Override
    public char getSimbolo() {
        return 'S'; // Símbolo para Slime
    }
}