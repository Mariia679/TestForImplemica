package com.implemica.second;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Floyd-Warshall algorithm is used to find the shortest path between
 * all pairs of nodes in a weighted graph with either positive or negative
 * edge weights but without negative edge cycles.
 *
 * https://en.wikipedia.org/wiki/Floyd–Warshall_algorithm
 */

public class FloydWarshallAlgorithm {

    /**
     * A 2-dimensional matrix is an contiguity matrix. At each step in the
     * algorithm, D[i][j] is the shortest path from i to j using intermediate
     * vertices {1..k−1}. All weights of paths is initialized to
     * <code>initializeWeight(final Node[] nodes, final Edge[] edges) </code>
     * method.
     */
    private final int[][] D;
    /**
     * A 2-dimensional matrix is an precedence matrix. At each step in the
     * algorithm, P[i][j] is defined as the peak prior to the top of j on the
     * shortest path from apex i to apex j with intermediate vertices in the
     * set {1, 2, ..., k}
     */
    private final Node[][] P;

    /**
     * Create an instance of this class by describing the graph upon which it
     * will operate. <p> Note
     * <code>Node.id</code> must contain the index of the node in the
     * <code>nodes</code> parameter. Thus
     * <code>Node[1].id</code> must equal one.
     *
     * @param nodes array of Node; must be completely populated
     * @param edges array of Edge, completely populated; order is not important
     */
    public FloydWarshallAlgorithm(final Node[] nodes, final Edge[] edges) {
        D = initializeWeight(nodes, edges);
        P = new Node[nodes.length][nodes.length];
        /**
         * Add all vertices one by one to the set of intermediate
         * vertices.
         * Before start of a iteration, we have shortest
         * distances between all pairs of vertices such that
         * the shortest distances consider only the vertices in
         * set {0, 1, 2, .. k-1} as intermediate vertices.
         * After the end of a iteration, vertex no. k is added
         * to the set of intermediate vertices and the set
         * becomes {0, 1, 2, .. k}
         */

        for (int k = 0; k < nodes.length; k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < nodes.length; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (int j = 0; j < nodes.length; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of D[i][j]
                    if (D[i][k] != Integer.MAX_VALUE
                            && D[k][j] != Integer.MAX_VALUE
                            && D[i][k] + D[k][j] < D[i][j]) {
                        D[i][j] = D[i][k] + D[k][j];
                        P[i][j] = nodes[k];
                    }
                }
            }
        }
    }

    public FloydWarshallAlgorithm(final List<Node> nodes, final List<Edge> edges) {
        this(nodes.toArray(new Node[0]), edges.toArray(new Edge[0]));
    }

    /**
     * Determines the length of the shortest path from top A (source) to
     * top B (target), calculated by summing the weights of the edges
     * traversed. <p> Note that distance, like path, is not commutative. That
     * is, distance(A,B) is not necessarily equal to distance(B,A).
     *
     * @param source Start Node
     * @param target End Node
     * @return The path length as the sum of the weights of the edges traversed,
     * or
     * <code>Integer.MAX_VALUE</code> if there is no path
     */
    public int getShortestDistance(final Node source, final Node target) {
        return D[source.id][target.id];
    }

    public int getShortestDistance(final Edge rute) {
        int d = getShortestDistance(rute.getFrom(), rute.getTo());
        rute.setWeight(d);

        return d;
    }

    /**
     * Describes the shortest path from top A (source) to top B (target)
     * by returning a collection of the vertices traversed, in the order
     * traversed. If there is no such path an empty collection is returned. <p>
     * Note that because each Edge applies only to one direction of traverse,
     * the path from A to B may not be the same as the path from B to A.
     *
     * @param source the start top
     * @param target the end top
     * @return A List (ordered Collection) of Node, possibly empty
     */
    public List<Node> getShortestPath(final Node source, final Node target) {
        if (D[source.id][target.id] == Integer.MAX_VALUE) {
            return new ArrayList<Node>(); // no path
        }
        final List<Node> path = getIntermediatePath(source, target);
        path.add(0, source);
        path.add(target);
        return path;
    }

    public List<Node> getShortestPath(final Edge rute) {
        return getShortestPath(rute.getFrom(), rute.getTo());
    }

    /**
     * This method constructs path from top
     * <code>source</code> to top
     * <code>target</code>.
     *
     * @param source the start top
     * @param target the end top
     * @return A List (ordered Collection) of Node, possibly empty
     */
    private List<Node> getIntermediatePath(final Node source, final Node target) {
        if (P[source.id][target.id] == null) {
            return new ArrayList<Node>();
        }
        final List<Node> path = new ArrayList<Node>();
        path.addAll(getIntermediatePath(source, P[source.id][target.id]));
        path.add(P[source.id][target.id]);
        path.addAll(getIntermediatePath(P[source.id][target.id], target));
        return path;
    }

    /**
     * This method constructs adjacency matrix. Infinity is equal to value of
     * Integer.MAX_VALUE. Size of occupied space is Node.length**2.
     *
     * @param nodes array of Node; must be completely populated
     * @param edges array of Edge, completely populated; order is not important
     * @return A 2-dimensional matrix is an adjacency matrix.
     */
    private int[][] initializeWeight(final Node[] nodes, final Edge[] edges) {
        int[][] W = new int[nodes.length][nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            Arrays.fill(W[i], Integer.MAX_VALUE);
        }
        for (Edge e : edges) {
            W[e.getFrom().id][e.getTo().id] = e.getWeight();

        }
        return W;
    }
}
