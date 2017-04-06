package cn.keepfight.frame.connect;

import java.io.IOException;

import net.sf.json.JSONObject;

/**
 * 网络连接服务
 * @author Tom
 *
 */
public class WebConnect implements Connectable{

	@Override
	public void open() throws IOException {
	}

	@Override
	public String simpleSwitch(String whisper) {
		return null;
	}

	@Override
	public JSONObject simpleGet(String url, String paramList) {
		return null;
	}

	@Override
	public void close() throws IOException {
	}

}
