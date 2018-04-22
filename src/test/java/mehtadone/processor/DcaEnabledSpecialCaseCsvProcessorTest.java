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
public class DcaEnabledSpecialCaseCsvProcessorTest {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private PropertyKeyTransformer propertyKeyTransformer;

    @InjectMocks
    private DcaEnabledSpecialCaseCsvProcessor dcaEnabledSpecialCaseCsvProcessor;

    @Test
    public void process() {
        final Properties properties = new Properties();
        properties.put("DEFAULT_dca_enabled", "true");
        properties.put("ETH_dca_enabled", "false");
        properties.put("XRP_dca_enabled", "true");

        final ApplicationSettings applicationSettings = new ApplicationSettings();

        dcaEnabledSpecialCaseCsvProcessor.process(new ArrayList<>(properties.stringPropertyNames()), properties, applicationSettings);

        Assertions.assertThat(applicationSettings.getDefaults()).containsEntry("DcaEnabled", "true");
        Assertions.assertThat(applicationSettings.getPtFeeder()).containsEntry("DcaExcludedCoins", "ETH");
    }

}
