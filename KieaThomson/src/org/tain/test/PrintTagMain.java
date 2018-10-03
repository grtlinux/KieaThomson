package org.tain.test;

import org.tain.utils.ClassUtils;

public class PrintTagMain {

	private static final boolean flag = true;

	public static void main(String[] args) {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		if (flag) printBody(args);
	}

	private static void printBody(String[] args) {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		String body = "Singapore (Platts)--2Oct2018/1010 pm EDT/210 GMT\n\n-------------------------Cash Brent-----------------------NYMEX WTI--"
				+ "\n Oct 03        Dec            Jan            Feb         Nov     Dec\n0200 GMT   84.92-84.96    84.37-84.41    84.00-84.04    75.2"
				+ "1   75.04\n---------------------------------------------------------------------\n Oct 02        Dec            Jan            Feb  "
				+ "       Nov     Dec\n0830 GMT   85.09-85.13    84.64-84.68    84.28-84.32    75.60   75.43\n-----------------------------------------"
				+ "----------------------------\n Oct 02        Dec            Jan            Feb         Nov     Dec\nFutures       84.80          84."
				+ "35          83.98       75.23   75.04\nCash       85.43-85.45    84.86-84.88    84.47-84.49    75.24   75.04\n----------------------"
				+ "-----------------------------------------------\n\nIndex of Platts EMEA Crude pages found on PGA1200\n \n\n--Platts Global Alert--\n";

		System.out.println(body);
	}
}
