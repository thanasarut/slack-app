package service;

import bean.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@Slf4j
@Service
public class ContactService {

    public Contact getContact(String nickname) {
        CompletableFuture<String> resultExecuteContact = executeGetPhoneNumberOfStaff(nickname);
        String resultString = "";
        try {
            resultString = resultExecuteContact.get();
            String[] tokenSplit = resultString.split(":");
            if (tokenSplit.length == 2) {
                return new Contact(tokenSplit[0], tokenSplit[1].trim());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new Contact(nickname, resultString);
    }

    @Async
    public CompletableFuture<String> executeGetPhoneNumberOfStaff(String nickname) {
        String message = "";
        try {
            StringBuffer output = new StringBuffer();
            Process process;
            process = Runtime.getRuntime().exec("get_phone_number_of_staff.sh " + nickname);
            Consumer<String> consumer = i-> output.append(i);
            new BufferedReader(new InputStreamReader(process.getInputStream())).lines().forEach(consumer);
            message = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(message);
    }

}
