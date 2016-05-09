package com.android.gaojun.weather.GSON;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CurrencyDao {
    private RetData retData;

    public RetData getRetData() {
        return retData;
    }

    public void setRetData(RetData retData) {
        this.retData = retData;
    }

    public class RetData{
        private String date;
        private String time;
        private String fromCurrency;
        private String toCurrency;
        private String amount;
        private String currency;
        private String convertedamount;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFromCurrency() {
            return fromCurrency;
        }

        public void setFromCurrency(String fromCurrency) {
            this.fromCurrency = fromCurrency;
        }

        public String getToCurrency() {
            return toCurrency;
        }

        public void setToCurrency(String toCurrency) {
            this.toCurrency = toCurrency;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getConvertedamount() {
            return convertedamount;
        }

        public void setConvertedamount(String convertedamount) {
            this.convertedamount = convertedamount;
        }
    }
}
