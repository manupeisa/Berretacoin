package aed;

public class Usuarios implements Comparable<Usuarios> {
    private int usuario;
    private int saldo;

    public Usuarios (int us, int plata){
        this.usuario = us;
        this.saldo = plata;
    }

    public int usuario(){
        return usuario;
    }

    public int saldo(){
        return saldo;
    }

    public void setSaldo(int nuevoSaldo) {
        this.saldo = nuevoSaldo;
    }


    @Override
    public int compareTo(Usuarios otro){
        if(this.saldo > otro.saldo){
            return 1 ;
        }

        else if( this.saldo == otro.saldo){
            if(this.usuario < otro.usuario){
                return 1;
            }
            else if (this.usuario > otro.usuario){
                return -1;
            }
            else {
                return 0;
            }
        }
        else {
            return -1;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) 
            return false;

        Usuarios otro = (Usuarios) o;

            return this.usuario() == otro.usuario(); // comparás solo por ID
    }
}
