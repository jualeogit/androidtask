package de.webcuisine.leon.juan.marsvision;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

/**
* For cells with an odd index use a different background color.
*/

public class OddCellAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x66294F95, 0xFFCCDEE4 };

	public OddCellAdapter(Context context, List<HashMap<String, String>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}
/**
* Change the color with 'colors' array
*/	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = super.getView(position, convertView, parent);
      int colorPos = position % colors.length;
      view.setBackgroundColor(colors[colorPos]);
      return view;
    }

}
