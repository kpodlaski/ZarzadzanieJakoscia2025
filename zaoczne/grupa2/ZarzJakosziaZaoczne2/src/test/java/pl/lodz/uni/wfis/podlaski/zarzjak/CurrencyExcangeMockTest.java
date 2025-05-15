package pl.lodz.uni.wfis.podlaski.zarzjak;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyExcangeMockTest {

    CurrencyExchange testObject;
    @Mock
    RateSystem rateSystem;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        //rateSystem = mock(RateSystem.class);
        //System.out.println("Seting UP");
        testObject = new CurrencyExchange(null);
        Field f = testObject.getClass().getDeclaredField("rateSystem");
        f.setAccessible(true);
        f.set(testObject,rateSystem);
        f.setAccessible(false);
    }

    @AfterEach
    void tearDown() {
        //System.out.println("tearing DOWN");
        testObject = null;
        rateSystem = null;
    }

    @Test
    void change() throws Exception {
        //GIVEN rate PLN- >EUR  = .33
        when(rateSystem.getRate(Currency.PLN,Currency.EUR)).thenReturn(.33);
        //WHEN PLN -> EUR, amount 1.23
        double result = testObject.change(Currency.PLN,Currency.EUR,1.23);
        // THEN we get 1.23*0.25
        assertEquals(.33*1.23,result,.01,"Wrong result amount");
        verify(rateSystem, atMostOnce()).getRate(any(Currency.class), any(Currency.class));
    }

    @Test()
    void change2() {
        //GIVEN default rate for currency PLN->USD == -1
        when(rateSystem.getRate(Currency.PLN,Currency.EUR)).thenReturn(-1.0);
        //WHEN PLN -> USD, amount 1.23
        //THEN exception wrong rate system
        assertThrows(Exception.class,
                () -> {testObject.change(Currency.EUR,Currency.USD,123);});
    }
    @Test()
    void change3(){
        //GIVEN if invoke rateSystem.getRate and got exception
        when(rateSystem.getRate(any(),any())).then((a)->{throw new Exception("ERRORR!!");});
        //WHEN PLN -> USD, amount 1.23
        //THEN exception wrong rate system
        assertThrows(Exception.class,
                () -> {testObject.change(Currency.EUR,Currency.USD,123);});
    }

}
