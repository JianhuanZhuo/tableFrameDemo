package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.chain.drag.Dragable;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * 有向无环图，逻辑节点类
 * @author Tom
 *
 */
public abstract class Element extends Region implements Dragable{

	/**
	 * 入度目标节点集合
	 */
	List<Element> backEles = new ArrayList<Element>();

	/**
	 * 出度目标节点
	 */
	List<DirectedEdge> frontEles = new ArrayList<DirectedEdge>();


	protected static class DirectedEdge{
		Line line;
		Element endEle;

		/**
		 * 指定终节点设置有向边
		 * @param endEle 终节点
		 * @throws GraphicException 终节点无效异常
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
	 * 检查是否为起始节点
	 * @return 是返回true，否则返回false
	 */
	public boolean isStartEle(){
		return backEles.isEmpty();
	}

	/**
	 * 查找当前节点中所有末端包含指定节点的边
	 * @param frontElem 指定末端节点
	 * @return 所有符合条件的边
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

		//@TODO 考虑终点和起点相叠加的情况
		double x = getLayoutX()+getLayoutBounds().getWidth()/2;
		double y = getLayoutY()+getLayoutBounds().getHeight()/2;
		// 更新前任连接线
		for (Element element : backEles) {
			for (DirectedEdge edge : element.getFrontEdge(this)) {
				edge.updateLineEnd(x, y);
			};
		}
		//更新下任连接线
		for (DirectedEdge directedEdge : frontEles) {
			directedEdge.updateLineStart(x, y);
		}
	}
}
