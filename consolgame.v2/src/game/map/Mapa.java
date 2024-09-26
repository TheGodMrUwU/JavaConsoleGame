package game.map;

import java.util.Random;
import game.Items.Item;
import game.Items.Llave;
import game.Items.PocionFuerza;
import game.Items.PocionSalud;
import game.enemies.Dragon;
import game.enemies.Enemigos;
import game.enemies.Goblin;
import game.enemies.Orco;
import game.enemies.Slime;

public class Mapa {
    private char[][] mapa; // Representación del mapa
    private Enemigos[][] enemigos; // Matriz para rastrear enemigos
    private Item[][] items; // Matriz para almacenar ítems
    private int ancho;
    private int alto;
    private Random random;

    // Constructor que inicializa el mapa con dimensiones específicas
    public Mapa(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.mapa = new char[alto][ancho];
        this.enemigos = new Enemigos[alto][ancho];
        this.items = new Item[alto][ancho];
        this.random = new Random();
        inicializarMapa();
        generarMazmorras();
        agregarEnemigos(30); // Cambiar la cantidad de enemigos aquí
        agregarItems(7); // Agregar ítems al mapa
    }

    public void reiniciarMapa() {
        inicializarMapa();      // Reiniciar el mapa como si fuera nuevo
        generarMazmorras();     // Generar nuevas mazmorras
        agregarEnemigos(30);    // Colocar enemigos en el nuevo mapa
        agregarItems(7);        // Colocar nuevos ítems
        colocarSalida();        // Colocar una nueva salida
    }
    

    // Método para colocar la salida 'E' en una posición aleatoria vacía del mapa
    public int[] colocarSalida() {
        int x, y;

        // Buscar una posición aleatoria vacía (' ')
        do {
            x = random.nextInt(ancho - 2) + 1;
            y = random.nextInt(alto - 2) + 1;
        } while (mapa[y][x] != ' ');

        // Colocar la salida en la posición encontrada
        mapa[y][x] = 'E';
        System.out.println("¡La salida 'E' ha aparecido en una ubicación secreta!");

        return new int[]{x, y}; // Devolver las coordenadas de la salida
    }

