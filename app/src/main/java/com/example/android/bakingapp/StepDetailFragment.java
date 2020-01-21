package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.POJOs.Step;

public class StepDetailFragment extends Fragment {

    Step step;
    Button backButton;
    OnBackButtonClickListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnBackButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDetailClickListener!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    public interface OnBackButtonClickListener {
        void onStepBackButtonSelected();
    }

    public StepDetailFragment(Step step) {
        this.step = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        ((TextView)view.findViewById(R.id.tv_step_video_id)).setText(step.getVideoUrl());
        ((TextView)view.findViewById(R.id.tv_step_descr_id)).setText(step.getFullDescr());
        ((TextView)view.findViewById(R.id.tv_step_thumbnail_id)).setText(step.getThumbnailUrl());

        backButton = (Button) view.findViewById(R.id.back_button_id);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onStepBackButtonSelected();
            }
        });



        return view;

    }

}
