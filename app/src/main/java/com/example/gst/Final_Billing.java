package com.example.gst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gst.models.Billing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class Final_Billing extends AppCompatActivity {
    TextView tvName,tvphno,tvdescription,tvtotal,tvquantity,tvspinner,np,gp,sgst,cgst;
    FirebaseAuth firebaseAuth;
    DatabaseReference BillingDb;
    Billing billingref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_billing);
        tvName=(TextView) findViewById(R.id.textView11);
        tvphno=(TextView) findViewById(R.id.textView12);
        tvdescription  = (TextView) findViewById(R.id.textView13);
        tvtotal = (TextView) findViewById(R.id.textView14);
        tvquantity = (TextView) findViewById(R.id.textView15);
        tvspinner = (TextView) findViewById(R.id.textView16);
        np = (TextView) findViewById(R.id.textView18);
        gp = (TextView) findViewById(R.id.textView20);
        sgst=  findViewById(R.id.textView44);
        cgst= findViewById(R.id.textView46);
        BillingDb = FirebaseDatabase.getInstance().getReference().child("Billing");
        firebaseAuth = FirebaseAuth.getInstance();
        // Retrieve the data from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phoneNo = intent.getStringExtra("phoneNo");
        String description = intent.getStringExtra("description");
        String amount = intent.getStringExtra("amount");
        String quan = intent.getStringExtra("quan");
        String spin = intent.getStringExtra("spin");
        // Convert amount, quan, and spin to integers
        int amt = Integer.parseInt(amount);
        int qt = Integer.parseInt(quan);
        float netPrice;
        float gstPrice;
        float sgstPrice;
        float cgstPrice;

        // Calculate netPrice and gstPrice
        if (spin.equals("28%")) {
            gstPrice = qt * (amt * (28 / 100f));
            sgstPrice= qt * (amt * (14 / 100f));
            cgstPrice= qt * (amt * (14 / 100f));
            netPrice = amt + gstPrice;
        } else if (spin.equals("18%")) {
            gstPrice = qt * (amt + (18 / 100f));
            sgstPrice= qt * (amt * (9 / 100f));
            cgstPrice= qt * (amt * (9 / 100f));
            netPrice = amt + gstPrice;
        } else if (spin.equals("12%")) {
            gstPrice = qt * (amt * (12 / 100f));
            sgstPrice= qt * (amt * (6 / 100f));
            cgstPrice= qt * (amt * (6/ 100f));
            netPrice = amt + gstPrice;
        } else if (spin.equals("5%")) {
            gstPrice = qt * (amt * (5 / 100f));
            sgstPrice= qt * (amt * (2.5f / 100f));
            cgstPrice= qt * (amt * (2.5f / 100f));
            netPrice = amt + gstPrice;
        } else {
            gstPrice = qt * (amt + 0f);
            sgstPrice= qt * (amt/2f + 0f);
            cgstPrice= qt * (amt/2f + 0f);
            netPrice = amt + gstPrice;
        }

        String netP = String.format("%.2f", netPrice);
        String gstP= String.format("%.2f", gstPrice);
        String sgstP = String.format("%.2f", sgstPrice);
        String cgstP= String.format("%.2f", cgstPrice);
        // Round off netPrice and gstPrice to 2 decimal places
//        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        netPrice = Float.parseFloat(decimalFormat.format(netPrice));
//        gstPrice = Float.parseFloat(decimalFormat.format(gstPrice));
        // Set the data to the TextViews
        tvName.setText(name);
        tvphno.setText(phoneNo);
        tvdescription.setText(description);
        tvtotal.setText(amount);
        tvquantity.setText(quan);
        tvspinner.setText(spin);
        np.setText(netP);
        gp.setText(gstP);
        sgst.setText(sgstP);
        cgst.setText(cgstP);
    }
}