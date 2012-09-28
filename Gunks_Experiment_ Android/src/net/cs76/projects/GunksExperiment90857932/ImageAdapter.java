/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
package net.cs76.projects.GunksExperiment90857932;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.lang.reflect.Field;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter 
{
	//a list of resource IDs for the images we want to display
	private Integer[] images;

	//a context so we can later create a view within it
	private Context myContext;
	
	//store a cache of resized bitmaps
	//Note: we're not managing the cache size to ensure it doesn't 
	//exceed any maximum memory usage requirements
	private Bitmap[] cache;
	
	private Field[] resourceList;
	private String[] iconImgList;

	// Constructor
	public ImageAdapter(Context c) 
	{
		myContext = c;
		resourceList = R.drawable.class.getFields();
		iconImgList = new String[resourceList.length];
		int count = 0, index = 0, j = resourceList.length;

		for(int i=0; i < j; i++)
		{
			if(resourceList[i].getName().startsWith("icon_"))
			{
				iconImgList[i] = resourceList[i].getName();
				count++;
			}
		}

		//Reserve the memory for an array of integers with length 'count' and initialize our cache.
		images = new Integer[count];
		cache = new Bitmap[count];

		//Get the values of each of those fields into the images array.
		try 
		{	
			for(int i=0; i < j; i++)
			{
				if(resourceList[i].getName().startsWith("icon_"))
				{
					images[index++] = resourceList[i].getInt(null);
				}
			}
		} 
		catch(IllegalArgumentException e){}
		catch(IllegalAccessException e){}
		catch(Exception e) {}
	}

	@Override
	public int getCount() 
	{
		return images.length;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}
	
	public String getImageViewName(int position)
	{
        int i = myContext.getResources().getIdentifier(iconImgList[position + 1], "string", myContext.getPackageName());	            
        return this.myContext.getString(i);
	}

	@Override
	// return the resource ID of the item at the current position
	public long getItemId(int position) 
	{
		return images[position];
	}

	//create a new ImageView when requested
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	     View MyView = convertView;
	     
		try
		{
		     if ( convertView == null )
	         {
	            //Inflate the layout
	       	  	LayoutInflater inflator = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            MyView = inflator.inflate(R.layout.grid_view_item, null);
	            
	            TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
	            
	            int i = myContext.getResources().getIdentifier(iconImgList[position + 1], "string", myContext.getPackageName());	            
	            tv.setText(this.myContext.getString(i));
	            
	            //See if we've stored a resized thumb in cache
	    		if(cache[position] == null)
	    		{
	    			//Create a new Bitmap that stores a resized version of the image we want to display. 
	    			Bitmap thumb = BitmapFactory.decodeResource(myContext.getResources(), images[position]);
	
	    			//Store the resized thumb in a cache so we don't have to re-generate it
	    			cache[position] = thumb;
	    		}
	 
	            ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);
	            iv.setImageBitmap(cache[position]);
	            iv.setTag(this.myContext.getString(i));
	         }
		}
		catch(Exception e) {}

	    return MyView; 
	}
}