package cn.keepfight.frame.table.operator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.HttpUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Pair;
import net.sf.json.JSONObject;

/**
 * 未完成
 * @author Tom
 *
 */
public class DeleteColumnOperator extends AbstractOperator{

	TableTStage tStage;

	private String[] inputResource;
	private String[] outputResource;
	private String[] params;


	public DeleteColumnOperator(TableTStage tStage) {
		this.tStage = tStage;
	}

	@Override
	public int getId() {
		return 333;
	}

	@Override public String getName() {return "insert-column";}

	@Override
	public String getLabel() {
		return "插入列";
	}

	@Override
	public String getIcon() {
		return "insert-column.png";
	}

	@Override
	public String getTips() {
		return "指定列进行进行合并";
	}

	@Override
	public String getDescription() {
		return "暂时无算子解释";
	}

	@Override
	public ActionResult onAction() throws Exception {

		List<String> resList = tStage.getPaneVC().columnSelectUtil.selectColumn(
				this,
				t -> new HashSet<>(t).size()>0?null:"进行合并的列数不得为0",
				"请选择需要进行列合并的列！");
		if (resList==null) { return null; }

		//获得输入新列名
		TextInputDialog dialog = new TextInputDialog("walter");
		dialog.setTitle("列合并");
		dialog.setHeaderText("请为新列选择列名");
		dialog.setContentText("列名输入:");

		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent() || !Pattern.compile("[A-Za-z][A-Za-z1-9_-]+").matcher(result.get()).matches()){
			new Alert(AlertType.ERROR, "列名无效", ButtonType.OK).show();
			return null;
		}
		String sqlList = "";
		for (String column : tStage.getPaneVC().getLoader().allColumns()) {
			if (resList.contains(column)) {
				continue;
			}
			sqlList += column+", ";
		}
		String catList = "";
		for (String str : resList) {
			catList += ", "+str;
		}

		sqlList += "CONCAT("+catList.substring(1)+") as "+result.get();

		String sqlString = "select "+sqlList+" from "+tStage.getSource().getEntityName();

		List<Pair<String, String>> paramPairs = new ArrayList<Pair<String,String>>();
		paramPairs.add(new Pair<String, String>("db", "wz"));
		paramPairs.add(new Pair<String, String>("sql", sqlString));
		String url = "http://127.0.0.1:8080/dap/dataLoad/createView.htm";
		System.out.println(url);
		String res = "";
		res = HttpUtils.simpleGetWithEncode(url, paramPairs);
		JSONObject resx = JSONObject.fromObject(res);
		System.out.println(resx.getBoolean("flag"));
		String viewName = resx.getString("view");

		Resource resultResource = new TableResource("wz", viewName);
		List<Resource> resResources = new ArrayList<>();
		resResources.add(resultResource);
		/**
		 * @FIXME 这种做法是不规范的，需要进行一个规范抽象
		 */
		resList.add(result.get());
	    params = resList.toArray(new String[resList.size()]);
		inputResource = new String[1];
		inputResource[0] = tStage.getSource().getSourceIDName();
		outputResource = new String[1];
		outputResource[0] = resultResource.getName();
		return new ActionResult(resResources);
	}



	@Override
	public OperatorResource generateResource() {
		OperatorResource res = super.generateResource();
		res.setInputResource(inputResource);
		res.setOutputResource(outputResource);
		res.setParams(params);
		return res;
	}
}
