package pl.lodz.uni.wfis.podlaski.zarzjak;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyExcangeMockTest {

    private CurrencyExchange testTarget;
    private CurrencyRates rates;

    @BeforeEach
    void setUp() {
        //System.out.println("SETIING UP");
        rates = mock(CurrencyRates.class);
        testTarget = new CurrencyExchange(rates);
    }

    @AfterEach
    void tearDown() {
        //System.out.println("TEARING DOWN");
        testTarget = null;
    }

    @Test
    void mockExchange() throws Exception {
        //w obiekcie testTarget, podmieniamy rates na obiekt mock
        //CurrencyRates rates1 = mock(CurrencyRates.class);
        //Field rateField = CurrencyExchange.class.getDeclaredField("rates");
        //rateField.setAccessible(true);
        //rateField.set(testTarget, rates1);
        //rateField.setAccessible(false);
        //System.out.println("Test 1");
        //GIVEN PLN->EUR rate = .33
        when(rates.getRate(Currency.PLN,Currency.EUR)).thenReturn(.33);
        //WHEN PLN->EUR, amount = 100
        double result = testTarget.exchange(Currency.PLN, Currency.EUR,100);
        //THEN result = 25 euro
        assertEquals(.33*100,result,.01,"Bad exchange result");
    }
}
