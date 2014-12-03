/**
 * @author niranjanshukla
 */
package au.com.ck.bluetoothnotifier;

import java.io.Serializable;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class Java2JS implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String result = "";
	
	@JavascriptInterface
	public String getResult(){
		Log.d("au.com.ck.bluetoothnotifier","Got result "+result);
		return this.result;
	}
	
	public void setResult(String result){
		Log.d("au.com.ck.bluetoothnotifier","Set result");
		this.result = result;
	}
	
	  @Override
	  public String toString() {
	    return result;
	  }
	
}