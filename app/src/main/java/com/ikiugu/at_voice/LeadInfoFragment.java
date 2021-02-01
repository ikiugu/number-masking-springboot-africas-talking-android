package com.ikiugu.at_voice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ikiugu.at_voice.api.model.Customer;

public class LeadInfoFragment extends Fragment {

    boolean hasCustomer;
    EditText customerName;
    EditText customerPhoneNumber;
    EditText customerComments;
    Button addLead;
    Button callLead;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lead_info, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*NavHostFragment.findNavController(LeadInfoFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);*/

        Customer customer = LeadInfoFragmentArgs.fromBundle(getArguments()).getCustomer();
        hasCustomer = customer != null;

        customerName = view.findViewById(R.id.cust_lead_name);
        customerPhoneNumber = view.findViewById(R.id.cust_phoneNumber);
        customerComments = view.findViewById(R.id.cust_lead_comments);
        addLead = view.findViewById(R.id.add_lead);
        callLead = view.findViewById(R.id.call_lead);

        setInteractivity(customer);


    }

    private void setInteractivity(Customer customer) {
        if (hasCustomer) {
            customerName.setText(customer.getName());
            customerPhoneNumber.setText(customer.getPhoneNumber());
            customerPhoneNumber.setEnabled(false);
            customerComments.setText(customer.getComment());
            addLead.setText("Edit Lead");
            callLead.setVisibility(View.VISIBLE);
        } else {
            customerPhoneNumber.setEnabled(true);
            addLead.setText("Call Lead");
            callLead.setVisibility(View.GONE);
        }
    }
}