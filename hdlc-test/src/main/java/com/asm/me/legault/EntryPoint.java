package com.asm.me.legault;


import com.asm.me.legault.endpoint.PrimaryStation;
import com.asm.me.legault.endpoint.SecondaryStation;
import com.asm.me.legault.hdlc.CompactBitSet;
import com.asm.me.legault.hdlc.IFrame;
import com.asm.util.ConvertCode;

import java.util.Arrays;

public class EntryPoint {
	
	static final String HOSTNAME = "localhost";
	static final int PORT_NUMBER = 65234;
	
	public static void main(String[] args){
		
//		PrimaryStation primaryStation = new PrimaryStation(PORT_NUMBER);
//		primaryStation.start();
//
//		SecondaryStation stationA = new SecondaryStation("A", HOSTNAME, PORT_NUMBER);
//		stationA.start();
//
//		final byte[] bytes = {0x34};
//		primaryStation.sendCommand(new IFrame(CompactBitSet.fromBytes(bytes)));

		final byte[] bytes = {0x34,0x35,0x36}; //  hex : 7E000034353643E17E

//		String hex = "7E000034353643E17E";
//		final byte[] bytes = hex.getBytes();

		final IFrame iFrame = new IFrame(CompactBitSet.fromBytes(bytes));

		//		System.out.println("iFrame.toString()  = " + iFrame.toString());
		System.out.println("iFrame.toHexString()  = " + iFrame.toHexString());

		final byte[] bytes1 = iFrame.toByteArray();
		System.out.println("bytes1  = " + Arrays.toString(    bytes1   ));



	}
}
