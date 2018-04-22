package mehtadone;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonFileWriter {

    public void write(final String filepath, final String filename, final Object object) {
        try {
            final ObjectWriter objectWriter = new ObjectMapper().writer(new DefaultPrettyPrinter());
            objectWriter.writeValue(System.out, object);
        } catch (Exception e) {
            System.out.println("Cannot create resulting json file '" + filename + "' ...");
            System.out.println("Check write grants for the directory '" + filepath + "' ...");

            System.exit(1);
        }
    }

}
