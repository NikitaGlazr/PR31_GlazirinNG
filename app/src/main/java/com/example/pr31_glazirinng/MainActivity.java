package com.example.pr31_glazirinng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private float initialTouchX;
    private ImageButton cameraButton;
    private ImageButton mapsButton;
    private ImageButton googleButton;
    private ImageButton phoneButton;
    private ImageButton youtibewwwButton;
    private int artActivityIndex; // Новая переменная для хранения индекса ArtActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewFlipper viewFlipper = findViewById(R.id.viewflipper);

        cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(this);

        mapsButton = findViewById(R.id.mapsButton);
        mapsButton.setOnClickListener(this);

        googleButton = findViewById(R.id.googleButton);
        googleButton.setOnClickListener(this);

        phoneButton = findViewById(R.id.phoneButton);
        phoneButton.setOnClickListener(this);

        youtibewwwButton = findViewById(R.id.youtibewwwButton);
        youtibewwwButton.setOnClickListener(this);

        Draw2D draw2D = new Draw2D(this);
        viewFlipper.addView(draw2D); // Добавить Draw2D в ViewFlipper
        artActivityIndex = viewFlipper.indexOfChild(draw2D); // Получить индекс Draw2D

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialTouchX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float finalTouchX = event.getX();
                        if (initialTouchX > finalTouchX) {
                            viewFlipper.showNext();
                            if (viewFlipper.getDisplayedChild() == artActivityIndex) {
                                Intent intent = new Intent(MainActivity.this, ArtActivity.class);

                            }
                        } else if (initialTouchX < finalTouchX) {
                            viewFlipper.showPrevious();
                            if (viewFlipper.getDisplayedChild() == artActivityIndex) {

                            }
                        }
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cameraButton) {
            openCamera();
        } else if (v.getId() == R.id.mapsButton) {
            openMaps();
        } else if (v.getId() == R.id.googleButton) {
            openGoogle();
        } else if (v.getId() == R.id.phoneButton) {
            openPhone();
        } else if (v.getId() == R.id.youtibewwwButton) {
            openYoutibewwwButton();
        }
    }
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Создание временного файла для сохранения фотографии
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Обработка ошибки создания файла
                ex.printStackTrace();
            }

            // Если файл был успешно создан, запускаем камеру для сделанных фотографий
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); // Вставить эту строку
            }
        }
    }
    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timestamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* префикс имен файлов */
                ".jpg",         /* расширение */
                storageDir      /* каталог сохранения */
        );

        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Фотография была сделана успешно, добавьте ее в галерею
            galleryAddPic();

            // Отображение Toast с уведомлением о сохранении фотографии
            Toast.makeText(this, "Фотография сохранена", Toast.LENGTH_SHORT).show();
            // Открываем камеру снова
            openCamera();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void openMaps() {
        double latitude = 55.0084;  // Широта Новосибирска
        double longitude = 82.9357; // Долгота Новосибирска

        Uri locationUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + Uri.encode("Автостанция, Речноый вокзал"));
        Intent intent = new Intent(Intent.ACTION_VIEW, locationUri);
        startActivity(intent);
    }

    private void openGoogle() {
        Uri googleUri = Uri.parse("https://natk.ru/stud-grad/schedule/187?group=ПР-21.102");
        Intent intent = new Intent(Intent.ACTION_VIEW, googleUri);
        startActivity(intent);
    }
    private void openPhone() {
        String phoneNumber = "tel:+79139481565";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }
    private void openYoutibewwwButton() {
        String url = "https://www.youtube.com/channel/UCPh_2-wn415LPyKgfwIHrXA";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
