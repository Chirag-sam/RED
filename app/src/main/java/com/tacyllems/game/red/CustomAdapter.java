package com.tacyllems.game.red;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by krsnv on 10/28/2017.
 */

public class CustomAdapter extends BaseAdapter {
  private Context context; //context
  private String[] items; //data source of the list adapter

  //public constructor
  public CustomAdapter(Context context, String[] items) {
    this.context = context;
    this.items = items;
  }

  @Override public int getCount() {
    return items.length; //returns total of items in the list
  }

  @Override public Object getItem(int position) {
    return items[position]; //returns list item at the specified position
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    // inflate the layout for each list row
    if (convertView == null) {
      convertView = LayoutInflater.from(context).
          inflate(R.layout.spinner_text, parent, false);
    }

    // get the TextView for item name and item description
    TextView textViewItemName = convertView.findViewById(R.id.textView);

    //sets the text for item name and item description from the current item object
    textViewItemName.setText(items[position]);

    // returns the view for the current row
    return convertView;
  }
}
