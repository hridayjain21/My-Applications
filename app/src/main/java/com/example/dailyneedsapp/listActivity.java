package com.example.dailyneedsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dailyneedsapp.Adappte.RecyclerViewAdapter;
import com.example.dailyneedsapp.data.DataBaseHandler;
import com.example.dailyneedsapp.model.item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class listActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerViewAdapter recyclerViewAdapter;
    private List<item> itemList;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText item_name,item_quan,item_type;
    private Button savebutton;
    private DataBaseHandler databasehandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fab = findViewById(R.id.fab);
         recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databasehandler = new DataBaseHandler(this);
        itemList = new ArrayList<>();

        List<item> itemList = databasehandler.getitemList();
        for(item item: itemList){
            Log.d("new", "onCreate: "+item.getItem_name());}

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Createpopup();
            }
        });

    }

    private void Createpopup() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        item_name = view.findViewById(R.id.item_name);
        item_quan = view.findViewById(R.id.item_quantity);
        item_type = view.findViewById(R.id.item_type);
        savebutton = view.findViewById(R.id.saveButton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!item_name.getText().toString().isEmpty() &&
                !item_name.getText().toString().isEmpty() &&
                !item_name.getText().toString().isEmpty()){
                    saveitem(view);
                }else{
                    Snackbar.make(view,"Please fill all queries", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view);
        alertDialog =builder.create();
        alertDialog.show();
    }

    private void saveitem(View view) {
        String itemname = item_name.getText().toString().trim();
        String itemquan = item_quan.getText().toString().trim();
        String itemtype = item_type.getText().toString().trim();

        item item = new item();
        item.setItem_name(itemname);
        item.setItem_quantity(itemquan);
        item.setItem_type(itemtype);
        databasehandler.additem(item);

        Snackbar.make(view,"Item Saved",BaseTransientBottomBar.LENGTH_SHORT);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          alertDialog.dismiss();
                                          startActivity(new Intent(listActivity.this,listActivity.class));
                                      }
                                  },1200);
    }
}
