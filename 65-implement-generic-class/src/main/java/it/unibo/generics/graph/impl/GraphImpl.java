package it.unibo.generics.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
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
        if (source == null || target == null) {
            return null;
        }
        if (!this.edges.containsKey(source) || !this.edges.containsKey(target)) {
            return null;
        }
        if (source.equals(target)) {
            return List.of(source);
        }
        // BFS to find path
        final Queue<N> queue = new LinkedList<>();
        final Map<N, N> predecessors = new HashMap<>();
        final Set<N> visited = new HashSet<>();
        
        queue.add(source);
        visited.add(source);
        
        while (!queue.isEmpty()) {
            final N current = queue.poll();
            for (final N neighbor : this.edges.getOrDefault(current, Set.of())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    predecessors.put(neighbor, current);
                    if (neighbor.equals(target)) {
                        // Found target, reconstruct path
                        return reconstructPath(source, target, predecessors);
                    }
                    queue.add(neighbor);
                }
            }
        }
        // No path found
        return null;
    }

    private List<N> reconstructPath(final N source, final N target, final Map<N, N> predecessors) {
        final List<N> path = new ArrayList<>();
        N current = target;
        while (current != null) {
            path.add(0, current);
            if (current.equals(source)) {
                break;
            }
            current = predecessors.get(current);
        }
        return path;
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
