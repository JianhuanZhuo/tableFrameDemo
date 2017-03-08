package cn.keepfight.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javafx.util.Pair;

public class HttpUtils {

	/**
	 * �� Get ������������ URL �� Session Cookie����Ȼ������� URL ����� Get ����
	 *
	 * @param url
	 *            Get ������Ŀ�� URL
	 * @return �� Get �Ľ���ַ���
	 * @throws Exception
	 *             �� Get �쳣
	 */
	public static String simpleGet(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			CloseableHttpResponse resp = httpClient.execute(post);
			try {
				return EntityUtils.toString(resp.getEntity());
			} finally {
				resp.close();
			}
		} finally {
			httpClient.close();
		}
	}

	public static String simpleGetWithEncode(String url, List<Pair<String, String>> params) throws Exception {
		String paramStr = "";
		for (Pair<String, String> p : params) {
			paramStr += "&"+p.getKey()+"="+URLEncoder.encode(p.getValue(), "utf-8");
		}
		return simpleGet(url+"?"+paramStr.substring(1));
	}

	public static String simplePost(String url, String param) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			// add Cookie JSESSIONID
			// post.addHeader("Cookie", "JSESSIONID=" + jseSession);
			StringEntity params =new StringEntity("yy="+param);
			post.addHeader("content-type", "application/x-www-form-urlencoded");
			post.setEntity(params);
			CloseableHttpResponse resp = httpClient.execute(post);
			try {
				return EntityUtils.toString(resp.getEntity());
			} finally {
				resp.close();
			}
		} finally {
			httpClient.close();
		}
	}

	public static String simplePostJSONWithUTG8(String url, String param) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			StringEntity params =new StringEntity(URLEncoder.encode(param, "utf-8"));
			post.addHeader("content-type", "application/x-www-form-urlencoded");
			post.setEntity(params);
			CloseableHttpResponse resp = httpClient.execute(post);
			try {
				return EntityUtils.toString(resp.getEntity());
			} finally {
				resp.close();
			}
		} finally {
			httpClient.close();
		}
	}
}
