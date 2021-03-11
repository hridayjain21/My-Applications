package com.example.dailyneedsapp.model;

public class item {
    private int Id;
    private String Item_name;
    private String Item_quantity;
    private String Item_type;

    public item() {
    }



    public item(int id, String item_name, String item_quantity, String item_type) {
        Id = id ;
        Item_name = item_name;
        Item_quantity = item_quantity;
        Item_type = item_type;

    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }



    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

    public String getItem_quantity() {
        return Item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        Item_quantity = item_quantity;
    }

    public String getItem_type() {
        return Item_type;
    }

    public void setItem_type(String item_type) {
        Item_type = item_type;
    }
}
