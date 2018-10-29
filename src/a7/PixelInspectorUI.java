package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorUI extends JPanel implements MouseListener {
	
	private PictureView picture_view;
	private JLabel xCoordinate;
	private JLabel yCoordinate;
	private JLabel red;
	private JLabel green;
	private JLabel blue;
	private JLabel brightness;
	private Pixel _pixel;
	private Picture picture;
	JPanel pix;

	public PixelInspectorUI(Picture picture) {
		setLayout(new BorderLayout());
		
		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);
		add(picture_view, BorderLayout.CENTER);
		
		JLabel title_label = new JLabel(" Pixel Inspector");
		add(title_label, BorderLayout.SOUTH);
		 pix = new JPanel();
		 pix.setLayout(new GridLayout(6,1));
		
		xCoordinate = new JLabel(" X: ");
		yCoordinate = new JLabel(" Y: ");
		red = new JLabel(" Red: ");
		green = new JLabel(" Green: ");
		blue = new JLabel(" Blue: ");
		brightness = new JLabel (" Brightness: ");
		
		this.picture = picture;
		
		pix.add(xCoordinate);
		pix.add(yCoordinate);
		pix.add(red);
		pix.add(green);
		pix.add(blue);
		pix.add(brightness);
		add(pix, BorderLayout.WEST);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		xCoordinate.setText(" X: " + x);
		yCoordinate.setText(" Y: " + y);
		
		_pixel = picture.getPixel(x,y);
		
		double redVal = Math.round(_pixel.getRed() * 100.0) / 100.0;
		double greenVal = Math.round(_pixel.getGreen() * 100.0) / 100.0;
		double blueVal = Math.round(_pixel.getBlue() * 100.0) / 100.0;
		double brightnessVal = Math.round(_pixel.getIntensity() * 100.0) / 100.0;

		red.setText(" Red: " +  redVal);
		green.setText(" Green: " + greenVal);
		blue.setText(" Blue: " + blueVal);
		brightness.setText(" " + "Brightness: " + brightnessVal + " ");
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
