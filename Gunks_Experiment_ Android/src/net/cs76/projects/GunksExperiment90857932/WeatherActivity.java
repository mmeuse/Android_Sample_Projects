/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
package net.cs76.projects.GunksExperiment90857932;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WeatherActivity extends Activity 
{
	WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) 
	{    
		super.onCreate(savedInstanceState);    
		setContentView(R.layout.weather);    
		mWebView = (WebView) findViewById(R.id.webview); 
		mWebView.getSettings().setJavaScriptEnabled(true);   
		mWebView.loadUrl("http://m.weather.com/right_now/USNY0990");
		mWebView.setWebViewClient(new HelloWebViewClient());
	} 
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{    
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
		{        
			mWebView.goBack();      
			return true;    
		}    
	
		return super.onKeyDown(keyCode, event);
	}
}


class HelloWebViewClient extends WebViewClient
{   
	@Override   
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{        
		view.loadUrl(url);      
		return true;    
	}
}