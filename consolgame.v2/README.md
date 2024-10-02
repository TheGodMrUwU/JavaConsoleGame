## COMO FUNCIONA LA CLASE MAPA??

¡Por supuesto! Voy a explicar detalladamente todo el código de la clase `Mapa` que me has proporcionado, línea por línea o por bloques lógicos de código, para que entiendas exactamente qué está ocurriendo y la lógica detrás de cada sección.

### **Clase `Mapa`**

La clase `Mapa` pertenece al paquete `game.map` y tiene como función principal gestionar el mapa de un juego, donde se representan las mazmorras, enemigos e ítems. 

```java
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
```

**Explicación:**
- El paquete `game.map` indica que esta clase está dentro de un paquete que gestiona el mapa del juego.
- Se importan varias clases:
  - **`Random`** de la librería estándar de Java, para generar números aleatorios.
  - Varias clases de **ítems** (`Item`, `Llave`, `PocionFuerza`, `PocionSalud`) y **enemigos** (`Dragon`, `Enemigos`, `Goblin`, `Orco`, `Slime`), que representan los objetos y enemigos que interactúan en el mapa.

---

### **Atributos de la clase `Mapa`**

```java
public class Mapa {
    private char[][] mapa; // Representación del mapa
    private Enemigos[][] enemigos; // Matriz para rastrear enemigos
    private Item[][] items; // Matriz para almacenar ítems
    private int ancho;
    private int alto;
    private Random random;
```

**Explicación:**
- **`char[][] mapa`**: Una matriz de caracteres que representa el mapa visualmente. Cada celda del mapa tiene un símbolo que puede ser una pared (`'#'`), un espacio vacío (`' '`), una salida (`'E'`), o un enemigo o ítem (con sus símbolos correspondientes).
- **`Enemigos[][] enemigos`**: Una matriz donde se almacenan objetos de tipo `Enemigos` (las instancias de los enemigos) en las posiciones correspondientes del mapa.
- **`Item[][] items`**: Similar a `enemigos`, esta matriz almacena los ítems que se encuentran en el mapa.
- **`int ancho` y `int alto`**: Dimensiones del mapa, ancho y alto, respectivamente.
- **`Random random`**: Una instancia de `Random` que se usa para generar posiciones y elementos aleatorios en el mapa, como habitaciones, enemigos e ítems.

---

### **Constructor**

```java
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
```

**Explicación:**
- El constructor inicializa el mapa con dimensiones específicas que se pasan como parámetros (`ancho` y `alto`).
- Inicializa las matrices `mapa`, `enemigos` e `items` para que tengan el tamaño adecuado.
- Crea una instancia de `Random` para generar números aleatorios que se usan en varios métodos.
- Luego, el constructor llama a varios métodos para configurar el mapa:
  - **`inicializarMapa`**: Prepara el mapa inicial con espacios vacíos y paredes en los bordes.
  - **`generarMazmorras`**: Crea las mazmorras y pasillos.
  - **`agregarEnemigos(30)`**: Coloca 30 enemigos en posiciones aleatorias del mapa.
  - **`agregarItems(7)`**: Coloca 7 ítems en posiciones aleatorias.

---

### **Método `reiniciarMapa`**

```java
public void reiniciarMapa() {
    inicializarMapa();      // Reiniciar el mapa como si fuera nuevo
    generarMazmorras();     // Generar nuevas mazmorras
    agregarEnemigos(30);    // Colocar enemigos en el nuevo mapa
    agregarItems(7);        // Colocar nuevos ítems
    colocarSalida();        // Colocar una nueva salida
}
```

**Explicación:**
- Este método permite **reiniciar el mapa**, es decir, empezar de cero con nuevas mazmorras, enemigos e ítems.
- Vuelve a ejecutar los mismos métodos que se llamaron en el constructor para reconfigurar el mapa.
- Además, coloca una nueva salida con el método `colocarSalida`, lo que da la sensación de que el jugador está en un mapa totalmente diferente tras cada reinicio.

---

