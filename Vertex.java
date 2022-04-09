import java.util.ArrayList;
import java.util.Arrays;

public class Vertex {

    private String name;
    private ArrayList<String> adjacencyList;

    public Vertex(String name){
        this.name = name;
        this.adjacencyList = new ArrayList<String>();
    }

    public Vertex(String name,String [] adjacencyList){
        this.name = name;
        // this.adjacencyList = new ArrayList<string>;
        this.adjacencyList = Arrays.asList(adjacencyList);
    }

    public Vertex(String name,ArrayList<String> adjacencyList){//lista me tis tyxaies koryfes poy tyxaia epileksame gia geitonikes
        this.name = name;
        this.adjacencyList = adjacencyList;
    }

}
