package com.ikiugu.at_voice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ikiugu.at_voice.api.model.Customer;

public class LeadInfoFragment extends Fragment {

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
        if (customer == null) {
            Toast.makeText(getContext(), "Customer is null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Customer name is " + customer.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}