package org.tain.kiea.thomson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.json.JSONObject;

import com.thomsonreuters.ema.access.AckMsg;
import com.thomsonreuters.ema.access.DataType;
import com.thomsonreuters.ema.access.DataType.DataTypes;
import com.thomsonreuters.ema.access.EmaUtility;
import com.thomsonreuters.ema.access.FieldEntry;
import com.thomsonreuters.ema.access.FieldList;
import com.thomsonreuters.ema.access.GenericMsg;
import com.thomsonreuters.ema.access.Map;
import com.thomsonreuters.ema.access.MapEntry;
import com.thomsonreuters.ema.access.Msg;
import com.thomsonreuters.ema.access.OmmConsumerClient;
import com.thomsonreuters.ema.access.OmmConsumerEvent;
import com.thomsonreuters.ema.access.RefreshMsg;
import com.thomsonreuters.ema.access.StatusMsg;
import com.thomsonreuters.ema.access.UpdateMsg;

@SuppressWarnings("unused")
public final class AppClient implements OmmConsumerClient {

	private static final boolean flag;

	static {
		flag = true;
	}

	///////////////////////////////////////////////////////////////////////////

	private static final int TIMACT_MS = 4148;
	private static final int ACTIV_DATE = 17;
	private static final int MRN_TYPE = 8593;
	private static final int MRN_V_MAJ = 8506;
	private static final int MRN_V_MIN = 11787;
	private static final int TOT_SIZE = 32480;
	private static final int FRAG_NUM = 32479;
	private static final int GUID = 4271;
	private static final int MRN_SRC = 12215;
	private static final int FRAGMENT = 32641;

	// store fragments for reassembly
	private static Hashtable<String, List<ByteBuffer>> fragBuilderHash = new Hashtable<String, List<ByteBuffer>>();
	// store total sizes
	private static Hashtable<String, Long> totalSizes = new Hashtable<String, Long>();

	// store fragments map Object
	private static Hashtable<String, FlatFragBean> mapFrag = new Hashtable<String, FlatFragBean>();

	private Boolean _udpEnable;
	private String _udpHost;
	private String _udpPort;

	public AppClient(Boolean _udpEnable, String _udpHost, String _udpPort) {
		this._udpEnable = _udpEnable;
		this._udpHost = _udpHost;
		this._udpPort = _udpPort;
	}

	@Override
	public void onStatusMsg(StatusMsg statusMsg, OmmConsumerEvent event) {
		if (flag) System.out.println("##### onStatusMsg ##### Closed");

		if (flag) System.out.println(">>>>> Item Name: " + (statusMsg.hasName() ? statusMsg.name() : "<not set>"));
		if (flag) System.out.println(">>>>> Service Name: " + (statusMsg.hasServiceName() ? statusMsg.serviceName() : "<not set>"));
		if (flag) System.out.println(">>>>> Item State: " + (statusMsg.hasState() ? statusMsg.state() : "<not set>"));

		if (flag) System.out.println();
	}

	@Override
	public void onRefreshMsg(RefreshMsg refreshMsg, OmmConsumerEvent event) {
		if (flag) System.out.println("##### onRefreshMsg ##### Open");

		if (flag) System.out.println(">>>>> Item name:" + (refreshMsg.hasName() ? refreshMsg.name() : "<not set>"));
		if (flag) System.out.println(">>>>> Service Name: " + (refreshMsg.hasServiceName() ? refreshMsg.serviceName() : "<not set>"));
		if (flag) System.out.println(">>>>> Item State: " + refreshMsg.state());

		if (refreshMsg.payload().dataType() == DataType.DataTypes.MAP) {
			decode(refreshMsg.payload().map());
		}

		if (flag) System.out.println();
	}

	@Override
	public void onUpdateMsg(UpdateMsg updateMsg, OmmConsumerEvent event) {
		if (flag) System.out.println("##### onUpdateMsg ##### Message");

		if (flag) System.out.println(">>>>> Item Name: " + (updateMsg.hasName() ? updateMsg.name() : "<not set>"));
		if (flag) System.out.println(">>>>> Service Name: " + (updateMsg.hasServiceName() ? updateMsg.serviceName() : "<not set>"));
		if (flag) System.out.println(">>>>> Closure: " + event.closure());

		if (updateMsg.payload().dataType() == DataType.DataTypes.MAP) {
			decode(updateMsg.payload().map());
		} else if (updateMsg.payload().dataType() == DataType.DataTypes.FIELD_LIST) {
			decode(updateMsg.payload().fieldList(), event.closure());
		}

		if (flag) System.out.println();
	}

	@Override
	public void onAckMsg(AckMsg ackMsg, OmmConsumerEvent event) {
		if (flag) System.out.println("##### onGenericMsg #####");
	}

