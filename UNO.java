import java.util.*;
public class UNO
{
	public static final Scanner scan = new Scanner(System.in);
	public static final Random ranGen = new Random();
	public static void main(String[] args)
	{
		// initialize all arrays & variables
		DynamicCardArray deck = createDynamicCardArrayDeck();
		Player[] players = createPlayersArray();
		initializePlayers(players, deck);
		DynamicCardArray discardPile = initializeDiscardPile(deck);
		int playerNum = ranGen.nextInt(players.length);
		boolean gameOver = false;
		/** this variable is used to keep track of whether the player has been affected by the special card.
		  * if the player has ALREADY been affected by the special card, it will reset to -1 until another special card affects another player.
		  */
		int nextPlayer = -1;
		shuffleCards(deck);
		introductoryRules();
		while(!gameOver)
		{
			startRoundPrints();
			// initialize variables per round
			Card faceUp = discardPile.get(discardPile.length()-1);
			playerNum = updatePlayerNum(playerNum, players.length);
			passwordCheck(players[playerNum], playerNum);
			Color color = isWildCard(faceUp);
			// if the deck is empty, reshuffle discardPile and reassign
			if (deck.length() == 0) 
			{
				faceUp = reuseDiscardPile(discardPile);
				deck = discardPile;
				discardPile = new DynamicCardArray();
				discardPile.add(faceUp);
			}
			// check for special cards and act accordingly (turn automatically skipped if there is a skip)
			if (faceUp.getValue() == Value.SKIP && playerNum == nextPlayer) 
			{
				nextPlayer = -1; 
				continue;
			}
			else if ((faceUp.getValue() == Value.DRAW2 || faceUp.getValue() == Value.WILDDRAW4) && playerNum == nextPlayer) 
			{
				specialCards(players[playerNum], faceUp, deck);
				nextPlayer = -1;
			}
			// time for the player to play their turn! if they can, let them play. else: draw a card and skip their turn.
			if (players[playerNum].validCardInDeck(color, faceUp.getValue())) 
			{
				printInterface(players[playerNum], playerNum, faceUp);
				int cardIndex = cardToPlay(color, faceUp.getValue(), players[playerNum]);
				Card newFaceUp = players[playerNum].playCard(cardIndex);
				discardPile.add(newFaceUp);
				nextPlayer = specialCardsUpdate(nextPlayer, newFaceUp, playerNum, players.length);
			}
			// PLAYER'S TURN IS SKIPPED IF THERE ARE NO PLAYABLE CARDS!
			else 
			{
				players[playerNum].drawCard(deck);
			}
			// check if the current player wins and end the game if so
			gameOver = isGameOver(players[playerNum], playerNum);
		}
		scan.close();
	}
	
	// *********************** VALIDATION METHODS **********************
	
	// isGameOver takes as input a player and the playerNum and returns a boolean to indicate whether the game is over
	public static boolean isGameOver(Player player, int playerNum)
	{
		if (player.checkIfWinner())
			{
				printGameOver(playerNum);
				return(true);
			}
		return(false);
	}
	
	// passwordCheck method takes a player and the player number and asks the player to enter their password until they get it right and the loop ends.
	public static void passwordCheck(Player player, int playerNum)
	{
		System.out.println("Player " + (playerNum+1) + ", enter your password to play your turn!");
		String password = scan.nextLine();
		while (!player.checkPassword(password))
		{
			System.out.println("Try again!");
			password = scan.nextLine();
		}
	}
	
	/** the cardToPlay method takes the faceUp card's color, value, and the current player. it will then ask the
	  * to pick a card from their deck until they enter a valid one that matches either the color or the value (or wild card),
	  * and return the valid index 
	  */ 
	public static int cardToPlay(Color color, Value value, Player player)
	{
		int cardIndex = 0;
		boolean isSuccessful = false;
		while (!isSuccessful)
		{
			try {
				cardIndex = Integer.parseInt(scan.nextLine());
				while (!player.checkCardValid(cardIndex, color, value) 
						&& !(player.getHand().get(cardIndex).getColor() == Color.BLANK))
				{
					System.out.println("That card cannot be played! Try again.");
					cardIndex = Integer.parseInt(scan.nextLine());
				}
				isSuccessful = true;
			}
			catch (NumberFormatException e)
			{
				System.out.println("Illegal argument. Please enter an integer!");
			}
			catch (IllegalArgumentException e)
			{
				System.out.println(e.getMessage());
			}
		}
		return(cardIndex);
	}
	
