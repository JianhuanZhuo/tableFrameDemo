package cn.keepfight.frame.table.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.HttpUtils;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import net.sf.json.JSONObject;

public class QueryOperator extends AbstractOperator{

	TableTStage tStage;

	private String[] inputResource;
	private String[] outputResource;
	private String[] params;


	public QueryOperator(TableTStage tStage) {
		this.tStage = tStage;
	}

	@Override public int getId() { return 333; }
	@Override public String getName() {return "query";}
	@Override public String getLabel() { return "行筛选";}
	@Override public String getIcon() { return "query.png"; }
	@Override public String getTips() { return "指定行条件进行筛选"; }
	@Override public String getDescription() { return "指定行条件进行筛选，这个算子没有太多详细的说明"; }

	class QueryPackage{
		List<Pair<String, String>> eqList = new ArrayList<>();
		List<Pair<String, String>> uneqList = new ArrayList<>();

		public QueryPackage addeq(String field, String condition) {
			eqList.add(new Pair<String, String>(field, condition));
			return this;
		}
		public QueryPackage adduneq(String field, String condition) {
			uneqList.add(new Pair<String, String>(field, condition));
			return this;
		}

		public String[] getParams() {
//			List<String> paramsList = eqList.stream()
//					.map(p->(p.getKey()+" = "+p.getValue()))
//					.collect(Collectors.toList());
//			paramsList.addAll(
//					uneqList.stream()
//					.map(p->(p.getKey()+" != "+p.getValue()))
//					.collect(Collectors.toList())
//					);
//			return paramsList.toArray(new String[paramsList.size()]);
			List<String> paramsList = Stream.concat(
					eqList.stream()
						.map(p->(p.getKey()+" = "+p.getValue()))
					,
					uneqList.stream()
						.map(p->(p.getKey()+" != "+p.getValue()))
					).collect(Collectors.toList());
			return paramsList.toArray(new String[paramsList.size()]);
		}

		public String generateSQL(String entity){
			if (eqList.size()+uneqList.size()==0) {
				return null;
			}

			List<String> conditionList = eqList.stream()
					.map(p->("("+p.getKey()+"='"+p.getValue()+"')"))
					.collect(Collectors.toList());
			conditionList.addAll(
				uneqList.stream()
				.map(p->("("+p.getKey()+"!='"+p.getValue()+"')"))
				.collect(Collectors.toList())
			);

			return "select * from "+entity+" where ("+
				conditionList.stream().collect(Collectors.joining(" and "))
				+")";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionResult onAction() throws Exception {

		List<String> allColumns = tStage.getPaneVC().getLoader().allColumns();

		Dialog<QueryPackage> dialog = new Dialog<>();
		dialog.setTitle(getLabel());
		dialog.setHeaderText("请选择筛选条件，可以删除或添加筛选条件，系统将筛选出符合全部条件的行");

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(ImageLoadUtil.load(getIcon(), ImageLoadUtil.IMG_SIZE_64)));

		// Set the button types.
		ButtonType loginButtonOK = new ButtonType("确定", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonOK, buttonTypeCancel);

		EventHandler<ActionEvent> handleOnDel = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				HBox box = (HBox) ((Button)event.getSource()).getParent();
				VBox waigong = (VBox) box.getParent();
				waigong.getChildren().remove(box);
				dialog.getDialogPane().getScene().getWindow().sizeToScene();
			}
		};

		ObservableList<String> options =
			    FXCollections.observableArrayList(allColumns);
		ObservableList<String> queryOptions =
			    FXCollections.observableArrayList("等于", "不等于");

		HBox cont = new HBox(10);

		Button addNew = new Button("添加排序字段");
		VBox hBox = new VBox(6);

		ComboBox<String> fieldSelectComboBox = new ComboBox<>(options);
		ComboBox<String> queryComboBox = new ComboBox<>(queryOptions);
		queryComboBox.getSelectionModel().select(0);
		Button del = new Button("-");
		TextField inputField = new TextField();
		inputField.setPromptText("输入条件");
		del.setOnAction(handleOnDel);
		hBox.getChildren().add(new HBox(8, fieldSelectComboBox, queryComboBox, inputField, del));

		addNew.setOnAction(e->{
			ComboBox<String> boxNew = new ComboBox<>(options);
			ComboBox<String> queryComboBoxNew = new ComboBox<>(queryOptions);
			queryComboBoxNew.getSelectionModel().select(0);
			Button delNew = new Button("-");
			del.setOnAction(handleOnDel);
			TextField inputFieldNew = new TextField();
			inputField.setPromptText("输入条件");
			hBox.getChildren().add(new HBox(8, boxNew, queryComboBoxNew, inputFieldNew, delNew));
			dialog.getDialogPane().getScene().getWindow().sizeToScene();
		});

		cont.getChildren().addAll(addNew, new Separator(), hBox);

		dialog.getDialogPane().setContent(cont);

		dialog.setResultConverter(type->{
			if (type==loginButtonOK) {
				QueryPackage pack = new QueryPackage();
				for (Node node : hBox.getChildren()) {
					String key =((ComboBox<String>)((HBox)node).getChildren().get(0)).getSelectionModel().getSelectedItem();
					String value =((TextField)((HBox)node).getChildren().get(2)).getText();
					if (((ComboBox<String>)((HBox)node).getChildren().get(1)).getSelectionModel().getSelectedItem().equals("等于")) {
						pack.addeq(key, value);
					}else {
						pack.adduneq(key, value);
					}
				}
				return pack;
			}
			return null;
		});

		Optional<QueryPackage> optionals =  dialog.showAndWait();
		if (!optionals.isPresent()) {
			return null;
		}

		QueryPackage pack = optionals.get();
		String sqlString = pack.generateSQL(tStage.getSource().getEntityName());

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
	    params = pack.getParams();
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
