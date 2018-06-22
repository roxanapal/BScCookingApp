package com.easy.cooking.learneat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easy.cooking.learneat.models.Step;
import com.easy.cooking.learneat.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepFragment extends Fragment {

    @BindView(R.id.tv_step_title)
    TextView tvStepTitle;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentStep = getArguments().getParcelable(Constants.ARGUMENT_STEP);
        }

        try {
            listener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // Inflates the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentStep = getArguments().getParcelable(Constants.ARGUMENT_STEP);

        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        tvStepTitle.setText(currentStep.getTitleStep());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvStepTitle.setText(currentStep.getTitleStep());


        // aici setezi toate campurile pe care vrei sa le afisezi pe ecran
    }

    @OnClick(R.id.btn_next_step)
    public void showNextStep(){
        listener.showNextStep(currentStep);
    }

    public interface OnFragmentInteractionListener {
        void showNextStep(Step step);
    }
}
