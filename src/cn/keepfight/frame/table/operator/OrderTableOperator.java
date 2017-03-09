package cn.keepfight.frame.table.operator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.table.TablePaneController;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.HttpUtils;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Pair;
import net.sf.json.JSONObject;

public class OrderTableOperator extends AbstractOperator{

	TableTStage tStage;


	private String[] inputResource;
	private String[] outputResource;
	private String[] params;


	public OrderTableOperator(TableTStage tStage) {
		this.tStage = tStage;
	}

	@Override
	public int getId() {
		return 332;
	}

	@Override public String getName() {return "order";}

	@Override
	public String getLabel() {
		return "排序";
	}

	@Override
	public String getIcon() {
		return "sequence-v-a-z.png";
	}

	@Override
	public String getTips() {
		return "使用指定单行或多行进行排序";
	}

	@Override
	public String getDescription() {
		return "暂时无算子解释";
	}

	@Override
	public List<Resource> onAction() throws Exception{

		TablePaneController tPaneController = tStage.getPaneVC();
		List<String> selectList = tPaneController.getTableSelect().getColumnStrings();
		List<String> allList = tPaneController.getLoader().allColumns();

		List<String> orderList = showDialog(allList, selectList);
		if (orderList.size()==0) {
			return null;
		}
		if (new HashSet<>(orderList).size()!=orderList.size()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("操作错误！");
			alert.setContentText("排序中不可使用相同列进行多个优先级的排序！");
			alert.show();
			return null;
		}

		String sqlString = "select * from "+tStage.getSource().getEntityName()+" order by ";
		String orderListStr = "";
		for (String str : orderList) {
			orderListStr += ", "+str;
		}
		List<Pair<String, String>> paramPairs = new ArrayList<Pair<String,String>>();
		paramPairs.add(new Pair<String, String>("db", "wz"));
		paramPairs.add(new Pair<String, String>("sql", sqlString+orderListStr.substring(1)));
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
	    params = orderList.toArray(new String[orderList.size()]);
		inputResource = new String[1];
		inputResource[0] = tStage.getSource().getSourceIDName();
		outputResource = new String[1];
		outputResource[0] = resultResource.getName();
		return resResources;
	}


	private List<String> showDialog(List<String> allColumns, List<String> selectColumns){
		// Create the custom dialog.
		Dialog<List<String>> dialog = new Dialog<>();
		dialog.setTitle("选择排序方式");
		dialog.setHeaderText("请选择排序方式，系统将会根据您选择的排序字段的优先顺序进行排序");

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(ImageLoadUtil.load(getIcon(), ImageLoadUtil.IMG_SIZE_64)));

		// Set the button types.
		ButtonType loginButtonOK = new ButtonType("确定", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(loginButtonOK, buttonTypeCancel);

		//
		ObservableList<String> options =
			    FXCollections.observableArrayList(allColumns);
		HBox hBox = new HBox(10);
		for (String c : selectColumns) {
			ComboBox<String> box = new ComboBox<>(options);
			box.getSelectionModel().select(c);
			Button del = new Button("-");
			hBox.getChildren().add(new HBox(5, box, del));
		}

		dialog.getDialogPane().setContent(hBox);

		dialog.setResultConverter(new Callback<ButtonType, List<String>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<String> call(ButtonType param) {
				List<String> reStrings = new ArrayList<>();
				if (param==loginButtonOK) {
					for (int i = 0; i < hBox.getChildren().size(); i++) {
						ComboBox<String> box = (ComboBox<String>) ((HBox)hBox.getChildren().get(i)).getChildren().get(0);
						reStrings.add(box.getSelectionModel().getSelectedItem());
					}
				}
				return reStrings;
			}
		});



		List<String> resList = new ArrayList<String>();
		Optional<List<String>> optionals =  dialog.showAndWait();
		if (optionals.isPresent()) {
			resList = optionals.get();
		}
		return resList;
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
