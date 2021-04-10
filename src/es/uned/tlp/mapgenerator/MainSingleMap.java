package es.uned.tlp.mapgenerator;

/**
 * Main class for generating a single map
 */
public class MainSingleMap {

    public static void main(final String[] args) {
	    SingleMapParameters params = SingleMapParameters.fromCommandLine(args);

	    if (params == null) {
	        // Invalid arguments
            showUsage();
            return;
        }

	    final MapGenerator mgen = new MapGenerator();
	    System.out.println(MapUtils.mapToString(mgen.generateMap(params), true));
    }

    private static void showUsage() {
        System.out.println("Usage: java -jar singleMapGenerator.jar <width> <height> [<grow_factor>]");
        System.out.println("\t\t<width>: (int > 0) Width of the map");
        System.out.println("\t\t<height>: (int > 0) Height of the map");
        System.out.println("\t\t<grow_factor>: (int [1, 100]) The higher the number, the larger the regions will tend to be. Defaults to " + SingleMapParameters.defaultTendency);
    }
}
