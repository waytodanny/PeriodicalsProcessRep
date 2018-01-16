package com.periodicals.utils.encryption;

import javax.naming.OperationNotSupportedException;

/**
 * @author Daniel Volnitsky
 * 12.01.2018
 */
public interface Cryptographer {
    /**
     * @return encrypted string
     */
    String encrypt(String str);
}
