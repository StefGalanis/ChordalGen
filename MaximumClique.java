import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class MaximumClique{

    private ArrayList<ArrayList<Integer> > maximumKliqueList;
    private ArrayList<ArrayList<Integer> > adjList;
    private ArrayList<ArrayList<Integer> > edges;
    private ArrayList<Integer> vertices;
    private int maximumCliqueSize;
    private int numberOfVertices;
    private Random rnd ;
    private int numberOfAddedEdges;
    private int minDegree;
    private int maxDegree;
    private int avgDegree;

    public MaximumClique(){
        this.maximumKliqueList = new ArrayList<ArrayList<Integer>>();
        this.edges = new ArrayList<ArrayList<Integer>>();
        this.adjList = new ArrayList<ArrayList<Integer>>();
        this.vertices = new ArrayList<Integer>();
        this.maximumCliqueSize = 0;
        this.numberOfVertices = 1;
        this.rnd = new Random();
        this.numberOfAddedEdges = 0;
        this.minDegree = 0;
        this.maxDegree = -1;
        this.avgDegree = 0;
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
                // System.out.println("Maximum Clique size: " + maximumClique.size());
                int randomNumber = this.rnd.nextInt(maximumClique.size()-1);
                // System.out.println("randomNumber: " + randomNumber);
                sizeOfSubset = randomNumber + 2;
                // System.out.println("randomSubset size : " + sizeOfSubset);
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
                // System.out.println("edge added {" + numberOfVertices + "," + randomVertex + "}");
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
            // System.out.println("Maximum Clique List: " + maximumKliqueList);
            this.numberOfVertices += 1;
        }
    }

    public void addVertexV2(){
        if (this.vertices.size() == 0){
            this.vertices.add(0);
            this.maximumCliqueSize = 1;
            ArrayList<Integer> maximumClique = new ArrayList<Integer>();
            ArrayList<Integer> newVertexAdjList = new ArrayList<Integer>();
            this.adjList.add(newVertexAdjList);
            maximumClique.add(0);
            this.maximumKliqueList.add(maximumClique);
        }
        else{
            this.vertices.add(numberOfVertices);
            int maximumCliquePosition = this.rnd.nextInt(this.maximumKliqueList.size());
            ArrayList<Integer> newMaximumClique = new ArrayList<Integer>();
            newMaximumClique.add(numberOfVertices);
            
            ArrayList<Integer> maximumClique = this.maximumKliqueList.get(maximumCliquePosition);
            Integer [] maximumCliqueToArray = maximumClique.toArray(new Integer[maximumClique.size()]);
            int sizeOfSubset = 0;
            if(maximumClique.size() <= 2){
                sizeOfSubset = maximumClique.size();
            }
            else{
                int randomNumber = this.rnd.nextInt(maximumClique.size()-1);
                sizeOfSubset = randomNumber + 2;
            }
            ArrayList<Integer> newVertexAdjList = new ArrayList<Integer>();
            for (int i=0; i<sizeOfSubset; i++){
                int range = sizeOfSubset - i;
                int randomElement = this.rnd.nextInt(range) + i;
                int randomVertex = maximumCliqueToArray[randomElement];
                int tempSawp = maximumCliqueToArray[i];
                maximumCliqueToArray[i] = maximumCliqueToArray[randomElement];
                maximumCliqueToArray[randomElement] = tempSawp;
                newVertexAdjList.add(randomVertex);
                this.adjList.get(randomVertex).add(numberOfVertices);
                this.numberOfAddedEdges += 1;
                newMaximumClique.add(randomVertex);
            }
            this.adjList.add(newVertexAdjList);
            if ((sizeOfSubset+1) > this.maximumCliqueSize){
                this.maximumCliqueSize = sizeOfSubset + 1;
                this.maximumKliqueList = new ArrayList<ArrayList<Integer>>();
                this.maximumKliqueList.add(newMaximumClique);
            }
            else if ((sizeOfSubset+1) == maximumCliqueSize){
                this.maximumKliqueList.add(newMaximumClique);
            }
            this.numberOfVertices += 1;
        }
    }

    public void extractGraphToFile(String fileName){
        try {
            FileWriter myWriter = new FileWriter(fileName + ".csv");
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
        this.avgDegree = sum / this.vertices.size();
        // System.out.println("minDegree: " + this.minDegree + " maxDegree: " + this.maxDegree + " avgDegree: " + this.avgDegree);
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

    public int getMaximumCliqueSize(){
        return this.maximumCliqueSize;
    }

    public int getNumberOfEdges(){
        return this.numberOfAddedEdges;
    }

    public double getEdgeDensity(){
        int numberOfEdges = getNumberOfEdges();
        int numberOfVertices = this.vertices.size();
        double edgeDensity = numberOfEdges / ((numberOfVertices*(numberOfVertices - 1))*0.5);
        return edgeDensity;
    }

    public void printAdjList(){
        // System.out.println("Adj List: "+ this.adjList);
        for (ArrayList<Integer> list : this.adjList){
            System.out.println(list);
        }
        System.out.println("End of graph");
    }

    public void printEdges()
    {
        System.out.println("Added edges: "+ this.edges);
    }

    public int runTest(int numberOfVetrices){
        for (int i=0; i<numberOfVetrices; i++){
            addVertexV2();
        }
        if (this.edges.size() != 0){
            return this.edges.size();
        }
        else{
            return this.numberOfAddedEdges;
        }
    }


    public static void main(String args[]){
        Integer [] samples = new Integer[] { 20, 40, 60, 80, 100 };

        for(int sample : samples){
            boolean flag = false;
            int max = -1;
            int min = 1;
            double avg = 0;
            double avgMinDegree = 0;
            double avgMaxDegree = 0;
            double avgMeanDegree = 0;
            double avgNumberOfEdges = 0;
            double avgEdgeDensity = 0;
            double avgMinCliqueSize = 0;
            double avgMaxCliqueSize = 0;
            double avgMeanCliqueSize = 0;
            double avgMaximalCliques = 0;
            for(int test=0; test<10; test++){
                MaximumClique object = new MaximumClique();
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

                String fileName = "MaximumCliqueGraph" + "" + sample;
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
                avgNumberOfEdges += object.getNumberOfEdges();
                avgEdgeDensity += object.getEdgeDensity();
                object.extractGraphToFile(fileName);
            }
            avgMinDegree = avgMinDegree/10;
            avgMaxDegree = avgMaxDegree/10;
            avgMeanDegree = avgMeanDegree/10;
            avgNumberOfEdges = avgNumberOfEdges/10;
            avgEdgeDensity = avgEdgeDensity/10;
            avgMinCliqueSize = avgMinCliqueSize/10;
            avgMaxCliqueSize = avgMaxCliqueSize/10;
            avgMeanCliqueSize = avgMeanCliqueSize/10;
            avgMaximalCliques = avgMaximalCliques/10;
            avg = avg/10;

            System.out.println("Statistics for graph with " + sample + " nodes");
            System.out.println("avgMinDegree" + "\t" + "avgMaxDegree" + "\t" +"avgMeanDegree"+ "\t" +"Edges"+ "\t" +"EdgeDesnity"+ "\t" +"avgMeanCliqueSize"+ "\t" +"avgMaxCliqueSize"+ "\t" +"avgMinCliqueSize"+ "\t" + "avgNumberOfMaximalCliques" + "\t" + "min" + "\t" + "max" + "\t" + "avg");
            System.out.println(avgMinDegree + "\t" + avgMaxDegree + "\t" +avgMeanDegree+ "\t" +avgNumberOfEdges + "\t" + avgEdgeDensity+ "\t" +avgMeanCliqueSize+ "\t" +avgMaxCliqueSize + "\t" + avgMinCliqueSize+ "\t" + avgMaximalCliques + "\t" + min + "\t" + max + "\t" + avg);
        }
    }
}