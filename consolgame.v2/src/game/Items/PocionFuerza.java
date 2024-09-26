package game.Items;

import game.player.Jugador;

public class PocionFuerza extends Item {
    private int aumento;

    public PocionFuerza(int aumento) {
        super('F'); // Símbolo 'F' para poción de fuerza
        this.aumento = aumento;
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.aumentarAtaque(aumento); // Aumentar ataque del jugador
        System.out.println("Usaste una poción de fuerza y aumentaste tu ataque en " + aumento + " puntos.");
    }

    @Override
    public String getNombre() {
        return "Poción de Fuerza"; // Nombre del ítem
    }
    
}
