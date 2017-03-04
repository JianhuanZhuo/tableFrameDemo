package cn.keepfight.frame.chain;

import java.util.ArrayList;
import java.util.List;

public class SampleChainDataSource extends ChainDataSource{

	/**
	 * ¹¤×÷´úºÅ
	 */
	private String workCodeName;

	private List<ResourceWithPosition> resources = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();


	public SampleChainDataSource(String workCodeName) {
		this.workCodeName = workCodeName;
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
