package de.webcuisine.leon.juan.marsvision;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import de.webcuisine.leon.juan.marsvision.R;

public class MainActivity extends ListActivity {

	private ProgressDialog progressDialog;

	/**
	 * URL from NASA to get JSON nodes
	 */
	
	private static String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY";

	/**
	 * JSON nodes:
	 *
	 * "photos" as initial node with depth 0
	 * "earth_date" and "img_src" with depth 1
	 * "camera" with depth 2
	 * "full_name" with depth 3
	 * */

	private static final String PHOTOS = "photos";
	private static final String ID = "id";
	private static final String DATE = "earth_date";
	private static final String NAME = "full_name";
	private static final String CAM = "camera";
	private static final String IMG = "img_src";

	/**
	 * JSON Database
	 */ 

	JSONArray datas = null;

	/**
	 * Rover HashMap
	 */ 
	ArrayList<HashMap<String, String>> roverList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		roverList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();

		/**
		 * Method onClick
		 */ 
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
		/**
		 * Image for Detail View Nodes (transferring)
		 */ 

				String image = ((TextView) view.findViewById(R.id.img)).getText().toString();

				Intent in = new Intent(getApplicationContext(), DetailViewActivity.class);
				in.putExtra(IMG, image);
				startActivity(in);

			}
		});
		
		/**
		 * AsyncTask to get JSON Nodes
		 */ 
		new GetMars().execute();
	}

	/**
	 * class AsyncTask for making HTTP call 
	 * */
	private class GetMars extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Waiting Mars...");
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			
			/**
			 * Creating Service Handler Class Instance
			 * 
			 * */
			
			ServiceCallHandler sch = new ServiceCallHandler();

			/**
			 * URL Request
			 * 
			 * */
			
			String jsonStr = sch.makeServiceCall(url, ServiceCallHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					/**
					 * Getting JSON Array Node
					 * 
					 * */
					
					datas = jsonObj.getJSONArray(PHOTOS);

					/**
					 * Loop for extract data
					 * 
					 * */
					
					for (int i = 0; i < datas.length(); i++) {
						JSONObject d = datas.getJSONObject(i);


						String id = d.getString(ID);
						String date = d.getString(DATE);
						String img = d.getString(IMG);
						JSONObject camera = d.getJSONObject(CAM);
						String name = camera.getString(NAME);
						
			
						/**
						 * Getting/Adding ListView Elements
						 * 
						 * */
						
						HashMap<String, String> element = new HashMap<String, String>();
						
						element.put(ID, id);
						element.put(NAME, name);
						element.put(IMG, img);
						element.put(DATE, date);

						roverList.add(element);
						
						/**
						 * Getting DetailView Element
						 * 
						 * */
						
						Intent in = new Intent(getApplicationContext(), DetailViewActivity.class);

						in.putExtra(IMG, img);
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceCallHandler", "ERROR: No data from URL");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (progressDialog.isShowing())
				progressDialog.dismiss();
			
			/**
			 * Updating parsed JSON data into ListView
			 * */
		


			/**
			 * Class OddCellAdapter
			 * 
			 * Alternative use for 'Simple Adapter': For cells with an odd index
			 * use a different background color
			 * */

			OddCellAdapter adapter = new OddCellAdapter(MainActivity.this,
					roverList, R.layout.list_item, new String[] { DATE, NAME,
							IMG }, new int[] { R.id.date, R.id.name, R.id.img });

			setListAdapter(adapter);
		}

	}
}
