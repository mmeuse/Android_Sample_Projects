/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
package net.cs76.projects.GunksExperiment90857932;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ClimbingRoute extends Activity implements OnClickListener
{
	private EditText mRouteNameText;
    private EditText mRouteDifficultyText;
    private EditText mRouteVerticalFTText;
    private EditText mRouteDescriptionText;
    private EditText mRoutePitchCountText;
    private Long mRowId;
    private ClimbingRouteDBAdapter mDbHelper;
    private String imageName;
    private String onWishList;
    private String onTickList;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        mDbHelper = new ClimbingRouteDBAdapter(this);
        mDbHelper.open();

        setContentView(R.layout.note_edit);
        
        mRouteNameText = (EditText) findViewById(R.id.climb_name);
        mRouteDifficultyText = (EditText) findViewById(R.id.climb_difficulty);
        mRouteVerticalFTText = (EditText) findViewById(R.id.climb_verticalft);
        mRouteDescriptionText = (EditText) findViewById(R.id.climb_description);
        mRoutePitchCountText = (EditText) findViewById(R.id.climb_pitchcount);
        Button confirmButton = (Button) findViewById(R.id.close_description);

        mRowId = (savedInstanceState == null) ? null : (Long) savedInstanceState.getSerializable(ClimbingRouteDBAdapter.KEY_ROWID);
        
		if (mRowId == null) 
		{
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(ClimbingRouteDBAdapter.KEY_ROWID)
									: null;
		}

		//populate the fields when the climb screen is initialized
		populateFields();
			
		//Dynamically add Wish Tick Buttons
		addActionButtonsForWishTick(onWishList, onTickList);

        confirmButton.setOnClickListener
        (
        		new View.OnClickListener() 
        		{
		            public void onClick(View view)
		            {
		                setResult(RESULT_OK);
		                finish();
		            }
        		}
        );
    }

    private void populateFields() 
    {
        if (mRowId != null) 
        {
            Cursor climb = mDbHelper.fetchClimb(mRowId);
            startManagingCursor(climb);
            
            mRouteNameText.setText(climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_ROUTENAME)));
            mRouteDifficultyText.setText(climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_DIFFICULTY)));
            mRouteVerticalFTText.setText(climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_VERTICALFT)));
            mRouteDescriptionText.setText(climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_DESCRIPTION)));
            mRoutePitchCountText.setText(climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_PITCHCOUNT)));
            
            //Get the image name of the climb from the database and retrieve it from asset resources to display on the view
            imageName = climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_IMAGENAME));
            setClimbingImage(imageName);   
            
            onWishList = climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_ONWISHLIST));
            onTickList = climb.getString(climb.getColumnIndexOrThrow(ClimbingRouteDBAdapter.KEY_ONTICKLIST));
        }
    }
    
    private void setClimbingImage(String imageFileName)
    {
    	 InputStream is = null;
			try 
			{
				is = getResources().getAssets().open(imageFileName);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
         Drawable image = Drawable.createFromResourceStream(getResources(),new TypedValue(),is, null);
         ImageView imageView = (ImageView)findViewById(R.id.climbingImage);
         imageView.setBackgroundDrawable(image);
    }
    
    private void addActionButtonsForWishTick(String onWish, String onTick)
    {
    	 View linearLayout = findViewById(R.id.wishTickButtonLayout);

    	//If a climb is on a wish list, give the user the option to remove it from the list or add it to the tick list
    	if(onWish.equalsIgnoreCase("Yes") && onTick.equalsIgnoreCase("No"))
    	{
    		Button addTickButton = new Button(this);
    		addTickButton.setText("Add to Tick list");
    		addTickButton.setOnClickListener(this);
    		
    		Button addWishButton = new Button(this);
    		addWishButton.setText("Remove from Wish list");
    		addWishButton.setOnClickListener(this);
    		
    		((android.widget.LinearLayout)linearLayout).addView(addTickButton);
    		((android.widget.LinearLayout)linearLayout).addView(addWishButton);
    		
    	}
    	else if(onWish.equalsIgnoreCase("No") && onTick.equalsIgnoreCase("No"))
    	{
    		Button addTickButton = new Button(this);
    		addTickButton.setText("Add to Tick list");
    		addTickButton.setOnClickListener(this);
    		
    		Button addWishButton = new Button(this);
    		addWishButton.setText("Add to Wish list");	
    		addWishButton.setOnClickListener(this);
    		
    		((android.widget.LinearLayout)linearLayout).addView(addTickButton);
    		((android.widget.LinearLayout)linearLayout).addView(addWishButton);
    	}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) 
    {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(ClimbingRouteDBAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() 
    {
        super.onPause();
        saveState();  
		//close the database since it is no longer needed
		mDbHelper.close();
    }

    @Override
    protected void onResume() 
    {
        super.onResume();
        populateFields();
    }

    private void saveState()
    {
    }
    
    //Update the climb only if the user is adding\removing the climb from their tick or wish lists
	public void onClick(View v)
	{
		Button clickedButton = (Button)v;
    	String buttonText = (String)clickedButton.getText();
    	
    	if(buttonText.equalsIgnoreCase("Add to Tick List"))
    	{
    		mDbHelper.updateClimb(mRowId,"No","Yes");
    	}
    	else if(buttonText.equalsIgnoreCase("Add to Wish List"))
    	{
    		mDbHelper.updateClimb(mRowId,"Yes","No");
    	}
    	else if(buttonText.equalsIgnoreCase("Remove from Wish List"))
    	{
    		mDbHelper.updateClimb(mRowId,"No","No");
    	}
	}
}
