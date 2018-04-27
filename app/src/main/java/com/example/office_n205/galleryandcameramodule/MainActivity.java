package com.example.office_n205.galleryandcameramodule;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.office_n205.galleryandcameramodule.Gallery.GalleryGridViewLayout;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GalleryGridViewLayout galleryGridViewLayout;
    Spinner directorySpinner;
    ArrayAdapter spinnerAdapter;
    ArrayList<String> directoryList;
    Button getCheckedFiles;
    TextView fileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryGridViewLayout = findViewById(R.id.gridImageViewLayout);
        getCheckedFiles = findViewById(R.id.getCheckedFiles);
        fileNames = findViewById(R.id.fileNames);
        directorySpinner = findViewById(R.id.directorySpinner);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            else{
                initialize();
            }
        }

        else{
            initialize();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(1 == requestCode){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                initialize();
            }else{
                MainActivity.this.finish();
            }
        }
    }

    public void initialize(){
        directoryList = new ArrayList<>();
        directoryList.add("ALL");
        directoryList.addAll(galleryGridViewLayout.getAllDirectories());
        spinnerAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, directoryList);
        directorySpinner.setAdapter(spinnerAdapter);

        getCheckedFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<File> allCheckedFiles = galleryGridViewLayout.getAllCheckedFiles();
                String text = "";
                if(null != allCheckedFiles){
                    for(int i = 0; i < allCheckedFiles.size(); i++){
                        text += allCheckedFiles.get(i).getPath() + "\n";
                    }
                }
                fileNames.setText(text);
            }
        });

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(0 == position) {
                    galleryGridViewLayout.initView(galleryGridViewLayout.WITH_CHECKBOX,
                            2, galleryGridViewLayout.FROM_ALL, 10);
                    galleryGridViewLayout.addMargin(2);
                }
                else{
                    galleryGridViewLayout.initView(galleryGridViewLayout.WITH_CHECKBOX,
                            2, directorySpinner.getItemAtPosition(position).toString(), 10);
                    galleryGridViewLayout.addMargin(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            galleryGridViewLayout.initView(galleryGridViewLayout.WITH_CHECKBOX, 3,
                    galleryGridViewLayout.FROM_ALL, 10);
            galleryGridViewLayout.addMargin(2);
        }
        else{
            finish();
        }
    }
}
