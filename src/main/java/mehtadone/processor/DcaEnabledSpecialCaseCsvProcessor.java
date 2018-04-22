package mehtadone.processor;

import mehtadone.PropertyKeyTransformer;
import mehtadone.output.AppSettings;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class DcaEnabledSpecialCaseCsvProcessor implements SpecialCaseCsvProcessor {

    private final PropertyKeyTransformer propertyKeyTransformer;

    public DcaEnabledSpecialCaseCsvProcessor(final PropertyKeyTransformer propertyKeyTransformer) {
        this.propertyKeyTransformer = propertyKeyTransformer;
    }

    @Override
    public void process(final List<String> keys, final Properties pairsProperties, final AppSettings appSettings) {
        final Set<String> dcaExcludedCoins = new HashSet<>();

        keys.stream().filter(k -> k.toLowerCase().contains("_dca_enabled")).collect(Collectors.toList())
                .forEach(k -> {
                    if (k.toLowerCase().contains("default_")) {
                        // Process DEFAULT_dca_enabled
                        appSettings.getSectionToFill("pairs", k).put(propertyKeyTransformer.transform(k), pairsProperties.getProperty(k));
                    } else {
                        // Add XXX from XXX_dca_enabled to the list of excluded coins only if XXX_dca_enabled = false
                        if (pairsProperties.getProperty(k).equalsIgnoreCase("false")) {
                            dcaExcludedCoins.add(k.toLowerCase().replaceAll("_dca_enabled", "").toUpperCase());
                        }
                    }
                });

        // Add excludedCoins
        if (!dcaExcludedCoins.isEmpty()) {
            appSettings.getSectionToFill("special-cases", "").put("DcaExcludedCoins", String.join(",", dcaExcludedCoins));
        }
    }

}
