package pl.lodz.uni.wfis.podlaski.zarzjak;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
public class CurrencyExcangeMockTest {

    private CurrencyExchange testTarget;
    //@Mock works only with @ExtendWith before class
    private CurrencyRates rates;

    @BeforeEach
    void setUp() {
        //System.out.println("SETIING UP");
        //no need if @Mock works
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
        verify(rates, atMostOnce()).getRate(any(Currency.class), any(Currency.class));
    }

    @Test
    void exchange2() {
        //System.out.println("Test 2");
        //GIVEN rates for PLN->USD is not set
        when(rates.getRate(
                eq(Currency.PLN), any(Currency.class)
        )).thenReturn(-1.0);

        //WHEN PLN->USD, amount = 100
        //THEN Exception is thrown
        assertThrows(Exception.class, ()->{
            double result = testTarget.exchange(Currency.PLN, Currency.USD,100);
        });

    }

    @Test
    void exchange3() throws Exception {
        //System.out.println("Test 2");
        //GIVEN no requirements
        //WHEN EUR->EUR, amount = 100
        double result = testTarget.exchange(Currency.EUR, Currency.EUR,100);
        //THEN result 100 euro
        assertEquals(100,result,.01);
    }
}
