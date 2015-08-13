package com.tiwo.communication;

import jssc.SerialPort;

public class SerialWriterThread implements Runnable {

	SerialPort out;

	public SerialWriterThread(SerialPort out) {
		this.out = out;
	}

	
	// currently not in use, just sends "test" forever
	public void run() {
		System.out.println("Thread started - 2");
        try {
			while (true) {
				out.writeBytes("This is a test string".getBytes());
				System.out.println(" --> thick..");
				//this.out.flush();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        //Write data to port
		/*
		try {
			/*
			int c = 0;
			while ((c = System.in.read()) > -1) {
				this.out.write(c);
			}
			
			while (true) {
				this.out.write("test".getBytes());
				//this.out.flush();
				Thread.sleep(2000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}
}
