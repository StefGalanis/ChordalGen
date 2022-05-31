import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 

public class MergeClique{

    private ArrayList<ArrayList<Integer> > edges;
    private ArrayList<ArrayList<Integer> > cliqueTreeEdges;
    private ArrayList<ArrayList<Integer> > cliqueList;
    private ArrayList<Integer> vertices;
    private int numberOfVertices;
    private Random rnd;
    private ArrayList<ArrayList<Integer> > adjList;
    private int numberOfEdges;


    public MergeClique(){
        this.edges = new ArrayList<ArrayList<Integer>>();
        this.cliqueTreeEdges = new ArrayList<ArrayList<Integer>>();
        this.cliqueList = new ArrayList<ArrayList<Integer>>();
        this.vertices = new ArrayList<Integer>();
        this.numberOfVertices = 0;
        this.rnd = new Random();
        this.adjList = new ArrayList<ArrayList<Integer>>();
        this.numberOfEdges = 0;
        buildExample(0);
        printEdges();
    }

    public MergeClique(String fileName){
        this.edges = new ArrayList<ArrayList<Integer>>();
        this.cliqueTreeEdges = new ArrayList<ArrayList<Integer>>();
        this.cliqueList = new ArrayList<ArrayList<Integer>>();
        this.vertices = new ArrayList<Integer>();
        this.numberOfVertices = 0;
        this.rnd = new Random();
        this.adjList = new ArrayList<ArrayList<Integer>>();
        this.numberOfEdges = 0;
        try {
            File myObj = new File("Graph20.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String [] arrayData = data.split(",");
                ArrayList<Integer> adjList = new ArrayList<Integer>();
                int index = 0;
                for (String item : arrayData){
                    adjList.add(Integer.parseInt(arrayData[index]));
                    index++;
                }
                this.adjList.add(adjList);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println(this.adjList);
        this.numberOfVertices = this.adjList.size();
        this.vertices = new ArrayList(Arrays.asList(createVerticesArray()));
    }

    public Integer[] createVerticesArray() {
        Integer[] a = new Integer[this.numberOfVertices];
        for (int i = 0; i < this.numberOfVertices; i++) {
            a[i] = i;
        }
        return a;
    }

    public void buildExample(int choice){
        if (choice == 1){
            Integer[] edge = new Integer[] {0,4};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {0,6};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {4,5};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {4,6};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {5,6};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {3,5};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {3,6};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {1,5};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {2,5};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            edge = new Integer[] {1,2};
            this.edges.add(new ArrayList(Arrays.asList(edge)));
            Integer[] spam = new Integer[] { 0, 1, 2, 3, 4, 5, 6 };
            this.vertices = new ArrayList(Arrays.asList(spam));
            this.numberOfVertices = 7;
        }
        else{
            Integer[] spam = new Integer[] { 0, 1, 2, 3, 4, 5, 6 };
            Integer[] adj0 = new Integer[] {4,6};
            this.adjList.add(new ArrayList(Arrays.asList(adj0)));
            Integer[] adj1 = new Integer[] {2,5};
            this.adjList.add(new ArrayList(Arrays.asList(adj1)));
            Integer[] adj2 = new Integer[] {1,5};
            this.adjList.add(new ArrayList(Arrays.asList(adj2)));
            Integer[] adj3 = new Integer[] {5,6};
            this.adjList.add(new ArrayList(Arrays.asList(adj3)));
            Integer[] adj4 = new Integer[] {0,5,6};
            this.adjList.add(new ArrayList(Arrays.asList(adj4)));
            Integer[] adj5 = new Integer[] {1,2,3,4,6};
            this.adjList.add(new ArrayList(Arrays.asList(adj5)));
            Integer[] adj6 = new Integer[] {0,3,4,5};
            this.adjList.add(new ArrayList(Arrays.asList(adj6)));
            this.numberOfVertices = 7;
            this.numberOfEdges = 10;
            // this.edgeCounter = 10;
            this.vertices = new ArrayList(Arrays.asList(spam));
            printAdjList();
        }
    }

    public void printAdjList(){
        System.out.println("Graph adjLists: " + this.adjList);
    }

    public void printEdges(){
        System.out.println("Graph edges: " + this.edges);
    }

    public void createCliqueTree(){
        int prev_card = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        int edgeCounter = 0; // s <- 0
        ArrayList<Integer> clique = new ArrayList<Integer>();
        // Et <- 0
        for (int i=this.numberOfVertices; i>0; i--){
            int max = -1;
            int uVertex = -1;
            for (int vertex : this.vertices){
                if (!list.contains(vertex)){
                    int intersectionNumber = calculateIntersection(vertex,list,this.adjList);
                    if (max < intersectionNumber){
                        max = intersectionNumber;
                        uVertex = vertex;
                    }
                }
            }
            int new_card = max;
            if (new_card <= prev_card){
                System.out.println("uVertex:" + uVertex);
                if (!clique.isEmpty()){
                    this.cliqueList.add(clique);
                    edgeCounter++;
                }
                clique = calculateElementsIntersection(uVertex,list,this.adjList);
                if (new_card != 0){
                    int kVar = getMin(clique,list); //max{j|uj E Ks}
                    if (kVar == -1){
                        System.out.println("Error function: getMin, Bad return statement");
                        System.exit(1);
                    }
                    int pVar = getFirstCliqueMatch(kVar);
                    ArrayList<Integer> edge = new ArrayList<Integer>();
                    edge.add(edgeCounter);
                    edge.add(kVar);
                    cliqueTreeEdges.add(edge);
                }
            }
            clique.add(uVertex);
            list.add(uVertex);
            prev_card = new_card;
        }
        this.cliqueList.add(clique);
        printCliqueTree();
        System.out.println("Clique Tree number of edges:" + edgeCounter);
        System.out.println("L:" + list);
    }


    public int getFirstCliqueMatch(int vertex){
        int position = 0;
        for (ArrayList<Integer> clique : this.cliqueList){
            if (clique.contains(vertex)){
                break;
            }
            else{
                position++;
            }
        }
        return position;
    }

    public int getMin(ArrayList<Integer> clique, ArrayList<Integer> list){
        for (int i=list.size()-1; i>=0; i--){
            if(clique.contains(list.get(i))){
                for(int j = 0; j<=this.cliqueList.size()-1; j++){
                    if(this.cliqueList.get(j).contains(list.get(i))){
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    public ArrayList<Integer> calculateElementsIntersection(int vertex, ArrayList<Integer> list){
        ArrayList<Integer> neighbors = findNeighbors(vertex);
        ArrayList<Integer> intersection = new ArrayList<Integer>();
        for (int neighbor : neighbors){
            if (list.contains(neighbor)){
                intersection.add(neighbor);
            }
        }
        return intersection;
    }
    
    public ArrayList<Integer> calculateElementsIntersection(int vertex, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> otherList){
        ArrayList<Integer> neighbors = this.adjList.get(vertex);
        ArrayList<Integer> intersection = new ArrayList<Integer>();
        for (int neighbor : neighbors){
            if (list.contains(neighbor)){
                intersection.add(neighbor);
            }
        }
        return intersection;
    }

    public int calculateIntersection(int vertex, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> otherList){
        ArrayList<Integer> neighbors = this.adjList.get(vertex);
        int counter = 0;
        for (int neighbor : neighbors){
            if (list.contains(neighbor)){
                counter++;
            }
        }
        return counter;
    }

    public ArrayList<Integer> findNeighbors(int vertex){
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        // System.out.println("edges:"+this.edges);
        for (ArrayList<Integer> edge : this.edges){
            if(edge.contains(vertex)){
                // System.out.println("edge:"+edge);
                for (int item : edge){
                    if (item != vertex){
                        neighbors.add(item);
                    }
                } 
            }
        }
        // System.out.println("vertex"+vertex+"\s"+neighbors);
        // System.exit(1);
        return neighbors;
    }

    public void printCliqueTree(){
        System.out.println("Clique tree edges: " + this.cliqueTreeEdges);
        System.out.println("Clique tree cliques: " + this.cliqueList);
    }

    public void mergeCliques(){
        double edgeDensity = this.numberOfEdges / ((this.numberOfVertices*(this.numberOfVertices - 1))*0.5);
        System.out.println(edgeDensity);
        // double desiredEdgeDensity;
        if (cliqueTreeEdges.size()>1){
            int randomEdgeIndex = rnd.nextInt(this.cliqueTreeEdges.size()-1);
            ArrayList<Integer> randomEdge = this.cliqueTreeEdges.get(randomEdgeIndex);
            int edge0 = randomEdge.get(0);
            int edge1 = randomEdge.get(1);
            ArrayList<Integer> clique0 = this.cliqueList.get(edge0);
            ArrayList<Integer> clique1 = this.cliqueList.get(edge1);
            for (int item0 : clique0){
                ArrayList<Integer> adjList0 = this.adjList.get(item0);
                for(int item1 : clique1){
                    if (!adjList0.contains(item1) && item0 != item1){
                        adjList0.add(item1);
                        this.adjList.get(item1).add(item0);
                        Integer [] edge = new Integer[] {item1,item0};
                        this.edges.add(new ArrayList(Arrays.asList(edge)));
                        this.numberOfEdges++;
                    }
                }
            }
            this.cliqueList.get(edge1).removeAll(clique0);
            this.cliqueList.get(edge1).addAll(clique0);
            this.cliqueList.set(edge0,this.cliqueList.get(edge1));
            
            this.cliqueTreeEdges.remove(randomEdgeIndex);
        }
        else{
            System.out.println("Graph is fully connected");
        }
    }


    public static void main(String args[]){
        MergeClique object = new MergeClique("filename");
        object.createCliqueTree();
        // object.buildExample();//
        // object.createCliqueTree();
        // object.mergeCliques();
        // object.printCliqueTree();
        // object.printAdjList();
        // object.mergeCliques();
        // object.printCliqueTree();
        // object.printAdjList();
        // object.printEdges();
    }
}