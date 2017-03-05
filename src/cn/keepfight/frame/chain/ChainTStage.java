package cn.keepfight.frame.chain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.keepfight.frame.ContextMaster;
import cn.keepfight.frame.ContextSlave;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * ���������
 * @author Tom
 *
 */
public class ChainTStage extends TStage<ChainDataSource,
			ChainMenuViewController, ChainPaneController> implements ContextMaster{

	/**
	 * ʹ��˫��ϣ�������߼���໥ӳ��
	 * @TODO �˴���Ľ�
	 */
	Map<ContextSlave, Resource> stageMapResource = new HashMap<>();
	Map<Resource, ContextSlave> resourceMapStage = new HashMap<>();

	@Override
	protected void fixAfter() {
		//���ѡ��������
		getScene().getStylesheets().add( getClass().getResource("drag/highlight.css").toExternalForm());

		// ���ر��ز˵�
		getMenuVC().loadLocalMenu();
	}

	@Override
	public void update(ContextSlave slave) {

	}

	@Override
	public void onDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("ȷ�϶Ի���");
		alert.setHeaderText("����������Դ�ڸ�����ѱ�ɾ�����Ƿ񱣴浱ǰ������ݣ�");
		alert.setContentText("�����رո��������Ӧ�����и���������Դ�ĸ���壬"
				+ "�Ƿ񱣴�����������������ڱ༭�����ݷ�����ʧ��"
				+ "��� �� �����������ݣ���� �� �����棬ֱ�ӹرգ�");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			System.out.println("click OK");
		} else {
			System.out.println("click cancel");
			close();
		}
	}

	@Override
	public DataSource doOperate(ContextSlave slave, Resource operator, Resource result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataSource> doOperate(ContextSlave slave, Resource operator, List<Resource> results) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataSource> doOperate(List<ContextSlave> slaves, Resource operator, List<Resource> results) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasContain(ContextSlave slave) {
		return stageMapResource.containsKey(slave);
	}

	public boolean hasContain(Resource resource) {
		return resourceMapStage.containsKey(resource);
	}

	protected void addMap(Resource resource, ContextSlave slave){
		resourceMapStage.put(resource, slave);
		stageMapResource.put(slave, resource);
	}
}
