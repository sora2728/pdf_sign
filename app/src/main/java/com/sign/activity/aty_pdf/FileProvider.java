package com.sign.activity.aty_pdf;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProvider implements AbstructProvider {
    private Context context;

    public FileProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<File> getList(String str) {
        List<File> list = null;
        if (context != null) {
            String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.DATA,
                    MediaStore.Files.FileColumns.SIZE
            };
            Cursor cursor = context.getContentResolver().query(
                    Uri.parse("content://media/external/file"),
//                    Uri.parse("content://media/external/file"),
                    projection,
                    MediaStore.Files.FileColumns.DATA + " like ?",
                    new String[]{str},
                    null);
            if (cursor != null) {
                list = new ArrayList<File>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));//ID
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));//路径
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));//大小
                    File file = new File(path);
                    if (size > 10) {
                        list.add(file);
                    }
                }
                cursor.close();
            }
        }
        return list;
    }
}
