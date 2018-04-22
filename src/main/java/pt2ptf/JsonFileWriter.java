package pt2ptf;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;

public class JsonFileWriter {

    private static final String SEPARATOR = File.separator;

    public void write(final String filepath, final String filename, final Object object) {
        try {
            final ObjectWriter objectWriter = new ObjectMapper().writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(new File(filepath + SEPARATOR + filename), object);
        } catch (Exception e) {
            System.out.println("Cannot create resulting json file '" + filename + "' ...");
            System.out.println("Check write grants for the directory '" + filepath + "' ...");

            System.exit(1);
        }
    }

}
