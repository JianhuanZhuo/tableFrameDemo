package cn.keepfight.frame.chain.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.ResourceElem;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.frame.table.DBTableDataSource;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.operator.WaitDialog;
import cn.keepfight.utils.HttpUtils;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import net.sf.json.JSONObject;

/**
 * 链算子面板内部算子，进行多表连接操作
 * @author Tom
 *
 */
public class JoinOperaor extends AbstractOperator{

	ChainTStage tStage;
	List<ResourceElem> selectResList;
	Map<String, List<String>> entityMapFields = new HashMap<String, List<String>>();

	private String[] inputResource;
	private String[] outputResource;
	private String[] params;

	public JoinOperaor(ChainTStage tStage) {
		this.tStage = tStage;
	}

	@Override public int getId() { return 333; }
	@Override public String getName() { return "join"; }
	@Override public String getLabel() { return "表连接"; }
	@Override public String getIcon() { return "left-join.png"; }
	@Override public String getTips() { return "选择多个表对象进行连接操作"; }
	@Override public String getDescription() { return "双击计算机以获取对本地文件，包括可移动闪存驱动器。"
			+ "您还可以选择您的帐户，您可以开始，如有必要，登录，然后打开所需的文件的位置之一。"
			+ "要添加新的位置，请单击添加位置。最近列表显示从任何设备最近打开的文件。如果此列表获取实用，"
			+ "您可以从其删除文件。只需右键单击文件名，然后选择从列表中删除。 "
			+ "您可以对最近列表中进行其他更改。若要了解详细信息，请参阅自定义最近使用的文件列表。"
			+ "如果您不使用列表中的最近使用的文件，并且您而是将直接插入浏览文件夹跳，"
			+ "使用 Ctrl + F12 直接打开打开对话框。"; }

