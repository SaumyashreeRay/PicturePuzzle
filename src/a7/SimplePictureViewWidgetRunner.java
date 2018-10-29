package a7;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimplePictureViewWidgetRunner {
	public static void main(String[] args) throws IOException {
		//Picture p = A7Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
		Picture p = A7Helper.readFromURL("http://images.mentalfloss.com/sites/default/files/styles/article_640x430/public/757px-van_gogh_-_starry_night_-_google_art_project_0.jpg");

		SimplePictureViewWidget widget = new SimplePictureViewWidget(p, "K to the M to the P");
		String title = "Assignment 7 Simple Picture View";

		JFrame main_frame = new JFrame();
		main_frame.setTitle(title);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(widget, BorderLayout.SOUTH);

		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}