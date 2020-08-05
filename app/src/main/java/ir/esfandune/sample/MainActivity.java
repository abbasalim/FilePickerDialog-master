package ir.esfandune.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.esfandune.sample.R;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import ir.esfandune.filepickerDialog.filter.CompositeFilter;
import ir.esfandune.filepickerDialog.ui.PickerDialog;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 782;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionsAndOpenFilePicker();
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
//            openFilePicker();
        }
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
//                    openFilePicker();
                } else {
                    showError();
                }
            }
        }
    }


    public void slctFolderClick(View view) {
        PickerDialog.FolderPicker(this).onFolderSelect(new PickerDialog.FolderClickListener() {
            @Override
            public void onFolderClicked(String FolderPath) {
                Toast.makeText(MainActivity.this, "پوشه انتخاب شد" + "\n" + FolderPath, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void slctFileClick(View view) {
        PickerDialog.FilePicker(this).onFileSelect(new PickerDialog.FileClickListener() {
            @Override
            public void onFileClicked(File clickedFile) {
                Toast.makeText(MainActivity.this, "انتخاب شد" + "\n" + clickedFile.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void slctFiltrdFileClick(View view) {
        ArrayList<FileFilter> ff = new ArrayList<>();
        ff.add(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith(".pdf");
            }
        });
        CompositeFilter cf = new CompositeFilter(ff);
        PickerDialog.FilePicker(this, null, true, cf).onFileSelect(new PickerDialog.FileClickListener() {
            @Override
            public void onFileClicked(File clickedFile) {

            }
        });
    }
}
