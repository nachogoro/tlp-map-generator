package es.uned.tlp.mapgenerator;

/**
 * Parameters with which a map is to be generated.
 */
public class MapParameters {
    public final int width;
    public final int height;
    public final int tendencyToGrow;

    public static final int defaultTendency = 40;

    private MapParameters(int width, int height, int tendencyToGrow) {
        this.width = width;
        this.height = height;
        this.tendencyToGrow = tendencyToGrow;
    }

    /**
     * Factory methods to generate parameters from command line arguments.
     * @param args Command-line arguments to the program.
     * @return The parsed MapParameters or null if they couldn't be parsed.
     */
    public static MapParameters fromCommandLine(String[] args) {
        if (args.length < 2 || args.length > 3) {
            return null;
        }

        final int width;
        final int height;
        final int tendencyToGrow;

        try {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);

            if (args.length == 3) {
                tendencyToGrow = Integer.parseInt(args[2]);
            } else {
                tendencyToGrow = defaultTendency;
            }
        } catch (final NumberFormatException e) {
            return null;
        }

        if (width == 0 || height == 0 || tendencyToGrow < 1 || tendencyToGrow > 100) {
            return null;
        }

        return new MapParameters(width, height, tendencyToGrow);
    }
}
