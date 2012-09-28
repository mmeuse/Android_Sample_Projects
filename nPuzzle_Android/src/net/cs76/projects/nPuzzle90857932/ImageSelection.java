/* Matthew Meuse
 * matt.meuse@gmail.com
 * HUID: 90857932
 * CS76 Building Mobile Applications
 * Harvard Extension School
 * n-Puzzle Android Application 
 * 
 * PERFORMED ALL OF MY TESTING ON EASY mode.
 * */
package net.cs76.projects.nPuzzle90857932;
 
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

//The main activity of the application that allows the user to select an image from resources
public class ImageSelection extends Activity implements OnItemClickListener
{
	private SharedPreferences preferences;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	preferences = getPreferences(MODE_PRIVATE);
    	    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Retrieve the save image and if not available, set the default id to a non realistic value
        Long imageSelected = preferences.getLong("difficultyColumns",-999);
        
        //If no image was saved, launch the grid view selected. Otherwise, launch the game
        if(imageSelected == -999)
        {        
	        //Using an image adapter, create a grid view of thumbnails that point to image resources
	        GridView grid = (GridView) findViewById(R.id.gridview);
	        grid.setAdapter(new ImageAdapter(this));
	        grid.setOnItemClickListener(this);
        }
        else
        {
        	LaunchGame(imageSelected);
        }
    }
    
    //Launches the Game and attempts to retrieve the stored preferences
    //Stored preferences retrieval is not working. Something I will need to debug
    public void LaunchGame(long imageResourceId)
    {
    	 String difficultyColumns = preferences.getString("difficultyColumns","4");
         String difficultyRows = preferences.getString("difficultyRows","4");	
         
     	Intent i = new Intent(this, GamePlay.class);
     	i.putExtra("imageToDisplay", imageResourceId);
     	i.putExtra("difficultyColumns",difficultyColumns);
     	i.putExtra("difficultyRows",difficultyRows);
     	startActivity(i);
    }
    
    //When a thumbnail is clicked, load it into the Gameplay Activity
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
    {
       LaunchGame(id);
    }
}