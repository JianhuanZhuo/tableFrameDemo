package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.keepfight.frame.chain.Element.DirectedEdge;

/**
 * �����޻�ͼ���߼��ṹ
 * @author Tom
 *
 */
public class DAGGraph {

	/**
	 * �ڵ㼯
	 */
	Set<Element> elements = new HashSet<Element>();

	/**
	 * ��ӽڵ�
	 * @param elem ����ӵĽڵ�
	 * @throws GraphicException �ڵ�Ϊ���쳣
	 */
	public void addElem(Element elem) throws GraphicException{
		//@TODO ��Ҫ�Ľ��Լ���Ƿ���Ϊ���ļ����������
		if (elem==null) {
			throw new GraphicException("elem added do not allow null!");
		}
		elements.add(elem);
	}

	/**
	 * ɾ���ڵ�
	 * @param elem ɾ��ָ���ڵ�
	 */
	public void removeElem(Element elem) {
		if (elements.contains(elem)) {
			elements.remove(elem);
		}
	}


	/**
	 * ���ɴ� source �� target �������
	 * @param source ���
	 * @param target �յ�
	 * @throws GraphicException ����ñ߿��ܴ��ڻػ��쳣�����DAG�в�����
	 * @return ��������ӵı�
	 */
	public DirectedEdge addEdge(Element source, Element target) throws GraphicException{
		if (source==target) {
			throw new GraphicException("cycle exception by link itself!");
		}
		if (source.frontEles.contains(target)) {
			throw new GraphicException("edge from source to target exists");
		}
		//@TODO �����

		//�����ɣ���ӱ�
		DirectedEdge edge = new DirectedEdge(target);
		source.frontEles.add(edge);
		target.backEles.add(source);

		return edge;
	}


	/**
	 * ���������������У��㷨�򵥶��ֱ���������ǰ�ڿ�����ûʱ����ʲô�Ż���
	 * @return ������������
	 */
	public List<Element> topolSort() {
		List<Element> resElements = new ArrayList<>();
		//�ҳ���ǰ�ζ����ڽ���б��м���
		boolean stateHasChangeFlag = true;
		while (stateHasChangeFlag) {
			stateHasChangeFlag = false;
			for (Element element : elements) {
				// �Ѽ���Ĳ����
				if (resElements.contains(element)) {
					break;
				}
				//ǰ�ζ����ڽ���б���
				boolean hasBackNotInRes = false;
				for (Element backEle : element.backEles) {
					if (!resElements.contains(backEle)) {
						hasBackNotInRes = true;
						break;
					}
				}
				//û��ǰ�������
				if (!hasBackNotInRes) {
					resElements.add(element);
					//״̬�б仯���������
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
