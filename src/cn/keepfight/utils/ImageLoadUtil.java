package cn.keepfight.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * ͼƬ���ع�����.
 * @author Tom
 *
 */
public class ImageLoadUtil {

	public static final int IMG_SIZE_16 = 16;
	public static final int IMG_SIZE_32 = 32;

	/**
	 * ͼ�񻺴�
	 */
	public static Map<String, Image> reusableImageMap = new HashMap<>();

	/**
	 * ����ָ���ߴ��ͼƬ�����ּ��ػ��ƻỺ���Ѽ�����ɵ�ͼƬ��<br/>
	 * ���ڵڶ���������ͬ��ͼƬ����ֱ��ʹ��֮ǰ���ص�ͼƬ�ļ������ǴӴ������ټ���һ��
	 *
	 * @param imageName
	 *            ָ������
	 * @param size
	 *            ָ���ߴ磬��֧�� {@link #reusableImage16Map} ��
	 *            {@link #reusableImage32Map}
	 * @return ͼƬ���������󲻴��ڷ���null��
	 */
	public static Image load(String imageName, int size) {
		if (size == IMG_SIZE_16 || size == IMG_SIZE_32) {
			if (reusableImageMap.containsKey("g" + size + "/" + imageName)) {
				return reusableImageMap.get("g" + size + "/" + imageName);
			} else {
				// @TODO ��������
				String imageUrl = "cn/keepfight/resources/g" + size + "/" + imageName;
				Image resImage = null;
				try {
					resImage = new Image(imageUrl, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				reusableImageMap.put("g" + size + "/" + imageName, resImage);
				return resImage;
			}
		}
		return null;
	}
}
