package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.chain.ChainDataSource;
import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.content.source.TableDataSource;
import cn.keepfight.frame.operator.OperatorDataSource;

/**
 * ��ʾ��幤���࣬��Ӧ {@link cn.keepfight.frame.content.source.DataSourceType} �����������ʵ����
 *
 * @author Tom
 *
 */
public class FrameFactory {

	/**
	 * ָ������Դ���ͽ�������չʾ���
	 * @param source ����Դ
	 * @return չʾ���
	 */
	@SuppressWarnings({ "rawtypes" })
	public static TStage generateBySource(DataSource source) throws InvalidSourceException, IOException {
		source.checkValid();
		TStage resTStage = null;
		switch (source.getSourceType()) {
		case TABLE:
			resTStage = new TableTStage();
			((TableTStage)resTStage).InitSource((TableDataSource)source);
			break;
		case OPERATOR:
			resTStage = new OperatorTStage();
			((OperatorTStage)resTStage).InitSource((OperatorDataSource)source);
			break;
		case OPERATORCHAIN:
			resTStage = new ChainTStage();
			((ChainTStage)resTStage).InitSource((ChainDataSource)source);
			break;
		default:
			break;
		}
		return resTStage;
	}
}
