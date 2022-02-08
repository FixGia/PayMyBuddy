package com.project.paymybuddy.Exception;

/**
 * NotConformDataException Class
 *
 * @author FixGia
 *
 */

public class NotConformDataException extends RuntimeException{
    /**
     * NotConformDataException Method
     *
     * @param message the error message;
     */
    public NotConformDataException(final String message){

        super(message);
    }
}
