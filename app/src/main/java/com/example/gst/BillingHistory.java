package com.example.gst;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gst.adapter.AdapterBill;
import com.example.gst.models.Billing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillingHistory extends AppCompatActivity {
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Billing> billingList;

    AdapterBill adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_history);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BillingHistory.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        billingList = new ArrayList<>();
        adapter = new AdapterBill( billingList,this);
        recyclerView.setAdapter(adapter);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Billing").child(userId);
        databaseReference = FirebaseDatabase.getInstance().getReference("Billing");
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                billingList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Billing billingClass = itemSnapshot.getValue(Billing.class);
                    billingClass.setKey(itemSnapshot.getKey());
                    billingList.add(billingClass);
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


