package cn.keepfight.frame.chain;

import cn.keepfight.utils.ImageLoadUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ResourceElemController {

	@FXML
	private ImageView pic;

	@FXML
	private Button btn;

	@FXML
	GridPane paneRoot;

	public ResourceElemController() {
	}

	@FXML
	public void initialize() {
	}

	/**
	 * 为该菜单按钮设置文字
	 *
	 * @param btnText
	 *            欲设置的文字
	 */
	public void setBtnText(String btnText) {
		if (btnText != null) {
			btn.setText(btnText);
		}
	}

	/**
	 * 获得按钮的文字
	 *
	 * @return 若该菜单无按钮，则返回null，否则返回一个字符串，注意可能是个空字符串。
	 */
	public String getBtnText() {
		return btn.getText();
	}

	/**
	 * 使用图标名进行设置。<br/>
	 * 该方法将使用{@link cn.keepfight.utils.ImageLoaderUtil.load(String, int)}
	 * 进行加载<br/>
	 *
	 * @param icon
	 *            图标名
	 * @see cn.keepfight.utils.ImageLoadUtil
	 */
	public void setPic(String icon) {
		setPic(ImageLoadUtil.load(icon, ImageLoadUtil.IMG_SIZE_32));
	}

	/**
	 * 指定图片作为该节点按钮图标。
	 *
	 * @param image
	 *            指定图片
	 */
	public void setPic(Image image) {
		pic.setImage(image);
	}

	/**
	 * 设置提示信息
	 *
	 * @param tip
	 *            欲设置的提示信息
	 */
	public void setTipText(String tip) {
		if (tip != null) {
			btn.setTooltip(new Tooltip(tip));
		}
	}

	public GridPane getPaneRoot() {
		return paneRoot;
	}
}
