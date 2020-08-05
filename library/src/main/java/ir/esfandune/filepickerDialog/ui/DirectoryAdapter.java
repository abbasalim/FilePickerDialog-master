package ir.esfandune.filepickerDialog.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nbsp.materialfilepicker.R;

import java.io.File;
import java.util.List;

import ir.esfandune.filepickerDialog.utils.FileTypeUtils;

import static ir.esfandune.filepickerDialog.utils.FileTypeUtils.FileType.IMAGE;

/**
 * Created by Dimorinny on 24.10.15.
 */

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryViewHolder> {


    private List<File> mFiles;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public DirectoryAdapter(Context context, List<File> files, boolean addBackBtn) {
        mContext = context;
        mFiles = files;
        if (addBackBtn) {
            File f = new File(PickerDialog.GO_BACK_ITEM_PATH);
            mFiles.add(0, f);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public DirectoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);

        return new DirectoryViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(DirectoryViewHolder holder, int position) {
        File currentFile = mFiles.get(position);

        if (currentFile.getPath().equals(PickerDialog.GO_BACK_ITEM_PATH)) {
            holder.mFileImage.setImageResource(R.drawable.ic_back);
            holder.mFileSubtitle.setText("");
            holder.mFileTitle.setText(R.string.back);
        } else {
            FileTypeUtils.FileType fileType = FileTypeUtils.getFileType(currentFile);
            holder.mFileSubtitle.setText(fileType.getDescription());
            holder.mFileTitle.setText(currentFile.getName());

            if (fileType == IMAGE)
                Glide.with(holder.itemView).load(currentFile).apply(new RequestOptions().placeholder(fileType.getIcon())).into(holder.mFileImage);
            else
                holder.mFileImage.setImageResource(fileType.getIcon());
        }
    }

    public boolean isEmpty() {
        return mFiles.size() == 0 || (mFiles.size() == 1 && mFiles.get(0).getPath().equals(PickerDialog.GO_BACK_ITEM_PATH));
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public File getModel(int index) {
        return mFiles.get(index);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class DirectoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView mFileImage;
        private TextView mFileTitle;
        private TextView mFileSubtitle;


        public DirectoryViewHolder(View itemView, final OnItemClickListener clickListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, getAdapterPosition());
                }
            });

            mFileImage = itemView.findViewById(R.id.item_file_image);
            mFileTitle = itemView.findViewById(R.id.item_file_title);
            mFileTitle.setSelected(true);
            mFileSubtitle = itemView.findViewById(R.id.item_file_subtitle);
        }
    }
}