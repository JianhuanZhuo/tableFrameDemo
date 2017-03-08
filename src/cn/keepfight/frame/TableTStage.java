package cn.keepfight.frame;

import java.util.Optional;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.table.TableDataSource;
import cn.keepfight.frame.table.TableMenuViewController;
import cn.keepfight.frame.table.TablePaneController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * 表格面板
 * @author Tom
 *
 */
public class TableTStage extends TStage<TableDataSource, TableMenuViewController,
			TablePaneController>{

	@Override
	protected void fixAfter() {
	}

	@Override
	public void onDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("确认对话框");
		alert.setHeaderText("该表格资源在父面板已被删除，是否保存当前面板内容？");
		alert.setContentText("即将关闭该面板以响应保存有该表格资源的父面板，"
				+ "是否保存该面板的内容以免正在编辑的数据发生丢失？"
				+ "点击 是 保存该面板数据，点击 否 不保存，直接关闭！");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			System.out.println("click OK");
		} else {
			System.out.println("click cancel");
			close();
		}
	}
}
