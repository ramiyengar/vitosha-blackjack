package eu.veldsoft.vitosha.blackjack;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

class GamePanel extends JPanel implements ActionListener {
	private Game game;

	private GameTable table;

	private JButton newGameButton = new JButton("Deal");
	private JButton hitButton = new JButton("Hit");
	private JButton doubleButton = new JButton("Double");
	private JButton standButton = new JButton("Stand");
	private JButton add1Chip = new JButton("1");
	private JButton add5Chip = new JButton("5");
	private JButton add10Chip = new JButton("10");
	private JButton add25Chip = new JButton("25");
	private JButton add100Chip = new JButton("100");
	private JButton clearBet = new JButton("Clear");

	private JLabel currentBet = new JLabel("Please set your bet...");
	private JLabel playerWallet = new JLabel("$999.99");
	private JLabel cardsLeft = new JLabel("Cards left...");
	private JLabel dealerSays = new JLabel("Dealer says...");

	public GamePanel() {
		this.setLayout(new BorderLayout());

		table = new GameTable();
		add(table, BorderLayout.CENTER);

		JPanel betPanel = new JPanel();
		betPanel.add(currentBet);
		betPanel.add(clearBet);
		betPanel.add(add1Chip);
		betPanel.add(add5Chip);
		betPanel.add(add10Chip);
		betPanel.add(add25Chip);
		betPanel.add(add100Chip);
		betPanel.add(playerWallet);

		JPanel dealerPanel = new JPanel();
		dealerPanel.add(dealerSays);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(newGameButton);
		optionsPanel.add(hitButton);
		optionsPanel.add(doubleButton);
		optionsPanel.add(standButton);
		optionsPanel.add(cardsLeft);

		JPanel bottomItems = new JPanel();
		bottomItems.setLayout(new GridLayout(0, 1));
		bottomItems.add(dealerPanel);
		bottomItems.add(betPanel);
		bottomItems.add(optionsPanel);
		add(bottomItems, BorderLayout.SOUTH);

		// opaque stuff
		// this.setBackground(new Color(6, 120, 0)); // now done in
		// AppWindow.java
		betPanel.setOpaque(false);
		dealerPanel.setOpaque(false);
		optionsPanel.setOpaque(false);
		bottomItems.setOpaque(false);

		// add listeners to buttons
		newGameButton.addActionListener(this);
		hitButton.addActionListener(this);
		doubleButton.addActionListener(this);
		standButton.addActionListener(this);
		clearBet.addActionListener(this);
		add1Chip.addActionListener(this);
		add5Chip.addActionListener(this);
		add10Chip.addActionListener(this);
		add25Chip.addActionListener(this);
		add100Chip.addActionListener(this);

		// tool tips
		newGameButton.setToolTipText("Deal a new game.");
		hitButton.setToolTipText("Request another card.");
		doubleButton
				.setToolTipText("Double your bet, and receive another card.");
		standButton.setToolTipText("Stand with your card-hand.");
		clearBet.setToolTipText("Clear your current bet.");
		add1Chip.setToolTipText("Add a $1 chip to your current bet.");
		add5Chip.setToolTipText("Add a $5 chip to your current bet.");
		add10Chip.setToolTipText("Add a $10 chip to your current bet.");
		add25Chip.setToolTipText("Add a $25 chip to your current bet.");
		add100Chip.setToolTipText("Add a $100 chip to your current bet.");

		game = new Game(new Dealer(), new Player("James Bond", 32, "Male", 100));

		updateValues();
	}

	public Game getGame() {
		return game;
	}

	public void actionPerformed(ActionEvent evt) {
		String act = evt.getActionCommand();

		if (act.equals("Deal")) {
			newGame();
		} else if (act.equals("Hit")) {
			hit();
		} else if (act.equals("Double")) {
			playDouble();
		} else if (act.equals("Stand")) {
			stand();
		} else if (act.equals("1") || act.equals("5") || act.equals("10")
				|| act.equals("25") || act.equals("100")) {
			game.increaseBet(Integer.parseInt(act));
		} else if (act.equals("Clear")) {
			System.out.println("clear bet");
			clearBet();
		}

		updateValues();
	}

	public void newGame() {
		game.getDealer().deal(game.getPlayer());
	}

	public void hit() {
		game.getDealer().hit(game.getPlayer());
	}

	public void playDouble() {
		game.getDealer().playDouble(game.getPlayer());
	}

	public void stand() {
		game.getDealer().stand(game.getPlayer());
	}

	public void clearBet() {
		game.getPlayer().clearBet();
	}

