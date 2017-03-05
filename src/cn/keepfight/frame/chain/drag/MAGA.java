package cn.keepfight.frame.chain.drag;

import cn.keepfight.utils.ImageLoadUtil;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MAGA extends Pane implements Dragable{
    ImageView view;
    public MAGA( double width, double height, String image) {
        view = new ImageView( ImageLoadUtil.load(image));
        view.setFitWidth(width);
        view.setFitHeight(height);

        getChildren().add(view);

        this.setPrefSize(width, height);
    }


	@Override
	public void updatePosition() {
	}
}