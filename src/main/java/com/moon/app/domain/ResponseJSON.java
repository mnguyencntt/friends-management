package com.moon.app.domain;

import org.springframework.util.ObjectUtils;

/**
 * The Class ResponseJSON.
 */
public class ResponseJSON {

   /** The status. */
   private boolean status;

   /** The info. */
   private String info;

   /** The data. */
   private Object data;

   /**
    * Instantiates a new response JSON.
    */
   public ResponseJSON() {

   }

   /**
    * Instantiates a new response JSON.
    *
    * @param status
    *           the status
    * @param info
    *           the info
    */
   public ResponseJSON(Boolean status, String info) {
      this.status = status;
      this.info = info;
   }

   /**
    * Instantiates a new response JSON.
    *
    * @param status
    *           the status
    * @param info
    *           the info
    * @param data
    *           the data
    */
   public ResponseJSON(Boolean status, String info, Object data) {
      this.status = status;
      this.info = info;
      this.data = data;
   }

   /**
    * Gets the status.
    *
    * @return the status
    */
   public Boolean getStatus() {
      return this.status;
   }

   /**
    * Sets the status.
    *
    * @param status
    *           the new status
    */
   public void setStatus(Boolean status) {
      this.status = status;
   }

   /**
    * Gets the info.
    *
    * @return the info
    */
   public String getInfo() {
      return this.info;
   }

   /**
    * Sets the info.
    *
    * @param info
    *           the new info
    */
   public void setInfo(String info) {
      this.info = info;
   }

   /**
    * Gets the data.
    *
    * @return the data
    */
   public Object getData() {
      return this.data;
   }

   /**
    * Sets the data.
    *
    * @param data
    *           the new data
    */
   public void setData(Object data) {
      this.data = data;
   }

   /**
    * The Class Builder.
    */
   public static class Builder {

      /** The status. */
      private boolean status;

      /** The info. */
      private String info;

      /** The data. */
      private Object data;

      /**
       * Sets the status builder.
       *
       * @param status
       *           the status
       * @return the builder
       */
      public Builder setStatusBuilder(final Boolean status) {
         this.status = status;
         return this;
      }

      /**
       * Sets the info builder.
       *
       * @param info
       *           the info
       * @return the builder
       */
      public Builder setInfoBuilder(final String info) {
         this.info = info;
         return this;
      }

      /**
       * Sets the data builder.
       *
       * @param data
       *           the data
       * @return the builder
       */
      public Builder setDataBuilder(final Object data) {
         this.data = data;
         if (!ObjectUtils.isEmpty(data)) {
            this.status = true;
         }
         return this;
      }

      /**
       * Builds the.
       *
       * @return the response JSON
       */
      public ResponseJSON build() {
         return new ResponseJSON(this.status, this.info, this.data);
      }
   }
}
