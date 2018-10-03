package org.tain.kiea.thomson;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.tain.utils.ResourcesUtils;

public class UdpSender {

	private static final boolean flag;
	private static final String _udpHost;
	private static final String _udpPort;
	private static final String _charSet;

	private static final byte[] header = {(byte)0x02, (byte)'R', (byte)'E', (byte)'U', (byte)'T' };

	static {
		flag = true;
		_udpHost = ResourcesUtils.getString("org.tain.kiea.thomson.udp.host");
		_udpPort = ResourcesUtils.getString("org.tain.kiea.thomson.udp.port");
		_charSet = ResourcesUtils.getString("org.tain.kiea.thomson.charSet");
	}

	///////////////////////////////////////////////////////////////////////////

	public static void send(String message) {

		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] body = null;
		byte[] buffer = null;
		try {
			body = message.getBytes(_charSet);
			buffer = new byte[header.length + body.length];

			System.arraycopy(header, 0, buffer, 0, header.length);
			System.arraycopy(body, 0, buffer, header.length, body.length);

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
}
