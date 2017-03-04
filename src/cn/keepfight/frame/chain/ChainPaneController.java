package cn.keepfight.frame.chain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.keepfight.frame.chain.ChainDataSource.Edge;
import cn.keepfight.frame.chain.ChainDataSource.ResourceWithPosition;
import cn.keepfight.frame.chain.Element.DirectedEdge;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.controller.PaneController;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ChainPaneController extends PaneController {

	private DAGGraph dag = new DAGGraph();

	/**
	 * 资源到的节点映射，用于根据资源查找节点
	 */
	private Map<Resource, ResourceElem> elemMap = new HashMap<Resource, ResourceElem>();

	ChainDataSource source;

	/**
	 * 画板的根布局
	 */
	@FXML
	BorderPane pane;

	@FXML
	Pane chainPane;

	@Override
	public void clearContent() {
		//@TODO 清空选择模型

		//清空连线


		//清空节点

		//清空其他
	}

	@Override
	public void load() {
		//清空画板内容
		clearContent();

		List<ResourceWithPosition> resources = source.getResources();
		for (ResourceWithPosition resWithP : resources) {
			ResourceElem elem = new ResourceElem(resWithP.resource);
			elemMap.put(resWithP.resource, elem);
			try {
				addElement(elem, resWithP.x, resWithP.y);
			} catch (GraphicException e) {
				//@TODO 这里需要作异常处理，现在仅作丢弃处理
				e.printStackTrace();
			}
		}
		List<Edge> edges = source.getEdges();
		for (Edge edge : edges) {
			try {
				addEdge(elemMap.get(edge.start), elemMap.get(edge.end));
			} catch (GraphicException e) {
				//@TODO 这里需要作异常处理，现在仅作丢弃处理
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setDataSource(DataSource source) throws InvalidSourceException {
		if (!(source instanceof ChainDataSource)) {
			throw new InvalidSourceException("source instanceof ChainDataSource");
		}
		source.checkValid();

		this.source = (ChainDataSource)source;
	}

	@Override
	public BorderPane getNode() {
		return pane;
	}

	/**
	 *指定位置添加节点
	 * @param element 欲添加的节点
	 * @param x 指定位置坐标x
	 * @param y 指定位置坐标y
	 * @throws GraphicException 图操作异常
	 */
	protected void addElement(Element element, Double x, Double y) throws GraphicException {
		dag.addElem(element);
		chainPane.getChildren().add(element);
		element.relocate(x, y);
	}

	protected void addEdge(Element source, Element target) throws GraphicException {
		DirectedEdge edge = dag.addEdge(source, target);
		chainPane.getChildren().add(edge.getLine());
		source.updatePosition();
		target.updatePosition();
	}
}
