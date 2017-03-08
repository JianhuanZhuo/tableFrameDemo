package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.chain.drag.Dragable;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * �����޻�ͼ���߼��ڵ���
 * @author Tom
 *
 */
public abstract class Element extends Region implements Dragable{

	/**
	 * ���Ŀ��ڵ㼯��
	 */
	List<Element> backEles = new ArrayList<Element>();

	/**
	 * ����Ŀ��ڵ�
	 */
	List<DirectedEdge> frontEles = new ArrayList<DirectedEdge>();


	protected static class DirectedEdge{
		Line line;
		Element endEle;

		/**
		 * ָ���սڵ����������
		 * @param endEle �սڵ�
		 * @throws GraphicException �սڵ���Ч�쳣
		 */
		public DirectedEdge(Element endEle) throws GraphicException {
			if (endEle==null) {
				throw new GraphicException("end element do not allow null!");
			}
			this.endEle = endEle;
			line = new Line();
		}

		protected void updateLineStart(double start_x, double start_y) {
			line.setStartX(start_x);
			line.setStartY(start_y);
		}

		protected void updateLineEnd(double end_x, double end_y) {
			line.setEndX(end_x);
			line.setEndY(end_y);
		}

		public Line getLine() {
			return line;
		}
	}

	/**
	 * ����Ƿ�Ϊ��ʼ�ڵ�
	 * @return �Ƿ���true�����򷵻�false
	 */
	public boolean isStartEle(){
		return backEles.isEmpty();
	}

	/**
	 * ���ҵ�ǰ�ڵ�������ĩ�˰���ָ���ڵ�ı�
	 * @param frontElem ָ��ĩ�˽ڵ�
	 * @return ���з��������ı�
	 */
	public DirectedEdge[] getFrontEdge(Element frontElem){
		List<DirectedEdge> edges = new ArrayList<>();

		for (DirectedEdge directedEdge : frontEles) {
			if (directedEdge.endEle==frontElem) {
				edges.add(directedEdge);
			}
		}

		return edges.toArray(new DirectedEdge[edges.size()]);
	}

	@Override
	public void updatePosition() {

		//@TODO �����յ���������ӵ����
		double x = getLayoutX()+getLayoutBounds().getWidth()/2;
		double y = getLayoutY()+getLayoutBounds().getHeight()/2;
		// ����ǰ��������
		for (Element element : backEles) {
			for (DirectedEdge edge : element.getFrontEdge(this)) {
				edge.updateLineEnd(x, y);
			};
		}
		//��������������
		for (DirectedEdge directedEdge : frontEles) {
			directedEdge.updateLineStart(x, y);
		}
	}
}
