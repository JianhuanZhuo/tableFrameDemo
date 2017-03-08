package cn.keepfight.frame.chain;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.keepfight.frame.FrameFactory;
import cn.keepfight.frame.PaneController;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.chain.ChainDataSource.Edge;
import cn.keepfight.frame.chain.ChainDataSource.ResourceWithPosition;
import cn.keepfight.frame.chain.Element.DirectedEdge;
import cn.keepfight.frame.chain.drag.DragMouseGestures;
import cn.keepfight.frame.chain.drag.RubberBandSelection;
import cn.keepfight.frame.chain.drag.SelectionManager;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ChainPaneController extends PaneController {

	private DAGGraph dag = new DAGGraph();

	private ChainTStage tStage;

	/**
	 * ѡ�������
	 */
	private SelectionManager selectionManager = new SelectionManager();

	/**
	 * ��ק��Ϊ
	 */
	private DragMouseGestures dragMouseGestures = new DragMouseGestures(selectionManager);

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

	@FXML
	private void initialize() {
		// ��ק����֧��
		new RubberBandSelection(chainPane, selectionManager);
	}

	@Override
	public void clearContent() {
		// @TODO ���ѡ��ģ��

		// �������

		// ��սڵ�

		// �������
	}

	protected Map<Resource, ResourceElem> getElemMap() {
		return elemMap;
	}

	@Override
	public void load() {
		// ��ջ�������
		clearContent();

		List<ResourceWithPosition> resources = source.getResources();
		for (ResourceWithPosition resWithP : resources) {
			ResourceElem elem = new ResourceElem(resWithP.resource);
			elemMap.put(resWithP.resource, elem);
			try {
				addElement(elem, resWithP.x, resWithP.y);
			} catch (GraphicException e) {
				// @TODO ������Ҫ���쳣�������ڽ�����������
				e.printStackTrace();
			}
		}
		List<Edge> edges = source.getEdges();
		for (Edge edge : edges) {
			try {
				addEdge(elemMap.get(edge.start), elemMap.get(edge.end));
			} catch (GraphicException e) {
				// @TODO ������Ҫ���쳣�������ڽ�����������
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

		this.source = (ChainDataSource) source;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (ChainTStage) tStage;
	}

	@Override
	public BorderPane getNode() {
		return pane;
	}

	public ResourceElem addResource(Resource newResource) {
		ResourceElem elem = new ResourceElem(newResource);
		elemMap.put(newResource, elem);
		try {
			addElement(elem, chainPane.getWidth()/2, chainPane.getHeight()/2);
		} catch (Exception e) {
		}
		return elem;
	}

	/**
	 * ָ��λ����ӽڵ�
	 *
	 * @param element
	 *            ����ӵĽڵ�
	 * @param x
	 *            ָ��λ������x
	 * @param y
	 *            ָ��λ������y
	 * @throws GraphicException
	 *             ͼ�����쳣
	 */
	protected void addElement(Element element, Double x, Double y) throws GraphicException {
		dag.addElem(element);
		chainPane.getChildren().add(element);
		element.relocate(x, y);

		// �����Ϊ����ק
		dragMouseGestures.makeDraggable(element);

		// ���˫�������
		element.addEventHandler(MouseEvent.MOUSE_CLICKED, onDoubleClickHander);
	}

	protected void addEdge(Element source, Element target) throws GraphicException {
		DirectedEdge edge = dag.addEdge(source, target);
		chainPane.getChildren().add(edge.getLine());

		//��������λ��
		target.relocate(source.getLayoutX()+50, source.getLayoutY()+(source.frontEles.size()-1)*50);

		source.updatePosition();
		target.updatePosition();
	}

	public ChainTStage getTStage() {
		return tStage;
	}


	EventHandler<MouseEvent> onDoubleClickHander = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 2) {
				return;
			}

			// ����Ƿ��Ѿ������
			ResourceElem sElem = (ResourceElem) event.getSource();
			if (tStage.hasContain(sElem)) {
				tStage.elemMapStage.get(sElem).showup();
				return;
			}
			//
			try {
				@SuppressWarnings("rawtypes")
				TStage newStage = FrameFactory.generateBySource(tStage, sElem.getResource().generateDataSource());
				tStage.addMap(sElem, newStage);
				newStage.show();
			} catch (InvalidSourceException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
