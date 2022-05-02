import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MergeClique{

    private ArrayList<ArrayList<Integer> > edges;
    private ArrayList<ArrayList<Integer> > cliqueTreeEdges;
    private ArrayList<Integer> vertices;
    private int numberOfVertices;


    public MergeClique(){
        this.edges = new ArrayList<ArrayList<Integer>>();
        this.cliqueTreeEdges = new ArrayList<ArrayList<Integer>>();
        this.vertices = new ArrayList<Integer>();
        this.numberOfVertices = 0;
        buildExample();
        printEdges();
    }

    public void buildExample(){
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



    public void printEdges(){
        System.out.println(this.edges);
    }

    public void createCliqueTree(){
        int prev_card = 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        int numberOfCliques = 0; // s <- 0
        // Et <- 0
        for (int i=this.numberOfVertices-1; i>0; i--){
            int max = -1;
            int uVertex = -1;
            for (int vertex : this.vertexList){
                if (!list.contains(vertex)){
                    int intersectionNumber = calculateIntersection(vertex,list);
                    if (max < intersectionNumber){
                        max = intersectionNumber;
                        uVertex = vertex;
                    }
                }
            }
            int new_card = max;
            if (new_card <= prev_card){
                numberOfCliques++;
                ArrayList<Integer> clique = calculateElementsIntersection(uVertex,list);
                if (new_card != 0){
                    int kVar = getMin(clique,list)//max{j|uj E Ks}
                    if (kvar == -1){
                        System.out.println("Error function: getMin, Bad return statement");
                        System.exit(1);
                    }
                    int pVar = getFirstCliqueMatch(kVar);
                    
                }
            }
        }
    }

    public int getFirstCliqueMatch(int vertex){
        int position = 0;
        for (ArrayList<Integer> clique : this.cliques){
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
        for (int item : list){
            if (clique.contains(item)){
                return item;
            }
        }
        return -1;
    }

    public ArrayList<Integer> calculateElementsIntersection(int vertex, ArrayList<Integer> list){
        ArrayList<Integer> neighbors = findNeighbors(vertex);
        ArrayList<Integer> intersection = ArrayList<Integer>();
        for (int neighbor : neighbors){
            if (list.contains(neighbor)){
                intersection.add(neighbor);
            }
        }
        return intersection;
    }

    public int calculateIntersection(int vertex, ArrayList<Integer> list){
        ArrayList<Integer> neighbors = findNeighbors(vertex);
        int counter = 0;
        for (int neighbor : neighbors){
            if (list.contains(neighbor)){
                counter++;
            }
        }
        return counter;
    }

    public static void main(String args[]){
        MergeClique object = new MergeClique();
        object.buildExample();
    }
}