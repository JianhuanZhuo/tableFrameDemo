package cn.keepfight.frame.chain;

import cn.keepfight.utils.ImageLoadUtil;
import javafx.scene.image.ImageView;

/**
 * ��Դ�ڵ�
 * @author Tom
 *
 */
public class ResourceElem extends Element{

	Resource resource;
	ImageView view;


	public ResourceElem(Resource resource) {
		//@TODO �������Ǹ�FXML��
	    view = new ImageView(ImageLoadUtil.load(resource.getIconURL()));
	    view.setFitWidth(32);
	    view.setFitHeight(32);
        getChildren().add(view);
        this.resource = resource;
	}

	public Resource getResource() {
		return resource;
	}
}
