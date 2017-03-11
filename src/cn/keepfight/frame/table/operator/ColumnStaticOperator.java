package cn.keepfight.frame.table.operator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.PictureResouce;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.frame.table.TableDataSource;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.HttpUtils;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import net.sf.json.JSONObject;

public class ColumnStaticOperator extends AbstractOperator{

	TableTStage tStage;


	private String[] inputResource;
	private String[] outputResource;
	private String[] params;


	public ColumnStaticOperator(TableTStage tStage) {
		this.tStage = tStage;
	}

	@Override public int getId() { return 333; }
	@Override public String getName() {return "static";}
	@Override public String getLabel() { return "列统计"; }
	@Override public String getIcon() { return "bar.png"; }
	@Override public String getTips() { return "指定列进行频次统计"; }
	@Override public String getDescription() { return "暂时无算子解释"; }

	@Override
	public ActionResult onAction() throws Exception {

		List<String> allColumns = tStage.getPaneVC().getLoader().allColumns();

		ChoiceDialog<String> dialog = new ChoiceDialog<>("请选择进行统计的列", allColumns);
		dialog.setTitle(getLabel());
		dialog.setHeaderText("选择指定的列进行频次统计");
		dialog.setContentText("选择列：");
		dialog.setGraphic(new ImageView(ImageLoadUtil.load(getIcon(), ImageLoadUtil.IMG_SIZE_64)));

		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent()){
		    return null;
		}

		//select column5, count(column5) num from 出行数据 group by column5 order by count(column5) DESC;
		String sqlString = "select "+result.get()+", count("+result.get()+") num from "
				+tStage.getSource().getEntityName()+" group by "+result.get()
				+" order by count("+result.get()+") DESC;";

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

		TableResource columnStatic = new TableResource("wz", viewName);
		List<Resource> resResources = new ArrayList<>();
		resResources.add(columnStatic);

		TableDataSource tableDataSource = (TableDataSource) columnStatic.generateDataSource();
		List<ObservableList<StringProperty>> dataList = tableDataSource.getRowList(0, 10);
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (int i = 0; i < dataList.size(); i++) {
			pieChartData.add(new PieChart.Data(dataList.get(i).get(0).getValue(),
					Integer.valueOf(dataList.get(i).get(1).getValue())));
		}

		PieChart chart = new PieChart(pieChartData);
//        chart.setTitle("Imported Fruits");

		Dialog<Boolean> display = new Dialog<>();
		display.setTitle("频次统计图");
		display.setHeaderText("频次统计如下图！");

		ButtonType loginButtonType = new ButtonType("确定", ButtonData.OK_DONE);
		display.getDialogPane().getButtonTypes().addAll(loginButtonType);

		// Set the icon (must be included in the project).
		display.setGraphic(new ImageView(ImageLoadUtil.load(getIcon(), ImageLoadUtil.IMG_SIZE_64)));

		display.getDialogPane().setContent(new BorderPane(chart));


		File file = new File("chart_"+System.currentTimeMillis()+".png");
		display.setResultConverter(en->{
			WritableImage image = chart.snapshot(new SnapshotParameters(), null);
	        try {
	            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
			return true;
		});

		display.showAndWait();
        PictureResouce pictureResouce = new PictureResouce(file.toURI().toURL().toString());
        resResources.add(pictureResouce);

	    params = new String[1];
	    params[0] = result.get();
		inputResource = new String[1];
		inputResource[0] = tStage.getSource().getSourceIDName();
		outputResource = new String[2];
		outputResource[0] = columnStatic.getName();
		outputResource[1] = file.toURI().toURL().toString();
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
