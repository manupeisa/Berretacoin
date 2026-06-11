package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TransaccionTests {

    private Transaccion tx1;
    private Transaccion tx2;
    private Transaccion tx3;
    private Transaccion txIgualA1;

    @BeforeEach
    public void setUp() {
        tx1 = new Transaccion(1, 10, 20, 100);
        tx2 = new Transaccion(2, 11, 21, 200);
        tx3 = new Transaccion(3, 12, 22, 100); // mismo monto que tx1 pero mayor id
        txIgualA1 = new Transaccion(1, 10, 20, 100); // igual a tx1
    }

    @Test
    public void testGetters() {
        assertEquals(1, tx1.id());
        assertEquals(10, tx1.id_comprador());
        assertEquals(20, tx1.id_vendedor());
        assertEquals(100, tx1.monto());
    }

    @Test
    public void testCompareToPorMonto() {
        assertTrue(tx2.compareTo(tx1) > 0);
        assertTrue(tx1.compareTo(tx2) < 0);
    }

    @Test
    public void testCompareToPorIdSiMontosIguales() {
        assertTrue(tx1.compareTo(tx3) < 0);
        assertTrue(tx3.compareTo(tx1) > 0);
    }

    @Test
    public void testCompareToIguales() {
        assertEquals(0, tx1.compareTo(txIgualA1));
    }

    @Test
    public void testEquals() {
        assertTrue(tx1.equals(txIgualA1));
        assertFalse(tx1.equals(tx2));
    }

    @Test
    public void testHashCodeDistintoSiNoEquals() {
        assertNotEquals(tx1.hashCode(), tx2.hashCode());
    }

    @Test
    public void testEqualsConNullYTipoDistinto() {
        assertFalse(tx1.equals(null));
        assertFalse(tx1.equals("No es una transacción"));
    }
}
