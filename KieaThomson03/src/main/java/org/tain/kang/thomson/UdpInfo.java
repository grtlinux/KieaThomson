package org.tain.kang.thomson;

import java.util.ArrayList;
import java.util.List;

import org.tain.kang.utils.ClassUtils;
import org.tain.kang.utils.ResourcesUtils;

public class UdpInfo {

	private static final boolean flag;

	private static final List<String> lstUdpInfo;
	private static int idx;

	static {
		flag = true;
		lstUdpInfo = new ArrayList<String>();
		idx = 0;

		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public UdpInfo() {}

	///////////////////////////////////////////////////////////////////////////

	private static void initialize() throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		String strRange = ResourcesUtils.getString("org.tain.kiea.thomson.udp.range");
		String[] itmRange = strRange.split("\\s*~\\s*");
		int idxBeg = Integer.parseInt(itmRange[0]);
		int idxEnd = Integer.parseInt(itmRange[1]);
		if (flag) System.out.printf(">>>>> lstUdpInfo. itmRange: (%02d ~ %02d)%n", idxBeg, idxEnd);

		for (int i=idxBeg; i <= idxEnd; i++) {
			try {
				String key = String.format("org.tain.kiea.thomson.udp.%02d", i);
				String info = ResourcesUtils.getString(key);
				lstUdpInfo.add(info);
			} catch (Exception e) {
				if (!flag) e.printStackTrace();
			}
		}

		if (flag) {
			for (String strUdpInfo : lstUdpInfo) {
				System.out.printf(">>>>> lstUdpInfo: [%s]%n", strUdpInfo);
			}
		}
	}

	public static String getUdpInfo() throws Exception {
		if (idx >= lstUdpInfo.size())
			return null;

		return lstUdpInfo.get(idx++);
	}

	public static List<String> getListUdpInfo() throws Exception {
		return lstUdpInfo;
	}

	public static int getSize() {
		return lstUdpInfo.size();
	}
}
