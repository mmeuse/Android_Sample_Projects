/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
package net.cs76.projects.GunksExperiment90857932;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class SplashScreen extends Activity {
	
	private long startTime = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
    	LinearLayout layout = (LinearLayout) findViewById(R.id.SplashLayout);   
        layout.setBackgroundColor(Color.WHITE);
        
        StartTimer();
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
  	     	     		   	DisplayMainMenu();
  	     			     }
  	     			});
  	     		   
  	     		    this.cancel();
              	}
              }
          },0,1000);		
  	}
  	
  	public void DisplayMainMenu()
  	{
  	 	Intent i = new Intent(this, MainMenu.class);
     	startActivity(i);
  	}
}