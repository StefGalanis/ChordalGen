import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;
import java.lang.Math;

public class MergeClique{

    private ArrayList<ArrayList<Integer> > edges;
    private ArrayList<ArrayList<Integer> > cliqueTreeEdges;
    private ArrayList<ArrayList<Integer> > cliqueList;
    private ArrayList<Integer> vertices;
    private int numberOfVertices;
    private Random rnd;
    private ArrayList<ArrayList<Integer> > adjList;
    private int numberOfEdges;
    private int minCliqueSize;
    private int maxCliqueSize;
    private double avgCliqueSize;
    private int minDegree;
    private int maxDegree;
    private double avgDegree;
    private ArrayList<Integer> cliquesToRemove;

    public MergeClique(){
        this.edges = new ArrayList<ArrayList<Integer>>();
        this.cliqueTreeEdges = new ArrayList<ArrayList<Integer>>();
        this.cliqueList = new ArrayList<ArrayList<Integer>>();
        this.vertices = new ArrayList<Integer>();
        this.numberOfVertices = 0;
        this.rnd = new Random();
        this.adjList = new ArrayList<ArrayList<Integer>>();
        this.numberOfEdges = 0;
        this.minDegree = 0;
        this.maxDegree = -1;
        this.avgDegree = 0;
        this.cliquesToRemove = new ArrayList<Integer>();
        // buildExample(0);
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
        this.cliquesToRemove = new ArrayList<Integer>();
        this.numberOfEdges = 0;
        this.minCliqueSize = -1;
        this.maxCliqueSize = -1;
        this.avgCliqueSize = 0;
        try {
            File myObj = new File(fileName);
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
        // System.out.println(this.adjList);
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

    public void buildExample(int nubmerOfNodes){
        
        for (int i=0; i<nubmerOfNodes; i++){
            ArrayList<Integer> tempAdjList = new ArrayList<Integer>();
            if ( i == 0 ){
                this.adjList.add(tempAdjList);
                this.vertices.add(this.numberOfVertices);
                this.numberOfVertices++;
            }
            else{
                this.vertices.add(this.numberOfVertices);
                tempAdjList.add(this.numberOfVertices-1);
                this.adjList.add(tempAdjList);
                this.adjList.get(this.numberOfVertices-1).add(this.numberOfVertices);
                this.numberOfEdges++;
                this.numberOfVertices++;
            }
        }
        // printAdjList();
    }

    public void buildExample(){
        Integer[] vetrexArray = new Integer[] { 0, 1, 2, 3, 4, 5, 6 };
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
        this.vertices = new ArrayList(Arrays.asList(vetrexArray));
        // printAdjList();
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
                // System.out.println("uVertex:" + uVertex);
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
        // printCliqueTree();
        // System.out.println("Clique Tree number of edges:" + edgeCounter);
        // System.out.println("maximal cliques " + cliqueList.size());
        // System.out.println("L:" + list);
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


    public void mergeCliques(String edgeDensityLimit){
        double edgeDensity = this.numberOfEdges / ((this.numberOfVertices*(this.numberOfVertices - 1))*0.5);
        // System.out.println(edgeDensity);
        double desiredEdgeDensity = 0;
        if (edgeDensityLimit.equals("n*log(n)")){
            desiredEdgeDensity = (this.numberOfVertices*Math.log(this.numberOfVertices))/((this.numberOfVertices*(this.numberOfVertices - 1))*0.5);
        }
        else if(edgeDensityLimit.equals("n*sqrt(n)")){
            desiredEdgeDensity = (this.numberOfVertices*Math.sqrt(this.numberOfVertices))/((this.numberOfVertices*(this.numberOfVertices - 1))*0.5);
        }
        else{
            System.exit(1);
        }
        // System.out.println("desiredEdgeDensity :" + desiredEdgeDensity);
        while(edgeDensity<desiredEdgeDensity){
            if (desiredEdgeDensity>1){
                System.exit(1);
            }
            if (cliqueTreeEdges.size()>=1){
                int randomEdgeIndex;
                if(cliqueTreeEdges.size()>1){
                    randomEdgeIndex = rnd.nextInt(this.cliqueTreeEdges.size()-1);
                }
                else{
                    randomEdgeIndex = 0;
                }
                ArrayList<Integer> randomEdge = this.cliqueTreeEdges.get(randomEdgeIndex);
                int edge0 = randomEdge.get(0);
                int edge1 = randomEdge.get(1);
                ArrayList<Integer> clique0 = this.cliqueList.get(edge0);
                ArrayList<Integer> clique1 = this.cliqueList.get(edge1);
                for (int item0 : clique0){
                    ArrayList<Integer> adjList0 = this.adjList.get(item0);
                    for(int item1 : clique1){
                        if (!adjList0.contains(item1) && item0 != item1){
                            this.adjList.get(item0).add(item1);
                            this.adjList.get(item1).add(item0);
                            this.numberOfEdges++;
                        }
                    }
                }
                this.cliqueList.get(edge1).removeAll(clique0);
                this.cliqueList.get(edge1).addAll(clique0);
                for (ArrayList<Integer> edge : this.cliqueTreeEdges){
                    if (edge.contains(edge0)){
                        int indexOfNodeToReplace = edge.indexOf(edge0);
                        edge.set(indexOfNodeToReplace,edge1);
                    }
                }
                this.cliquesToRemove.add(edge0);
                this.cliqueTreeEdges.remove(randomEdgeIndex);
                printCliqueTree();
            }
            else{
                System.out.println("Graph is fully connected with edge density " + edgeDensity );
                printAdjList();
                break;
            }
            edgeDensity = this.numberOfEdges / ((this.numberOfVertices*(this.numberOfVertices - 1))*0.5);
        }
    }



    public void calculateCliqueSize(){
        int max = -1;
        int min = 1;
        boolean flag = false;
        double avg = 0;
        // for (int cliqueIndex : this.cliquesToRemove){
        //     this.cliqueList.remove(cliqueIndex);
        //     System.out.println(this.cliqueList);
        // }
        for (int i=0; i<this.cliqueList.size(); i++){
            if(!this.cliquesToRemove.contains(i)){
                ArrayList<Integer> list = this.cliqueList.get(i);
                if (flag == false){
                    min = list.size();
                    flag = true;
                }
                else if (min > list.size()){
                    min = list.size();
                }
                if (list.size() > max){
                    max = list.size();
                }
                avg += list.size();
            }
        }
        avg = avg / (cliqueList.size() - this.cliquesToRemove.size());
        this.minCliqueSize = min;
        this.maxCliqueSize = max;
        this.avgCliqueSize = avg;
    }

    public int getMinimumCliqueSize(){
        return this.minCliqueSize;
    }

    public int getMaximumCliqueSize(){
        return this.maxCliqueSize;
    }

    public double getAvarageCliqueSize(){
        return this.avgCliqueSize;
    }

    public void printCliqueTree(){
        System.out.println("Clique tree edges: " + this.cliqueTreeEdges);
        System.out.println("Clique tree cliques: " + this.cliqueList);
    }

    public int getCliqueListSize(){
        return this.cliqueList.size();
    }

    public int getNumberOfEdges(){
        return this.numberOfEdges;
    }

    public int getNumberOfVertices(){
        return this.numberOfVertices;
    }

    public int getNumberOfCliquesToRemove(){
        return this.cliquesToRemove.size();
    }

    public void calcualteDegree(){
        boolean flag = false;
        int sum = 0;
        // System.out.println(this.adjList);
        for (ArrayList<Integer> List : this.adjList){
            int degree = List.size();
            if (flag == false){
                this.minDegree = degree;
                flag = true;
            }
            if (degree > this.maxDegree){
                this.maxDegree = degree;
            }
            if(degree < this.minDegree){
                this.minDegree = degree;
            }
            sum = sum + degree;
        }
        this.avgDegree = sum / this.numberOfVertices;
        // System.out.println("minDegree: " + this.minDegree + " maxDegree: " + this.maxDegree + " avgDegree: " + this.avgDegree);
    }

    public int getminDegree(){
        return this.minDegree;
    }

    public int getmaxDegree(){
        return this.maxDegree;
    }

    public double getavgDegree(){
        return this.avgDegree;
    }

    public double getEdgeDensity(){
        double edgeDensity = this.numberOfEdges / ((this.numberOfVertices*(this.numberOfVertices - 1))*0.5);
        return edgeDensity;
    }

    public static void main(String args[]){
        MergeClique example = new MergeClique();
        example.buildExample(5);
        example.createCliqueTree();
        example.printCliqueTree();
        example.mergeCliques("n*log(n)");
        example.printCliqueTree();
        System.out.println(example.getEdgeDensity());
        System.exit(1);
        Integer [] samples = new Integer[] { 20, 40, 60, 80, 100 };
        String [] edgeDensities = new String[] {"n*log(n)","n*sqrt(n)"};
        for (String edgeDensity : edgeDensities){
            System.out.println("Statistics for " + edgeDensity);
            for (int sample : samples){
                boolean flag = false;
                int max = -1;
                int min = 1;
                double avg = 0;
                double avgMinCliqueSize = 0;
                double avgMaxCliqueSize = 0;
                double avgMeanCliqueSize = 0;
                double avgMinDegree = 0;
                double avgMaxDegree = 0;
                double avgMeanDegree = 0;
                // double avg = 0;
                double avgMaximalCliques = 0;
                double avgEdges = 0;
                double avgEdgeDensity = 0;
                for(int test=0; test<10; test++){
                    MergeClique object = new MergeClique();
                    object.buildExample(sample);
                    object.createCliqueTree();
                    object.mergeCliques(edgeDensity);
                    object.calcualteDegree();
                    object.calculateCliqueSize();
                    int numberOfEdges = object.getNumberOfEdges();
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
                    avgMinDegree += object.getminDegree();
                    avgMaxDegree += object.getmaxDegree();
                    avgMeanDegree += object.getavgDegree();
                    avgMinCliqueSize += object.getMinimumCliqueSize();
                    avgMaxCliqueSize += object.getMaximumCliqueSize();
                    avgMeanCliqueSize += object.getAvarageCliqueSize();
                    avgMaximalCliques += object.getCliqueListSize() - object.getNumberOfCliquesToRemove();
                    avgEdges += object.getNumberOfEdges();
                    avgEdgeDensity += object.getEdgeDensity();
                }
                // avg = avg / 10;
                avgMaximalCliques = avgMaximalCliques/10;
                avgEdges = avgEdges/10;
                avgMinCliqueSize = avgMinCliqueSize/10;
                avgMaxCliqueSize = avgMaxCliqueSize/10;
                avgMeanCliqueSize = avgMeanCliqueSize/10;
                avgMinDegree = avgMinDegree/10;
                avgMaxDegree = avgMaxDegree/10;
                avgMeanDegree = avgMeanDegree/10;
                avgEdgeDensity = avgEdgeDensity/10;
                avg = avg/10;

                System.out.println("Statistics for graph with " + sample + " nodes");
                System.out.println("EdgeDesnity" + "\t" +"MinDegree" + "\t" + "MaxDegree" + "\t" + "MeanDegree" + "\t" + "MinClSize" + "\t" + "MaxClSize" + "\t" + "MeanClSize" + "\t" + "Edges" + "\t" + "Cliques" + "\t" + "min" + "\t" + "max" + "\t" + "avg");
                System.out.println(avgEdgeDensity + "\t" + avgMinDegree + "\t" + avgMaxDegree + "\t" + avgMeanDegree + "\t" + avgMinCliqueSize + "\t" + avgMaxCliqueSize + "\t" + avgMeanCliqueSize + "\t" + avgEdges + "\t" + avgMaximalCliques + "\t" + min + "\t" + max + "\t" + avg);
                
                // object.printCliqueTree();
                // System.out.println("edges before: " + object.getNumberOfEdges());
                // System.out.println("edges after: " + object.getNumberOfEdges());
            }
        }
    }
}