package aed;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HeapTests {
    private Heap<Integer> heap;

    @BeforeEach
    public void setUp() {
        heap = new Heap<>();
    }

    @Test
    public void testHeapVacio() {
        assertTrue(heap.esVacio());
        assertEquals(0, heap.cardinal());
    }

    @Test
    public void testInsertarYVerMax() {
        heap.insertar(10);
        assertFalse(heap.esVacio());
        assertEquals(1, heap.cardinal());
        assertEquals(10, heap.verMax());

        heap.insertar(20);
        assertEquals(2, heap.cardinal());
        assertEquals(20, heap.verMax());

        heap.insertar(5);
        assertEquals(3, heap.cardinal());
        assertEquals(20, heap.verMax());
    }

    @Test
    public void testEliminarMax() {
        heap.insertar(10);
        heap.insertar(20);
        heap.insertar(5);

        assertEquals(20, heap.eliminarMax());
        assertEquals(2, heap.cardinal());
        assertEquals(10, heap.verMax());

        assertEquals(10, heap.eliminarMax());
        assertEquals(1, heap.cardinal());
        assertEquals(5, heap.verMax());

        assertEquals(5, heap.eliminarMax());
        assertTrue(heap.esVacio());
    }

    @Test
    public void testHeapify() {
        ArrayList<Integer> lista = new ArrayList<>(Arrays.asList(3, 7, 1, 9, 2));
        heap.heapify(lista);
        assertEquals(5, heap.cardinal());
        assertEquals(9, heap.verMax());
    }

    @Test
    public void testActualizarMayor() {
        Heap.Handle<Integer> h1 = heap.insertar(10);

        heap.actualizar(h1, 25);
        assertEquals(25, heap.verMax());
        assertEquals(1, heap.cardinal());
    }

    @Test
    public void testActualizarMenor() {
        Heap.Handle<Integer> h1 = heap.insertar(50);

        heap.actualizar(h1, 10);
        assertEquals(10, heap.verMax());
        assertEquals(1, heap.cardinal());
    }


    @Test
    public void testIndicesYSwap() {
        Heap.Handle<Integer> h1 = heap.insertar(10);
        Heap.Handle<Integer> h2 = heap.insertar(20);

        assertEquals(0, h2.posicion());
        assertEquals(1, h1.posicion());
    }

    @Test
    public void testHijosYPadres() {
        assertEquals(1, heap.hijoizquierdo(0));
        assertEquals(2, heap.hijoderecho(0));
        assertEquals(0, heap.padre(1));
        assertEquals(0, heap.padre(2));
    }
}
