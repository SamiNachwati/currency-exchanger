package ca.mohawk.assignment1;

import androidx.annotation.NonNull;

/*
I, Sami Nachwati, 000879289 certify that this material is my original work.
No other person's work has been used without due acknowledgment
 */
public class Currency {

    /* value of country's currency */
    double currencyValue;

    /* shortened name of the country */
    String currencyName;

    /**
     * Constructor for Currency class
     * @param currencyValue value of country's currency
     * @param currencyName shortened name of the country
     */
    public Currency(double currencyValue, String currencyName) {
        this.currencyValue = currencyValue;
        this.currencyName = currencyName;
    }

    /**
     * Getter for currencyValue
     * @return value of country's currency
     */
    public double getCurrencyValue() {
        return currencyValue;
    }

    /**
     * Getter for currencyName
     * @return shortened name of the country
     */
    public String getCurrencyName() {
        return currencyName;
    }


    /**
     * toString method for Currency class
     * @return String representation of Currency object
     */
    @NonNull
    public String toString(){
        return currencyName + ", " + currencyValue;
    }
}
