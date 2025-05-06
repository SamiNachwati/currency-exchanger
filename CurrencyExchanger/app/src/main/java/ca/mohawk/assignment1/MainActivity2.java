package ca.mohawk.assignment1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Objects;


/**
 * Class of MainActivity2
 */
public class MainActivity2 extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener{

    /*
    I, Sami Nachwati, 000879289 certify that this material is my original work.
    No other person's work has been used without due acknowledgment
     */
    /*
    Currency sources:
    https://www.oanda.com/currency-converter/en/?from=CAD&to=LBP&amount=1
     */

    /* Last position of the spinner */
    static int lastPosition = 0;

    /* Name of the currency */
    static String currency = "";

    /* Current currency */
    static Currency currentCurrency;

    /* HashMap of currencies */
    private final HashMap<String, Currency> currencies = new HashMap<>();

    /* File name for SharedPreferences */
    public static final String SP_File = "saved_configuration";

    /* Keys for currency name */
    public static final String Currency_Name = "currency_name";

    /* Keys for currency value */
    public static final String Currency_Value = "currency_value";

    /* Keys for currency key */
    public static final String Currency_Key = "currency_key";

    /* Key for spinner state */
    public static final String Spin_State = "spin_state";

    /* First time flag for loading preferences */
    public static boolean firstTime = true;


    /**
     * onCreate method for MainActivity2
     * @param savedInstanceState saved instance state
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView rate = findViewById(R.id.rate);
        Button saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(this::save);
        Spinner spinner = findViewById(R.id.spinner);

        // Populate currencies BEFORE loading preferences
        currencies.put("US Dollar", new Currency(0.70,"us"));
        currencies.put("Saudi Riyal", new Currency(2.63, "sa"));
        currencies.put("Lebanese LBP", new Currency(62_748.80,"lb"));
        currencies.put("Italian Euro", new Currency(0.67,"it"));
        currencies.put("Japanese Yen", new Currency(107.80,"jp"));
        currencies.put("Singapore Dollar", new Currency(0.95,"sg"));
        currencies.put("Thailand Baht", new Currency(23.70, "th"));
        currencies.put("Tunisia Dinar", new Currency(2.23, "tn"));
        currencies.put("Turkey Lira", new Currency(25.33,"tr"));
        currencies.put("Sweden Krona", new Currency(7.58,"se"));
        currencies.put("Spain Peso", new Currency(111.94,"es"));
        currencies.put("Russia Ruble", new Currency(64.16,"ru"));
        currencies.put("Morocco Dirham", new Currency(6.92,"ma"));
        currencies.put("Mexico Peso", new Currency(14.39,"mx"));

        // Load preferences AFTER populating currencies
        if (firstTime) {
            loadPreferences();
            firstTime = false;
        }

        // Ensure currentCurrency is never null
        if (currentCurrency == null) {
            currentCurrency = currencies.get("US Dollar");
        }

        spinner.setSelection(lastPosition, true);
        spinner.setOnItemSelectedListener(this);

        ImageView country = findViewById(R.id.selectedImage);
        int curId = getResources().getIdentifier(this.currencies.get(currency).getCurrencyName(), "drawable", getPackageName());

        country.setImageResource(curId);
        rate.setText("Conversion Rate = " + currentCurrency.getCurrencyValue());
    }


    /**
     * onItemSelected method for MainActivity2
     * @param parent parent view group
     * @param view view of the item
     * @param position position of the item in the spinner
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent,
                               View view, int position, long id){
        String[] currencies = getResources().getStringArray(R.array.currencies);
        TextView rate = findViewById(R.id.rate);
        lastPosition = position;
        currency = currencies[position];
        rate.setText("Conversion Rate = " + Objects.requireNonNull(this.currencies.get(currency)).getCurrencyValue());
        ImageView country = findViewById(R.id.selectedImage);

        // Update flag image
        int curId = getResources().getIdentifier(this.currencies.get(currency).getCurrencyName(), "drawable", getPackageName());
        country.setImageResource(curId);
    }

    /**
     * onNothingSelected method for MainActivity2
     * @param parent parent view group
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    /**
     * save method for MainActivity2
     * @param view view of the button
     */
    protected void save(View view) {
        currentCurrency = currencies.get(currency);
        SharedPreferences sharedPreferences = this.getSharedPreferences(SP_File, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the exact key used in currencies (e.g., "US Dollar")
        editor.putString(Currency_Key, currency);
        editor.putString(Currency_Name, currentCurrency.getCurrencyName());
        editor.putFloat(Currency_Value, (float) currentCurrency.getCurrencyValue());
        editor.putInt(Spin_State, lastPosition);
        editor.apply();

        // Notify MainActivity that settings changed
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);

        finish(); // Close MainActivity2
    }


    /**
     * loadPreferences method for MainActivity2
     */
    private void loadPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(SP_File, Context.MODE_PRIVATE);
        lastPosition = sharedPreferences.getInt(Spin_State, 0);
        currency = sharedPreferences.getString(Currency_Key, "US Dollars");

        // Ensure the currency exists in the map before assigning it
        if (currencies.containsKey(currency)) {
            currentCurrency = currencies.get(currency);
        } else {
            currentCurrency = currencies.get("US Dollar");
            currency = "US Dollar";
        }
    }
}

