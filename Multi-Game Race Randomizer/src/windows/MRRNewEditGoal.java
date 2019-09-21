package windows;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constants.Console;
import constants.GameType;
import constants.GoalType;
import objects.GameCheckBox;
import utils.MRRGoalSaveLoad;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class MRRNewEditGoal extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private boolean editing;	//True - Edit, False - New
	private boolean submit;		//True if Submit is clicked
	private JPanel contentPane;
	private final JLabel lblGameName = new JLabel("Game Name:");
	private JComboBox<GameCheckBox> comboBoxGameName = new JComboBox<GameCheckBox>();
	private final JLabel lblConsole = new JLabel("Console:");
	private JComboBox<Console> comboBoxConsole;
	private final JLabel lblGameType = new JLabel("Game Type:");
	private JComboBox<GameType> comboBoxGameType;
	private final JLabel lblGoalName = new JLabel("Goal Name:");
	private JTextField textFieldGoalName = new JTextField();
	private final JLabel lblGoalType = new JLabel("Goal Type:");
	private JComboBox<GoalType> comboBoxGoalType;
	private final JLabel lblDescription = new JLabel("Description:");
	private JTextArea textFieldDescription = new JTextArea();
	private final JLabel lblTimeEndsWhen = new JLabel("Time Ends When:");
	private JTextField textFieldTimeEndsWhen = new JTextField();
	private final JButton btnSubmit = new JButton("Submit");
	private final JButton btnCancel = new JButton("Cancel");
	private final JScrollPane scrollPane = new JScrollPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MRRNewEditGoal frame = new MRRNewEditGoal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MRRNewEditGoal() {
		super();
		initGUI();
	}
	
	public MRRNewEditGoal(JFrame parent, boolean isModal) {
		super(parent, isModal);
		initGUI();
	}
	
	private void initGUI() {
		setTitle("New Goal");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 387);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		ToolTipManager.sharedInstance().setInitialDelay(1000);
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		
		comboBoxGameName.setEditable(true);
		comboBoxGameName.setBounds(101, 9, 323, 20);
		for(GameCheckBox game : MRRGoalSaveLoad.gameListCheckbox) {
			comboBoxGameName.addItem(game);
		}
		comboBoxGameName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Object o = e.getItem();
					if(o instanceof GameCheckBox) {
						GameCheckBox game = (GameCheckBox) o;
						comboBoxConsole.setSelectedItem(game.getConsole());
						comboBoxGameType.setSelectedItem(game.getGameType());
					}
				}
			}
		});
		
		contentPane.add(comboBoxGameName);
		
		lblGameName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGameName.setForeground(new Color(0, 0, 128));
		lblGameName.setBounds(10, 11, 81, 14);
		contentPane.add(lblGameName);
		lblConsole.setForeground(new Color(0, 0, 128));
		lblConsole.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblConsole.setBounds(10, 42, 81, 14);
		contentPane.add(lblConsole);
		
		comboBoxConsole = new JComboBox<Console>();
		comboBoxConsole.setModel(new DefaultComboBoxModel<Console>(Console.values()));
		comboBoxConsole.setBounds(101, 40, 113, 20);
		contentPane.add(comboBoxConsole);
		
		lblGameType.setForeground(new Color(0, 0, 128));
		lblGameType.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGameType.setBounds(10, 67, 81, 14);
		contentPane.add(lblGameType);
		
		comboBoxGameType = new JComboBox<GameType>();
		comboBoxGameType.setModel(new DefaultComboBoxModel<GameType>(GameType.values()));
		comboBoxGameType.setBounds(101, 65, 113, 20);
		contentPane.add(comboBoxGameType);
		
		lblGoalName.setForeground(new Color(0, 0, 128));
		lblGoalName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGoalName.setBounds(10, 127, 81, 14);
		contentPane.add(lblGoalName);
		
		textFieldGoalName.setColumns(10);
		textFieldGoalName.setBounds(101, 125, 323, 20);
		contentPane.add(textFieldGoalName);
		
		lblGoalType.setForeground(new Color(0, 0, 128));
		lblGoalType.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGoalType.setBounds(10, 158, 81, 14);
		contentPane.add(lblGoalType);
		
		comboBoxGoalType = new JComboBox<GoalType>();
		comboBoxGoalType.setToolTipText("<html>My classification for the default goals. Feel free to edit however you like:<br><br>Achievement - Goals similar to modern video game achievements. Usually do not complete the game.<br>Speedrun - Common speedrunning goals that usually complete the game.<br>Fast vs Longer - If someone familar with the game in question can complete the goal in 15 minutes or less, it is considered Fast. </html>");
		comboBoxGoalType.setModel(new DefaultComboBoxModel<GoalType>(GoalType.values()));
		comboBoxGoalType.setBounds(101, 156, 161, 20);
		contentPane.add(comboBoxGoalType);
		
		lblDescription.setForeground(new Color(0, 0, 128));
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescription.setBounds(10, 189, 81, 14);
		contentPane.add(lblDescription);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(101, 187, 323, 65);
		
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textFieldDescription);
		textFieldDescription.setColumns(10);
		textFieldDescription.setLineWrap(true);
		textFieldDescription.setWrapStyleWord(true);
		
		lblTimeEndsWhen.setForeground(new Color(0, 0, 128));
		lblTimeEndsWhen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTimeEndsWhen.setBounds(10, 269, 123, 14);
		contentPane.add(lblTimeEndsWhen);
		
		textFieldTimeEndsWhen.setColumns(10);
		textFieldTimeEndsWhen.setBounds(136, 265, 288, 20);
		contentPane.add(textFieldTimeEndsWhen);
		
		//Submit
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Validation
				String s = "";
				if(comboBoxGameName.getSelectedItem() == null || comboBoxGameName.getSelectedItem().toString().isEmpty())
					s += "Game Name is required.\n";
				if(comboBoxConsole.getSelectedItem() == null || comboBoxConsole.getSelectedItem().toString().isEmpty())
					s += "Console is required.\n";
				if(comboBoxGameType.getSelectedItem() == null || comboBoxGameType.getSelectedItem().toString().isEmpty())
					s += "Game Type is required.\n";
				if(textFieldGoalName.getText() == null || textFieldGoalName.getText().isEmpty())
					s += "Goal Name is required.\n";
				if(comboBoxGoalType.getSelectedItem() == null || comboBoxGoalType.getSelectedItem().toString().isEmpty())
					s += "Goal Type is required.\n";
				if(textFieldDescription.getText() == null || textFieldDescription.getText().isEmpty())
					s += "Description is required.\n";
				if(textFieldTimeEndsWhen.getText() == null || textFieldTimeEndsWhen.getText().isEmpty())
					s += "Time Ends When is required.\n";
				
				if(! s.isEmpty()) {
					JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					submit = true;
					MRRNewEditGoal.this.setVisible(false);
				}
			}
		});
		
		btnSubmit.setBounds(10, 315, 89, 23);
		
		contentPane.add(btnSubmit);
		
		//Cancel
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MRRNewEditGoal.this.setVisible(false);
			}
		});
		btnCancel.setBounds(109, 315, 89, 23);
		
		contentPane.add(btnCancel);
	}
	
	/*
	public Game getComboBoxGameName() {
		Object o = comboBoxGameName.getSelectedItem();
		if(o instanceof Game) {
			Game game = (Game) o;
			return game;
		}
		else {
			//assumed String at this point. create a new game
			String s = (String) o;
			Console c = (Console) comboBoxConsole.getSelectedItem();
			GameType gt = (GameType) comboBoxGameType.getSelectedItem();
			Game game = new Game(s, c.name(), gt.name());
			MRRGoalSaveLoad.gameList.add(game);
			return game;
		}
	}
	*/
	
	public JComboBox<GameCheckBox> getComboBoxGameName() {
		return comboBoxGameName;
	}

	public void setComboBoxGameName(GameCheckBox comboBoxGameName) {
		this.comboBoxGameName.setSelectedItem(comboBoxGameName);
	}


	public void setComboBoxConsole(Console console) {
		this.comboBoxConsole.setSelectedItem(console);
	}
	
	/*
	public Console getComboBoxConsole() {
		return comboBoxConsole.getItemAt(comboBoxConsole.getSelectedIndex());
	}
	*/
	
	public JComboBox<Console> getComboBoxConsole() {
		return comboBoxConsole;
	}
	
	public void setComboBoxGameType(GameType gameType) {
		this.comboBoxGameType.setSelectedItem(gameType);
	}
	
	/*
	public GameType getComboBoxGameType() {
		return comboBoxGameType.getItemAt(comboBoxGameType.getSelectedIndex());
	}
	*/
	
	public JComboBox<GameType> getComboBoxGameType() {
		return comboBoxGameType;
	}
	
	public String getTextFieldGoalName() {
		return textFieldGoalName.getText();
	}

	public void setTextFieldGoalName(String goalName) {
		this.textFieldGoalName.setText(goalName);
	}

	public void setComboBoxGoalType(GoalType goalType) {
		this.comboBoxGoalType.setSelectedItem(goalType);
	}
	
	public JComboBox<GoalType> getComboBoxGoalType() {
		return comboBoxGoalType;
	}
	
	public String getTextFieldDescription() {
		return textFieldDescription.getText();
	}

	public void setTextFieldDescription(String description) {
		this.textFieldDescription.setText(description);
	}

	public String getTextFieldTimeEndsWhen() {
		return textFieldTimeEndsWhen.getText();
	}

	public void setTextFieldTimeEndsWhen(String timeEndsWhen) {
		this.textFieldTimeEndsWhen.setText(timeEndsWhen);
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}


	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
}
