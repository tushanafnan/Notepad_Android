package com.example.notepaddemp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepaddemp.utils.DBUtils;

public class RecordActivity extends AppCompatActivity {

    private ImageView note_back, note_save, note_delete;
    private EditText content;
    private MyHelper myHelper;
    private String id;
    private TextView noteTitle;
    private EditText getContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_back = findViewById(R.id.note_back);
        note_save = findViewById(R.id.note_save);
        note_delete = findViewById(R.id.delete);
        content = findViewById(R.id.note_content);
        noteTitle = (TextView)findViewById(R.id.note_name);
        myHelper = new MyHelper(this);
        initData();
        note_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        note_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });

        note_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteContent = content.getText().toString().trim();
                if (id != null) {//user wants to update note
                    if (noteContent.length() > 0) {
                        if (myHelper.updateData(id, noteContent, DBUtils.getDate())) {
                            showToast("update successfully!");
                            setResult(2);
                            finish();
                        } else {
                            showToast("update failed");
                        }
                    } else {
                        showToast("The input cannot be empty!");
                    }
                } else {//insert a new note
                    if (noteContent.length() > 0) {
                        if (myHelper.insertData(noteContent, DBUtils.getDate())) {
                            showToast("insert successfully!");
                            setResult(2);
                            finish();
                        } else {
                            showToast("insert failed");
                        }
                    } else {
                        showToast("The input cannot be empty!");
                    }
                }
            }
        });
    }

    public void showToast(String message) {
        Toast.makeText(RecordActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    public void initData(){
        Intent intent = getIntent();
        if(intent !=null){
            id = intent.getStringExtra("id");
            if(id !=null){
                noteTitle.setText("Update your note");
                content.setText(intent.getStringExtra("content"));

            }
        }
        noteTitle.setText("Add a note");
    }
}