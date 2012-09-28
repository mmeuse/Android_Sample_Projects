/* Matthew Meuse
 * matt.meuse@gmail.com
 * HUID: 90857932
 * CS76 Building Mobile Applications
 * Harvard Extension School
 * n-Puzzle Android Application 
 * NOTE:  Some code is purposely duplicated with methods in Gameplay.
 * I Would have refactored the code to not copy\paste from GamePlay, but I ran out of time.
 * */
package net.cs76.projects.nPuzzle90857932;
 
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class YouWin extends Activity implements OnClickListener
{
	private int tileWidth = 0;
	private int tileHeight = 0;	
	private ArrayList<Tile> initialTileArray;
	private ArrayList<Bitmap> initialBitmapArray;
	private int resource;
	private int difficultyColumns;
	private int difficultyRows;
	private int numberOfMoves;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    try
	    {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.youwin);
	           
		    Bundle extras = getIntent().getExtras();  
		    resource = extras.getInt("imageToDisplay");	
		    difficultyColumns = extras.getInt("difficultyColumns");
		  	difficultyRows =  extras.getInt("difficultyRows");
		  	initialBitmapArray = CreateBitmapArray();
		  	numberOfMoves = extras.getInt("numberOfMoves");
		  	
		  	 TextView t=(TextView)findViewById(R.id.Congratulations); 
		     t.setText("Congratulations, you won the game in " + numberOfMoves + " moves!");
		       
		     Button button =(Button)findViewById(R.id.startOverButton);  
		     button.setOnClickListener(this);  
		     
		     DisplayInitialBoard();
        }
        catch (OutOfMemoryError e){}
		catch(Exception e){}
	}
	
	//Generates a bitmap array from the passed in resource image.
	public ArrayList<Bitmap> CreateBitmapArray()
	{
		ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
		
		try
		{
			//load large image from resources and scale it to the screen size
	    	Bitmap background = BitmapFactory.decodeResource(this.getResources(), resource);			    

			int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
	    	int screenHeight = this.getResources().getDisplayMetrics().heightPixels;
	    	int originalWidth = background.getWidth();
	    	int originalHeight = background.getHeight();		
	    	float scaleWidth = (float)screenWidth / originalWidth;   
	        float scaleHeight = (float)screenHeight / originalHeight;           
	        float scaleFactor = Math.min(scaleWidth,scaleHeight);   
	        int newScaledWidth = Math.round(originalWidth * scaleFactor);   
	        int newScaledHeight = Math.round(originalHeight * scaleFactor);   

	      	background = Bitmap.createScaledBitmap(background,newScaledWidth, newScaledHeight,true);	

	    	tileWidth = (int)(newScaledWidth/difficultyColumns);
	    	tileHeight = (int)(newScaledHeight/difficultyRows);
	    	
	    	for(int y = 0; y < newScaledHeight; y+=tileHeight)
	    	{
	    		if((y + tileHeight) <= newScaledHeight)
	    		{
	        		for(int x = 0; x < newScaledWidth; x+=tileWidth)
	        		{
	        			if((x + tileWidth) <= newScaledWidth)
	        			{
			        		// create cropped image from loaded image
			                Bitmap cropped = Bitmap.createBitmap(background, x, y, tileWidth, tileHeight);
			                bitmapArray.add(cropped);
	        			}
	        		}    
	    		}
	    	}	
		}
		catch(Exception e){}
    	
    	return bitmapArray;		
	}
	
	//This methods adds the original tiled image to the screen
	public void DisplayInitialBoard()
	{	
		try
		{
		    initialTileArray = GenerateTileCollectionFromInitialBoard(initialBitmapArray);
		    GenerateTable(initialTileArray);
		}
		catch(Exception e){}
	}
	
	//This method generates an array of tiles to display in the layout
	public ArrayList<Tile> GenerateTileCollectionFromInitialBoard(ArrayList<Bitmap> bitmapArray)
	{
		ArrayList<Tile> tileCollection = new ArrayList<Tile>();
		
		try
		{
			//Replace the last image with a blank tile
			bitmapArray.set(bitmapArray.size() - 1, CreateBlankTile(tileWidth,tileHeight));
			
			Tile currentTile;
			int currentBitmapArrayIndex = 0;
			
			for(int row = 0; row < difficultyRows; row++)
			{
				for(int column = 0; column < difficultyColumns; column++)
				{
					currentTile = new Tile();
					currentTile.id = bitmapArray.indexOf(currentBitmapArrayIndex);
					currentTile.xPosition = column;
				    currentTile.yPosition = row;
				    currentTile.bitmap = bitmapArray.get(currentBitmapArrayIndex);
				    currentBitmapArrayIndex++;
				    tileCollection.add(currentTile);
				}		
			}
		}
		catch(Exception e){}

		return tileCollection;
	}
	
	//This method is used to create a generic blank tile
	public Bitmap CreateBlankTile(int tileWidth, int tileHeight)
	{
		Bitmap bitmap = Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.RGB_565);
		
		try
		{
			Canvas canvas = new Canvas(bitmap);
			canvas.drawColor(Color.BLACK);	
		}
		catch(Exception e){}
		
		return bitmap;
	}
	

	//This method populates the layout with the tiled images of the resource image
	public void GenerateTable(ArrayList<Tile> tileArray)
	{
		try
		{
			LinearLayout layout = (LinearLayout) findViewById(R.id.YouWinLayout);
			   
			//Create the layout and an initial row object
	        TableLayout MainLayout = new TableLayout(this);   
	        MainLayout.setShrinkAllColumns(true);
	        TableRow row = new TableRow(this);
			int newColumnIndex = difficultyColumns;
	        
			for (int i = 0; i < tileArray.size(); i++)
			{
				if(i== newColumnIndex)
				{
					newColumnIndex = (newColumnIndex + difficultyColumns);
					
					//Add existing row to TableLaout and create a new row
					MainLayout.addView(row);
					row = new TableRow(this);
				}
				
			    ImageView imageView = new ImageView(this);
			    imageView.setImageBitmap((Bitmap)tileArray.get(i).bitmap);
			    imageView.setBackgroundColor(Color.RED);
			    imageView.setPadding(1,1,1,1);
			    imageView.setTag(tileArray.get(i));
			    row.addView(imageView);
				
				if(i == tileArray.size() - 1)
				{
					MainLayout.addView(row);
				}
			}		
	 
	        layout.addView(MainLayout); 
		}
		catch(Exception e){}
	}
	
	public void onClick(View v)
	{
	 	Intent i = new Intent(this, ImageSelection.class);
    	startActivity(i);	 
	}
}