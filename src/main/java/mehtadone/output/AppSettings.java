package mehtadone.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppSettings {

    private final Map<String, String> PtFeeder = new HashMap<>();
    private final Map<String, String> Common = new HashMap<>();
    private final Map<String, String> Defaults = new HashMap<>();
    private final Map<String, List<MarketConditionsGroupingConfig>> MarketConditionsGrouping = new HashMap<>();

    @JsonProperty("PtFeeder")
    public Map<String, String> getPtFeeder() {
        return PtFeeder;
    }

    @JsonProperty("Common")
    public Map<String, String> getCommon() {
        return Common;
    }

    @JsonProperty("Defaults")
    public Map<String, String> getDefaults() {
        return Defaults;
    }

    @JsonProperty("MarketConditionsGrouping")
    public Map<String, List<MarketConditionsGroupingConfig>> getMarketConditionsGrouping() {
        return MarketConditionsGrouping;
    }

    /**
     * Depends on the properties file and key decide which section to fill
     *
     * @param propertiesFile properties file
     * @param key            property key
     * @return section to fill
     */
    public Map<String, String> getSectionToFill(final String propertiesFile, final String key) {
        // All 'indicators' go into common section
        if ("indicators".equalsIgnoreCase(propertiesFile)) {
            return getCommon();
        }

        // For 'dca' and 'pairs' we will look into the prefix
        if (key.toLowerCase().startsWith("default_")) {
            return getDefaults();
        } else {
            return getCommon();
        }
    }

    public static class MarketConditionsGroupingConfig {

        private final String Name;
        private final String MaxTopCoinAverageChange;

        public MarketConditionsGroupingConfig(final String Name, final String MaxTopCoinAverageChange) {
            this.Name = Name;
            this.MaxTopCoinAverageChange = MaxTopCoinAverageChange;
        }

        @JsonProperty("Name")
        public String getName() {
            return Name;
        }

        @JsonProperty("MaxTopCoinAverageChange")
        public String getMaxTopCoinAverageChange() {
            return MaxTopCoinAverageChange;
        }
    }

}
