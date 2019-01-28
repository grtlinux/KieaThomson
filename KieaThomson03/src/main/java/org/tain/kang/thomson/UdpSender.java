package org.tain.kang.thomson;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.tain.kang.utils.ResourcesUtils;

public class UdpSender {

	private static final boolean flag;
	private static final String _udpHost;
	private static final String _udpPort;
	private static final String _charSet;

	private static final int _fragSize;

	private static final byte[] reutHeader = {(byte)0x02, (byte)'R', (byte)'E', (byte)'U', (byte)'T' };

	static {
		flag = true;
		_udpHost = ResourcesUtils.getString("org.tain.kiea.thomson.udp.host");
		_udpPort = ResourcesUtils.getString("org.tain.kiea.thomson.udp.port");
		_charSet = ResourcesUtils.getString("org.tain.kiea.thomson.charSet");

		_fragSize = Integer.valueOf(ResourcesUtils.getString("org.tain.kiea.thomson.udp.maxSize"));

		if (flag) {
			System.out.printf(">>>>> UdpSender._udpHost  = %s%n", _udpHost);
			System.out.printf(">>>>> UdpSender._udpPort  = %s%n", _udpPort);
			System.out.printf(">>>>> UdpSender._charSet  = %s%n", _charSet);
			System.out.printf(">>>>> UdpSender._fragSize = %s%n", _fragSize);
		}
		
		new UdpInfo();
	}

	public UdpSender() {}

	///////////////////////////////////////////////////////////////////////////

	// TODO: KANG-20181004: important
	// send one udp packet.
	// if packet size is more than 64 kb, then occur an error event....
	// and then to modify to the below send(guid, message)
	public static void send(String message) {

		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] body = null;
		byte[] buffer = null;
		try {
			body = message.getBytes(_charSet);
			buffer = new byte[reutHeader.length + body.length];

			System.arraycopy(reutHeader, 0, buffer, 0, reutHeader.length);
			System.arraycopy(body, 0, buffer, reutHeader.length, body.length);

			socket = new DatagramSocket();
			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(_udpHost), Integer.parseInt(_udpPort));
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) try { socket.close(); } catch (Exception e) {}
		}

		if (flag) System.out.printf(">>>>> UdpSender.send(): length = %d%n", buffer.length);
		if (flag) {
			StringBuilder sb = new StringBuilder();
			for (int i=0; i < 20; i++) {
				if (i == 0) {
					sb.append(String.format("[0x%02X]", buffer[i]));
				} else {
					sb.append(String.format("%c", buffer[i]));
				}
			}

			System.out.printf(">>>>> UdpSender.buffer(front 20 bytes) => \"%s.....\"%n", sb.toString());
		}
	}

	/**
	 * split the message to maxsize 
	 * 
	 * @param guid
	 * @param message
	 */
	public static void send(String guid, String message) {

		int messageSize = message.length();
		int fragCnt = (messageSize + _fragSize - 1) / _fragSize;

		for (int i=0; i < fragCnt; i++) {
			int fragNum = i + 1;
			int idxBeg = i * _fragSize;
			int idxEnd = Math.min(idxBeg + _fragSize, messageSize);

			String strGuidHeader = String.format("%-55s%05d%05d", guid, fragCnt, fragNum);
			String strBody = message.substring(idxBeg, idxEnd);

			sendUdpPacket(reutHeader, strGuidHeader.getBytes(), strBody.getBytes());
		}
	}

	private static void sendUdpPacket(byte[] reutHeader, byte[] guidHeader, byte[] body) {

		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] buffer = null;
		try {
			buffer = new byte[reutHeader.length + guidHeader.length + body.length];

			System.arraycopy(reutHeader, 0, buffer, 0                                    , reutHeader.length);
			System.arraycopy(guidHeader, 0, buffer, reutHeader.length                    , guidHeader.length);
			System.arraycopy(body      , 0, buffer, reutHeader.length + guidHeader.length, body.length);

			socket = new DatagramSocket();
			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(_udpHost), Integer.parseInt(_udpPort));
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) try { socket.close(); } catch (Exception e) {}
		}

		if (flag) System.out.printf(">>>>> Udp.send() --> length = %d%n", buffer.length);
		if (flag) {
			StringBuilder sb = new StringBuilder();
			int sizHeader = reutHeader.length + guidHeader.length;
			for (int i=0; i < sizHeader; i++) {
				if (i == 0) {
					sb.append(String.format("[0x%02X]", buffer[i]));
				} else {
					sb.append(String.format("%c", buffer[i]));
				}
			}

			System.out.printf(">>>>> Udp.message --> (70=5+55+5+5) \"%s%s\" + \"%s...\"%n"
					, sb.substring(0, 6)      // [0x02]
					, sb.substring(6, 10)     // REUT
					, sb.substring(10)        // guid ~
					);
		}
	}
	
	/**
	 * 
	 */
	public static void multiSend(String guid, String message) throws Exception {
		for (String udpInfo : UdpInfo.getListUdpInfo()) {
			if (flag) System.out.println(">>>>> udpInfo: " + udpInfo);
			
			String[] udpItem = udpInfo.split("\\s*:\\s*");
			if (flag) System.out.printf(">>>>> [%s] [%s]%n", udpItem[0], udpItem[1]);
			
			//send(udpItem[0], udpItem[1], guid, message);
		}
	}
	
	public static void send(String udpHost, String udpPort, String guid, String message) {

		int messageSize = message.length();
		int fragCnt = (messageSize + _fragSize - 1) / _fragSize;

		for (int i=0; i < fragCnt; i++) {
			int fragNum = i + 1;
			int idxBeg = i * _fragSize;
			int idxEnd = Math.min(idxBeg + _fragSize, messageSize);

			String strGuidHeader = String.format("%-55s%05d%05d", guid, fragCnt, fragNum);
			String strBody = message.substring(idxBeg, idxEnd);

			sendUdpPacket(udpHost, udpPort, reutHeader, strGuidHeader.getBytes(), strBody.getBytes());
		}
	}

	private static void sendUdpPacket(String udpHost, String udpPort, byte[] reutHeader, byte[] guidHeader, byte[] body) {

		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] buffer = null;
		try {
			buffer = new byte[reutHeader.length + guidHeader.length + body.length];

			System.arraycopy(reutHeader, 0, buffer, 0                                    , reutHeader.length);
			System.arraycopy(guidHeader, 0, buffer, reutHeader.length                    , guidHeader.length);
			System.arraycopy(body      , 0, buffer, reutHeader.length + guidHeader.length, body.length);

			socket = new DatagramSocket();
			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(udpHost), Integer.parseInt(udpPort));
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) try { socket.close(); } catch (Exception e) {}
		}

		if (flag) System.out.printf(">>>>> Udp.send() --> length = %d     [%s:%s]%n", buffer.length, udpHost, udpPort);
		if (flag) {
			StringBuilder sb = new StringBuilder();
			int sizHeader = reutHeader.length + guidHeader.length;
			for (int i=0; i < sizHeader; i++) {
				if (i == 0) {
					sb.append(String.format("[0x%02X]", buffer[i]));
				} else {
					sb.append(String.format("%c", buffer[i]));
				}
			}

			System.out.printf(">>>>> Udp.message --> (70=5+55+5+5) \"%s%s\" + \"%s...\"%n"
					, sb.substring(0, 6)      // [0x02]
					, sb.substring(6, 10)     // REUT
					, sb.substring(10)        // guid ~
					);
		}
	}
}
