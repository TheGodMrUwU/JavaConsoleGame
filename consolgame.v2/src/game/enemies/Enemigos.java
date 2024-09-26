package game.enemies;

import game.map.Mapa;
import game.player.Jugador;

public abstract class Enemigos {
    protected int vida;
    protected int ataque;
    protected int x;
    protected int y;

    public Enemigos(int vida, int ataque, int x, int y) {
        this.vida = vida;
        this.ataque = ataque;
        this.x = x;
        this.y = y;
    }

    // Método que hace que el enemigo ataque al jugador
    public void atacar(Jugador jugador) {
        System.out.println("El enemigo te ataca y causa " + ataque + " puntos de daño.");
        jugador.recibirDanio(ataque);
    }

    public void recibirDanio(int danio, Mapa mapa) {
        vida -= danio;
        System.out.println("El enemigo recibió " + danio + " puntos de daño. Vida restante: " + vida);
        if (vida <= 0) {
            System.out.println("El enemigo ha sido derrotado!");
            mapa.eliminarEnemigo(this);
        }
    }

    // Métodos para obtener las coordenadas
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVida() {
        return vida;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract char getSimbolo();
}

