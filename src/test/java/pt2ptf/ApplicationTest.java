package pt2ptf;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import pt2ptf.output.ApplicationSettings;
import pt2ptf.processor.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    @Test
    public void test() {
        final Properties indicatorsProperties = new Properties();
        indicatorsProperties.put("BB_std", "2");
        indicatorsProperties.put("BB_candle_period", "300");
        indicatorsProperties.put("BB_length", "20");
        indicatorsProperties.put("SMA_cross_candles", "2");
        indicatorsProperties.put("SMA_candle_period", "300");
        indicatorsProperties.put("SMA_fast_length", "12");
        indicatorsProperties.put("SMA_slow_length", "24");
        indicatorsProperties.put("EMA_cross_candles", "3");
        indicatorsProperties.put("EMA_candle_period", "300");
        indicatorsProperties.put("EMA_fast_length", "3");
        indicatorsProperties.put("EMA_slow_length", "24");
        indicatorsProperties.put("RSI_candle_period", "300");
        indicatorsProperties.put("RSI_length", "14");
        indicatorsProperties.put("STOCH_length", "14");
        indicatorsProperties.put("MACD_candle_period", "300");
        indicatorsProperties.put("MACD_fast_length", "12");
        indicatorsProperties.put("MACD_slow_length", "26");
        indicatorsProperties.put("MACD_signal", "9");

        final Properties dcaProperties = new Properties();
        dcaProperties.put("DCA_keep_balance", "0");
        dcaProperties.put("DCA_keep_balance_percentage", "0");
        dcaProperties.put("DEFAULT_DCA_max_cost", "10");
        dcaProperties.put("DEFAULT_DCA_A_buy_strategy", "LOWBB");
        dcaProperties.put("DEFAULT_DCA_A_buy_value", "5");
        dcaProperties.put("DEFAULT_DCA_A_buy_value_limit", "-2.5");
        dcaProperties.put("DEFAULT_DCA_B_buy_strategy", "RSI");
        dcaProperties.put("DEFAULT_DCA_B_buy_value", "33");
        dcaProperties.put("DEFAULT_DCA_B_buy_value_limit", "5");
        dcaProperties.put("DEFAULT_DCA_trailing_buy", "0");
        dcaProperties.put("DEFAULT_DCA_max_buy_times", "20");
        dcaProperties.put("DEFAULT_DCA_buy_trigger", "0");
        dcaProperties.put("DEFAULT_DCA_buy_percentage", "100");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_1", "100");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_2", "50");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_3", "50");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_4", "25");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_5", "30");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_6", "25");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_7", "20");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_8", "15.5");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_9", "12.11");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_10", "10");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_11", "8");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_12", "7");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_13", "6");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_14", "6");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_15", "5");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_16", "5");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_17", "5");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_18", "5");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_19", "5");
        dcaProperties.put("DEFAULT_DCA_buy_percentage_20", "5");
        dcaProperties.put("DEFAULT_DCA_A_sell_strategy", "GAIN");
        dcaProperties.put("DEFAULT_DCA_A_sell_value", "1");
        dcaProperties.put("DEFAULT_DCA_A_sell_value_1", "0.95");
        dcaProperties.put("DEFAULT_DCA_A_sell_value_2", "0.85");
        dcaProperties.put("DEFAULT_DCA_A_sell_value_3", "0.75");
        dcaProperties.put("DEFAULT_DCA_B_sell_strategy", "RSI");
        dcaProperties.put("DEFAULT_DCA_B_sell_value", "40");
        dcaProperties.put("DEFAULT_DCA_trailing_profit", "0.147");
        dcaProperties.put("DEFAULT_DCA_max_profit", "0");
        dcaProperties.put("DEFAULT_DCA_min_orderbook_volume_percentage", "100");
        dcaProperties.put("DEFAULT_DCA_ignore_sell_only_mode", "false");
        dcaProperties.put("DEFAULT_DCA_max_buy_spread", "1.5");
        dcaProperties.put("DEFAULT_DCA_rebuy_timeout", "10");
        dcaProperties.put("DEFAULT_DCA_stop_loss_trigger", "0");
        dcaProperties.put("DEFAULT_DCA_stop_loss_timeout", "0");
        dcaProperties.put("DEFAULT_DCA_pending_order_wait_time", "0");

        final Properties pairsProperties = new Properties();
        pairsProperties.put("market", "BTC");
        pairsProperties.put("DEFAULT_trading_enabled", "true");
        pairsProperties.put("BNB_TRADING_enabled", "false");
        pairsProperties.put("BTC_trading_enabled", "false");
        pairsProperties.put("DEFAULT_sell_only_mode_enabled", "false");
        pairsProperties.put("XRP_sell_only_mode_enabled", "true");
        pairsProperties.put("ETC_SELL_only_mode_enabled", "true");
        pairsProperties.put("ETH_sell_only_mode_enabled", "true");
        pairsProperties.put("DEFAULT_DCA_enabled", "true");
        pairsProperties.put("KKK_DCA_enabled", "false");
        pairsProperties.put("STE_dca_enabled", "false");
        pairsProperties.put("enabled_pairs", "ALL");
        pairsProperties.put("hidden_pairs", "ALL");
        pairsProperties.put("start_balance", "0");
        pairsProperties.put("keep_balance", "0");
        pairsProperties.put("DEFAULT_buy_max_change_percentage", "8");
        pairsProperties.put("DEFAULT_buy_min_change_percentage", "-10");
        pairsProperties.put("max_trading_pairs", "5");
        pairsProperties.put("pair_min_listed_days", "14");
        pairsProperties.put("DEFAULT_initial_cost", "0.0015");
        pairsProperties.put("DEFAULT_initial_cost_percentage", "0");
        pairsProperties.put("DEFAULT_min_buy_volume", "200");
        pairsProperties.put("DEFAULT_min_buy_price", "0");
        pairsProperties.put("DEFAULT_max_buy_spread", "1");
        pairsProperties.put("DEFAULT_min_orderbook_volume_percentage", "100");
        pairsProperties.put("DEFAULT_A_buy_strategy", "LOWBB");
        pairsProperties.put("DEFAULT_A_buy_value", "5");
        pairsProperties.put("DEFAULT_A_buy_value_limit", "-2.5");
        pairsProperties.put("DEFAULT_B_buy_strategy", "RSI");
        pairsProperties.put("DEFAULT_B_buy_value", "33");
        pairsProperties.put("DEFAULT_B_buy_value_limit", "0");
        pairsProperties.put("DEFAULT_trailing_buy", "0");
        pairsProperties.put("DEFAULT_A_sell_strategy", "GAIN");
        pairsProperties.put("DEFAULT_A_sell_value", "1");
        pairsProperties.put("DEFAULT_B_sell_strategy", "RSI");
        pairsProperties.put("DEFAULT_B_sell_value", "40");
        pairsProperties.put("DEFAULT_trailing_profit", "0.16");
        pairsProperties.put("BNB_trading_enabled", "false");
        pairsProperties.put("PPT_panic_sell_enabled", "true");
        pairsProperties.put("DEFAULT_panic_sell_enabled", "false");
        pairsProperties.put("DEFAULT_rebuy_timeout", "2");
        pairsProperties.put("price_trigger_market", "BTC");
        pairsProperties.put("price_rise_trigger", "0");
        pairsProperties.put("price_rise_trigger_recover", "0");
        pairsProperties.put("price_drop_trigger", "0");
        pairsProperties.put("price_drop_recover_trigger", "0");
        pairsProperties.put("TRX_trading_enabled", "false");

        final PropertyKeyTransformer propertyKeyTransformer = new PropertyKeyTransformer();

        final Set<String> specialCases = ImmutableSet.of("market", "hidden_pairs",
                "trading_enabled", "dca_enabled", "sell_only_mode_enabled");

        final Map<String, SpecialCaseProcessor> specialCaseProcessors = ImmutableMap.of(
                "market", new MarketSpecialCaseProcessor(),
                "hidden_pairs", new HiddenPairsSpecialCaseProcessor());

        final Map<String, SpecialCaseCsvProcessor> specialCaseCsvProcessors = ImmutableMap.of(
                "_trading_enabled", new TradingEnabledSpecialCaseCsvProcessor(propertyKeyTransformer),
                "_sell_only_mode_enabled", new SellOnlyModeEnabledSpecialCaseCsvProcessor(propertyKeyTransformer),
                "_dca_enabled", new DcaEnabledSpecialCaseCsvProcessor(propertyKeyTransformer)
        );

        final Application application = new Application(
                indicatorsProperties,
                dcaProperties,
                pairsProperties,
                propertyKeyTransformer,
                specialCases,
                specialCaseProcessors,
                specialCaseCsvProcessors);

        final ApplicationSettings applicationSettings = application.run();

        Assertions.assertThat(applicationSettings.getPtFeeder()).containsAllEntriesOf(ImmutableMap.<String, String>builder()
                .put("BaseCurrency", "BTC")
                .put("DcaExcludedCoins", "STE,KKK")
                .put("ExcludedCoins", "BTC,BNB,TRX")
                .put("HiddenCoins", "ALL")
                .put("MinutesForLongerTermTrend", "720")
                .put("MinutesToMeasureTrend", "120")
                .put("SomOnlyCoins", "ETC,XRP,ETH")
                .put("TopCurrenciesToCheck", "35").build());

        Assertions.assertThat(applicationSettings.getCommon()).containsAllEntriesOf(ImmutableMap.<String, String>builder()
                .put("DcaKeepBalance", "0")
                .put("DcaKeepBalancePercentage", "0")
                .put("EnabledPairs", "ALL")
                .put("IndicatorsBbCandlePeriod", "300")
                .put("IndicatorsBbLength", "20")
                .put("IndicatorsBbStd", "2")
                .put("IndicatorsEmaCandlePeriod", "300")
                .put("IndicatorsEmaCrossCandles", "3")
                .put("IndicatorsEmaFastLength", "3")
                .put("IndicatorsEmaSlowLength", "24")
                .put("IndicatorsMacdCandlePeriod", "300")
                .put("IndicatorsMacdFastLength", "12")
                .put("IndicatorsMacdSignal", "9")
                .put("IndicatorsMacdSlowLength", "26")
                .put("IndicatorsRsiCandlePeriod", "300")
                .put("IndicatorsRsiLength", "14")
                .put("IndicatorsSmaCandlePeriod", "300")
                .put("IndicatorsSmaCrossCandles", "2")
                .put("IndicatorsSmaFastLength", "12")
                .put("IndicatorsSmaSlowLength", "24")
                .put("IndicatorsStochLength", "14")
                .put("KeepBalance", "0")
                .put("MaxTradingPairs", "5")
                .put("PairMinListedDays", "14")
                .put("PptPanicSellEnabled", "true")
                .put("PriceDropRecoverTrigger", "0")
                .put("PriceDropTrigger", "0")
                .put("PriceRiseTrigger", "0")
                .put("PriceRiseTriggerRecover", "0")
                .put("StartBalance", "0").build());

        Assertions.assertThat(applicationSettings.getDefaults()).containsAllEntriesOf(ImmutableMap.<String, String>builder()
                .put("ABuyStrategy", "LOWBB")
                .put("ABuyValue", "5")
                .put("ABuyValueLimit", "-2.5")
                .put("ASellStrategy", "GAIN")
                .put("ASellValue", "1")
                .put("BBuyStrategy", "RSI")
                .put("BBuyValue", "33")
                .put("BBuyValueLimit", "0")
                .put("BSellStrategy", "RSI")
                .put("BSellValue", "40")
                .put("BuyMaxChangePercentage", "8")
                .put("BuyMinChangePercentage", "-10")
                .put("DcaABuyStrategy", "LOWBB")
                .put("DcaABuyValue", "5")
                .put("DcaABuyValueLimit", "-2.5")
                .put("DcaASellStrategy", "GAIN")
                .put("DcaASellValue", "1")
                .put("DcaASellValue1", "0.95")
                .put("DcaASellValue2", "0.85")
                .put("DcaASellValue3", "0.75")
                .put("DcaBBuyStrategy", "RSI")
                .put("DcaBBuyValue", "33")
                .put("DcaBBuyValueLimit", "5")
                .put("DcaBSellStrategy", "RSI")
                .put("DcaBSellValue", "40")
                .put("DcaBuyPercentage", "100")
                .put("DcaBuyPercentage1", "100")
                .put("DcaBuyPercentage2", "50")
                .put("DcaBuyPercentage3", "50")
                .put("DcaBuyPercentage4", "25")
                .put("DcaBuyPercentage5", "30")
                .put("DcaBuyPercentage6", "25")
                .put("DcaBuyPercentage7", "20")
                .put("DcaBuyPercentage8", "15.5")
                .put("DcaBuyPercentage9", "12.11")
                .put("DcaBuyPercentage10", "10")
                .put("DcaBuyPercentage11", "8")
                .put("DcaBuyPercentage12", "7")
                .put("DcaBuyPercentage13", "6")
                .put("DcaBuyPercentage14", "6")
                .put("DcaBuyPercentage15", "5")
                .put("DcaBuyPercentage16", "5")
                .put("DcaBuyPercentage17", "5")
                .put("DcaBuyPercentage18", "5")
                .put("DcaBuyPercentage19", "5")
                .put("DcaBuyPercentage20", "5")
                .put("DcaBuyTrigger", "0")
                .put("DcaEnabled", "true")
                .put("DcaIgnoreSellOnlyMode", "false")
                .put("DcaMaxBuySpread", "1.5")
                .put("DcaMaxBuyTimes", "20")
                .put("DcaMaxCost", "10")
                .put("DcaMaxProfit", "0")
                .put("DcaMinOrderbookVolumePercentage", "100")
                .put("DcaPendingOrderWaitTime", "0")
                .put("DcaRebuyTimeout", "10")
                .put("DcaStopLossTimeout", "0")
                .put("DcaStopLossTrigger", "0")
                .put("DcaTrailingBuy", "0")
                .put("DcaTrailingProfit", "0.147")
                .put("InitialCost", "0.0015")
                .put("InitialCostPercentage", "0")
                .put("MaxBuySpread", "1")
                .put("MinBuyPrice", "0")
                .put("MinBuyVolume", "200")
                .put("MinOrderbookVolumePercentage", "100")
                .put("PanicSellEnabled", "false")
                .put("RebuyTimeout", "2")
                .put("SellOnlyModeEnabled", "false")
                .put("TradingEnabled", "true")
                .put("TrailingBuy", "0")
                .put("TrailingProfit", "0.16").build());

        Assertions.assertThat(applicationSettings.getMarketConditionsGrouping()).containsOnlyKeys("Configs");
        Assertions.assertThat(applicationSettings.getMarketConditionsGrouping().get("Configs"))
                .contains(new ApplicationSettings.MarketConditionsGroupingConfig("Bear", "-2"),
                        new ApplicationSettings.MarketConditionsGroupingConfig("Boring", "2"),
                        new ApplicationSettings.MarketConditionsGroupingConfig("Bear", "100000"));
    }

}
