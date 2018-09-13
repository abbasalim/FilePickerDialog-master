# Android File Picker Dialog
file picker Dialog library for Android

![](http://uupload.ir/files/59lc_screenshot_from_2018-09-13_13-36-45.png)

![](http://uupload.ir/files/agiu_screenshot_from_2018-09-13_13-36-03.png)

## Using

Step 1. Add it in your root build.gradle at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```gradle
dependencies {
	        implementation 'com.github.abbasalim:FilePickerDialog-master:1.0'
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
## Other Options:
* open Specific Path 
* show Hidden file
* file Filter
## Example FileFilter And Show Hidden Files
```
ArrayList<FileFilter> ff = new ArrayList<>();
                ff.add(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory() || file.getName().endsWith(".wave");
                    }
                });
                CompositeFilter cf = new CompositeFilter(ff);
                MainActivity context = (MainActivity) getActivity();
                PickerDialog.FilePicker(context,null,true,cf).onFileSelect(new PickerDialog.FileClickListener() {
                    @Override
                    public void onFileClicked(File clickedFile) {
                       
                    }
                });
```         
Runtime permissions:

You should handle runtime permissions in activity, from what you called Material File Picker.

WaveAcc.ir

Esfandune.ir

