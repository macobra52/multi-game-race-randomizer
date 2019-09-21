package windows;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import constants.Console;
import constants.GameType;
import constants.GoalType;
import objects.Game;
import objects.GameCheckBox;
import objects.Goal;
import utils.MRRGoalSaveLoad;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;

public class MRRGui {

	private JFrame frmMultiraceRandomizerV;
	
	//Game list
	private final JScrollPane scrollPane = new JScrollPane();
	private final JPanel panel = new JPanel();
	private Set<GameCheckBox> gameList;
	private final JLabel lblMultigameRaceRandomizer = new JLabel("Multi-Game Race Randomizer by MaCobra52");
	private final JLabel lblSelectGamesTo = new JLabel("Select games to include:");
	private final JButton btnSelectAll = new JButton("Select All");
	private final JButton btnSelectNone = new JButton("Select None");
	private final JButton btnSelectOnlyvanilla = new JButton("Select Only Vanilla Games");
	
	//Goals
	private final JLabel lblSelectGoalsTo = new JLabel("Select goals to include:");
	private final JCheckBox chckbxFastAchievements = new JCheckBox("Fast Achievements");
	private final JCheckBox chckbxLongerAchievements = new JCheckBox("Longer Achievements");
	private final JCheckBox chckbxFastSpeedrun = new JCheckBox("Fast Speedrun");
	private final JCheckBox chckbxLongerSpeedrun = new JCheckBox("Longer Speedrun");
	
	//Options
	private final JCheckBox chckbxAllowDuplicateGames = new JCheckBox("Allow duplicate games");
	private final JCheckBox chckbxAllSameConsole = new JCheckBox("Same console");
	private final JLabel lblOptions = new JLabel("Options:");
	private final JLabel lblRaceLength = new JLabel("Race length:");
	private final JSpinner spinner = new JSpinner();
	private final JLabel lblGoals = new JLabel("Goals");
	
	private final JButton btnGenerate = new JButton("Generate!");
	
	private final JLabel lblGoalCount = new JLabel("Goal Count:");
	private final JLabel labelCount = new JLabel();
	private final JLabel lblNotEnoughGoals = new JLabel("Not enough goals for selected options");
	
	//List of goals imported and generated
	private List<Goal> goals;
	
	//All possible goals after filtering
	private List<Goal> chosenGoals;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MRRGui window = new MRRGui();
					window.frmMultiraceRandomizerV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MRRGui() {
		MRRGoalSaveLoad.loadGoals();
		gameList = MRRGoalSaveLoad.gameListCheckbox;
		goals = MRRGoalSaveLoad.save.getGoals();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMultiraceRandomizerV = new JFrame();
		frmMultiraceRandomizerV.setTitle("Multi-Game Race Randomizer V0.2");
		frmMultiraceRandomizerV.setBounds(100, 100, 450, 492);
		frmMultiraceRandomizerV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMultiraceRandomizerV.getContentPane().setLayout(null);
		frmMultiraceRandomizerV.setLocationRelativeTo(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setBounds(10, 77, 414, 94);
		ToolTipManager.sharedInstance().setInitialDelay(1000);
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		
		frmMultiraceRandomizerV.getContentPane().add(scrollPane);
		scrollPane.setViewportView(panel);
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		System.out.println("Goal count: " + goals.size());
		
		chosenGoals = new ArrayList<Goal>(goals);
		
		for(GameCheckBox game : gameList) {
			game.setSelected(true);
			game.setBackground(Color.WHITE);
			
			//Adjust the count if checked/unchecked
			game.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(game.isSelected()) {
						addChosenGoals(game.getGameName());
						setChosenGoalCount();
					}
					else {
						removeChosenGoals(game.getGameName());
						setChosenGoalCount();
					}	
				}
			});
			
