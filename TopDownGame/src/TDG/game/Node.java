package TDG.game;

class Node {
    double F;
    double G;
    double H;
    Tile t;
    Node parentNode;
    public Node(double G, double H, Tile t, Node parentNode){
        this.F = G+H;
        this.G = G;
        this.H = H;
        this.t = t;
        this.parentNode = parentNode;
    }
    public Node(Tile t){
        this.t = t;
    }
    @Override
    public boolean equals(Object o){
        if(this.t==null){
            return false;
        }
        Node n = (Node)o;
        return n.t.equals(this.t);
    }
}
