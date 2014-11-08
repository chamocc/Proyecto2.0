/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ed. Chamo
 */
import java.io.File;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class TraficoGT {
    
private static final String DB_PATH = "target/neo4j-hello-db";

    public String greeting;

    // START SNIPPET: vars
    GraphDatabaseService graphDb;
    Node firstNode;
    Node secondNode;
    Node thirdNode;
    Node fourthNode;
    Node fithNode;
    Node sixthNode;
    Relationship relationship12, relationship13, relationship14, relationship21, relationship23, relationship24, relationship31, relationship32, relationship34, relationship41, relationship42, relationship43;
    // END SNIPPET: vars

    // START SNIPPET: createReltype
    private static enum RelTypes implements RelationshipType
    {
        KNOWS
    }
    // END SNIPPET: createReltype

    public static void main( final String[] args )
    {
        TraficoGT grafo = new TraficoGT();
        grafo.createDb();
        grafo.removeData();
        grafo.shutDown();
    }
    
    void createDb()
    {
        deleteFileOrDirectory( new File( DB_PATH ) );
        // START SNIPPET: startDb
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
        registerShutdownHook( graphDb );
        // END SNIPPET: startDb

        // START SNIPPET: transaction
        try ( Transaction tx = graphDb.beginTx() )
        {
            // Database operations go here
            // END SNIPPET: transaction
            // START SNIPPET: addData
            firstNode = graphDb.createNode();
            firstNode.setProperty( "persona", "Cuarto#1 ");
            secondNode = graphDb.createNode();
            secondNode.setProperty( "persona", "Cuarto#2 " );
            thirdNode = graphDb.createNode();
            thirdNode.setProperty( "persona", "Cuarto#3 " );
            fourthNode = graphDb.createNode();
            fourthNode.setProperty( "persona", "Cuarto#4 " );
            fithNode = graphDb.createNode();
            
            relationship12 = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
            relationship12.setProperty( "distancia", "10 " );
            relationship13 = firstNode.createRelationshipTo( thirdNode, RelTypes.KNOWS );
            relationship13.setProperty( "distancia", "5 " );
            relationship14 = firstNode.createRelationshipTo( fourthNode, RelTypes.KNOWS );
            relationship14.setProperty( "distancia", "7 " );
            
            relationship21 = secondNode.createRelationshipTo( firstNode, RelTypes.KNOWS );
            relationship21.setProperty( "distancia", "20 " );
            relationship23 = secondNode.createRelationshipTo( thirdNode, RelTypes.KNOWS );
            relationship23.setProperty( "distancia", "23 " );
            relationship24 = secondNode.createRelationshipTo( fourthNode, RelTypes.KNOWS );
            relationship24.setProperty( "distancia", "21 " );
            
           
            relationship31 = thirdNode.createRelationshipTo( firstNode, RelTypes.KNOWS );
            relationship31.setProperty( "distancia", "30 " );
            relationship32 = thirdNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
            relationship32.setProperty( "distancia", "35 " );
            relationship34 = thirdNode.createRelationshipTo( fourthNode, RelTypes.KNOWS );
            relationship34.setProperty( "distancia", "33 " );
                    
            relationship41 = fourthNode.createRelationshipTo( firstNode, RelTypes.KNOWS );
            relationship41.setProperty( "distancia", "40 " );
            relationship42 = fourthNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
            relationship42.setProperty( "distancia", "45 " );
            relationship43 = fourthNode.createRelationshipTo( thirdNode, RelTypes.KNOWS );
            relationship43.setProperty( "distancia", "43 " );
            // END SNIPPET: addData

            // START SNIPPET: readData
            System.out.print( firstNode.getProperty( "persona" ) );
            System.out.print( relationship12.getProperty( "distancia" ) );
            
            
            System.out.print("\n");

            // END SNIPPET: readData


            // START SNIPPET: transaction
            tx.success();
        }
        // END SNIPPET: transaction
    }
    
     void removeData()
    {
        try ( Transaction tx = graphDb.beginTx() )
        {
            // START SNIPPET: removingData
            // let's remove the data
           // firstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            thirdNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            sixthNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            firstNode.delete();
            secondNode.delete();
            thirdNode.delete();
            fourthNode.delete();
            fithNode.delete();
            sixthNode.delete();
            
            // END SNIPPET: removingData

            tx.success();
        }
    }

    void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // START SNIPPET: shutdownServer
        graphDb.shutdown();
        // END SNIPPET: shutdownServer
    }

    // START SNIPPET: shutdownHook
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    // END SNIPPET: shutdownHook

    private static void deleteFileOrDirectory( File file )
    {
        if ( file.exists() )
        {
            if ( file.isDirectory() )
            {
                for ( File child : file.listFiles() )
                {
                    deleteFileOrDirectory( child );
                }
            }
            file.delete();
        }
    }
    
}
