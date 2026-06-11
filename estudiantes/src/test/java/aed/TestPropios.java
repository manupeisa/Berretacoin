package aed;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestPropios {
    private Berretacoin berretacoin;
    
@BeforeEach
void init() {
    berretacoin = new Berretacoin(10);
}

@Test
public void bloqueVacioNoRevienta() {
    Transaccion[] bloque = new Transaccion[0];
    berretacoin.agregarBloque(bloque);

    assertEquals(1, berretacoin.maximoTenedor()); // todos tienen 0 saldo
    assertEquals(0, berretacoin.montoMedioUltimoBloque()); // Saldo_bloque = 0  Tal que monto medio = 0
    assertEquals(0, berretacoin.txUltimoBloque().length);
}

@Test
public void bloqueSinCreacionSoloReales() {
    berretacoin = new Berretacoin(5);

    // Llego a bloque 3000 para habilitar bloques sin creación
    for (int i = 0; i < 3000; i++) {
        berretacoin.agregarBloque(new Transaccion[] {});
    }

    Transaccion[] bloque = new Transaccion[] {
        new Transaccion(0, 1, 2, 3), // 
        new Transaccion(1, 4, 5, 2), // 
        new Transaccion(2, 2, 3, 3) //
        //  Usuario 1 = -3$ Usuario 2 = 0$ Usuario 3 = 3$  Usuario 4 = -2$ Usuario 5= 2$ 
    };

    berretacoin.agregarBloque(bloque);

    assertEquals(3, berretacoin.maximoTenedor()); // 3 recibió 2, 2 queda en 2
    assertEquals(2, berretacoin.montoMedioUltimoBloque()); // (4+2)/2
    berretacoin.hackearTx(); //  Usuario 1 = -3$ Usuario 2 = 3$ Usuario 3 = -3$  Usuario 4 = -2$ Usuario 5= 2$ 
    berretacoin.hackearTx(); //  Usuario 1 = 0$ Usuario 2 = 0$ Usuario 3 = 0$  Usuario 4 = -2$ Usuario 5= 2$ 
    assertEquals(5, berretacoin.maximoTenedor()); // 
    assertEquals(2, berretacoin.montoMedioUltimoBloque()); 
    berretacoin.hackearTx(); //  Usuario 1 = 0$ Usuario 2 = 0$ Usuario 3 = 0$  Usuario 4 = 0$ Usuario 5= 0$ 
    assertEquals(1, berretacoin.maximoTenedor());
    assertEquals(0, berretacoin.montoMedioUltimoBloque()); 
}


    @Test
public void ultimoBloqueVacío() {
    
    Transaccion[] bloque = new Transaccion[]{};
    berretacoin.agregarBloque(bloque);
    Transaccion[] ultBloque = new Transaccion[berretacoin.txUltimoBloque().length];

    berretacoin.txUltimoBloque();
    assertEquals(ultBloque.length, bloque.length);
}

@Test
public void mismoCompradorMultiplesTransacciones() {
    // Llego a bloque 3000 para habilitar bloques sin creación
    for (int i = 0; i < 3000; i++) {
        berretacoin.agregarBloque(new Transaccion[] {});
    }
    Transaccion[] bloque = {
        new Transaccion(0, 7, 1, 20), 
        new Transaccion(1, 1, 2, 5),
        new Transaccion(2, 1, 3, 5),
        new Transaccion(3, 1, 4, 5)
    };
    
    berretacoin.agregarBloque(bloque);
    assertEquals(8, berretacoin.montoMedioUltimoBloque()); // 35 / 4 me da 8.75 = 8 ?
    assertEquals(1, berretacoin.maximoTenedor()); // le quedan $5

    Transaccion max = new Transaccion(0, 7, 1, 20);
    
    assertEquals(max, berretacoin.txMayorValorUltimoBloque());
    
    Transaccion[] copia = berretacoin.txUltimoBloque();

    assertTrue(Arrays.equals(copia,bloque));    
    }
}


