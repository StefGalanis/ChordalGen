import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MaximumClique{

    private ArrayList<ArrayList<Integer> > maximumKliqueList;
    private ArrayList<ArrayList<Integer> > edges;
    private ArrayList<Integer> vertices;
    private int maximumCliqueSize;
    private int numberOfVertices;
    private Random rnd ;

    public MaximumClique(){
        this.maximumKliqueList = new ArrayList<ArrayList<Integer>>();
        this.edges = new ArrayList<ArrayList<Integer>>();
        this.vertices = new ArrayList<Integer>();
        this.maximumCliqueSize = 0;
        this.numberOfVertices = 1;
        this.rnd = new Random();
    }

    public void addVertex(){
        if (this.vertices.size() == 0){
            this.vertices.add(0);
            this.maximumCliqueSize = 1;
            ArrayList<Integer> maximumClique = new ArrayList<Integer>();
            maximumClique.add(0);
            this.maximumKliqueList.add(maximumClique);
        }
        else{
            this.vertices.add(numberOfVertices);
            // this.rnd = new Random();
            int maximumCliquePosition = this.rnd.nextInt(this.maximumKliqueList.size());
            ArrayList<Integer> newMaximumClique = new ArrayList<Integer>();
            newMaximumClique.add(numberOfVertices);
            
            ArrayList<Integer> maximumClique = this.maximumKliqueList.get(maximumCliquePosition);
            Integer [] maximumCliqueToArray = maximumClique.toArray(new Integer[maximumClique.size()]);
            // System.out.println(maximumClique.size());
            int sizeOfSubset = 0;
            if(maximumClique.size() <= 2){
                sizeOfSubset = maximumClique.size();
            }
            else{
                System.out.println("Maximum Clique size: " + maximumClique.size());
                int randomNumber = this.rnd.nextInt(maximumClique.size()-1);
                System.out.println("randomNumber: " + randomNumber);
                sizeOfSubset = randomNumber + 2;
                System.out.println("randomSubset size : " + sizeOfSubset);
            }
            // System.out.println(sizeOfSubset);
            for (int i=0; i<sizeOfSubset; i++){
                int range = sizeOfSubset - i;
                int randomElement = this.rnd.nextInt(range) + i;
                int randomVertex = maximumCliqueToArray[randomElement];
                int tempSawp = maximumCliqueToArray[i];
                maximumCliqueToArray[i] = maximumCliqueToArray[randomElement];
                maximumCliqueToArray[randomElement] = tempSawp;
                ArrayList<Integer> edge = new ArrayList<Integer>();
                System.out.println("edge added {" + numberOfVertices + "," + randomVertex + "}");
                edge.add(numberOfVertices);
                edge.add(randomVertex);
                this.edges.add(edge);
                newMaximumClique.add(randomVertex);
            }
            if ((sizeOfSubset+1) > maximumCliqueSize){
                maximumCliqueSize = sizeOfSubset + 1;
                this.maximumKliqueList = new ArrayList<ArrayList<Integer>>();
                this.maximumKliqueList.add(newMaximumClique);
            }
            else if ((sizeOfSubset+1) == maximumCliqueSize){
                this.maximumKliqueList.add(newMaximumClique);
            }
            System.out.println("Maximum Clique List: " + maximumKliqueList);
            this.numberOfVertices += 1;
        }
    }

    public int getMaximumCliqueSize(){
        return this.maximumCliqueSize;
    }


    public void printEdges()
    {
        System.out.println("Added edges: "+ this.edges);
    }


    public static void main(String args[]){
            MaximumClique object = new MaximumClique();
            for(int j=0; j<6; j++){
                object.addVertex();
            }
            System.out.println(object.getMaximumCliqueSize());
            object.printEdges();
            // object.findRandomClique();
            // object.addVertex();
            // object.printAdjList();
            // object.addVertex();
            // object.printAdjList();
            // object.findRandomClique();
    }
}