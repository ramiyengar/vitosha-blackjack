package eu.veldsoft.vitosha.blackjack;

class Game {
	private Dealer dealer;
	private Player player;

	public Game(Dealer dealer, Player player) {
		this.dealer = dealer;
		this.player = player;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void increaseBet(int amount) {
		dealer.acceptBetFrom(player, amount + player.getBet());
	}

	public void clearBet() {
		player.clearBet();
	}

	public void newGame() {
		dealer.deal(player);
	}

	public void hit() {
		dealer.hit(player);
	}

	public void stand() {
		dealer.stand(player);
	}

	public void playDouble() {
		dealer.playDouble(player);
	}
}