			panel.add(game);
		}
		lblSelectGamesTo.setForeground(new Color(0, 0, 128));

		lblSelectGamesTo.setBounds(10, 52, 143, 14);
		
		frmMultiraceRandomizerV.getContentPane().add(lblSelectGamesTo);
		
		//Select All
		btnSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(GameCheckBox game : gameList) {
					if(! game.isSelected()) {
						addChosenGoals(game.getGameName());
						game.setSelected(true);
					}
				}
				setChosenGoalCount();
			}
		});
		btnSelectAll.setBounds(10, 182, 89, 23);
		
		frmMultiraceRandomizerV.getContentPane().add(btnSelectAll);
		
		//Select None
		btnSelectNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(GameCheckBox game : gameList) {
					if(game.isSelected()) {
						removeChosenGoals(game.getGameName());
						game.setSelected(false);
					}
				}
				setChosenGoalCount();
			}
		});
		btnSelectNone.setBounds(110, 182, 109, 23);
		
		frmMultiraceRandomizerV.getContentPane().add(btnSelectNone);
		lblSelectGoalsTo.setForeground(new Color(0, 0, 128));
		lblSelectGoalsTo.setBounds(10, 225, 220, 14);
		
		frmMultiraceRandomizerV.getContentPane().add(lblSelectGoalsTo);
		chckbxFastAchievements.setToolTipText("<html>Achievement based goals that generally do not take long to complete.<br>(15 minutes or less for someone familiar with the game)</html>");
		chckbxFastAchievements.setSelected(true);
		chckbxFastAchievements.setBounds(10, 246, 143, 23);
		frmMultiraceRandomizerV.getContentPane().add(chckbxFastAchievements);
		chckbxLongerAchievements.setToolTipText("<html>Longer achievement based goals<br>(more than 15 minutes for someone familiar with the game)</html>");
		chckbxLongerAchievements.setSelected(true);
		chckbxLongerAchievements.setBounds(162, 246, 151, 23);
		frmMultiraceRandomizerV.getContentPane().add(chckbxLongerAchievements);
		chckbxFastSpeedrun.setToolTipText("<html>Speedrunning goals that do not take long to complete.<br>(15 minutes or less for someone familiar with the game)</html>");
		chckbxFastSpeedrun.setSelected(true);
		chckbxFastSpeedrun.setBounds(10, 272, 151, 23);
		frmMultiraceRandomizerV.getContentPane().add(chckbxFastSpeedrun);
		chckbxLongerSpeedrun.setToolTipText("<html>Longer speedrunning goals.<br>(more than 15 minutes for someone familiar with the game)</html>");
		chckbxLongerSpeedrun.setSelected(true);
		chckbxLongerSpeedrun.setBounds(163, 272, 163, 23);
		frmMultiraceRandomizerV.getContentPane().add(chckbxLongerSpeedrun);
		chckbxAllowDuplicateGames.setToolTipText("If checked, multiple goals from the same game may be selected.");
		chckbxAllowDuplicateGames.setBounds(10, 332, 162, 23);
		
		//Listeners for goal types
		chckbxFastAchievements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxFastAchievements.isSelected()) {
					addChosenGoals(GoalType.FAST_ACHIEVEMENT);
					setChosenGoalCount();
				}
				else {
					removeChosenGoals(GoalType.FAST_ACHIEVEMENT);
					setChosenGoalCount();
				}
			}
		});
		
		chckbxLongerAchievements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxLongerAchievements.isSelected()) {
					addChosenGoals(GoalType.LONGER_ACHIEVEMENT);
					setChosenGoalCount();
				}
				else {
					removeChosenGoals(GoalType.LONGER_ACHIEVEMENT);
					setChosenGoalCount();
				}
			}
		});
		
		chckbxFastSpeedrun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxFastSpeedrun.isSelected()) {
					addChosenGoals(GoalType.FAST_SPEEDRUN);
					setChosenGoalCount();
				}
				else {
					removeChosenGoals(GoalType.FAST_SPEEDRUN);
					setChosenGoalCount();
				}
			}
		});
		
		chckbxLongerSpeedrun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxLongerSpeedrun.isSelected()) {
					addChosenGoals(GoalType.LONGER_SPEEDRUN);
					setChosenGoalCount();
				}
				else {
					removeChosenGoals(GoalType.LONGER_SPEEDRUN);
					setChosenGoalCount();
				}
			}
		});
		
		frmMultiraceRandomizerV.getContentPane().add(chckbxAllowDuplicateGames);
		lblOptions.setForeground(new Color(0, 0, 128));
		lblOptions.setBounds(10, 311, 59, 14);
		
		//Allow Duplicate Games
		chckbxAllowDuplicateGames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChosenGoalCount();
			}
		});
		
		frmMultiraceRandomizerV.getContentPane().add(lblOptions);
		chckbxAllSameConsole.setToolTipText("If selected, all games generated will be on the same console.");
		chckbxAllSameConsole.setBounds(174, 332, 139, 23);
		
		frmMultiraceRandomizerV.getContentPane().add(chckbxAllSameConsole);
		lblRaceLength.setForeground(new Color(0, 0, 128));
		lblRaceLength.setBounds(10, 377, 75, 14);
		
		//Same Console
		chckbxAllSameConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChosenGoalCount();
			}
		});
				
		frmMultiraceRandomizerV.getContentPane().add(lblRaceLength);
		spinner.setModel(new SpinnerNumberModel(3, 2, 9, 1));
		spinner.setBounds(84, 374, 36, 23);
		
		//Spinner
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setChosenGoalCount();
			}
		});
		
		frmMultiraceRandomizerV.getContentPane().add(spinner);
		lblGoals.setBounds(126, 377, 46, 14);
		
		frmMultiraceRandomizerV.getContentPane().add(lblGoals);
		
		//Generate
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Goal> randomizedGoals = randomizeGoals();
				String s = "";
				
				for(Goal goal : randomizedGoals) {
					System.out.println(goal.toString());
					s += "Game: " + goal.getGameName() + " (" + goal.getConsole() + ")"
							+ "\n" + "Goal: " + goal.getGoalName()
							+ "\n" + "Description: " + goal.getDescription()
							+ "\n" + "Time ends when: " + goal.getTimeEndsWhen()
							+ "\n\n";
					
				}
				
				s += "Be sure to allow enough time to change games/consoles. Happy racing! :)";
				
				JOptionPane.showMessageDialog(null, s, "Your Randomized Multi-Game Race", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		btnGenerate.setBounds(10, 420, 89, 23);
		
		frmMultiraceRandomizerV.getContentPane().add(btnGenerate);
		btnSelectOnlyvanilla.setToolTipText("Select only games that are not romhacks or randomizers.");
		
		//Select only Vanilla
		btnSelectOnlyvanilla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(GameCheckBox game : gameList) {
					if(game.getGameType() == GameType.VANILLA && ! game.isSelected()) {
						addChosenGoals(game.getGameName());
						game.setSelected(true);
					}
					else if(game.getGameType() != GameType.VANILLA &&  game.isSelected()) {
						removeChosenGoals(game.getGameName());
						game.setSelected(false);
					}
				}
				setChosenGoalCount();
			}
		});
		
		btnSelectOnlyvanilla.setBounds(229, 182, 195, 23);
		
		frmMultiraceRandomizerV.getContentPane().add(btnSelectOnlyvanilla);
		lblMultigameRaceRandomizer.setForeground(new Color(0, 0, 128));
		lblMultigameRaceRandomizer.setHorizontalAlignment(SwingConstants.CENTER);
		lblMultigameRaceRandomizer.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMultigameRaceRandomizer.setBounds(10, 11, 414, 14);
		
		frmMultiraceRandomizerV.getContentPane().add(lblMultigameRaceRandomizer);
		lblGoalCount.setBounds(325, 424, 66, 14);
		
		frmMultiraceRandomizerV.getContentPane().add(lblGoalCount);
		labelCount.setBounds(397, 424, 27, 14);
		
		setChosenGoalCount();
		frmMultiraceRandomizerV.getContentPane().add(labelCount);
		lblNotEnoughGoals.setForeground(Color.RED);
		lblNotEnoughGoals.setBounds(195, 377, 229, 14);
		
		lblNotEnoughGoals.setVisible(false);
		
		//Click the error label brings up a help menu
		lblNotEnoughGoals.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = "What do the errors mean?\n\n"
						+ "'Not enough goals for selected options'\n"
						+ "The number of goals that can be randomized is less than your selected race length\n"
						+ "Increase the number of selected games/goal options or decrease the race length.\n\n"
						+ "''Allow Duplicate Games' required'\n"
						+ "The number of games selected is less than your selected race length\n"
						+ "Enable 'Allow Duplicate Games' or select more games to include in the randomization.\n\n"
						+ "'No eligible console found'\n"
						+ "If 'Same console' is selected, there must be enough goals selectable for the same console\n"
						+ "Select more games to include in the randomization or enable 'Allow Duplicate Games'";
				JOptionPane.showMessageDialog(null, s, "Error Help", JOptionPane.INFORMATION_MESSAGE);	
			}
		});
		
		frmMultiraceRandomizerV.getContentPane().add(lblNotEnoughGoals);
	}
	
	public List<Goal> randomizeGoals() {
		//All filtering is done by the frontend, so the only considerations now are the options.
		List<Goal> randomizedGoals = new ArrayList<Goal>();
		List<Goal> chosenGoals = new ArrayList<Goal>(this.chosenGoals);
		Goal chosenGoal;
		int numberOfGoals = (Integer) spinner.getValue();
		
		//If allow dups is not checked, need to also filter out all goals from the same game
		//If same console is checked, roll the console out of eligible consoles then filter out the rest
		
		if(chckbxAllSameConsole.isSelected()) {
			List<Console> eligibleConsoles = new ArrayList<Console>(getEligibleConsoleCount(numberOfGoals));
			Collections.shuffle(eligibleConsoles);
			Console chosenConsole = eligibleConsoles.get(0);
			
			//All other consoles are filtered
			chosenGoals.removeIf(g -> g.getConsole() != chosenConsole);
		}

		for(int i = 0; i < numberOfGoals; i++) {
			//Build elegibleGames list and pick one at random
			//otherwise it would be biased toward games with more goals
			Set<Game> elegibleGames = new TreeSet<Game>();
			for(Goal goal : chosenGoals) {
				elegibleGames.add(goal.getGame());
			}
			List<Game> elegibleGamesList = new ArrayList<Game>(elegibleGames);
			Collections.shuffle(elegibleGamesList);
			Game chosenGame = elegibleGamesList.get(0);
			
			//Now build elegibleGoals with the chosen game and pick one at random
			List<Goal> elegibleGoals = new ArrayList<Goal>(chosenGoals);
			elegibleGoals.removeIf(g -> ! chosenGame.equals(g.getGame()));
			Collections.shuffle(elegibleGoals);
			chosenGoal = elegibleGoals.get(0);
			
			randomizedGoals.add(chosenGoal);
			
			//If allow dups is not checked, filter out all goal matching the chosen goal's game
			if(! chckbxAllowDuplicateGames.isSelected()) {
				chosenGoals.removeIf(g -> chosenGame.equals(g.getGame()));
			}
			else {
				chosenGoals.remove(chosenGoal);
			}
		}
		
		return randomizedGoals;
	}
	
	/*
	//Count all goals
	public int getGoalCount() {
		return goals.size();
	}
	
	//Goal count by console
	public int getGoalCount(Console console) {
		int count = 0;
		for(Goal goal : goals) {
			if(goal.getConsole() == console)
				++count;
		}
		return count;
	}
	
	//Goal count by game type
	public int getGoalCount(GameType gameType) {
		int count = 0;
		for(Goal goal : goals) {
			if(goal.getGameType() == gameType)
				++count;
		}
		return count;
	}
	
	//Goal count by goal type
	public int getGoalCount(GoalType goalType) {
		int count = 0;
		for(Goal goal : goals) {
			if(goal.getGoalType() == goalType)
				++count;
		}
		return count;
	}
	
	//Goal count by game name
	public int getGoalCount(String gameName) {
		int count = 0;
		for(Goal goal : goals) {
			if(goal.getGameName().equalsIgnoreCase(gameName))
				++count;
		}
		return count;
	}
	*/
	
	//Count all distinct games in chosen goals
	public int getDistinctGameCount() {
		Set<String> gameSet = new TreeSet<String>();
		for(Goal goal : chosenGoals) {
			gameSet.add(goal.getGameName());
		}
		
		return gameSet.size();
	}
	
	//Count all consoles in chosen goals that can be used if same console is checked
	public HashSet<Console> getEligibleConsoleCount(int numberOfGoals) {
		HashSet<Console> elegibleConsoles = new HashSet<Console>();
		
		for(Console console: Console.values()) {
			List<Goal> filteredGoals = chosenGoals.stream()
					.filter(g -> g.getConsole() == console)
					.collect(Collectors.toList());
			
			if(filteredGoals.size() <= 1) 
				continue;
			if(! chckbxAllowDuplicateGames.isSelected()) {
				Set<String> gameNames = new TreeSet<String>();
				for(Goal goal : filteredGoals) {
					gameNames.add(goal.getGameName());
				}
				if(gameNames.size() >= numberOfGoals)
					elegibleConsoles.add(console);
			}
			else if(filteredGoals.size() >= numberOfGoals){
				elegibleConsoles.add(console);
			}
		}
		
		return elegibleConsoles;
	}
	
	//Count all chosen goals
	public void setChosenGoalCount() {
		int chosenGoalCount = chosenGoals.size();
		labelCount.setText(String.valueOf(chosenGoalCount));
		
		//Compare to number of goals (spinner)
		int numberOfGoals = (Integer) spinner.getValue();
		if(chosenGoalCount < numberOfGoals) {
			labelCount.setForeground(Color.RED);
			lblNotEnoughGoals.setText("Not enough goals for selected options");
			lblNotEnoughGoals.setVisible(true);
			btnGenerate.setEnabled(false);
			return;
		}
		
		//allow duplicates check
		if(! chckbxAllowDuplicateGames.isSelected()) {
			int distinctGameCount = getDistinctGameCount();
			if(distinctGameCount < numberOfGoals) {
				labelCount.setForeground(Color.BLACK);
				lblNotEnoughGoals.setText("'Allow Duplicate Games' required");
				lblNotEnoughGoals.setVisible(true);
				btnGenerate.setEnabled(false);
				return;
			}
		}
		
		//same console check
		if(chckbxAllSameConsole.isSelected()) {
			int elegibleConsoleCount = getEligibleConsoleCount(numberOfGoals).size();
			if(elegibleConsoleCount < 1) {
				labelCount.setForeground(Color.BLACK);
				lblNotEnoughGoals.setText("No eligible console found");
				lblNotEnoughGoals.setVisible(true);
				btnGenerate.setEnabled(false);
				return;
			}
		}
		
		//All tests pass
		labelCount.setForeground(Color.BLACK);
		lblNotEnoughGoals.setVisible(false);
		btnGenerate.setEnabled(true);
	}
	
	//Add all games to the chosen goals based on game name (need to consider current state of goal types checked)
	public void addChosenGoals(String gameName) {
		List<Goal> filteredGoals = goals.stream()
				.filter(g -> gameName.equals(g.getGameName()))
				.collect(Collectors.toList());
		if(! chckbxFastAchievements.isSelected()) {
			filteredGoals.removeIf(g -> g.getGoalType() == GoalType.FAST_ACHIEVEMENT);
		}
		if(! chckbxLongerAchievements.isSelected()) {
			filteredGoals.removeIf(g -> g.getGoalType() == GoalType.LONGER_ACHIEVEMENT);
		}
		if(! chckbxFastSpeedrun.isSelected()) {
			filteredGoals.removeIf(g -> g.getGoalType() == GoalType.FAST_SPEEDRUN);
		}
		if(! chckbxLongerSpeedrun.isSelected()) {
			filteredGoals.removeIf(g -> g.getGoalType() == GoalType.LONGER_SPEEDRUN);
		}
		chosenGoals.addAll(filteredGoals);
	}
	
	//Remove all games from the chosen goals based on game name
	public void removeChosenGoals(String gameName) {
		chosenGoals.removeIf(g -> gameName.equals(g.getGameName()));
	}
	
	//Add all games to the chosen goals based on goal type (need to consider current state of games checked)
	public void addChosenGoals(GoalType goalType) {
		List<Goal> filteredGoals = goals.stream()
				.filter(g -> goalType == g.getGoalType())
				.collect(Collectors.toList());
		for(GameCheckBox game : gameList) {
			if(! game.isSelected()) {
				filteredGoals.removeIf(g -> game.getGameName().equals(g.getGameName()));
			}
		}
		
		chosenGoals.addAll(filteredGoals);
	}
	
	//Remove all games from the chosen goals based on goal type
	public void removeChosenGoals(GoalType goalType) {
		chosenGoals.removeIf(g -> goalType == g.getGoalType());
	}
}
