package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao{


    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Account getBalanceById(int user_id) {
        Account account = null;
        String sql = "SELECT balance FROM account WHERE user_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
            if(results.next()){
                account = mapRowToAccount(results);
            }
       }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;
    }
    private Account mapRowToAccount(SqlRowSet rowSet){
         Account account = new Account();
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }
}
