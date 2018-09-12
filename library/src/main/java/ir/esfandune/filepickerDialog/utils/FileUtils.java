package ir.esfandune.filepickerDialog.utils;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class FileUtils {
    public static List<File> getFileListByDirPath(String path, FileFilter filter,boolean showHidden) {
        File directory = new File(path);
        File[] files = directory.listFiles(filter);

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result =new ArrayList<>();
        for (File f:files) {
            if (showHidden ||  !f.getName().startsWith("."))
            result.add(f);
        }
//        List<File> result = Arrays.asList(files); چون add نداره و سایز ثابته
        Collections.sort(result, new FileComparator());

        return result;
    }

    public static String cutLastSegmentOfPath(String path) {
        if (path.length() - path.replace("/", "").length() <= 1)
            return "/";
        String newPath = path.substring(0, path.lastIndexOf("/"));
        // We don't need to list the content of /storage/emulated
        if (newPath.equals("/storage/emulated"))
            newPath = "/storage";
        return newPath;
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}