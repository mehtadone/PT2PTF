package mehtadone.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@JsonPropertyOrder({"PtFeeder", "Common", "Defaults", "MarketConditionsGrouping"})
public class ApplicationSettings {

    /**
     * Comparator in order to sort correctly strings with integers. For example "s1", "s2", ..., "s9", "s10", "s11", ..., "s19", "s20", "s21", ...
     * Original string comparator sorts such sequence next way: "s1", "s10", "s11", ..., "s19", "s2", "s20", "s21" ..., "s9",
     */
    private static final Comparator<String> STRING_WITH_INDICES_COMPARATOR = new Comparator<String>() {

        @Override
        public int compare(final String s1, final String s2) {
            final String s1StringPart = s1.replaceAll("\\d", "");
            final String s2StringPart = s2.replaceAll("\\d", "");

            if (s1StringPart.equalsIgnoreCase(s2StringPart)) {
                return getInt(s1) - getInt(s2);
            }

            return s1.compareTo(s2);
        }

        private int getInt(final String s) {
            final String num = s.replaceAll("\\D", "");
            // Return 0 if there is not in in the string
            return num.isEmpty() ? 0 : Integer.parseInt(num);
        }
    };

    private final Map<String, String> PtFeeder = new TreeMap<>(STRING_WITH_INDICES_COMPARATOR);
    private final Map<String, String> Common = new TreeMap<>(STRING_WITH_INDICES_COMPARATOR);
    private final Map<String, String> Defaults = new TreeMap<>(STRING_WITH_INDICES_COMPARATOR);
    private final Map<String, List<MarketConditionsGroupingConfig>> MarketConditionsGrouping = new TreeMap<>(STRING_WITH_INDICES_COMPARATOR);

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
        // All 'indicators' go into Common section
        if ("indicators".equalsIgnoreCase(propertiesFile)) {
            return getCommon();
        }

        // For 'dca' and 'pairs' we will look into the prefix
        if ("dca".equalsIgnoreCase(propertiesFile) || "pairs".equalsIgnoreCase(propertiesFile)) {
            if (key.toLowerCase().startsWith("default_")) {
                return getDefaults();
            } else {
                return getCommon();
            }
        }

        // By default, return PtFeeder section to fill
        return getPtFeeder();
    }

    @EqualsAndHashCode
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
