package com.example.dailyneedsapp.Adappte;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyneedsapp.R;
import com.example.dailyneedsapp.data.DataBaseHandler;
import com.example.dailyneedsapp.model.item;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Handler;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<item> itemList;
    private Button yesconf,noconf;
    private TextView deleteconf;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;
    private EditText entername,enterquantity,entertype;
    private Button savebutton;

    public RecyclerViewAdapter(Context context, List<item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        item item = itemList.get(position);
        viewHolder.itemname.setText(MessageFormat.format("Name : {0}", item.getItem_name()));
        viewHolder.itemquan.setText(MessageFormat.format("Quantity : {0}", item.getItem_quantity()));
        viewHolder.itemtype.setText(MessageFormat.format("Type : {0}", item.getItem_type()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemname;
        private TextView itemquan;
        private TextView itemtype;
        private Button deletebutton;
        private Button editbutton;


        public ViewHolder(@NonNull View itemView , Context ctx) {
            super(itemView);
            context = ctx;
            itemname = itemView.findViewById(R.id.item_name);
            itemquan = itemView.findViewById(R.id.item_quantity);
            itemtype = itemView.findViewById(R.id.item_type);
            deletebutton = itemView.findViewById(R.id.delete_button);
            editbutton = itemView.findViewById(R.id.edit_button);

            deletebutton.setOnClickListener(this);
            editbutton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            item item = itemList.get(position);
            switch (view.getId()){
                case R.id.delete_button:
                    deleteitem(item);
                    break;
                case R.id.edit_button:
                    updateitem(item);
                    break;
            }
        }
        private void deleteitem(final item item) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.delete_confirmation,null);
            deleteconf = view.findViewById(R.id.delete_conf);
            yesconf = view.findViewById(R.id.conf_yes);
            noconf = view.findViewById(R.id.conf_no);
            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            noconf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            yesconf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHandler db = new DataBaseHandler(context);
                    db.deletecontact(item);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();
                }
            });



    }
        private void updateitem(final item newitem) {
            item item = itemList.get(getAdapterPosition());
            builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.popup,null);
            entername = view.findViewById(R.id.item_name);
            enterquantity = view.findViewById(R.id.item_quantity);
            entertype = view.findViewById(R.id.item_type);
            savebutton = view.findViewById(R.id.saveButton);
            entername.setText(item.getItem_name());
            enterquantity.setText(item.getItem_quantity());
            entertype.setText(item.getItem_type());
            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();
            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHandler db = new DataBaseHandler(context);
                    newitem.setItem_name(entername.getText().toString().trim());
                    newitem.setItem_quantity(enterquantity.getText().toString().trim());
                    newitem.setItem_type(entertype.getText().toString().trim());
                    db.updateitem(newitem);
                    notifyItemChanged(getAdapterPosition(),newitem);
                    alertDialog.dismiss();
                }
            });




        }




    }


}

