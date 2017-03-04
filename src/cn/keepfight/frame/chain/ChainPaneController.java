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
	 * ��Դ���Ľڵ�ӳ�䣬���ڸ�����Դ���ҽڵ�
	 */
	private Map<Resource, ResourceElem> elemMap = new HashMap<Resource, ResourceElem>();

	ChainDataSource source;

	/**
	 * ����ĸ�����
	 */
	@FXML
	BorderPane pane;

	@FXML
	Pane chainPane;

	@Override
	public void clearContent() {
		//@TODO ���ѡ��ģ��

		//�������


		//��սڵ�

		//�������
	}

	@Override
	public void load() {
		//��ջ�������
		clearContent();

		List<ResourceWithPosition> resources = source.getResources();
		for (ResourceWithPosition resWithP : resources) {
			ResourceElem elem = new ResourceElem(resWithP.resource);
			elemMap.put(resWithP.resource, elem);
			try {
				addElement(elem, resWithP.x, resWithP.y);
			} catch (GraphicException e) {
				//@TODO ������Ҫ���쳣�������ڽ�����������
				e.printStackTrace();
			}
		}
		List<Edge> edges = source.getEdges();
		for (Edge edge : edges) {
			try {
				addEdge(elemMap.get(edge.start), elemMap.get(edge.end));
			} catch (GraphicException e) {
				//@TODO ������Ҫ���쳣�������ڽ�����������
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
	 *ָ��λ����ӽڵ�
	 * @param element ����ӵĽڵ�
	 * @param x ָ��λ������x
	 * @param y ָ��λ������y
	 * @throws GraphicException ͼ�����쳣
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
