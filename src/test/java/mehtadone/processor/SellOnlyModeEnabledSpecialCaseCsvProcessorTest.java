package mehtadone.processor;

import mehtadone.PropertyKeyTransformer;
import mehtadone.output.ApplicationSettings;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class SellOnlyModeEnabledSpecialCaseCsvProcessorTest {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private PropertyKeyTransformer propertyKeyTransformer;

    @InjectMocks
    private SellOnlyModeEnabledSpecialCaseCsvProcessor sellOnlyModeEnabledSpecialCaseCsvProcessor;

    @Test
    public void process() {
        final Properties properties = new Properties();
        properties.put("DEFAULT_sell_only_mode_enabled", "false");
        properties.put("ETH_sell_only_mode_enabled", "true");
        properties.put("ETC_sell_only_mode_enabled", "true");
        properties.put("XRP_sell_only_mode_enabled", "true");
        properties.put("BTC_sell_only_mode_enabled", "false");

        final ApplicationSettings applicationSettings = new ApplicationSettings();

        sellOnlyModeEnabledSpecialCaseCsvProcessor.process(new ArrayList<>(properties.stringPropertyNames()), properties, applicationSettings);

        Assertions.assertThat(applicationSettings.getDefaults()).containsEntry("SellOnlyModeEnabled", "false");
        Assertions.assertThat(applicationSettings.getPtFeeder()).containsEntry("SomOnlyCoins", "ETC,XRP,ETH");
    }

}