	@Override
	public ActionResult onAction() throws Exception {
		selectResList = tStage.getPaneVC().getSelectedResources().stream()
			.filter(r->r.getResource().getDataSourceType()==DataSourceType.TABLE)
			.collect(Collectors.toList());
		if (selectResList.size()<2) {
			new Alert(AlertType.ERROR, "选择资源数不允许小于2!", ButtonType.CLOSE).showAndWait();
			return null;
		}

		new WaitDialog<Boolean>(new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				for (int i = 0; i < selectResList.size(); i++) {
					DBTableDataSource dbTableSource = ((DBTableDataSource)selectResList.get(i).getResource().generateDataSource());
					entityMapFields.put(
							dbTableSource.getEntityName(),
							dbTableSource.getHeadList().stream().map(head->head.getValue()).collect(Collectors.toList()));
				}
				return null;
			}
		}).justWait();

		JoinPackage resPackage = createDialog();
		if (resPackage==null) {
			return null;
		}

		String fieldSQL = entityMapFields.entrySet().stream()
			.flatMap(ent->{
				return ent.getValue().stream()
					.map(field->ent.getKey()+"."+field+" as "+ent.getKey()+"_"+field);})
			.collect(Collectors.joining(","));


		String sqlString = "select "+fieldSQL+" from "+resPackage.generateSQL();
		System.out.println(sqlString);
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

	    params = resPackage.getParams();
		inputResource = resPackage.getInputEntity();
		outputResource = new String[1];
		outputResource[0] = resultResource.getName();

		tStage.doOperate(selectResList, generateResource(), resultResource);

		return null;
	}

	class JoinPackage{
		String tbl1, tbl2, field1, field2, joinWay;
		public JoinPackage(String tbl1, String field1, String tbl2, String field2, String joinWay) {
			this.tbl1 = tbl1;
			this.tbl2 = tbl2;
			this.field1 = field1;
			this.field2 = field2;
			if (joinWay.equals("内连接")) {
				this.joinWay = "left join";
			}
		}

		public String[] getInputEntity(){
			String[] inputEntities = new String[2];
			inputEntities[0] = tbl1;
			inputEntities[1] = tbl2;
			return inputEntities;
		}

		public String[] getParams(){
			String[] params = new String[1];
			params[0]=generateSQL();
			return params;
		}

		public String generateSQL() {
			return ""+tbl1+" "+joinWay+" "+tbl2+
					" ON "+"("+tbl1+"."+field1+" = "+tbl2+"."+field2+")";
		}
	}

	private JoinPackage createDialog(){

		Dialog<JoinPackage> dialog = new Dialog<>();
		dialog.setTitle("表连接");
		dialog.setHeaderText("请选择表连接条件，系统将会根据您选择连接条件进行连接");

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(ImageLoadUtil.load(getIcon(), ImageLoadUtil.IMG_SIZE_64)));

		// Set the button types.
		ButtonType loginButtonOK = new ButtonType("确定", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonOK, buttonTypeCancel);

		ObservableList<String> options =
			    FXCollections.observableArrayList(entityMapFields.keySet());
		ObservableList<String> joinTypes =
			    FXCollections.observableArrayList(Arrays.asList("内连接"));

		Button addBtn = new Button("添加连接条件");
		VBox allConditionVBox = new VBox(10);

		class ConditionPackage extends HBox implements EventHandler<ActionEvent>{
			ComboBox<String> ent1 = new ComboBox<>(options);
			ComboBox<String> field1 = new ComboBox<>();
			ComboBox<String> joinType = new ComboBox<>(joinTypes);
			ComboBox<String> ent2 = new ComboBox<>(options);
			ComboBox<String> field2 = new ComboBox<>();
			Button del = new Button("-");

			public ConditionPackage() {
				setSpacing(5);
				joinType.getSelectionModel().select(0);
				del.setOnAction(this);
				this.getChildren().addAll(
						ent1, new Label("/"), field1, new Label("--"),
						joinType,
						new Label("--"), ent2, new Label("/"), field2, del);
				ent1.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						List<String> tables = entityMapFields.get(ent1.getSelectionModel().getSelectedItem());
						if (tables!=null) {
							field1.setItems(FXCollections.observableList(tables));
						}
					}
				});
				ent2.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						List<String> tables = entityMapFields.get(ent2.getSelectionModel().getSelectedItem());
						if (tables!=null) {
							field2.setItems(FXCollections.observableList(tables));
						}
					}
				});
			}

			@Override
			public void handle(ActionEvent event) {
				VBox waigong = (VBox) this.getParent();
				waigong.getChildren().remove(this);
				dialog.getDialogPane().getScene().getWindow().sizeToScene();
			}
		}
		ConditionPackage cond = new ConditionPackage();
		allConditionVBox.getChildren().add(cond);
		/*********************************************************************
		 * @TODO 做添加
//		addBtn.setOnAction(e->{
//			allConditionVBox.getChildren().add(new ConditionPackage());
//			dialog.getDialogPane().getScene().getWindow().sizeToScene();
//		});
		*********************************************************************/
		dialog.getDialogPane().setContent(new VBox(10, addBtn, new Separator(),allConditionVBox));
		dialog.getDialogPane().setPrefWidth(700);

		dialog.setResultConverter(btnType -> {
			if(btnType==loginButtonOK){
				String tbl1 = cond.ent1.getSelectionModel().getSelectedItem();
				String tbl2 = cond.ent2.getSelectionModel().getSelectedItem();
				String field1 = cond.field1.getSelectionModel().getSelectedItem();
				String field2 = cond.field2.getSelectionModel().getSelectedItem();
				String type = cond.joinType.getSelectionModel().getSelectedItem();
				JoinPackage result = new JoinPackage(tbl1, field1, tbl2, field2, type);
				/***************************************************
				 *
				 * @TODO 做多个
				 **************************************************/
				return result;
			}
			return null;
		});

		Optional<JoinPackage> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
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
