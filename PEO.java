import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PEO{
    private ArrayList<ArrayList<Integer> > adjList;
    private ArrayList<Integer> vertexList;
    private Integer vertexCounter;
    private ArrayList<ArrayList<Integer> > additionalEdges;
    
    public PEO(){
        this.adjList = new ArrayList<ArrayList<Integer>>();
        this.additionalEdges = new ArrayList<ArrayList<Integer>>();
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

    public void addVertex(){
        ArrayList<Integer> neighbors = generateNeighbors();
        vertexList.add(vertexCounter);
        adjList.add(neighbors);
        for (int i=0; i<neighbors.size(); i++){
            int neighborName = neighbors.get(i);
            adjList.get(neighborName).add(vertexCounter);
        }
        // System.out.println(adjList);
        for (int i=0; i<neighbors.size(); i++){
            int neighborName2 = neighbors.get(i);
            for (int j=0; j<neighbors.size(); j++){
                int neighborName = neighbors.get(j);
                if (neighborName != neighborName2){
                    if (!adjList.get(neighborName2).contains(neighborName)){
                        adjList.get(neighborName2).add(neighborName);
                        // ArrayList<Integer> edge = new ArrayList<Integer>({neighborName2,neighborName});
                        Integer [] edge = new Integer[] {neighborName2,neighborName};
                        additionalEdges.add(new ArrayList(Arrays.asList(edge)));
                    }
                }
            }
        }
        // System.out.println(adjList);
        vertexCounter++;
    }



    public ArrayList<Integer> generateNeighbors(){
        //Integer[] spam = new Integer[] { 0, 1, 2, 3, 4 };
        //ArrayList<Integer> vertexList = new ArrayList(Arrays.asList(spam));
        Random rnd = new Random();
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        int numberOfNeighbors = rnd.nextInt(vertexList.size()-1)+1;
        // System.out.println("number of neighbors \t" + numberOfNeighbors);
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
        // System.out.println("neighbors \t" + neighbors.toString());
        return neighbors;
    }

    public void printAdditionalEdges(){
        System.out.println("Additional Edges: " + this.additionalEdges);
    }

    public int runTest(int numberOfVetrices){
        for (int i=0; i<numberOfVetrices; i++){
            addVertex();
        }
        return this.additionalEdges.size();
    }
    
    public static void main(String args[]){
        Integer [] samples = new Integer[] { 20, 40, 60, 80, 100 };
        int max = -1;
        int min = 1;
        boolean flag = false;
        double avg = 0;
        for(int sample : samples){
            PEO object = new PEO();
            int numberOfEdges = object.runTest(sample);
            if (flag == false){
                min = numberOfEdges;
                flag = true;
            }
            else if (min > numberOfEdges){
                min = numberOfEdges;
            }
            if (numberOfEdges > max){
                max = numberOfEdges;
            }
            avg = avg + numberOfEdges;
            // System.out.println();
        }
        avg = avg / samples.length;
        System.out.println("max: " + max);
        System.out.println("min: " + min);
        System.out.println("avg: " + avg);
        // object.addVertex();
        // object.printAdditionalEdges();
        
    }
}