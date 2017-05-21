package com.implemica.second;

/**
 * The Edge object is the edge graph.
 */
public class Edge {

    private final Node from;

    private final Node to;

    /**
     * <code>weight</code> is the edge weight from the graph top
     */
    private int weight;

    /**
     * @param from   the graph top that starts from the edge.
     * @param to     the graph top that ends from the edge.
     * @param weight the edge weight.
     */
    public Edge(final Node from, final Node to, final int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return String.format("[%s -> %s, w=%d]", getFrom(), getTo(), getWeight());
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @return the from
     */
    public Node getFrom() {
        return from;
    }

    /**
     * @return the to
     */
    public Node getTo() {
        return to;
    }
}
