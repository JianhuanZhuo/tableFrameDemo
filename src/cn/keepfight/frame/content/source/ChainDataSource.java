package cn.keepfight.frame.content.source;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.model.OperatorModel;

/**
 * ����������Դ��
 * @author Tom
 *
 */
public class ChainDataSource implements DataSource{

	/**
	 * ��������
	 */
	String workCodeName;

	List<OperatorModel> opers = new ArrayList<>();

	public ChainDataSource(String workCodeName) {
		this.workCodeName = workCodeName;
	}

	@Override
	public DataSourceType getSourceType() {
		return DataSourceType.OPERATORCHAIN;
	}

	@Override
	public String getSourceIDName() {
		return workCodeName;
	}

	@Override
	public void checkValid() throws InvalidSourceException {

	}

}
