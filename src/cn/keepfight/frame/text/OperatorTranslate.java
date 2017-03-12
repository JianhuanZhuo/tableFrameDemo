package cn.keepfight.frame.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.chain.TableResource;
import cn.keepfight.frame.connect.db.JDBCConnector;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.operator.WaitDialog;
import cn.keepfight.utils.SimpleSQLUtils;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceDialog;

public class OperatorTranslate extends AbstractOperator{

	private int id = 321;//����ID
	private String name = "translate";//������
	private String label = "��ת��";//����ͼ�갴ť�ϵ�����
	private String tips = "ʹ��ת�������ӽ����ı�ת��Ϊ����Դ�洢";//������ʾ��Ϣ
	private String icon = "translate.png";//����ͼ��
	private String description = "�����ַ����ɴ��ı��ļ��������ݣ�"
			+ "ʹ�� Microsoft Excel�U �������� Excel �д򿪵��ı��ļ���"
			+ "�������Ե�����ı��ļ���Ϊ�ⲿ����������Ҫ�����ݴ� Excel �������ı��ļ���"
			+ "ʹ�����Ϊ���";//����������Ϣ

	private String[] inputResource;
	private String[] outputResource;
	private String[] params;

	private TextTStage tStage;


	String reg;

	public OperatorTranslate(TextTStage tStage) {
		this.tStage = tStage;
	}


	@Override public int getId() { return id; }
	@Override public String getName() { return name; }
	@Override public String getLabel() { return label; }
	@Override public String getIcon() { return icon; }
	@Override public String getTips() { return tips; }
	@Override public String getDescription() { return description; }

	@Override
	public ActionResult onAction() {
		List<Resource> res = new ArrayList<Resource>();

		List<String> choices = new ArrayList<>();
		choices.add("����");
		choices.add("�Ʊ��");
		choices.add("�ո��");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("ѡ��ָ���", choices);
		dialog.setTitle("�ı��ļ�ת��Ϊ��");
		dialog.setHeaderText("��Ϊ������ѡ��һ���ָ���ţ�");
		dialog.setContentText("��ǰ�������ȿ��õķ���Ϊ��");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Your choice: " + result.get());
		    File file = tStage.getSource().getFile();
		    switch (result.get()) {
				case "����":
					reg=",";
					break;
				case "�Ʊ��":
					reg="\t";
					break;
				case "�ո��":
					reg=" ";
					break;
				default:
					break;
			}

			String tableName = new WaitDialog<String>(new Task<String>() {
				@Override
				protected String call() throws Exception {
					SimpleSQLUtils simpleSQL = SimpleSQLUtils.build(new JDBCConnector().getConnection()).setDB("wz");
					BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					String head = bf.readLine();
					bf.close();
					List<String> fs = Arrays.asList(head.split(",")).stream()
							.map(s->(s+" varchar(255)"))
							.collect(Collectors.toList());
					String tableName = "table_"+System.currentTimeMillis();
					simpleSQL.createTable(tableName, fs, null);
					simpleSQL.loadData(file.getAbsolutePath(), tableName, " FIELDS TERMINATED by '"+reg+"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES");

					return tableName;
				}
			}).justWait();

			TableResource resultResource = new TableResource("wz", tableName);
			res.add(resultResource);

		    params = new String[1];
		    params[0] = "spilt="+result.get();
			inputResource = new String[1];
			inputResource[0]=tStage.getSource().getSourceIDName();
			outputResource = new String[1];
			outputResource[0]=resultResource.getName();

			return new ActionResult(res);
		}

		return null;
	}

	public boolean justWait() {

		Task<List<String>> download = new Task<List<String>>() {

			@Override
			protected List<String> call() throws Exception {

				return null;
			}
		};

		download.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {


			}
		});

		return true;
	}


	@Override
	public OperatorResource generateResource() {
		OperatorResource newResource = new OperatorResource(id, name);
		newResource.setIcon(icon);
		newResource.setDescription(description);
		newResource.setLabel(label);
		newResource.setInputResource(inputResource);
		newResource.setOutputResource(outputResource);
		newResource.setParams(params);
		return newResource;
	}
}
