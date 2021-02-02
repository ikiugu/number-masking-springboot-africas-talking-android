package com.ikiugu.at_voice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ikiugu.at_voice.adapter.CustomLeadAdapter;
import com.ikiugu.at_voice.api.RetrofitClient;
import com.ikiugu.at_voice.api.model.Customer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ikiugu.at_voice.MainActivity.AGENT_PHONE;
import static com.ikiugu.at_voice.MainActivity.SHARED_PREFS_NAME;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomLeadAdapter customLeadAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.recycler_progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(MainFragmentDirections.actionMainFragmentToLeadInfoFragment(null));
            }
        });

        buildListView(view);

        getData();
    }

    private void buildListView(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        customLeadAdapter = new CustomLeadAdapter(new ArrayList<>());
        recyclerView.setAdapter(customLeadAdapter);
    }

    private void getData() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS_NAME, 0);

        String phone = sharedPreferences.getString(AGENT_PHONE, "");

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), "Clear data and try again", Toast.LENGTH_SHORT).show();
            return;
        }


        RetrofitClient.getInstance().getApi().getLeadsByPhoneNumber(phone)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        if (response.isSuccessful()) {
                            updateAdapter(response.body());
                        } else {
                            showToastError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        showToastError();
                    }
                });
    }

    private void showToastError() {
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
    }

    private void updateAdapter(List<Customer> customers) {
        if (customers.size() == 0) {
            Toast.makeText(getContext(), "No leads yet", Toast.LENGTH_SHORT).show();
        }
        customLeadAdapter.clear();
        customLeadAdapter.addAll(customers);
    }
}