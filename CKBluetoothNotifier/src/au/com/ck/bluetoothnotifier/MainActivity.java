/**
 * @author niranjanshukla
 */
package au.com.ck.bluetoothnotifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity{
    
    Thread t;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mServiceIntent = new Intent(this, BluetoothReceiverService.class);
        this.startService(mServiceIntent);
    }

}