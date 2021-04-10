package es.uned.tlp.mapgenerator;

/**
 * Parameters with which a battery of maps is generated.
 */
public class BatteryParameters {
    public final int numberOfMaps;

    private BatteryParameters(final int numberOfMaps) {
        this.numberOfMaps = numberOfMaps;
    }

    /**
     * Factory methods to generate parameters from command line arguments.
     * @param args Command-line arguments to the program.
     * @return The parsed BatteryMapParameters or null if they couldn't be parsed.
     */
    public static BatteryParameters fromCommandLine(String[] args) {
        if (args.length != 1) {
            return null;
        }

        try {
            final int numberOfMaps = Integer.parseInt(args[0]);
            if (numberOfMaps <= 0) {
                return null;
            }

            return new BatteryParameters(numberOfMaps);

        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
