package app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SalaryCoputationTest {

    SalaryCoputation target;
    DBAccess db;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        target = new SalaryCoputation();
        db = mock(DBAccess.class);

        Field f = SalaryCoputation.class.getDeclaredField("db");
        f.setAccessible(true);
        f.set(target,db);
        f.setAccessible(false);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void computeTax() {
        //Preapare sate for operations
        Person p = new Person();
        p.name = "Alex";
        p.id = "01";
        p.basicSalary = 7356.75;
        //Conduct tested operatrions
        double result = target.computeTax(p);
        //Validate expected results
        double expected = 1324.22;
        assertEquals(expected,result, .001);
    }

    @Test
    void computeTax2() {
        //Preapare sate for operations
        Person p = new Person();
        p.name = "Alex";
        p.id = "01";
        p.basicSalary = 7356.75;
        target.taxRatio = .32;
        //Conduct tested operatrions
        double result = target.computeTax(p);
        //Validate expected results
        double expected = 2354.16;
        assertEquals(expected,result, .001);
    }

    @Test
    void computeTax3() throws NoSuchFieldException, IllegalAccessException {
        //Preapare sate for operations
        Person p = new Person();
        p.name = "Alex";
        p.id = "01";
        p.basicSalary = 7356.75;
        target.taxRatio = .32;
        when(db.getPersonById("01")).thenReturn(p);

        //Conduct tested operatrions
        double result = target.computeTax("01");
        //Validate expected results
        double expected = 2354.16;
        assertEquals(expected,result, .001);
    }

    @Test
    void computeTax4()  {
        //Preapare sate for operations
        when(db.getPersonById("01")).thenReturn(null);

        //Conduct tested operatrions
        double result = target.computeTax("01");
        //Validate expected results
        double expected = 0.0; //2354.16;
        assertEquals(expected,result, .001);
    }
}