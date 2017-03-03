package cn.keepfight.frame.content.source;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 表格数据的数据源类. 这是一个抽象方法，在使用该类构造抽象类之前，请注意实现如下方法：
 *
 * @author Tom
 *
 */
public abstract class TableDataSource implements DataSource{

	@Override
	public DataSourceType getSourceType() {
		return DataSourceType.TABLE;
	}

	/**
	 * 欲构造的数据源列数
	 *
	 * @return 欲构造的数据源列数
	 */
	public abstract int getColumnNum();

	/**
	 * 获得全部行数，注意，该行数不包括列头。
	 * @return 全部行数
	 */
	public abstract int getRowNum() ;

	/**
	 * 该数据源是否存在表头数据，该方法用于指明是否使用默认表头。
	 * @return 存在返回 true，否则返回false。
	 */
	public abstract boolean hasHead();

	/**
	 * 获得表格数据的列表头。
	 * @return 列表头。
	 */
	public abstract ObservableList<StringProperty> getHeadList();

	/**
	 * 允许在数据源不提供标题头的情况下获得标题头数据，如果使用默认的标题头：column_0...。
	 * @return 标题头数据
	 * @throws InvalidSourceException 数据源无效异常。
	 */
	public ObservableList<StringProperty> getHeadListWithDefault() throws InvalidSourceException{
		if (hasHead()) {
			ObservableList<StringProperty> headList = getHeadList();
			if (headList == null) {
				throw new InvalidSourceException("invalid head list!");
			}
			return headList;
		}
		int columnNum = getColumnNum();
		if (columnNum < 1) {
			throw new InvalidSourceException("invalid columnNum!");
		}
		ObservableList<StringProperty> resList = FXCollections.observableArrayList();
		for (int i = 0; i < columnNum; i++) {
			resList.add(new SimpleStringProperty("column_"+i));
		}
		return resList;
	}

	/**
	 * 获得指定起始和指定范围的行内容。<br/>
	 * <pre>
	 * getRowList(1, 10); //获得1~10行内容
	 * getRowList(11, 10);//获得11~20行内容
	 * </pre>
	 * 我们约定从 1 计起，即startRow 参数应大于等于 1，且参数 limit 应大于1。
	 *
	 * @param startRow 获得表指定的范围内容的起始行号，最小为 1。
	 * @param limit 获得表指定内容的范围。
	 * @return 返回指定的内容列表，参数无效则返回 null。若数据源无数据，则返回大小为0的 List 对象。
	 */
	public abstract List<ObservableList<StringProperty>> getRowList(int startRow, int limit);
}
