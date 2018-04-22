package mehtadone;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mehtadone.output.ApplicationSettings;
import mehtadone.processor.*;

import java.util.Map;
import java.util.Set;

public class Main {

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String PAIRS_PROPERTIES_FILENAME = "pairs.properties";
    private static final String DCA_PROPERTIES_FILENAME = "dca.properties";
    private static final String INDICATORS_PROPERTIES_FILENAME = "indicators.properties";

    private static final String RESULTING_JSON_FILENAME = "appsettings.json";

    private static final PropertyFileReader PROPERTY_FILE_READER = new PropertyFileReader();
    private static final JsonFileWriter JSON_FILE_WRITER = new JsonFileWriter();

    private static final PropertyKeyTransformer PROPERTY_KEY_TRANSFORMER = new PropertyKeyTransformer();

    /**
     * Keys that require special treatment, like XXX_trading_enabled, XXX_sell_only_mode_enabled, XXX_dca_enabled, etc
     * From the pairs.properties and needs to be put into PtFeeder section
     */
    private static final Set<String> SPECIAL_CASES = ImmutableSet.of("market", "hidden_pairs",
            "trading_enabled", "dca_enabled", "sell_only_mode_enabled");

    private static final Map<String, SpecialCaseProcessor> SPECIAL_CASE_PROCESSORS = ImmutableMap.of(
            "market", new MarketSpecialCaseProcessor(),
            "hidden_pairs", new HiddenPairsSpecialCaseProcessor()
    );
    private static final Map<String, SpecialCaseCsvProcessor> SPECIAL_CASE_CSV_PROCESSORS = ImmutableMap.of(
            "_trading_enabled", new TradingEnabledSpecialCaseCsvProcessor(PROPERTY_KEY_TRANSFORMER),
            "_sell_only_mode_enabled", new SellOnlyModeEnabledSpecialCaseCsvProcessor(PROPERTY_KEY_TRANSFORMER),
            "_dca_enabled", new DcaEnabledSpecialCaseCsvProcessor(PROPERTY_KEY_TRANSFORMER)
    );

    public static void main(final String[] args) {
        final ApplicationSettings applicationSettings = new Application(
                PROPERTY_FILE_READER.read(USER_DIR, INDICATORS_PROPERTIES_FILENAME),
                PROPERTY_FILE_READER.read(USER_DIR, DCA_PROPERTIES_FILENAME),
                PROPERTY_FILE_READER.read(USER_DIR, PAIRS_PROPERTIES_FILENAME),
                PROPERTY_KEY_TRANSFORMER,
                SPECIAL_CASES,
                SPECIAL_CASE_PROCESSORS,
                SPECIAL_CASE_CSV_PROCESSORS
        ).run();

        JSON_FILE_WRITER.write(USER_DIR, RESULTING_JSON_FILENAME, applicationSettings);
    }

}
