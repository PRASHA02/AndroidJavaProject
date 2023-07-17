package com.example.gst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gst.models.Billing;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class Addbilling extends AppCompatActivity {

    private Spinner taxSlabSpinner;
    private Button backbtn,addbtn;

    EditText fname,phno,desc,price,quantity;
    private int taxSlab=0;
    FirebaseAuth auth;
    FirebaseDatabase db;

    DatabaseReference ref;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbilling);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Billing");
        fname = (EditText) findViewById(R.id.editTextText3);
        phno = (EditText) findViewById(R.id.editTextText4);
        desc=(EditText) findViewById(R.id.editTextText5);
        price =(EditText) findViewById(R.id.editTextText6);
        quantity = (EditText) findViewById(R.id.editTextText7);
        addbtn= (Button) findViewById(R.id.button);
        backbtn = (Button) findViewById(R.id.button2);
        taxSlabSpinner = (Spinner) findViewById(R.id.tax_slab_spinner);
        setupTaxSpinner();
    }


    private void setupTaxSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tax_slab_list_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taxSlabSpinner.setAdapter(adapter);
        taxSlabSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        taxSlab = 28;
                        break;
                    case 1:
                        taxSlab = 18;
                        break;
                    case 2:
                        taxSlab = 12;
                        break;
                    case 3:
                        taxSlab = 5;
                        break;
                    case 4:
                        taxSlab = 0;
                        break;
                    case 5:
                        taxSlab=50;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                taxSlab=4;
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(areFieldsValid()) {
                    createBilling();
                    Intent intent = new Intent(Addbilling.this,Login.class);
                    startActivity(intent);
                }
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Addbilling.this,admin_dashboard.class);
                startActivity(intent);
            }
        });
    }
    private boolean areFieldsValid() {
        String name = fname.getText().toString();
        String phoneNo = phno.getText().toString();
        String description = desc.getText().toString();
        String amount = price.getText().toString();
        String quan = quantity.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!name.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "Name should only contain alphabetic characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(this, "Phone Number is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!phoneNo.matches("[0-9]+")) {
            Toast.makeText(this, "Phone Number should only contain numbers only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNo.length() != 10) {
            Toast.makeText(this, "Phone Number should contain exactly 10 numbers", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Description is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!description.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "Description should only contain alphabetic characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(amount)) {
            Toast.makeText(this, "Selling Price is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!amount.matches("[0-9]+")) {
            Toast.makeText(this, "Selling price should only contain numbers only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(quan)) {
            Toast.makeText(this, "Quantity is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!quan.matches("[0-9]+")) {
            Toast.makeText(this, "Quantity should only contain numbers only", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (quan.length() < 1 || quan.length() > 10) {
            Toast.makeText(this, "Quantity should be between 1-10 numbers only", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void createBilling() {
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String name = fname.getText().toString();
        String phoneNo = phno.getText().toString();

        String description = desc.getText().toString();
        String amount = price.getText().toString();
//    int amt = new Integer(amount).intValue();
        String quan = quantity.getText().toString();
//    int qt = new Integer(quan).intValue();
        String spin = taxSlabSpinner.getSelectedItem().toString();
//    int spin_tax = new Integer(spin).intValue();

        Billing billing = new Billing(name, phoneNo, description, amount, quan,spin);
//        String billId = ref.child(userId).push().getKey();
//        DatabaseReference reference = ref.child(userId).child(billId);
        String billId = ref.push().getKey();
        DatabaseReference reference = ref.child(billId);
        reference.setValue(billing);
        Toast.makeText(Addbilling.this, "Billing Details added", Toast.LENGTH_SHORT).show();

    }
}