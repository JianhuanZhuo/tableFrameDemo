package cn.keepfight.frame.content.source;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ������ݵ�����Դ��. ����һ�����󷽷�����ʹ�ø��๹�������֮ǰ����ע��ʵ�����·�����
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
	 * �����������Դ����
	 *
	 * @return �����������Դ����
	 */
	public abstract int getColumnNum();

	/**
	 * ���ȫ��������ע�⣬��������������ͷ��
	 * @return ȫ������
	 */
	public abstract int getRowNum() ;

	/**
	 * ������Դ�Ƿ���ڱ�ͷ���ݣ��÷�������ָ���Ƿ�ʹ��Ĭ�ϱ�ͷ��
	 * @return ���ڷ��� true�����򷵻�false��
	 */
	public abstract boolean hasHead();

	/**
	 * ��ñ�����ݵ��б�ͷ��
	 * @return �б�ͷ��
	 */
	public abstract ObservableList<String> getHeadList();

	/**
	 * ����������Դ���ṩ����ͷ������»�ñ���ͷ���ݣ����ʹ��Ĭ�ϵı���ͷ��column_0...��
	 * @return ����ͷ����
	 * @throws InvalidSourceException ����Դ��Ч�쳣��
	 */
	public ObservableList<String> getHeadListWithDefault() throws InvalidSourceException{
		if (hasHead()) {
			ObservableList<String> headList = getHeadList();
			if (headList == null) {
				throw new InvalidSourceException("invalid head list!");
			}
			return headList;
		}
		int columnNum = getColumnNum();
		if (columnNum < 1) {
			throw new InvalidSourceException("invalid columnNum!");
		}
		ObservableList<String> resList = FXCollections.observableArrayList();
		for (int i = 0; i < columnNum; i++) {
			resList.add("column_"+i);
		}
		return resList;
	}

	/**
	 * ���ָ����ʼ��ָ����Χ�������ݡ�<br/>
	 * <pre>
	 * getRowList(1, 10); //���1~10������
	 * getRowList(11, 10);//���11~20������
	 * </pre>
	 * ����Լ���� 1 ���𣬼�startRow ����Ӧ���ڵ��� 1���Ҳ��� limit Ӧ����1��
	 *
	 * @param startRow ��ñ�ָ���ķ�Χ���ݵ���ʼ�кţ���СΪ 1��
	 * @param limit ��ñ�ָ�����ݵķ�Χ��
	 * @return ����ָ���������б�������Ч�򷵻� null��������Դ�����ݣ��򷵻ش�СΪ0�� List ����
	 */
	public abstract List<ObservableList<String>> getRowList(int startRow, int limit);
}
