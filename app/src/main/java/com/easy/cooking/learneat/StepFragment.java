package com.easy.cooking.learneat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.cooking.learneat.models.Step;
import com.easy.cooking.learneat.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepFragment extends Fragment {

    @BindView(R.id.tv_step_title)
    TextView tvStepTitle;

    @BindView(R.id.tv_step_points)
    TextView tvStepPoints;

    @BindView(R.id.tv_step_description)
    TextView tvStepDescription;

    @BindView(R.id.iv_step_image)
    ImageView ivStepImage;

    @BindView(R.id.btn_next_step)
    Button btnNextStep;

    private Step currentStep;
    private OnFragmentInteractionListener listener;

    // Mandatory constructor for instantiating the fragment
    public StepFragment() {
    }

    public static StepFragment newInstance(Step step) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.ARGUMENT_STEP, step);
        StepFragment fragment = new StepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    // Inflates the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            currentStep = getArguments().getParcelable(Constants.ARGUMENT_STEP);
        }

        setupTheListener();

        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        setStepInfo(currentStep);

        return view;
    }

    private void setStepInfo(Step step) {
        tvStepTitle.setText(step.getTitleStep());

        String pointsString = step.getPointsStep() + this.getString(R.string.item_recipe_points_label);
        tvStepPoints.setText(pointsString);

        tvStepDescription.setText(step.getDescriptionStep());
        Picasso.get()
                .load(step.getUrlImageStep())
                .into(ivStepImage);
    }

    @OnClick(R.id.btn_next_step)
    public void showNextStep() {
        listener.showNextStep(currentStep);
    }

    private void setupTheListener() {
        try {
            listener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void showNextStep(Step step);
    }
}
