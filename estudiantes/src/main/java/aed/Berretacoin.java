package aed;

import java.util.ArrayList;  
// Importo la calse predefinida ArrayList que esta permitida para el tp 

public class Berretacoin<T extends Comparable<T>> {
    private Heap<Transaccion> ultimo_bloque; //Guardo el ultimo bloque de mi cadena de bloques en un Heap 
    private Heap<Usuarios> usuarios; // Guardo los usuarios y el saldo que tiene cada uno 
    private int monto_bloque; // Guardo el Monto de todas las transaciones del bloque eceptuando las de creacion 
    private int cant_bloques; // Cuento la cantidad de bloques que hay en todo el Berretacoin, asi me fijo los bloques de creacion (bloques < 3000)

    private ListaEnlazada<Transaccion> transacciones_copia;
    private int[]  saldo; // 
    private ArrayList<Handle<ListaEnlazada.Nodo>> lista_de_handles;

   
    public static class Handle<Nodo> {
        private Nodo valor;
        
        public Handle(Nodo valor) {
            this.valor = valor;
        }

        public Nodo valor() {
            return valor;
        }

        public void actualizarValor(Nodo nuevoValor) {
            this.valor = nuevoValor;
        }
    }

    public Berretacoin(int n_usuarios) {// Loop de O(p) + heapify O(p) = O(p)
        this.monto_bloque = 0; // Al no haber bloques todavia, no hay transaciones por lo que el monto es cero 
        this.cant_bloques = 0; // Al arrancar mi Berretacoin mi cant bloques es cero 
        ArrayList<Usuarios> lista_usuarios = new ArrayList<>();
        this.saldo = new int[n_usuarios];
        for (int i = 0; i < n_usuarios; i++){ //O(p)
            Usuarios user = new Usuarios(i+1, 0); //O(1)
            lista_usuarios.add(user);
        }
        this.usuarios = new Heap<>(); // Inicio mi heap Usarios, donde voy a guardar el saldo de cada usuario, O(1)
        usuarios.heapify(lista_usuarios); // Convierto mi lista_usuarios en un heap con complejidad Linneal Tq = O(p)
    }

    public void agregarBloque(Transaccion[] transacciones) {//Itera nb transacciones, y hace heap.actualizar O(log p) dos veces por transacción
    this.ultimo_bloque = new Heap<>(); // Nuevo bloque por lo que borro el anterior heap y inicio denuevo, O(1)
    this.monto_bloque = 0; // Arranco un nuevo bloque por lo que reinicio el monto bloque 
    ArrayList<Transaccion> trans = new ArrayList<>(); // Creo un ArrayList para guardar todas las transaciones del bloque, O(1)
    this.transacciones_copia = new ListaEnlazada<>();     
    this.lista_de_handles = new ArrayList<Handle<ListaEnlazada.Nodo>>(transacciones.length);
    
    for (int i = 0; i < transacciones.length; i++) { //O(nb)
        Transaccion tx = transacciones[i]; //O(1)
        int comprador = tx.id_comprador(); //O(1)
        int vendedor = tx.id_vendedor(); //O(1)
        int monto = tx.monto(); //O(1)


        ListaEnlazada.Nodo nodo = transacciones_copia.agregarAtrasAux(tx);
        lista_de_handles.add(new Handle<ListaEnlazada.Nodo>(nodo));


        // Actualizar el saldo del comprador
        if (comprador > 0) { // Si el comprador no es el sistema (id_comprador == 0) //O(1)
            Usuarios handle_c = new Usuarios(comprador, saldo[comprador-1]); //O(1)
            Heap.Handle<Usuarios> handleComprador = usuarios.getHandle(handle_c); // Obtener el handle del comprador //O(1)
            Usuarios usuarioComprador = handleComprador.valor(); // Obtengo el monto del comprador en el handle //O(1)
            Usuarios actualizado_c = new Usuarios(usuarioComprador.usuario(), usuarioComprador.saldo() - monto); //O(1)
            usuarios.actualizar(handleComprador, actualizado_c); // Reordenar el heap con el nuevo monto del comprador //O(log p)
            saldo[comprador-1] -= monto; //O(1)
        }  


        // Actualizar el saldo del vendedor
        Usuarios handle_v = new Usuarios(vendedor, saldo[vendedor-1]); //O(1)
        Heap.Handle<Usuarios> handleVendedor = usuarios.getHandle(handle_v); // Obtener el handle del vendedor //O(1)
        Usuarios usuarioVendedor = handleVendedor.valor(); // Obtengo el monto del vendedor en el handle //O(1)
        //usuarioVendedor.setSaldo(usuarioVendedor.saldo() + monto); // Actualizo el monto vendedor 
        Usuarios actualizado_v = new Usuarios(usuarioVendedor.usuario(), usuarioVendedor.saldo() + monto); //O(1)
        usuarios.actualizar(handleVendedor, actualizado_v); // Reordenar el heap con el nuevo monto del vendedor //O(log p)

        saldo[vendedor-1] += monto; //O(1)
         

        // Agregar la transacción al heap del último bloque
        trans.add(tx); //O(1)
        if(cant_bloques >= 3000 && comprador != 0){ // Si la cantidad de bloques es mayor a 3000 entonces no hay mas transaccion de creacion 
                                 // por lo que sumo todas las transaciones del bloque 
                monto_bloque += transacciones[i].monto(); //O(1)
            }
    }   

    
    if(cant_bloques < 3000){ // Es bloque de creacion, por lo que sumo todas las transaciones menos la primera 
            for(int p = 1; p < transacciones.length; p++){ //O(n-1)
                monto_bloque += transacciones[p].monto() ;
            }
        }

    // Construir el heap del último bloque
    this.ultimo_bloque.heapify(trans); // Convierto mi trans en un heap con complejidad Linneal tq :  O(n)
    this.cant_bloques++; // Sumo 1 a la cantidad de bloques de mi Berretacoin //O(1)
    
    }