### **Método `colocarSalida`**

```java
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
```

**Explicación:**
- Este método coloca una salida (`'E'`) en una posición aleatoria vacía del mapa.
- **Bucle `do-while`**: Busca una celda vacía (`' '`) y coloca la salida en esa posición.
- Imprime un mensaje indicando que la salida ha aparecido en una ubicación secreta.
- Devuelve las coordenadas de la salida como un arreglo de enteros `[x, y]`.

---

### **Método `inicializarMapa`**

```java
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
```

**Explicación:**
- Este método inicializa el mapa con bordes de paredes (`'#'`) y espacios vacíos en el interior (`' '`).
- También establece que las posiciones iniciales de enemigos e ítems sean `null`, lo que significa que no hay ni enemigos ni ítems en el mapa aún.

```java
private boolean esBorde(int i, int j) {
    return i == 0 || i == alto - 1 || j == 0 || j == ancho - 1;
}
```

- El método `esBorde` verifica si una celda está en el borde del mapa (es decir, en la primera o última fila, o en la primera o última columna). Si es así, devuelve `true` y esa celda se marca como una pared.

---

### **Método `generarMazmorras`**

```java
private void generarMazmorras() {
    int numHabitaciones = random.nextInt(50) + 50; // Aumentar número de habitaciones (50 a 99)
    for (int i = 0; i < numHabitaciones; i++) {
        crearHabitacion();
    }
    crearPasillos();
}
```

**Explicación:**
- Este método genera entre **50 y 99 habitaciones** aleatorias en el mapa. Utiliza el método **`crearHabitacion`** para generar una habitación por cada iteración del bucle.
- Después de crear todas las habitaciones, llama a **`crearPasillos`** para conectar las habitaciones con pasillos.

---

### **Método `crearHabitacion`**

```java
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
```

**Explicación:**
- Genera habitaciones cuadradas de tamaño

 aleatorio (entre 6 y 10 celdas de lado).
- Verifica que la habitación se pueda colocar en el mapa utilizando **`puedeColocarHabitacion`**. Si no puede, el método termina.
- Dibuja la habitación en el mapa usando paredes (`'#'`) en los bordes y espacios vacíos (`' '`) en el interior.
- Finalmente, crea aberturas (entradas/salidas) en la habitación utilizando el método **`crearAbertura`**.

---

### **Método `crearAbertura`**

```java
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
```

**Explicación:**
- Este método crea una abertura (una puerta) en uno de los lados de la habitación. Dependiendo del valor de `pared`, la abertura se coloca en la parte superior, inferior, izquierda o derecha de la habitación.

---

### **Método `crearPasillos`**

```java
private void crearPasillos() {
    for (int i = 1; i < alto - 1; i++) {
        for (int j = 1; j < ancho - 1; j++) {
            if (mapa[i][j] == ' ' && random.nextDouble() < 0.2) {
                mapa[i][j] = ' '; // Espacio vacío aleatorio para pasillos
            }
        }
    }
}
```

**Explicación:**
- Después de crear las habitaciones, este método genera pasillos aleatorios con una probabilidad del 20% para conectar las habitaciones. Recorre todo el mapa y, si encuentra un espacio vacío (`' '`), tiene un 20% de probabilidad de que lo convierta en un pasillo.

---

### **Método `agregarEnemigos`**

```java
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
```

**Explicación:**
- Este método coloca enemigos en posiciones aleatorias del mapa.
- Genera un número `cantidad` de enemigos.
- Si encuentra una posición vacía en el mapa, genera un enemigo en esa posición con el método **`generarEnemigo`** y actualiza el mapa con el símbolo del enemigo.
- Si la posición ya está ocupada, el método vuelve a intentar (es decir, decrementa `i` y reitera).

```java
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
```

**Explicación:**
- Este método genera un enemigo aleatorio. Dependiendo del número aleatorio (`tipo`), se genera un **Goblin**, **Slime**, **Orco** o **Dragon** en las coordenadas dadas.

---

### **Método `eliminarEnemigo`**

