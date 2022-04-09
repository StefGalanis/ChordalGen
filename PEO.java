import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PEO{
    private ArrayList<ArrayList<Integer> > adjList;
    private ArrayList<Integer> vertexList;
    private Integer vertexCounter;
    
    public PEO(){
        this.adjList = new ArrayList<ArrayList<Integer>>();
        ArrayList edge = new ArrayList();
        Integer[] spam = new Integer[] { 0, 1, 2 };
        Integer[] adj0 = new Integer[] {1};
        adjList.add(new ArrayList(Arrays.asList(adj0)));
        Integer[] adj1 = new Integer[] {2};
        adjList.add(new ArrayList(Arrays.asList(adj1)));
        Integer[] adj2 = new Integer[] {0};
        adjList.add(new ArrayList(Arrays.asList(adj2)));
        this.vertexCounter = 3;
        this.vertexList = new ArrayList(Arrays.asList(spam));
        // System.out.println(adjList);
        // System.out.println(adjList.get(0));
    }

    private void addVertex(){
        ArrayList<Integer> neighbors = generateNeighbors();
        vertexList.add(vertexCounter);
        adjList.add(neighbors);
        for (int i=0; i<neighbors.size(); i++){
            int neighborName = neighbors.get(i);
            adjList.get(neighborName).add(vertexCounter);
        }
        System.out.println(adjList);
        for (int i=0; i<neighbors.size(); i++){
            int neighborName2 = neighbors.get(i);
            for (int j=0; j<neighbors.size(); j++){
                int neighborName = neighbors.get(j);
                if (neighborName != neighborName2){
                    if (!adjList.get(neighborName2).contains(neighborName)){
                        adjList.get(neighborName2).add(neighborName);
                    }
                }
            }
        }
        System.out.println(adjList);
        vertexCounter++;
    }

    private ArrayList<Integer> generateNeighbors(){
        //Integer[] spam = new Integer[] { 0, 1, 2, 3, 4 };
        //ArrayList<Integer> vertexList = new ArrayList(Arrays.asList(spam));
        Random rnd = new Random();
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        int numberOfNeighbors = rnd.nextInt(vertexList.size()-1)+1;
        System.out.println("number of neighbors \t" + numberOfNeighbors);
        Integer [] tempVertexList = vertexList.toArray(new Integer[vertexList.size()]);
        Integer arraySize = tempVertexList.length; 
        int currentPos = 0;
        for (int i=0; i<numberOfNeighbors; i++){
            int elementPosition = rnd.nextInt(arraySize - currentPos)+currentPos;  
            neighbors.add(tempVertexList[elementPosition]);
            Integer swap = tempVertexList[elementPosition];
            tempVertexList[elementPosition] = tempVertexList[currentPos];
            tempVertexList[currentPos] = swap;
            currentPos++;
        }
        System.out.println("neighbors \t" + neighbors.toString());
        return neighbors;
    }


    
    public static void main(String args[]){
        PEO object = new PEO();
        object.addVertex();
    }
}