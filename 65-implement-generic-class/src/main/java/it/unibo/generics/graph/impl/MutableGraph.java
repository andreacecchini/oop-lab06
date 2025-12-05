package it.unibo.generics.graph.impl;

import it.unibo.generics.graph.api.FringeAccumulationStrategy;
import it.unibo.generics.graph.api.Graph;

import java.util.*;


/**
 * Implements a mutable {@link Graph}
 *
 * @param <N> node's type
 */
public class MutableGraph<N> implements Graph<N> {

    private final Map<N, Set<N>> edges = new HashMap<>();
    private final FringeAccumulationStrategy<Step<N>> updateFringeStrategy;

    /**
     * Builds an empty mutable {@link Graph}.
     */
    private MutableGraph(final FringeAccumulationStrategy<Step<N>> updateFringeStrategy) {
        this.updateFringeStrategy = updateFringeStrategy;
    }

    /**
     *
     * @param <N> node's type.
     * @return an empty {@link MutableGraph} with BFS as visit strategy.
     */
    public static <N> MutableGraph<N> withBreathFirst() {
        return new MutableGraph<>(Deque::addLast);
    }

    /**
     *
     * @param <N> node's type.
     * @return an empty {@link MutableGraph} with DFS as visit strategy.
     */
    public static <N> MutableGraph<N> withDepthFirst() {
        return new MutableGraph<>(Deque::addFirst);
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
        return Set.copyOf(this.edges.get(node));
    }

    @Override
    public List<N> getPath(N source, N target) {
        if (nodesExist(source, target)) {
            return graphVisit(source, target);
        }
        return List.of();
    }

    private List<N> graphVisit(N source, N target) {
        final Deque<Step<N>> fringe = new LinkedList<>();
        final Set<N> visited = new HashSet<>();
        fringe.add(new Step<>(source));
        while (!fringe.isEmpty()) {
            final Step<N> lastPath = fringe.removeLast();
            final N current = lastPath.current();
            if (current.equals(target)) {
                return lastPath.getPath();
            }
            if (!visited.contains(current)) {
                visited.add(current);
                for (final N neighbor : linkedNodes(current)) {
                    // Add to the fringe with the specific strategy
                    updateFringeStrategy.addToFringe(fringe, new Step<>(lastPath, neighbor));
                }
            }
        }
        return List.of();
    }

    @SafeVarargs
    private boolean nodesExist(final N... nodes) {
        final Set<N> nodeSet = nodeSet();
        for (final N node : nodes) {
            if (!nodeSet.contains(node)) {
                throw new IllegalArgumentException("node %s does not exist in graph".formatted(node));
            }
        }
        return true;
    }

}