    public Transaccion txMayorValorUltimoBloque(){ //Devuelve raíz del heap ultimo_bloque, O(1)
        return this.ultimo_bloque.verMax(); // Devuelvo el maximo del heap ultimo_bloque que es la transaccion de mayor monto 
    }
    
    public Transaccion[] txUltimoBloque(){//Recorre la lista enlazada una sola vez, O(nb)
     
       Transaccion[] res = new Transaccion[transacciones_copia.longitud()];
       ListaEnlazada<Transaccion>.Nodo actual = transacciones_copia.primeroNodo();
       int i = 0;
       while(actual != null){
        res[i] = actual.valor;
        actual = actual.sig;
        i ++;
       }
       
        return res; 
    }

    public int maximoTenedor(){//Devuelve el máximo del heap de usuarios, O(1)
        Usuarios maximo_usuario = this.usuarios.verMax(); // Guardo el id_usuario  y el saldo del usuario con mayor saldo 
        return maximo_usuario.usuario(); // Devuelvo el id del usuario con mayor saldo 
    }

    public int montoMedioUltimoBloque(){//Accede a una suma ya calculada y la divide, O(1)
        int cardinal = ultimo_bloque.cardinal();
        if (cardinal == 0){//O(1)
            return 0;
        }
        else if(cant_bloques < 3000 && cardinal > 1){  // Me fijo si es bloque de craeación para saber por cuanto dividir //O(1)
                return this.monto_bloque / (cardinal - 1);
            }
        else{ //O(1)
                return this.monto_bloque / cardinal; // divido la suma de los montos de las transacciones del ultimo bloque dividido
                                                     // la cantidad de transacciones correspondiente
            }
    }

    public void hackearTx() { // Elimina max del heap (O(log nb)) y actualiza dos heaps (O(log p) c/u)

        Transaccion maximo = ultimo_bloque.verMax(); // O(1)
        ultimo_bloque.eliminarMax(); // O(log nb)

        if (maximo.id_comprador() > 0) {

            Usuarios hande_c = new Usuarios(maximo.id_comprador(), saldo[(maximo.id_comprador())-1]);
            Heap.Handle<Usuarios> handleComprador = usuarios.getHandle(hande_c);
            Usuarios usuario_comprador = handleComprador.valor();
            Usuarios actualizado_c = new Usuarios(usuario_comprador.usuario(), usuario_comprador.saldo() + maximo.monto());

            usuarios.actualizar(handleComprador, actualizado_c); // O(log p)

            saldo[(maximo.id_comprador())-1] += maximo.monto();
        }

        Usuarios hande_v = new Usuarios(maximo.id_vendedor(), saldo[(maximo.id_vendedor())-1]);
        Heap.Handle<Usuarios> handleVendedor = usuarios.getHandle(hande_v);
        Usuarios usuario_vendedor = handleVendedor.valor();
        Usuarios actualizado_v = new Usuarios(usuario_vendedor.usuario(), usuario_vendedor.saldo() - maximo.monto());
        usuarios.actualizar(handleVendedor, actualizado_v); // O(log p)
        saldo[(maximo.id_vendedor())-1] -= maximo.monto();

        monto_bloque -= maximo.monto();

        Handle<ListaEnlazada.Nodo> handleDelNodo = lista_de_handles.get(maximo.id());
        ListaEnlazada.Nodo nodoAeliminar = handleDelNodo.valor();
        transacciones_copia.eliminarPorNodo(nodoAeliminar);
    }
} 


