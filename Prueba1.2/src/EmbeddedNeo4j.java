package prueba.neo4j;

/*
*Ejemplo tomado de Neo4j
*link: https://github.com/neo4j/neo4j/blob/2.1.5/community/embedded-examples/src/main/java/org/neo4j/examples/EmbeddedNeo4j.java
*/

/*
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.io.File;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class EmbeddedNeo4j
{
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
    Relationship relationship, relationship2, relationship3;
    // END SNIPPET: vars

    // START SNIPPET: createReltype
    private static enum RelTypes implements RelationshipType
    {
        KNOWS
    }
    // END SNIPPET: createReltype

    public static void main( final String[] args )
    {
        EmbeddedNeo4j hello = new EmbeddedNeo4j();
        hello.createDb();
        hello.removeData();
        hello.shutDown();
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
            firstNode.setProperty( "persona", "Aaron " );
            secondNode = graphDb.createNode();
            secondNode.setProperty( "persona", "Kuk " );
            thirdNode = graphDb.createNode();
            thirdNode.setProperty( "persona", "Daniel " );
            fourthNode = graphDb.createNode();
            fourthNode.setProperty( "persona", "Alejandro " );
            fithNode = graphDb.createNode();
            fithNode.setProperty( "persona", "Chamo " );
            sixthNode = graphDb.createNode();
            sixthNode.setProperty( "persona", "Douglas " );

            relationship = firstNode.createRelationshipTo( fithNode, RelTypes.KNOWS );
            relationship.setProperty( "verbo", "estudia con " );
            
            relationship2 = thirdNode.createRelationshipTo( fourthNode, RelTypes.KNOWS );
            relationship2.setProperty( "verbo", "es amigo de " );
            
            relationship3 = sixthNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
            relationship3.setProperty( "verbo", "es profesor " );
                    
            // END SNIPPET: addData

            // START SNIPPET: readData
            System.out.print( firstNode.getProperty( "persona" ) );
            System.out.print( relationship.getProperty( "verbo" ) );
            System.out.print( fithNode.getProperty( "persona" ) );
            
            System.out.print("\n");
            
            System.out.print( thirdNode.getProperty( "persona" ) );
            System.out.print( relationship2.getProperty( "verbo" ) );
            System.out.print( fourthNode.getProperty( "persona" ) );

            System.out.print("\n");
            
            System.out.print( sixthNode.getProperty( "persona" ) );
            System.out.print( relationship3.getProperty( "verbo" ) );
            System.out.print( secondNode.getProperty( "persona" ) );
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
            firstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
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
