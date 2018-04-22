package pt2ptf.processor;

import pt2ptf.output.ApplicationSettings;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class HiddenPairsSpecialCaseProcessorTest {

    @InjectMocks
    private HiddenPairsSpecialCaseProcessor hiddenPairsSpecialCaseProcessor;

    @Test
    public void process() {
        final Properties properties = new Properties();
        properties.put("hidden_pairs", "ALL");

        final ApplicationSettings applicationSettings = new ApplicationSettings();

        hiddenPairsSpecialCaseProcessor.process("hidden_pairs", properties, applicationSettings);

        Assertions.assertThat(applicationSettings.getPtFeeder()).containsEntry("HiddenCoins", "ALL");
    }

}
