package com.paul.mykroscope;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public class PostingActivity extends AppCompatActivity {
    private static final String TAG = "PostingActivity";
    private static final int PICK_IMAGE = 1;
    ImageView imageView;
    RequestParams params = new RequestParams();
    String imgPath;
    private static int RESULT_LOAD_IMG = 1;

    ProgressDialog nDialog;

    String mPath;

    HurlStack connection;

    Spinner categorySpinner;
    Spinner cellTypeSpinner;

    int cellTypeArray;

    EditText descriptionText;
    EditText cellNameText;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Mykroscope/";

        pickImageFromGallery();


        imageView = (ImageView) findViewById(R.id.cellView);


        //Spinner
        categorySpinner = (Spinner) findViewById(R.id.category);
        cellTypeSpinner = (Spinner) findViewById(R.id.cellType);

        String[] category = new String[]{
                "Human cell",
                "Plant cell",
                "Animal cell",
                "Others"
        };




        //EditText
        descriptionText = (EditText) findViewById(R.id.description);
        cellNameText = (EditText) findViewById(R.id.cell);

        //Share Button
        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                if (EasyPermissions.hasPermissions(PostingActivity.this, galleryPermissions)) {
                    uploadImage(v);
                } else {
                    EasyPermissions.requestPermissions(PostingActivity.this, "Access for storage",
                            101, galleryPermissions);
                }
            }
        });

// Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,category
        );




// Specify the layout to use when the list of choices appears

        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




// Apply the adapter to the spinner

        categorySpinner.setAdapter(adapterCategory);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        setCellType(position);
//                        showToast(String.valueOf(position));
                        break;
                    case 1:
                        setCellType(position);
                        break;
                    case 2:
                        setCellType(position);
                        break;
                    case 3:
                        setCellType(position);
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });


//        cellTypeArray = R.array.cellTypeHuman;
//        ArrayAdapter<CharSequence> adapterCellType = ArrayAdapter.createFromResource(PostingActivity.this,
//                cellTypeArray, android.R.layout.simple_spinner_item);
//        adapterCellType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        cellTypeSpinner.setAdapter(adapterCellType);


    }

    private void showToast(final String text) {
        final Activity activity = this;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // When Upload button is clicked
    public void uploadImage(View v) {
        nDialog = new ProgressDialog(PostingActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
        // When Image is selected from Gallery
        if (imgPath != null && !imgPath.isEmpty()) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            nDialog.hide();
                            showToast("Successfully Posted");
                            Log.d(TAG,response);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            nDialog.hide();
                            showToast("Error" + error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    return params;
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(postRequest);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "You must select image from gallery before you try to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

    public String getBase64FromFile(String path) {
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        byte[] baat = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            baat = baos.toByteArray();
            encodeString = Base64.encodeToString(baat, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    public void pickImageFromGallery() {
        Log.d(TAG, "Testing buttons");
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(Uri.parse(mPath),"image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(pickIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            try {
                // When an Image is picked
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        } else {
            finish();
        }
    }
    public void setCellType(int pos){
        switch(pos){
            case 0:
                cellTypeArray = R.array.cellTypeHuman;
                break;
            case 1:
                cellTypeArray = R.array.cellTypePlant;
                break;
            case 2:
                cellTypeArray = R.array.cellTypeAnimal;
                break;
            case 3:
                cellTypeArray = R.array.cellTypeOthers;
                break;
        }
        ArrayAdapter<CharSequence> adapterCellType = ArrayAdapter.createFromResource(PostingActivity.this,
                cellTypeArray, android.R.layout.simple_spinner_item);
        adapterCellType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cellTypeSpinner.setAdapter(adapterCellType);
    }


}
