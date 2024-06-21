package com.example.notepaddemp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView add;
    private MyHelper myHelper;
    private ListView listView;
    private List<Note> list;//to save notes
    private NotepadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (ImageView)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivityForResult(intent,1);
            }
        });
        initData();
    }
    public void initData(){
        myHelper = new MyHelper(this);
        listView = (ListView)findViewById(R.id.listview);
        showQueryData();
    }
    public void showQueryData(){
        if(list != null){
            list.clear();
        }
        list =myHelper.queryData();//query all the notes from your database
        adapter = new NotepadAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int which, long l) {
                final AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Note note = list.get(which);
                                if(myHelper.deleteData(note.getId())){
                                    list.remove(which);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this,"delete successfully" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("No", null);
                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                Note note = list.get(position);
                intent.putExtra("id",note.getId());
                intent.putExtra("content",note.getNotepadContent());
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            showQueryData();//refresh your layout
        }
    }
}