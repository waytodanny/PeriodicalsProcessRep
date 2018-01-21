package com.periodicals.utils.encryption;

import javax.naming.OperationNotSupportedException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Daniel Volnitsky
 * 12.01.2018
 */
public interface Cryptographer {
    /**
     * @return encrypted string
     */
    String encrypt(String str) throws NoSuchAlgorithmException;
}
