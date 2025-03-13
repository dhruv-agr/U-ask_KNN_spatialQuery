package org.example;

class Point {

    double x, y;

    Point(double _x, double _y) {
        x = _x;
        y = _y;
    }

    Point() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        String res = "(" + getX() + "," + getY() + ")";
        return res;
    }
}
