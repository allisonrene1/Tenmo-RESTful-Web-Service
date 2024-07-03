package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    private TransferAccountService transferAccountService = new TransferAccountService();

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                BigDecimal prompt1 = new BigDecimal(scanner.nextLine());
                if(prompt1.compareTo(BigDecimal.ZERO) > 0){
                    return prompt1;
                }
                else {
                    System.out.println("Invalid input, please enter a positive decimal!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
    public void printBalance(Account account){
        System.out.println("Your current account balance is: $" + account.getBalance() );
    }

    public Transfer printSendRequest(User[] users,  AuthenticatedUser authenticatedUser){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("Users");
        System.out.println("ID " + "           " + "Name");
        System.out.println("-----------------------------------------");
        for(User u: users){
            if(!u.getUsername().equals(authenticatedUser.getUser().getUsername())){
                System.out.println(u.getId() + "          "+ u.getUsername());
            }

        }
        System.out.println("------------------------------------------");
        System.out.println();
        int userId = promptForInt("Enter ID of user you are sending to (0 to cancel):");
        BigDecimal amount = promptForBigDecimal("Enter amount: ");
        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setUser_id_to(userId);
        transfer.setUser_id_from(authenticatedUser.getUser().getId());

        return transfer;
    }
    public void printTransfers(List<Transfer> transfers, AuthenticatedUser currentUser){
        System.out.println("------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To                 Amount");
        for(Transfer transfer: transfers){

            if(transfer.getUser_id_from() == currentUser.getUser().getId()){
                System.out.printf("%-10s TO: %-12s $%-10.2f", transfer.getTransfer_id(), transfer.getUsernameTo(), transfer.getAmount());
                System.out.println();

            }
            if(transfer.getUser_id_to() == currentUser.getUser().getId()){
                System.out.printf("%-10s FROM: %-12s $%-10.2f", transfer.getTransfer_id(), transfer.getUsernameFrom(), transfer.getAmount());
                System.out.println();

            }

        }

    }
    public void printTransferDetails(AuthenticatedUser currentUser){
        int transferId = promptForInt("Enter transfer Id to view: ");
        Transfer transfer = transferAccountService.getTransferById(transferId);

        if(transfer != null){
            System.out.println("-------------------------------------");
            System.out.println("Transfer Details");
            System.out.println("-------------------------------------");
            System.out.println("Transfer ID: " + transfer.getTransfer_id());
            System.out.println("From Account: " + transfer.getUsernameFrom());
            System.out.println("To Account: " + transfer.getUsernameTo());
            System.out.println("The Amount: $" + transfer.getAmount());
        }


    }

}