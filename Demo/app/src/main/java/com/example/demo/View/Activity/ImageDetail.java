package com.example.demo.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo.DataModel.Db.GalleryDbManager;
import com.example.demo.Interface.MediaInjection;
import com.example.demo.Model.Data;
import com.example.demo.R;
import com.example.demo.ViewModel.MediaViewModel;

public class ImageDetail extends AppCompatActivity implements View.OnClickListener {

    private String imageId;
    private MediaViewModel mMediaViewModel;
    private Data imageData;
    private ImageView image,ivBack;
    private TextView tvName,tvSubmit;
    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        initView();
        setUpViewModel();
        getBundleData();
        setClickEvent();
    }

    private void setClickEvent() {
        ivBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    private void initView() {
        image =(ImageView) findViewById(R.id.image);
        ivBack =(ImageView) findViewById(R.id.iv_back);
        tvName =(TextView) findViewById(R.id.tv_name);
        tvSubmit =(TextView) findViewById(R.id.submit);
        etComment =(EditText) findViewById(R.id.et_comment);
    }

    private void setUpViewModel() {
        mMediaViewModel =new  ViewModelProvider(this,MediaInjection.INSTANCE.provideViewModelFactory()).get(MediaViewModel.class);
    }

    private void getBundleData() {
        imageId = getIntent().getExtras().getString("id");
        Log.d("imageiddddd",imageId);
        getImageDataFromDb();
    }

    private void getImageDataFromDb() {
       imageData =  mMediaViewModel.getImageDataFromDb(imageId);
       tvName.setText(imageData.getTitle());
       if (imageData.getDescription() != null){
           etComment.setText(imageData.getDescription().toString());
       }
        Glide.with(this)
                .load(imageData.getLink())
                .thumbnail(0.2f)
                .into(image);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                 onBackPressed();
                break;
            case R.id.submit :
               handleSubmit();
                break;

        }
    }

    private void handleSubmit() {
        if (!etComment.getText().toString().trim().equals("")){
            mMediaViewModel.updateComment(imageId,etComment.getText().toString().trim());
            Toast.makeText(this,"Comment updated successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Please enter comment.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
