package es.uned.tlp.mapgenerator;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	    MapParameters params = MapParameters.fromCommandLine(args);

	    if (params == null) {
	        // Invalid arguments
            showUsage();
            return;
        }

	    final MapGenerator mgen = new MapGenerator();
	    System.out.println(mgen.generateMap(params));
    }

    private static void showUsage() {
        System.out.println("Usage: java -jar mapGenerator.jar <width> <height> [<grow_factor>]");
        System.out.println("\t\t<width>: (int > 0) Width of the map");
        System.out.println("\t\t<height>: (int > 0) Height of the map");
        System.out.println("\t\t<grow_factor>: (int [1, 100]) The higher the number, the larger the regions will tend to be. Defaults to " + MapParameters.defaultTendency);
    }
}
