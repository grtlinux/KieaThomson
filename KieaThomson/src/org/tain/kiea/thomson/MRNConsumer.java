package org.tain.kiea.thomson;

import org.tain.utils.ClassUtils;
import org.tain.utils.ResourcesUtils;

import com.thomsonreuters.ema.access.EmaFactory;
import com.thomsonreuters.ema.access.OmmConsumer;
import com.thomsonreuters.ema.access.OmmConsumerConfig;
import com.thomsonreuters.ema.access.OmmException;
import com.thomsonreuters.ema.access.ReqMsg;
import com.thomsonreuters.ema.rdm.EmaRdm;

public final class MRNConsumer {

	private static final boolean flag;

	//private static String _ip;
	//private static String _port;
	private static String _serviceName;
	private static String _userName;

	private static String _udpEnable;
	private static String _udpHost;
	private static String _udpPort;
	private static String _charSet;

	private static String[] _ricsMRN = { "MRN_HDLN", "MRN_STORY", "MRN_TRSI", "MRN_TRNA" };

	private static AppClient _appClient;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	public MRNConsumer(String[] args) throws Exception {
		if (flag) System.out.println(">>>>> " + ClassUtils.getClassInfo());

		new ConnectInfo();

		//_ip = ResourcesUtils.getString("org.tain.kiea.thomson.ip");
		//_port = ResourcesUtils.getString("org.tain.kiea.thomson.port");
		_serviceName = ResourcesUtils.getString("org.tain.kiea.thomson.serviceName");
		_userName = ResourcesUtils.getString("org.tain.kiea.thomson.userName");

		_udpEnable = ResourcesUtils.getString("org.tain.kiea.thomson.udp.enable");
		_udpHost = ResourcesUtils.getString("org.tain.kiea.thomson.udp.host");
		_udpPort = ResourcesUtils.getString("org.tain.kiea.thomson.udp.port");
		_charSet = ResourcesUtils.getString("org.tain.kiea.thomson.charSet");

		if (flag) {
			//System.out.printf(">>> %s: _ip          = '%s'%n", ClassUtils.getClassName(), _ip);
			//System.out.printf(">>> %s: _port        = '%s'%n", ClassUtils.getClassName(), _port);
			System.out.printf(">>> %s: _serviceName = '%s'%n", ClassUtils.getClassName(), _serviceName);
			System.out.printf(">>> %s: _userName    = '%s'%n", ClassUtils.getClassName(), _userName);
			System.out.printf(">>> %s: _udpEnable   = '%s'%n", ClassUtils.getClassName(), _udpEnable);
			System.out.printf(">>> %s: _udpHost     = '%s'%n", ClassUtils.getClassName(), _udpHost);
			System.out.printf(">>> %s: _udpPort     = '%s'%n", ClassUtils.getClassName(), _udpPort);
			System.out.printf(">>> %s: _charSet     = '%s'%n", ClassUtils.getClassName(), _charSet);
		}

		if (flag) {
			if (Boolean.valueOf(_udpEnable)) {
				System.out.println(">>> INFO: udp send is OK!!");
			} else {
				System.out.println(">>> INFO: udp send is not doing..");
			}
		}

		for (String connInfo : ConnectInfo.getListConnectInfo()) {
			System.out.printf(">>>>> TRY_CONNECTION: trying to connect to the service [%s]%n", connInfo);

			try {
				_appClient = new AppClient(Boolean.valueOf(_udpEnable), _udpHost, _udpPort);

				OmmConsumerConfig config = EmaFactory.createOmmConsumerConfig();
				// OmmConsumer consumer = EmaFactory.createOmmConsumer(config.host(_ip + ":" + _port).username(_userName));
				OmmConsumer consumer = EmaFactory.createOmmConsumer(config.host(connInfo).username(_userName));
				ReqMsg reqMsg = EmaFactory.createReqMsg();

				for (int i=0; i < _ricsMRN.length; i++) {
					consumer.registerClient(reqMsg
							.domainType(EmaRdm.MMT_NEWS_TEXT_ANALYTICS)
							.serviceName(_serviceName)
							.name(_ricsMRN[i])
							, _appClient
							, new Integer(i));
				}

				System.out.printf(">>>>> TRY_CONNECTION: success to connect to the service [%s]%n", connInfo);
				break;
			} catch (OmmException e) {
				System.out.printf(">>>>> TRY_CONNECTION: failed to connect to the service [%s], and try again!!!%n", connInfo);
				e.printStackTrace();
				continue;
			}
		}
	}
}
