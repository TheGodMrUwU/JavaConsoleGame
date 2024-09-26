package game.enemies;

import game.player.Jugador;

public class Goblin extends Enemigos {
    public Goblin(int x, int y) {
        super(20, 10, x, y); // Vida de 8 y ataque de 5
    }

    @Override
    public void atacar(Jugador jugador) {
        jugador.recibirDanio(ataque);
        System.out.println("El Goblin ataca al jugador y causa " + ataque + " puntos de daño.");
    }

    @Override
    public char getSimbolo() {
        return 'G'; // Símbolo para Goblin
    }
}
