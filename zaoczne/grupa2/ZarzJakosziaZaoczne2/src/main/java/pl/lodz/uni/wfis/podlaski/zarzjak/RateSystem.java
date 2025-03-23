package pl.lodz.uni.wfis.podlaski.zarzjak;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RateSystem {
    HashMap<Currency,HashMap<Currency,Double>> rates = 
            new HashMap<Currency, HashMap<Currency, Double>>();
    public RateSystem(){
        for (Currency c : Currency.values()) {
            HashMap<Currency, Double> r2 = new HashMap<>();
            rates.put(c,r2);
            for (Currency c2 : Currency.values()){
                r2.put(c2,-1.0);
            }
        }
    }
    public double getRate(Currency source, Currency target) {
        return rates.get(source).get(target);
    }
    public void setRate(Currency source, Currency target, double rate){
        rates.get(source).put(target,rate);
    }
}
