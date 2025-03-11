package org.example;
//import jree;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        Quad root = new Quad(new Point(0, 0), new Point(8, 8));
        Node n1 = new Node(new Point(1, 1), 1);
        Node n2 = new Node(new Point(2, 5), 2);
        Node n3 = new Node(new Point(7, 6), 3);
        Node n4 = new Node(new Point(2, 7), 3);
        Node n5 = new Node(new Point(3, 4), 3);
        Node n6 = new Node(new Point(6, 2), 3);
        Node n7 = new Node(new Point(7, 7), 7);
        Node n8 = new Node(new Point(3, 6), 6);
        Node n9 = new Node(new Point(5, 5), 5);
        Node n10 = new Node(new Point(5, 6), 9);
        Node n11 = new Node(new Point(5, 7), 6);


        root.insert(root, n1);
        root.insert(root, n2);
        root.insert(root, n3);
        root.insert(root, n4);
        root.insert(root, n5);
        root.insert(root, n6);

        root.insert(root, n7);
        root.insert(root, n8);
        root.insert(root, n9);
        root.insert(root, n10);
        root.insert(root, n11);


        root.levelOrder(root);


        }

}


