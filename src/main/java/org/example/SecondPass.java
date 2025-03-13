package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SecondPass {
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
                    createidToinv(tree);

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

    public void createidToinv(Quad tree){
        ArrayList<String> invList = new ArrayList<>();
        ConcurrentHashMap<Integer,ArrayList<String>> hashMap = new ConcurrentHashMap<>();

        for(Node node : tree.nodearr){
            hashMap.put(node.getId(),invList);
        }

    }

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
                    insertIntoOti(tree, node,lastWords);
                }

            }

            // Print or process the extracted last words
            for (String words : lastWordsList) {
                System.out.println(words + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void insertIntoOti(Quad root, Node node, String text){

        if(!root.nodearr.isEmpty()){
            for(Node n : root.nodearr){
                if(n.getId() == node.getId()){
                    String filename = root.oti.get(node.getId());
                    writeToFile(filename, node.getId(), text);
                    return;
                }
            }
        }

        // left quadrants
        if ((root.topLeft.x + root.botRight.x) / 2 >= node.pos.x) {
            if ((root.topLeft.y + root.botRight.y) / 2 >= node.pos.y) {
                // Indicates topLeftTree

                insertIntoOti(root.topLeftTree, node,text);

            } else {
                // Indicates botLeftTree

                insertIntoOti(root.botLeftTree, node, text);

            }
            // right quadrants
        } else {
            if ((root.topLeft.y + root.botRight.y) / 2 >= node.pos.y) {
                // Indicates topRightTree

                insertIntoOti(root.topRightTree, node, text);

            } else {
                // Indicates botRightTree

                insertIntoOti(root.botRightTree, node, text);

            }
        }
    }

    public void writeToFile(String filename,int id, String data){
        String filePath = filename; // Replace with your file path
        String textToAppend = data; // The text to add

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // The 'true' argument in FileWriter's constructor enables append mode.
            writer.newLine();
            writer.write(id + " " + textToAppend);
            System.out.println("Text appended successfully.");

        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
        }
    }

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
