package com.example.collegeproject.Assignment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityCreateAssignmentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class CreateAssignmentActivity extends AppCompatActivity {

    ActivityCreateAssignmentBinding binding;
    DatePickerDialog.OnDateSetListener setListener;
    BottomSheetDialog bottomSheetDialog;
    View sheetView;
    Intent cameraIntent;
    // Launch Camera
     /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                cameraLauncher.launch(cameraIntent);
            }
            else{
                Toast.makeText(CreateAssignmentActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

     */
    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                //binding.UserImage.setImageBitmap(bitmap);

                //BitMap to URI
               /* ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                bytes.toByteArray();
                */


                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "AssignmentImg", null);
                binding.docImage.setImageURI(Uri.parse(path));
                getTitleAndSize(Uri.parse(path));
                binding.docTitle.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
            }
        }
    });
    // Request Camera Permissions
    ActivityResultLauncher<String> requestLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                cameraLauncher.launch(cameraIntent);
            } else
                Toast.makeText(CreateAssignmentActivity.this, "Camera Permission Denied\nTo Allow Permission go to\n Setting < App Manager / App Permission", Toast.LENGTH_SHORT).show();
        }
    });
    // Launch Gallery
    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

        if (result != null) {

            /*  InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(result);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);

       binding.galleryImg.setImageBitmap(selectedImage);

       */
            binding.docImage.setImageURI(result);
            getTitleAndSize(result);
            binding.docTitle.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, result);
                startActivity(intent);
            });

        }

    });
    // Open document Intent
    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
                /*
                DocumentFile documentFile = DocumentFile.fromSingleUri(CreateAssignmentActivity.this, result);
                String fileName = documentFile.getName();
                binding.docTitle.setText(fileName);

                 */

                getTitleAndSize(result);
                binding.docTitle.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(result, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });

            }

        }
    });

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.clear.setOnClickListener(view -> {
            binding.assignmentCard.setVisibility(View.GONE);
        });

        binding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.post.setEnabled(false);
                Snackbar.make(view, "Assignment sent Successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 1000);

            }

        });


        setSupportActionBar(binding.postBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);

        bottomSheetDialog = new BottomSheetDialog(CreateAssignmentActivity.this, R.style.BottomSheetTheme);
        sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.class_bottom_dialog, findViewById(R.id.bottom_sheet_dailog));
        bottomSheetDialog.setContentView(sheetView);


        binding.chooseClass.setOnClickListener(view -> {

            bottomSheetDialog.show();
        });
        RadioGroup csRadioGroup = sheetView.findViewById(R.id.csradiogroup);
        csRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.cs1) {
                    binding.chooseClass.setText("CS 1st Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs2) {
                    binding.chooseClass.setText("CS 2nd Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs3) {
                    binding.chooseClass.setText("CS 3rd Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs4) {
                    binding.chooseClass.setText("CS 4th Year");
                    bottomSheetDialog.dismiss();
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.dobLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dobLayout.setEndIconTintList(ContextCompat.getColorStateList(CreateAssignmentActivity.this, R.color.calender_icon));
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateAssignmentActivity.this
                        , setListener, year, month, day);
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                binding.dob.setText(date);
            }
        };

        binding.cameraImg.setOnClickListener(view -> {
            //    binding.editTextTextMultiLine.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard

           /* ContentValues value = new ContentValues();
            value.put(MediaStore.Images.Media.TITLE,"new image");
            value.put(MediaStore.Images.Media.DESCRIPTION,"From the camera");
            captureImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,captureImageUri);
            */

            cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                if (ActivityCompat.checkSelfPermission(CreateAssignmentActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // ActivityCompat.requestPermissions(CreateAssignmentActivity.this, new String[]{Manifest.permission.CAMERA},1);
                    requestLauncher.launch(Manifest.permission.CAMERA);
                } else {
                    cameraLauncher.launch(cameraIntent);
                }
            } else {
                Toast.makeText(CreateAssignmentActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
            }

        });


        binding.galleryImg.setOnClickListener(view -> {
            binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
            galleryLauncher.launch("image/*");
        });

        binding.documentImg.setOnClickListener(view -> {
            binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
            docLauncher.launch("application/*");
        });

    }

    private void getTitleAndSize(Uri result) {

        Cursor cursor = getContentResolver().query(result, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        cursor.moveToFirst();
        double i = Double.parseDouble(cursor.getString(sizeIndex));
        if (i < 900000) {
            i /= Math.pow(10, 3);
            binding.docSize.setText("Size : " + String.format("%.2f", i) + " KB");
        } else {
            i /= Math.pow(10, 6);
            binding.docSize.setText("Size : " + String.format("%.2f", i) + " MB");

        }
        binding.docTitle.setText(cursor.getString(nameIndex));
        cursor.close();
        binding.assignmentCard.setVisibility(View.VISIBLE);
    }

    private String getMimeType(String url) {
        String parts[] = url.split("\\.");
        String extension = parts[parts.length - 1];
        String type = null;
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @Override
    public boolean onSupportNavigateUp() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

        //AlertDialog.Builder builder = new AlertDialog.Builder(CreateAssignmentActivity.this);
        builder.setTitle("Are You Sure ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
        return super.onSupportNavigateUp();
    }
}