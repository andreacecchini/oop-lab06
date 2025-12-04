package it.unibo.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Example class using {@link List} and {@link Map}.
 *
 */
public final class UseListsAndMaps {

    private static final int START = 1_000;
    private static final int END = 2_000;
    private static final int N_ADDS = 100_000;
    private static final int N_READS = 1_000;
    private static final long AFRICA = 1_110_635_000L;
    private static final long AMERICA = 972_005_000L;
    private static final long ANTARCTICA = 0L;
    private static final long ASIA = 4_298_723_000L;
    private static final long EUROPE = 742_452_000L;
    private static final long OCEANIA = 38_304_000L;

    private UseListsAndMaps() {
    }

    /**
     * @param s
     *          unused
     */
    public static void main(final String... s) {
        /*
         * 1) Create a new ArrayList<Integer>, and populate it with the numbers
         * from 1000 (included) to 2000 (excluded).
         */
        final List<Integer> arrayList = new ArrayList<>(END - START);
        for (var i = START; i < END; i++) {
            arrayList.add(i);
        }
        /*
         * 2) Create a new LinkedList<Integer> and, in a single line of code
         * without using any looping construct (for, while), populate it with
         * the same contents of the list of point 1.
         */
        final List<Integer> linkedList = new LinkedList<>(arrayList);
        /*
         * 3) Using "set" and "get" and "size" methods, swap the first and last
         * element of the first list. You can not use any "magic number".
         * (Suggestion: use a temporary variable)
         */
        final Integer tmp = arrayList.getFirst();
        arrayList.set(0, arrayList.getLast());
        arrayList.set(arrayList.size() - 1, tmp);
        /*
         * 4) Using a single for-each, print the contents of the arraylist.
         */
        final StringBuilder builder = new StringBuilder();
        for (final var i : arrayList) {
            builder.append(i).append(", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        log(builder.toString());
        /*
         * 5) Measure the performance of inserting new elements in the head of
         * the collection: measure the time required to add 100.000 elements as
         * first element of the collection for both ArrayList and LinkedList,
         * using the previous lists. In order to measure times, use as example
         * TestPerformance.java.
         */
        final Consumer<List<Integer>> insert = l -> {
            for (var i = 0; i < N_ADDS; i++) {
                l.addFirst(i);
            }
        };
        final var t1Insert = benchmark(arrayList, insert);
        final var t2Insert = benchmark(linkedList, insert);
        log(String.format("ArrayList took %d ns to insert %d elements", t1Insert, N_ADDS));
        log(String.format("LinkedList took %d ns to insert %d elements", t2Insert, N_ADDS));
        /*
         * 6) Measure the performance of reading 1000 times an element whose
         * position is in the middle of the collection for both ArrayList and
         * LinkedList, using the collections of point 5. In order to measure
         * times, use as example TestPerformance.java.
         */
        final Consumer<List<Integer>> read = l -> {
            for (var i = 0; i < N_READS; i++) {
                l.get(l.size() / 2);
            }
        };
        final var t1Read = benchmark(arrayList, read);
        final var t2Read = benchmark(linkedList, read);
        log(String.format("ArrayList took %d ns to read %d elements", t1Read, N_READS));
        log(String.format("LinkedList took %d ns to read %d elements", t2Read, N_READS));
        /*
         * 7) Build a new Map that associates to each continent's name its
         * population:
         *
         * Africa -> 1,110,635,000
         *
         * Americas -> 972,005,000
         *
         * Antarctica -> 0
         *
         * Asia -> 4,298,723,000
         *
         * Europe -> 742,452,000
         *
         * Oceania -> 38,304,000
         */
        final Map<String, Long> world = new HashMap<>();
        world.put("Africa", AFRICA);
        world.put("Americas", AMERICA);
        world.put("Antarctica", ANTARCTICA);
        world.put("Asia", ASIA);
        world.put("Europe", EUROPE);
        world.put("Oceania", OCEANIA);
        /*
         * 8) Compute the population of the world
         */
        long worldPopulation = 0;
        for (final var continent : world.values()) {
            worldPopulation += continent;
        }
        log(String.format("There are %d people around the world", worldPopulation));
    }

    private static long benchmark(final List<Integer> l, final Consumer<List<Integer>> action) {
        final long time = System.nanoTime();
        action.accept(l);
        return System.nanoTime() - time;
    }

    private static void log(final String msg) {
        System.out.println(msg);
    }
}