	@Override
	public void onAllMsg(Msg msg, OmmConsumerEvent event) {
		if (flag) System.out.println("##### onAllMsg #####");
	}

	@Override
	public void onGenericMsg(GenericMsg genericMsg, OmmConsumerEvent event) {
		if (flag) System.out.println("##### onGenericMsg #####");
	}

	///////////////////////////////////////////////////////////////////////////

	private void decode(Map map) {
		if (flag) System.out.println(">>>>> Decode map: cnt = " + map.size());

		if (map.summaryData().dataType() == DataTypes.FIELD_LIST) {
			if (flag) System.out.println("Map Summary data: ");
			decode(map.summaryData().fieldList(), null);

			if (flag) System.out.println();
			//return;
		}

		Iterator<MapEntry> iter = map.iterator();
		MapEntry mapEntry;
		while (iter.hasNext()) {
			mapEntry = iter.next();

			if (mapEntry.key().dataType() == DataTypes.BUFFER) {
				System.out.println("Action: "
						+ mapEntry.mapActionAsString()
						+ " key value: "
						+ EmaUtility.asHexString(mapEntry.key().buffer().buffer()));
			}

			if (mapEntry.loadType() == DataTypes.FIELD_LIST) {
				if (flag) System.out.println("Entry data:");
				decode(mapEntry.fieldList(), null);

				if (flag) System.out.println();
			}
		}
	}

