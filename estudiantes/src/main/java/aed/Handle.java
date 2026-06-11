package aed;


public class Handle<T extends Comparable<T>> {
    private T informacion;
    private int indice;

    public Handle(T informacion, int indice) {
        this.informacion = informacion;
        this.indice = indice;
    }
    public Handle(T informacion) {
        this.informacion = informacion;
    }    

    public T getInformacion() {
        return informacion;
    }
    
    public boolean esVacia(){
        if (informacion == null){
            return true;
        }
        return false;
    }
    
    public void setInformacion(T informacion) {
        this.informacion = informacion;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

}