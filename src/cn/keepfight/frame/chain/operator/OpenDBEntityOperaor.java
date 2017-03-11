package cn.keepfight.frame.chain.operator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.HttpUtils;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ����������ڲ����ӣ����ڴ򿪱����ļ������һ�������ļ���Դ
 * @author Tom
 *
 */
public class OpenDBEntityOperaor extends AbstractOperator{

	List<File> targetList;
	ChainTStage tStage;

	public OpenDBEntityOperaor(ChainTStage tStage) {
		this.tStage = tStage;
	}

	@Override public int getId() { return 777; }
	@Override public String getName() { return "open_table"; }
	@Override public String getLabel() { return "�����"; }
	@Override public String getIcon() { return "table.png"; }
	@Override public String getTips() { return "ѡ��ָ�����ݿ�����������������"; }
	@Override public String getDescription() { return "˫��������Ի�ȡ�Ա����ļ����������ƶ�������������"
			+ "��������ѡ�������ʻ��������Կ�ʼ�����б�Ҫ����¼��Ȼ���������ļ���λ��֮һ��"
			+ "Ҫ����µ�λ�ã��뵥�����λ�á�����б���ʾ���κ��豸����򿪵��ļ���������б��ȡʵ�ã�"
			+ "�����Դ���ɾ���ļ���ֻ���Ҽ������ļ�����Ȼ��ѡ����б���ɾ���� "
			+ "�����Զ�����б��н����������ġ���Ҫ�˽���ϸ��Ϣ��������Զ������ʹ�õ��ļ��б�"
			+ "�������ʹ���б��е����ʹ�õ��ļ������������ǽ�ֱ�Ӳ�������ļ�������"
			+ "ʹ�� Ctrl + F12 ֱ�Ӵ򿪴򿪶Ի���"; }

	@Override
	public ActionResult onAction() {
		Pair<String, String> selectDB = createSelectDB(this);
		if (selectDB!=null) {
			Resource newResource = new TableResource(selectDB.getKey(), selectDB.getValue().split("-")[1]);
			tStage.getPaneVC().addResource(newResource);
		}
		return null;
	}


	@Override
	public OperatorResource generateResource() {
		OperatorResource res = super.generateResource();
		return res;
	}

	private Pair<String, String> createSelectDB(AbstractOperator operator){
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle(operator.getLabel());
		dialog.setHeaderText(operator.getTips());

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(ImageLoadUtil.load("table.png")));

		// Set the button types.
		ButtonType ok = new ButtonType("����", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("ȡ��", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(ok, cancel);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ComboBox<String> db = new ComboBox<String>();
		db.getItems().add("wz");
		db.getSelectionModel().select(0);
		ComboBox<String> entity = new ComboBox<String>();

		grid.add(new Label("���ݿ⣺"), 0, 0);
		grid.add(db, 1, 0);
		grid.add(new Label("�����ͼ��"), 0, 1);
		grid.add(entity, 1, 1);

		ProgressIndicator indic = new ProgressIndicator();
		Label runing = new Label("���ڸ��¿������ݿ����Ϣ...");
		BorderPane borderPane = new BorderPane(indic);
		borderPane.setBottom(runing);

		Node loginButton = dialog.getDialogPane().lookupButton(ok);
		loginButton.setDisable(true);

		dialog.setResultConverter(btnType -> {
			if(btnType==ok){
				return new Pair<String, String>(db.getSelectionModel().getSelectedItem(), entity.getSelectionModel().getSelectedItem());
			}
			return null;
		});
		dialog.getDialogPane().setContent(borderPane);

		//�������������ʽ��Ӧ����Service�Ŷ�
		Task<List<String>> download = new Task<List<String>>() {

			@Override
			protected List<String> call() throws Exception {
				JSONObject resx = JSONObject.fromObject(HttpUtils.simpleGet("http://127.0.0.1:8080/dap/dataLoad/getTables.htm?db=wz"));
//				if(resx!=null && resx.getBoolean("flag")){
//					resx.getJSONArray("tables").stream()
//						.map(obj->{
//							JSONArray arr = (JSONArray)obj;
//							return ""+arr.getString(1)+"-"+arr.getString(0);})
//						.filter(s->(s==null||((String) s).trim().length()==0))
//						.collect(Collectors.toList());
//				}
				List<String> sList = new ArrayList<String>();
				if(resx!=null && resx.getBoolean("flag")){
					for (int i = 0; i < resx.getJSONArray("tables").size(); i++) {
						JSONArray row = resx.getJSONArray("tables").getJSONArray(i);
						String type = "";
						switch (row.getString(1)) {
						case "BASE TABLE":
							type="������";
							break;
						case "VIEW":
							type="��ͼ��";
							break;
						default:
							type="δ֪��";
							break;
						}
						sList.add(""+type+"-"+row.getString(0));
					}
				}
				return sList;
			}
		};

		download.setOnSucceeded(t ->{
			entity.setItems(FXCollections.observableList(download.getValue()));
			dialog.getDialogPane().setContent(grid);
			Node okBtn = dialog.getDialogPane().lookupButton(ok);
			okBtn.setDisable(false);
		});
		new Thread(download).start();
		Optional<Pair<String, String>> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("db="+result.get().getKey()+ ", entity=" + result.get().getValue());
			return result.get();
		}
		return null;
	}
}