```java
public void eliminarEnemigo(Enemigos enemigo) {
    int x = enemigo.getX();
    int y = enemigo.getY();
    enemigos[y][x] = null; // Limpiar la referencia al enemigo
    mapa[y][x] = ' '; // Limpiar la celda
}
```

**Explicación:**
- Este método elimina un enemigo del mapa. Al recibir un objeto `Enemigos`, se obtiene su posición con `getX()` y `getY()`, y se eliminan tanto del mapa como de la matriz de enemigos, dejando un espacio vacío (`' '`).

---

### **Método `mostrarMapa`**

```java
public void mostrarMapa() {
    for (int y = 0; y < alto; y++) {
        for (int x = 0; x < ancho; x++) {
            System.out.print(mapa[y][x]);
        }
        System.out.println();
    }
}
```

**Explicación:**
- Este método imprime el mapa en la consola, recorriendo la matriz `mapa` fila por fila.

---

### **Método `agregarItems`**

```java
private void agregarItems(int cantidad) {
    boolean llaveGenerada = false; // Para controlar si ya hemos generado la llave

    for (int i = 0; i < cantidad; i++) {
        int x = random.nextInt(ancho - 2) + 1;
        int y = random.nextInt(alto - 2) + 1;

        if (mapa[y][x] == ' ') {
            if (!llaveGenerada) {
                items[y][x] = new Llave(this); // Generar la llave
                mapa[y][x] = items[y][x].getSimbolo();
                llaveGenerada = true;
            } else {
                int tipoItem = random.nextInt(2);
                switch (tipoItem) {
                    case 0:
                        items[y][x] = new PocionSalud(20); // Poción de salud
                        mapa[y][x] = items[y][x].getSimbolo();
                        break;
                    case 1:
                        items[y][x] = new PocionFuerza(5); // Poción de fuerza
                        mapa[y][x] = items[y][x].getSimbolo();
                        break;
                }
            }
        }
    }
}
```

**Explicación:**
- Este método coloca ítems en posiciones aleatorias del mapa. Primero coloca una **Llave**, asegurándose de que solo haya una. Luego, distribuye **pociones de salud** o **pociones de fuerza** en las celdas vacías restantes.

---

### **Método `recogerItem`**

```java
public Item recogerItem(int x, int y) {
    if (x >= 0 && x < ancho && y >= 0 && y < alto && items[y][x] != null) {
        Item item = items[y][x];
        items[y][x] = null; // Eliminar el ítem del mapa
        mapa[y][x] = ' '; // Actualizar el mapa para mostrar un espacio vacío
        return item;
    }
    return null;
}
```

**Explicación:**
- Este método permite recoger un ítem en las coordenadas `(x, y)`. Si hay un ítem en esa posición, lo devuelve, lo elimina del mapa y actualiza la celda para que esté vacía (`' '`).

---

### **Método `toStringArray`**

```java
public String[] toStringArray() {
    String[] resultado = new String[mapa.length];
    for (int i = 0; i < mapa.length; i++) {
        resultado[i] = new String(mapa[i]); // Convertir cada fila del mapa en una String
    }
    return resultado;
}
```

**Explicación:**
- Convierte el mapa en un arreglo de cadenas (`String[]`), donde cada cadena es una fila del mapa. Esto es útil si se desea manipular o visualizar el mapa en formato de texto.

---

### **Conclusión general**

La clase **`Mapa`** gestiona la creación y manipulación del mapa del juego. Desde la colocación de habitaciones, pasillos y paredes, hasta la distribución de enemigos y objetos, todo se hace de forma aleatoria para garantizar que el juego tenga un entorno cambiante en cada ejecución. Esta lógica es fundamental para un juego tipo "mazmorras", ya que genera variedad y hace que el jugador explore nuevos mapas en cada partida.

El uso de matrices bidimensionales permite un manejo eficaz de las posiciones de los objetos y personajes en el mapa, y la clase `Random` garantiza que la distribución sea impredecible.

