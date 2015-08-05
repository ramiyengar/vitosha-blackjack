package eu.veldsoft.vitosha.blackjack;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

class GameTable extends JPanel {
	private Table table = null;

	// drawing position vars
	private final int CARD_INCREMENT = 20;
	private final int CARD_START = 100;
	private final int DEALER_POSITION = 50;
	private final int PLAYER_POSITION = 200;

	private final int CARD_IMAGE_WIDTH = 71;
	private final int CARD_IMAGE_HEIGHT = 96;

	private final int NAME_SPACE = 10;

	private Font handTotalFont;
	private Font playerNameFont;

	private String dealerName;
	private String playerName;

	private Image[] cardImages = new Image[CardPack.CARDS_IN_PACK + 1];

	// take game model as parameter so that it can get cards and draw them
	public GameTable() {
		super();

		table = new Table();

		this.setBackground(Color.BLUE);
		this.setOpaque(false);

		handTotalFont = new Font("Serif", Font.PLAIN, 96);
		playerNameFont = new Font("Serif", Font.ITALIC, 20);

		table.setShowAllDealerCards(true);

		for (int i = 0; i < CardPack.CARDS_IN_PACK; i++) {
			String cardName = "/drawable-mdpi/card" + ((i < 9) ? "0" : "")
					+ (i + 1) + ".png";

			URL urlImg = getClass().getResource(cardName);
			Image cardImage = Toolkit.getDefaultToolkit().getImage(urlImg);
			cardImages[i] = cardImage;
		}

		String backCard = "/drawable-mdpi/red_back.png";

		URL backCardURL = getClass().getResource(backCard);
		Image backCardImage = Toolkit.getDefaultToolkit().getImage(backCardURL);

		cardImages[CardPack.CARDS_IN_PACK] = backCardImage;

		MediaTracker imageTracker = new MediaTracker(this);

		for (int i = 0; i < CardPack.CARDS_IN_PACK + 1; i++) {
			imageTracker.addImage(cardImages[i], i + 1);
		}

		try {
			imageTracker.waitForAll();
		} catch (InterruptedException excep) {
			System.out.println("Interrupted while loading card images.");
		}
	}

	public void setNames(String dealerName, String playerName) {
		this.dealerName = dealerName;
		this.playerName = playerName;
	}

	public void update(DealerCardHand dealer, PlayerCardHand player,
			boolean showDealer) {
		table.updateAll(dealer, player, showDealer);
	}

	// draw images from jar archive or dir:
	// http://www.particle.kth.se/~fmi/kurs/PhysicsSimulation/Lectures/10B/jar.html

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);

		g.setFont(playerNameFont);

		g.drawString(dealerName, CARD_START, DEALER_POSITION - NAME_SPACE);
		g.drawString(playerName, CARD_START, PLAYER_POSITION - NAME_SPACE);

		g.setFont(handTotalFont);

		String cardName;

		// draw dealer cards

		int i = CARD_START;

		if (table.isShowAllDealerCards() == true) {
			for (Card aCard : table.getDealer()) {
				g.drawImage(cardImages[aCard.getCode() - 1], i,
						DEALER_POSITION, this);

				i += CARD_INCREMENT;
			}

			g.drawString(Integer.toString(table.getDealer().getTotal()), i
					+ CARD_IMAGE_WIDTH + CARD_INCREMENT, DEALER_POSITION
					+ CARD_IMAGE_HEIGHT);
		} else {
			for (Card aCard : table.getDealer()) {
				g.drawImage(cardImages[CardPack.CARDS_IN_PACK], i,
						DEALER_POSITION, this);

				i += CARD_INCREMENT;
			}

			try {
				Card topCard = table.getDealer().lastElement();

				i -= CARD_INCREMENT;

				g.drawImage(cardImages[topCard.getCode() - 1], i,
						DEALER_POSITION, this);
			} catch (Exception e) {
				// caused when trying to draw cards from empty vector
				// can't use NoSuchElementException above...?
				System.out.println("No cards have been dealt yet.");
			}

			g.drawString("?", i + CARD_IMAGE_WIDTH + CARD_INCREMENT,
					DEALER_POSITION + CARD_IMAGE_HEIGHT);

		}

		// draw player cards

		i = CARD_START;

		for (Card aCard : table.getPlayer()) {
			g.drawImage(cardImages[aCard.getCode() - 1], i, PLAYER_POSITION,
					this);

			i += CARD_INCREMENT;
		}

		g.drawString(Integer.toString(table.getPlayer().getTotal()), i + CARD_IMAGE_WIDTH
				+ CARD_INCREMENT, PLAYER_POSITION + CARD_IMAGE_HEIGHT);
	}
}