package org.example;

import org.example.dataTypes.IdWeightPair;
import org.example.dataTypes.SearchResult;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SecondPass {



    double wmax = Double.MIN_VALUE;
    int wsize = 0;

    public void createIndex(){
        String filePath = "C:\\Users\\dhruv\\Projects\\SpatialProject\\U-ask_KNN_spatialQuery\\Test\\Data\\data1.txt";
        double x ;
        double y ;
        int id;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
//                System.out.println(line);
                String[] values = line.split(" ");
                if (values.length >= 3) {
                    id = Integer.parseInt(values[0]);
                    x = Double.parseDouble(values[1]);
                    y = Double.parseDouble(values[2]);




                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void levelOrder(Quad root) {

        ArrayList<Quad> q = new ArrayList<Quad>();
        q.add(root);
        while (!q.isEmpty()) {

            ArrayList<Quad> same_level = new ArrayList<>();
            int qsize = q.size();
            for (int i = 0; i < qsize; i++) {
                Quad tree = q.remove(0);

                if(tree!=null && tree.nodearr.size() !=0){
//                    createidToinv(tree);

                }

                same_level.add(tree);

                if (tree.topLeftTree != null) {
                    q.add(tree.topLeftTree);
                }
                if (tree.topRightTree != null) {
                    q.add(tree.topRightTree);
                }

                if (tree.botRightTree != null) {
                    q.add(tree.botRightTree);
                }
                if (tree.botLeftTree != null) {
                    q.add(tree.botLeftTree);
                }

            }
            System.out.println(same_level.toString());
        }
//        return res;

    }

//    public void createidToinv(Quad tree){
//        ArrayList<String> invList = new ArrayList<>();
//        ConcurrentHashMap<Integer,ArrayList<String>> hashMap = new ConcurrentHashMap<>();
//
//        for(Node node : tree.nodearr){
//            hashMap.put(node.getId(),invList);
//        }
//
//    }

    // object textual index
    public void createOti(Quad tree){
        ConcurrentHashMap<Integer,String> hashMap = new ConcurrentHashMap<>();

        // read the original data file and for each object search for it in the quadtree then add it's id and textfile name into oti hashmap
        String filePath = "C:\\Users\\dhruv\\Projects\\SpatialProject\\U-ask_KNN_spatialQuery\\Test\\Data\\data1.txt";


        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int id=Integer.MIN_VALUE;
            double x;
            double y;
            List<String> lastWordsList = new ArrayList<>();
            Node node = new Node();
            while ((line = reader.readLine()) != null) {



                String lastWords = extractLastWords(line);


                String[] values = line.split(" ");
                if (values.length >= 3) {
                    id = Integer.parseInt(values[0]);
                    x = Double.parseDouble(values[1]);
                    y = Double.parseDouble(values[2]);
                    node.setId(id);
                    node.setPos(new Point(x,y));

                }
                if (tree !=null && lastWords != null && !lastWords.isEmpty()) {
                    // creating empty idToInv as we read each object from original dataset
//                    wordWeightPairs = new ArrayList<>();
//                    this.idToInv.put(node.getId(),wordWeightPairs);

                    insertIntoOti(tree, node,lastWords);

                }

            }

//            System.out.println("inv list: " + this.invList);
//            System.out.println("inv set: " + this.invSet);




            // Print or process the extracted last words
            for (String words : lastWordsList) {
                System.out.println(words + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void insertIntoOti(Quad root, Node node, String text){
        ArrayList<IdWeightPair> idWeightPairs = new ArrayList<>();
        HashSet<Integer> objIdSet = new HashSet<>();
        ConcurrentHashMap<String,ArrayList<IdWeightPair>> invList = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, HashSet<Integer>> invSet = new ConcurrentHashMap<>();


        SearchResult sr = root.searchObject(root, node);
        String filename = sr.getTree().oti.get(sr.getObjectId());
        writeToFile(filename, sr.getObjectId(), text);

    }

    public void writeToFile(String filename,int id, String data){


        System.out.println("file path is: " + filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            // The 'true' argument in FileWriter's constructor enables append mode.
            writer.newLine();
            writer.write(id + " " + data);
            System.out.println("Text appended successfully.");

        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
        }
    }

    public void buildInvertedIndex(Quad root, int id, String words, ConcurrentHashMap<String,ArrayList<IdWeightPair>> invList, ConcurrentHashMap<String, HashSet<Integer>> invSet, ArrayList<IdWeightPair> idWeightPairs, HashSet<Integer> objIdSet){


        String[] wordsArr = words.split("\\s+");
        HashSet<String> processedValues = new HashSet<>();
        for(String word: wordsArr){
            if (!processedValues.contains(word)) {
                double weight = calculateWeight(word, wordsArr);
                IdWeightPair idWpair = new IdWeightPair(id, weight);

                if (invList.containsKey(word)) {
                    System.out.println("key present: " + word);
                    invList.get(word).add(idWpair);

                } else {
                    idWeightPairs = new ArrayList<>();
                    idWeightPairs.add(idWpair);
                    invList.put(word, idWeightPairs);
                }

                Quadruple tuple = new Quadruple();

                Optional<IdWeightPair> opt = invList.get(word).stream().max(Comparator.comparingDouble(IdWeightPair::getWeight));

                tuple.setWmax(opt.get().getWeight());
                tuple.setWsize(invList.get(word).size());

                if(!invSet.containsKey(word)){
                    objIdSet = new HashSet<>();
                    objIdSet.add(id);
                    invSet.put(word,objIdSet);
                }
                processedValues.add(word);
            }
        }


    }

    public double calculateWeight(String word, String[] wordsArr){
        int count = 0;
        if (wordsArr != null) {
            for (String element : wordsArr) {
                if (word.equals(element)) {
                    count++;
                }
            }
        }
        double weight = 0.0;
        weight = (double) count /wordsArr.length;
        return weight;
    }

//    public double findMax(ConcurrentHashMap<String,ArrayList<IdWeightPair>>  invlist){
//        double wmax=Double.MIN_VALUE;
//        for(ArrayList<IdWeightPair> idWeightPairs1 : invList.values()) {
//            for(IdWeightPair idp: idWeightPairs1){
//                if(idp.getWeight()>wmax){
//                    wmax = idp.getWeight();
//                }
//            }
//        }
//        return wmax;
//    }
    public String extractLastWords(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.trim().split("\\s+"); //split by any number of spaces

        if (parts.length > 0) {
            StringBuilder lastWords = new StringBuilder();
            boolean wordStarted = false;

            for (int i = parts.length - 1; i >= 0; i--) {

                if(Character.isLetter(parts[i].charAt(0))){
                    if(wordStarted){
                        lastWords.insert(0, " ");
                        lastWords.insert(0, parts[i]);
                    }
                    else{
                        lastWords.insert(0, parts[i]);
                        wordStarted = true;
                    }
                }
                else if (wordStarted){
                    break;
                }
            }

            return lastWords.toString();
        }

        return null;
    }

}
