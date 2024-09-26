package game.Items;

import game.map.Mapa;
import game.player.Jugador;

public class Llave extends Item {
    private Mapa mapa; // Referencia al mapa para colocar la salida

    public Llave(Mapa mapa) {
        super('K'); // Símbolo 'K' para la llave
        this.mapa = mapa; // Guardamos el mapa
    }

    @Override
    public void usar(Jugador jugador) {
        int[] salidaCoords = mapa.colocarSalida(); // Colocar la salida en el mapa
        System.out.println("Usaste la llave y la salida 'E' apareció en [" + salidaCoords[0] + ", " + salidaCoords[1] + "]!");
    }

    @Override
    public String getNombre() {
        return "Llave"; // Nombre del ítem
    }
}
