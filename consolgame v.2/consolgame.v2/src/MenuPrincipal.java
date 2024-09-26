import game.map.Mapa;
import game.player.Jugador;

import java.util.Scanner;

public class MenuPrincipal {

    // Opciones del menú principal
    private String[] opciones = {
            "Iniciar Nuevo Juego",
            "Ver Instrucciones",
            "Salir del Juego"
    };

    // Mostrar el menú principal
    public void mostrarMenu() {
        System.out.println("\n----- BIENVENIDO A LA MAZMORRA -----");
        System.out.println("\n----- Menú Principal -----");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        System.out.println("--------------------------");
    }

    // Lógica para seleccionar las opciones del menú
    public void seleccionarOpcion() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                limpiarPantalla();
                mostrarMenu();
                System.out.print("Selecciona una opción: ");
                String opcion = scanner.nextLine();
                switch (opcion) {
                    case "1":
                        iniciarJuego();
                        return;
                    case "2":
                        verInstrucciones();
                        return;
                    case "3":
                        System.out.println("Saliendo del juego...");
                        return;
                    default:
                        System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                }
            }
        }
    }

    // Método para iniciar el juego completo
    public void iniciarJuego() {
        limpiarPantalla();
        System.out.println("¡Iniciando un nuevo juego!");

        // Crear el mapa y jugador
        Mapa mapa = new Mapa(80, 30);
        Jugador jugador = new Jugador(mapa);
        Scanner scanner = new Scanner(System.in);
        boolean jugando = true;
        String mensajeEstado = "";

        // Bucle principal del juego
        while (jugando) {
            //limpiarPantalla();
            mapa.mostrarMapa();  // Mostrar el mapa actualizado con el jugador

            // Mostrar comandos disponibles
            System.out.println("Usa W, A, S, D para moverte, U para abrir inventario o Q para salir.");
            System.out.println("\nSalud: " + jugador.getVida() + " | Ataque: " + jugador.getAtaque());

            // Mostrar el mensaje de estado, si existe
            if (!mensajeEstado.isEmpty()) {
                System.out.println(mensajeEstado);
                mensajeEstado = "";  // Limpiar el mensaje después de mostrarlo
            }

            // Leer el comando del jugador
            String input = scanner.next().toLowerCase(); 
            if (input.length() == 0) continue;

            char comando = input.charAt(0);  // Procesar solo el primer carácter

            // Ejecutar la acción correspondiente
            switch (comando) {
                case 'w':
                case 'a':
                case 's':
                case 'd':
                    jugador.mover(comando);  // Mover al jugador en la dirección
                    break;
                case 'q':
                    jugando = false;  // Salir del bucle y terminar el juego
                    break;
                case 'u':
                    abrirInventarioInteractivo(jugador, scanner);  // Abrir el inventario
                    break;
                default:
                    mensajeEstado = "Comando no reconocido. Usa W, A, S, D, U o Q.";  // Mensaje de error
                    break;
            }
        }

        System.out.println("¡Gracias por jugar!");
    }

    // Método para abrir y manejar el inventario del jugador
    private static void abrirInventarioInteractivo(Jugador jugador, Scanner scanner) {
        boolean enInventario = true;
        while (enInventario) {
            jugador.mostrarInventario();
            System.out.println("Selecciona un ítem con su número o presiona '0' para cerrar el inventario.");

            String input = scanner.next();  // Leer entrada del usuario
            int opcion;

            try {
                opcion = Integer.parseInt(input);  // Convertir la entrada en número
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                continue;
            }

            if (opcion == 0) {
                enInventario = false;  // Cerrar inventario
                System.out.println("Has cerrado el inventario.");
            } else {
                jugador.usarItem(opcion - 1);  // Usar el ítem seleccionado
            }
        }
    }

    // Mostrar instrucciones
    public void verInstrucciones() {
        limpiarPantalla();
        Instrucciones instrucciones = new Instrucciones();
        instrucciones.mostrarInstrucciones();
    }

    // Limpiar la pantalla usando códigos ANSI
    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

// Clase de instrucciones del juego
class Instrucciones {
    public void mostrarInstrucciones() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n----- Instrucciones del Juego -----");
            System.out.println("Bienvenido al juego de la Mazmora. Tu objetivo es escapar de la mazmora.");
            System.out.println("A continuación, se describen los símbolos y objetos que encontrarás en el juego:\n");

            System.out.println("Simbología de Items:");
            System.out.println("- H: PocionSalud - Restauran salud.");
            System.out.println("- K: Llaves - Te permiten abrir puertas cerradas.");
            System.out.println("- F: PocionFuerza - Mejora tus puntos de ataque.");

            System.out.println("\nSimbología de Enemigos:");
            System.out.println("- G: Goblins - Enemigos débiles que atacan en grupo.");
            System.out.println("- O: Orcos - Enemigos más fuertes con alta defensa.");
            System.out.println("- S: Slimes - Enemigos que se dividen en más slimes al ser atacados.");
            System.out.println("- D: Dragones - Enemigos muy poderosos, ¡ten cuidado!");

            System.out.println("\nSimbología de Salida:");
            System.out.println("- E: Salida - La celda que debes encontrar para escapar de la mazmora.");

            System.out.println("\nSimbología del Jugador:");
            System.out.println("- J: Tú - Eres el jugador en el juego.");

            System.out.println("\nSimbología del Inventario:");
            System.out.println("- Para usar el inventario presiona U.");
            System.out.println("- Para usar los items seleciona el numero correspondiente a la fila del item.");

            System.out.println("\n¡Buena suerte en tu aventura!");

            // Opción para volver al menú principal
            while (true) {
                System.out.print("\nPresiona 'M' para volver al menú principal: ");
                String volver = scanner.nextLine();
                if (volver.equalsIgnoreCase("M")) {
                    MenuPrincipal menu = new MenuPrincipal();
                    menu.seleccionarOpcion();
                    return;
                } else {
                    System.out.println("Comando inválido. Por favor, presiona 'M' para volver al menú principal.");
                }
            }
        }
    }
}
