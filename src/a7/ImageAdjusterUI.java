package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.lang.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjusterUI extends JPanel implements ChangeListener {

	private PictureView picture_view;
	private JPanel adjuster;

	private JSlider blur_change;
	private JLabel blur;

	private JSlider saturation_change;
	private JLabel saturation;

	private JSlider brightness_change;
	private JLabel bright;

	private Picture original;

	public ImageAdjusterUI(Picture picture) {
		original = picture;

		setLayout(new BorderLayout());

		picture_view = new PictureView(picture.createObservable());
		// picture_view.addMouseListener(this);

		add(picture_view, BorderLayout.CENTER);

		adjuster = new JPanel();
		adjuster.setLayout(new GridLayout(3, 1));

		blur = new JLabel(" Blur: ");
		blur_change = new JSlider(0, 5, 0);
		blur_change.addChangeListener(this);
		blur_change.setPaintTicks(true);
		blur_change.setPaintLabels(true);
		blur_change.setMajorTickSpacing(1);
		blur_change.setSnapToTicks(true);

		saturation = new JLabel(" Saturation: ");
		saturation_change = new JSlider(-100, 100, 0);
		saturation_change.addChangeListener(this);
		saturation_change.setMajorTickSpacing(25);
		saturation_change.setPaintTicks(true);
		saturation_change.setPaintLabels(true);

		bright = new JLabel(" Brightness: ");
		brightness_change = new JSlider(-100, 100, 0);
		brightness_change.addChangeListener(this);
		brightness_change.setPaintTicks(true);
		brightness_change.setPaintLabels(true);
		brightness_change.setMajorTickSpacing(50);

		adjuster.add(blur);
		adjuster.add(blur_change);
		adjuster.add(saturation);
		adjuster.add(saturation_change);
		adjuster.add(bright);
		adjuster.add(brightness_change);
		add(adjuster, BorderLayout.SOUTH);

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Picture temp = new PictureImpl(original.getWidth(), original.getHeight());
		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {
				temp.setPixel(i, j, original.getPixel(i, j));
			}
		}
		for (int i = 0; i < temp.getWidth(); i++) {
			for (int j = 0; j < temp.getHeight(); j++) {
				 
				Pixel changed = blurring(original, (ColorPixel) original.getPixel(i,j), blur_change.getValue(), i, j);
				Pixel changed2 = brightening((ColorPixel) changed, brightness_change.getValue());
				Pixel changed3 = saturating ((ColorPixel)changed2, saturation_change.getValue(), original.getPixel(i, j).getIntensity() );
				temp.setPixel(i, j, changed3);
			}

		}
		picture_view.setPicture(temp.createObservable());

	}

	public Pixel blurring(Picture original, ColorPixel p, int blurValue, int x, int y) {

		double redAverage, greenAverage, blueAverage;
		double redSum = 0.0, greenSum = 0.0, blueSum = 0.0;
		double count = 0.0;

		int startX = x - blurValue;
		int endX = x + blurValue;
		int startY = y - blurValue;
		int endY = y + blurValue;
		if(blurValue == 0){
			return p;
		}
		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				if ((i >= 0 && i < original.getWidth()) && (j >= 0 && j < original.getHeight())) {
					if (i != x || j != y) {
						redSum += original.getPixel(i, j).getRed();
						greenSum += original.getPixel(i, j).getGreen();
						blueSum += original.getPixel(i, j).getBlue();
						count++;
					}

				}
			}
		}

		redAverage = redSum / count;
		greenAverage = greenSum / count;
		blueAverage = blueSum / count;

		Pixel blurred = new ColorPixel(redAverage, greenAverage, blueAverage);
		//System.out.println("Red " + redAverage +  "Green " + greenAverage + "Blue "+ blueAverage);
		return blurred;
	}

	public Pixel saturating(Pixel p, int f, double b) {

		double newRed = 0.0, newGreen = 0.0, newBlue = 0.0;
		if ((p.getRed() == 0.0 && p.getGreen() == 0.0) && p.getBlue() == 0.0) { 
			return p;
		}
		
		if (f < 0) {
			// new = old * (1.0 + (f / 100.0) ) - (b * f / 100.0)
			newRed = p.getRed() * (1.0 + (f / 100.0)) - (b * f / 100.0);
			newGreen = p.getGreen() * (1.0 + (f / 100.0)) - (b * f / 100.0);
			newBlue = p.getBlue() * (1.0 + (f / 100.0)) - (b * f / 100.0);

		} else if (f>0){
			// new = old * ((a + ((1.0 - a) * (f / 100.0))) / a)
			double a = Math.max(Math.max(p.getRed(), p.getGreen()), p.getBlue());
			newRed = p.getRed() * ((a + ((1.0 - a) * (f / 100.0))) / a);
			newGreen = p.getGreen() * ((a + ((1.0 - a) * (f / 100.0))) / a);
			newBlue = p.getBlue() * ((a + ((1.0 - a) * (f / 100.0))) / a);
		}
		else{
			return p;
		}
		Pixel saturated = new ColorPixel(newRed, newGreen, newBlue);
		return saturated;

	}

	public Pixel brightening(ColorPixel p, double value) {
		value = value / 100.0;
		ColorPixel white = new ColorPixel(1.0, 1.0, 1.0);
		ColorPixel black = new ColorPixel(0.0, 0.0, 0.0);

		if (value > 0) {
			return p.blend(white, 1 - value);
		} else if (value < 0) {
			return p.blend(black, 1 - Math.abs(value));
		} else
			return p;
	}

}
