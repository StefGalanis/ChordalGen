import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MCS{
    private ArrayList<ArrayList<Integer> > adjList;
    private ArrayList<ArrayList<Integer> > kliqueList;
    private ArrayList<ArrayList<Integer> > cliqueTreeEdges;
    private ArrayList<Integer> vertexList;
    private Integer vertexCounter;
    private Integer edgeCounter;
    
    public MCS(int choice){
        if (choice == 1){
            this.adjList = new ArrayList<ArrayList<Integer>>();
            this.kliqueList = new ArrayList<ArrayList<Integer>>();
            // ArrayList edge = new ArrayList();
            Integer[] spam = new Integer[] { 0, 1, 2, 3 };
            Integer[] adj0 = new Integer[] {1,3};
            adjList.add(new ArrayList(Arrays.asList(adj0)));
            Integer[] adj1 = new Integer[] {2,0,3};
            adjList.add(new ArrayList(Arrays.asList(adj1)));
            Integer[] adj2 = new Integer[] {1,3};
            adjList.add(new ArrayList(Arrays.asList(adj2)));
            Integer[] adj3 = new Integer[] {0,1,2};
            adjList.add(new ArrayList(Arrays.asList(adj3)));
            this.vertexCounter = 4;
            this.vertexList = new ArrayList(Arrays.asList(spam));
            for(ArrayList<Integer> item : adjList){
                System.out.println(item);
            }
        }
        else if(choice == 2){
            this.adjList = new ArrayList<ArrayList<Integer>>();
            this.kliqueList = new ArrayList<ArrayList<Integer>>();
            this.cliqueTreeEdges = new ArrayList<ArrayList<Integer>>() ;
            // ArrayList edge = new ArrayList();
            Integer[] spam = new Integer[] { 0, 1, 2, 3, 4, 5, 6 };
            Integer[] adj0 = new Integer[] {4,6};
            adjList.add(new ArrayList(Arrays.asList(adj0)));
            Integer[] adj1 = new Integer[] {2,5};
            adjList.add(new ArrayList(Arrays.asList(adj1)));
            Integer[] adj2 = new Integer[] {1,5};
            adjList.add(new ArrayList(Arrays.asList(adj2)));
            Integer[] adj3 = new Integer[] {5,6};
            adjList.add(new ArrayList(Arrays.asList(adj3)));
            Integer[] adj4 = new Integer[] {0,5,6};
            adjList.add(new ArrayList(Arrays.asList(adj4)));
            Integer[] adj5 = new Integer[] {1,2,3,4,6};
            adjList.add(new ArrayList(Arrays.asList(adj5)));
            Integer[] adj6 = new Integer[] {0,3,4,5};
            adjList.add(new ArrayList(Arrays.asList(adj6)));
            this.vertexCounter = 7;
            this.edgeCounter = 10;
            this.vertexList = new ArrayList(Arrays.asList(spam));
            // for(ArrayList<Integer> item : adjList){
            //     System.out.println(item);
            // }
            printAdjList();
        }
        else if(choice == 3){
            this.adjList = new ArrayList<ArrayList<Integer>>();
            this.kliqueList = new ArrayList<ArrayList<Integer>>();
            Integer[] spam = new Integer[] { 0, 1 };
            Integer[] adj0 = new Integer[] {1};
            this.adjList.add(new ArrayList(Arrays.asList(adj0)));
            Integer[] adj1 = new Integer[] {0};
            this.adjList.add(new ArrayList(Arrays.asList(adj1)));
            this.vertexCounter = 2;
            this.edgeCounter = 1;
            this.vertexList = new ArrayList(Arrays.asList(spam));
            printAdjList();
        }
    }

    public void runMCS(){
        ArrayList<Integer> peoList = new ArrayList<Integer>();
        ArrayList<Integer> next = new ArrayList<Integer>();
        int numberOfVertices = vertexList.size();
        Integer [] tempVertexList = vertexList.toArray(new Integer[numberOfVertices]);

        Random rnd = new Random();
        int elementPosition = rnd.nextInt(numberOfVertices);
        peoList.add(tempVertexList[elementPosition]);
        System.out.println(peoList);
        for (int i=0; i<numberOfVertices-1; i++){
            ArrayList<Integer> unionList = new ArrayList<Integer>(); // L i+1
            System.out.println("PEO: "+ peoList);
            for (int peoItem : peoList){
                ArrayList<Integer> tempAdjList = adjList.get(peoItem);
                for(int item : tempAdjList){
                    if ((!unionList.contains(item)) && (!peoList.contains(item))){
                        unionList.add(item);
                    }
                }
            }

            System.out.println("UnionList" + ": " + unionList);
            int uVertex = 0;
            int max = -1;

            for (int unionItem : unionList){
                int counter = 0;
                ArrayList<Integer> klique = new ArrayList<Integer>();
                for (int peoItem : peoList){
                    if(adjList.get(unionItem).contains(peoItem)){
                        counter++;
                    }
                }
                if (max<counter){
                    max=counter;
                    uVertex = unionItem;
                }
            }

            System.out.println("new_card: " + max +" U: " + uVertex);
            peoList.add(uVertex);
        }
        System.out.println("PEO: "+ peoList);
    }

    public void runPrim(){
        ArrayList<Integer> peoList = new ArrayList<Integer>();
        ArrayList<Integer> next = new ArrayList<Integer>();
        int numberOfVertices = vertexList.size();
        Integer [] tempVertexList = vertexList.toArray(new Integer[numberOfVertices]);
        int prev_card = 0;
        int s = 0;
        Random rnd = new Random();
        int elementPosition = rnd.nextInt(numberOfVertices);
        peoList.add(tempVertexList[elementPosition]);
        System.out.println(peoList);
        ArrayList<Integer> klique = new ArrayList<Integer>();
        klique.add(tempVertexList[elementPosition]);
        System.out.println(numberOfVertices);
        for (int i=0; i<numberOfVertices-1; i++){
            
            ArrayList<Integer> unionList = new ArrayList<Integer>(); // L i+1

            for (int peoItem : peoList){
                ArrayList<Integer> tempAdjList = adjList.get(peoItem);
                for(int item : tempAdjList){
                    if ((!unionList.contains(item)) && (!peoList.contains(item))){
                        unionList.add(item);
                    }
                }
            }
            

            int uVertex = 0;
            int max = -1;

            for (int unionItem : unionList){
                int counter = 0;
                for (int peoItem : peoList){
                    if(adjList.get(unionItem).contains(peoItem)){
                        counter++;
                    }
                }
                if (max<counter){
                    max=counter;
                    uVertex = unionItem;
                }
            }

            int new_card = max;
            
            if (new_card <= prev_card){

                this.kliqueList.add(klique);

                klique = new ArrayList<Integer>();
                s++; //clique counter
                for (int peoItem : peoList){
                    if(adjList.get(uVertex).contains(peoItem)){
                        klique.add(peoItem);
                    }
                }

                if (new_card != 0){
                    int k = 0;
                    for (int j = peoList.size()-1; j>0; j--){
                        if (klique.contains(peoList.get(j))){
                            k = j;
                            break;
                        }
                    }
                    int vertexUk = peoList.get(k);
                    int p = 0;
                    boolean flag = false;
                    for (ArrayList<Integer> tempklique : this.kliqueList){
                        for (int kliqueItem : tempklique){
                            
                            if (tempklique.contains(vertexUk)){
                                flag = true;
                                break;
                            }
                        }
                        if (flag == true){
                            break;
                        }   
                        p++;
                    }
                    // System.out.println("Ks: " + s + "Kp: " + p);
                    // create an edge between Ks and Kp, Et = Et U {Ks,Kp}
                    ArrayList<Integer> tempList = new ArrayList<Integer>();
                    tempList.add(s);
                    tempList.add(p);
                    this.cliqueTreeEdges.add(tempList);
                }

                klique.add(uVertex);
                peoList.add(uVertex);
                prev_card = new_card;
                // System.out.println("current clique: " +klique);
                // System.out.println("clique Lists: " +kliqueList);
                
            }
            else{
                klique.add(uVertex);
                peoList.add(uVertex);
                prev_card = new_card;
            }
            // clique(Ui) = s
            
        }
        kliqueList.add(klique);
        System.out.println("PEO: "+ peoList);
        // System.out.println("clique Lists: " +this.kliqueList);
        prindKliqueList();
        System.out.println("Clique Tree Edges: " +this.cliqueTreeEdges);
        
    }


    public ArrayList<Integer> findRandomClique(){ 
        ArrayList<Integer> randomClique = new ArrayList<Integer>();
        Random rnd = new Random();
        int elementPosition = rnd.nextInt(vertexCounter);
        int randomVertex = vertexList.get(elementPosition);
        ArrayList<Integer> randomVertexNeighborhoodList = this.adjList.get(randomVertex);
        Integer [] randomVertexNeighborhood = randomVertexNeighborhoodList.toArray(new Integer[randomVertexNeighborhoodList.size()]);
        int degree = randomVertexNeighborhood.length;
        // pick a random number from 1 to degree
        int cliqueSize = rnd.nextInt(degree)+1;
        randomClique.add(randomVertex);
        int counter = 0;
        while(randomClique.size() <= cliqueSize && randomVertexNeighborhood.length > counter /*&& randomClique.size() < sizeLimit*/){
            int range = (randomVertexNeighborhood.length - counter);
            // System.out.println(range);
            elementPosition = rnd.nextInt(range) + counter;
            randomVertex = randomVertexNeighborhood[elementPosition];
            int tempSawp = randomVertexNeighborhood[counter];
            randomVertexNeighborhood[counter] = randomVertexNeighborhood[elementPosition];
            randomVertexNeighborhood[elementPosition] = tempSawp;
            ArrayList<Integer> tempRandomVertexNeighborhood = this.adjList.get(randomVertex);
            int containsCounter = 0;
            for(int cliqueItem : randomClique){
                if (tempRandomVertexNeighborhood.contains(cliqueItem)){ containsCounter++; }
            }
            if(containsCounter == randomClique.size()){ 
                randomClique.add(randomVertex);
                randomVertexNeighborhoodList = this.adjList.get(randomVertex);
                randomVertexNeighborhood = randomVertexNeighborhoodList.toArray(new Integer[randomVertexNeighborhoodList.size()]);
                counter = 0;
            }
            else{
                counter++;
            }
        }
        System.out.println("RandomClique: " + randomClique);
        return randomClique;
    }

    public void addVertex(){

        ArrayList<Integer> clique = findRandomClique();
        System.out.println("-----------");
        int newVertex = this.vertexList.get(vertexList.size()-1) + 1;
        for (int itemClique : clique){
            this.adjList.get(itemClique).add(newVertex);
            this.edgeCounter++;
        }
        this.adjList.add(clique);
        this.vertexList.add(newVertex);
        this.vertexCounter++;
    }

    public void mergeCliques(){
        // ean duo klikes exoyn koines koryfes shmainei oti einai geitonikes
        //
    }

    public ArrayList<Integer> findMaximumClique(){
        ArrayList<Integer> maximumClique = new ArrayList<Integer>();
        int maxDegree = -1;
        int maxDegreeVertex = -1;
        for (int i=0; i<this.adjList.size(); i++){
            if (this.adjList.get(i).size() > maxDegree){
                maxDegree = this.adjList.get(i).size();
                maxDegreeVertex = i;
            }
        }
        ArrayList<Integer> maxDegreeVertexNeighborhoodList = this.adjList.get(maxDegreeVertex);
        return maxDegreeVertexNeighborhoodList;
    }


    public void printAdjList(){
        int vertex = 0;
        for(ArrayList<Integer> item : this.adjList){
            System.out.println("Node" + vertex + ":" + item);
            vertex++;
        }
        System.out.println("Number of edges:" + this.edgeCounter + "\s Number of Vertices:" + this.vertexCounter);
    }

    public void prindKliqueList(){
        int vertex = 0;
        for(ArrayList<Integer> item : this.kliqueList){
            System.out.println("Node" + vertex + ":" + item);
            vertex++;
        }
    }
    
    public static void main(String args[]){
        MCS object = new MCS(2);
        // object.runMCS();
        for (int j=0; j<0; j++){
            object.addVertex();
        }
        object.printAdjList();
        object.runPrim();
        // object.addVertex();
        // object.printAdjList();
        // object.findRandomClique();
    }
}