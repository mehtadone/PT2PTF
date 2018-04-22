package pt2ptf.processor;

import pt2ptf.output.ApplicationSettings;

import java.util.Properties;

public interface SpecialCaseProcessor {

    void process(final String key, final Properties pairsProperties, final ApplicationSettings applicationSettings);

}
