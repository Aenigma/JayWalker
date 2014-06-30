package org.compbox.jaywalker.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import org.compbox.jaywalker.JayWalker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JayGooey extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID;
	static {
		serialVersionUID = -5655836705393084573L;
	}
	private final JFileChooser chooser;
	private final JTextField textData;
	private final JTextField textReplDir;
	private final JTextField textReplFile;
	private final JButton btnGo;
	private boolean testing;
	private JTextArea areaOutput;

	public JayGooey(boolean testing) {
		// pre-procesing
		this.testing = testing;
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getenv("HOMEPATH")
				+ "/Dropbox"));

		getContentPane().setLayout(new BorderLayout(0, 0));
		ActionListener browseListener = new BrowseButtonListener();
		ActionListener buttonListener = new ButtonListener();

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 20, 0, 0, 0, 20, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 100, 35, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblDataDirectory = new JLabel("Data Directory");
		lblDataDirectory
				.setToolTipText("<html><p>The data directory is the directory in which files will be replaced.</p><p>If you value anything in this directory, make absolutely certain that the directory is backed up or you WILL lose your files</p></html>");
		GridBagConstraints gbc_lblDataDirectory = new GridBagConstraints();
		gbc_lblDataDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblDataDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataDirectory.gridx = 1;
		gbc_lblDataDirectory.gridy = 1;
		panel.add(lblDataDirectory, gbc_lblDataDirectory);

		textData = new JTextField();
		textData.setEditable(false);
		GridBagConstraints gbc_textData = new GridBagConstraints();
		gbc_textData.insets = new Insets(0, 0, 5, 5);
		gbc_textData.fill = GridBagConstraints.HORIZONTAL;
		gbc_textData.gridx = 2;
		gbc_textData.gridy = 1;
		panel.add(textData, gbc_textData);
		textData.setColumns(10);

		JButton btnDataDir = new JButton("Browse");
		btnDataDir.setActionCommand("DATA_DIR");
		btnDataDir.addActionListener(browseListener);
		GridBagConstraints gbc_btnDataDir = new GridBagConstraints();
		gbc_btnDataDir.insets = new Insets(0, 0, 5, 5);
		gbc_btnDataDir.gridx = 3;
		gbc_btnDataDir.gridy = 1;
		panel.add(btnDataDir, gbc_btnDataDir);

		JLabel lblReplacementDirectory = new JLabel("Replacement Directory");
		lblReplacementDirectory
				.setToolTipText("<html><p>Every file in the replacement directory is collected to be used to replace files in the data directory.</p><p>A third of files in the data directory will be replaced with random files from the replacement directory.</p></html>");
		GridBagConstraints gbc_lblReplacementDirectory = new GridBagConstraints();
		gbc_lblReplacementDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblReplacementDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_lblReplacementDirectory.gridx = 1;
		gbc_lblReplacementDirectory.gridy = 2;
		panel.add(lblReplacementDirectory, gbc_lblReplacementDirectory);

		textReplDir = new JTextField();
		textReplDir.setEditable(false);
		GridBagConstraints gbc_textReplDir = new GridBagConstraints();
		gbc_textReplDir.insets = new Insets(0, 0, 5, 5);
		gbc_textReplDir.fill = GridBagConstraints.HORIZONTAL;
		gbc_textReplDir.gridx = 2;
		gbc_textReplDir.gridy = 2;
		panel.add(textReplDir, gbc_textReplDir);
		textReplDir.setColumns(10);

		JButton btnReplDir = new JButton("Browse");
		btnReplDir.setActionCommand("REPLACEMENT_DIR");
		btnReplDir.addActionListener(browseListener);
		GridBagConstraints gbc_btnReplDir = new GridBagConstraints();
		gbc_btnReplDir.insets = new Insets(0, 0, 5, 5);
		gbc_btnReplDir.gridx = 3;
		gbc_btnReplDir.gridy = 2;
		panel.add(btnReplDir, gbc_btnReplDir);

		JLabel lblReplacementFile = new JLabel("Replacement File");
		lblReplacementFile.setToolTipText("<html>"
				+ "<p>The replacement file is the file that "
				+ "replaces all files within any directory called "
				+ "\"autism\".</p><p>It is strongly recommended that "
				+ "you use a picture of Jay here.</p></html>");
		GridBagConstraints gbc_lblReplacementFile = new GridBagConstraints();
		gbc_lblReplacementFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblReplacementFile.anchor = GridBagConstraints.EAST;
		gbc_lblReplacementFile.gridx = 1;
		gbc_lblReplacementFile.gridy = 3;
		panel.add(lblReplacementFile, gbc_lblReplacementFile);

		textReplFile = new JTextField();
		textReplFile.setToolTipText("");
		textReplFile.setEditable(false);
		GridBagConstraints gbc_textReplFile = new GridBagConstraints();
		gbc_textReplFile.insets = new Insets(0, 0, 5, 5);
		gbc_textReplFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_textReplFile.gridx = 2;
		gbc_textReplFile.gridy = 3;
		panel.add(textReplFile, gbc_textReplFile);
		textReplFile.setColumns(10);

		JButton btnReplFile = new JButton("Browse");
		btnReplFile.setToolTipText("");
		btnReplFile.setActionCommand("REPLACEMENT_FILE");
		btnReplFile.addActionListener(browseListener);
		GridBagConstraints gbc_btnReplFile = new GridBagConstraints();
		gbc_btnReplFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnReplFile.gridx = 3;
		gbc_btnReplFile.gridy = 3;
		panel.add(btnReplFile, gbc_btnReplFile);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		panel.add(scrollPane, gbc_scrollPane);

		areaOutput = new JTextArea();
		areaOutput.setEditable(false);
		scrollPane.setViewportView(areaOutput);

		btnGo = new JButton("Launch");
		btnGo.setToolTipText("Becomes available when the three files are selected above.");
		btnGo.setActionCommand("LAUNCH");
		btnGo.setEnabled(false);
		btnGo.addActionListener(buttonListener);
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.gridwidth = 3;
		gbc_btnGo.insets = new Insets(0, 0, 0, 5);
		gbc_btnGo.gridx = 1;
		gbc_btnGo.gridy = 5;
		panel.add(btnGo, gbc_btnGo);

		// post processing
		this.setUpFrame();
	}

	public JayGooey(String operationDirectory, String replacementDirectory,
			String replacementFile, boolean testing) {
		this(testing);
		textData.setText(operationDirectory);
		textReplDir.setText(replacementDirectory);
		textReplFile.setText(replacementFile);
		
		launchCheck();
	}

	private void setUpFrame() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getClassLoader().getResource(
						"org/compbox/jaywalker/gui/assets/bigjay.png")));
		if(this.testing) {
			this.setTitle("Jay Walker - Testing Mode");
			StringBuilder disclaimer = new StringBuilder();
			disclaimer.append("This program will not rip apart directories.\n");
			disclaimer.append("This is a dysfunctional version as a demo.\n");
			disclaimer.append("Clicking the Launch button will show you what\n");
			disclaimer.append("changes would have happened in the finished\n");
			disclaimer.append("product. Have fun!");
			areaOutput.setText(disclaimer.toString());
		} else {
			this.setTitle("Jay Walker - Destruction Mode");
		}
		this.pack();
		this.setMinimumSize(this.getPreferredSize());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private synchronized String getFilePathString(String dialogTitle, int mode)
			throws NoFileChosenException {
		chooser.setDialogTitle(dialogTitle);
		chooser.setFileSelectionMode(mode);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile().toString();
		else
			throw new NoFileChosenException();
	}

	private void launchCheck() {
		btnGo.setEnabled(!(textData.getText().equals("")
				|| textReplDir.getText().equals("") || textReplFile
				.getText().equals("")));
	}
	
	private class BrowseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (BrowseButtonActions.valueOf(e.getActionCommand())) {
			case DATA_DIR:
				dataDirectory();
				break;
			case REPLACEMENT_DIR:
				replDirectory();
				break;
			case REPLACEMENT_FILE:
				replFile();
				break;
			}
			launchCheck();
		}

		private void dataDirectory() {
			try {
				textData.setText(getFilePathString("Select the data directory",
						JFileChooser.DIRECTORIES_ONLY).toString());
			} catch (NoFileChosenException e) {
				// if an exception is thrown, do not update the text field
			}
		}

		private void replDirectory() {
			try {
				textReplDir.setText(getFilePathString(
						"Select the replacement directory",
						JFileChooser.DIRECTORIES_ONLY).toString());
			} catch (NoFileChosenException e) {
				// if an exception is thrown, do not update the text field
			}
		}

		private void replFile() {
			try {
				textReplFile.setText(getFilePathString(
						"Select a replacement file", JFileChooser.FILES_ONLY)
						.toString());
			} catch (NoFileChosenException e) {
				// if an exception is thrown, do not update the text field
			}
		}

	}

	enum BrowseButtonActions {
		DATA_DIR, REPLACEMENT_DIR, REPLACEMENT_FILE
	};

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (ButtonActions.valueOf(e.getActionCommand())) {
			case LAUNCH:
				launch();
			}
		}

		private void launch() {
			final Path dataPath;
			final Path replDirPath;
			final Path replFilePath;
			try {
				dataPath = Paths.get(textData.getText());
				replDirPath = Paths.get(textReplDir.getText());
				replFilePath = Paths.get(textReplFile.getText());

				String results = JayWalker.existCheck(dataPath, replDirPath,
						replFilePath);
				if (!results.equals("")) {
					JOptionPane.showMessageDialog(null,
							"Problem finding files. Please fix the following"
									+ "issues:\n" + results,
							"File Does Not Exist", JOptionPane.ERROR_MESSAGE);
				} else {
					JayWalker walker = new JayWalker(dataPath, replDirPath,
							replFilePath, testing);
					areaOutput.setText(walker.walk());
				}
			} catch (InvalidPathException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"Invalid File", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	enum ButtonActions {
		LAUNCH
	};
}
