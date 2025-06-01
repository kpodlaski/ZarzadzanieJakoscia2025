package biz;

import biz.AccountManager;
import biz.AuthenticationManager;
import biz.BankHistory;
import biz.InterestOperator;
import db.dao.DAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Account;
import model.User;
import model.exceptions.OperationIsNotAllowedException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void haveAccountWithPln(String userName, Integer accID, double amount) throws SQLException {
        // Write code here that turns the phrase above into concrete actions
        User user = dao.findUserByName(userName);
        Account acc = new Account();
        acc.setAmmount(amount);
        acc.setId(accID);
        acc.setOwner(user);
        when(dao.findAccountById(accID)).thenReturn(acc);
    }
    @Given("There is an account:{int} with {double} pln")
    public void thereIsAnAccountWithPln(Integer accID, double amount) throws SQLException {
        // Write code here that turns the phrase above into concrete actions
        Account acc = new Account();
        acc.setAmmount(amount);
        acc.setId(accID);
        when(dao.findAccountById(accID)).thenReturn(acc);
    }
    @Given("Everything is authorised")
    public void everythingIsAuthorised() {
        // Write code here that turns the phrase above into concrete actions
        when(auth.canInvokeOperation(any(), any())).thenReturn(true);
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with amount: {double}")
    public void makeTransferFromAccToAccWithAmount(String userName, Integer srcId, Integer destID, double amount) throws SQLException, OperationIsNotAllowedException {
        // Write code here that turns the phrase above into concrete actions
        User user = dao.findUserByName(userName);
        target.internalPayment(user,amount, "Opis transakcji", srcId, destID);
    }
    @Then("account:{int} value:{double} pln")
    public void accountValuePln(Integer accId, Double amount) throws SQLException {
        // Write code here that turns the phrase above into concrete actions
        Account acc = dao.findAccountById(accId);
        assertEquals(amount, acc.getAmmount());
    }

}
