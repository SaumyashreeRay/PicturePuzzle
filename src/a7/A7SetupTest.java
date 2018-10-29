package a7;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class A7SetupTest {
	public static void main(String[] args) throws IOException {
		//Picture p = A7Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
		 Picture p =
		 A7Helper.readFromURL("http://s1125.photobucket.com/user/QuidHD/media/IMG_0864.png");
		// Picture p =
		// A7Helper.readFromURL("http://vignette4.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20140628142636");
		// Picture p =
		// A7Helper.readFromURL("http://cdn2.bigcommerce.com/n-arxsrf/xhb95jcc/product_images/uploaded_images/rainbow.jpg?t=1453387370");

		SimplePictureViewWidget widget = new SimplePictureViewWidget(p, "K to the M to the P");
		String title = "Assignment 7 Simple Picture View";

		// PixelInspectorUI widget = new PixelInspectorUI (p);
		// String title = "Assignment 7 Pixel Inspector";

		// ImageAdjuster widget = new ImageAdjuster(p);
		// String title = "Assignment 7 Image Adjuster";

		// FramePuzzleWidgetUI widget = new FramePuzzleWidgetUI(p);
		// String title = "Assignment 7 Frame Puzzle";

		JFrame main_frame = new JFrame();
		main_frame.setTitle(title);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		// top_panel.add(widget, BorderLayout.WEST);
		top_panel.add(widget, BorderLayout.SOUTH);

		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}