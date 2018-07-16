package com.easy.cooking.learneat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.easy.cooking.learneat.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BitmapUtils {

    // Creates a temporary file in the cache directory
    public static File createTemporaryFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalCacheDir();

        return File.createTempFile(imageFileName,  ".jpg", storageDir);
    }

    // Deletes a file with a given path
    public static boolean deleteTemporaryFile(Context context, String imagePath) {
        File imageFile = new File(imagePath);
        boolean deleted = imageFile.delete();

        if (!deleted) {
            String errorMessage = context.getString(R.string.bitmap_utils_error);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }

        return deleted;
    }

    public static Bitmap resamplePicture(Context context, String imagePath) {
        // screen size
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int targetH = metrics.heightPixels;
        int targetW = metrics.widthPixels;

        // dimensions of the original bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }


}
