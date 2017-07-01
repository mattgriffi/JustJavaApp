package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private final int PRICE_PER_COFFEE = 5;
    private final int PRICE_WHIPPED_CREAM = 1;
    private final int PRICE_CHOCOLATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String orderSummary = createOrderSummary();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + getNameText());
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 101)
            displayQuantity(++quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 0)
            displayQuantity(--quantity);
    }

    /**
     * Calculates the price of the current quantity.
     */
    private int calculatePrice() {
        return quantity * (PRICE_PER_COFFEE +
                (getCheckboxState(R.id.whipped_cream_checkbox) ? PRICE_WHIPPED_CREAM : 0) +
                (getCheckboxState(R.id.chocolate_checkbox) ? PRICE_CHOCOLATE : 0));
    }

    /**
     * Converts the given num to a String with currency formatting.
     */
    private String convertToCurrency(int num) {
        return NumberFormat.getCurrencyInstance().format(num);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Returns text in name EditText.
     */
    private String getNameText() {
        EditText editText = (EditText) findViewById(R.id.name_field);
        return editText.getText().toString();
    }

    /**
     * Returns state of given checkbox id.
     */
    private boolean getCheckboxState(int id) {
        CheckBox checkBox = (CheckBox) findViewById(id);
        return checkBox.isChecked();
    }

    /**
     * Returns an order summary with a name, quantity, price, and thank you message.
     */
    private String createOrderSummary() {
        return
                getString(R.string.order_summary_name, getNameText()) +
                getString(R.string.order_summary_whipped_cream, getCheckboxState(R.id.whipped_cream_checkbox)) +
                getString(R.string.order_summary_chocolate, getCheckboxState(R.id.chocolate_checkbox)) +
                getString(R.string.order_summary_quantity, quantity) +
                getString(R.string.order_summary_total, convertToCurrency(calculatePrice())) +
                getString(R.string.order_summary_thank_you);
    }
}