	public void updateValues() {
		dealerSays
				.setText("<html><p align=\"center\"><font face=\"Serif\" color=\"white\" style=\"font-size: 20pt\">"
						+ game.getDealer().says() + "</font></p></html>");

		if (game.getDealer().isGameOver()) {
			if (game.getPlayer().betPlaced() && !game.getPlayer().isBankrupt()) {
				newGameButton.setEnabled(true);
			} else {
				newGameButton.setEnabled(false);
			}
			hitButton.setEnabled(false);
			doubleButton.setEnabled(false);
			standButton.setEnabled(false);

			if (game.getPlayer().betPlaced()) {
				clearBet.setEnabled(true);
			} else {
				clearBet.setEnabled(false);
			}

			if (game.getPlayer().getWallet() >= 1.0) {
				add1Chip.setEnabled(true);
			} else {
				add1Chip.setEnabled(false);
			}

			if (game.getPlayer().getWallet() >= 5) {
				add5Chip.setEnabled(true);
			} else {
				add5Chip.setEnabled(false);
			}

			if (game.getPlayer().getWallet() >= 10) {
				add10Chip.setEnabled(true);
			} else {
				add10Chip.setEnabled(false);
			}

			if (game.getPlayer().getWallet() >= 25) {
				add25Chip.setEnabled(true);
			} else {
				add25Chip.setEnabled(false);
			}

			if (game.getPlayer().getWallet() >= 100) {
				add100Chip.setEnabled(true);
			} else {
				add100Chip.setEnabled(false);
			}
		} else {
			newGameButton.setEnabled(false);
			hitButton.setEnabled(true);
			if (game.getDealer().canPlayerDouble(game.getPlayer())) {
				doubleButton.setEnabled(true);
			} else {
				doubleButton.setEnabled(false);
			}

			standButton.setEnabled(true);

			clearBet.setEnabled(false);
			add1Chip.setEnabled(false);
			add5Chip.setEnabled(false);
			add10Chip.setEnabled(false);
			add25Chip.setEnabled(false);
			add100Chip.setEnabled(false);
		}

		// redraw cards and totals
		table.update(game.getDealer().getHand(), game.getPlayer().getHand(),
				(game.getDealer().areCardsFaceUp()) ? true : false);
		table.setNames(game.getDealer().getName(), game.getPlayer().getName());
		table.repaint();

		cardsLeft
				.setText("Deck: "
						+ game.getDealer().cardsLeftInPack()
						+ "/"
						+ (game.getDealer().CARD_PACKS * eu.veldsoft.vitosha.blackjack.CardPack.CARDS_IN_PACK));

		if (game.getPlayer().isBankrupt()) {
			moreFunds();
		}

		// redraw bet
		currentBet.setText(Double.toString(game.getPlayer().getBet()));
		playerWallet.setText(Double.toString(game.getPlayer().getWallet()));
	}

	private void moreFunds() {
		int response = JOptionPane
				.showConfirmDialog(
						null,
						"Marshall Aid. One Hundred dollars. With the compliments of the USA.",
						"Out of funds", JOptionPane.YES_NO_OPTION);

		if (response == JOptionPane.YES_OPTION) {
			game.getPlayer().setWallet(100.00);
			updateValues();
		}
	}

	public void savePlayer() {
		if (game.getDealer().isGameOver()) {
			JFileChooser playerSaveDialog = new JFileChooser("~");
			SimpleFileFilter fileFilter = new SimpleFileFilter(".ser",
					"(.ser) Serialised Files");
			playerSaveDialog.addChoosableFileFilter(fileFilter);
			int playerSaveResponse = playerSaveDialog.showSaveDialog(this);

			if (playerSaveResponse == playerSaveDialog.APPROVE_OPTION) {
				String filePath = playerSaveDialog.getSelectedFile()
						.getAbsolutePath();

				try {
					ObjectOutputStream playerOut = new ObjectOutputStream(
							new FileOutputStream(filePath));
					playerOut.writeObject(game.getPlayer());
					playerOut.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Can't save a player while a game is in progress.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void openPlayer() {
		if (game.getDealer().isGameOver()) {
			JFileChooser playerOpenDialog = new JFileChooser("~");
			SimpleFileFilter fileFilter = new SimpleFileFilter(".ser",
					"(.ser) Serialised Files");
			playerOpenDialog.addChoosableFileFilter(fileFilter);
			int playerOpenResponse = playerOpenDialog.showOpenDialog(this);

			if (playerOpenResponse == playerOpenDialog.APPROVE_OPTION) {
				String filePath = playerOpenDialog.getSelectedFile()
						.getAbsolutePath();

				try {
					ObjectInputStream playerIn = new ObjectInputStream(
							new FileInputStream(filePath));
					Player openedPlayer = (Player) playerIn.readObject();
					openedPlayer.hand = new PlayerCardHand();
					game.setPlayer(openedPlayer);
					playerIn.close();
					System.out.println(openedPlayer.getName());
				} catch (ClassNotFoundException e) {
					System.err.println(e);
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		} else {
			JOptionPane
					.showMessageDialog(
							this,
							"Can't open an existing player while a game is in progress.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updatePlayer() {
		PlayerDialog playerDetails = new PlayerDialog(null, "Player Details",
				true, game.getPlayer());
		playerDetails.setVisible(true);

		game.setPlayer(playerDetails.getPlayer());
	}
}