package com.easy.cooking.learneat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.adapters.CompletedRecipeAdapter;
import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.CompletedRecipe;
import com.easy.cooking.learneat.models.User;
import com.easy.cooking.learneat.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String PROFILE_PHOTO_NAME = "profilePicture";
    private static final String PROFILE_PHOTO_UPDATE_TOAST = "Fotografia de profil a fost schimbata cu succes!";

    @BindView(R.id.profile_toolbar)
    Toolbar profileToolbar;

    @BindView(R.id.iv_profile_picture)
    ImageView ivProfilePicture;

    @BindView(R.id.fab_edit)
    FloatingActionButton fabEdit;

    @BindView(R.id.tv_profile_username)
    TextView tvProfileUsername;

    @BindView(R.id.tv_profile_points)
    TextView tvProfilePoints;

    @BindView(R.id.tv_profile_completed_recipes_null)
    TextView tvProfileGalleryNull;

    @BindView(R.id.rv_profile_completed_recipes)
    RecyclerView rvCompletedRecipes;

    private FirebaseAuth mAuth;
    private User user;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        initToolbar();

        Intent intent = getIntent();
        if (intent == null) {
            showErrorMessage();
            return;
        }

        user = (User) intent.getBundleExtra(Constants.EXTRA_BUNDLE).getSerializable(Constants.EXTRA_PROFILE);

        rvCompletedRecipes.setFocusable(false);
        setUserLayout();

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

    }

    private void setUserLayout() {
        tvProfileUsername.setText(user.getUsername());
        Picasso.get().load(user.getUrlProfilePhoto()).into(ivProfilePicture);
        tvProfilePoints.setText(String.valueOf(user.getNumberPoints()));

        List<CompletedRecipe> completedRecipeList = new ArrayList<>();
        if (user.getCompletedRecipesGallery() == null) {
            tvProfileGalleryNull.setVisibility(View.VISIBLE);
        } else {
            for (CompletedRecipe completedRecipe : user.getCompletedRecipesGallery().values()) {
                completedRecipeList.add(completedRecipe);
            }
            CompletedRecipeAdapter adapter = new CompletedRecipeAdapter(completedRecipeList, this);
            rvCompletedRecipes.setNestedScrollingEnabled(false);
            rvCompletedRecipes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvCompletedRecipes.setAdapter(adapter);
        }

    }

    private void initToolbar() {
        profileToolbar.setTitle(R.string.menu_profile);
        setSupportActionBar(profileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void showErrorMessage() {
        Toast.makeText(this, R.string.profile_data_error, Toast.LENGTH_SHORT).show();
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.profile_gallery_picker_title)), Constants.KEY_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.KEY_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        ivProfilePicture.setImageBitmap(bitmap);

                        final String userUid = mAuth.getCurrentUser().getUid();

                        UploadTask uploadTask = storage.getReference(userUid).child(PROFILE_PHOTO_NAME).putBytes(byteArray);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storage.getReference(userUid).child(PROFILE_PHOTO_NAME).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Toast.makeText(ProfileActivity.this, PROFILE_PHOTO_UPDATE_TOAST, Toast.LENGTH_LONG).show();
                                        FirebaseController.getInstance().updateProfilePhoto(mAuth.getCurrentUser(), uri.toString());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(ProfileActivity.this, R.string.error_uri, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProfileActivity.this, R.string.error_upload, Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, R.string.some_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
