package pt2ptf.processor;

import pt2ptf.output.ApplicationSettings;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class MarketSpecialCaseProcessorTest {

    @InjectMocks
    private MarketSpecialCaseProcessor marketSpecialCaseProcessor;

    @Test
    public void process() {
        final Properties properties = new Properties();
        properties.put("market", "BTC");

        final ApplicationSettings applicationSettings = new ApplicationSettings();

        marketSpecialCaseProcessor.process("market", properties, applicationSettings);

        Assertions.assertThat(applicationSettings.getPtFeeder()).containsEntry("BaseCurrency", "BTC");
    }

}
