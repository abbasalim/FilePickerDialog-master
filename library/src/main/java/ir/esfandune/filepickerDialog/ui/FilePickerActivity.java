package ir.esfandune.filepickerDialog.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nbsp.materialfilepicker.R;
import ir.esfandune.filepickerDialog.filter.CompositeFilter;
import ir.esfandune.filepickerDialog.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dimorinny on 24.10.15.
 */
  abstract public class FilePickerActivity /*extends AppCompatActivity*/ implements DirectoryFragment.FileClickListener {
    private AlertDialog.Builder alertDialog;
    private View v;
    private Activity act;



    public static final String ARG_START_PATH = "arg_start_path";
    public static final String ARG_CURRENT_PATH = "arg_current_path";

    public static final String ARG_FILTER = "arg_filter";
    public static final String ARG_CLOSEABLE = "arg_closeable";
    public static final String ARG_TITLE = "arg_title";

    public static final String STATE_START_PATH = "state_start_path";
    private static final String STATE_CURRENT_PATH = "state_current_path";

    public static final String RESULT_FILE_PATH = "result_file_path";
    private static final int HANDLE_CLICK_DELAY = 150;

    private Toolbar mToolbar;
    private String mStartPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String mCurrentPath = mStartPath;
    private CharSequence mTitle;

    private Boolean mCloseable;

    private CompositeFilter mFilter;

   public FilePickerActivity(Activity c) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_file_picker);
        act=c;
        alertDialog = new AlertDialog.Builder(c);
        v = c.getLayoutInflater().inflate(R.layout.activity_file_picker, null);
       alertDialog.setView(v);
       alertDialog.create();

//        initArguments(savedInstanceState);
        initViews();
        initToolbar();
        initBackStackState();
        initFragment();
    }

    @SuppressWarnings("unchecked")
   /* private void initArguments(Bundle savedInstanceState) {
        if (getIntent().hasExtra(ARG_FILTER)) {
            Serializable filter = getIntent().getSerializableExtra(ARG_FILTER);

            if (filter instanceof Pattern) {
                ArrayList<FileFilter> filters = new ArrayList<>();
                filters.add(new PatternFilter((Pattern) filter, false));
                mFilter = new CompositeFilter(filters);
            } else {
                mFilter = (CompositeFilter) filter;
            }
        }

        if (savedInstanceState != null) {
            mStartPath = savedInstanceState.getString(STATE_START_PATH);
            mCurrentPath = savedInstanceState.getString(STATE_CURRENT_PATH);
            updateTitle();
        } else {
            if (getIntent().hasExtra(ARG_START_PATH)) {
                mStartPath = getIntent().getStringExtra(ARG_START_PATH);
                mCurrentPath = mStartPath;
            }

            if (getIntent().hasExtra(ARG_CURRENT_PATH)) {
                String currentPath = getIntent().getStringExtra(ARG_CURRENT_PATH);

                if (currentPath.startsWith(mStartPath)) {
                    mCurrentPath = currentPath;
                }
            }
        }

        if (getIntent().hasExtra(ARG_TITLE)) {
            mTitle = getIntent().getCharSequenceExtra(ARG_TITLE);
        }

        if (getIntent().hasExtra(ARG_CLOSEABLE)) {
            mCloseable = getIntent().getBooleanExtra(ARG_CLOSEABLE, true);
        }
    }*/

    private void initToolbar() {
        setSupportActionBar(mToolbar);

        // Show back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Truncate start of path
        try {
            Field f;
            if (TextUtils.isEmpty(mTitle)) {
                f = mToolbar.getClass().getDeclaredField("mTitleTextView");
            } else {
                f = mToolbar.getClass().getDeclaredField("mSubtitleTextView");
            }

            f.setAccessible(true);
            TextView textView = (TextView) f.get(mToolbar);
            textView.setEllipsize(TextUtils.TruncateAt.START);
        } catch (Exception ignored) {
        }

        if (!TextUtils.isEmpty(mTitle)) {
            setTitle(mTitle);
        }
        updateTitle();
    }

    private void initViews() {
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
    }

    private void initFragment() {
//    todo    getFragmentManager().beginTransaction()
//                .replace(R.id.container, DirectoryFragment.getInstance(
//                        mCurrentPath, mFilter))
//                .addToBackStack(null)
//                .commit();
    }

    private void initBackStackState() {
        String pathToAdd = mCurrentPath;
        ArrayList<String> separatedPaths = new ArrayList<>();

        while (!pathToAdd.equals(mStartPath)) {
            pathToAdd = FileUtils.cutLastSegmentOfPath(pathToAdd);
            separatedPaths.add(pathToAdd);
        }

        Collections.reverse(separatedPaths);

        for (String path : separatedPaths) {
            addFragmentToBackStack(path);
        }
    }

    private void updateTitle() {
        if (getSupportActionBar() != null) {
            String titlePath = mCurrentPath.isEmpty() ? "/" : mCurrentPath;
            if (TextUtils.isEmpty(mTitle)) {
                getSupportActionBar().setTitle(titlePath);
            } else {
                getSupportActionBar().setSubtitle(titlePath);
            }
        }
    }




    private void addFragmentToBackStack(String path) {
//        getFragmentManager().beginTransaction()
//                .replace(R.id.container, DirectoryFragment.getInstance(
//     todo                   path, mFilter))
//                .addToBackStack(null)
//                .commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        menu.findItem(R.id.action_close).setVisible(mCloseable);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        if (menuItem.getItemId() == android.R.id.home) {
//            onBackPressed();
//        } else if (menuItem.getItemId() == R.id.action_close) {
//            finish();
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }
//
//    @Override
//    public void onBackPressed() {
//        FragmentManager fm = getFragmentManager();
//
//        if (!mCurrentPath.equals(mStartPath)) {
//            fm.popBackStack();
//            mCurrentPath = FileUtils.cutLastSegmentOfPath(mCurrentPath);
//            updateTitle();
//        } else {
//            setResult(RESULT_CANCELED);
//            finish();
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(STATE_CURRENT_PATH, mCurrentPath);
//        outState.putString(STATE_START_PATH, mStartPath);
//    }

    @Override
    public void onFileClicked(final File clickedFile) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleFileClicked(clickedFile);
            }
        }, HANDLE_CLICK_DELAY);
    }

    private void handleFileClicked(final File clickedFile) {
        if (clickedFile.isDirectory()) {
            mCurrentPath = clickedFile.getPath();
            // If the user wanna go to the emulated directory, he will be taken to the
            // corresponding user emulated folder.
            if (mCurrentPath.equals("/storage/emulated"))
                mCurrentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            addFragmentToBackStack(mCurrentPath);
            updateTitle();
        } else {
            setResultAndFinish(clickedFile.getPath());
        }
    }

    private void setResultAndFinish(String filePath) {
        Intent data = new Intent();
        data.putExtra(RESULT_FILE_PATH, filePath);
//        setResult(RESULT_OK, data);
//        finish();
    }
    abstract public ActionBar getSupportActionBar();
    abstract public void setSupportActionBar(Toolbar mToolbar);
    abstract public void setTitle(CharSequence mTitle);

    public void show() {
        alertDialog.show();
    }
}

