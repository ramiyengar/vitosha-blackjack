package eu.veldsoft.vitosha.blackjack;

class Table {
	private DealerCardHand dealer;

	private PlayerCardHand player;

	private boolean showAllDealerCards;

	public DealerCardHand getDealer() {
		return dealer;
	}

	public void setDealer(DealerCardHand dealer) {
		this.dealer = dealer;
	}

	public PlayerCardHand getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCardHand player) {
		this.player = player;
	}

	public boolean isShowAllDealerCards() {
		return showAllDealerCards;
	}

	public void setShowAllDealerCards(boolean showAllDealerCards) {
		this.showAllDealerCards = showAllDealerCards;
	}

	public void updateAll(DealerCardHand dealer, PlayerCardHand player,
			boolean showDealer) {
		setDealer(dealer);
		setPlayer(player);
		setShowAllDealerCards(showDealer);
	}
}
