package es.uned.tlp.mapgenerator;

import java.util.*;

/**
 * Class in charge of generating a map.
 */
public class MapGenerator {
    /**
     * A coordinate in a map.
     */
    private static class Coordinate {
        public final int x;
        public final int y;

        public Coordinate(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static final Random rgen = new Random();

    /**
     * Generates a map with the given parameters and returns its Haskell-compatible string representation
     * @param params Parameters for the map.
     * @return The string representation of the map.
     */
    public String generateMap(final MapParameters params) {
        final Set<Coordinate> availableCoords = new HashSet<>();

        // Generate all possible coordinates
        for (int row = 0; row < params.height; ++row) {
            for (int col = 0; col < params.width; ++col) {
                availableCoords.add(new Coordinate(col, row));
            }
        }

        final int[][] map = new int[params.height][params.width];
        int region = 1;

        while (availableCoords.size() > 0) {
            // Select a random coordinate to start the region among the available ones
            final Coordinate dropCoord = getRandomElement(availableCoords);

            // Randomly expand the region in the map from that coordinate
            expandRegion(map, dropCoord, region, availableCoords, params.tendencyToGrow);
            region++;
        }

        return mapToString(map);
    }

    /**
     * Selects a random element from the set.
     * Runs in linear time.
     * @param set Set from which the element is to be picked.
     * @return Element from the set picked at random.
     */
    private Coordinate getRandomElement(final Set<Coordinate> set) {
        final int nElem = getRandomIntInclusive(0, set.size() - 1);
        int i = 0;
        for (Coordinate c : set) {
            if (i == nElem) {
                return c;
            }
            ++i;
        }

        throw new IllegalArgumentException("Cannot pick from empty set!");
    }

    /**
     * Recursively expands the specified region in the map from the given coordinate.
     * @param map Map on which to expand.
     * @param coord Coordinate from which the region should expand.
     * @param region Region to be expanded.
     * @param availableCoords Coordinates which still have not been occupied by any region.
     * @param loadFactor Probability (1-100) that the region will expand in any possible direction.
     */
    private void expandRegion(
            final int[][] map,
            final Coordinate coord,
            final int region,
            final Set<Coordinate> availableCoords,
            final int loadFactor) {
        // Assign the selected coordinate to the specified region
        map[coord.y][coord.x] = region;

        // Remove the coordinate from the available coordinates
        availableCoords.remove(coord);

        // Decide if we should expand upwards...
        {
            final Coordinate upwardsCoord = new Coordinate(coord.x, coord.y - 1);
            if (availableCoords.contains(upwardsCoord) && flipLoadedCoin(loadFactor)) {
                expandRegion(map, upwardsCoord, region, availableCoords, loadFactor);
            }
        }

        // ... down-wards...
        {
            final Coordinate downwardsCoord = new Coordinate(coord.x, coord.y + 1);
            if (availableCoords.contains(downwardsCoord) && flipLoadedCoin(loadFactor)) {
                expandRegion(map, downwardsCoord, region, availableCoords, loadFactor);
            }
        }

        // ... to the left ...
        {
            final Coordinate leftCoord = new Coordinate(coord.x - 1, coord.y);
            if (availableCoords.contains(leftCoord) && flipLoadedCoin(loadFactor)) {
                expandRegion(map, leftCoord, region, availableCoords, loadFactor);
            }
        }

        // ... and to the right
        {
            final Coordinate rightCoord = new Coordinate(coord.x + 1, coord.y);
            if (availableCoords.contains(rightCoord) && flipLoadedCoin(loadFactor)) {
                expandRegion(map, rightCoord, region, availableCoords, loadFactor);
            }
        }
    }

    /**
     * Returns a random integer in the [min, max] range.
     * @param min Minimum value of the integer (inclusive)
     * @param max Maximum value of the integer (inclusive)
     * @return A random integer in the [min, max] range.
     */
    private int getRandomIntInclusive(int min, int max) {
        final int maxValueExcluded = max + 1;
        return rgen.nextInt(maxValueExcluded - min) + min;
    }

    /**
     * Returns a string representation of the given map.
     * @param map Map.
     * @return String representation of the given map.
     */
    private String mapToString(final int[][] map) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (int row = 0; row < map.length; ++row) {
            sb.append("[");
            for (int col = 0; col < map[0].length; ++col) {
                if (col != 0) {
                    sb.append(", ");
                }
                sb.append(map[row][col]);
            }
            sb.append("]");

            if (row != map.length - 1) {
                sb.append(",\n");
            }
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * Simulates the flipping of a loaded coin and returns true if it gets heads.
     * @param loadFactor An integer between 1 and 100 which specifies how loaded
     *                   the coin is. 1 makes it very hard to land heads, while
     *                   100 makes it certain.
     * @return {@code true} if we got heads, {@code false} otherwise
     */
    private boolean flipLoadedCoin(int loadFactor) {
        return getRandomIntInclusive(1, 100)  <= loadFactor;
    }
}
