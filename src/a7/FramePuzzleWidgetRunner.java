package a7;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FramePuzzleWidgetRunner {
	public static void main(String[] args) throws IOException {
		Picture p = A7Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
//		Picture p = A7Helper.readFromURL("https://support.apple.com/library/content/dam/edam/applecare/images/en_US/osx/mac-apple-logo-screen-icon.png");

		FramePuzzleWidgetUI widget = new FramePuzzleWidgetUI(p);
		String title = "Assignment 7 Frame Puzzle";

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