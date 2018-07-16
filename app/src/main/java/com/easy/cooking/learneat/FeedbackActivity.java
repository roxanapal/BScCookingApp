package com.easy.cooking.learneat;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackActivity extends AppCompatActivity {

    @BindView(R.id.feedback_toolbar)
    Toolbar feedbackToolbar;

    @BindView(R.id.feedback_btn_send)
    Button btnSendFeedback;

    @BindView(R.id.tiet_feedback_subject)
    TextInputEditText tietSubject;

    @BindView(R.id.tiet_feedback_message)
    TextInputEditText tietMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        initToolbar();

        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                String uriMessage = "mailto:" + Uri.encode(getString(R.string.feedback_owner_email)) +
                        "?subject=" + Uri.encode(tietSubject.getText().toString()) +
                        "&body=" + Uri.encode(tietMessage.getText().toString());
                Uri uri = Uri.parse(uriMessage);

                sendIntent.setData(uri);
                startActivity(Intent.createChooser(sendIntent, "Send feedback"));

            }
        });
    }

    public void initToolbar() {
        feedbackToolbar.setTitle(R.string.menu_feedback);
        setSupportActionBar(feedbackToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
