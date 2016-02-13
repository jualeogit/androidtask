package de.webcuisine.leon.juan.marsvision;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class DetailViewActivity extends Activity {

	/**
	* JSON nodes of ListView
	*/
	private static final String FULLNAME = "fullname";
	private static final String DATE = "date";
	private static final String IMG = "img";

	/**
	* Image for DetailView (image2 as probe)
	*/
	private String imagen = null;
	//private String imagen2 = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/ncam/NLB_486264973EDR_S0481570NCAM00546M_.JPG";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item2);
		
	/**
	* Retrieve values from MainActivity using Intent
	* Only Image JSON value is necessary	
	*/
		
		Intent in = getIntent();
		imagen = in.getStringExtra(IMG);
		

	/**
	* View the image as Webview using 'loadUrl' method
	*/

		WebView lblImg = (WebView) findViewById(R.id.img);
		lblImg.loadUrl(imagen);
	}
}
