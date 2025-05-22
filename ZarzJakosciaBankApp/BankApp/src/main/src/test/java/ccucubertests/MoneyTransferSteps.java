package ccucubertests;

import biz.AccountManager;
import biz.AuthenticationManager;
import biz.BankHistory;
import biz.InterestOperator;
import db.dao.DAO;
import io.cucumber.java.en.Given;
import model.User;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class MoneyTransferSteps {

    AccountManager target;
    AuthenticationManager auth;
    BankHistory history;
    InterestOperator interest;

    DAO dao;

    @Given("We have user {string} with id: {int}")
    public void we_have_user_with_id(String name, Integer id) throws SQLException {
        User user = new User();
        user.setName( name);
        user.setId(id);
        when(dao.findUserByName(name)).thenReturn(user);
    }

}
