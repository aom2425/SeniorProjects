package com.example.seniorproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.seniorproject.ExampleAdapter;
import com.example.seniorproject.ExampleItem;
import com.example.seniorproject.R;
import com.example.seniorproject.activty.DashboardActivity;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    TextView name, email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = (TextView) getView().findViewById(R.id.text_name);
        email = (TextView) getView().findViewById(R.id.text_email);
        //name.setText("Users Name");
        //email.setText("Users Email");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
