package Front_end;

import javax.swing.*;
import java.util.function.BiConsumer;

/**
 * Settings panel that includes theme selection and back/validate buttons.
 * Designed to be swapped into the main frame in place of the menu.
 */
public class Settings extends JPanel {
	private JComboBox<String> resolutionComboBox;
	private BiConsumer<Integer, Integer> onValidateClicked;

	/**
	 * Constructs the Settings panel with theme and resolution dropdowns,
	 * plus Back and Validate buttons.
	 */
	public Settings() {
		setOpaque(false);
		// Resolution selection
		int[][] resolutions = new int[][] {
				{600, 600},
				{800, 800},
				{1920, 1080},
		};

		// To string
		String[] sizeStrings = new String[resolutions.length];
		for (int i = 0; i < resolutions.length; i++) {
			sizeStrings[i] = resolutions[i][0] + "×" + resolutions[i][1];
		}
		add(new JLabel("Select resolution:"));
		resolutionComboBox = new JComboBox<>(sizeStrings);
		resolutionComboBox.setSelectedIndex(0);
		add(resolutionComboBox);

		//button
		JButton validateButton = new JButton("Validate");
		validateButton.addActionListener(e -> {
			int selectedIndex = resolutionComboBox.getSelectedIndex();
			if (onValidateClicked != null && selectedIndex >= 0 && selectedIndex < resolutions.length) {
				int width = resolutions[selectedIndex][0];
				int height = resolutions[selectedIndex][1]; // to int for callback
				onValidateClicked.accept(width, height);
			}
		});
		add(validateButton);;
	}

	/**
	 * Registers a callback to be invoked when the user clicks Validate.
	 * @param callback a BiConsumer to call on Validate
	 */
	public void settingsValidated(BiConsumer<Integer, Integer>  callback) {
		this.onValidateClicked = callback;
	}
}
