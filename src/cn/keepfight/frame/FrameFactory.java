package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.chain.ChainDataSource;
import cn.keepfight.frame.chain.ChainTStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.operator.OperatorDataSource;
import cn.keepfight.frame.table.TableDataSource;
import cn.keepfight.frame.text.TextDataSource;
import cn.keepfight.frame.text.TextTStage;

/**
 * ��ʾ��幤���࣬��Ӧ {@link cn.keepfight.frame.content.source.DataSourceType} �����������ʵ����
 *
 * @author Tom
 *
 */
public class FrameFactory {

	/**
	 * ָ������Դ���ͽ�������չʾ���
	 * @param master �����
	 * @param source ����Դ
	 * @return չʾ���
	 */
	@SuppressWarnings({ "rawtypes" })
	public static TStage generateBySource(ContextMaster master, DataSource source) throws InvalidSourceException, IOException {
		source.checkValid();
		TStage resTStage = null;
		switch (source.getSourceType()) {
		case TABLE:
			resTStage = new TableTStage();
			((TableTStage)resTStage).initSource(master, (TableDataSource)source);
			break;
		case OPERATOR:
			resTStage = new OperatorTStage();
			((OperatorTStage)resTStage).initSource(master, (OperatorDataSource)source);
			break;
		case CHAIN:
			resTStage = new ChainTStage();
			((ChainTStage)resTStage).initSource(master, (ChainDataSource)source);
			break;
		case TEXT:
			resTStage = new TextTStage();
			((TextTStage)resTStage).initSource(master, (TextDataSource)source);
			break;
		default:
			break;
		}
		return resTStage;
	}
}
