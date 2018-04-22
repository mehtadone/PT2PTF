package mehtadone.processor;

import mehtadone.output.AppSettings;

import java.util.List;
import java.util.Properties;

public interface SpecialCaseCsvProcessor {

    void process(final List<String> keys, final Properties pairsProperties, final AppSettings appSettings);

}
