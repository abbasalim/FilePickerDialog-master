package com.dimorinny.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ir.esfandune.filepickerDialog.filter.CompositeFilter;
import ir.esfandune.filepickerDialog.ui.DirectoryFragment;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 782;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }

    private void openFilePicker() {
        DirectoryFragment instance = new DirectoryFragment();
        Bundle args = new Bundle();
        args.putString(DirectoryFragment.ARG_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
        args.putSerializable(DirectoryFragment.ARG_FILTER, new CompositeFilter(new ArrayList<FileFilter>()));
        args.putBoolean(DirectoryFragment.ARG_SHOW_HIDDEN, false);
        instance.setArguments(args);
        instance.show(getSupportFragmentManager(), "AnyTag");
        DirectoryFragment.FileClickListener f = new DirectoryFragment.FileClickListener() {
            @Override
            public void onFileClicked(File clickedFile) {
                Toast.makeText(MainActivity.this, "فایل انتخاب شد", Toast.LENGTH_SHORT).show();
            }
        };
        instance.setInterFace(f);
    }

    private void showError() {
        Toast.makeText(this, "Allow external storage Write", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker();
                } else {
                    showError();
                }
            }
        }
    }


    public void onnew(View view) {
        checkPermissionsAndOpenFilePicker();
    }
}