## COMO FUNCIONA LA CLASE JUGADOR??

Claro, vamos a desglosar el código paso a paso y en detalle.

### Paquetes Importados

- **`game.map.Mapa`**: Este paquete contiene la clase `Mapa`, que maneja el entorno o el mapa del juego.
- **`game.enemies.Enemigos`**: Este paquete contiene la clase `Enemigos`, que representa a los enemigos en el juego.
- **`game.Items.Item`**: Este paquete contiene la clase `Item`, que representa los objetos que el jugador puede recoger.

### Clase `Jugador`

La clase `Jugador` representa al personaje que el jugador controla. A continuación, se describen sus atributos y métodos:

#### Atributos

- **`x` y `y`**: Son las coordenadas del jugador en el mapa. Indican la posición actual del jugador.
- **`mapa`**: Es una referencia al objeto `Mapa` que contiene el entorno del juego.
- **`vida`**: Representa los puntos de vida del jugador. Comienza con 100.
- **`ataque`**: Indica los puntos de ataque del jugador. Comienza con 10.
- **`inventario`**: Es una referencia a un objeto de la clase `Inventario`, que almacena los objetos que el jugador posee.
- **`contadorMapas`**: Es un contador que lleva la cuenta de cuántos mapas ha completado el jugador. Comienza en 0.

#### Constructor

```java
public Jugador(Mapa mapa) {
    this.mapa = mapa;
    this.x = 1;
    this.y = 1;
    this.vida = 100;
    this.ataque = 10;
    this.inventario = new Inventario(); // Inicializa el inventario
    mapa.setCelda(x, y, 'J');
}
```

- **Inicialización**: El constructor recibe un objeto `Mapa` y lo asigna al atributo `mapa`.
- **Posición Inicial**: El jugador empieza en la posición `(1, 1)`.
- **Vida y Ataque**: Se inicializan con valores predeterminados.
- **Inventario**: Se crea un nuevo objeto `Inventario`.
- **Marcación en el Mapa**: La posición del jugador se marca en el mapa con `'J'`.

#### Métodos

##### `interactuarConSalida()`

```java
public void interactuarConSalida() {
    contadorMapas++;
    if (contadorMapas < 5) {
        System.out.println("Has encontrado la salida. Preparando una nueva mazmorra...");
        mapa.reiniciarMapa();
    } else {
        System.out.println("¡Felicidades! Has completado todas las mazmorras y has ganado el juego.");
        System.exit(0);
    }
}
```

- **Incremento del Contador**: Aumenta `contadorMapas` cada vez que el jugador interactúa con una salida.
- **Comprobación**: Si el jugador ha completado menos de 5 mapas, el mapa se reinicia.
- **Finalización del Juego**: Si el jugador completa 5 mapas, el juego termina con un mensaje de victoria.

##### `recuperarVida(int cantidad)`

```java
public void recuperarVida(int cantidad) {
    vida += cantidad;
    System.out.println("Vida actual: " + vida);
}
```

- **Aumento de Vida**: Incrementa los puntos de vida del jugador.
- **Mensaje**: Muestra la vida actual después de la recuperación.

##### `aumentarAtaque(int cantidad)`

```java
public void aumentarAtaque(int cantidad) {
    ataque += cantidad;
    System.out.println("Ataque actual: " + ataque);
}
```

- **Aumento de Ataque**: Incrementa los puntos de ataque del jugador.
- **Mensaje**: Muestra el ataque actual después del incremento.

##### `mover(char direccion)`

```java
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
        interactuarConSalida();
    }

    Item item = mapa.recogerItem(nuevoX, nuevoY);
    if (item != null) {
        System.out.println("¡Has encontrado un " + item.getNombre() + "!");
        inventario.agregarItem(item);
        inventario.mostrarInventario();
    }
    
    mapa.setCelda(x, y, 'J');
}
```

