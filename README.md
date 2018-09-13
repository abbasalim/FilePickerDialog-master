# Android File Picker Dialog
file picker Dialog library for Android

![](http://uupload.ir/files/59lc_screenshot_from_2018-09-13_13-36-45.png)

![](http://uupload.ir/files/agiu_screenshot_from_2018-09-13_13-36-03.png)

## Using

Add repository url and dependency in application module gradle file:

```gradle
repositories {
    maven {
        url  " " 
    }
}

dependencies {
    compile ' '
}
```

## Open file Or Folder picker:

```java
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
```


Runtime permissions:

You should handle runtime permissions in activity, from what you called Material File Picker.

WaveAcc.ir

Esfandune.ir