    // Inicializa todo el mapa como espacio vacío y añade paredes de límites
    private void inicializarMapa() {
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if (esBorde(i, j)) {
                    mapa[i][j] = '#'; // Pared
                } else {
                    mapa[i][j] = ' '; // Espacio vacío
                }
                enemigos[i][j] = null; // Inicializar con null para no enemigos
                items[i][j] = null; // Inicializar con null para no ítems
            }
        }
    }

    private boolean esBorde(int i, int j) {
        return i == 0 || i == alto - 1 || j == 0 || j == ancho - 1;
    }

    private void generarMazmorras() {
        int numHabitaciones = random.nextInt(50) + 50; // Aumentar número de habitaciones (50 a 99)
        for (int i = 0; i < numHabitaciones; i++) {
            crearHabitacion();
        }
        crearPasillos();
    }

    private void crearHabitacion() {
        int tamañoHabitacion = random.nextInt(5) + 6; // Tamaño aleatorio (6 a 10)
        int x = random.nextInt(ancho - tamañoHabitacion - 2) + 1;
        int y = random.nextInt(alto - tamañoHabitacion - 2) + 1;

        if (!puedeColocarHabitacion(x, y, tamañoHabitacion))
            return;

        for (int i = y; i < y + tamañoHabitacion; i++) {
            for (int j = x; j < x + tamañoHabitacion; j++) {
                mapa[i][j] = (i == y || i == y + tamañoHabitacion - 1 || j == x || j == x + tamañoHabitacion - 1) ? '#'
                        : ' ';
            }
        }

        for (int i = 0; i < 4; i++) {
            crearAbertura(x, y, tamañoHabitacion);
        }
    }

    private boolean puedeColocarHabitacion(int x, int y, int tamaño) {
        for (int i = y; i < y + tamaño; i++) {
            for (int j = x; j < x + tamaño; j++) {
                if (mapa[i][j] != ' ')
                    return false; // Si hay un obstáculo, no se puede colocar la habitación
            }
        }
        return true; // Se puede colocar la habitación
    }

    private void crearAbertura(int x, int y, int tamaño) {
        int pared = random.nextInt(4);
        switch (pared) {
            case 0:
                mapa[y][random.nextInt(tamaño - 2) + x + 1] = ' '; // Abertura en la parte superior
                break;
            case 1:
                mapa[y + tamaño - 1][random.nextInt(tamaño - 2) + x + 1] = ' '; // Abertura en la parte inferior
                break;
            case 2:
                mapa[random.nextInt(tamaño - 2) + y + 1][x] = ' '; // Abertura a la izquierda
                break;
            case 3:
                mapa[random.nextInt(tamaño - 2) + y + 1][x + tamaño - 1] = ' '; // Abertura a la derecha
                break;
        }
    }

    private void crearPasillos() {
        for (int i = 1; i < alto - 1; i++) {
            for (int j = 1; j < ancho - 1; j++) {
                if (mapa[i][j] == ' ' && random.nextDouble() < 0.2) {
                    mapa[i][j] = ' '; // Espacio vacío aleatorio para pasillos
                }
            }
        }
    }

    public char getCelda(int x, int y) {
        return mapa[y][x];
    }

    public Enemigos getEnemigo(int x, int y) {
        return enemigos[y][x];
    }

    public void agregarEnemigos(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int x = random.nextInt(ancho - 2) + 1;
            int y = random.nextInt(alto - 2) + 1;

            if (mapa[y][x] == ' ' && enemigos[y][x] == null) {
                Enemigos enemigo = generarEnemigo(x, y);
                mapa[y][x] = enemigo.getSimbolo();
                enemigos[y][x] = enemigo;
            } else {
                i--; // Reintentar si la posición ya está ocupada
            }
        }
    }

    public Enemigos generarEnemigo(int x, int y) {
        int tipo = random.nextInt(4);
        switch (tipo) {
            case 0:
                return new Goblin(x, y);
            case 1:
                return new Slime(x, y);
            case 2:
                return new Orco(x, y);
            case 3:
                return new Dragon(x, y);
            default:
                return null; // No debería llegar aquí
        }
    }

    public void eliminarEnemigo(Enemigos enemigo) {
        int x = enemigo.getX();
        int y = enemigo.getY();
        enemigos[y][x] = null; // Limpiar la referencia al enemigo
        mapa[y][x] = ' '; // Limpiar la celda
    }

    public void mostrarMapa() {
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                System.out.print(mapa[y][x]);
            }
            System.out.println();
        }
    }

    public void setCelda(int x, int y, char valor) {
        mapa[y][x] = valor; // Establecer valor en una celda específica
    }

    // Método para agregar ítems al mapa
    private void agregarItems(int cantidad) {
        boolean llaveGenerada = false; // Para controlar si ya hemos generado la llave
    
        for (int i = 0; i < cantidad; i++) {
            int x = random.nextInt(ancho - 2) + 1;
            int y = random.nextInt(alto - 2) + 1;
    
            // Asegurarse de que haya espacio para el ítem
            if (mapa[y][x] == ' ') {
                if (!llaveGenerada) {
                    // Generar la única llave, pasamos la referencia del mapa
                    items[y][x] = new Llave(this); // Aquí pasamos el mapa actual
                    mapa[y][x] = items[y][x].getSimbolo(); // Representación de la llave en el mapa
                    llaveGenerada = true; // Cambiamos la bandera porque ya generamos la llave
                } else {
                    // Generar pociones aleatoriamente
                    int tipoItem = random.nextInt(2); // 0: Pocion de Salud, 1: Pocion de Fuerza
                    switch (tipoItem) {
                        case 0:
                            items[y][x] = new PocionSalud(20); // Crear poción de salud
                            mapa[y][x] = items[y][x].getSimbolo(); // Representación en el mapa
                            break;
                        case 1:
                            items[y][x] = new PocionFuerza(5); // Crear poción de fuerza
                            mapa[y][x] = items[y][x].getSimbolo(); // Representación en el mapa
                            break;
                    }
                }
            }
        }
    }
    
    
    
    public Item recogerItem(int x, int y) {
        // ...
        if (x >= 0 && x < ancho && y >= 0 && y < alto && items[y][x] != null) {
            Item item = items[y][x];
            items[y][x] = null; // Eliminar el ítem del mapa
            mapa[y][x] = ' '; // Actualizar el mapa para mostrar un espacio vacío
            return item;
        }
        return null;
    }

    public String[] toStringArray() {
        String[] resultado = new String[mapa.length];
        for (int i = 0; i < mapa.length; i++) {
            resultado[i] = new String(mapa[i]); // Convertir cada fila del mapa en una String
        }
        return resultado;
    }
    
}