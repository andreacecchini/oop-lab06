package it.unibo.generics.graph.api;


import java.util.Deque;

/**
 * Provides a strategy to update the fringe
 * in order to change the behaviour of the visit.
 *
 * @param <S> step type
 */
@FunctionalInterface
public interface FringeAccumulationStrategy<S> {
    /**
     * Provides a way to add the step to the fringe.
     *
     * @param fringe the fringe.
     * @param step   the step to be added.
     */
    void addToFringe(Deque<S> fringe, S step);
}
