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
public class TradingEnabledSpecialCaseCsvProcessorTest {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private PropertyKeyTransformer propertyKeyTransformer;

    @InjectMocks
    private TradingEnabledSpecialCaseCsvProcessor tradingEnabledSpecialCaseCsvProcessor;

    @Test
    public void process() {
        final Properties properties = new Properties();
        properties.put("DEFAULT_trading_enabled", "true");
        properties.put("ETH_trading_enabled", "false");
        properties.put("ETC_trading_enabled", "false");
        properties.put("XRP_trading_enabled", "true");

        final ApplicationSettings applicationSettings = new ApplicationSettings();

        tradingEnabledSpecialCaseCsvProcessor.process(new ArrayList<>(properties.stringPropertyNames()), properties, applicationSettings);

        Assertions.assertThat(applicationSettings.getDefaults()).containsEntry("TradingEnabled", "true");
        Assertions.assertThat(applicationSettings.getPtFeeder()).containsEntry("ExcludedCoins", "ETC,ETH");
    }

}
