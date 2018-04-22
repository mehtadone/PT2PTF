package mehtadone;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertyKeyTransformerTest {

    @InjectMocks
    private PropertyKeyTransformer propertyKeyTransformer;

    @Test
    public void transform_keyWithPrefix() {
        Assertions.assertThat(propertyKeyTransformer.transform("INDICATORS", "EMA_cross_candles"))
                .isEqualTo("IndicatorsEmaCrossCandles");
    }

    @Test
    public void transform_defaultKeyWithPrefix() {
        Assertions.assertThat(propertyKeyTransformer.transform("INDICATORS", "default_EMA_cross_candles"))
                .isEqualTo("IndicatorsEmaCrossCandles");
    }

}
