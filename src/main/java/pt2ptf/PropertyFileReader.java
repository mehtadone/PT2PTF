package pt2ptf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

    private static final String SEPARATOR = File.separator;

    public Properties read(final String filepath, final String filename) {
        final Properties properties = new Properties();

        try (final InputStream inputStream = new FileInputStream(filepath + SEPARATOR + filename)) {
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println("Properties file '" + filename + "' not found ...");
            System.out.println("Please make sure that property files are named");
            System.out.println("'pairs.properties', 'dca.properties' and 'indicators.properties'");
            System.out.println("and are located in the same directory as executable .jar file ...");

            System.exit(1);
        }

        return properties;
    }

}
