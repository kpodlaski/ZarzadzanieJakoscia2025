package biz;

import biz.AccountManager;
import biz.AuthenticationManager;
import biz.BankHistory;
import biz.InterestOperator;
import db.dao.DAO;
import io.cucumber.java.en.Given;
import model.Account;
import model.User;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoneyTansferSteps {

    private AccountManager target;
    DAO dao;
    BankHistory history ;
    AuthenticationManager auth;
    InterestOperator interestOperator;

    @Given("Prepare AcountManager With Mocks")
    public void prapareAMForMockTests(){
        dao = mock(DAO.class);
        history = mock(BankHistory.class);
        auth = mock(AuthenticationManager.class);
        interestOperator = mock(InterestOperator.class);
        target = new AccountManager();
        target.dao = dao;
        target.history = history;
        target.auth = auth;
        target.interestOperator = interestOperator;
    }

    @Given("We have user {string} with id: {int}")
    public void WeHaveUserWithID(String userName, Integer userId) throws SQLException {
        User user = new User();
        user.setName(userName);
        user.setId(userId);
        when(dao.findUserByName(userName)).thenReturn(user);
    }

    @Given("{string} have account: {int} with: {double} pln")
    public void HaveAccountWithPln(String userName, Integer accID, double amount) throws SQLException {
        // Write code here that turns the phrase above into concrete actions
        User user = dao.findUserByName(userName);
        Account acc = new Account();
        acc.setAmmount(amount);
        acc.setId(accID);
        acc.setOwner(user);
        when(dao.findAccountById(accID)).thenReturn(acc);
    }
    @Given("There is an account:{int} with {double} pln")
    public void there_is_an_account_with_pln(Integer accID, double amount) throws SQLException {
        // Write code here that turns the phrase above into concrete actions
        Account acc = new Account();
        acc.setAmmount(amount);
        acc.setId(accID);
        when(dao.findAccountById(accID)).thenReturn(acc);
    }
    @Given("Everything is authorised")
    public void everything_is_authorised() {
        // Write code here that turns the phrase above into concrete actions
        when(auth.canInvokeOperation(any(), any())).thenReturn(true);
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with amount: {int}")
    public void make_transfer_from_acc_to_acc_with_amount(String string, Integer int1, Integer int2, Integer int3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("account:{int} value:{double} pln")
    public void account_value_pln(Integer int1, Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
