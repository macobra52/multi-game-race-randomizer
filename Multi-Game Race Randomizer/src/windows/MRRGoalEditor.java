package windows;

import java.awt.EventQueue;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import constants.Console;
import constants.GameType;
import constants.GoalType;
import objects.Game;
import objects.GameCheckBox;
import objects.Goal;
import utils.MRRGoalSaveLoad;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MRRGoalEditor {
	
	private JFrame frmMRRGoalEditor;
	private MRRNewEditGoal neg;
	
	//List of goals imported and generated
	private List<Goal> goals;
	private Set<GameCheckBox> gameListCheckBox;
	private Set<Game> gameList;
	
	//Actual table data (above converted to arrays)
	private String[] columnNames = {"Game Name","Console","Game Type", "Goal Name", "Goal Type", "Description", "Time Ends When"};
	private String[][] data;
	
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollPane;
	private final JLabel lblTitle = new JLabel("MGRR Goal Editor by MaCobra52");
	private final JButton btnNewGoal = new JButton("New Goal");
	private final JButton btnDuplicateGoal = new JButton("Duplicate Goal");
	private final JButton btnEditGoal = new JButton("Edit Goal");
	private final JButton btnSave = new JButton("Save");
	private final JLabel lblyouHaveUnsaved = new JLabel("*You have unsaved changes");
	private final JButton btnDeleteGoal = new JButton("Delete Goal");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MRRGoalEditor window = new MRRGoalEditor();
					window.frmMRRGoalEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MRRGoalEditor() {
		MRRGoalSaveLoad.loadGoals();
		//MRRGoalSaveLoad.loadGoals();
		goals = MRRGoalSaveLoad.save.getGoals();
		gameList = MRRGoalSaveLoad.save.getGameList();
		gameListCheckBox = MRRGoalSaveLoad.gameListCheckbox;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMRRGoalEditor = new JFrame();
		
		//Save prompt if exiting with unsaved changes
		frmMRRGoalEditor.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(lblyouHaveUnsaved.isVisible() && lblyouHaveUnsaved.getForeground().equals(Color.RED)){
					String s = "You have unsaved changes. Would you like to save your changes before exiting?";
					int result = JOptionPane.showConfirmDialog(null, s, "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					
					//Yes
					if(result == JOptionPane.YES_OPTION) {
						MRRGoalSaveLoad.saveGoals();
						System.exit(0);
					}
					
					//No
					else if(result == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				}
				else
					System.exit(0);
			}
		});
		
		//Manual layout management for now.
		frmMRRGoalEditor.getContentPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				resizeElements();
			}
		});
		
		frmMRRGoalEditor.setTitle("MGRR Goal Editor V0.2");
		frmMRRGoalEditor.setBounds(100, 100, 1200, 600);
		frmMRRGoalEditor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmMRRGoalEditor.getContentPane().setLayout(null);
		frmMRRGoalEditor.setLocationRelativeTo(null);
				
		data = new String[goals.size()][7];
		
		for(int i = 0; i < goals.size(); i++) {
			Goal goal = goals.get(i);
			data[i][0] = goal.getGameName();
			data[i][1] = goal.getConsole().name();
			data[i][2] = goal.getGameType().name();
			data[i][3] = goal.getGoalName();
			data[i][4] = goal.getGoalType().name();
			data[i][5] = goal.getDescription();
			data[i][6] = goal.getTimeEndsWhen();	
		}
		
		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int columns) {
				return false;
			}
		};
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		resizeColumnWidth();
		
		//Double-clicked a row in the table
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
				btnEditGoal.setEnabled(true);
				btnDeleteGoal.setEnabled(true);
				btnDuplicateGoal.setEnabled(true);
				
				if(e.getClickCount() == 2) {
					editGoal(row);
				}
			}
		});
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setBackground(Color.WHITE);
		table.setAutoCreateRowSorter(true);
		
		//Default sorting is by Game name ascending
		table.getRowSorter().toggleSortOrder(0);
		
		lblTitle.setForeground(new Color(0, 0, 128));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitle.setBounds(502, 11, 228, 14);
		frmMRRGoalEditor.getContentPane().add(lblTitle);
		
		scrollPane.setBounds(10, 36, 1165, 466);
		frmMRRGoalEditor.getContentPane().add(scrollPane);
		
		//New Goal
		btnNewGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGoal();
			}
		});
		btnNewGoal.setBounds(10, 528, 89, 23);
		frmMRRGoalEditor.getContentPane().add(btnNewGoal);
		btnEditGoal.setEnabled(false);
		
		//Edit Goal
		btnEditGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				editGoal(row);
			}
		});
		btnEditGoal.setBounds(239, 528, 89, 23);
		frmMRRGoalEditor.getContentPane().add(btnEditGoal);
		
		btnDuplicateGoal.setEnabled(false);
		//Duplicate Goal
		btnDuplicateGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				duplicateGoal(row);
			}
		});
		btnDuplicateGoal.setBounds(108, 528, 121, 23);
		frmMRRGoalEditor.getContentPane().add(btnDuplicateGoal);
		
		//Delete
		btnDeleteGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				deleteGoal(row);	
			}
		});
		
		btnDeleteGoal.setEnabled(false);
		btnDeleteGoal.setBounds(338, 528, 98, 23);
		
		frmMRRGoalEditor.getContentPane().add(btnDeleteGoal);
				
		//Save
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MRRGoalSaveLoad.saveGoals();
				lblyouHaveUnsaved.setText("Saved!");
				lblyouHaveUnsaved.setForeground(Color.GREEN);
				btnSave.setEnabled(false);
			}
		});
		btnSave.setBounds(1086, 528, 89, 23);
		btnSave.setEnabled(false);
		frmMRRGoalEditor.getContentPane().add(btnSave);
		
		lblyouHaveUnsaved.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblyouHaveUnsaved.setForeground(Color.RED);
		lblyouHaveUnsaved.setBounds(895, 531, 181, 14);
		lblyouHaveUnsaved.setVisible(false);
		
		frmMRRGoalEditor.getContentPane().add(lblyouHaveUnsaved);
		neg = new MRRNewEditGoal(frmMRRGoalEditor, true);
	}
	
	public void editGoal(int row) {
		neg.setTitle("Edit Goal");
		neg.setEditing(true);
		neg.setSubmit(false);
		neg.setComboBoxGameName(getGameCheckBoxFromString( (String) model.getValueAt(row, 0)));
		neg.setComboBoxConsole(Console.valueOf((String) model.getValueAt(row, 1)));
		neg.setComboBoxGameType(GameType.valueOf((String) model.getValueAt(row, 2)));
		neg.setTextFieldGoalName((String) model.getValueAt(row, 3));
		neg.setComboBoxGoalType(GoalType.valueOf((String) model.getValueAt(row, 4)));
		neg.setTextFieldDescription((String) model.getValueAt(row, 5));
		neg.setTextFieldTimeEndsWhen((String) model.getValueAt(row, 6));
		neg.getComboBoxGameName().setEnabled(false);
		neg.getComboBoxConsole().setEnabled(false);
		neg.getComboBoxGameType().setEnabled(false);
		
		//Get goal object for reference
		Goal goalToEdit = null;
		
		String goalName = neg.getTextFieldGoalName();
		GoalType goalType = (GoalType) neg.getComboBoxGoalType().getSelectedItem();
		String description = neg.getTextFieldDescription();
		String timeEndsWhen = neg.getTextFieldTimeEndsWhen();
		
		GameCheckBox gameCheckBox = (GameCheckBox) neg.getComboBoxGameName().getSelectedItem();
		String gameName = gameCheckBox.getGame().getGameName();
		for(Goal g : goals) {
			if(g.getGameName().equals(gameName) && g.getGoalName().equals(goalName)) {
				goalToEdit = g;
				break;
			}
		}
		
		neg.setVisible(true);
		
		if(neg.isSubmit()) {
			TableModel tm = table.getModel();
			
			goalName = neg.getTextFieldGoalName();
			goalType = (GoalType) neg.getComboBoxGoalType().getSelectedItem();
			description = neg.getTextFieldDescription();
			timeEndsWhen = neg.getTextFieldTimeEndsWhen();
			
			goalToEdit.setGoalName(goalName);
			goalToEdit.setDescription(description);
			goalToEdit.setGoalType(goalType);
			goalToEdit.setTimeEndsWhen(timeEndsWhen);
			
			//tm.setValueAt((String) neg.getComboBoxGameName().getSelectedItem(), row, 0);
			//tm.setValueAt((String) neg.getComboBoxConsole().getSelectedItem(), row, 1);
			//tm.setValueAt((String) neg.getComboBoxGameType().getSelectedItem(), row, 2);
			tm.setValueAt(goalName, row, 3);
			tm.setValueAt((String) goalType.name(), row, 4);
			tm.setValueAt(description, row, 5);
			tm.setValueAt(timeEndsWhen, row, 6);
			
			
			btnSave.setEnabled(true);
			lblyouHaveUnsaved.setText("*You have unsaved changes");
			lblyouHaveUnsaved.setForeground(Color.RED);
			lblyouHaveUnsaved.setVisible(true);
		}
	}
	
	public void newGoal() {
		neg.setTitle("New Goal");
		neg.setEditing(false);
		neg.setSubmit(false);
		neg.setComboBoxGameName(null);
		neg.setComboBoxConsole(null);
		neg.setComboBoxGameType(null);
		neg.setTextFieldGoalName("");
		neg.setComboBoxGoalType(null);
		neg.setTextFieldDescription("");
		neg.setTextFieldTimeEndsWhen("");
		neg.getComboBoxGameName().setEnabled(true);
		neg.getComboBoxConsole().setEnabled(true);
		neg.getComboBoxGameType().setEnabled(true);
		
		neg.setVisible(true);
		
		if(neg.isSubmit()) {
			DefaultTableModel tm = (DefaultTableModel) table.getModel();
			
			Object gameName = neg.getComboBoxGameName().getSelectedItem();
			Console console = (Console) neg.getComboBoxConsole().getSelectedItem();
			GameType gameType = (GameType) neg.getComboBoxGameType().getSelectedItem();
			String goalName = neg.getTextFieldGoalName();
			GoalType goalType = (GoalType) neg.getComboBoxGoalType().getSelectedItem();
			String description = neg.getTextFieldDescription();
			String timeEndsWhen = neg.getTextFieldTimeEndsWhen();
			
			GameCheckBox gameCheckBox;
			Game game = null;
			Goal goal;
			
			//If game is new, initialize it and add to the gameList
			if(gameName instanceof GameCheckBox) {
				gameCheckBox = (GameCheckBox) gameName;
				game = gameCheckBox.getGame();
			}
			else {
				String g = (String) gameName;
				String s = "This will add the following new game to the list. Do you want to continue?"
						+ "\nName: " + g
						+ "\nConsole: " + console.name()
						+ "\nGame Type: " + gameType.name();
				int choice = JOptionPane.showConfirmDialog(null, s, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				//No - back out
				if(choice != 0)
					return;
				
				game = new Game(g, console, gameType);
				gameList.add(game);
				gameCheckBox = new GameCheckBox(game);
				gameListCheckBox.add(gameCheckBox);
				neg.getComboBoxGameName().addItem(gameCheckBox);
			}
			
			goal = new Goal(game, goalName, description, goalType, timeEndsWhen);
			goals.add(goal);
			
			//Now add to the JTable
			tm.addRow(goal.toArray());
			
			btnSave.setEnabled(true);
			lblyouHaveUnsaved.setText("*You have unsaved changes");
			lblyouHaveUnsaved.setForeground(Color.RED);
			lblyouHaveUnsaved.setVisible(true);
		}
		
	}
	
	public void duplicateGoal(int row) {
		neg.setTitle("New Goal");
		neg.setEditing(false);
		neg.setSubmit(false);
		neg.setComboBoxGameName(getGameCheckBoxFromString( (String) model.getValueAt(row, 0)));
		neg.setComboBoxConsole(Console.valueOf((String) model.getValueAt(row, 1)));
		neg.setComboBoxGameType(GameType.valueOf((String) model.getValueAt(row, 2)));
		neg.setTextFieldGoalName((String) model.getValueAt(row, 3));
		neg.setComboBoxGoalType(GoalType.valueOf((String) model.getValueAt(row, 4)));
		neg.setTextFieldDescription((String) model.getValueAt(row, 5));
		neg.setTextFieldTimeEndsWhen((String) model.getValueAt(row, 6));
		neg.getComboBoxGameName().setEnabled(true);
		neg.getComboBoxConsole().setEnabled(true);
		neg.getComboBoxGameType().setEnabled(true);
		neg.setVisible(true);
		
		if(neg.isSubmit()) {
			DefaultTableModel tm = (DefaultTableModel) table.getModel();
			
			Object gameName = neg.getComboBoxGameName().getSelectedItem();
			Console console = (Console) neg.getComboBoxConsole().getSelectedItem();
			GameType gameType = (GameType) neg.getComboBoxGameType().getSelectedItem();
			String goalName = neg.getTextFieldGoalName();
			GoalType goalType = (GoalType) neg.getComboBoxGoalType().getSelectedItem();
			String description = neg.getTextFieldDescription();
			String timeEndsWhen = neg.getTextFieldTimeEndsWhen();
			
			GameCheckBox gameCheckBox;
			Game game = null;
			Goal goal;
			
			//If game is new, initialize it and add to the gameList
			if(gameName instanceof GameCheckBox) {
				gameCheckBox = (GameCheckBox) gameName;
				game = gameCheckBox.getGame();
			}
			else {
				String g = (String) gameName;
				String s = "This will add the following new game to the list. Do you want to continue?"
						+ "\nName: " + g
						+ "\nConsole: " + console.name()
						+ "\nGame Type: " + gameType.name();
				int choice = JOptionPane.showConfirmDialog(null, s, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				//No - back out
				if(choice != 0)
					return;
				
				game = new Game(g, console, gameType);
				gameList.add(game);
				gameCheckBox = new GameCheckBox(game);
				gameListCheckBox.add(gameCheckBox);
				neg.getComboBoxGameName().addItem(gameCheckBox);
			}
			
			goal = new Goal(game, goalName, description, goalType, timeEndsWhen);
			goals.add(goal);
			
			//Now add to the JTable
			tm.addRow(goal.toArray());
			
			btnSave.setEnabled(true);
			lblyouHaveUnsaved.setText("*You have unsaved changes");
			lblyouHaveUnsaved.setForeground(Color.RED);
			lblyouHaveUnsaved.setVisible(true);
		}
		
	}
	
	public void deleteGoal(int row) {
		String s = "Are you sure you want to delete the selected Goal?";
		int choice = JOptionPane.showConfirmDialog(null, s, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		//Yes
		if(choice == JOptionPane.YES_OPTION) {
			DefaultTableModel tm = (DefaultTableModel) table.getModel();
			tm.removeRow(row);
			Goal g = goals.remove(row);
			System.out.println("removed " + g);
			
			if(getGoalCount(g.getGameName()) == 0) {
				System.out.println("last one removed");
				gameList.remove(g.getGame());
				GameCheckBox game = getGameCheckBoxFromGame(g.getGame());
				gameListCheckBox.remove(game);
				neg.getComboBoxGameName().removeItem(game);
			}
			//add 'restore default' button - create backup of default files
			
			btnEditGoal.setEnabled(false);
			btnDeleteGoal.setEnabled(false);
			btnDuplicateGoal.setEnabled(false);
			
			btnSave.setEnabled(true);
			lblyouHaveUnsaved.setText("*You have unsaved changes");
			lblyouHaveUnsaved.setForeground(Color.RED);
			lblyouHaveUnsaved.setVisible(true);
		}
	}
	
	public GameCheckBox getGameCheckBoxFromString(String game) {
		for(GameCheckBox g : gameListCheckBox) {
			if(g.getGameName().equals(game)) {
				return g;
			}
		}
		
		return null;
	}
	
	public GameCheckBox getGameCheckBoxFromGame(Game game) {
		for(GameCheckBox g : gameListCheckBox) {
			if(g.getGame().equals(game)) {
				return g;
			}
		}
		
		return null;
	}
	
	public Goal findGoalFromGameGoalName(String gameName, String goalName) {
		for(Goal g : goals) {
			if(g.getGameName().equals(gameName) && g.getGoalName().equals(goalName)) {
				return g;
			}
		}
		
		return null;
	}
	
	//Get goal count for specific game name
	public int getGoalCount(String game) {
		int count = 0;
		
		for(Goal goal : goals) {
			if(goal.getGameName().equals(game))
				count++;
		}
		
		return count;
	}
	
	public void resizeColumnWidth() {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 100; // Min width
	        int maxWidth = 280;	//Max width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > maxWidth) width = maxWidth;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public void resizeElements() {
		int width = frmMRRGoalEditor.getWidth();
		int height = frmMRRGoalEditor.getHeight();
		
		//lblTitle.setBounds(502, 11, 228, 14);
		lblTitle.setLocation((Integer) width/2 - lblTitle.getWidth()/2, lblTitle.getY());
		//scrollPane.setBounds(10, 36, 1165, 466);
		scrollPane.setSize(width - 35, height - 144);
		//btnNewGoal.setBounds(10, 528, 89, 23);
		btnNewGoal.setLocation(btnNewGoal.getX(), height - 72);
		//btnDuplicateGoal.setBounds(108, 528, 121, 23);
		btnDuplicateGoal.setLocation(btnDuplicateGoal.getX(), height - 72);
		//btnEditGoal.setBounds(239, 528, 89, 23);
		btnEditGoal.setLocation(btnEditGoal.getX(), height - 72);
		//btnDeleteGoal.setBounds(338, 528, 98, 23);
		btnDeleteGoal.setLocation(btnDeleteGoal.getX(), height - 72);
		//lblyouHaveUnsaved.setBounds(895, 528, 181, 14);
		lblyouHaveUnsaved.setLocation(width - 305, height - 69);
		//btnSave.setBounds(1086, 528, 89, 23);
		btnSave.setLocation(width - 114, height - 72);
	}
}
