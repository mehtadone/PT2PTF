package mehtadone.processor;

import mehtadone.output.AppSettings;

import java.util.Properties;

public class EnabledPairsSpecialCaseProcessor implements SpecialCaseProcessor {

    @Override
    public void process(final String key, final Properties pairsProperties, final AppSettings appSettings) {
        appSettings.getSectionToFill("special-cases", key).put("EnabledCoinsOnly", pairsProperties.getProperty(key));
    }

}
