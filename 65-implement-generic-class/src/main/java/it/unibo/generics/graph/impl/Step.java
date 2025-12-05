package it.unibo.generics.graph.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a step in a graph path.
 *
 * @param <N> node's type.
 */
record Step<N>(Step<N> previous, N current) {
    /**
     * Builds a step in a graph path.
     *
     * @param previous all the previous nodes.
     * @param current  the current (and last) node in the path
     */
    Step(final Step<N> previous, final N current) {
        this.previous = previous;
        this.current = Objects.requireNonNull(current);
    }

    /**
     * Builds the initial step in a graph path.
     *
     * @param current the first node in the path.
     */
    Step(final N current) {
        this(null, current);
    }

    /**
     *
     * @return the complete path from the initial to the current position.
     */
    List<N> getPath() {
        final List<N> path = new LinkedList<>();
        Step<N> tmp = this;
        while (tmp != null) {
            path.addFirst(tmp.current);
            tmp = tmp.previous;
        }
        return path;
    }

}
