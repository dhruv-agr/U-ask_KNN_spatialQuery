package org.example;
//import jree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        Quad root = new Quad(new Point(0, 0), new Point(8, 8));
//        Node n1 = new Node(new Point(1, 1), 1);
//        Node n2 = new Node(new Point(2, 5), 2);
//        Node n3 = new Node(new Point(7, 6), 3);
//        Node n4 = new Node(new Point(2, 7), 3);
//        Node n5 = new Node(new Point(3, 4), 3);
//        Node n6 = new Node(new Point(6, 2), 3);
//        Node n7 = new Node(new Point(7, 7), 7);
//        Node n8 = new Node(new Point(3, 6), 6);
//        Node n9 = new Node(new Point(5, 5), 5);
//        Node n10 = new Node(new Point(5, 6), 9);
//        Node n11 = new Node(new Point(5, 7), 6);
//
//
//        root.insert(root, n1);
//        root.insert(root, n2);
//        root.insert(root, n3);
//        root.insert(root, n4);
//        root.insert(root, n5);
//        root.insert(root, n6);
//
//        root.insert(root, n7);
//        root.insert(root, n8);
//        root.insert(root, n9);
//        root.insert(root, n10);
//        root.insert(root, n11);




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
                    Node n = new Node(new Point(x, y), id);

                    root.insert(root, n);


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        root.levelOrder(root);

        // #########################################################     Second pass     ##############################################################
        // ############################################################################################################################################

        SecondPass sp = new SecondPass();
        sp.createOti(root);
//        System.out.println("id: " + id);
//        System.out.print("x: " + x);
//        System.out.print(" y: " + y);

//        POWER processor = new POWER(index, 8);
//
//        // Example query from paper
//        Set<String> posKeywords = Set.of("beauty");
//        Set<String> negPhrases = Set.of("blah");
//        List<Node> results = processor.executeTKQN(
//                33.81271974, -117.91897705, posKeywords, negPhrases, 10, 0.5
//        );
//
//        System.out.println("Top Results:");
//        results.forEach(obj -> System.out.printf(
//                "[ID: %s] Distance: %.2fm | Text: %s%n",
//                obj.getId(), obj.distanceTo(34.05, -118.24), obj.getText()
//        ));

        }

}


