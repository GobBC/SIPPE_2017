/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author gvillasenor
 */
public class SMTPAuthenticator extends Authenticator {

    private String username = "";
    private String password = "";

    public SMTPAuthenticator() {
    }

    public SMTPAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);        
    }
}
