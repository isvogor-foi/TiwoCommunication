package com.tiwo.communication;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialReaderThread implements Runnable{
	SerialPort in;
    
    public SerialReaderThread (SerialPort sp)
    {
        this.in = sp;
    }
    
    public void run ()
    {
		System.out.println("Thread started - 1");

        try {
        	if(in == null) return;
			byte[] buffer = in.readBytes(1024);
            System.out.print(new String(buffer,0,1024));
            
		} catch (SerialPortException e) {
			e.printStackTrace();
		}//Read 10 bytes from serial port

    	/*
        byte[] buffer = new byte[1024];
        int len = -1;
        try
        {
            while ( ( len = this.in.read(buffer)) > -1 )
            {
                System.out.print(new String(buffer,0,len));
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        } 
        */           
    }
}
