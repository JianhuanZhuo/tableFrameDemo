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
 * ����������ڲ����ӣ����ж�����Ӳ���
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
	@Override public String getLabel() { return "������"; }
	@Override public String getIcon() { return "left-join.png"; }
	@Override public String getTips() { return "ѡ���������������Ӳ���"; }
	@Override public String getDescription() { return "˫��������Ի�ȡ�Ա����ļ����������ƶ�������������"
			+ "��������ѡ�������ʻ��������Կ�ʼ�����б�Ҫ����¼��Ȼ���������ļ���λ��֮һ��"
			+ "Ҫ����µ�λ�ã��뵥�����λ�á�����б���ʾ���κ��豸����򿪵��ļ���������б��ȡʵ�ã�"
			+ "�����Դ���ɾ���ļ���ֻ���Ҽ������ļ�����Ȼ��ѡ����б���ɾ���� "
			+ "�����Զ�����б��н����������ġ���Ҫ�˽���ϸ��Ϣ��������Զ������ʹ�õ��ļ��б�"
			+ "�������ʹ���б��е����ʹ�õ��ļ������������ǽ�ֱ�Ӳ�������ļ�������"
			+ "ʹ�� Ctrl + F12 ֱ�Ӵ򿪴򿪶Ի���"; }

	@Override
	public ActionResult onAction() throws Exception {
		selectResList = tStage.getPaneVC().getSelectedResources().stream()
			.filter(r->r.getResource().getDataSourceType()==DataSourceType.TABLE)
			.collect(Collectors.toList());
		if (selectResList.size()<2) {
			new Alert(AlertType.ERROR, "ѡ����Դ��������С��2!", ButtonType.CLOSE).showAndWait();
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
			if (joinWay.equals("������")) {
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
		dialog.setTitle("������");
		dialog.setHeaderText("��ѡ�������������ϵͳ���������ѡ������������������");

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(ImageLoadUtil.load(getIcon(), ImageLoadUtil.IMG_SIZE_64)));

		// Set the button types.
		ButtonType loginButtonOK = new ButtonType("ȷ��", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("ȡ��", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonOK, buttonTypeCancel);

		ObservableList<String> options =
			    FXCollections.observableArrayList(entityMapFields.keySet());
		ObservableList<String> joinTypes =
			    FXCollections.observableArrayList(Arrays.asList("������"));

		Button addBtn = new Button("�����������");
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
		 * @TODO �����
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
				 * @TODO �����
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
