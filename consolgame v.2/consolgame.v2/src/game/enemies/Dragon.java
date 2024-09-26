package game.enemies;

import game.player.Jugador;

public class Dragon extends Enemigos {
    public Dragon(int x, int y) {
        super(50, 20, x, y); // Vida de 8 y ataque de 5
    }

    @Override
    public void atacar(Jugador jugador) {
        jugador.recibirDanio(ataque);
        System.out.println("El Dragon ataca al jugador y causa " + ataque + " puntos de daño.");
    }

    @Override
    public char getSimbolo() {
        return 'D'; // Símbolo para Goblin
    }
}