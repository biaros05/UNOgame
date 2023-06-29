public class Player
{
	private DynamicCardArray hand;
	private String password; // protect a player's hand using a password!

	// constructor
	public Player(String password)
	{
		this.hand = new DynamicCardArray();
		this.password = password;
	}
	
	// toString
	public String toString()
	{
		return(this.hand + "");
	}
	
	// ************************************* GETTER METHODS *************************************** //
	
	//getHand method that returns the player's hand
	public DynamicCardArray getHand()
	{
		return(this.hand);
	}
	
	// ********************************** HAND CHECKING METHODS (BOOLEAN) ********************************* //
	
	// this method takes a String as input and returns whether it is equal to that player's password
	public boolean checkPassword(String password)
	{
		return(this.password.equals(password));
	}
	
	/** checkCardValid takes as input the index of the player's card and the card that is currently facing up 
	  * and checks if the player can effectively play their card
	  */
	public boolean checkCardValid(int index, Color color, Value value)
	{
		if (index < 0 || index >= this.hand.length())
		{
			throw new IllegalArgumentException("index must be >= 0 and < " + this.hand.length() + "!");
		}
		boolean canPlayCard = canPlayCard(index, color, value);
		return(canPlayCard);
	}
	
	// helper method to refactor code: checks if the player can effectively play the desired card based on the index provided
	private boolean canPlayCard(int index, Color color, Value value)
	{
		return(this.hand.get(index).getColor() == color || 
		this.hand.get(index).getValue() == value || 
		this.hand.get(index).getColor() == Color.BLANK); // BLANK can be played on any color or value
	}
	
	/** this method checks whether the player has a valid card in their deck:
	  * either the value OR color of any of their cards is equal to the faceUP
	  * OR they have a Wild Card they can use.
	  */
	public boolean validCardInDeck(Color color, Value value)
	{
		return(hand.contains(color, value) || hand.contains(Color.BLANK));
	}
	
	// the checkIfWinner method checks if a player has won the game (if they have no cards left!)
	public boolean checkIfWinner()
	{
		return(this.hand.length() == 0);
	}
	
	// ******************************* HAND ALTERING METHODS (DRAW & PLAY CARDS) ****************************** //
	
	// the drawCard method allows the player to draw a card from the deck and adds it to their hand
	public void drawCard(DynamicCardArray deck)
	{
		Card newCard = deck.takeFromTop();
		this.hand.add(newCard);
	}
	
	// this method takes no input and allows the player to draw a random card from the deck
	public void drawRandomCard(DynamicCardArray deck)
	{
		Card newCard = deck.getRandomCard();
		this.hand.add(newCard);
	}
	
	/** the drawCard method allows the player to draw two cards from the deck 
	  * and adds them to their hand if the value of the card facing up is DRAW2
	  */
	public void drawTwoCards(DynamicCardArray deck, Value value)
	{
		if (value == Value.DRAW2)
		{
			drawCard(deck);
			drawCard(deck);
		}
	}
	
	/** the drawCard method allows the player to draw four cards from the deck 
	  * and adds them to their hand if the value of the card facing up is WILDDRAW4
	  */
	public void drawFourCards(DynamicCardArray deck, Value value)
	{
		if (value == Value.WILDDRAW4)
		{
			drawCard(deck);
			drawCard(deck);
			drawCard(deck);
			drawCard(deck);
		}
	}
	
		// this method takes no input and draws 7 random cards for the player (useful for when the game begins)
	public void draw7Cards(DynamicCardArray deck)
	{
		int numStartCards = 7;
		for (int i = 0; i < numStartCards; i++)
		{
			drawRandomCard(deck);
		}
	}
	
	/** the playACard method takes as input the index of the card the player will play,
	  *	removes it from the player's hand and returns that same Card played.
	  * NOTE: whether the card can be played is already checked in the main method!
	  */
	public Card playCard(int index)
	{
		if (index < 0 || index >= this.hand.length())
		{
			throw new IllegalArgumentException("index must be >= 0 and < " + this.hand.length() + "!");
		}
		
		Card newFaceUp = this.hand.get(index);
		this.hand.remove(index);
		return(newFaceUp);
	}

}
	