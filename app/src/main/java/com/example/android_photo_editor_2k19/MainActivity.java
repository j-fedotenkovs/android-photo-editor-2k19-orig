package com.example.android_photo_editor_2k19;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    private SeekBar seekBarBrightness;
    private SeekBar seekBarContrast;
    private SeekBar seekBarSaturation;
    private PictureThread thread;
    private Button bRight;
    private Button bLeft;
    private static final int PICK_IMAGE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.img);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        thread = new PictureThread(imageView,bitmap);

        thread.start();





        seekBarBrightness = (SeekBar) findViewById(R.id.seekBarBrightness);
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                thread.adjustBrightness(seekBar.getProgress()-100);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }


        });

        seekBarContrast = (SeekBar) findViewById(R.id.seekBarContrast);
        seekBarContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                thread.adjustContrast(seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

        seekBarSaturation = (SeekBar) findViewById(R.id.seekBarSaturation);
        seekBarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                thread.adjustSaturation(seekBar.getProgress()-255);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });


        bRight = (Button) findViewById(R.id.right);
        bRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               thread.rotateBitmap(90);
            }
        });

        bLeft = (Button) findViewById(R.id.left);
        bLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                thread.rotateBitmap(-90);
            }
        });


    }

    public void pick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICK_IMAGE_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();

                    // method 1
                    try {
                        Bitmap fileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // method 2

                    //try {
                    //    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                    //    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    //    imageStream.close(;
                    //   iv.setImageBitmap(yourSelectedImage);
                    //} catch (FileNotFoundException e) {
                    //    e.printStackTrace();
                    //}

                    // method 3
                    // iv.setImageURI(selectedImage);
                }
                break;
        }
    }
}


