package com.example.project_todolistapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_header extends Fragment {

    private static final String SHARED_PREF = "sharedPrefs";
    private static final String BACKGROUND = "background";
    private RelativeLayout defaultBtn, emberBtn, persianBtn, phaseBtn, spectrumBtn, stealthBtn;

    private String backgroundChoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.header_fragment, container, false);
    }

    public void removeAllBG(View def, View ember, View persian, View phase, View Spec, View ste) {
        def.setBackgroundColor(0xFFF2F2F2);
        ember.setBackgroundColor(0xFFF2F2F2);
        persian.setBackgroundColor(0xFFF2F2F2);
        phase.setBackgroundColor(0xFFF2F2F2);
        Spec.setBackgroundColor(0xFFF2F2F2);
        ste.setBackgroundColor(0xFFF2F2F2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        defaultBtn = view.findViewById(R.id.defaultBtn);
        emberBtn = view.findViewById(R.id.emberBtn);
        persianBtn = view.findViewById(R.id.persianBtn);
        phaseBtn = view.findViewById(R.id.phaseBtn);
        spectrumBtn = view.findViewById(R.id.spectrumBtn);
        stealthBtn = view.findViewById(R.id.stealthBtn);

        loadSharePrefs();

        switch (backgroundChoice) {
            case "default":
                defaultBtn.setBackgroundColor(0xFFBFDEFF);
                break;
            case "ember":
                emberBtn.setBackgroundColor(0xFFBFDEFF);
                break;
            case "persian":
                persianBtn.setBackgroundColor(0xFFBFDEFF);
                break;
            case "phase":
                phaseBtn.setBackgroundColor(0xFFBFDEFF);
                break;
            case "spectrum":
                spectrumBtn.setBackgroundColor(0xFFBFDEFF);
                break;
            case "stealth":
                stealthBtn.setBackgroundColor(0xFFBFDEFF);
                break;
        }

        // create onclick listener for all btn
        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData("default");
                removeAllBG(defaultBtn, emberBtn, persianBtn, phaseBtn, spectrumBtn, stealthBtn);
                defaultBtn.setBackgroundColor(0xFFBFDEFF);
            }
        });
        emberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData("ember");
                removeAllBG(defaultBtn, emberBtn, persianBtn, phaseBtn, spectrumBtn, stealthBtn);
                emberBtn.setBackgroundColor(0xFFBFDEFF);
            }
        });
        persianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData("persian");
                removeAllBG(defaultBtn, emberBtn, persianBtn, phaseBtn, spectrumBtn, stealthBtn);
                persianBtn.setBackgroundColor(0xFFBFDEFF);
            }
        });
        phaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData("phase");
                removeAllBG(defaultBtn, emberBtn, persianBtn, phaseBtn, spectrumBtn, stealthBtn);
                phaseBtn.setBackgroundColor(0xFFBFDEFF);
            }
        });
        spectrumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData("spectrum");
                removeAllBG(defaultBtn, emberBtn, persianBtn, phaseBtn, spectrumBtn, stealthBtn);
                spectrumBtn.setBackgroundColor(0xFFBFDEFF);
            }
        });
        stealthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData("stealth");
                removeAllBG(defaultBtn, emberBtn, persianBtn, phaseBtn, spectrumBtn, stealthBtn);
                stealthBtn.setBackgroundColor(0xFFBFDEFF);
            }
        });
    }

    public void saveData(String choice) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(BACKGROUND, choice);

        editor.apply();
    }

    public void loadSharePrefs() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        backgroundChoice = sharedPreferences.getString(BACKGROUND, "default");
    }



}
