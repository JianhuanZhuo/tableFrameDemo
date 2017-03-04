package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.keepfight.frame.chain.Element.DirectedEdge;

/**
 * 有向无环图，逻辑结构
 * @author Tom
 *
 */
public class DAGGraph {

	/**
	 * 节点集
	 */
	Set<Element> elements = new HashSet<Element>();

	/**
	 * 添加节点
	 * @param elem 欲添加的节点
	 * @throws GraphicException 节点为空异常
	 */
	public void addElem(Element elem) throws GraphicException{
		//@TODO 需要改进以检查是否因为它的加入而产生环
		if (elem==null) {
			throw new GraphicException("elem added do not allow null!");
		}
		elements.add(elem);
	}

	/**
	 * 删除节点
	 * @param elem 删除指定节点
	 */
	public void removeElem(Element elem) {
		if (elements.contains(elem)) {
			elements.remove(elem);
		}
	}


	/**
	 * 生成从 source 到 target 的有向边
	 * @param source 起点
	 * @param target 终点
	 * @throws GraphicException 加入该边可能存在回环异常，这该DAG中不允许
	 * @return 返回已添加的边
	 */
	public DirectedEdge addEdge(Element source, Element target) throws GraphicException{
		if (source==target) {
			throw new GraphicException("cycle exception by link itself!");
		}
		if (source.frontEles.contains(target)) {
			throw new GraphicException("edge from source to target exists");
		}
		//@TODO 待完成

		//检查完成，添加边
		DirectedEdge edge = new DirectedEdge(target);
		source.frontEles.add(edge);
		target.backEles.add(source);

		return edge;
	}


	/**
	 * 返回拓扑排序序列，算法简单而粗暴，仅用于前期开发，没时间作什么优化。
	 * @return 拓扑排序序列
	 */
	public List<Element> topolSort() {
		List<Element> resElements = new ArrayList<>();
		//找出完前任都不在结果列表中即可
		boolean stateHasChangeFlag = true;
		while (stateHasChangeFlag) {
			stateHasChangeFlag = false;
			for (Element element : elements) {
				// 已加入的不检查
				if (resElements.contains(element)) {
					break;
				}
				//前任都不在结果列表中
				boolean hasBackNotInRes = false;
				for (Element backEle : element.backEles) {
					if (!resElements.contains(backEle)) {
						hasBackNotInRes = true;
						break;
					}
				}
				//没有前任则加入
				if (!hasBackNotInRes) {
					resElements.add(element);
					//状态有变化，继续检查
					stateHasChangeFlag = true;
				}
			}
		}
		return resElements;
	}

	public Set<Element> getElements() {
		return elements;
	}
}
