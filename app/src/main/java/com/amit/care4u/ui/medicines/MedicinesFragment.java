package com.amit.care4u.ui.medicines;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amit.care4u.AlarmFormActivity;
import com.amit.care4u.DatabaseHandler;
import com.amit.care4u.MedicineAdapter;
import com.amit.care4u.MedicineModel;
import com.amit.care4u.R;
import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MedicinesFragment extends Fragment {

    private static final int REQUEST_PHONE_CALL = 101;
    FloatingActionButton floating;
    RecyclerView med_recycler;
    List<MedicineModel> medicineModels;
    MedicineAdapter medicineAdapter;
    DatabaseHandler databaseHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medicines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floating = view.findViewById(R.id.floating);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity().getApplicationContext(), AlarmFormActivity.class));
            }
        });

        medicineModels = new ArrayList<MedicineModel>();
        medicineModels.add(new MedicineModel("Polyjuice potion", "When sneaking...", "hp"));
        databaseHandler = new DatabaseHandler(getActivity().getApplicationContext());
        List<MedicineModel> temp = databaseHandler.viewMedicines();
        if(temp.size()>0)
            medicineModels.addAll(temp);

        med_recycler = view.findViewById(R.id.med_recycler);
        medicineAdapter = new MedicineAdapter(getActivity().getApplicationContext(), medicineModels);
        med_recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        med_recycler.setAdapter(medicineAdapter);

        ShakeDetector.create(getActivity().getApplicationContext(), new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
                Toast.makeText(getActivity().getApplicationContext(), "EMERGENCY DETECTED !", Toast.LENGTH_SHORT).show();
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:+91" + sharedPreferences.getString("mobile1", "none")));
                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    return;
                }
                startActivity(call);
            }
        });
    }
}