- **Borrar Posición Actual**: Limpia la posición actual del jugador en el mapa.
- **Calcular Nueva Posición**: Actualiza las coordenadas según la dirección (`w`, `s`, `a`, `d`).
- **Interacción con Enemigos**: Si hay un enemigo en la nueva posición, el jugador lo ataca y puede recibir un contraataque.
- **Mover al Espacio Libre**: Si la nueva celda está vacía, el jugador se mueve allí.
- **Interacción con la Salida**: Si la celda es una salida (`'E'`), se llama a `interactuarConSalida()`.
- **Recoger Items**: Si hay un objeto, se recoge y se muestra en el inventario.
- **Actualizar Posición**: Marca la nueva posición del jugador en el mapa.

##### `atacarEnemigo(Enemigos enemigo)`

```java
public void atacarEnemigo(Enemigos enemigo) {
    System.out.println("Atacas al enemigo y le causas " + ataque + " puntos de daño.");
    enemigo.recibirDanio(ataque, mapa);
}
```

- **Infligir Daño**: Ataca al enemigo, reduciendo su vida por el valor del ataque del jugador.

##### `recibirDanio(int danio)`

```java
public void recibirDanio(int danio) {
    vida -= danio;
    System.out.println("Recibiste " + danio + " puntos de daño. Vida restante: " + vida);
    if (vida <= 0) {
        System.out.println("Has muerto. Fin del juego.");
        System.exit(0);
    }
}
```

- **Reducir Vida**: Disminuye los puntos de vida del jugador.
- **Verificar Muerte**: Si la vida es 0 o menor, el juego termina.

##### `recogerItem(Item item)`

```java
public void recogerItem(Item item) {
    inventario.agregarItem(item);
    System.out.println("Has recogido un ítem: " + item.getNombre());
}
```

- **Agregar al Inventario**: Añade el objeto al inventario del jugador.
- **Mensaje**: Informa al jugador del objeto recogido.

##### `usarItem(int index)`

```java
public void usarItem(int index) {
    inventario.usarItem(index, this);
}
```

- **Uso de Objetos**: Permite usar un objeto del inventario en un índice específico.

##### `mostrarInventario()`

```java
public void mostrarInventario() {
    inventario.mostrarInventario();
}
```

- **Visualizar Inventario**: Muestra todos los objetos que el jugador posee.

#### Getters

- **`posX()` y `posY()`**: Retornan las coordenadas actuales del jugador.
- **`getVida()`**: Retorna los puntos de vida actuales.
- **`getAtaque()`**: Retorna los puntos de ataque actuales.
- **`getInventario()`**: Retorna el inventario del jugador.
- **`getContadorMapas()`**: Retorna el número de mapas completados.

### Resumen

La clase `Jugador` proporciona funcionalidades esenciales para manejar el personaje del jugador en el juego. Permite interactuar con el entorno, combatir enemigos, gestionar salud y ataque, y manejar un inventario de objetos. Todo esto se integra para crear una experiencia de juego interactiva y dinámica.

## COMO FUNCIONA LA CLASE ENEMIGOS???

Claro, vamos a desglosar la clase `Enemigos` paso a paso y en detalle.

### Paquetes Importados

- **`game.map.Mapa`**: Importa la clase `Mapa`, que maneja el entorno del juego.
- **`game.player.Jugador`**: Importa la clase `Jugador`, que representa al personaje controlado por el jugador.

### Clase `Enemigos`

La clase `Enemigos` es abstracta y define la estructura básica para los enemigos en el juego. Como es abstracta, no se pueden crear instancias directas de ella; debe ser extendida por otras clases concretas.

#### Atributos

- **`vida`**: Representa los puntos de vida del enemigo.
- **`ataque`**: Representa los puntos de ataque del enemigo.
- **`x` y `y`**: Coordenadas del enemigo en el mapa, indicando su posición actual.

#### Constructor

```java
public Enemigos(int vida, int ataque, int x, int y) {
    this.vida = vida;
    this.ataque = ataque;
    this.x = x;
    this.y = y;
}
```

- **Inicialización**: Se asignan los valores de vida, ataque y posición `(x, y)` al enemigo al ser creado.

