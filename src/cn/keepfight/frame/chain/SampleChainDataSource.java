package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.List;

public class SampleChainDataSource extends ChainDataSource{

	/**
	 * ��������
	 */
	private String workCodeName;

	private List<ResourceWithPosition> resources = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();


	public SampleChainDataSource(String workCodeName) {
		this.workCodeName = workCodeName;

		ResourceWithPosition resource = new ResourceWithPosition();
		OperatorResource operatorResource = new OperatorResource(0, "translate");
		operatorResource.setIcon("translate.png");
		operatorResource.setLabel("ת����");
		operatorResource.setDescription("�������:"
				+ "���������յı��շѡ��ֽ��ֵ��׼����ʱ��"
				+ "������������й��ɵ���ֵԤ����ñ��Ƴɱ��"
				+ "������ʱ��Բ�������㣬�Ӷ��򻯷������������"
				+ "���ֱ��Ϊ���������������������˵Ц�ģ�������������");

		resource.resource = operatorResource;
		resource.x = 50;
		resource.y = 50;

		resources.add(resource);
	}

	@Override
	public String getSourceIDName() {
		return workCodeName;
	}

	@Override
	public List<ResourceWithPosition> getResources() {
		return resources;
	}

	@Override
	public List<Edge> getEdges() {
		return edges;
	}
}
