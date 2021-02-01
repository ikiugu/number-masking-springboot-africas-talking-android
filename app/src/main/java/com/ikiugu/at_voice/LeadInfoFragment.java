package com.ikiugu.at_voice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ikiugu.at_voice.api.RetrofitClient;
import com.ikiugu.at_voice.api.dto.CustomerDto;
import com.ikiugu.at_voice.api.model.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ikiugu.at_voice.MainActivity.AGENT_PHONE;
import static com.ikiugu.at_voice.MainActivity.SHARED_PREFS_NAME;

public class LeadInfoFragment extends Fragment implements View.OnClickListener {

    boolean hasCustomer;
    EditText customerName;
    EditText customerPhoneNumber;
    EditText customerComments;
    Button addLead;
    Button callLead;
    private Customer customer;

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

        customer = LeadInfoFragmentArgs.fromBundle(getArguments()).getCustomer();
        hasCustomer = customer != null;

        customerName = view.findViewById(R.id.cust_lead_name);
        customerPhoneNumber = view.findViewById(R.id.cust_phoneNumber);
        customerComments = view.findViewById(R.id.cust_lead_comments);
        addLead = view.findViewById(R.id.add_lead);
        callLead = view.findViewById(R.id.call_lead);

        setInteractivity(customer);

        addLead.setOnClickListener(this);
        callLead.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {

        if (view.getId() == addLead.getId()) {

            SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS_NAME, 0);

            String agentPhoneNumber = sharedPreferences.getString(AGENT_PHONE, "");

            if (TextUtils.isEmpty(agentPhoneNumber)) {
                Toast.makeText(getContext(), "Agent phone number is blank", Toast.LENGTH_SHORT).show();
                return;
            }


            String name = customerName.getText().toString();
            String number = customerPhoneNumber.getText().toString();
            String comment = customerComments.getText().toString();

            Customer customer = new Customer();
            customer.setName(name);
            customer.setPhoneNumber(number);
            customer.setComment(comment);

            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomer(customer);
            customerDto.setAgentPhoneNumber(agentPhoneNumber);

            RetrofitClient.getInstance().getApi().createLead(customerDto)
                    .enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                if (response.body()) {
                                    Navigation.findNavController(view).navigate(LeadInfoFragmentDirections.actionLeadInfoFragmentToMainFragment());
                                } else {
                                    showToast("An error occurred");
                                }
                            } else {
                                showToast("An error occurred");
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            showToast(t.getMessage());
                        }
                    });

        }


        if (view.getId() == callLead.getId()) {
            customer.setCalling(true);
            customer.setComment("Call attempted");
            RetrofitClient.getInstance().getApi().updateCustomer(customer.getPhoneNumber(), customer)
                    .enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                if (response.body()) {

                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + customer.getPhoneNumber()));//change the number
                                    startActivity(callIntent);

                                } else {
                                    showToast("An error occurred");
                                }
                            } else {
                                showToast("An error occurred");
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            showToast(t.getMessage());
                        }
                    });
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}