package cn.keepfight.frame.table.operator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.HttpUtils;
import javafx.scene.control.TextInputDialog;
import javafx.util.Pair;
import net.sf.json.JSONObject;

public class MergeColumnOperator extends AbstractOperator{

	TableTStage tStage;


	private String[] inputResource;
	private String[] outputResource;
	private String[] params;


	public MergeColumnOperator(TableTStage tStage) {
		this.tStage = tStage;
	}

	@Override
	public int getId() {
		return 333;
	}

	@Override public String getName() {return "merge-column";}

	@Override
	public String getLabel() {
		return "�кϲ�";
	}

	@Override
	public String getIcon() {
		return "merge-column.png";
	}

	@Override
	public String getTips() {
		return "ָ���н��н��кϲ�";
	}

	@Override
	public String getDescription() {
		return "��ʱ�����ӽ���";
	}

	@Override
	public List<Resource> onAction() {

		List<String> resList = tStage.getPaneVC().columnSelectUtil.selectColumn(
				this,
				t -> new HashSet<>(t).size()>0?null:"���кϲ�����������Ϊ0",
				"��ѡ����Ҫ�����кϲ����У�");
		if (resList==null) { return null; }

		//�������������
		TextInputDialog dialog = new TextInputDialog("walter");
		dialog.setTitle("�кϲ�");
		dialog.setHeaderText("��Ϊ����ѡ������");
		dialog.setContentText("��������:");

		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent()){
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
		try {
			res = HttpUtils.simpleGetWithEncode(url, paramPairs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject resx = JSONObject.fromObject(res);
		System.out.println(resx.getBoolean("flag"));
		String viewName = resx.getString("view");

		Resource resultResource = new TableResource("wz", viewName);
		List<Resource> resResources = new ArrayList<>();
		resResources.add(resultResource);
	    params = resList.toArray(new String[resList.size()]);
		inputResource = new String[1];
		inputResource[0] = tStage.getSource().getSourceIDName();
		outputResource = new String[1];
		outputResource[0] = resultResource.getName();
		return resResources;
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
