package com.easy.cooking.learneat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.CompletedRecipe;
import com.easy.cooking.learneat.models.Recipe;
import com.easy.cooking.learneat.utils.BitmapUtils;
import com.easy.cooking.learneat.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadPhototActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 10;
    private static final int REQUEST_STORAGE_PERMISSION = 11;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider";
    private static final String DD_MM_YYYY_FORMAT = "dd-MM-YYYY";
    private static final String HH_MM_SS_FORMAT = "hh:mm:ss";
    private static final String JPG_EXTENSION = ".jpg";
    private static final String COMPLETED_RECIPES_FOLDER = "completedRecipes";

    @BindView(R.id.upload_photo_toolbar)
    Toolbar uploadPhotoToolbar;

    @BindView(R.id.btn_upload_photo)
    Button btnUploadPhoto;

    @BindView(R.id.tv_upload_photo_subtitle)
    TextView tvUploadPhotoSubtitle;

    @BindView(R.id.iv_upload_photo)
    ImageView ivUploadPhoto;

    @BindView(R.id.fab_clear)
    FloatingActionButton fabClear;

    @BindView(R.id.fab_save)
    FloatingActionButton fabSave;

    private String temporaryPhotoPath;
    private Intent intent;

    private Bitmap resultBitmap;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photot);
        ButterKnife.bind(this);

        intent = getIntent();
        if (intent == null) {
            showErrorMessage();
            return;
        }

        initToolbar();

    }

    private void showErrorMessage() {
        Toast.makeText(this, R.string.recipe_data_error, Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        uploadPhotoToolbar.setTitle(R.string.upload_photo_title_toolbar);
        setSupportActionBar(uploadPhotoToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @OnClick(R.id.btn_upload_photo)
    public void uploadPhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // request the external storage permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // there is a camera activity to handle the intent
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTemporaryFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                temporaryPhotoPath = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processImage();
        } else {
            BitmapUtils.deleteTemporaryFile(this, temporaryPhotoPath);
        }
    }

    private void processImage() {
        btnUploadPhoto.setVisibility(View.GONE);
        tvUploadPhotoSubtitle.setVisibility(View.GONE);
        fabClear.setVisibility(View.VISIBLE);
        fabSave.setVisibility(View.VISIBLE);

        resultBitmap = BitmapUtils.resamplePicture(this, temporaryPhotoPath);
        ivUploadPhoto.setImageBitmap(resultBitmap);
    }

    @OnClick(R.id.fab_clear)
    public void clearImage(View view) {
        ivUploadPhoto.setImageResource(0);
        btnUploadPhoto.setVisibility(View.VISIBLE);
        tvUploadPhotoSubtitle.setVisibility(View.VISIBLE);
        fabSave.setVisibility(View.GONE);
        fabClear.setVisibility(View.GONE);

        BitmapUtils.deleteTemporaryFile(this, temporaryPhotoPath);
    }

    @OnClick(R.id.fab_save)
    public void saveImage(View view) {
        recipe = intent.getParcelableExtra(Constants.EXTRA_RECIPE);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();

        final String userUid = auth.getCurrentUser().getUid();

        Date currentTime = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        final String ddmmyyyy = new SimpleDateFormat(DD_MM_YYYY_FORMAT).format(currentTime);
        @SuppressLint("SimpleDateFormat")
        final String hhmmss = new SimpleDateFormat(HH_MM_SS_FORMAT).format(currentTime);

        UploadTask uploadTask = storage
                .getReference(userUid)
                .child(COMPLETED_RECIPES_FOLDER + "/" + getImageName(ddmmyyyy, hhmmss))
                .putBytes(byteArray);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getReference(userUid + "/" + COMPLETED_RECIPES_FOLDER)
                        .child(getImageName(ddmmyyyy, hhmmss))
                        .getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                CompletedRecipe completedRecipe = new CompletedRecipe(
                                        recipe.getIdRecipe(),
                                        recipe.getTitleRecipe(),
                                        uri.toString(),
                                        ddmmyyyy + " " + hhmmss,
                                        recipe.getPointsRecipe());

                                FirebaseController.getInstance().addCompletedRecipe(auth.getCurrentUser(), completedRecipe);
                                Toast.makeText(UploadPhototActivity.this, "Succes pe getURI:" + uri, Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(UploadPhototActivity.this, "Eroare uri", Toast.LENGTH_LONG).show();
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadPhototActivity.this, "Upload cu eroare", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getImageName(String ddmmyyyy, String hhmmss) {
        return new StringBuilder()
                .append(recipe.getIdRecipe())
                .append(ddmmyyyy)
                .append('-')
                .append(hhmmss)
                .append(JPG_EXTENSION)
                .toString();
    }

}
