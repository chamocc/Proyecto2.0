/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class Nodo<E> {
    private boolean visitado, enCola;
    private E dato;
    
    public Nodo(E dato){
        visitado=false;
        enCola=false;
        this.dato=dato;
    }
    
    public E getDato(){
        return dato;
    }
    
    public void setDato(E dato){
        this.dato=dato;
    }
    
    public boolean estaEnCola(){
        return enCola;
    }
    
    public boolean estaVisitado(){
        return visitado;
    }
}
