# Berretacoin

Trabajo Práctico 2 — Algoritmos y Estructuras de Datos (UBA)

Simulación de una criptomoneda simplificada con cadena de bloques, implementada en Java usando estructuras de datos propias.

---

## Descripcion

Berretacoin modela un sistema de transacciones en bloques al estilo blockchain. Cada bloque contiene un conjunto de transacciones entre usuarios. El sistema permite agregar bloques, consultar estadísticas del último bloque, identificar al usuario con mayor saldo y eliminar la transacción de mayor valor ("hackear").

---

## Estructuras de datos implementadas

| Estructura | Archivo | Descripcion |
|---|---|---|
| `Heap<T>` | [Heap.java](estudiantes/src/main/java/aed/Heap.java) | Max-heap genérico con handles para acceso y actualización en O(log n) |
| `ListaEnlazada<T>` | [ListaEnlazada.java](estudiantes/src/main/java/aed/ListaEnlazada.java) | Lista doblemente enlazada con iterador bidireccional |
| `Berretacoin<T>` | [Berretacoin.java](estudiantes/src/main/java/aed/Berretacoin.java) | Sistema principal que orquesta los heaps de transacciones y usuarios |

---

## Operaciones y complejidades

| Operacion | Complejidad | Descripcion |
|---|---|---|
| `new Berretacoin(p)` | O(p) | Inicializa p usuarios con saldo 0 usando heapify |
| `agregarBloque(txs)` | O(nb · log p) | Procesa nb transacciones actualizando el heap de usuarios |
| `txMayorValorUltimoBloque()` | O(1) | Devuelve la raíz del heap del último bloque |
| `txUltimoBloque()` | O(nb) | Recorre la lista enlazada del bloque actual |
| `maximoTenedor()` | O(1) | Devuelve el id del usuario con mayor saldo (raíz del heap) |
| `montoMedioUltimoBloque()` | O(1) | División sobre suma ya calculada en `agregarBloque` |
| `hackearTx()` | O(log nb + log p) | Elimina el max del heap de transacciones y actualiza saldos |

**Variables:** `nb` = cantidad de transacciones en el bloque, `p` = cantidad de usuarios.

---

## Diseño

- El **heap de usuarios** mantiene siempre el usuario con mayor saldo en la raíz → `maximoTenedor()` en O(1).
- El **heap del último bloque** permite acceso y eliminación del max en O(log nb).
- Los **handles** del heap permiten actualizar un elemento en posición arbitraria en O(log n) sin necesidad de buscarlo.
- La **lista enlazada** con punteros directos a nodos permite eliminar una transacción hackeada en O(1) (sin búsqueda).
- El `monto_bloque` se calcula de forma incremental dentro de `agregarBloque`, evitando iteraciones adicionales en `montoMedioUltimoBloque`.
- Los bloques de creación (primeros 3000) tienen una transacción especial del sistema (id_comprador = 0) que se excluye del promedio.

---

## Tecnologias

- **Java 8**
- **Maven** como build tool
- **JUnit 5** para tests

---

## Compilar y ejecutar tests

```bash
cd estudiantes
mvn test
```

---

## Estructura del proyecto

```
tp2/
└── estudiantes/
    ├── pom.xml
    └── src/
        ├── main/java/aed/
        │   ├── Berretacoin.java   # Clase principal
        │   ├── Heap.java          # Max-heap con handles
        │   ├── ListaEnlazada.java # Lista doblemente enlazada
        │   ├── Transaccion.java   # Modelo de transaccion
        │   ├── Usuarios.java      # Modelo de usuario con saldo
        │   ├── Handle.java        # Interfaz de handle
        │   ├── Secuencia.java     # Interfaz de secuencia
        │   └── Iterador.java      # Interfaz de iterador
        └── test/java/aed/
            ├── BerretacoinTests.java
            ├── HeapTests.java
            ├── ListaEnlazadaTests.java
            ├── TransaccionTests.java
            ├── UsuariosTests.java
            ├── HandleTests.java
            └── TestPropios.java   # Tests adicionales
```
