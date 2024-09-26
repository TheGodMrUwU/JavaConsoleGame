package game.player;

import game.map.Mapa;
import game.enemies.Enemigos;
import game.Items.Item;

public class Jugador {
    private int x;
    private int y;
    private Mapa mapa;
    private int vida;
    private int ataque;
    private Inventario inventario; // Instancia de Inventario
    private int contadorMapas = 0;  // <<--- Añadimos esta variable para llevar el control de los mapas completados

    public Jugador(Mapa mapa) {
        this.mapa = mapa;
        this.x = 1;
        this.y = 1;
        this.vida = 100;
        this.ataque = 10;
        this.inventario = new Inventario(); // Inicializar el inventario
        mapa.setCelda(x, y, 'J');
    }

     // Método para interactuar con la salida y gestionar la generación de nuevos mapas
     public void interactuarConSalida() {
        contadorMapas++; // Aumentamos el contador cuando interactuamos con la salida
        if (contadorMapas < 5) {
            System.out.println("Has encontrado la salida. Preparando una nueva mazmorra...");
            mapa.reiniciarMapa(); // Reiniciamos el mapa
        } else {
            System.out.println("¡Felicidades! Has completado todas las mazmorras y has ganado el juego.");
            System.exit(0); // Cerramos el juego cuando se completan 5 mapas
        }
    }

    // Otros métodos que ya tenías, como mover, atacar, etc.

    // Getters para el contador, si necesitas usarlos
    public int getContadorMapas() {
        return contadorMapas;
    }

    public void recuperarVida(int cantidad) {
        vida += cantidad; // Aumentar vida
        System.out.println("Vida actual: " + vida);
    }

    public void aumentarAtaque(int cantidad) {
        ataque += cantidad; // Aumentar ataque
        System.out.println("Ataque actual: " + ataque);
    }

    public void mover(char direccion) {
        mapa.setCelda(x, y, ' ');
    
        int nuevoX = x, nuevoY = y;
        switch (direccion) {
            case 'w':
                nuevoY--;
                break;
            case 's':
                nuevoY++;
                break;
            case 'a':
                nuevoX--;
                break;
            case 'd':
                nuevoX++;
                break;
        }
    
        Enemigos enemigo = mapa.getEnemigo(nuevoX, nuevoY);
        if (enemigo != null) {
            atacarEnemigo(enemigo);
    
            if (enemigo.getVida() > 0) {
                enemigo.atacar(this);
            }
    
        } else if (mapa.getCelda(nuevoX, nuevoY) == ' ') {
            x = nuevoX;
            y = nuevoY;
        } else if (mapa.getCelda(nuevoX, nuevoY) == 'E') {
            // Aquí interactuamos con la salida
            interactuarConSalida();
        }
    
        // Verificar si hay un ítem en la nueva posición
        Item item = mapa.recogerItem(nuevoX, nuevoY);
        if (item != null) {
            System.out.println("¡Has encontrado un " + item.getNombre() + "!");
            inventario.agregarItem(item);
            inventario.mostrarInventario();
        } else {
            
        }
    
        mapa.setCelda(x, y, 'J');
    }
    

    public void atacarEnemigo(Enemigos enemigo) {
        System.out.println("Atacas al enemigo y le causas " + ataque + " puntos de daño.");
        enemigo.recibirDanio(ataque, mapa);
    }

    public void recibirDanio(int danio) {
        vida -= danio;
        System.out.println("Recibiste " + danio + " puntos de daño. Vida restante: " + vida);
        if (vida <= 0) {
            System.out.println("Has muerto. Fin del juego.");
            System.exit(0);
        }
    }

    // Método para recoger un ítem y agregarlo al inventario
    public void recogerItem(Item item) {
        inventario.agregarItem(item);
        System.out.println("Has recogido un ítem: " + item.getNombre());
    }

    // Método para usar un ítem del inventario
    public void usarItem(int index) {
        inventario.usarItem(index, this);
    }

    // Mostrar el inventario
    public void mostrarInventario() {
        inventario.mostrarInventario(); // Llamar al método del inventario
    }

    // Getters
    public int posX() {
        return x;
    }

    public int posY() {
        return y;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public Inventario getInventario() {
        return inventario; // Método para obtener el inventario
    }
} 