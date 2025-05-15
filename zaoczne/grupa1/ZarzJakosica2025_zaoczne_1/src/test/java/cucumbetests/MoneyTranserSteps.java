package cucumbetests;

import biz.AccountManager;
import biz.AuthenticationManager;
import biz.BankHistory;
import biz.InterestOperator;
import db.dao.DAO;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Account;
import model.User;
import model.exceptions.OperationIsNotAllowedException;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoneyTranserSteps {
    private HashMap<String, User> users;
    private HashMap<Integer, Account> accounts;
    private DAO dao;
    private BankHistory history;
    private AuthenticationManager auth;
    private InterestOperator interestOperator;
    private AccountManager testObject;

    private boolean result;

    @Before
    public void setUp(){
        users = new HashMap<>();
        accounts = new HashMap<>();
        dao = mock(DAO.class);
        history = mock(BankHistory.class);
        auth = mock(AuthenticationManager.class);
        interestOperator = mock(InterestOperator.class);
        testObject = new AccountManager();
        result = false;
        Field field;
        try {
            field = AccountManager.class.getDeclaredField("dao");
            field.setAccessible(true);
            field.set(testObject,dao); //aM.dao = dao;
            field.setAccessible(false);
            field = AccountManager.class.getDeclaredField("auth");
            field.setAccessible(true);
            field.set(testObject,auth); //aM.auth = authMock;
            field.setAccessible(false);
            field = AccountManager.class.getDeclaredField("interestOperator");
            field.setAccessible(true);
            field.set(testObject,interestOperator); //aM.interestOperator = intrestMock;
            field.setAccessible(false);
            field = AccountManager.class.getDeclaredField("history");
            field.setAccessible(true);
            field.set(testObject,history); //aM.history=histMock;
            field.setAccessible(false);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Given("We have user {string} with id: {int}")
    public void we_have_user_with_id(String name, Integer userid) {
        User user = new User();
        user.setName(name);
        user.setId(userid);
        users.put(name,user);
    }

    @Given("{string} have account: {int} with: {double} pln")
    public void have_account_with_pln(String userName, Integer accID, Double amount) throws SQLException {
        User user = users.get(userName);
        Account acc = new Account();
        acc.setId(accID);
        acc.setAmmount(amount);
        acc.setOwner(user);
        accounts.put(accID,acc);
        when(dao.findAccountById(accID)).thenReturn(acc);
    }

    @Given("There is an account:{int} with {double} pln")
    public void there_is_an_account_with_pln(Integer accID, Double amount) throws SQLException {
        // Write code here that turns the phrase above into concrete actions
        Account acc = new Account();
        acc.setId(accID);
        acc.setAmmount(amount);
        accounts.put(accID,acc);
        when(dao.findAccountById(accID)).thenReturn(acc);
    }

    @Given("Everything is authorised")
    public void everything_is_authorised() {
        when(auth.canInvokeOperation(any(), any())).thenReturn(true);
    }

    @When("{string} make transfer from acc: {int} to acc: {int} with amount: {double}")
    public void make_transfer_from_acc_to_acc_with_amount(String userName, Integer srcId, Integer destID, Double amount) throws SQLException, OperationIsNotAllowedException {
        // Write code here that turns the phrase above into concrete actions
        User user = users.get(userName);
        result = testObject.internalPayment(user,amount,"Description",srcId,destID);
    }

    @Then("account:{int} value:{double} pln")
    public void account_value_pln(Integer accId, Double amount) {
        Account acc = accounts.get(accId);
        assertEquals(amount,acc.getAmmount(),0.01);
    }

    @Then("Operation is successful")
    public void opperation_is_succesfull(){
        assertTrue(result);
    }

    @Given("DAO works in a proper way")
    public void dao_updates_with_success() throws SQLException {
        when(dao.updateAccountState(any(Account.class))).thenReturn(true);
    }
}
