package game.Items;

import game.player.Jugador;

public class PocionSalud extends Item {
    private int cantidad;

    public PocionSalud(int cantidad) {
        super('H'); // Símbolo 'H' para poción de salud
        this.cantidad = cantidad;
    }

    @Override
    public void usar(Jugador jugador) {
        jugador.recuperarVida(cantidad); // Recuperar vida al jugador
        System.out.println("Usaste una poción de salud y recuperaste " + cantidad + " puntos de vida.");
    }

    @Override
    public String getNombre() {
        return "Poción de Salud"; // Nombre del ítem
    }
    
}
