import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private List<Node<T>> children = new ArrayList<Node<T>>();
    private Node<T> parent = null;
    private T data = null;
    private int rank;

    public Node() {
    	this.data = null;
    	this.rank = Integer.MIN_VALUE;
    }
    
    public Node(T data, int rank) {
        this.data = data;
        this.rank = rank;
    }

    public Node(T data, int rank, Node<T> parent) {
        this.data = data;
        this.parent = parent;
        this.rank = rank;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setParent(Node<T> parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(T data, int rank) {
        Node<T> child = new Node<T>(data, rank);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public T getMove() {
        return this.data;
    }

    public int getRank() {
    	return rank;
    }
    
    public void setMove(T data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        if(this.children.size() == 0) 
            return true;
        else 
            return false;
    }

    public void removeParent() {
        this.parent = null;
    }
}

