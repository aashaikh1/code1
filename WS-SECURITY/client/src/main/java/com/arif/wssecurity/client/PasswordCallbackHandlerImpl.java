package com.arif.wssecurity.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class PasswordCallbackHandlerImpl implements CallbackHandler {

    private Map<String, String> pwd = new HashMap<>();

    public PasswordCallbackHandlerImpl() {
        pwd.put("Arif", "arif");
        pwd.put("Aziz", "aziz");
        pwd.put("arifclient", "keystorepass");
        pwd.put("arifserver", "keystorepass");
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pwdCallback = (WSPasswordCallback)callbacks[i];

            String password = pwd.get(pwdCallback.getIdentifier());
            if (password != null) {
                pwdCallback.setPassword(password);
                return;
            }
        }
    }

    public void setAliasPassword(String alias, String password) {
        pwd.put(alias, password);
    }
}
