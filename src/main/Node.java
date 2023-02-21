package main;

public class Node implements Comparable<Node> {
    public Pitchers pitchers;
    public Node parent;
    public int G;
    public double H;
    public double F;

    public Node(Pitchers p) {
        pitchers = p;
    }

    public Node(Pitchers p, Node n, int g, double h, double f) {
        pitchers = p;
        parent = n;
        G = g;
        H = h;
        F = f;
    }

    @Override
    public int compareTo(Node n) {
        if (n == null) return -1;
        if (F > n.F)
            return 1;
        else if (F < n.F) return -1;
        return 0;
    }
}


