package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.List;

public class SampleChainDataSource extends ChainDataSource{

	/**
	 * 工作代号
	 */
	private String workCodeName;

	private List<ResourceWithPosition> resources = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();


	public SampleChainDataSource(String workCodeName) {
		this.workCodeName = workCodeName;

//		{
//			ResourceWithPosition resource = new ResourceWithPosition();
//			OperatorResource operatorResource = new OperatorResource(0, "translate");
//			operatorResource.setIcon("translate.png");
//			operatorResource.setLabel("转换表");
//			operatorResource.setDescription("换算表是:"
//					+ "计算人身保险的保险费、现金价值和准备金时，"
//					+ "将运算过程中有规律的数值预先算好编制成表格，"
//					+ "便于随时查对并代入计算，从而简化繁复的运算程序，"
//					+ "这种表格即为换算表。————————说笑的，这是样本而已");
//
//			resource.resource = operatorResource;
//			resource.x = 50;
//			resource.y = 50;
//
//			resources.add(resource);
//		}
//		{
//			ResourceWithPosition resource = new ResourceWithPosition();
//			TableResource operatorResource = new TableResource("wz", "出行数据");
//			resource.resource = operatorResource;
//			resource.x = 100;
//			resource.y = 50;
//
//			resources.add(resource);
//		}
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
