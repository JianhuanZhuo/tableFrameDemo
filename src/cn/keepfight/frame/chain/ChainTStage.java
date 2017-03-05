package cn.keepfight.frame.chain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.keepfight.frame.ContextMaster;
import cn.keepfight.frame.ContextSlave;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * 算子链面板
 * @author Tom
 *
 */
public class ChainTStage extends TStage<ChainDataSource,
			ChainMenuViewController, ChainPaneController> implements ContextMaster{

	/**
	 * 使用双哈希保存两者间的相互映射
	 * @TODO 此处需改进
	 */
	Map<ContextSlave, Resource> stageMapResource = new HashMap<>();
	Map<Resource, ContextSlave> resourceMapStage = new HashMap<>();

	@Override
	protected void fixAfter() {
		//添加选择对象高亮
		getScene().getStylesheets().add( getClass().getResource("drag/highlight.css").toExternalForm());

		// 加载本地菜单
		getMenuVC().loadLocalMenu();
	}

	@Override
	public void update(ContextSlave slave) {

	}

	@Override
	public void onDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("确认对话框");
		alert.setHeaderText("该算子链资源在父面板已被删除，是否保存当前面板内容？");
		alert.setContentText("即将关闭该面板以响应保存有该算子链资源的父面板，"
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

	@Override
	public DataSource doOperate(ContextSlave slave, Resource operator, Resource result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataSource> doOperate(ContextSlave slave, Resource operator, List<Resource> results) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataSource> doOperate(List<ContextSlave> slaves, Resource operator, List<Resource> results) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasContain(ContextSlave slave) {
		return stageMapResource.containsKey(slave);
	}

	public boolean hasContain(Resource resource) {
		return resourceMapStage.containsKey(resource);
	}

	protected void addMap(Resource resource, ContextSlave slave){
		resourceMapStage.put(resource, slave);
		stageMapResource.put(slave, resource);
	}
}
