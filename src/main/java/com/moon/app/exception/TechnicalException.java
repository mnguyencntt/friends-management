package com.moon.app.exception;

/**
 * The Class ParkingException.
 */
public class TechnicalException extends Exception {

   /** The Constant serialVersionUID. */
   private static final long serialVersionUID = 1L;

   /**
    * Instantiates a new parking exception.
    *
    * @param message
    *           the message
    */
   public TechnicalException(String message) {
      super(message);
   }

}