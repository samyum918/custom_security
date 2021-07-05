package com.spring.customSecurity.constant;

import com.spring.customSecurity.model.UserAccount;

import java.util.Arrays;
import java.util.List;

public class Database {
    public static List<UserAccount> userAccountList = Arrays.asList(
            new UserAccount("abc", "123", "MAKER", Arrays.asList("MAKER")),
            new UserAccount("def", "123", "CHECKER", Arrays.asList("MAKER", "CHECKER"))
    );
}
