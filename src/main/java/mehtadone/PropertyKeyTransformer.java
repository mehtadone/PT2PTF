package mehtadone;

import org.apache.commons.text.WordUtils;

import java.util.Optional;

/**
 * Transforms property keys according to the rules described here https://wiki.ptfeeder.co/configuration.html#pt-feeder-naming-convention
 */
public class PropertyKeyTransformer {

    private static final String PROFIT_TRAILER_SEPARATOR = "_";

    /**
     * Transform key according to the rules described here https://wiki.ptfeeder.co/configuration.html#pt-feeder-naming-convention
     *
     * @param key key to be transformed
     * @return transformed key
     */
    public String transform(final String key) {
        return Optional.of(key)
                .map(String::toLowerCase)
                // Remove "default_" part if it is a default key
                .map(k -> k.replaceAll("default_", ""))
                // Replace underscores with spaces
                .map(k -> k.replaceAll("_", " "))
                // Capitalize every word
                .map(WordUtils::capitalizeFully)
                // Remove spaces and get a final key
                .map(k -> k.replaceAll(" ", "")).get();
    }

    /**
     * In some cases, like for indicators, it is required to add prefix
     *
     * @param prefix prefix to be added
     * @param key    key to be transformed
     * @return transformed key
     */
    public String transform(final String prefix, final String key) {
        return transform(prefix + PROFIT_TRAILER_SEPARATOR + key);
    }

}