	// ******************* UPDATE VARIABLE METHODS **********************
	
	// the updatePlayerNum method updates the player number accordingly and returns the propern playerNum index.
	public static int updatePlayerNum(int playerNum, int length)
	{
		if (playerNum >= length - 1)
		{
			playerNum = 0;
		}
		else
		{
			playerNum++;
		}
		return(playerNum);
	}
	
	// ************************* SPECIAL CARDS METHODS **************************** //
	
	/**
	  * the specialCardsUpdate method takes the numPlayers, current playerNum, nextPlayer, and the faceUp card and checks whether
	  * nextPlayer should be updated (if the next player is affected by a special card) or not, and updates the variable accordingly
	  */
	public static int specialCardsUpdate(int nextPlayer, Card newFaceUp, int playerNum, int numPlayers)
	{
		if (newFaceUp.getValue() == Value.SKIP || newFaceUp.getValue() == Value.DRAW2 || newFaceUp.getValue() == Value.WILDDRAW4)
				{
					nextPlayer = updatePlayerNum(playerNum, numPlayers);
				}
		return(nextPlayer);
	}
	
	/** the specialCards method takes as input the faceup, player, and deck as input.
	  * it will check if the player must draw 2 or 4 cards depending on the card that is currently facing up
	  */
	public static void specialCards(Player player, Card faceUp, DynamicCardArray deck)
	{
		if(faceUp.getValue() == Value.DRAW2)
		{
			System.out.println("You must draw 2 cards!");
			player.drawTwoCards(deck, faceUp.getValue());
		}
		else if (faceUp.getValue() == Value.WILDDRAW4)
		{
			System.out.println("You must draw 4 cards!");
			player.drawFourCards(deck, faceUp.getValue());
		}
	}
	
	/** the isWildCard method takes the faceUp card as input. if it is a wild card, it asks the player to
	  * pick a color and returns that color. or else, it returns the current color of the faceUp card!
	  */
	public static Color isWildCard(Card faceUp)
	{
		int BLUE = 0;
		int RED = 1;
		int GREEN = 2;
		int YELLOW = 3;
		if (faceUp.getColor() == Color.BLANK)
		{
			System.out.println("WILD CARD! Enter the number of the color you would like to continue the game with!");
			while(true)
			{
				System.out.println("0: BLUE, 1: RED, 2: GREEN. 3: YELLOW");
				try{
					int color = Integer.parseInt(scan.nextLine());
					if (color == BLUE)
					{
						return(Color.BLUE);
					}
					else if (color == RED)
					{
						return(Color.RED);
					}
					else if (color == GREEN)
					{
						return(Color.GREEN);
					}
					else if (color == YELLOW)
					{
						return(Color.YELLOW);
					}
					else
					{
						System.out.println("Invalid color! Enter a number between 0-3 to pick your color.");
					}
				}
				catch(NumberFormatException e)
				{
					System.out.println("Illegal argument. Please enter an integer!");
				}
			}
		}
		else
		{
			return(faceUp.getColor());
		}
	}
	
	// *************************** INITIALIZER METHODS *************************** //
	
	/** reuseDiscardPile method takes the discardPile as input, stores the faceUp card in a variable, removes it from the discard pile and shuffles it.
	  * it returns the faceUp card to the calling method
	  */
	public static Card reuseDiscardPile(DynamicCardArray discardPile)
	{
		Card faceUp = discardPile.get(discardPile.length()-1);
		discardPile.remove(discardPile.length()-1);
		shuffleCards(discardPile);
		return(faceUp);
	}

	// initializeDiscardPile method initiaizes the discardPile by adding the faceUp and returns it
	public static DynamicCardArray initializeDiscardPile(DynamicCardArray deck)
	{
		DynamicCardArray discardPile = new DynamicCardArray();
		discardPile.add(deck.get(deck.length()-1));
		deck.remove(deck.length()-1);
		return(discardPile);
	}
	
	// the createDynamicCardArrayDeck method creates the deck with all necessary UNO cards and returns it
	public static DynamicCardArray createDynamicCardArrayDeck()
	{
		DynamicCardArray array = new DynamicCardArray();
		for (Color color : Color.values())
		{
			for (Value value : Value.values())
			{
				loopForCards(color, value, array);
			}
		}
		for (int i = 0; i < 4; i++)
		{
			array.add(new Card(Color.BLANK, Value.WILDCARD));
			array.add(new Card(Color.BLANK, Value.WILDDRAW4));
		}
		return(array);
	}
	
