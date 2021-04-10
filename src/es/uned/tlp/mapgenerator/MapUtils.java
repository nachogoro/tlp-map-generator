package es.uned.tlp.mapgenerator;

import java.util.*;

public class MapUtils {
    private static final Random rgen = new Random();

    /**
     * Returns a random integer in the [min, max] range.
     * @param min Minimum value of the integer (inclusive)
     * @param max Maximum value of the integer (inclusive)
     * @return A random integer in the [min, max] range.
     */
    public static int getRandomIntInclusive(int min, int max) {
        final int maxValueExcluded = max + 1;
        return rgen.nextInt(maxValueExcluded - min) + min;
    }

    /**
     * Returns a string representation of the given map.
     * @param map Map.
     * @return String representation of the given map.
     */
    public static String mapToString(final int[][] map, final boolean multiline) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (int row = 0; row < map.length; ++row) {
            sb.append("[");
            for (int col = 0; col < map[0].length; ++col) {
                if (col != 0) {
                    sb.append(",");
                }
                sb.append(map[row][col]);
            }
            sb.append("]");

            if (row != map.length - 1) {
                sb.append(",");

                if (multiline) {
                    sb.append("\n");
                }
            }
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * Returns the adjacencies of a map.
     */
    public static Map<Integer, SortedSet<Integer>> getAdjacencies(final int[][] map) {
        final Map<Integer, SortedSet<Integer>> adjacencies = new HashMap<>();

        for (int row = 0; row < map.length; ++row) {
            for (int col = 0; col < map[0].length; ++col) {
                final int thisRegion = map[row][col];
                if (col != map[0].length - 1) {
                    // We can look to the left
                    final int toTheLeft = map[row][col+1];
                    if (thisRegion != toTheLeft) {
                        addAdjacency(adjacencies, thisRegion, toTheLeft);
                    }
                }

                if (row != map.length - 1) {
                    // We can look downwards
                    final int down = map[row+1][col];
                    if (thisRegion != down) {
                        addAdjacency(adjacencies, thisRegion, down);
                    }
                }
            }
        }

        return adjacencies;
    }

    /**
     * Adds an adjacency to the given adjacencies map.
     * @param adjacencies Map in which adjacencies must be added.
     * @param region One of the adjacent regions.
     * @param otherRegion The other adjacent region.
     */
    private static void addAdjacency(
            final Map<Integer, SortedSet<Integer>> adjacencies,
            final int region,
            final int otherRegion) {
        final int mainRegion = Math.max(region, otherRegion);
        final int adjacentRegion = Math.min(region, otherRegion);

        if (!adjacencies.containsKey(mainRegion)) {
            adjacencies.put(mainRegion, new TreeSet<>());
        }
        adjacencies.get(mainRegion).add(adjacentRegion);
    }

    /**
     * Returns a string representation of the adjacencies, in a format compatible with
     * the test tool provided by the professors.
     * @param adjacencies Adjacencies map.
     * @return A string representation of the adjacencies map.
     */
    public static String adjacenciesToString(final Map<Integer, SortedSet<Integer>> adjacencies) {
        final List<String> individualAdjacencies = new ArrayList<>();

        final List<Integer> mainRegions = new ArrayList<>(adjacencies.keySet());
        Collections.sort(mainRegions);

        for (Integer region : mainRegions) {
            individualAdjacencies.add(String.format(
                    "(%d,[%s])",
                    region, setToCommaSeparatedString(adjacencies.get(region))));
        }

        return "[" + String.join(",", individualAdjacencies) + "]";
    }

    /**
     * Returns a comma-separated string of the elements in the set.
     * @param set Set containing the elements.
     * @return A comma comma-separated string of the elements in the set.
     */
    private static String setToCommaSeparatedString(SortedSet<Integer> set) {
        final StringBuilder sb = new StringBuilder();

        Iterator<Integer> it = set.iterator();

        while (it.hasNext()) {
            sb.append(it.next()).append(",");
        }

        final String withExtraComma = sb.toString();
        return withExtraComma.substring(0, withExtraComma.length() - 1);
    }
}
