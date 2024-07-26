package com.example.calendar;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtil {
    private final Context context;

    public FileUtil(Context context)
    {
        this.context = context;
    }
    public boolean doesFileExistInInternalStorage(Context context, String fileName) {
        String[] fileList = context.fileList();
        for (String file : fileList) {
            if (file.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public void saveDataToFile(String fileName, String key, String value)
    {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            osw.write(key+"="+value+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(osw != null)
            {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getValueFromFile(String fileName, String key)
    {
        String value = null;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            fis = context.openFileInput(fileName);
            isr = new InputStreamReader(fis);
            reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("=");
                if(parts.length == 2 && parts[0].equals(key))
                {
                    value = parts[1];
                    break;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
    public void showFileContents(String fileName, String key) {
        String fileContents = getValueFromFile(fileName, key);
        if (fileContents != null) {
            Toast.makeText(context, "File Contents: " + fileContents, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "No data found in file.", Toast.LENGTH_LONG).show();
        }
    }
    public void saveImageToFile(Context context, Bitmap bitmap, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Bitmap loadImageFromFile(Context context, String filename) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}