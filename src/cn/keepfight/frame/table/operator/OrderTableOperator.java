package cn.keepfight.frame.table.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.frame.table.TablePaneController;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.HttpUtils;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	public ActionResult onAction() throws Exception{

		TablePaneController tPaneController = tStage.getPaneVC();
		List<String> selectList = tPaneController.getTableSelect().getColumnStrings();
		List<String> allList = tPaneController.getLoader().allColumns();

		SelectPackage order = showDialog(allList, selectList);


		List<Pair<String, String>> paramPairs = new ArrayList<Pair<String,String>>();
		paramPairs.add(new Pair<String, String>("db", "wz"));
		paramPairs.add(new Pair<String, String>("sql", order.generateSQL(tStage.getSource().getEntityName())));
		String url = "http://127.0.0.1:8080/dap/dataLoad/createView.htm";
		System.out.println(url);
		System.out.println("param: "+order.generateSQL(tStage.getSource().getEntityName()));
		String res = "";
		res = HttpUtils.simpleGetWithEncode(url, paramPairs);


		JSONObject resx = JSONObject.fromObject(res);
		System.out.println(resx.getBoolean("flag"));
		String viewName = resx.getString("view");

		Resource resultResource = new TableResource("wz", viewName);
		List<Resource> resResources = new ArrayList<>();
		resResources.add(resultResource);
	    params = order.generateParams();
		inputResource = new String[1];
		inputResource[0] = tStage.getSource().getSourceIDName();
		outputResource = new String[1];
		outputResource[0] = resultResource.getName();
		return new ActionResult(resResources);
	}

	enum OrderSC{
		ASC("ASC"),
		DESC("DESC");
		private String str;
		private OrderSC(String str) {this.str=str;}
		public String getStr(){return str;}
	}

	/**
	 * 使用建造者模式
	 * @author Tom
	 *
	 */
	class SelectPackage{
		Set<String> selectField = new HashSet<>();
		Set<String> orderField = new HashSet<>();
		OrderSC sc = OrderSC.ASC;
		boolean limit = false;
		int offset = 0;
		int count = 0;

		public SelectPackage addSelectField(String f){
			selectField.add(f);
			return this;
		}

		public SelectPackage addOrderField(String f){
			orderField.add(f);
			return this;
		}

		public SelectPackage setSC(OrderSC sc) {
			this.sc=sc;
			return this;
		}

		public SelectPackage setLimit(int offset, int count){
			limit = true;
			this.offset = offset;
			this.count = count;
			return this;
		}

		public String generateSQL(String entity) {
			String sql = "";
			if (selectField.isEmpty()) {
				sql = "select * from "+entity+" ";
			}else {
				StringJoiner strJoiner = new StringJoiner(",");
				selectField.stream().filter(str->(str!=null&&str.trim().length()!=0))
				.forEach(str->strJoiner.add(str));
				sql+= "select "+strJoiner.toString()+" from "+entity+" ";
			}
			if (!orderField.isEmpty()) {
				StringJoiner strJoiner = new StringJoiner(",");
				orderField.stream().filter(str->(str!=null&&str.trim().length()!=0))
				.peek(s->{System.out.println("xxx:"+s);})
				.forEach(str->strJoiner.add(str));
				sql+= " order by "+strJoiner.toString()+" "+sc.getStr();
			}
			if (limit) {
				sql+= " limit "+offset+", "+count;
			}
			return sql;
		}

		public String[] generateParams() {
			List<String> res = new ArrayList<>();
			if (!selectField.isEmpty()) {
				selectField.stream().map(s->"显示字段-"+s).forEach(e->res.add(e));
			}
			if (!orderField.isEmpty()) {
				orderField.stream().map(s->"排序字段-"+s).forEach(e->res.add(e));
			}
			if (limit) {
				res.add("起始排名-"+offset);
				res.add("计数总数-"+count);
			}
			return res.toArray(new String[res.size()]);
		}
	}

	private SelectPackage showDialog(List<String> allColumns, List<String> selectColumns){
		// Create the custom dialog.
		Dialog<SelectPackage> dialog = new Dialog<>();
		dialog.setTitle("选择排序方式");
		dialog.setHeaderText("请选择排序方式，系统将会根据您选择的排序字段的优先顺序进行排序");

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
		VBox cont = new VBox(10);
		ComboBox<String> order = new ComboBox<>(FXCollections.observableArrayList(Arrays.asList("升序", "降序")));
		order.getSelectionModel().select(0);
		CheckBox onlySelectedFieldBox = new CheckBox("仅显示指定字段");
		Button addNewSelectBtn = new Button("添加显示字段");

		VBox selectVisVBox = new VBox(10);
		for (String c : allColumns) {
			ComboBox<String> box = new ComboBox<>(options);
			box.getSelectionModel().select(c);
			Button del = new Button("-");
			del.setOnAction(handleOnDel);
			selectVisVBox.getChildren().add(new HBox(5, box, del));
		}

		addNewSelectBtn.setOnAction(e->{
			ComboBox<String> box = new ComboBox<>(options);
			Button del = new Button("-");
			del.setOnAction(handleOnDel);
			selectVisVBox.getChildren().add(new HBox(5, box, del));
			dialog.getDialogPane().getScene().getWindow().sizeToScene();
		});

		VBox viField = new VBox(new Separator(), new HBox(addNewSelectBtn, selectVisVBox));
		HBox functionHBox = new HBox(8, new Label("指定结果排序方式（正序或反序）："), order, new Separator(Orientation.VERTICAL), onlySelectedFieldBox);
		cont.getChildren().add(functionHBox);
		onlySelectedFieldBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int index = cont.getChildren().indexOf(functionHBox);
				try {
					if (((CheckBox)event.getSource()).isSelected()) {
						cont.getChildren().add(index+1, viField);
					}else {
						cont.getChildren().remove(viField);
					}
					dialog.getDialogPane().getScene().getWindow().sizeToScene();
				} catch (Exception e) {
				}
			}
		});
		cont.getChildren().add(new Separator());
		Button addNew = new Button("添加排序字段");
		VBox hBox = new VBox(10);
		for (String c : selectColumns) {
			ComboBox<String> box = new ComboBox<>(options);
			box.getSelectionModel().select(c);
			Button del = new Button("-");
			del.setOnAction(handleOnDel);
			hBox.getChildren().add(new HBox(5, box, del));
		}

		addNew.setOnAction(e->{
			ComboBox<String> box = new ComboBox<>(options);
			Button del = new Button("-");
			del.setOnAction(handleOnDel);
			hBox.getChildren().add(new HBox(5, box, del));
			dialog.getDialogPane().getScene().getWindow().sizeToScene();
		});
		cont.getChildren().add(new HBox(addNew, hBox));
		cont.getChildren().add(new Separator());

		CheckBox isLimit = new CheckBox("仅取指定排名");
		TextField startField = new TextField();
		startField.setPromptText("起始排名(0开始计数)");
		startField.setDisable(true);
		TextField endField = new TextField();
		endField.setPromptText("取排名总数");
		endField.setDisable(true);
		isLimit.setOnAction(e->{
			if (isLimit.isSelected()) {
				startField.setDisable(false);
				endField.setDisable(false);
			}else {
				startField.setDisable(true);
				endField.setDisable(true);
			}
		});

		cont.getChildren().addAll(new HBox(isLimit, startField, new Label("-"), endField));

		dialog.getDialogPane().setContent(cont);

		dialog.setResultConverter(new Callback<ButtonType, SelectPackage>() {
			@SuppressWarnings("unchecked")
			@Override
			public SelectPackage call(ButtonType param) {
				SelectPackage selectPackage = new SelectPackage();
				if (param==loginButtonOK) {
					if (onlySelectedFieldBox.isSelected()) {
						if (selectVisVBox.getChildren().size()<=0) {
							return null;
						}
						for (int i = 0; i < selectVisVBox.getChildren().size(); i++) {
							ComboBox<String> box = (ComboBox<String>) ((HBox)selectVisVBox.getChildren().get(i)).getChildren().get(0);
							selectPackage.addSelectField(box.getSelectionModel().getSelectedItem());
						}
					}
					for (int i = 0; i < hBox.getChildren().size(); i++) {
						ComboBox<String> box = (ComboBox<String>) ((HBox)hBox.getChildren().get(i)).getChildren().get(0);
						selectPackage.addOrderField(box.getSelectionModel().getSelectedItem());
					}
					if (isLimit.isSelected()) {
						try {
							int offset = Integer.valueOf(startField.getText().trim());
							int count = Integer.valueOf(endField.getText().trim());
							if (offset<0 || count<=0) {
								throw new RuntimeException("offset count 输入异常！");
							}
							selectPackage.setLimit(offset, count);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return selectPackage;
			}
		});

		Optional<SelectPackage> optionals =  dialog.showAndWait();
		if (optionals.isPresent()) {
			return optionals.get();
		}
		return null;
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
