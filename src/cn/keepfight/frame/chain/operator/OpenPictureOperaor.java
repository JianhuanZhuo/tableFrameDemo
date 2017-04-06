package cn.keepfight.frame.chain.operator;

import java.io.File;
import java.net.MalformedURLException;

import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.PictureResouce;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * 链算子面板内部算子，用于打开本地文件并添加一个本地文件资源
 * @author Tom
 *
 */
public class OpenPictureOperaor extends AbstractOperator{

	ChainTStage tStage;

	public OpenPictureOperaor(ChainTStage tStage) {
		this.tStage = tStage;
	}

	@Override public int getId() { return 333; }
	@Override public String getName() { return "picture_import"; }
	@Override public String getLabel() { return "导入图片"; }
	@Override public String getIcon() { return "picture.png"; }
	@Override public String getTips() { return "打开一个本地图片文件作为文件资源"; }
	@Override public String getDescription() { return "双击计算机以获取对本地文件，包括可移动闪存驱动器。"
			+ "您还可以选择您的帐户，您可以开始，如有必要，登录，然后打开所需的文件的位置之一。"
			+ "要添加新的位置，请单击添加位置。最近列表显示从任何设备最近打开的文件。如果此列表获取实用，"
			+ "您可以从其删除文件。只需右键单击文件名，然后选择从列表中删除。 "
			+ "您可以对最近列表中进行其他更改。若要了解详细信息，请参阅自定义最近使用的文件列表。"
			+ "如果您不使用列表中的最近使用的文件，并且您而是将直接插入浏览文件夹跳，"
			+ "使用 Ctrl + F12 直接打开打开对话框。"; }

	@Override
	public ActionResult onAction() {
		//@TODO 做成可以选择本地或是网络的
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开图片文件");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("支持的图片文件格式", "*.PNG", "*.GIF", "*.JPEG", "*.JPG", "*.BMP"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG 文件 (*.PNG)", "*.PNG"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF 文件 (*.GIF)", "*.GIF"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG 文件 (*.JPEG)", "*.JPEG", "*.JPG"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP 文件 (*.BMP)", "*.BMP"));
		File targetFile = fileChooser.showOpenDialog(tStage);
		if (targetFile != null) {
			Resource newResource;
			try {
				newResource = new PictureResouce(targetFile.toURI().toURL().toString());
				tStage.getPaneVC().addResource(newResource);
			} catch (MalformedURLException e) {
				new Alert(AlertType.ERROR, "指定路径错误！", ButtonType.CLOSE).showAndWait();
				e.printStackTrace();
			}
	    }
		return null;
	}


	@Override
	public OperatorResource generateResource() {
		OperatorResource res = super.generateResource();
		return res;
	}
}