	private void decode(FieldList fieldList, Object closuer) {
		if (flag) System.out.println(">>>>> Decode fieldList: cnt = " + fieldList.size());

		Iterator<FieldEntry> iter = fieldList.iterator();
		FieldEntry fieldEntry;
		FlatFragBean bean = new FlatFragBean();

		while (iter.hasNext()) {
			fieldEntry = iter.next();
			if (!flag) System.out.printf("  FIELD_ENTRY: %d/%s/loadType(%d) %s%n"
					, fieldEntry.fieldId()
					, fieldEntry.name()
					, fieldEntry.loadType()
					, fieldEntry.load());

			switch (fieldEntry.fieldId()) {
			case TIMACT_MS:
				bean.setTimactMs(String.valueOf(fieldEntry.load()));
				break;
			case ACTIV_DATE:
				bean.setActivDate(String.valueOf(fieldEntry.load()));
				break;
			case MRN_TYPE:
				bean.setMrnType(String.valueOf(fieldEntry.load()));
				break;
			case MRN_V_MAJ:
				bean.setMrnVerMajor(String.valueOf(fieldEntry.load()));
				break;
			case MRN_V_MIN:
				bean.setMrnVerMinor(String.valueOf(fieldEntry.load()));
				break;
			case TOT_SIZE:
				bean.setTotalSize(Long.valueOf(String.valueOf(fieldEntry.load())));
				break;
			case GUID:
				bean.setGuid(String.valueOf(fieldEntry.load()));
				break;
			case MRN_SRC:
				bean.setMrnSrc(String.valueOf(fieldEntry.load()));
				break;
			case FRAG_NUM:
				bean.addFragNum(String.valueOf(fieldEntry.load()));
				break;
			case FRAGMENT:
				bean.addSize(new Long(fieldEntry.buffer().buffer().limit()));
				bean.addFragment(Arrays.copyOf(fieldEntry.buffer().buffer().array(), fieldEntry.buffer().buffer().limit()));
				break;
			default:
				if (flag) System.out.println("ERROR: WRONG MESSAGE......");
				return;
				//break;
			}
		}

		if (flag && bean.getTotalSize() == 0) {
			FlatFragBean org = mapFrag.get(bean.getGuid());
			org.addFragNum(bean.getFragNum().get(0));
			org.addSize(bean.getSizes().get(0));
			org.addFragment(bean.getFragment().get(0));
			bean = org;
		}

		mapFrag.put(bean.getGuid(), bean);


		if (flag) System.out.printf(">>>>> MESSAGE >>>>> mapFrag.size() = %d\n%s", mapFrag.size(), bean);

		if (!bean.isTotalSize())
			return;

		if (flag) {
			ByteArrayOutputStream bos = null;
			String strMsg = null;

			try {
				bos = new ByteArrayOutputStream();
				List<byte[]> lstFragment = bean.getFragment();
				for (byte[] fragment : lstFragment) {
					bos.write(fragment);
				}

				strMsg = unzipPayload(bos.toByteArray());
				if (flag) System.out.println("  => FRAGMENT JSON STRING: " + strMsg);

				// pretty-print json response
				JSONObject jsonResponse = new JSONObject(strMsg);
				int spacesToIndentEachLevel = 2;
				if (flag) System.out.println("  => FRAGMENT JSON PRETTY:\n" + jsonResponse.toString(spacesToIndentEachLevel));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) try { bos.close(); } catch (Exception e) {}
			}

			if (flag && _udpEnable) {
				// UDP send
				udpSend(strMsg);
			}
		}
	}

	private void decode_old(FieldList fieldList, Object closuer) {
		if (flag) System.out.println(">>>>> Decode fieldList: cnt = " + fieldList.size());

		Iterator<FieldEntry> iter = fieldList.iterator();
		FieldEntry fieldEntry;
		String guid = null;
		long totalSize = 0;

		while (iter.hasNext()) {
			fieldEntry = iter.next();
			if (flag) System.out.printf("  FIELD_ENTRY: %d/%s/loadType(%d) %s%n"
					, fieldEntry.fieldId()
					, fieldEntry.name()
					, fieldEntry.loadType()
					, fieldEntry.load());

			if (fieldEntry.loadType() == DataTypes.BUFFER) {
				if (fieldEntry.fieldId() == FRAGMENT) {
					if (flag) System.out.println("  => FRAGMENT JSON zipped SIZE: " + fieldEntry.buffer().buffer().limit());

					if (totalSize == 0) {
						// not a first segment
						totalSize = totalSizes.get(guid).longValue();
					}

					if (fieldEntry.buffer().buffer().limit() == totalSize) {
						// there is only one segment, we are ready unzip using gzip
						String strFlatFrag = unzipPayload(Arrays.copyOf(fieldEntry.buffer().buffer().array(), fieldEntry.buffer().buffer().limit()));
						if (flag) System.out.println("  => FRAGMENT JSON STRING: " + strFlatFrag);
						try {
							// pretty-print json response
							JSONObject jsonResponse = new JSONObject(strFlatFrag);
							int spacesToIndentEachLevel = 2;
							if (flag) System.out.println("  => FRAGMENT JSON PRETTY:\n" + jsonResponse.toString(spacesToIndentEachLevel));
						} catch (Exception e) {
							System.err.println("Exception parsing json: " + e);
							e.printStackTrace(System.err);
						}

						if (flag) udpSend(strFlatFrag);
					} else {
						List<ByteBuffer> alFrags = null;
						if (!fragBuilderHash.containsKey(guid)) {
							// no hash key
							alFrags = new ArrayList<ByteBuffer>();
							alFrags.add(fieldEntry.buffer().buffer());
							fragBuilderHash.put(guid, alFrags);
						} else {
							// exist hash key
							alFrags = fragBuilderHash.get(guid);
							alFrags.add(fieldEntry.buffer().buffer());

							int sizeNow = 0;
							for (int i=0; i < alFrags.size(); i++) {
								sizeNow += alFrags.get(i).limit();
							}

							if (sizeNow < totalSize) {
								fragBuilderHash.put(guid, alFrags);
							} else {
								// we are ready
								ByteArrayOutputStream bos = null;
								String strFlatFrag = null;

								try {
									bos = new ByteArrayOutputStream();
									for (int i=0; i < alFrags.size(); i++) {
										bos.write(Arrays.copyOf(alFrags.get(i).array(), alFrags.get(i).limit()));
									}
									strFlatFrag = unzipPayload(bos.toByteArray());
									if (flag) System.out.println("  => FRAGMENT JSON STRING: " + strFlatFrag);

									// pretty-print json response
									JSONObject jsonResponse = new JSONObject(strFlatFrag);
									int spacesToIndentEachLevel = 2;
									if (flag) System.out.println("  => FRAGMENT JSON PRETTY:\n" + jsonResponse.toString(spacesToIndentEachLevel));
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									if (bos != null) try { bos.close(); } catch (Exception e) {}
								}

								if (flag) udpSend(strFlatFrag);
							}
						}
					}
				}
			} else if (fieldEntry.fieldId() == TOT_SIZE) {
				totalSize = fieldEntry.uintValue();
			} else if (fieldEntry.fieldId() == GUID) {
				guid = fieldEntry.rmtes().toString();
				if (totalSize != 0)
					totalSizes.put(guid, new Long(totalSize));
			}
		}
	}

	private String unzipPayload(byte[] bytes) {
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

		return bos.toString();
	}

	///////////////////////////////////////////////////////////////////////////

	private void udpSend(String msg) {
		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] buffer = msg.getBytes();
		try {
			socket = new DatagramSocket();
			packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(_udpHost), Integer.parseInt(_udpPort));
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) try { socket.close(); } catch (Exception e) {}
		}

		if (flag) System.out.printf(">>>>> udpSend%n");
	}
}
