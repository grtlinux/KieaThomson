package org.tain.kiea.thomson;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class FlatFragBean {

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
	private List<ByteBuffer> fragment;

	public FlatFragBean() {
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
		this.fragment = new ArrayList<ByteBuffer>();
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
	public List<ByteBuffer> getFragment() {
		return fragment;
	}
	public void setFragment(List<ByteBuffer> fragment) {
		this.fragment = fragment;
	}

	//////////////////////////////////////////////////////////

	public void addSize(Long size) {
		this.sizes.add(size);
	}

	public void addFragNum(String fragNum) {
		this.fragNum.add(fragNum);
	}

	public void addFragment(ByteBuffer fragment) {
		this.fragment.add(fragment);
	}

	//////////////////////////////////////////////////////////

	public boolean isTotalSize() {
		if (this.totalSize == 0)
			return false;

		if (this.sizes.size() == 0)
			return false;

		long sumSizes = 0;
		for (Long size : this.sizes) {
			sumSizes += size;
		}

		if (sumSizes == this.totalSize) {
			return true;
		} else {
			return false;
		}
	}
}
