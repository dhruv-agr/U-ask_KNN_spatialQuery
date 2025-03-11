package org.example;

import java.util.ArrayList;
import java.util.List;

class Quad {
    Point topLeft;
    Point botRight;
    ArrayList<Node> nodearr = new ArrayList<Node>();

    Quad topLeftTree;
    Quad topRightTree;
    Quad botLeftTree;
    Quad botRightTree;
    int capacity;

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
        // remove from curr node and move to child nodes
        while (!root.nodearr.isEmpty()) {

            Node node = root.nodearr.removeFirst();

            root.checkQuadAndCreateIfNot(root, node);
        }

        // add new node
        root.insert(root, newNode);
    }

    public void levelOrder(Quad root) {
//        List<List<List<Node>>> res = new ArrayList<ArrayList<ArrayList<Node>>>();
//        if(root==null){
//            return res;
//        }

        ArrayList<Quad> q = new ArrayList<Quad>();
        q.add(root);
        while (!q.isEmpty()) {
            List<ArrayList<Node>> same_level = new ArrayList<ArrayList<Node>>();
            int qsize = q.size();
            for (int i = 0; i < qsize; i++) {
                Quad tree = q.remove(0);
                same_level.add(tree.nodearr);

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
//            res.add(same_level);
//            for(int i=0;i<same_level.size();i++){
//                System.out.print(same_level.get(i).toString());
//            }
            System.out.println(same_level.toString());
        }
//        return res;

    }

    public void checkQuadAndCreateIfNot(Quad root, Node node){
        // left quadrants
        if ((root.topLeft.x + root.botRight.x) / 2 >= node.pos.x) {
            if ((root.topLeft.y + root.botRight.y) / 2 >= node.pos.y) {
                // Indicates topLeftTree
                if (root.topLeftTree == null) {
                    root.topLeftTree = new Quad(
                            new Point(root.topLeft.x, root.topLeft.y),
                            new Point((root.topLeft.x + root.botRight.x) / 2, (root.topLeft.y + root.botRight.y) / 2));
                    root.topLeftTree.nodearr.add(node);
                } else {
                    insert(root.topLeftTree, node);
                }
            } else {
                // Indicates botLeftTree
                if (root.botLeftTree == null) {
                    root.botLeftTree = new Quad(
                            new Point(root.topLeft.x, (root.topLeft.y + root.botRight.y) / 2),
                            new Point((root.topLeft.x + root.botRight.x) / 2, root.botRight.y));
                    root.botLeftTree.nodearr.add(node);
                } else {
                    insert(root.botLeftTree, node);
                }
            }
            // right quadrants
        } else {
            if ((root.topLeft.y + root.botRight.y) / 2 >= node.pos.y) {
                // Indicates topRightTree
                if (root.topRightTree == null) {
                    root.topRightTree = new Quad(
                            new Point((root.topLeft.x + root.botRight.x) / 2, root.topLeft.y),
                            new Point(root.botRight.x, (root.topLeft.y + root.botRight.y) / 2));
                    root.topRightTree.nodearr.add(node);
                } else {
                    insert(root.topRightTree, node);
                }
            } else {
                // Indicates botRightTree
                if (root.botRightTree == null) {
                    root.botRightTree = new Quad(
                            new Point((root.topLeft.x + root.botRight.x) / 2, (root.topLeft.y + root.botRight.y) / 2),
                            new Point(root.botRight.x, root.botRight.y));
                    root.botRightTree.nodearr.add(node);
                } else {
                    insert(root.botRightTree, node);
                }
            }
        }
    }
}

