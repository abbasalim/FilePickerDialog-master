package ir.esfandune.filepickerDialog.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.nbsp.materialfilepicker.R;

import java.io.File;

import ir.esfandune.filepickerDialog.filter.CompositeFilter;
import ir.esfandune.filepickerDialog.utils.FileUtils;
import ir.esfandune.filepickerDialog.widget.EmptyRecyclerView;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class DirectoryFragment extends DialogFragment {


    public static final String ARG_FILE_PATH = "arg_file_path";
    public static final String ARG_FILTER = "arg_filter";
    public static final String ARG_SHOW_HIDDEN = "arg_showhiddenFilew";
    public static final String GO_BACK_ITEM_PATH = "GOBACK";
    public static final String ARG_FOLDER_PICKER = "folderChooser";
    private View mEmptyView, slctFolder;
    private String mPath;
    private boolean showHidden;
    private CompositeFilter mFilter;
    private EmptyRecyclerView rc;
    private DirectoryAdapter rcAdapter;
    private FileClickListener OnFileClicked;
    private FolderClickListener OnFolderClicked;

    public void setInterFace(FileClickListener f) {
        OnFileClicked = f;
    }

    public void setInterFace(FolderClickListener f) {
        OnFolderClicked = f;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        OnFileClicked = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directory, container, false);
        rc = view.findViewById(R.id.directory_recycler_view);
        mEmptyView = view.findViewById(R.id.directory_empty_view);
        slctFolder = view.findViewById(R.id.slctFolder);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initArgs();
        initFilesList();
        slctFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnFolderClicked != null)
                    OnFolderClicked.onFolderClicked(mPath);
            }
        });
    }

    private void initFilesList() {
        rcAdapter = new DirectoryAdapter(getActivity(), FileUtils.getFileListByDirPath(mPath, mFilter,showHidden), !mPath.equals("/storage"));
        rcAdapter.setOnItemClickListener(new DirectoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                File clickedFile = rcAdapter.getModel(position);
                boolean gobackFile = clickedFile.getPath().equals(GO_BACK_ITEM_PATH);
                if (clickedFile.isDirectory()) {
                    mPath = clickedFile.getPath();
                    initFilesList();
                } else if (gobackFile) {
                    mPath = mPath.substring(0, mPath.lastIndexOf(File.separator));
                    initFilesList();
                    Log.d("backPaht", mPath);
                } else if (OnFileClicked != null) {
                    OnFileClicked.onFileClicked(clickedFile);
                }
            }
        });

        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(rcAdapter);
        rc.setEmptyView(mEmptyView);
        runLayoutAnimation(rc);

    }
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_spcf);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @SuppressWarnings("unchecked")
    private void initArgs() {
        if (getArguments().getString(ARG_FILE_PATH) != null) {
            mPath = getArguments().getString(ARG_FILE_PATH);
        }
        mFilter = (CompositeFilter) getArguments().getSerializable(ARG_FILTER);
        showHidden =  getArguments().getBoolean(ARG_SHOW_HIDDEN);
        slctFolder.setVisibility(getArguments().getBoolean(ARG_FOLDER_PICKER) ? View.VISIBLE : View.GONE);

    }

    public interface FileClickListener {
        void onFileClicked(File clickedFile);
    }

    public interface FolderClickListener {
        void onFolderClicked(String FolderPath);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow()!=null) {
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }
    }

}
