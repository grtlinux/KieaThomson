package org.tain.kiea.thomson;

import com.thomsonreuters.ema.access.AckMsg;
import com.thomsonreuters.ema.access.GenericMsg;
import com.thomsonreuters.ema.access.Msg;
import com.thomsonreuters.ema.access.OmmConsumerClient;
import com.thomsonreuters.ema.access.OmmConsumerEvent;
import com.thomsonreuters.ema.access.RefreshMsg;
import com.thomsonreuters.ema.access.StatusMsg;
import com.thomsonreuters.ema.access.UpdateMsg;

public final class AppClient implements OmmConsumerClient {

	private static final boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	private Boolean _udpEnable;
	private String _udpHost;
	private String _udpPort;

	public AppClient(Boolean _udpEnable, String _udpHost, String _udpPort) {
		this._udpEnable = _udpEnable;
		this._udpHost = _udpHost;
		this._udpPort = _udpPort;
	}

	@Override
	public void onStatusMsg(StatusMsg arg0, OmmConsumerEvent arg1) {
		if (flag) System.out.println("##### onStatusMsg ##### Closed");
	}

	@Override
	public void onRefreshMsg(RefreshMsg arg0, OmmConsumerEvent arg1) {
		if (flag) System.out.println("##### onRefreshMsg ##### Open");
	}

	@Override
	public void onUpdateMsg(UpdateMsg arg0, OmmConsumerEvent arg1) {
		if (flag) System.out.println("##### onUpdateMsg ##### Message");
	}

	@Override
	public void onAckMsg(AckMsg arg0, OmmConsumerEvent arg1) {
		if (flag) System.out.println("##### onGenericMsg #####");
	}

	@Override
	public void onAllMsg(Msg arg0, OmmConsumerEvent arg1) {
		if (flag) System.out.println("##### onAllMsg #####");
	}

	@Override
	public void onGenericMsg(GenericMsg arg0, OmmConsumerEvent arg1) {
		if (flag) System.out.println("##### onGenericMsg #####");
	}

	///////////////////////////////////////////////////////////////////////////
}
