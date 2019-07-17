package Utility;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.*;
import javax.media.jai.*;
import com.sun.media.jai.codec.*;

/*
рограммы установки: Windows JDK Install и Windows JRE Install - провод€т копирование одних и тех же файлов:

    * checkmmx.exe
    * mlib_jai.dll
    * mlib_jai_mmx.dll

в каталог bin и

    * jai_codec.jar
    * jai_core.jar
    * mlibwrapper_jai.jar

в каталог lib\ext.

–азница заключаетс€ только в том, что установка дл€ JDK запросит у вас путь к установленному JDK (например, "C:\ j2sdk1.4.2_01"), а программа установки дл€ JRE запросит путь к JRE (например, "C:\ j2sdk1.4.2_01\jre" или "C:\Program Files\Java\j2re1.4.2_01").
ѕрограмма установки Windows CLASSPATH Install по-умолчанию предлагает сохранить весь JAI пакет в каталоге "C:\jai-1_1_2". «атем нам придетс€ к переменной окружени€ CLASSPATH вручную добавить путь "C:\jai-1_1_2\lib" (без кавычек). Ётот вариант установки менее удобен, чем установка дл€ JDK или JRE.

ћожно отметить, что наличие файлов checkmmx.exe, mlib_jai.dll и mlib_jai_mmx.dll не об€зательно, но их присутствие все-таки увеличивает скорость и качество работы JAI. 
*/

public class ImageUtils {
	private RenderedOp image = null;
	private RenderedOp result = null;
	private int height = 0;
	private int width = 0;
	
	/** загрузить изображение */
	public BufferedImage load(String file) throws IOException {
		FileSeekableStream fss = new FileSeekableStream(file);
		image = JAI.create("stream", fss);
		height = image.getHeight();
		width = image.getWidth();
		System.out.println("image:"+image);
		System.out.println("height:"+height);
		System.out.println("width:"+width);
		return image.getAsBufferedImage();
	}

	/** сохранить изображение на диск*/
	public void writeResult(String file, String type) throws IOException {
		FileOutputStream os = new FileOutputStream(file);
		JAI.create("encode", result, os, type, null);
	}

	/** создать уменьшенную копию */
	public BufferedImage thumbnail(float edgeLength) {
		boolean tall = (height > width);
		float modifier = edgeLength / (float) (tall ? height : width);
			
		ParameterBlock params = new ParameterBlock();
		params.addSource(image);
			
		params.add(modifier);//x scale factor
		params.add(modifier);//y scale factor
		params.add(0.0F);//x translate
		params.add(0.0F);//y translate
		params.add(new InterpolationNearest());//метод интерпол€ции 
		
		return JAI.create("scale", params).getAsBufferedImage();
	}

	/** обрезать изображение */
	public BufferedImage crop(float edge) {
		ParameterBlock params = new ParameterBlock();
		params.addSource(image);
			
		params.add(edge);//кол-во обрезаемых пикселей по x 
		params.add(edge);// кол-во обрезаемых пикселей по y
		params.add((float) width - edge);//ширина после обрезани€
		params.add((float) height - edge);//высота после обрезани€
		
		return JAI.create("crop", params).getAsBufferedImage();
	}

	/** рамка вокруг изображени€ */
	public BufferedImage border(int edge, double edgeColor) {
		ParameterBlock params = new ParameterBlock();
		params.addSource(image);
			
		params.add(edge);//ширина рамки слева
		params.add(edge);// ширина рамки справа
		params.add(edge);// ширина рамки сверху
		params.add(edge);// ширина рамки снизу
		double[] fill = {edgeColor};
		params.add(new BorderExtenderConstant(fill));//тип
		params.add(edgeColor);//цвет рамки
		
		return JAI.create("border", params).getAsBufferedImage();
	}
	

}
