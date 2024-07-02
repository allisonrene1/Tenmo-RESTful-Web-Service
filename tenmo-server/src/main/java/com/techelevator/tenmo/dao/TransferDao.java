package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer getTransferById(int transfer_id);

    List<Transfer> getTransfersByUserId(int user_d);

    Transfer sendTransfers(Transfer transfer);

    Transfer updateTransferStatus(Transfer updateTransfer);


}
