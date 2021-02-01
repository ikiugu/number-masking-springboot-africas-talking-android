package com.ikiugu.at_voice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    public SwipeRefreshLayout swipeRefreshLayout;

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

        buildListView(view);

        getData();
    }

    private void buildListView(@NonNull View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipe_container);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        customLeadAdapter = new CustomLeadAdapter(new ArrayList<>());
        recyclerView.setAdapter(customLeadAdapter);
    }

    private void getData() {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS_NAME, 0);

        String phone = sharedPreferences.getString(AGENT_PHONE, "");

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), "Clear data and try again", Toast.LENGTH_SHORT).show();
            return;
        }


        RetrofitClient.getInstance().getApi().getLeadsByPhoneNumber(phone)
                .enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (response.isSuccessful()) {
                            updateAdapter(response.body());
                        } else {
                            showToastError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                        showToastError();
                    }
                });
    }

    private void showToastError() {
        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
    }

    private void updateAdapter(List<Customer> customers) {
        System.out.println(customers.size());
        customLeadAdapter.clear();
        customLeadAdapter.addAll(customers);
    }
}