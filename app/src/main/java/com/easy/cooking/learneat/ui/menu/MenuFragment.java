package com.easy.cooking.learneat.ui.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.cooking.learneat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roxana on 4/21/2018.
 */

public class MenuFragment extends Fragment {

    @BindView(R.id.menu_avatar)
    ImageView ivMenuAvatar;

    @BindView(R.id.menu_name)
    TextView tvMenuName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivMenuAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatar_female));
        tvMenuName.setText(R.string.menu_test_name);
    }
}
