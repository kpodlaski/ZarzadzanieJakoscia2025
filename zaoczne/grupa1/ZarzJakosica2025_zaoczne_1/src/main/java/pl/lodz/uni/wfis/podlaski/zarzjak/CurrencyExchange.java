package pl.lodz.uni.wfis.podlaski.zarzjak;



public class CurrencyExchange {

    private CurrencyRates rates;

    public CurrencyExchange(CurrencyRates rates) {
        this.rates = rates;
    }

    public double exchange(Currency source, Currency target, double amount) throws Exception {
        if (rates == null){
            System.out.println("ojojo");
        }
        if (source == target) return amount;
        double rate = rates.getRate(source,target);
        if (rate<=0) throw new Exception("Bad rate for this exchange");
        return amount*rate;
    }

}
