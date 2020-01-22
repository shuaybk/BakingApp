package com.example.android.bakingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.POJOs.Step;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class StepDetailFragment extends Fragment {

    private final String BUNDLE_STEP_KEY = "STEP DETAIL STEP KEY";

    Step step;
    private SimpleExoPlayer exoPlayer;


    public StepDetailFragment () {}

    public StepDetailFragment(Step step) {
        this.step = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        if (savedInstanceState != null) {
            step = (Step)savedInstanceState.getSerializable(BUNDLE_STEP_KEY);
        }
        ((TextView)view.findViewById(R.id.tv_step_descr_id)).setText(step.getFullDescr());

        initVideoPlayer(view);

        return view;

    }

    //Initialize the exoplayer if there is a video for this step, otherwise hide the video player
    private void initVideoPlayer(View view) {
        PlayerView simpleExoPlayerView = view.findViewById(R.id.exoplayer_id);

        if (step.getVideoUrl().equals("")) {
            simpleExoPlayerView.setVisibility(View.GONE);
        } else {
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            simpleExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(true);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "VideoPlayer"));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(step.getVideoUrl()));
            exoPlayer.prepare(videoSource);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(BUNDLE_STEP_KEY, step);
        super.onSaveInstanceState(outState);

    }

}
