import Vertex;
import java.util.Random;

public class Graph{

    private ArrayList<Vertex> Vertices;
    private int vertexCounter;
    private Random randomGenerator;

    public Graph(){
        this.randomGenerator = new Random();
        Vertex newVertex = new Vertex("0");
        this.Vertices.add(newVertex);
        this.vertexCounter = 1;
    }

    public void addNewVertex(){

    }

    public ArrayList<Vertex> findRandomClique(){
        numberOfVertices = this.Vertices.size();
        int randomIndex = randomGenerator.nextInt(numberOfVertices);
        Vertex randomVertex = this.Vertices.get(randomIndex);
        int cliqueSize = randomGenerator.nextInt
        for (int i=0; i<numberOfVertices; i++){

        }
        return randomClique;
    }

    public void makeAdjacent(){

    }

    public static void main(String args[]){
        Graph graph = new Graph();
        // ArrayList<Vertex> adjacencyList1 = findRandomClique();
        // Vertex vertex1 = new Vertex("1",adjacencyList1);
    }
}