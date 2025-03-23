package pl.lodz.uni.wfis.podlaski.zarzjak;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeTest {

    CurrencyExchange testObject;
    RateSystem rateSystem;

    @BeforeEach
    void setUp() {
        rateSystem = new RateSystem();
        //System.out.println("Seting UP");
        testObject = new CurrencyExchange(rateSystem);
    }

    @AfterEach
    void tearDown() {
        //System.out.println("tearing DOWN");
        testObject = null;
        rateSystem = null;
    }

    @Test
    void change() throws Exception {
        //System.out.println("ch1");
        //GIVEN rate PLN- >EUR  = .33
        rateSystem.setRate(Currency.PLN,Currency.EUR,.33);
        //WHEN PLN -> EUR, amount 1.23
        double result = testObject.change(Currency.PLN,Currency.EUR,1.23);
        // THEN we get 1.23*0.25
        assertEquals(.33*1.23,result,.01,"Wrong result amount");
    }

    @Test()
    void change2() {
        //System.out.println("ch2");
        //GIVEN default rate for currency PLN->USD == -1
        //rateSystem.setRate(Currency.PLN,Currency.USD,-1);
        //WHEN PLN -> USD, amount 1.23
        //THEN exception wrong rate system
        assertThrows(Exception.class,
                () -> {testObject.change(Currency.EUR,Currency.USD,123);});
    }
}