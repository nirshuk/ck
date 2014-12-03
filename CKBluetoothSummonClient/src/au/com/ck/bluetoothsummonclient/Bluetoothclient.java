/**
 * @author niranjanshukla
 */
package au.com.ck.bluetoothsummonclient;
import java.io.*;
import java.util.Vector;

import javax.bluetooth.*;
import javax.microedition.io.*;


public class Bluetoothclient extends Thread implements DiscoveryListener {

	
	protected UUID defaultUUID = new UUID(0x1101);

	
	private LocalDevice local;
	private DiscoveryAgent agent;
	private DataOutputStream dout;
	private StreamConnection conn;
	private Vector<RemoteDevice> devices;
	private Vector<ServiceRecord> services;

	public Bluetoothclient() {
		services = new Vector<ServiceRecord>();
	}

	@Override
	public void run() {
		findWearable();
	}

	protected void findWearable(){
		try{
			devices              = new Vector<RemoteDevice>();
			LocalDevice local    = LocalDevice.getLocalDevice();
			DiscoveryAgent agent = local.getDiscoveryAgent();

			agent.startInquiry(DiscoveryAgent.GIAC, this);
			debugString("Starting wearable discovery...");
		}catch(Exception e) {
			debugString("Error initiating discovery.");
		}
	}

	protected void findServices(RemoteDevice device){
		try{
			UUID[] uuids  = new UUID[1];
			uuids[0]      = defaultUUID;    //The UUID of the each service
			local         = LocalDevice.getLocalDevice();
			agent         = local.getDiscoveryAgent();

			debugString("Beginning to search services");
			agent.searchServices(null, uuids, device, this);
			debugString("Starting Service Discovery...");
		}catch(Exception e){
			debugString("Error finding services.");
		}
	}

	public void broadcastCommand(String str) {
		for(ServiceRecord sr : services) {
			String url = sr.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);

			conn = null;

			try {
				debugString("Sending command to " + url);

				conn = (StreamConnection) Connector.open(url);
				dout = new DataOutputStream(conn.openOutputStream());

				dout.writeUTF(str);
				debugString(String.format("Sending %s", str));

				dout.flush();
				dout.close();
				conn.close();

				debugString("Sent. Connection Closed.");

			} catch (Exception e) {
				debugString("Failed to connect to " + url);
				e.printStackTrace();
			}
		}
	}


	@Override
	public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
		try {
			String name = arg0.getFriendlyName(true);

			debugString("Found wearable: " + name);
			devices.add(arg0);
			
		} catch (IOException e) {
			debugString("Failed to get remoteWearable Name.");
		}
	}

	@Override
	public void inquiryCompleted(int arg0) {
		debugString("Inquiry Completed.");
		debugString(devices.toString());

		// Start service probing
		for(RemoteDevice d :devices) {
			debugString("Proceeding to finding services");
			findServices(d);
		}
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		debugString("Service search completed.");

		broadcastCommand(new String("{\"sender\":\"mom\",\"message\":\"Join the bedtime reading\"}"));
	}

	@Override
	public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
		debugString("Inside services Discovered callback for services "+arg1);
		for(ServiceRecord x : arg1) {
			services.add(x);
		}
	}

	
	protected static void debugString(String str) {
		System.out.println(String.format("%s :: %s", Bluetoothclient.class.getName(), str));
	}
}
