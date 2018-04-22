package mehtadone;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PropertyKeyTransformerTest {

    private PropertyKeyTransformer propertyKeyTransformer;

    @Before
    public void before() {
        propertyKeyTransformer = new PropertyKeyTransformer();
    }

    @Test
    public void transform_withPrefix() {
        Assertions.assertThat(propertyKeyTransformer.transform("INDICATORS", "EMA_cross_candles"))
                .isEqualTo("IndicatorsEmaCrossCandles");
    }

}