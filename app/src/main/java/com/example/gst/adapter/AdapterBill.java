package com.example.gst.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.gst.Final_Billing;
import com.example.gst.R;
import com.example.gst.models.Billing;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
public class AdapterBill extends RecyclerView.Adapter<AdapterBill.MyViewHolder> {

    private List<Billing> billingList;
    Context context;


    public AdapterBill(List<Billing> billingList, Context context) {
        this.billingList = billingList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Billing billings = billingList.get(position);
        holder.billingNameTextView.setText(billingList.get(position).getName());
        holder.phoneNoTextView.setText(billingList.get(position).getPhoneNo());
        holder.descriptionTextView.setText(billingList.get(position).getDescription());
        holder.amountTextView.setText(billingList.get(position).getAmount());
        holder.QuanTextView.setText(billingList.get(position).getQuan());
        holder.taxSpinner.setText(billingList.get(position).getSpin());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Final_Billing.class);
                intent.putExtra("name", billings.getName());
                intent.putExtra("phoneNo", billings.getPhoneNo());
                intent.putExtra("description", billings.getDescription());
                intent.putExtra("amount", billings.getAmount());
                intent.putExtra("quan", billings.getQuan());
                intent.putExtra("spin", billings.getSpin());
                context.startActivity(intent);
            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Billing");

                    // Get the specific child node reference based on your data structure
                    Billing billings = billingList.get(position);
                    DatabaseReference childRef = databaseRef.child(billings.getKey());

                    String generatedKey = childRef.getKey();
                    Billing billingss = new Billing();
                    billingss.setKey(generatedKey);

                    // Remove the data from the Firebase database
                    childRef.removeValue();
                    // Remove the item from the list
                    billingList.remove(position);
                    notifyItemRemoved(position);


                }
            }
        });
        // Set the delete icon click listener
//        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Remove the item from the list
//                billingList.remove(position);
//                notifyItemRemoved(position);
        // Get the reference to the Firebase database node where the data is stored
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("your_database_node");
//
//        // Get the specific child node reference based on your data structure
//        DatabaseReference childRef = databaseRef.child(billings.getId());
//        DatabaseReference childRef = databaseRef.push();
//        String generatedKey = childRef.getKey();
//        Billing billings = new Billing();
//        billings.setId(generatedKey);
//// Set other fields of the billing object
//        childRef.setValue(billings);
//
//        // Remove the data from the Firebase database
//        childRef.removeValue();
//            }
//        });

    }



    @Override
    public int getItemCount() {
        return billingList.size();
    }

    public class MyViewHolder extends ViewHolder {

        TextView billingNameTextView;
        TextView phoneNoTextView;
        TextView descriptionTextView;
        TextView amountTextView;
        TextView QuanTextView;
        TextView taxSpinner;
        ImageView deleteIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            billingNameTextView = itemView.findViewById(R.id.name);
            phoneNoTextView = itemView.findViewById(R.id.phoneNo);
            descriptionTextView = itemView.findViewById(R.id.description_name);
            amountTextView = itemView.findViewById(R.id.price);
            QuanTextView = itemView.findViewById(R.id.quantity);
            taxSpinner = itemView.findViewById(R.id.tax);
            deleteIcon = itemView.findViewById(R.id.next);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Intent intent = new Intent(view.getContext(),Final_Billing.class);
//                    view.getContext().startActivity(intent);

                }
            });
                // Other view references




        }

//        public void onClick(View view) {
//
//            int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                Billing billng = billingList.get(position);
//
//                Intent intent = new Intent(context, Final_Billing.class);
//                intent.putExtra("name", billng.getName());
//                intent.putExtra("phoneNo", billng.getPhoneNo());
//                intent.putExtra("description", billng.getDescription());
//                intent.putExtra("amount", billng.getAmount());
//                intent.putExtra("Key",billng.getKey());
//                intent.putExtra("quan",billng.getQuan());
//                intent.putExtra("spin", billng.getSpin());
//                context.startActivity(intent);
//            }
//        }

    }
}


