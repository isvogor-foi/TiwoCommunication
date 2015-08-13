package com.tiwo.communication;

import java.io.IOException;
import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;



public class Serial {

	private ArrayList<String> ports;
	public static SerialPort serialPort;
	private static Serial instance;
	public boolean isConnected;
	
	public static Serial getInstance(){
		if(instance == null) instance = new Serial();
		return instance;
	}
	
	private Serial() {		
		ports = new ArrayList<String>();
		isConnected = false;

        @SuppressWarnings("rawtypes")
		String[] portList = SerialPortList.getPortNames();
        
        // return if nothing detected
        if(portList.length == 0){
        	System.out.println("Error: Arduino not detected");
        	return;
        }
        
        // populate port names
        for(int i = 0; i < portList.length; i++){
            ports.add(portList[i]);
        }

	}
	
	public void connect(String portName, int baudRate) throws Exception {
		if (isConnected)
			return;

		serialPort = new SerialPort(portName);
		isConnected = true;

		serialPort.openPort();

		serialPort.setParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		serialPort.addEventListener(new PortReader());

	}
    
    public void disconnect() throws SerialPortException{
    	if(isConnected){
    		serialPort.closePort();
    		isConnected = false;
    	}
    }	

    public void sendMessage(String message) {
    	try {
			serialPort.writeBytes(message.getBytes());
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
    }

	public ArrayList<String> getPorts() {
		return ports;
	}

	// receiving response from port
	private static class PortReader implements SerialPortEventListener {

		StringBuilder message = new StringBuilder();
		@Override
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() && event.getEventValue() > 0) {
				try {
					byte buffer[] = serialPort.readBytes();
					for (byte b : buffer) {
						if ((b == '\r' || b == '\n') && message.length() > 0) {
							String toProcess = message.toString();
							System.out.println(toProcess);
							message.setLength(0);
						} else {
							message.append((char) b);
						}
					}
				} catch (SerialPortException ex) {
					System.out.println(ex);
					System.out.println("serialEvent");
				}
			}
		}
	}

    

}
