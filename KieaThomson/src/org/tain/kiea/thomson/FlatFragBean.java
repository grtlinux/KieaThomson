package org.tain.kiea.thomson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.tain.utils.ResourcesUtils;

public final class FlatFragBean {

	private String _charSet;

	private String guid;
	private String timactMs;
	private String activDate;
	private String mrnType;
	private String mrnVerMajor;
	private String mrnVerMinor;
	private String mrnSrc;
	private Long totalSize;
	private List<Long> sizes;
	private List<String> fragNum;
	private List<byte[]> fragment;

	public FlatFragBean() {
		this._charSet = ResourcesUtils.getString("org.tain.kiea.thomson.charSet");

		this.guid = null;
		this.timactMs = null;
		this.activDate = null;
		this.mrnType = null;
		this.mrnVerMajor = null;
		this.mrnVerMinor = null;
		this.mrnSrc = null;
		this.totalSize = new Long(0);
		this.sizes = new ArrayList<Long>();
		this.fragNum = new ArrayList<String>();
		this.fragment = new ArrayList<byte[]>();
	}

	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getTimactMs() {
		return timactMs;
	}
	public void setTimactMs(String timactMs) {
		this.timactMs = timactMs;
	}
	public String getActivDate() {
		return activDate;
	}
	public void setActivDate(String activDate) {
		this.activDate = activDate;
	}
	public String getMrnType() {
		return mrnType;
	}
	public void setMrnType(String mrnType) {
		this.mrnType = mrnType;
	}
	public String getMrnVerMajor() {
		return mrnVerMajor;
	}
	public void setMrnVerMajor(String mrnVerMajor) {
		this.mrnVerMajor = mrnVerMajor;
	}
	public String getMrnVerMinor() {
		return mrnVerMinor;
	}
	public void setMrnVerMinor(String mrnVerMinor) {
		this.mrnVerMinor = mrnVerMinor;
	}
	public String getMrnSrc() {
		return mrnSrc;
	}
	public void setMrnSrc(String mrnSrc) {
		this.mrnSrc = mrnSrc;
	}
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public List<Long> getSizes() {
		return sizes;
	}
	public void setSizes(List<Long> sizes) {
		this.sizes = sizes;
	}
	public List<String> getFragNum() {
		return fragNum;
	}
	public void setFragNum(List<String> fragNum) {
		this.fragNum = fragNum;
	}
	public List<byte[]> getFragment() {
		return fragment;
	}
	public void setFragment(List<byte[]> fragment) {
		this.fragment = fragment;
	}

	//////////////////////////////////////////////////////////

	public void addSize(Long size) {
		this.sizes.add(size);
	}

	public void addFragNum(String fragNum) {
		this.fragNum.add(fragNum);
	}

	public void addFragment(byte[] fragment) {
		this.fragment.add(fragment);
	}

	//////////////////////////////////////////////////////////

	public long sumSizes() {
		long sumSizes = 0;

		for (Long size : this.sizes) {
			sumSizes += size;
		}

		return sumSizes;
	}

	public boolean isTotalSize() {
		if (this.totalSize == 0)
			return false;

		if (this.sizes.size() == 0)
			return false;

		if (sumSizes() == this.totalSize) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(String.format("\t==> GUID       : %s%n", this.getGuid()));
		sb.append(String.format("\t==> TIMACT_MS  : %s%n", this.getTimactMs()));
		sb.append(String.format("\t==> ACTIV_DATE : %s%n", this.getActivDate()));
		sb.append(String.format("\t==> MRN_TYPE   : %s%n", this.getMrnType()));
		sb.append(String.format("\t==> MRN_V_MAJ  : %s%n", this.getMrnVerMajor()));
		sb.append(String.format("\t==> MRN_V_MIN  : %s%n", this.getMrnVerMinor()));
		sb.append(String.format("\t==> MRN_SRC    : %s%n", this.getMrnSrc()));
		sb.append(String.format("\t==> TOT_SIZE   : %s%n", this.getTotalSize()));
		sb.append(String.format("\t==> SIZES      : %s%n", this.getSizes()));
		sb.append(String.format("\t==> FRAG_NUM   : %s%n", this.getFragNum()));
		sb.append(String.format("\t==> sizable    : %s%n", this.isTotalSize()));
		sb.append(String.format("\t==> charSet    : %s%n", this._charSet));

		return sb.toString();
	}

	public String getBodyHeaderJson() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append(String.format("\"%s\":\"%s\",", "GUID", this.getGuid()));
		sb.append(String.format("\"%s\":\"%s\",", "TIMACT_MS", this.getTimactMs()));
		sb.append(String.format("\"%s\":\"%s\",", "ACTIV_DATE", this.getActivDate()));
		sb.append(String.format("\"%s\":\"%s\",", "MRN_TYPE", this.getMrnType()));
		sb.append(String.format("\"%s\":\"%s\",", "MRN_V_MAJ", this.getMrnVerMajor()));
		sb.append(String.format("\"%s\":\"%s\",", "MRN_V_MIN", this.getMrnVerMinor()));
		sb.append(String.format("\"%s\":\"%s\",", "MRN_SRC", this.getMrnSrc()));
		sb.append(String.format("\"%s\":\"%s\",", "TOT_SIZE", this.getTotalSize()));
		sb.append(String.format("\"%s\":\"%s\",", "charSet", this._charSet));
		sb.append(String.format("\"%s\":%s,"    , "SIZES", this.getSizes()));
		sb.append(String.format("\"%s\":%s,"    , "FRAG_NUM", this.getFragNum()));

		return sb.toString();
	}

	public String getMessage() {
		ByteArrayOutputStream bos = null;
		String retMsg = null;

		try {
			bos = new ByteArrayOutputStream();
			List<byte[]> lstFragment = this.getFragment();
			for (byte[] fragment : lstFragment) {
				bos.write(fragment);
			}

			retMsg = unzipPayload(bos.toByteArray());
			retMsg = retMsg.replaceFirst("\\{", this.getBodyHeaderJson());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) try { bos.close(); } catch (Exception e) {}
		}

		return retMsg;
	}

	private String unzipPayload(byte[] bytes) throws Exception {
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = null;

		try {
			bis = new ByteArrayInputStream(bytes);
			bos = new ByteArrayOutputStream();

			GZIPInputStream gis = new GZIPInputStream(bis);
			byte[] buffer = new byte[bytes.length + 1];
			int len = -1;
			while ((len = gis.read(buffer, 0, bytes.length + 1)) != -1) {
				bos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) try { bis.close(); } catch (IOException e) {}
			if (bos != null) try { bos.close(); } catch (IOException e) {}
		}

		return bos.toString(this._charSet);
	}

	///////////////////////////////////////////////////////////////////////////


	public byte[] _getPacketHeader() {
		byte[] packetHeader = {(byte)0x02, (byte)'R', (byte)'E', (byte)'U', (byte)'T' };
		return packetHeader;
	}
}
