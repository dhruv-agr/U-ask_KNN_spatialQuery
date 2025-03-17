package org.example.dataTypes;

import org.example.Quad;

public class SearchResult {
    Quad tree;
    int objectId;

    public SearchResult(Quad tree, int objectId) {
        this.tree = tree;
        this.objectId = objectId;
    }

    public SearchResult() {

    }

    public Quad getTree() {
        return this.tree;
    }

    public void setTree(Quad tree) {
        this.tree = tree;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
}
