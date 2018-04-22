package mehtadone.processor;

import mehtadone.output.AppSettings;

import java.util.Properties;

public interface SpecialCaseProcessor {

    void process(final String key, final Properties pairsProperties, final AppSettings appSettings);

}
