package mehtadone.processor;

import mehtadone.PropertyKeyTransformer;
import mehtadone.output.ApplicationSettings;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class SellOnlyModeEnabledSpecialCaseCsvProcessor implements SpecialCaseCsvProcessor {

    private final PropertyKeyTransformer propertyKeyTransformer;

    public SellOnlyModeEnabledSpecialCaseCsvProcessor(final PropertyKeyTransformer propertyKeyTransformer) {
        this.propertyKeyTransformer = propertyKeyTransformer;
    }

    @Override
    public void process(final List<String> keys, final Properties pairsProperties, final ApplicationSettings applicationSettings) {
        final Set<String> somOnlyCoins = new HashSet<>();

        keys.stream().filter(k -> k.toLowerCase().contains("_sell_only_mode_enabled")).collect(Collectors.toList())
                .forEach(k -> {
                    if (k.toLowerCase().contains("default_")) {
                        // Process DEFAULT_sell_only_mode_enabled
                        applicationSettings.getSectionToFill("pairs", k).put(propertyKeyTransformer.transform(k), pairsProperties.getProperty(k));
                    } else {
                        // Add XXX from XXX_sell_only_mode_enabled to the list of excluded coins only if XXX_sell_only_mode_enabled = true
                        if (pairsProperties.getProperty(k).equalsIgnoreCase("true")) {
                            somOnlyCoins.add(k.toLowerCase().replaceAll("_sell_only_mode_enabled", "").toUpperCase());
                        }
                    }
                });

        // Add excludedCoins
        if (!somOnlyCoins.isEmpty()) {
            applicationSettings.getSectionToFill("special-cases", "").put("SomOnlyCoins", String.join(",", somOnlyCoins));
        }
    }

}
