package com.example.dailyneedsapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.dailyneedsapp.data.DataBaseHandler;
import com.example.dailyneedsapp.model.item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText item_name,item_quantity,item_type;
    private DataBaseHandler databasehandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databasehandler = new DataBaseHandler(this);
        bypassActiivity();

       // List<item> itemList = cb.getitemList();
      //  for(item item:itemList){
       //      Log.d("itemnew", "onCreate: "+item.getItem_name()+" , "+item.getItem_quantity()+" , "+item.getItem_type());
       // }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Createpopup();
//             Snackbar.make(view, "MAKE YOYR OWN BUDDY", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

    }

    private void bypassActiivity() {
        if(databasehandler.gettcount()>0){
            startActivity(new Intent(MainActivity.this,listActivity.class));
        }
    }


    private void Createpopup() {
        builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.popup,null);
        saveButton = view.findViewById(R.id.saveButton);
        item_name = view.findViewById(R.id.item_name);
        item_quantity = view.findViewById(R.id.item_quantity);
        item_type = view.findViewById(R.id.item_type);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!item_name.getText().toString().isEmpty() &&
                        !item_quantity.getText().toString().isEmpty() &&
                        !item_type.getText().toString().isEmpty()){
                    saveitem(v);
                }
                else{
                    Snackbar.make(v,"please fill all queries",BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }


    private void saveitem(View view) {
        String itemname = item_name.getText().toString().trim();
        String itemquantity = (item_quantity.getText().toString().trim());
        String itemtype = item_type.getText().toString().trim();

        item item = new item();
        item.setItem_name(itemname);
        item.setItem_quantity(itemquantity);
        item.setItem_type(itemtype);
        databasehandler.additem(item);

        Snackbar.make(view,"Item Saved", BaseTransientBottomBar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(MainActivity.this,listActivity.class));
            }
        },1200);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
