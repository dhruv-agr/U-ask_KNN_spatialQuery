package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Quad {
    Point topLeft;
    Point botRight;
    ArrayList<Node> nodearr = new ArrayList<Node>();
    ArrayList<Quad> neigh = new ArrayList<>();

    Quad topLeftTree;
    Quad topRightTree;
    Quad botLeftTree;
    Quad botRightTree;
    int capacity;
    String name;

    // Default constructor
    public Quad() {
        topLeft = new Point(0, 0);
        botRight = new Point(0, 0);


        topLeftTree = null;
        topRightTree = null;
        botLeftTree = null;
        botRightTree = null;
    }

    // Parameterized constructor
    public Quad(Point topL, Point botR) {

        topLeftTree = null;
        topRightTree = null;
        botLeftTree = null;
        botRightTree = null;
        topLeft = topL;
        botRight = botR;
        name ="";
    }

    void insert(Quad root, Node node) {
        if (root.nodearr.size() < 4 && root.topLeftTree == null && root.topRightTree == null
                && root.botLeftTree == null && root.botRightTree == null) {
            root.nodearr.add(node);
        } else if (root.nodearr.size() == 4 && root.topLeftTree == null && root.topRightTree == null
                && root.botLeftTree == null && root.botRightTree == null) {
            // curr node is full and no children so split
            split(root, node);
        } else {
            //children exist

            root.checkQuadAndCreateIfNot(root,node);

        }
    }

    void split(Quad root, Node newNode) {

        root.topLeftTree = new Quad(
                new Point(root.topLeft.x, root.topLeft.y),
                new Point((root.topLeft.x + root.botRight.x) / 2, (root.topLeft.y + root.botRight.y) / 2));
        root.topLeftTree.name = root.name + "-TL";


        root.botLeftTree = new Quad(
                new Point(root.topLeft.x, (root.topLeft.y + root.botRight.y) / 2),
                new Point((root.topLeft.x + root.botRight.x) / 2, root.botRight.y));
        root.botLeftTree.name = root.name + "-BL";


        root.topRightTree = new Quad(
                new Point((root.topLeft.x + root.botRight.x) / 2, root.topLeft.y),
                new Point(root.botRight.x, (root.topLeft.y + root.botRight.y) / 2));
        root.topRightTree.name = root.name + "-TR";


        root.botRightTree = new Quad(
                new Point((root.topLeft.x + root.botRight.x) / 2, (root.topLeft.y + root.botRight.y) / 2),
                new Point(root.botRight.x, root.botRight.y));
        root.botRightTree.name = root.name + "-BR";



        // add and modify neighbours
        root.topLeftTree.neigh.add(root.topRightTree);
        root.topLeftTree.neigh.add(root.botRightTree);
        root.topLeftTree.neigh.add(root.botLeftTree);
        addNeighboursFromParent(root, root.topLeftTree);
        // add and modify neighbours
        root.botLeftTree.neigh.add(root.topLeftTree);
        root.botLeftTree.neigh.add(root.topRightTree);
        root.botLeftTree.neigh.add(root.botRightTree);
        addNeighboursFromParent(root, root.botLeftTree);
        // add and modify neighbours
        root.topRightTree.neigh.add(root.topLeftTree);
        root.topRightTree.neigh.add(root.botLeftTree);
        root.topRightTree.neigh.add(root.botRightTree);
        addNeighboursFromParent(root, root.topRightTree);
        // add and modify neighbours
        root.botRightTree.neigh.add(root.topLeftTree);
        root.botRightTree.neigh.add(root.botLeftTree);
        root.botRightTree.neigh.add(root.topRightTree);
        addNeighboursFromParent(root, root.botRightTree);

        // remove from curr node and move to child nodes
        while (!root.nodearr.isEmpty()) {

            Node node = root.nodearr.removeFirst();

            root.checkQuadAndCreateIfNot(root, node);
        }

        // add new node
        root.insert(root, newNode);
    }


    public void levelOrder(Quad root) {

        ArrayList<Quad> q = new ArrayList<Quad>();
        q.add(root);
        while (!q.isEmpty()) {

            ArrayList<Quad> same_level = new ArrayList<>();
            int qsize = q.size();
            for (int i = 0; i < qsize; i++) {
                Quad tree = q.remove(0);

                if(tree.nodearr.size() !=0){
                    createHashMap(tree);
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

    public void checkQuadAndCreateIfNot(Quad root, Node node){
        // left quadrants
        if ((root.topLeft.x + root.botRight.x) / 2 >= node.pos.x) {
            if ((root.topLeft.y + root.botRight.y) / 2 >= node.pos.y) {
                // Indicates topLeftTree

                    insert(root.topLeftTree, node);

            } else {
                // Indicates botLeftTree

                    insert(root.botLeftTree, node);

            }
            // right quadrants
        } else {
            if ((root.topLeft.y + root.botRight.y) / 2 >= node.pos.y) {
                // Indicates topRightTree

                    insert(root.topRightTree, node);

            } else {
                // Indicates botRightTree

                    insert(root.botRightTree, node);

            }
        }
    }

    public void addNeighboursFromParent(Quad parent, Quad child){

        for(Quad q : parent.neigh){
            // horizontal, vertical and diagonal adjacency
            if(q !=null){
                if( ((q.botRight.x == child.topLeft.x || q.topLeft.x == child.botRight.x) && ((q.topLeft.y <= child.botRight.y) && (q.botRight.y >= child.topLeft.y)))

                        || ((q.topLeft.y==child.botRight.y || q.botRight.y == child.topLeft.y) && ((q.topLeft.x <= child.botRight.x) && (q.botRight.x>=child.topLeft.x)))

                        || ((q.botRight.x == child.topLeft.x && q.botRight.y == child.topLeft.y)
                        || (q.topLeft.x == child.botRight.x && q.botRight.y == child.topLeft.y)
                        || (q.botRight.x == child.topLeft.x && q.topLeft.y == child.botRight.y)
                        || (q.topLeft.x == child.botRight.x && q.topLeft.y == child.botRight.y))
                ){
                    child.neigh.add(q);
                }
            }


        }

    }

    public void createHashMap(Quad tree){
        ConcurrentHashMap<Integer,Point> hashMap = new ConcurrentHashMap<>();

        for(Node node : tree.nodearr){
            hashMap.put(node.getId(),node.getPos());
        }
        String filename = "C:\\Users\\dhruv\\Projects\\SpatialProject\\U-ask_KNN_spatialQuery\\Test\\Data\\output\\" +tree.name +".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<Integer, Point> entry : hashMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
            System.out.println("ConcurrentHashMap saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        ArrayList<String> neighNames = new ArrayList<>();
        for(Quad n:this.neigh){
            neighNames.add(n.name);
        }
        String res = this.name + " Nodes:" + this.nodearr + " Neigh:" + neighNames;
        return res;
    }


}

