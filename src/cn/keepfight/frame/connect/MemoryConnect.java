package cn.keepfight.frame.connect;

import java.io.IOException;

import net.sf.json.JSONObject;

/**
 * 本地连接服务，用于测试
 * @author Tom
 *
 */
public class MemoryConnect implements Connectable{

	@Override
	public void open() throws IOException {
	}

	@Override
	public String simpleSwitch(String whisper) {
		// TODO Auto-generated method stub
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
