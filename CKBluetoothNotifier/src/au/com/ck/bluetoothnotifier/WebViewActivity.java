/**
 * @author niranjanshukla
 */
package au.com.ck.bluetoothnotifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
 
public class WebViewActivity extends Activity {
 
	private WebView webView;
 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summon);
 
		
		//webView.loadUrl("http://www.google.com");
		//Intent intent = getIntent();
		//String message = intent.getStringExtra("APIResponse");
		
		//String customHtml = "<html><body>"+message+"</body></html>";
		//webView.loadData(customHtml, "text/html", "UTF-8");
	}
	
	public void onStart() {
        super.onStart();
        
        webView = (WebView) findViewById(R.id.webView1);
		Intent intent = getIntent();
		Java2JS j2j = (Java2JS)intent.getSerializableExtra("summonmessage");
		Log.d("au.com.ck.bluetoothnotifier","Got object in WebViewActivity "+j2j.getResult());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.addJavascriptInterface(j2j, "java2js");
		webView.loadUrl("file:///android_asset/summonmessage.html");
		
	}
 
}