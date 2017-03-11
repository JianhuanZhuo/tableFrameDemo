package cn.keepfight.frame.files.operator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.keepfight.frame.chain.FilesResource;
import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.files.FilesTStage;
import cn.keepfight.frame.menu.ActionResult;
import cn.keepfight.operator.AbstractOperator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class FilesFilterOperator extends AbstractOperator{

	FilesTStage tStage;

	public FilesFilterOperator(FilesTStage tStage) {
		this.tStage = tStage;
	}

	private String[] inputResource;
	private String[] outputResource;
	private String[] params;

	@Override public int getId() { return 444; }
	@Override public String getName() { return "filter"; }
	@Override public String getLabel() { return "过滤"; }
	@Override public String getIcon() { return "filter.png"; }
	@Override public String getTips() { return "对当前文件群进行过滤操作"; }
	@Override public String getDescription() { return "这是对过滤操作不怎么详细的详细说明！"; }

	@Override
	public ActionResult onAction() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(getLabel());
		dialog.setHeaderText("为过滤算子设置过滤的关键字，该算子将仅输出所有包含关键字的文件！");
		dialog.setContentText("允许输入多个关键字，已逗号分隔：");

		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent() || result.get().trim().length()==0){
			new Alert(AlertType.WARNING, "格式错误", ButtonType.OK).show();
			return null;
		}

		String[] sp = result.get().split(",");

		//过滤文件
		List<File> resFileList = tStage.getSource().getFiles().parallelStream()
			.filter(f->{
				try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    br.lines()
						.anyMatch(line->
							Arrays.asList(sp).stream()
								.anyMatch(keyStr -> (line.indexOf(keyStr)!=-1))
						);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			})
			.collect(Collectors.toList());

		//包装结果

		Resource resultResource = new FilesResource(resFileList);
		List<Resource> resResources = new ArrayList<>();
		resResources.add(resultResource);

		/**
		 * @FIXME 这种做法是不规范的，需要进行一个规范抽象
		 */
	    params = sp;
		inputResource = new String[1];
		inputResource[0] = tStage.getSource().getSourceIDName();
		outputResource = new String[1];
		outputResource[0] = resultResource.getName();
		return new ActionResult(resResources);
	}


	@Override
	public OperatorResource generateResource() {
		OperatorResource res = super.generateResource();
		res.setInputResource(inputResource);
		res.setOutputResource(outputResource);
		res.setParams(params);
		return res;
	}

}
