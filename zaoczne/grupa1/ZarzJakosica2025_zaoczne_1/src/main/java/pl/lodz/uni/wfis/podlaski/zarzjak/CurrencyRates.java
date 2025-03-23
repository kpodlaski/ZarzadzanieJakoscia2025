package pl.lodz.uni.wfis.podlaski.zarzjak;

import java.util.HashMap;

public class CurrencyRates {
    HashMap<Currency, HashMap<Currency,Double>> rates;
    public CurrencyRates(){
        rates = new HashMap<>();
        for (Currency s: Currency.values()) {
            HashMap<Currency,Double> r = new HashMap<>();
            rates.put(s,r);
            for (Currency t: Currency.values()) {
                if (s == t) continue;
                r.put(t,-1.0);
            }
        }
    }
    public double getRate(Currency source, Currency target) {
        return rates.get(source).get(target);
    }
    public void setRate(Currency source, Currency target, double rate) {
        rates.get(source).put(target,rate);
    }
}
