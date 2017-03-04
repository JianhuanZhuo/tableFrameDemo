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

		protected void updateLine(double start_x, double start_y, double end_x, double end_y) {
			line.setStartX(start_x);
			line.setStartY(start_y);
			line.setEndX(end_x);
			line.setEndY(end_y);
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
	 * ����Ŀ��ڵ�
	 */
	List<DirectedEdge> frontEles = new ArrayList<DirectedEdge>();



	/**
	 * ����Ƿ�Ϊ��ʼ�ڵ�
	 * @return �Ƿ���true�����򷵻�false
	 */
	public boolean isStartEle(){
		return backEles.isEmpty();
	}

	@Override
	public void updatePosition() {

		//@TODO �����յ���������ӵ����

		double x = getLayoutX()+getLayoutBounds().getWidth()/2;
		double y = getLayoutY()+getLayoutBounds().getHeight()/2;
		// ����ǰ��������
		for (Element element : backEles) {
			int index = element.frontEles.indexOf(this);
			element.frontEles.get(index).updateLineEnd(x, y);
		}
		//��������������
		for (DirectedEdge directedEdge : frontEles) {
			directedEdge.updateLineStart(x, y);
		}
	}
}
