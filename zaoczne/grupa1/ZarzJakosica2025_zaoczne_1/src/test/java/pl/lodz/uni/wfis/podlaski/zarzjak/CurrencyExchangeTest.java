package pl.lodz.uni.wfis.podlaski.zarzjak;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeTest {

    private CurrencyExchange testTarget;
    private CurrencyRates rates;

    @BeforeEach
    void setUp() {
        //System.out.println("SETIING UP");
        rates = new CurrencyRates();
        testTarget = new CurrencyExchange(rates);
    }

    @AfterEach
    void tearDown() {
        //System.out.println("TEARING DOWN");
        testTarget = null;
    }

    @Test
    void exchange() throws Exception {
        //System.out.println("Test 1");
        //GIVEN PLN->EUR rate = .33
        rates.setRate(Currency.PLN, Currency.EUR, .33);
        //WHEN PLN->EUR, amount = 100
        double result = testTarget.exchange(Currency.PLN, Currency.EUR,100);
        //THEN result = 25 euro
        assertEquals(.33*100,result,.01,"Bad exchange result");
    }

    @Test
    void exchange2() {
        //System.out.println("Test 2");
        //GIVEN rates for PLN->USD is not set
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