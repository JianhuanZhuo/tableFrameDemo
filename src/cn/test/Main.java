package cn.test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.keepfight.utils.HttpUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import net.sf.json.JSONObject;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//http://localhost:8080/dap/dataLoad/getRule.htm
		JSONObject resx;
		{
			String url = "http://127.0.0.1:8080/dap/dataLoad/getRule.htm";
			String res = HttpUtils.simpleGet(url);
			System.out.println(res.length());
			System.out.println(res);
			resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
		}

		{
			String url = "http://127.0.0.1:8080/dap/dataLoad/getCloum.htm?db=wz&tableName=��������";
			String res = HttpUtils.simpleGet(url);
			System.out.println(res.length());
			System.out.println(res);
			resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
		}

		{
			String url = "http://127.0.0.1:8080/dap/dataLoad/getTableContent.htm?table=��������&start=0&limit=10";
			String res = HttpUtils.simpleGet(url);
			System.out.println(res.length());
			System.out.println(res);
			resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
		}

		{
			String url = "http://127.0.0.1:8080/dap/dataLoad/createView.htm?db=wz&sql="+URLEncoder.encode("select * from �������� order by column3", "utf-8");
			System.out.println(url);
			String res = HttpUtils.simpleGet(url);
			System.out.println(res.length());
			System.out.println(res);
			resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
		}


		{
			String url = "http://127.0.0.1:8080/dap/dataLoad/saveAs.htm?db=wz&name="+URLEncoder.encode("��������", "utf-8");
			System.out.println(url);
			String res = HttpUtils.simpleGet(url);
			System.out.println(res.length());
			System.out.println(res);
			resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
		}

		{
			List<Pair<String, String>> paramPairs = new ArrayList<Pair<String,String>>();
			paramPairs.add(new Pair<String, String>("db", "wz"));
			paramPairs.add(new Pair<String, String>("name", "��������"));
			String url = "http://127.0.0.1:8080/dap/dataLoad/saveAs.htm";
			System.out.println(url);
			String res = HttpUtils.simpleGetWithEncode(url, paramPairs);
			System.out.println(res.length());
			System.out.println(res);
			resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
		}

//		{
//			String url = "http://127.0.0.1:8080/dap/dataLoad/test2.htm";
//			String res = HttpUtils.simplePost(url, URLEncoder.encode(resx.toString(), "utf-8"));
//			System.out.println(res.length());
//			System.out.println(res);
//			JSONObject json = JSONObject.fromObject(res);
//			System.out.println(json.getBoolean("flag"));
//		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
