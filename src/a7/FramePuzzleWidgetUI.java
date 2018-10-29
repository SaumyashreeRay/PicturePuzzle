package a7;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class FramePuzzleWidgetUI extends JPanel implements KeyListener, MouseListener {

	private Picture original;
	private int width;
	private int height;
	private PictureView[][] tile_array;
	private int tile_width;
	private int tile_height;
	private Pixel blank;
	private PictureView blankTile;
	private Picture blankPicture;
	public int blankTileX, blankTileY;
	private ArrayList<Picture> rainbow;

	FramePuzzleWidgetUI(Picture picture) {
		this.original = picture;
		width = picture.getWidth();
		height = picture.getHeight();

		setLayout(new GridLayout(5, 5));
		blank = new ColorPixel(1, 1, 1);
		tile_width = width / 5;
		tile_height = height / 5;

		blankTile = new PictureView(new PictureImpl(tile_width, tile_height).createObservable());
		blankPicture = new PictureImpl(tile_width, tile_height);
		Picture red = new PictureImpl(tile_width, tile_height);
		Picture yellow = new PictureImpl(tile_width, tile_height);
		Picture green = new PictureImpl(tile_width, tile_height);
		Picture blue = new PictureImpl(tile_width, tile_height);

		for (int i = 0; i < tile_width; i++) {
			for (int j = 0; j < tile_height; j++) {
				red.setPixel(i, j, new ColorPixel(0.92, 0.0039, 0.024));
				green.setPixel(i, j, new ColorPixel(0.99, 0.67, 0.03));
				yellow.setPixel(i, j, new ColorPixel(0.404, 0.714, 0.024));
				blue.setPixel(i, j, new ColorPixel(0.12, 0.54, 1.0)); 
			}
		}

		for (int i = 0; i < tile_width; i++) {
			for (int j = 0; j < tile_height; j++) {
				if (i < tile_width / 2 && j < tile_height / 2) {
					blankPicture.setPixel(i, j, red.getPixel(i, j));
				} else if (i < tile_width / 2 && j > tile_height / 2) {
					blankPicture.setPixel(i, j, blue.getPixel(i, j));
				} else if (i > tile_width / 2 && j > tile_height / 2) {
					blankPicture.setPixel(i, j, green.getPixel(i, j));
				} else if (i > tile_width / 2 && j < tile_height / 2) {
					blankPicture.setPixel(i, j, yellow.getPixel(i, j));
				}

			}
		}

		tile_array = new PictureView[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				tile_array[i][j] = new PictureView(
						original.extract(tile_width * i, tile_height * j, tile_width, tile_height).createObservable());
			}
		}
		tile_array[4][4] = blankTile;
		blankTileX = 4;
		blankTileY = 4;

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				tile_array[i][j].addKeyListener(this);
				tile_array[i][j].addMouseListener(this);
				tile_array[i][j].setFocusable(true);
				add(tile_array[i][j]);

			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (blankTileY < tile_height - 1) {
				setNewTile(tile_array, blankTileX, blankTileY, blankTileX, blankTileY + 1);
				blankTileY++;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (blankTileY > 0) {
				setNewTile(tile_array, blankTileX, blankTileY, blankTileX, blankTileY - 1);
				blankTileY--;
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (blankTileX > 0) {
				setNewTile(tile_array, blankTileX, blankTileY, blankTileX - 1, blankTileY);
				blankTileX--;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (blankTileX < tile_width - 1) {
				setNewTile(tile_array, blankTileX, blankTileY, blankTileX + 1, blankTileY);
				blankTileX++;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// need the tile index!
		PictureView picture_OG = (PictureView) e.getSource();

		int xClickedTile = 0, yClickedTile = 0;

		for (int j = 0; j < tile_array.length; j++) {
			for (int i = 0; i < tile_array[j].length; i++) {
				if (tile_array[i][j].equals(picture_OG)) {
					xClickedTile = i;
					yClickedTile = j;
				}
			}
		}

		int difference = 0;

		try {
			Robot r = new Robot();

			// row : both have y in common
			if (yClickedTile == blankTileY) {
				difference = Math.abs(xClickedTile - blankTileX);

				if (xClickedTile > blankTileX) { // left
					for (int i = 0; i < difference; i++) {
						r.keyPress(37);
						r.keyRelease(37);
					}
				} else { // right
					for (int i = 0; i < difference; i++) {
						r.keyPress(39);
						r.keyRelease(39);
					}
				}
			}
			// column: both have same x
			else if (xClickedTile == blankTileX) {
				difference = Math.abs(yClickedTile - blankTileY);

				if (yClickedTile > blankTileY) {
					for (int i = 0; i < difference; i++) { // up
						r.keyPress(38);
						r.keyRelease(38);
					}
				} else {
					for (int i = 0; i < difference; i++) { // down
						r.keyPress(40);
						r.keyRelease(40);
					}
				}
			}
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
		}

	}

	public void setNewTile(PictureView[][] tile_array, int sourceX, int sourceY, int newX, int newY) {
		tile_array[sourceX][sourceY].setPicture(tile_array[newX][newY].getPicture().createObservable());
		tile_array[newX][newY].setPicture(blankPicture.createObservable());
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
