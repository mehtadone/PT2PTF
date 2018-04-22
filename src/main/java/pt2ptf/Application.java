package pt2ptf;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import pt2ptf.output.ApplicationSettings;
import pt2ptf.processor.SkipSpecialCaseProcessor;
import pt2ptf.processor.SpecialCaseCsvProcessor;
import pt2ptf.processor.SpecialCaseProcessor;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

    private final Properties indicatorsProperties;
    private final Properties dcaProperties;
    private final Properties pairsProperties;

    private final PropertyKeyTransformer propertyKeyTransformer;

    private final Set<String> specialCases;
    private final Map<String, SpecialCaseProcessor> specialCaseProcessors;
    private final Map<String, SpecialCaseCsvProcessor> specialCaseCsvProcessors;

    public Application(
            final Properties indicatorsProperties,
            final Properties dcaProperties,
            final Properties pairsProperties,
            final PropertyKeyTransformer propertyKeyTransformer,
            final Set<String> specialCases,
            final Map<String, SpecialCaseProcessor> specialCaseProcessors,
            final Map<String, SpecialCaseCsvProcessor> specialCaseCsvProcessors
    ) {
        this.indicatorsProperties = indicatorsProperties;
        this.dcaProperties = dcaProperties;
        this.pairsProperties = pairsProperties;

        this.propertyKeyTransformer = propertyKeyTransformer;

        this.specialCases = specialCases;
        this.specialCaseProcessors = specialCaseProcessors;
        this.specialCaseCsvProcessors = specialCaseCsvProcessors;
    }

    public ApplicationSettings run() {
        // Final object to be transformed to JSON
        final ApplicationSettings applicationSettings = new ApplicationSettings();

        /*
         * Properties from indicators.properties go into Common section
         * See https://wiki.ptfeeder.co/configuration.html#section-common and https://wiki.ptfeeder.co/configuration.html#section-defaultsfor details
         */
        indicatorsProperties.stringPropertyNames()
                .forEach(key -> applicationSettings.getSectionToFill("indicators", key)
                        .put(propertyKeyTransformer.transform("indicators", key), indicatorsProperties.getProperty(key)));

        /*
         * Properties from dca.properties go into Common or Default section, depends on the DEFAULT_ proprty key prefix
         * See https://wiki.ptfeeder.co/configuration.html#section-common and https://wiki.ptfeeder.co/configuration.html#section-defaultsfor details
         */
        dcaProperties.stringPropertyNames()
                .forEach(key -> applicationSettings.getSectionToFill("dca", key)
                        .put(propertyKeyTransformer.transform(key), dcaProperties.getProperty(key)));

        /*
         * Properties from pairs.properties go into Common or Default section, depends on the DEFAULT_ proprty key prefix
         * See https://wiki.ptfeeder.co/configuration.html#section-common and https://wiki.ptfeeder.co/configuration.html#section-defaultsfor details
         * Mind also, that special cases, mainly should go into PtFeeder section
         */
        pairsProperties.stringPropertyNames().stream()
                // Filter out special cases, so we can process those separately later
                .filter(k -> specialCases.stream().noneMatch(sc -> k.toLowerCase().contains(sc.toLowerCase())))
                .forEach(key -> applicationSettings.getSectionToFill("pairs", key)
                        .put(propertyKeyTransformer.transform(key), pairsProperties.getProperty(key)));

        // Get all special cases, which should mainly go into PtFeeder section
        final List<String> specialCasesKeys = pairsProperties.stringPropertyNames().stream()
                .filter(k -> specialCases.stream().anyMatch(sc -> k.toLowerCase().contains(sc.toLowerCase()))).collect(Collectors.toList());

        // Process all special cases keys which map directly to the JSON property name, like market, enabled_pairs, hidden_pairs, etc
        specialCasesKeys.forEach(key -> specialCaseProcessors.getOrDefault(key.toLowerCase(), new SkipSpecialCaseProcessor())
                .process(key, pairsProperties, applicationSettings));
        // Process special cases keys with XXX_ possible prefixes, and which should be converted to CSV values
        specialCaseCsvProcessors.values().forEach(c -> c.process(specialCasesKeys, pairsProperties, applicationSettings));
        // Add hardcoded PtFeeder section
        applicationSettings.getPtFeeder().putAll(ImmutableMap.of(
                "TopCurrenciesToCheck", "35",
                "MinutesToMeasureTrend", "120",
                "MinutesForLongerTermTrend", "720"
        ));

        // Add hardcoded MarketConditionsGrouping section
        applicationSettings.getMarketConditionsGrouping().put("Configs", ImmutableList.of(
                new ApplicationSettings.MarketConditionsGroupingConfig("Bear", "-2"),
                new ApplicationSettings.MarketConditionsGroupingConfig("Boring", "2"),
                new ApplicationSettings.MarketConditionsGroupingConfig("Bull", "100000")));

        return applicationSettings;
    }

}
