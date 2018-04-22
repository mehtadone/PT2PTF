package mehtadone;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import mehtadone.output.AppSettings;

import java.util.Properties;
import java.util.Set;

public class Application {

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String PAIRS_PROPERTIES_FILENAME = "pairs.properties";
    private static final String DCA_PROPERTIES_FILENAME = "dca.properties";
    private static final String INDICATORS_PROPERTIES_FILENAME = "indicators.properties";

    private static final String RESULTING_JSON_FILENAME = "appsettings.json";

    /**
     * Keys that require special treatment, like XXX_trading_enabled, XXX_sell_only_mode_enabled, XXX_dca_enabled, etc
     * From the pairs.properties and needs to be put into PtFeeder section
     */
    private static final Set<String> SPECIAL_CASES = ImmutableSet.of("market", "sell_only_mode_enabled",
            "dca_enabled", "trading_enabled", "enabled_pairs", "hidden_pairs");

    public static void main(final String[] args) throws Exception {
        new Application(new PropertyFileReader(), new JsonFileWriter(), new PropertyKeyTransformer());
    }

    public Application(
            final PropertyFileReader propertyFileReader,
            final JsonFileWriter jsonFileWriter,
            final PropertyKeyTransformer propertyKeyTransformer
    ) {
        final Properties indicatorsProperties = propertyFileReader.read(USER_DIR, INDICATORS_PROPERTIES_FILENAME);
        final Properties dcaProperties = propertyFileReader.read(USER_DIR, DCA_PROPERTIES_FILENAME);
        final Properties pairsProperties = propertyFileReader.read(USER_DIR, PAIRS_PROPERTIES_FILENAME);

        // Final object to be transformed to JSON
        final AppSettings appSettings = new AppSettings();

        /*
         * Properties from indicators.properties go into Common section
         * See https://wiki.ptfeeder.co/configuration.html#section-common and https://wiki.ptfeeder.co/configuration.html#section-defaultsfor details
         */
        indicatorsProperties.stringPropertyNames()
                .forEach(key -> appSettings.getSectionToFill("indicators", key)
                        .put(propertyKeyTransformer.transform("indicators", key), indicatorsProperties.getProperty(key)));

        /*
         * Properties from dca.properties go into Common or Default section, depends on the DEFAULT_ proprty key prefix
         * See https://wiki.ptfeeder.co/configuration.html#section-common and https://wiki.ptfeeder.co/configuration.html#section-defaultsfor details
         */
        dcaProperties.stringPropertyNames()
                .forEach(key -> appSettings.getSectionToFill("dca", key)
                        .put(propertyKeyTransformer.transform(key), dcaProperties.getProperty(key)));

        /*
         * Properties from pairs.properties go into Common or Default section, depends on the DEFAULT_ proprty key prefix
         * See https://wiki.ptfeeder.co/configuration.html#section-common and https://wiki.ptfeeder.co/configuration.html#section-defaultsfor details
         * Mind also, that special cases, which should go into PtFeeder section
         */
        pairsProperties.stringPropertyNames().stream()
                // Filter out special cases, so we can process those separately later
                .filter(k -> SPECIAL_CASES.stream().noneMatch(sc -> k.toLowerCase().contains(sc.toLowerCase())))
                .forEach(key -> appSettings.getSectionToFill("pairs", key)
                        .put(propertyKeyTransformer.transform(key), pairsProperties.getProperty(key)));

        /*
         * Process special cases, which should go into PtFeeder section
         */
        pairsProperties.stringPropertyNames().stream()
                // Filter out special cases, so we can process those separately later
                .filter(k -> SPECIAL_CASES.stream().anyMatch(sc -> k.toLowerCase().contains(sc.toLowerCase())))
        ;
//                .map();

        // Add hardcoded MarketConditionsGrouping section
        appSettings.getMarketConditionsGrouping().put("Configs", ImmutableList.of(
                new AppSettings.MarketConditionsGroupingConfig("Bear", "-2"),
                new AppSettings.MarketConditionsGroupingConfig("Boring", "2"),
                new AppSettings.MarketConditionsGroupingConfig("Bear", "3")));

        jsonFileWriter.write(USER_DIR, RESULTING_JSON_FILENAME, appSettings);
    }

}
