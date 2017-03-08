package cn.keepfight.frame.chain;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.keepfight.frame.ContextMaster;
import cn.keepfight.frame.ContextSlave;
import cn.keepfight.frame.FrameFactory;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
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
	Map<ContextSlave, ResourceElem> stageMapElem = new HashMap<>();
	Map<ResourceElem, ContextSlave> elemMapStage = new HashMap<>();

	@Override
	protected void fixAfter() {
		//���ѡ��������
		getScene().getStylesheets().add( getClass().getResource("drag/highlight.css").toExternalForm());
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
	public void doOperate(ContextSlave slave, Resource operator, Resource result) {
		//����ū���ڵ�
		ResourceElem sElem = stageMapElem.get(slave);

		//������ӽڵ㡢����ڵ�
		ResourceElem operatorElem = getPaneVC().addResource(operator);
		ResourceElem eElem = getPaneVC().addResource(result);

		try {
			getPaneVC().addEdge(sElem, operatorElem);
			getPaneVC().addEdge(operatorElem, eElem);
		} catch (GraphicException e) {
			e.printStackTrace();
		}

		//���ؽ����Դ������Դ
		DataSource resDataSource = eElem.getResource().generateDataSource();
		/****************************************************************
		try {
			slave.reSetSource(resDataSource);
			elemMapStage.put(eElem, slave);
			stageMapElem.put(slave, eElem);
		} catch (InvalidSourceException | IOException e) {

		****************************************************************/
		try {
			ContextSlave newSlave = FrameFactory.generateBySource(this, resDataSource);
			addMap(eElem, newSlave);
			slave.close();
			newSlave.showup();
		} catch (InvalidSourceException | IOException e1) {
			System.err.println("Source InvalidSourceException!");
			e1.printStackTrace();
		}
		return ;
	}

	@Override
	public void doOperate(ContextSlave slave, Resource operator, List<Resource> results) {
		// TODO Auto-generated method stub
		return ;
	}

	@Override
	public void doOperate(List<ContextSlave> slaves, Resource operator, List<Resource> results) {
		// TODO Auto-generated method stub
		return ;
	}

	public boolean hasContain(ContextSlave slave) {
		return stageMapElem.containsKey(slave);
	}

	public boolean hasContain(ResourceElem elem) {
		return elemMapStage.containsKey(elem);
	}

	protected void addMap(ResourceElem elem, ContextSlave slave){
		elemMapStage.put(elem, slave);
		stageMapElem.put(slave, elem);
	}
}