#### Métodos

##### `atacar(Jugador jugador)`

```java
public void atacar(Jugador jugador) {
    System.out.println("El enemigo te ataca y causa " + ataque + " puntos de daño.");
    jugador.recibirDanio(ataque);
}
```

- **Ataque al Jugador**: El enemigo inflige daño al jugador equivalente a su valor de `ataque`.
- **Mensaje**: Informa al jugador del daño recibido.

##### `recibirDanio(int danio, Mapa mapa)`

```java
public void recibirDanio(int danio, Mapa mapa) {
    vida -= danio;
    System.out.println("El enemigo recibió " + danio + " puntos de daño. Vida restante: " + vida);
    if (vida <= 0) {
        System.out.println("El enemigo ha sido derrotado!");
        mapa.eliminarEnemigo(this);
    }
}
```

- **Reducción de Vida**: Disminuye la vida del enemigo por el daño recibido.
- **Verificación de Muerte**: Si la vida es 0 o menor, el enemigo es eliminado del mapa.
- **Mensaje**: Informa del daño recibido y si el enemigo ha sido derrotado.

#### Métodos para Coordenadas

- **`getX()` y `getY()`**: Retornan las coordenadas actuales del enemigo.
  
```java
public int getX() {
    return x;
}

public int getY() {
    return y;
}
```

- **`setPosicion(int x, int y)`**: Actualiza las coordenadas del enemigo.

```java
public void setPosicion(int x, int y) {
    this.x = x;
    this.y = y;
}
```

#### Otros Métodos

- **`getVida()`**: Retorna los puntos de vida actuales del enemigo.

```java
public int getVida() {
    return vida;
}
```

- **`getSimbolo()`**: Método abstracto que debe ser implementado por las subclases para definir cómo se representa el enemigo en el mapa. No tiene implementación aquí.

```java
public abstract char getSimbolo();
```

### Resumen

La clase `Enemigos` define la estructura básica para cualquier enemigo dentro del juego. Proporciona métodos para atacar al jugador, recibir daño, y manejar su posición en el mapa. Como es abstracta, se espera que otras clases concreten detalles específicos, como el símbolo que representa al enemigo en el mapa. Esto permite una fácil extensión y personalización para diferentes tipos de enemigos.

## COMO FUNCIONA LA CLASE ITEMS???

Vamos a desglosar la clase `Item` paso a paso y en detalle.

### Paquete Importado

- **`game.player.Jugador`**: Importa la clase `Jugador`, para interactuar con el jugador al usar un ítem.

### Clase `Item`

La clase `Item` es abstracta y define la estructura básica para los objetos dentro del juego. Al ser abstracta, no se pueden crear instancias directas de ella; debe ser extendida por otras clases concretas.

#### Atributos

- **`simbolo`**: Un carácter que representa el ítem en el mapa o interfaz.

#### Constructor

```java
public Item(char simbolo) {
    this.simbolo = simbolo;
}
```

- **Inicialización**: Asigna un símbolo al ítem al ser creado.

#### Métodos

##### `getSimbolo()`

```java
public char getSimbolo() {
    return simbolo; // Retornar el símbolo del ítem
}
```

- **Obtener Símbolo**: Devuelve el símbolo que representa al ítem.

##### `usar(Jugador jugador)`

```java
public abstract void usar(Jugador jugador);
```

- **Uso del Ítem**: Método abstracto que debe ser implementado por las subclases. Define cómo el ítem interactúa con el jugador cuando se usa.

##### `getNombre()`

```java
public abstract String getNombre();
```

- **Obtener Nombre**: Método abstracto que debe ser implementado por las subclases. Devuelve el nombre del ítem.

### Resumen

La clase `Item` establece una estructura básica para todos los objetos en el juego. Define un símbolo para su representación y métodos abstractos que las subclases deben implementar para especificar el uso y el nombre del ítem. Esto permite una fácil extensión y personalización para diferentes tipos de ítems, asegurando que cada uno tenga un comportamiento específico cuando se utiliza.
