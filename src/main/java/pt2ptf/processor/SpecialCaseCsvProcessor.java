package pt2ptf.processor;

import pt2ptf.output.ApplicationSettings;

import java.util.List;
import java.util.Properties;

public interface SpecialCaseCsvProcessor {

    void process(final List<String> keys, final Properties pairsProperties, final ApplicationSettings applicationSettings);

}
