package game.player;

import game.Items.Item;
import java.util.ArrayList;

public class Inventario {
    private ArrayList<Item> items;

    public Inventario() {
        this.items = new ArrayList<>();
    }

    public void agregarItem(Item item) {
        items.add(item); // Agregar el ítem al inventario
    }

    public void usarItem(int index, Jugador jugador) {
        if (index >= 0 && index < items.size()) {
            Item item = items.get(index);
            item.usar(jugador);
            items.remove(index); // Remover el ítem después de usarlo
        } else {
            System.out.println("Ítem no válido.");
        }
    }

    public void mostrarInventario() {
        for (Item item : items) {
            System.out.println("- " + item.getNombre());
        }
    }
}
