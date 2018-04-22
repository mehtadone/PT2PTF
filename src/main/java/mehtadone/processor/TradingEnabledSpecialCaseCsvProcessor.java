package mehtadone.processor;

import mehtadone.PropertyKeyTransformer;
import mehtadone.output.AppSettings;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class TradingEnabledSpecialCaseCsvProcessor implements SpecialCaseCsvProcessor {

    private final PropertyKeyTransformer propertyKeyTransformer;

    public TradingEnabledSpecialCaseCsvProcessor(final PropertyKeyTransformer propertyKeyTransformer) {
        this.propertyKeyTransformer = propertyKeyTransformer;
    }

    @Override
    public void process(final List<String> keys, final Properties pairsProperties, final AppSettings appSettings) {
        final Set<String> excludedCoins = new HashSet<>();

        keys.stream().filter(k -> k.toLowerCase().contains("_trading_enabled")).collect(Collectors.toList())
                .forEach(k -> {
                    if (k.toLowerCase().contains("default_")) {
                        // Process DEFAULT_trading_enabled
                        appSettings.getSectionToFill("pairs", k).put(propertyKeyTransformer.transform(k), pairsProperties.getProperty(k));
                    } else {
                        // Add XXX from XXX_trading_enabled to the list of excluded coins only if XXX_trading_enabled = false
                        if (pairsProperties.getProperty(k).equalsIgnoreCase("false")) {
                            excludedCoins.add(k.toLowerCase().replaceAll("_trading_enabled", "").toUpperCase());
                        }
                    }
                });

        // Add excludedCoins
        if (!excludedCoins.isEmpty()) {
            appSettings.getSectionToFill("special-cases", "").put("ExcludedCoins", String.join(",", excludedCoins));
        }
    }

}
