package com.example.asignment.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.asignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class imageDetailFragment extends Fragment {
    private static final int STORAGE_PERMISSION_CODE = 1000;
    boolean anhien = false;
    int width, height;
    ProgressDialog progressBar;
    String link;
    ImageView imageView;
    TextView txtdow, txtShare, txtSet;
    FloatingActionButton fab, fab_dowload, fab_share, fab_set;
    private DisplayMetrics displayMetrics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //màn hình để set wall và dowload...
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        fab = view.findViewById(R.id.fab);
        fab_dowload = view.findViewById(R.id.fab_dowload);
        fab_set = view.findViewById(R.id.fab_setwall);
        fab_share = view.findViewById(R.id.fab_share);
        txtdow = view.findViewById(R.id.txtdl);
        txtShare = view.findViewById(R.id.txtshare);
        txtSet = view.findViewById(R.id.txtset);
        imageView = view.findViewById(R.id.imgdt);
        Bundle bundle = getArguments();
        link = bundle.getString("link");
        Picasso.with(getContext()).load(link).into(imageView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                if (anhien == false) {
                    show();
                    anhien = true;
                } else {
                    hide();
                    anhien = false;
                }

            }
        });
        fab_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWalll();
            }
        });
        fab_dowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permision = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permision, STORAGE_PERMISSION_CODE);
                    } else {
                        new DownloadFileFromURL(getContext()).execute(link);
                    }
                } else {
                    new DownloadFileFromURL(getContext()).execute(link);
                }

            }
        });
        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "share", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setWalll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v1 = getLayoutInflater().inflate(R.layout.dialog_setwall, null);
        Button btnHome = v1.findViewById(R.id.btnHome);
        Button btnLock = v1.findViewById(R.id.btnLock);
        Button btnBoth = v1.findViewById(R.id.btnBoth);
        TextView tvCancel = v1.findViewById(R.id.textView4);
        builder.setView(v1);
        final Dialog dialog = builder.create();
        dialog.show();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetScreenWidthHeight();
                final Bitmap bitmapImg = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                final WallpaperManager wallManager = WallpaperManager.getInstance(getContext());
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        // On Android N and above use the new API to set both the general system wallpaper and
                        // the lock-screen-specific wallpaper
                        wallManager.setBitmap(bitmapImg, null, true, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_LOCK);
                    } else {
                        wallManager.setBitmap(bitmapImg);

                    }
                    Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (IOException ex) {
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetScreenWidthHeight();
                final Bitmap bitmapImg = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                final WallpaperManager wallManager = WallpaperManager.getInstance(getContext());
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        // On Android N and above use the new API to set both the general system wallpaper and
                        // the lock-screen-specific wallpaper
                        wallManager.setBitmap(bitmapImg, null, true, WallpaperManager.FLAG_SYSTEM);
                    } else {
                        wallManager.setBitmap(bitmapImg);

                    }
                    Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (IOException ex) {
                }
            }
        });
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetScreenWidthHeight();
                final Bitmap bitmapImg = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                final WallpaperManager wallManager = WallpaperManager.getInstance(getContext());
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        // On Android N and above use the new API to set both the general system wallpaper and
                        // the lock-screen-specific wallpaper
                        wallManager.setBitmap(bitmapImg, null, true, WallpaperManager.FLAG_LOCK);
                    } else {
                        wallManager.setBitmap(bitmapImg);

                    }
                    Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (IOException ex) {
                }
            }
        });
    }

    private void GetScreenWidthHeight() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            displayMetrics = new DisplayMetrics();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;
        } else {
            displayMetrics = new DisplayMetrics();
            Point size = new Point();
            WindowManager windowManager = getActivity().getWindowManager();
            windowManager.getDefaultDisplay().getSize(size);
            width = size.x;
            height = size.y;
        }
    }



    private void hide() {
        fab_share.hide();
        fab_dowload.hide();
        fab_set.hide();
        txtSet.setVisibility(View.INVISIBLE);
        txtShare.setVisibility(View.INVISIBLE);
        txtdow.setVisibility(View.INVISIBLE);

    }

    private void show() {
        fab_share.show();
        fab_dowload.show();
        fab_set.show();
        txtSet.setVisibility(View.VISIBLE);
        txtShare.setVisibility(View.VISIBLE);
        txtdow.setVisibility(View.VISIBLE);
    }

    class DownloadFileFromURL extends AsyncTask<String, Integer, String> {
        public Context context;

        public DownloadFileFromURL(Context context) {
            this.context = context;
        }

        /**
         * Before starting background thread
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
            progressBar = new ProgressDialog(getContext());
            progressBar.setMessage("Loading... Please wait...");
            progressBar.setIndeterminate(false);
            progressBar.setCancelable(false);
            progressBar.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            String param = f_url[0];
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                URL url = new URL(param);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lenghtOfFile = connection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                // Output stream to write file
                int start = link.length() - 10;
                int end = link.length();

                OutputStream output = new FileOutputStream(root + "/" + link.substring(start, end));

                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        /**
         * After completing background task
         **/
        @Override
        protected void onPostExecute(String file_url) {
            progressBar.dismiss();
            Toast.makeText(context, "Download Success", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new DownloadFileFromURL(getContext()).execute(link);
                } else {
                    Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
