/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ed. Chamo
 */
public class Main {
        public static void main( final String[] args )
    {
        TraficoGT grafo = new TraficoGT();
        grafo.createDb();
        grafo.removeData();
        grafo.shutDown();
    }
    
}
