package org.tain.kiea.thomson;

import java.util.ArrayList;
import java.util.List;

import org.tain.utils.ClassUtils;
import org.tain.utils.ResourcesUtils;

public class ConnectInfo {

	private static final boolean flag;

	private static final List<String> lstConnInfo;
	private static int idx;

	static {
		flag = true;
		lstConnInfo = new ArrayList<String>();
		idx = 0;

		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public ConnectInfo() {}

	///////////////////////////////////////////////////////////////////////////

	private static void initialize() throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		String strRange = ResourcesUtils.getString("org.tain.kiea.thomson.connectInfo.range");
		String[] itmRange = strRange.split("\\s*~\\s*");
		int idxBeg = Integer.parseInt(itmRange[0]);
		int idxEnd = Integer.parseInt(itmRange[1]);
		if (flag) System.out.printf(">>>>> lstConnInfo. itmRange: (%02d ~ %02d)%n", idxBeg, idxEnd);

		for (int i=idxBeg; i <= idxEnd; i++) {
			try {
				String key = String.format("org.tain.kiea.thomson.connectInfo.%02d", i);
				String info = ResourcesUtils.getString(key);
				lstConnInfo.add(info);
			} catch (Exception e) {
				if (!flag) e.printStackTrace();
			}
		}

		if (flag) {
			for (String strConnInfo : lstConnInfo) {
				System.out.printf(">>>>> lstConnInfo: [%s]%n", strConnInfo);
			}
		}
	}

	public static String getConnectInfo() throws Exception {
		if (idx >= lstConnInfo.size())
			return null;

		return lstConnInfo.get(idx++);
	}

	public static List<String> getListConnectInfo() throws Exception {
		return lstConnInfo;
	}

	public static int getSize() {
		return lstConnInfo.size();
	}
}
