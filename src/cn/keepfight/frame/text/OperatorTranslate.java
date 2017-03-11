package cn.keepfight.frame.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.operator.WaitDialog;
import cn.keepfight.utils.HttpUtils;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceDialog;
import javafx.util.Pair;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OperatorTranslate extends AbstractOperator{

	private int id = 321;//算子ID
	private String name = "translate";//算子名
	private String label = "表转换";//算子图标按钮上的名字
	private String tips = "使用转换表算子将该文本转换为表资源存储";//算子提示信息
	private String icon = "translate.png";//算子图标
	private String description = "有两种方法可从文本文件导入数据，"
			+ "使用 Microsoft Excel︰ 您可以在 Excel 中打开的文本文件，"
			+ "或您可以导入的文本文件作为外部数据区域。若要将数据从 Excel 导出到文本文件，"
			+ "使用另存为命令。";//算子描述信息

	private String[] inputResource;
	private String[] outputResource;
	private String[] params;

	private TextTStage tStage;

	public OperatorTranslate(TextTStage tStage) {
		this.tStage = tStage;
	}


	@Override public int getId() { return id; }
	@Override public String getName() { return name; }
	@Override public String getLabel() { return label; }
	@Override public String getIcon() { return icon; }
	@Override public String getTips() { return tips; }
	@Override public String getDescription() { return description; }

	@Override
	public ActionResult onAction() {
		List<Resource> res = new ArrayList<Resource>();

		List<String> choices = new ArrayList<>();
		choices.add("逗号");
		choices.add("制表符");
		choices.add("空格符");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("选择分隔符", choices);
		dialog.setTitle("文本文件转存为表");
		dialog.setHeaderText("请为该算子选择一个分割符号：");
		dialog.setContentText("当前开发进度可用的符号为：");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Your choice: " + result.get());

		    BufferedReader reader = new BufferedReader(tStage.getSource().getReader());
		    String line;
		    JSONArray json = new JSONArray();
			try {
				line = reader.readLine();
			    while (line!=null && line.trim().length()!=0) {
			    	String reg = "";
			    	switch (result.get()) {
					case "逗号":
						reg=",";
						break;
					case "制表符":
						reg="\t";
						break;
					case "空格符":
						reg=" ";
						break;
					default:
						break;
					}
			    	JSONArray lineObj = new JSONArray();
			    	String[] dataStrings = line.split(reg);
			    	for (int i = 0; i < dataStrings.length; i++) {
						String s = dataStrings[i];
						lineObj.add(s);
					}
			    	json.add(lineObj);
			    	line = reader.readLine();
				}
			    reader.close();
			    System.out.println(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String resObj = new WaitDialog<String>(new Task<String>() {
				@Override
				protected String call() throws Exception {
					String url = "http://127.0.0.1:8080/dap/dataLoad/test2.htm";
					return HttpUtils.simplePostJSONWithUTG8(url, new Pair<String, String>("yy", json.toString()));
				}
			}).justWait();

			if (resObj==null) {
				return null;
			}

			JSONObject resJsonObject;
			try {
				System.out.println(resObj);
				resJsonObject = JSONObject.fromObject(resObj);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			TableResource resultResource = new TableResource("wz", resJsonObject.getString("tableName"));
			res.add(resultResource);

		    params = new String[1];
		    params[0] = "spilt="+result.get();
			inputResource = new String[1];
			inputResource[0]=tStage.getSource().getSourceIDName();
			outputResource = new String[1];
			outputResource[0]=resultResource.getName();

			return new ActionResult(res);
		}

		return null;
	}

	public boolean justWait() {

		Task<List<String>> download = new Task<List<String>>() {

			@Override
			protected List<String> call() throws Exception {

				return null;
			}
		};

		download.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {


			}
		});

		return true;
	}


	@Override
	public OperatorResource generateResource() {
		OperatorResource newResource = new OperatorResource(id, name);
		newResource.setIcon(icon);
		newResource.setDescription(description);
		newResource.setLabel(label);
		newResource.setInputResource(inputResource);
		newResource.setOutputResource(outputResource);
		newResource.setParams(params);
		return newResource;
	}
}
