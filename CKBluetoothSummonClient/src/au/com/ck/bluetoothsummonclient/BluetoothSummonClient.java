/**
 * @author niranjanshukla
 */
package au.com.ck.bluetoothsummonclient;
public class BluetoothSummonClient {

	public static void main(String[] args) {
		Bluetoothclient client = new Bluetoothclient();
		client.start();
		
		while(true) {}
	}

}
