package pl.lodz.uni.wfis.podlaski.zarzjak;

public class CurrencyExchange {

    private RateSystem rateSystem;

    public CurrencyExchange(RateSystem rs){
        this.rateSystem = rs;
    }
    public double change(Currency source, Currency target, double amount) throws Exception {
        double rate = rateSystem.getRate(source, target);
        if (rate<=0){
            throw new Exception("Wrong rate for this currencies");
        }
        return rate * amount;
    }
}
