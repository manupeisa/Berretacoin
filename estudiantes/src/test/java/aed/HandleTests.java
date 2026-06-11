package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HandleTests {

    private Handle<Integer> handleInt;
    private Handle<String> handleStr;

    @BeforeEach
    public void setUp() {
        handleInt = new Handle<>(42, 0);
        handleStr = new Handle<>("dato", 5);
    }

    @Test
    public void testGetInformacion() {
        assertEquals(42, handleInt.getInformacion());
        assertEquals("dato", handleStr.getInformacion());
    }

    @Test
    public void testGetIndice() {
        assertEquals(0, handleInt.getIndice());
        assertEquals(5, handleStr.getIndice());
    }

    @Test
    public void testSetInformacion() {
        handleInt.setInformacion(99);
        assertEquals(99, handleInt.getInformacion());

        handleStr.setInformacion("nuevo");
        assertEquals("nuevo", handleStr.getInformacion());
    }

    @Test
    public void testSetIndice() {
        handleInt.setIndice(7);
        assertEquals(7, handleInt.getIndice());

        handleStr.setIndice(2);
        assertEquals(2, handleStr.getIndice());
    }

    @Test
    public void testEsVacia() {
        Handle<Integer> vacio = new Handle<>(null, 1);
        assertTrue(vacio.esVacia());

        assertFalse(handleInt.esVacia());
    }
}

