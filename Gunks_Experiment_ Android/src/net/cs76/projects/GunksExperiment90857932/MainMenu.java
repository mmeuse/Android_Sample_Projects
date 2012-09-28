/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
package net.cs76.projects.GunksExperiment90857932;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MainMenu extends Activity implements OnItemClickListener
{
	private ImageAdapter imageAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);      
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this);
  	    gridview.setAdapter(imageAdapter);		
  	    gridview.setOnItemClickListener(this);
    }
    
 	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
	 	try
	 	{
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.options_menu, menu);
	 	}
		catch(Exception e)
		{
			return false;
		}
 	
    	return true;
    }
  	
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	try
    	{
    		if (item.getItemId() == R.id.searchroutes) 
	    	{
    			 Intent i = new Intent(this, SearchRoutes.class);
    	    	 i.putExtra("searchContext", 0);
    	         startActivity(i);    			
	    	}
	    	else if (item.getItemId() == R.id.mywishlist) 
	    	{
	    		 Intent i = new Intent(this, SearchRoutes.class);
		    	 i.putExtra("searchContext", 2);
		         startActivity(i);
	    	}
	    	else if (item.getItemId() == R.id.myticklist) 
	    	{
	    		 Intent i = new Intent(this, SearchRoutes.class);
		    	 i.putExtra("searchContext", 3);
		         startActivity(i);
	    	}
    	}
		catch(Exception e)
		{
			return false;
		}
    	
    	return true;
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
    {
	    String imageClicked = imageAdapter.getImageViewName(position);
	    
	    if(imageClicked == getString(R.string.icon_0))
	    {
	    	 Intent i = new Intent(this, SearchRoutes.class);
	    	 i.putExtra("searchContext", 0);
	         startActivity(i);
	    }
	    else  if(imageClicked == getString(R.string.icon_1))
	    {
	    	 Intent i = new Intent(this, SearchRoutes.class);
	    	 i.putExtra("searchContext", 1);
	         startActivity(i);
	    }
	    else if(imageClicked == getString(R.string.icon_2))
	    {
	    	 Intent i = new Intent(this, SearchRoutes.class);
	    	 i.putExtra("searchContext", 2);
	         startActivity(i);
	    }
	    else if(imageClicked == getString(R.string.icon_3))
	    {
	    	 Intent i = new Intent(this, SearchRoutes.class);
	    	 i.putExtra("searchContext", 3);
	         startActivity(i);
	    }
	    else if(imageClicked == getString(R.string.icon_4))
	    {
		    Intent intent = new Intent(Intent.ACTION_VIEW, 
		    	    Uri.parse("http://maps.google.com/maps?f=d&daddr=New Paltz, NY"));
		    	intent.setComponent(new ComponentName("com.google.android.apps.maps","com.google.android.maps.MapsActivity"));
		    	startActivity(intent);
	    }
	    else if(imageClicked == getString(R.string.icon_5))
	    {
	    	 Intent i = new Intent(this, WeatherActivity.class);
	         startActivity(i);
	    } 
	    else if(imageClicked == getString(R.string.icon_6))
	    {
	    	 Intent i = new Intent(this, FoodLodgingActivity.class);
	         startActivity(i);
	    } 
	    else if(imageClicked == getString(R.string.icon_7))
	    {
	    	 Intent i = new Intent(this, GearShopActivity.class);
	         startActivity(i);
	    } 
    }
}