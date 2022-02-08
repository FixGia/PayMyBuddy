package com.project.paymybuddy.Exception;

/**
 * BalanceInsufficientClass
 *
 * @author FixGia
 */
public class BalanceInsufficientException extends RuntimeException{
    /**
     * BalanceInsufficientMethod
     *
     * @param message
     */
    public BalanceInsufficientException(final String message) {
        super(message);
    }
}
