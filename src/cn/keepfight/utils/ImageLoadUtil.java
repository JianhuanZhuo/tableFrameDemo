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
	public static final int IMG_SIZE_64 = 64;

	/**
	 * ͼ�񻺴�
	 */
	public static Map<String, Image> reusableImageMap = new HashMap<>();

	public static Image load(String imageName) {
		return load(imageName, IMG_SIZE_32);
	}

	/**
	 * ����ָ���ߴ��ͼƬ�����ּ��ػ��ƻỺ���Ѽ�����ɵ�ͼƬ��<br/>
	 * ���ڵڶ���������ͬ��ͼƬ����ֱ��ʹ��֮ǰ���ص�ͼƬ�ļ������ǴӴ������ټ���һ��
	 *
	 * @param imageName
	 *            ָ������
	 * @param size
	 *            ָ���ߴ磬��֧�� {@link #reusableImage16Map} ��
	 *            {@link #reusableImage32Map}��{@link #reusableImage64Map}
	 * @return ͼƬ���������󲻴��ڷ���null��
	 */
	public static Image load(String imageName, int size) {
		if (size == IMG_SIZE_16 || size == IMG_SIZE_32 || size == IMG_SIZE_64) {
			if (reusableImageMap.containsKey("g" + size + "/" + imageName)) {
				return reusableImageMap.get("g" + size + "/" + imageName);
			} else {
				// @TODO ��������
				String imageUrl = "cn/keepfight/resources/g" + size + "/" + imageName;
				Image resImage = null;
				try {
					resImage = new Image(imageUrl);
				} catch (Exception e) {
					System.err.println("url is: "+imageUrl);
					e.printStackTrace();
				}
				reusableImageMap.put("g" + size + "/" + imageName, resImage);
				return resImage;
			}
		}
		return null;
	}
}
