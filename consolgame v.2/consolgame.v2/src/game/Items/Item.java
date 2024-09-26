package game.Items;

import game.player.Jugador;

public abstract class Item {
    protected char simbolo;

    public Item(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSimbolo() {
        return simbolo; // Retornar el símbolo del ítem
    }

    public abstract void usar(Jugador jugador); // Método abstracto para usar el ítem

    // Método abstracto para obtener el nombre del ítem
    public abstract String getNombre();

}
