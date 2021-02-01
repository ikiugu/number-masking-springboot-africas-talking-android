package com.ikiugu.at_voice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ikiugu.at_voice.MainFragmentDirections;
import com.ikiugu.at_voice.R;
import com.ikiugu.at_voice.api.model.Customer;

import java.util.List;

public class CustomLeadAdapter extends RecyclerView.Adapter<CustomLeadAdapter.ViewHolder> {
    private List<Customer> customers;

    public CustomLeadAdapter(List<Customer> customers) {
        this.customers = customers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.lead_list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Customer customer = customers.get(position);
        holder.lead_name.setText(customer.getName());
        holder.lead_phone_number.setText(customer.getPhoneNumber());
        holder.lead_comment.setText(customer.getComment());

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView lead_name;
        public TextView lead_phone_number;
        public TextView lead_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            this.lead_name = (TextView) itemView.findViewById(R.id.lead_name);
            this.lead_phone_number = (TextView) itemView.findViewById(R.id.lead_phone_number);
            this.lead_comment = (TextView) itemView.findViewById(R.id.lead_comment);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Customer customer = customers.get(getAdapterPosition());
            Navigation.findNavController(view).navigate(MainFragmentDirections.actionMainFragmentToLeadInfoFragment(customer));
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        customers.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Customer> list) {
        customers.addAll(list);
        notifyDataSetChanged();
    }
}
