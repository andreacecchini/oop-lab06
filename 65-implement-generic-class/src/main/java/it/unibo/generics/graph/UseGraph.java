package it.unibo.generics.graph;

import it.unibo.generics.graph.api.Graph;
import it.unibo.generics.graph.impl.GraphImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public final class UseGraph {

    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private static final String D = "d";
    private static final String E = "e";

    private UseGraph() {
    }

    /**
     * @param args
     *            ignored
     */
    public static void main(final String... args) {
        /*
         * Test your graph implementation(s) by calling testGraph
         */
        testGraph(new GraphImpl<>());
    }

    private static void testGraph(final Graph<String> graph) {
        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(C);
        graph.addNode(D);
        graph.addNode(E);
        graph.addEdge(A, B);
        graph.addEdge(B, C);
        graph.addEdge(C, D);
        graph.addEdge(D, E);
        graph.addEdge(C, A);
        graph.addEdge(E, A);
        /*
         * Should be ["a","b","c","d","e"], in any order
         */
        assertIsAnyOf(graph.nodeSet(), Set.of(A, B, C, D, E));
        /*
         * ["d","a"], in any order
         */
        assertIsAnyOf(graph.linkedNodes(C), Set.of(A, D));
        /*
         * Either the path b,c,a or b,c,d,e,a
         */
        assertIsAnyOf(
            graph.getPath(B, A),
            Arrays.asList(B, C, A),
            Arrays.asList(B, C, D, E, A)
        );
    }

    private static void assertIsAnyOf(final Object actual, final Object... valid) {
        for (final var target: Objects.requireNonNull(valid)) {
            if (Objects.equals(target, actual)) {
                System.out.println("OK: " + actual + " matches " + target); // NOPMD
                return;
            }
        }
        throw new AssertionError("None of " + Arrays.asList(valid) + " matches " + actual);
    }
}
