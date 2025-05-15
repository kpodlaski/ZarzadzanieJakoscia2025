package app;

public class SalaryCoputation {
    private DBAccess db;
    double taxRatio = .18;
    double computeTax(Person p){
        int v = (int) (taxRatio*p.basicSalary*1000);
        if (v%10 >= 5) v+=10;
        v= v/10;
        return v/100.0;
    }

    double computeTax(String personID){
        Person p = db.getPersonById(personID);
        if (p==null){
            return 0.0;
        }
        return computeTax(p);
    }
}
