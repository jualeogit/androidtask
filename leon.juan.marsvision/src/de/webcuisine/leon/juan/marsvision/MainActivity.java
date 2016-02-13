package de.webcuisine.leon.juan.marsvision;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import de.webcuisine.leon.juan.marsvision.R;

public class MainActivity extends ListActivity {

	private ProgressDialog pDialog;
	
	private static String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY";

	private static final String PHOTOS = "photos";
	private static final String ID = "id";
	private static final String DATE = "earth_date";
	private static final String NAME = "full_name";
	private static final String CAM = "camera";
	private static final String IMG = "img_src";

	JSONArray datas = null;
	ArrayList<HashMap<String, String>> contactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		contactList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String image = ((TextView) view.findViewById(R.id.img)).getText().toString();
						
				Intent in = new Intent(getApplicationContext(),
						DetailViewActivity.class);
				in.putExtra(IMG, image);
				startActivity(in);

			}
		});

	}



	
}
