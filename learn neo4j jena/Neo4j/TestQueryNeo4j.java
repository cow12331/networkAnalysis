import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class TestQueryNeo4j {

    public static void main(String[] args) throws Exception {
        final GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase("target/twitter-db");
        final Transaction tx = graphDb.beginTx();
        final Node node = graphDb.createNode();
        final ExecutionEngine engine = new ExecutionEngine(graphDb);
        long startTime=System.currentTimeMillis();
        ExecutionResult result = engine.execute("START n=node(*) RETURN n.id");
        //ExecutionResult result = engine.execute("MATCH (twitter)-[:HASFOLLOW]-(followers) WHERE twitter.id = '1' RETURN followers.id ");
        long endTime=System.currentTimeMillis();
        System.out.println(result.dumpToString());
        System.out.println("select time "+(endTime-startTime)+"ms");
        //System.out.println(engine.execute("START n=node(*) RETURN n.id").dumpToString());
        graphDb.shutdown();
    }

}