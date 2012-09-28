/* Matthew Meuse
 * matt.meuse@gmail.com
 * HUID: 90857932
 * CS76 Building Mobile Applications
 * Harvard Extension School
 * n-Puzzle Android Application 
 * */
package net.cs76.projects.nPuzzle90857932;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.os.Bundle;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Collections;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class GamePlay extends Activity implements OnClickListener
{
	private int tileWidth = 0;
	private int tileHeight = 0;	
	private long startTime;
	private ArrayList<Bitmap> initialBitmapArray;
	private ArrayList<Tile> initialTileArray;
	private ArrayList<Tile> currentTileArray;
	private int resource;
	private int difficultyColumns;
	private int difficultyRows;
	private int movesCounter = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    try
	    {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.gameplay);
	           
		    Bundle extras = getIntent().getExtras();  
		    resource = (int)extras.getLong("imageToDisplay");	
		    difficultyColumns = Integer.parseInt(extras.getString("difficultyColumns"));
		  	difficultyRows = Integer.parseInt(extras.getString("difficultyRows"));
		  	
		  	StartGamePlay(difficultyColumns,difficultyRows);
        }
        catch (OutOfMemoryError e){}
	    catch(Exception e) {}
	}
	
	@Override
	 public void onPause() 
	{
    	// call the parent's onPause() method
    	super.onPause();
    	
    	SaveSharedPreferences();
    }
	
	///This method initializes the bitmap array with tiles from the resource image and displays the tiled image to the user
	///After three seconds the tiled image is shuffled and display to the user
	public void StartGamePlay(int columns, int rows)
	{
		try
		{
			movesCounter = 0;
			difficultyColumns = columns;
			difficultyRows = rows;
			
			initialBitmapArray = CreateBitmapArray();
			
			SaveSharedPreferences();
			
		    DisplayInitialBoard();  
	    	StartTimer();
		}
		catch(Exception e) {}
	}
	
	//Save the shared preferences
	//This method does not seem to work currently, but it doesnt crash either. I will need to debug this.
	public void SaveSharedPreferences()
	{
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
 
    	editor.putString("difficultyColumns", String.valueOf(difficultyColumns));
    	editor.putString("difficultyRows", String.valueOf(difficultyRows));
    	editor.putLong("resource", resource);
    	editor.commit();	
	}
	
	//Creates an array of tiles in bitmap format
	public ArrayList<Bitmap> CreateBitmapArray()
	{
    	ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    	
    	try
    	{
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
	
	        //Created a scaled version of the resource bitmap while also maintaining aspect ratio of the image for the smaller screen
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
    	catch(Exception e) {}
    	
    	return bitmapArray;		
	}
	
	//Call this method to display the initial image that is tiled for 3 seconds before being shuffled
	public void DisplayInitialBoard()
	{
		try
		{
		    initialTileArray = GenerateTileCollectionFromInitialBoard(initialBitmapArray);
		    currentTileArray = initialTileArray;
		    GenerateTable(currentTileArray);
		}
		catch(Exception e) {}
	}
	
	//Call this method to shuffle the tile images and display them to the user
	public void DisplayShuffledBoard()
	{
		try
		{
			ArrayList<Bitmap> shuffledArray;
	
			shuffledArray = ShuffleBitmapArray(initialBitmapArray);
			currentTileArray = GenerateTileCollectionFromReversedBoard(shuffledArray);
			
			LinearLayout layout = (LinearLayout) findViewById(R.id.GameplayLayout);
			 
			//Clear the layout and populate the GUI with a shuffled array
			layout.removeAllViews();
			GenerateTable(currentTileArray);
		}
		catch(Exception e) {}
	}
	
	//This method takes the input array of bitmaps and reverses their order
	public ArrayList<Bitmap> ShuffleBitmapArray(ArrayList<Bitmap> bitmapArray)
	{
		ArrayList<Bitmap> reversedArray = bitmapArray;
		try
		{
			Collections.reverse(reversedArray);
		}
		catch(Exception e) {}
		 	 
		return reversedArray;
	}
	
	//This thread is used to time when the automatic tile shuffle occurs. Usually 3 seconds.
	public void StartTimer()
	{
		Timer timer = new Timer();
		startTime = System.currentTimeMillis();
	   		
        timer.schedule(new TimerTask()
        {
            public void run() 
            {	
            	long currentTime = System.currentTimeMillis() - startTime; 
            	
            	if(currentTime >= 3000)
            	{		   	
	     		   runOnUiThread(new Runnable()
	     		   {
	     			     public void run() 
	     			     {
	     	     		   	DisplayShuffledBoard();
	     			     }
	     			});
	     		   
	     		    this.cancel();
            	}
            }
        },0,1000);		
	}
	
	//This method create and array of tiles to populate the layout
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
					if(currentBitmapArrayIndex < bitmapArray.size())
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
		}
		catch(Exception e) {}
		
		return tileCollection;
	}
	
	//This method creates an array of tiles in reverse order to populate the layout
	public ArrayList<Tile> GenerateTileCollectionFromReversedBoard(ArrayList<Bitmap> bitmapArray)
	{
		ArrayList<Tile> tileCollection = new ArrayList<Tile>();
		
		try
		{
			//Replace the last image with a blank tile and temporary remove it from the array
			Bitmap blankTile = bitmapArray.set(0, CreateBlankTile(tileWidth,tileHeight));		
			bitmapArray.remove(0);
					
			Tile currentTile;
			int currentBitmapArrayIndex = 0;
			int id = bitmapArray.size();
			
			for(int row = 0; row < difficultyRows; row++)
			{
				for(int column = 0; column < difficultyColumns; column++)
				{
						if(currentBitmapArrayIndex < bitmapArray.size())
						{
							currentTile = new Tile();			
							currentTile.xPosition = column;
						    currentTile.yPosition = row;
						    currentTile.id = id;
						    currentTile.bitmap = bitmapArray.get(currentBitmapArrayIndex);		    
						    tileCollection.add(currentTile);			    
						    currentBitmapArrayIndex++;
						    id--;
						}		
				}	
			}
			
			 //If there are an odd number of elements in the array
			 if(tileCollection.size() % 2 != 0)
			 {
				  //swap the last two elements
				  Collections.swap(tileCollection,tileCollection.size() - 1,tileCollection.size() - 2);
			 }
			 
			 //Add the blank tile to the end of the image collection
			 currentTile = new Tile();
			 currentTile.id = -1;
			 currentTile.xPosition = difficultyColumns - 1;
			 currentTile.yPosition = difficultyRows - 1;
			 currentTile.bitmap = blankTile;
			 tileCollection.add(currentTile);
		}
		catch(Exception e){}
			
		return tileCollection;
	}
	
	//Call this to create a blank tile bitmap
	public Bitmap CreateBlankTile(int tileWidth, int tileHeight)
	{
		Bitmap bitmap = Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.BLACK);		
		return bitmap;
	}
	
	//Call this method to see if the puzzle is solved
	public boolean CheckTilePositions()
	{
		try
		{
			int id = 1;
			
			for(Tile currentTile : currentTileArray)
			{
				//if the current tile is out of place
				if(currentTile.id != id)
				{
					//Check to see if it is the blank tile in the last position
					if(currentTile.id == -1 && (currentTileArray.indexOf(currentTile) == currentTileArray.size() - 1))
					{
						//If it is, return true as you won the game
						return true;
					}
					else
					{
						//Else, return false as the puzzle is not yet solved
						return false;
					}
				}
				else
				{
					//Iterate to the next position is the current tile is in place
					id++;
				}
			}
		}
		catch(Exception e){return false;}
		
		return false;
	}
	
	//Call this method to swap a tile with a blank if it is adjacent to the blank tile
	public void SwapClickedTileWithBlank(ImageView img)
	{
		try
		{
			Tile currentTile = (Tile)img.getTag();
			
			if(currentTile.id != -1)
			{
				int x = currentTile.xPosition;
				int y = currentTile.yPosition;
				
				for(Tile t : currentTileArray)
				{
					if(t.xPosition == x + 1 && t.yPosition == y && t.id == -1)
					{			
						//swap the position in the array
						Collections.swap(currentTileArray,currentTileArray.indexOf(currentTile),currentTileArray.indexOf(t));
						
						//swap the coordinates of the clicked tile with the blank tile
						currentTile.xPosition = x + 1;
						currentTile.yPosition = y;
						t.xPosition = x;
						t.yPosition = y;
						
						//Update the tile collection with the new x,y for both tiles
						currentTileArray.set(currentTileArray.indexOf(currentTile), currentTile);
						currentTileArray.set(currentTileArray.indexOf(t), t);
					}
					else if(t.xPosition == x - 1 && t.yPosition == y && t.id == -1)
					{
						//swap the position in the array
						Collections.swap(currentTileArray,currentTileArray.indexOf(currentTile),currentTileArray.indexOf(t));
						
						//swap the coordinates of the clicked tile with the blank tile
						currentTile.xPosition = x - 1;
						currentTile.yPosition = y;
						t.xPosition = x;
						t.yPosition = y;
						
						//Update the tile collection with the new x,y for both tiles
						currentTileArray.set(currentTileArray.indexOf(currentTile), currentTile);
						currentTileArray.set(currentTileArray.indexOf(t), t);
					}
					else if(t.xPosition == x && t.yPosition == y + 1 && t.id == -1)
					{
						//swap the position in the array
						Collections.swap(currentTileArray,currentTileArray.indexOf(currentTile),currentTileArray.indexOf(t));
						
						//swap the coordinates of the clicked tile with the blank tile
						currentTile.xPosition = x;
						currentTile.yPosition = y + 1;
						t.xPosition = x;
						t.yPosition = y;
						
						//Update the tile collection with the new x,y for both tiles
						currentTileArray.set(currentTileArray.indexOf(currentTile), currentTile);
						currentTileArray.set(currentTileArray.indexOf(t), t);
					}
					else if(t.xPosition == x && t.yPosition == y - 1 && t.id == -1)
					{
						//swap the position in the array
						Collections.swap(currentTileArray,currentTileArray.indexOf(currentTile),currentTileArray.indexOf(t));
						
						//swap the coordinates of the clicked tile with the blank tile
						currentTile.xPosition = x;
						currentTile.yPosition = y - 1;
						t.xPosition = x;
						t.yPosition = y;
						
						//Update the tile collection with the new x,y for both tiles
						currentTileArray.set(currentTileArray.indexOf(currentTile), currentTile);
						currentTileArray.set(currentTileArray.indexOf(t), t);	
					}
					
					 LinearLayout layout = (LinearLayout) findViewById(R.id.GameplayLayout);
					 layout.removeAllViews();
					 
					 GenerateTable(currentTileArray);
				}
			}
			
			//After a successful swap, check to see if the puzzle is solved
			if(CheckTilePositions())
			{
			 	Intent i = new Intent(this, YouWin.class);
		    	i.putExtra("imageToDisplay", resource);
		    	i.putExtra("difficultyColumns", difficultyColumns);
		    	i.putExtra("difficultyRows",difficultyRows);
			  	i.putExtra("numberOfMoves",movesCounter);
		    	startActivity(i);		
			}
		}
		catch(Exception e){}
	}
	
	
	//Generates a TableView layout containing the tiled images and adds it to the layout
	public void GenerateTable(ArrayList<Tile> tileArray)
	{
		try
		{
			LinearLayout layout = (LinearLayout) findViewById(R.id.GameplayLayout);
			   
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
			    imageView.setOnClickListener(this);
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
	
	//Called when a tile image view is clicked
	public void onClick(View v)
	{
		try
		{
			 ImageView view = (ImageView)v;
			 movesCounter++;
			 SwapClickedTileWithBlank(view);
		}
		catch(Exception e){}
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) 
	    {
		 	try
		 	{
		    	MenuInflater inflater = getMenuInflater();
		    	inflater.inflate(R.menu.options_menu, menu);
		 	}
			catch(Exception e){return false;}
		 	
	    	return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	    	try
	    	{
		    	if (item.getItemId() == R.id.easy) 
		    	{
		    		LinearLayout layout = (LinearLayout) findViewById(R.id.GameplayLayout);
		    		layout.removeAllViews();
		    		
		    		StartGamePlay(3,3);
		    	}
		    	else if (item.getItemId() == R.id.medium) 
		    	{
		    		LinearLayout layout = (LinearLayout) findViewById(R.id.GameplayLayout);
		    		layout.removeAllViews();
		    		
		    		StartGamePlay(4,4);
		    	}
		    	else if (item.getItemId() == R.id.hard) 
		    	{
		    		LinearLayout layout = (LinearLayout) findViewById(R.id.GameplayLayout);
		    		layout.removeAllViews();
		    		
		    		StartGamePlay(5,5);
		    	}
		    	else if (item.getItemId() == R.id.shuffle) 
		    	{
		    		initialBitmapArray = CreateBitmapArray();
		    		initialBitmapArray.set(initialBitmapArray.size() - 1, CreateBlankTile(tileWidth,tileHeight));
		    		DisplayShuffledBoard();
		    	}
		    	else if (item.getItemId() == R.id.pickImage) 
		    	{
		    		finish();
		    	}
	    	}
			catch(Exception e){return false;}
	    	
	    	return true;
	    }
}