/**
 * @author niranjanshukla
 */
package au.com.ck.bluetoothnotifier;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

public class BluetoothReceiverService extends IntentService {
	private BluetoothServerSocket mmServerSocket;
	private BluetoothAdapter mBluetoothAdapter;
	private UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
    
    public BluetoothReceiverService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

    public BluetoothReceiverService(){
    	super("BluetoothReceiverService");
    }
	@Override
    protected void onHandleIntent(Intent workIntent) {
		new BluetoothServer().start();
        
    }
	
	private class BluetoothServer extends Thread{
		
		BluetoothServerSocket bss;
		public BluetoothServer(){
			
			BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

	        for (BluetoothDevice btd : bta.getBondedDevices()) {
	            Log.i("Bluetooth Device Found",
	                  btd.toString() + "; " + btd.getName());
	        }

	        try {
	            bss = bta.listenUsingRfcommWithServiceRecord("BluetoothChat", UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
	            
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		}
		
		@Override
	    public void run() {
	        // TODO Auto-generated method stub
	        boolean bContinue = true;
	        while (bContinue) {
	            try {
	                Thread.sleep(100);
	            } catch (Exception e) {

	            }

	            try {
	                System.out.println("Listening for connection");
	                BluetoothSocket bs = bss.accept();
	                //Read the incoming string.
	                String buffer;

	                DataInputStream in = new DataInputStream(bs.getInputStream());
	                buffer = in.readUTF();
	                Log.d("Incoming message received !",buffer);
	                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
	                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	  			    Java2JS j2j = new Java2JS();
	  			    j2j.setResult(buffer);
	  			    intent.putExtra("summonmessage", j2j);
	  			  
	  			    startActivity(intent);
	                bs.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	                bContinue = false;
	            }
	        }
	        try {
				bss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }


		
	}
	
	
	
	
	
}


