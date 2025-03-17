package org.example.dataTypes;

public class IdWeightPair {
    int id;
    double weight;

    public IdWeightPair(int id, double weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString(){
        return "(" + this.id + ", " + this.weight + ")";
    }
}
