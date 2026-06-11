package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class UsuariosTests {

    private Usuarios u1;
    private Usuarios u2;
    private Usuarios u3;
    private Usuarios igualAU1;

    @BeforeEach
    public void setUp() {
        u1 = new Usuarios(1, 100);
        u2 = new Usuarios(2, 200);
        u3 = new Usuarios(3, 100); // mismo saldo que u1
        igualAU1 = new Usuarios(1, 500); // mismo usuario (ID), diferente saldo
    }

    @Test
    public void comparar() {
        assertEquals(1, u1.usuario());
        assertEquals(100, u1.saldo());
    }

    @Test
    public void testSetSaldo() {
        u1.setSaldo(300);
        assertEquals(300, u1.saldo());
    }

    @Test
    public void testCompareToMayorSaldo() {
        assertTrue(u2.compareTo(u1) > 0);
        assertTrue(u1.compareTo(u2) < 0);
    }

    @Test
    public void testCompareToIgualSaldoMenorId() {
        assertTrue(u1.compareTo(u3) > 0);  // u1.id < u3.id → debería retornar 1
        assertTrue(u3.compareTo(u1) < 0);
    }

    @Test
    public void testCompareToIguales() {
        Usuarios otroU1 = new Usuarios(1, 100);
        assertEquals(0, u1.compareTo(otroU1));
    }

    @Test
    public void testEqualsPorId() {
        assertTrue(u1.equals(igualAU1)); // mismo id
        assertFalse(u1.equals(u2));
        
    }

    @Test
    public void testEqualsContraNullYDistintoTipo() {
        assertFalse(u1.equals(null));
        assertFalse(u1.equals("otro tipo"));
    }
}
