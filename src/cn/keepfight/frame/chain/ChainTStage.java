package cn.keepfight.frame.chain;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.keepfight.frame.ContextMaster;
import cn.keepfight.frame.ContextSlave;
import cn.keepfight.frame.FrameFactory;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
	Map<ContextSlave, ResourceElem> stageMapElem = new HashMap<>();
	Map<ResourceElem, ContextSlave> elemMapStage = new HashMap<>();

	@Override
	protected void fixAfter() {
		//添加选择对象高亮
		getScene().getStylesheets().add( getClass().getResource("drag/highlight.css").toExternalForm());
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
	public void doOperate(ContextSlave slave, Resource operator, Resource result) {
		//查找奴隶节点
		ResourceElem sElem = stageMapElem.get(slave);

		//添加算子节点、结果节点
		ResourceElem operatorElem = getPaneVC().addResource(operator);
		ResourceElem eElem = getPaneVC().addResource(result);

		try {
			getPaneVC().addEdge(sElem, operatorElem);
			getPaneVC().addEdge(operatorElem, eElem);
		} catch (GraphicException e) {
			e.printStackTrace();
		}

		//返回结果资源的数据源
		DataSource resDataSource = eElem.getResource().generateDataSource();
		/****************************************************************
		try {
			slave.reSetSource(resDataSource);
			elemMapStage.put(eElem, slave);
			stageMapElem.put(slave, eElem);
		} catch (InvalidSourceException | IOException e) {

		****************************************************************/
		try {
			ContextSlave newSlave = FrameFactory.generateBySource(this, resDataSource);
			addMap(eElem, newSlave);
			slave.close();
			newSlave.showup();
		} catch (InvalidSourceException | IOException e1) {
			System.err.println("Source InvalidSourceException!");
			e1.printStackTrace();
		}
		return ;
	}

	@Override
	public void doOperate(ContextSlave slave, Resource operator, List<Resource> results) {
		// TODO Auto-generated method stub
		return ;
	}

//	@Override
//	public void doOperate(List<ContextSlave> slaves, Resource operator, List<Resource> results){
//		//奴隶节点集合
//		List<ResourceElem> sElemList = slaves.stream()
//				.map(slave->stageMapElem.get(slave))
//				.filter(p->p!=null)
//				.collect(Collectors.toList());
//
//		//添加算子节点、结果节点
//		ResourceElem operatorElem = getPaneVC().addResource(operator);
//
//		//结果节点集合
//		List<ResourceElem> eElemList = results.stream()
//				.map(result->getPaneVC().addResource(result))
//				.collect(Collectors.toList());
//		//添加边
//		try {
//			for (ResourceElem sElem : sElemList) {
//					getPaneVC().addEdge(sElem, operatorElem);
//			}
//			for (ResourceElem eElem : eElemList) {
//				getPaneVC().addEdge(operatorElem, eElem);
//			}
//		} catch (GraphicException e) {
//			e.printStackTrace();
//		}
//		return ;
//	}

	public boolean hasContain(ContextSlave slave) {
		return stageMapElem.containsKey(slave);
	}

	public boolean hasContain(ResourceElem elem) {
		return elemMapStage.containsKey(elem);
	}

	protected void addMap(ResourceElem elem, ContextSlave slave){
		elemMapStage.put(elem, slave);
		stageMapElem.put(slave, elem);
	}

	public ContextSlave mapSlave(ResourceElem elem) {
		return elemMapStage.get(elem);
	}

	@Override
	public void doOperate(List<ResourceElem> slaves, Resource operator, Resource result) {
		//奴隶节点集合
		//添加算子节点、结果节点
		ResourceElem operatorElem = getPaneVC().addResource(operator);

		//结果节点集合
		ResourceElem eElem = getPaneVC().addResource(result);
		//添加边
		try {
			for (ResourceElem sElem : slaves) {
				getPaneVC().addEdge(sElem, operatorElem);
			}
			getPaneVC().addEdge(operatorElem, eElem);
		} catch (GraphicException e) {
			e.printStackTrace();
		}
		return ;
	}
}
