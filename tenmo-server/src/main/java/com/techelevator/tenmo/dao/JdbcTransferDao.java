package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transfer getTransferById(int transfer_id){
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer" +
                "WHERE transfer_id = ?;" ;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_id);
        if(results.next()){
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public List<Transfer> getTransfersByUserId(int user_id) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount" +
                    "FROM transfer" +
                    "WHERE account_from IN (SELECT account_id FROM account WHERE user_id = ?) OR account_to IN (SELECT account_id FROM account WHERE user_id = ?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id, user_id);
        while(results.next()){
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public Transfer sendTransfers(Transfer createdtransfer) {

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) \n" +
                "                VALUES (2, 2, (SELECT account_id FROM account WHERE user_id = ?), (SELECT account_id FROM account WHERE user_id = ?), ?) RETURNING transfer_id;";
        try{
            int newTransferId = jdbcTemplate.queryForObject(sql, int.class, createdtransfer.getUser_id_from(), createdtransfer.getUser_id_to(), createdtransfer.getAmount());
            createdtransfer.setTransfer_id(newTransferId);

            jdbcTemplate.update("UPDATE account SET balance ")






        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

//        jdbcTemplate.update("UPDATE account SET account_id WHERE user_id =?");



        return createdtransfer;
    }

    @Override
    public Transfer updateTransferStatus(Transfer updateTransfer) {
        Transfer updateTransfers = null;
        String sql = "UPDATE transfer SET transfer_type_id = ?, transfer_status_id = ?, account = ? " +
                    "WHERE transfer_id = ?;";
        try{
            int updatedTransfer = jdbcTemplate.update(sql, updateTransfer.getTransfer_type_id(), updateTransfer.getTransfer_status_id(), updateTransfer.getAmount());

        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updateTransfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet){
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rowSet.getInt("transfer_id"));
        transfer.setTransfer_type_id(rowSet.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
}
