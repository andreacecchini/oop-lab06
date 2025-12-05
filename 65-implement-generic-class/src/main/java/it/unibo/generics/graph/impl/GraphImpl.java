package it.unibo.generics.graph.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

final public class GraphImpl<N> implements Graph<N> {

    final Map<N, Set<N>> edges = new HashMap<>();

    /**
     * Build an empty graph.
     */
    public GraphImpl() {
    }

    @Override
    public void addNode(N node) {
        this.edges.putIfAbsent(Objects.requireNonNull(node), new HashSet<>());
    }

    @Override
    public void addEdge(N source, N target) {
        if (nodesExist(source, target)) {
            this.edges.get(source).add(target);
        }
    }

    @Override
    public Set<N> nodeSet() {
        return Set.copyOf(this.edges.keySet());
    }

    @Override
    public Set<N> linkedNodes(N node) {
        return Set.copyOf(this.edges.getOrDefault(Objects.requireNonNull(node), Set.of()));
    }

    @Override
    public List<N> getPath(N source, N target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPath'");
    }

    // private method, it is impossible for the user to cause heap pollution...
    @SafeVarargs
    private boolean nodesExist(final N... nodes) {
        for (final N node : nodes) {
            if (!this.edges.containsKey(node)) {
                throw new IllegalArgumentException("No such node: " + node);
            }
        }
        return true;
    }

}
