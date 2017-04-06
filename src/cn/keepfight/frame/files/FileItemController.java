package cn.keepfight.frame.files;

import cn.keepfight.utils.ImageLoadUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class FileItemController {

	@FXML
	private ImageView pic;

	@FXML
	private Button btn;

	@FXML
	private GridPane paneRoot;

	/**
	 * 为该文件Item按钮设置文字
	 *
	 * @param text
	 *            欲设置的文字
	 */
	public void setText(String text) {
		if (text != null) {
			btn.setText(text);
		}
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
	 * 指定图片作为该FileItem图标。
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
