package org.tain.test;

import org.tain.utils.ClassUtils;

public class PacketHeaderMain {

	private static final boolean flag = true;

	public static void main(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (flag) test01(args);
		if (flag) test02(args);
	}

	private static void test01(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		byte[] header = {(byte)0x02, (byte)'R', (byte)'E', (byte)'U', (byte)'T' };

		StringBuilder sb = new StringBuilder();
		for (byte byt : header) {
			sb.append(String.format("%02X ", byt));
		}

		System.out.println(">>>>> [" + sb.toString() + "]");
	}

	private static void test02(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		byte[] src1 = "1234567890".getBytes();
		byte[] src2 = "abcd".getBytes();

		byte[] dst = new byte[src1.length + src2.length];

		System.arraycopy(src1, 0, dst, 0, src1.length);
		System.arraycopy(src2, 0, dst, src1.length, src2.length);

		System.out.println(">>>>> [" + new String(dst) + "]");
	}
}
