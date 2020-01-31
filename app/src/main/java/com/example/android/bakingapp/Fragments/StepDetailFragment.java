package com.example.android.bakingapp.Fragments;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.POJOs.Step;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


public class StepDetailFragment extends Fragment {

    private final String BUNDLE_STEP_KEY = "STEP DETAIL STEP KEY";
    private final String PLAYER_POSITION_KEY = "VIDEO POSITION KEY";
    private final String PLAYER_STATE_KEY = "VIDEO STATE KEY";

    Step step;
    private SimpleExoPlayer exoPlayer;
    private TrackSelector trackSelector;
    private MediaSource videoSource;

    private PlayerView playerView;
    Boolean isLandscape;
    long video_position = C.TIME_UNSET;
    boolean video_playState = true;


    public StepDetailFragment () {}

    public StepDetailFragment(Step step) {
        this.step = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        playerView = view.findViewById(R.id.exoplayer_id);


        if (view.findViewById(R.id.landscape_layout_id) != null) {
            isLandscape = true;
        } else {
            isLandscape = false;
        }

        if (savedInstanceState != null) {
            step = (Step)savedInstanceState.getSerializable(BUNDLE_STEP_KEY);
        }

        ImageView thumbnailIv = view.findViewById(R.id.thumbnail_iv);

        if (!isLandscape || step.getVideoUrl().equals("")) {
            TextView textView = ((TextView) view.findViewById(R.id.tv_step_descr_id));
            textView.setText(step.getFullDescr());
            textView.setVisibility(View.VISIBLE);

        } else {
            //Make the video fullscreen
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            ConstraintLayout layout = view.findViewById(R.id.landscape_layout_id);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height = height;
            params.width = width;
            layout.setLayoutParams(params);
        }
        if (!step.getThumbnailUrl().equals("")) {
            Picasso.get().load(step.getThumbnailUrl()).into(thumbnailIv);
            thumbnailIv.setVisibility(View.VISIBLE);
        }

        initVideoPlayer();

        return view;

    }

    //Initialize the exoplayer if there is a video for this step, otherwise hide the video player
    private void initVideoPlayer() {

        if (step.getVideoUrl().equals("")) {
            playerView.setVisibility(View.GONE);
        } else {
            trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            playerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(video_playState);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "VideoPlayer"));
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(step.getVideoUrl()));
            exoPlayer.prepare(videoSource);
            if (video_position != C.TIME_UNSET) {
                exoPlayer.seekTo(video_position);
            }

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            video_position = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer == null) {
            initVideoPlayer();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        long position = C.TIME_UNSET;
        boolean playState = true;
        if (exoPlayer != null) {
            position = exoPlayer.getCurrentPosition();
            playState = exoPlayer.getPlayWhenReady();
        }

        outState.putSerializable(BUNDLE_STEP_KEY, step);
        outState.putLong(PLAYER_POSITION_KEY, position);
        outState.putBoolean(PLAYER_STATE_KEY, playState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            video_position = savedInstanceState.getLong(PLAYER_POSITION_KEY);
            video_playState = savedInstanceState.getBoolean(PLAYER_STATE_KEY);
            if (exoPlayer != null) {
                exoPlayer.release();
            }
            initVideoPlayer();
        }
    }
}
