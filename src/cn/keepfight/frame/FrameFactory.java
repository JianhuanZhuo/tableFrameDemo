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
 * 显示面板工厂类，对应 {@link cn.keepfight.frame.content.source.DataSourceType} 进行生成面板实例。
 *
 * @author Tom
 *
 */
public class FrameFactory {

	/**
	 * 指定数据源类型进行生成展示面板
	 * @param master 父面板
	 * @param source 数据源
	 * @return 展示面板
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
