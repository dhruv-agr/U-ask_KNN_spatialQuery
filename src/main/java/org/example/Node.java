package org.example;

class Node {
    Point pos;

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    Node(Point _pos, int _data) {
        pos = _pos;
        id = _data;
    }

    Node() {
        id = 0;
    }

    @Override
    public String toString() {
        String res = getPos() + " " + getId();
        return res;
    }
}
