package net.cs76.projects.GunksExperiment90857932;
/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SearchRoutes extends ListActivity 
{
    private ClimbingRouteDBAdapter mDbHelper;
    private int SearchContext = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_list);
        Bundle extras = getIntent().getExtras();  
	    SearchContext = (int)extras.getInt("searchContext");	
        
        mDbHelper = new ClimbingRouteDBAdapter(this);
        mDbHelper.open();
        
        fillData();
    }

    //Populate the ListView with Climbs
    private void fillData() 
    {
    	Cursor climbCursor = null;
    	 
    	switch(SearchContext)
    	{
    	case 0:
    		 climbCursor = mDbHelper.fetchAllClimbsAlphabetically();
    		break;
    	case 1:
    		 climbCursor = mDbHelper.fetchAllClimbsByDifficulty();
    		break;
    	case 2:
    		 climbCursor = mDbHelper.fetchAllClimbsFromWishList();
    		break;
    	case 3:
    		 climbCursor = mDbHelper.fetchAllClimbsFromTickList();
    		break;
    	default:
    		 climbCursor = mDbHelper.fetchAllClimbsAlphabetically();
    		break;	
    	}
       
        startManagingCursor(climbCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{ClimbingRouteDBAdapter.KEY_DIFFICULTY,ClimbingRouteDBAdapter.KEY_ROUTENAME};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.routedifficulty,R.id.routename};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter climbs = new SimpleCursorAdapter(this, R.layout.notes_row, climbCursor, from, to);
        setListAdapter(climbs);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) 
    {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, ClimbingRoute.class);
        i.putExtra(ClimbingRouteDBAdapter.KEY_ROWID, id);
        startActivity(i);
    }
}
