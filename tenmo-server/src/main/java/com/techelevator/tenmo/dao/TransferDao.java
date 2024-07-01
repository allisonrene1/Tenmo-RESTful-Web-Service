package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {

    Transfer getTransferById(int transfer_id);

    List<Transfer> getTransfersByUserId(int user_d);

    Transfer sendTransfers(TransferDto transferDto);

    Transfer updateTransferStatus(int transfer_id, int transfer_status_id);


}
