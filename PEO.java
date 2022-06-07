import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class PEO{
    private ArrayList<ArrayList<Integer> > adjList;
    private ArrayList<Integer> vertexList;
    private Integer vertexCounter;
    private ArrayList<ArrayList<Integer> > additionalEdges;
    private int minDegree;
    private int maxDegree;
    private int avgDegree;

    
    public PEO(){
        this.adjList = new ArrayList<ArrayList<Integer>>();
        this.additionalEdges = new ArrayList<ArrayList<Integer>>();
        ArrayList edge = new ArrayList();
        Integer[] vetrexArray = new Integer[] { 0, 1};
        Integer[] adj0 = new Integer[] {1};
        adjList.add(new ArrayList(Arrays.asList(adj0)));
        Integer[] adj1 = new Integer[] {0};
        adjList.add(new ArrayList(Arrays.asList(adj1)));
        // Integer[] adj2 = new Integer[] {0};
        // adjList.add(new ArrayList(Arrays.asList(adj2)));
        this.vertexCounter = 2;
        this.vertexList = new ArrayList(Arrays.asList(vetrexArray));
        this.minDegree = 0;
        this.maxDegree = -1;
        this.avgDegree = 0;
    }

    public void addVertex(){
        ArrayList<Integer> neighbors = generateNeighbors();
        vertexList.add(vertexCounter);
        adjList.add(neighbors);
        for (int i=0; i<neighbors.size(); i++){
            int neighborName = neighbors.get(i);
            adjList.get(neighborName).add(vertexCounter);
        }
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
        Random rnd = new Random();
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        int numberOfNeighbors = rnd.nextInt(vertexList.size()-1)+1;
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
    
    public void calcualteDegree(){
        boolean flag = false;
        int sum = 0;
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
        this.avgDegree = sum / this.vertexCounter;
        // System.out.println("minDegree: " + this.minDegree + " maxDegree: " + this.maxDegree + " avgDegree: " + this.avgDegree);
    }

    public void extractGraphToFile(String fileName){
        try {
            FileWriter myWriter = new FileWriter(fileName + ".csv");
            System.out.println("Successfully wrote to the file.");
            for (ArrayList<Integer> list : this.adjList){
                boolean flag = false;
                for (int item : list){
                    String dataToWrite = "" + item;
                    if (flag == false){
                        myWriter.write(dataToWrite);
                        flag = true;
                    }
                    else{
                        myWriter.write("," + dataToWrite);
                    }
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int getNumberOfEdges(){
        return (this.additionalEdges.size() + 1);
    }

    public int getminDegree(){
        return this.minDegree;
    }

    public int getmaxDegree(){
        return this.maxDegree;
    }

    public int getavgDegree(){
        return this.avgDegree;
    }

    public double getEdgeDensity(){
        int numberOfEdges = getNumberOfEdges();
        int numberOfVertices = this.vertexList.size();
        double edgeDensity = numberOfEdges / ((numberOfVertices*(numberOfVertices - 1))*0.5);
        return edgeDensity;
    }

    public static void main(String args[]){
        Integer [] samples = new Integer[] { 18, 38, 58, 78, 98 };
        
        for(int sample : samples){
            boolean flag = false;
            int max = -1;
            int min = 1;
            double avgMinCliqueSize = 0;
            double avgMaxCliqueSize = 0;
            double avgMeanCliqueSize = 0;
            double avgMinDegree = 0;
            double avgMaxDegree = 0;
            double avgMeanDegree = 0;
            double avg = 0;
            double avgMaximalCliques = 0;
            double avgEdges = 0;
            double avgEdgeDensity = 0;
            System.out.println("for " + (sample+2) + " nodes");
            for(int test=0; test<10; test++){
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
                String fileName = "Graph" + "" + sample;
                object.calcualteDegree();
                object.extractGraphToFile(fileName);
                MergeClique mergeClique = new MergeClique(fileName+".csv");
                mergeClique.createCliqueTree();
                mergeClique.calculateCliqueSize();
                avgMinCliqueSize += mergeClique.getMinimumCliqueSize();
                avgMaxCliqueSize += mergeClique.getMaximumCliqueSize();
                avgMeanCliqueSize += mergeClique.getAvarageCliqueSize();
                avgMaximalCliques += mergeClique.getCliqueListSize();
                avgMinDegree += object.getminDegree();
                avgMaxDegree += object.getmaxDegree();
                avgMeanDegree += object.getavgDegree();
                avgEdges += object.getNumberOfEdges();
                avgEdgeDensity += object.getEdgeDensity();
            }

            avg = avg / 10;
            avgMaximalCliques = avgMaximalCliques/10;
            avgEdges = avgEdges/10;
            avgMinCliqueSize = avgMinCliqueSize/10;
            avgMaxCliqueSize = avgMaxCliqueSize/10;
            avgMeanCliqueSize = avgMeanCliqueSize/10;
            avgMinDegree = avgMinDegree/10;
            avgMaxDegree = avgMaxDegree/10;
            avgMeanDegree = avgMeanDegree/10;
            avgEdgeDensity = avgEdgeDensity/10;
            
            System.out.println("EdgeDesnity" + "\t" +"MinDegree" + "\t" + "MaxDegree" + "\t" + "MeanDegree" + "\t" + "MinClSize" + "\t" + "MaxClSize" + "\t" + "MeanClSize" + "\t" + "Edges" + "\t" + "Cliques" + "\t" + "min" + "\t" + "max" + "\t" + "avg");
            System.out.println(avgEdgeDensity + "\t" + avgMinDegree + "\t" + avgMaxDegree + "\t" + avgMeanDegree + "\t" + avgMinCliqueSize + "\t" + avgMaxCliqueSize + "\t" + avgMeanCliqueSize + "\t" + avgEdges + "\t" + avgMaximalCliques + "\t" + min + "\t" + max + "\t" + avg);
            // System.out.println("min" + "\t" + "max" + "\t" + "avg");
            // System.out.println(min + "\t" + max + "\t" + avg);
            // System.out.println("max: " + max);
            // System.out.println("min: " + min);
            // System.out.println("avg: " + avg);
        }
        // object.printAdditionalEdges();
        
    }
}