	private static void loopForCards(Color color, Value value, DynamicCardArray array)
	{
		for (int i = 0; i < 2; i++)
		{
			if (color != Color.BLANK 
				&& value != Value.WILDCARD 
				&& value != Value.WILDDRAW4)
			{
				array.add(new Card(color, value));
			}
		}
	}
	
	// the createPlayersArray method creates the Player array and deals each player 7 cards to begin
	public static Player[] createPlayersArray()
	{
		
		boolean isSuccessful = false;
		Player[] players = new Player[0];
		System.out.println("Before we begin, how many players will be participating? (minimum 3, maximum 7)");
		while(!isSuccessful)
		{
			try {
				int numPlayers = Integer.parseInt(scan.nextLine());
				while(numPlayers > 7 || numPlayers < 3)
				{
					System.out.println("Minimum 3 players, maximum 7 players!");
					numPlayers = Integer.parseInt(scan.nextLine());
				}
				players = new Player[numPlayers];
				isSuccessful = true;
			}
			catch (NumberFormatException e)
			{
				System.out.println("Illegal argument. Please enter an integer!");
			}
			catch (NegativeArraySizeException e)
			{
				System.out.println("Number must be greater than 0!"); 
			}
		}
		return(players);
	}
	
	// the initializePlayers method initializes each player in the Player array by creating the Player and asking for their passwords
	public static void initializePlayers(Player[] players, DynamicCardArray deck)
	{
		String password = "";
		System.out.println("In order to see the cards in your deck, each player will need their own passwords! This prevents any cheating from taking place");
		for (int i = 0; i < players.length; i++)
		{
			System.out.println("Player " + (i+1) + ", enter your desired password: ");
			password = scan.nextLine();
			players[i] = new Player(password);
			players[i].draw7Cards(deck);
		}
	}
	
	
	// ************************** MISC METHODS ************************** //
	
	// the introductoryRules method prints the introductory rules for the card game
	public static void introductoryRules()
	{
		Card c = new Card(Color.RED, Value.ONE);
		System.out.println("\033c");
		System.out.println(ASCII.printWelcome());
		System.out.println(ASCII.noU());
		System.out.println("Welcome to Uno! Playing this game is very simple. You will have a hand of cards, which will be displayed if you have any playable cards.");
		System.out.println("In order to play a card, your hand will display numbers over each of your cards. Enter the number above your desired card in order to play it!");
		System.out.println("Note that if the card you wish to play is not playable, you will be asked to enter again. Here is a preview of what it will look like:");
		System.out.println();
		System.out.println("     0     ");
		System.out.println(c);
		System.out.println("Enter a card: [TYPE VALID NUMBER HERE]");
		System.out.println();
		System.out.println("If your hand has no playable cards, the program will automacially draw a card at your turn until you have a playable card.");
		System.out.println("Please review the official UNO rules and see README.md before beginning the game!");
		System.out.println("Enter any key to start the game!");
		String empty = scan.nextLine();
		
	}
	
	// startRoundPrint clears the screen and prints the UNO banner
	public static void startRoundPrints()
	{
		System.out.println("\033c");
		System.out.println();
		System.out.println(ASCII.printUNO());
	}
	
	// printGameOver contains print statements for when the game officially ends
	public static void printGameOver(int playerNum)
	{
		System.out.println("\033c");
		System.out.println("Player " + (playerNum+1) + " has won the game!");
		System.out.println();
		System.out.println(ASCII.spongebobWinner());
		System.out.println(ASCII.youWon());
	}
	
	// printInterface prints the player's cards and the faceUp card
	public static void printInterface(Player player, int playerNum, Card faceUp)
	{
		System.out.println("Player " + (playerNum + 1) + "'s turn. Pick a card from your deck: ");
		System.out.println(player);
		System.out.println("FACEUP CARD:");
		System.out.println(faceUp);
		System.out.println("Enter a card: ");
	}
	
	// the shuffleDeck method takes as input the deck and will shuffle it multiple times
	public static void shuffleCards(DynamicCardArray cards)
	{
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
		cards.shuffle();
	}
}