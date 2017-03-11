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
 * 链算子面板内部算子，用于打开本地文件并添加一个本地文件资源
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
	@Override public String getLabel() { return "导入表"; }
	@Override public String getIcon() { return "table.png"; }
	@Override public String getTips() { return "选择指定数据库表并加入算子链面板中"; }
	@Override public String getDescription() { return "双击计算机以获取对本地文件，包括可移动闪存驱动器。"
			+ "您还可以选择您的帐户，您可以开始，如有必要，登录，然后打开所需的文件的位置之一。"
			+ "要添加新的位置，请单击添加位置。最近列表显示从任何设备最近打开的文件。如果此列表获取实用，"
			+ "您可以从其删除文件。只需右键单击文件名，然后选择从列表中删除。 "
			+ "您可以对最近列表中进行其他更改。若要了解详细信息，请参阅自定义最近使用的文件列表。"
			+ "如果您不使用列表中的最近使用的文件，并且您而是将直接插入浏览文件夹跳，"
			+ "使用 Ctrl + F12 直接打开打开对话框。"; }

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
		ButtonType ok = new ButtonType("导入", ButtonData.OK_DONE);
		ButtonType cancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
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

		grid.add(new Label("数据库："), 0, 0);
		grid.add(db, 1, 0);
		grid.add(new Label("表或视图："), 0, 1);
		grid.add(entity, 1, 1);

		ProgressIndicator indic = new ProgressIndicator();
		Label runing = new Label("正在更新可用数据库表信息...");
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

		//这里用任务的形式，应该用Service才对
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
							type="基本表";
							break;
						case "VIEW":
							type="试图表";
							break;
						default:
							type="未知表";
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
