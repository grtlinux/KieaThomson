package org.tain.test;

public class PacketHeaderMain {

	public static void main(String[] args) {

		byte[] header = {(byte)0x02, (byte)'R', (byte)'E', (byte)'U', (byte)'T' };

		StringBuilder sb = new StringBuilder();
		for (byte byt : header) {
			sb.append(String.format("%02X ", byt));
		}

		System.out.println(">>>>> [" + sb.toString() + "]");
	}
}
