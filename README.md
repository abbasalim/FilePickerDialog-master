# File Picker Dialog
Material file picker Dialog library for Android

![](https://i.imgur.com/mjxs05n.png)

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

Open file picker:

```java
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
```


Runtime permissions:

You should handle runtime permissions in activity, from what you called Material File Picker.