/* Complejidad de cada operacion:

1-nuevoBerretacoin O(p) :
La complejidad es igual a la cantidad de usuarios, que ingresa como parametro de entrada. Se forma a partir de un for, con una variable 'i' 
que arranca en i=0 hasta i= n_usuarios. 
Y dado que tengo un hepify, este transforma mi lista en heap de forma lineal por lo que esto tambien es O(P)
Total: for = O(p) + hepify = O(p)  tq me queda O(2P) = O(P).

2-agregarBloque O(nb * log P) :  
Dada la complejidad nosotros planteamos un for que va a recorrer desde 0 hasta la cantidad de trsnsacion que haya, por lo que 
esto va ser nb (Cant. transaciones en el bloque) y como dentro del for vamos actualizando el heap Usuarios entonces en el peor caso de cada 
actualizacion del saldo de usuario es log P ya que dentro del heap usuarios si mi usuario era el mayor y se le saco el monto entonces ahora
debo reordenarlo y en el peor caso este sera log P 
Como todo esto lo hacemos en un bucle con nb iteracion entonces como resultado me queda  O(nb * log P)

3-txMayorValorUltimoBloque O(1):
Simplemente devuelve una referencia ya calculada y almacenada en el heap ultimo_bloque, por lo que la complejidad es constante.
Ya que devuelve el maximo que al ser siempre el de indice 0 es O(1) 

4-txUltimoBloque O(nb):
Dado que nosotros nos guardamos como atributo una ArrayList<Transaccion> lo unico que hacemos en el txUltimoBloque
es un for que va desde 0 hasta la longitud de mi ArrayList que lo que hace es pasar mi ArrayList a un lista del tipo Transaccion[]

5-maximoTenedor O(1):
Solo devuelve el identificador del usuario con mayor saldo, que se encuentra guardado en el heap usuarios al ser siempre el primero de mi heap 
entonces siempre se que va a estar ubicado en el indice 0 tal que la complejidad de mi programa va a ser simpre O(1)

6-montoMedioUltimoBloque O(1):
En esta operacion lo unico que hacemos es realizar la division que nos devuelve el resultado del promedio pedido. Creamos como atributo privado
de la clase, la suma de los montos de las transacciones del ultimo bloque, que se llama 'monto_bloque', calculamos esa suma que requeria una 
iteración, en otra funcion, en agregarBloque; entonces, al ya tenerla calculada, en montoMedioUltimoBloque solo nos quedaba realizar la division
(que es una operacion de O(1)) entre esa suma y el cardinal del bloque, dependiendo si es de creacion o no.

7-hackearTx() O(log nb+ log P):
Este método tarda O(log nb+log P) porque primero saca la transaccion de mayor valor del ultimo bloque usando el heap (esto cuesta O(1)) 
y luego la elimino que eso me cuesta O(log nb) ya que debo reordenar mi heap. Luego cambio el saldo de los usuarios involucrados en la 
trasaccion (maximo 2 ya que puede ser de creacion) y al volver a ubicarlos en el heap que mantiene el ranking de saldos, cada “mover” cuesta 
log P, así que entre los dos suman O(2log P) = O(log p). 
Todo lo demás (sumar y restar saldos en un arreglo,  recalcular el promedio, actualizar el puntero al nuevo máximo, etc.) valen O(1), por lo 
tanto, la complejidad total de hackearTx es O(log nb + log P).

 */