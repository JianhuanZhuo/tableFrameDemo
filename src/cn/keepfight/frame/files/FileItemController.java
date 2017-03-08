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
	 * Ϊ���ļ�Item��ť��������
	 *
	 * @param text
	 *            �����õ�����
	 */
	public void setText(String text) {
		if (text != null) {
			btn.setText(text);
		}
	}

	/**
	 * ʹ��ͼ�����������á�<br/>
	 * �÷�����ʹ��{@link cn.keepfight.utils.ImageLoaderUtil.load(String, int)}
	 * ���м���<br/>
	 *
	 * @param icon
	 *            ͼ����
	 * @see cn.keepfight.utils.ImageLoadUtil
	 */
	public void setPic(String icon) {
		setPic(ImageLoadUtil.load(icon, ImageLoadUtil.IMG_SIZE_32));
	}

	/**
	 * ָ��ͼƬ��Ϊ��FileItemͼ�ꡣ
	 *
	 * @param image
	 *            ָ��ͼƬ
	 */
	public void setPic(Image image) {
		pic.setImage(image);
	}



	/**
	 * ������ʾ��Ϣ
	 *
	 * @param tip
	 *            �����õ���ʾ��Ϣ
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
