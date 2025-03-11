package org.example;

class Node {
    Point pos;

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    int data;

    Node(Point _pos, int _data) {
        pos = _pos;
        data = _data;
    }

    Node() {
        data = 0;
    }

    @Override
    public String toString() {
        String res = getPos() + " " + getData();
        return res;
    }
}
