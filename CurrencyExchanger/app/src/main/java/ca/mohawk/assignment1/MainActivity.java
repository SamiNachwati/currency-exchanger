package ca.mohawk.assignment1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    /*
    I, Sami Nachwati, 000879289 certify that this material is my original work.
    No other person's work has been used without due acknowledgment
     */

    /* HashMap for currency rates */
    static HashMap<String, Double> currencyRates = new HashMap<>() {{
        put("us", 0.70);
        put("sa", 2.63);
        put("lb", 62748.80);
        put("it", 0.67);
        put("jp", 107.80);
        put("sg", 0.95);
        put("th", 23.70);
        put("tn", 2.23);
        put("tr", 25.33);
        put("se", 7.58);
        put("es", 111.94);
        put("ru", 64.16);
        put("ma", 6.92);
        put("mx", 14.39);
    }};

    /* Currency key for the selected currency */
    static String currencyKey = "us";

    /* Currency exchange rate */
    static double currencyExchangeRate = 0.7;


    /**
     * onCreate method for MainActivity
     * @param savedInstanceState saved instance state
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadPreferences(); // Load saved currency selection
        updateUI(); // Update UI based on saved preferences

        Button convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(this::convert);
        this.convert(null);
    }

    /**
     * getCurrencyExchangeRate method for MainActivity
     * @return currency exchange rate
     */
    public double getCurrencyExchangeRate(){
        return currencyExchangeRate;
    }

    /**
     * setCurrencyExchangeRate method for MainActivity
     * @param rate currency exchange rate
     */
    public void setCurrencyExchangeRate(double rate){
        currencyExchangeRate = rate;
    }

    /**
     * reconfigure method for MainActivity
     * @param view view of the button
     */
    public void reconfigure(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivityForResult(intent, 1); // Expect result

    }


    /**
     * onActivityResult method for MainActivity
     * @param requestCode request code for the activity
     * @param resultCode result code for the activity
     * @param data data returned from the activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadPreferences(); // Reload preferences
            updateUI(); // Refresh UI with new values
        }
    }

    /**
     * updateUI method for MainActivity
     */
    @SuppressLint("SetTextI18n")
    private void updateUI() {
        ImageView destination = findViewById(R.id.destination);
        Button convertButton = findViewById(R.id.convertButton);

        // Update flag image
        int curId = getResources().getIdentifier(currencyKey, "drawable", getPackageName());
        if (curId == 0) curId = R.drawable.us;
        destination.setImageResource(curId);

        // Update currency exchange rate text
        convertButton.setText("@ " + getCurrencyExchangeRate()  + " = ");

        // Update conversion result
        this.convert(null);
    }



    /**
     * loadPreferences method for MainActivity
     */
    private void loadPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(MainActivity2.SP_File, MODE_PRIVATE);

        // Get saved currency key
        currencyKey = sharedPreferences.getString(MainActivity2.Currency_Name, "us");

        // Ensure currency key is valid
        Double rate = currencyRates.get(currencyKey);
        if (rate == null) {
            currencyKey = "us"; // Default to USD if invalid key
            rate = 0.70;
        }
        setCurrencyExchangeRate(rate);
    }


    /**
     * convert method for MainActivity
     * @param view view of the button
     */
    @SuppressLint("DefaultLocale")
    public void convert(View view){
        EditText rateInput = findViewById(R.id.rateInput);
        TextView res = findViewById(R.id.rateRes);
        if(rateInput.getText() == null || rateInput.getText().toString().isEmpty() || rateInput.getText().toString().equals("0")){
            rateInput.setText("1.0");
        }
        double rateInputFromUser = Double.parseDouble(rateInput.getText().toString());
        res.setText(String.format("%.2f", rateInputFromUser * getCurrencyExchangeRate()));

    }
}