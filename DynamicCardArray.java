import java.util.Random;
public class DynamicCardArray
{
	private Card[] array;
	private int next; // represents next free position in the array & array size
	// replace Card[] with DynamicCardArray instead of using java's built in array type, you are using this specific array
	
	// constructor that takes no input and initializes fields
	public DynamicCardArray()
	{
		this.array = new Card[10000];
		this.next = 0;
	}
	
	// this toString method will print the array of cards, seven cards in each row
	public String toString()
	{
		String s = "";
		int loop7Columns = this.next / 7; // represents how many times can you print seven cards in one row.
		int remainder = this.next % 7; // represents remaining cards left
		int index = 0;
		
		for (int i = 0; i < loop7Columns; i++)
		{
			s = s + "\n" + printCards(index, 7);
			index+=7;
		}
		
		// if remainder is not 0, print the remaining cards
		if (remainder != 0)
		{
			s = s + "\n" + printCards(index, remainder);
		}
		
		return(s);
	}
	
	/** this toString helper method is used to print the array of cards side by side. 
	 * depending on the row number, it will print the corresponding line for that row, 
	 * which will take the form of a full card once each row is printed
	 */
	private String printCards(int index, int whenToStop)
	{
		String s = "";
		int numRows = 11;
		int stopLoop = index + whenToStop;
		for (int row = 0; row <= numRows; row++)
		{
			for (int i = index; i < stopLoop; i++)
			{
				s+=checkRow(row, i);
			}
			s+="\n";
		}
		return(s);
	}
	
	// source: https://emojicombos.com/dot-art-editor#
	// this helper method checkRow returns the String corresponding to the correct row number in order to print the card with nice ASCII art.
	private String checkRow(int ROWNUM, int i)
	{
		if (ROWNUM == 0)
		{
			return("     " + i + "   "+ "\t");
		}
		else if (ROWNUM == 1)
		{
			return("⡴⣟⢿⡹⣟⢿⡹⣟⠿⠙⠫⠟⣦"+ "\t");
		}
		else if (ROWNUM == 2)
		{
			return("⣟⠾⣭⢷⣫⠫⣡⠴⣎⡿⣹⡖⡌"+ "\t");
		}
		else if (ROWNUM == 3)
		{
			return("⣞⣯⢽⠚⣠⢞⣵⡻⣟⣞⢧⢿⣱"+ "\t");
		}
		else if (ROWNUM == 4)
		{
			return("⢯⡞ ⡼⢧⣟⢮⡽⣳⢞⡯⣞⣳"+ "\t");
		}
		else if (ROWNUM == 5)
		{
			return("⡟⢠⣟⢿⣹⢮⣟⢷⣟⠿⣼⢫⡇"+ "\t");
		}
		else if (ROWNUM == 6)
		{
			return("⡌⣾⢾"+array[i].getColor()+"⣟⣹⢷"+ "\t");
		}
		else if (ROWNUM == 7)
		{
			return("⢸"+array[i].getValue()+"⣼"+ "\t");
		}
		else if (ROWNUM == 8)
		{
			return("⣞⡮⣗⢯⣳⢯⡞⣧⢟⡧⢋⡼⣳"+ "\t");
		}
		else if (ROWNUM == 9)
		{
			return("⢹⣞⣭⠿⣜⣧⢿⡹⠎⣰⢞⡽⣳"+ "\t");
		}
		else if (ROWNUM == 10)
		{
			return("⡘⠾⣜⣻⣻⠎⢃⣴⢺⣟⡾⣹⣻"+ "\t");
		}
		else
		{
			return("⠛⣶⢤⡤⣖⢾⡻⡼⣟⡾⡵⣟⠷"+ "\t");
		}
		
		
	}
	
	// *********************** GETTER METHODS ************************ //
	
	// length method that takes no input and returns an int representing the number of elements inside the array
	public int length()
	{
		return this.next;
	}
	
			// get method takes as input an int representing an index and returns the value at that index
	public Card get(int i)
	{
		if ( i < 0 || i >= this.next) // mimicking how an array works so you are throwing an out of bounds exception error
		{
			throw new ArrayIndexOutOfBoundsException("Invalid index " + i);
		}
		return(this.array[i]);
	}
	
	// *************************** DECK ALTERING METHODS *********************//
	
	// add method that takes as input a Card and adds it to the next available space in the array
	public void add(Card value)
	{
		this.array[next] = value;
		next++;
	}
	
	// remove method that takes an index and removes the corresponding Card from the Card array
	public void remove(int index)
	{
		for (int i = index; i < this.next - 1; i++)
		{
			this.array[i] = this.array[i+1];
		}
		this.next--;
	}
	
		// this takeFromTop takes no input and returns the card that was at the top of the pile
	public Card takeFromTop()
	{
		Card cardTaken = get(next - 1);
		remove(next - 1);
		return(cardTaken);
	}
	
	//getRandomCard takes no input and returns a random card from the pile
	public Card getRandomCard()
	{
		Random ranGen = new Random();
		Card cardTaken = get(ranGen.nextInt(this.next));
		remove(ranGen.nextInt(this.next));
		return(cardTaken);
	}
	
	// ************************** DECK CHECKING METHODS **************************** //
	
	// contains method that takes a Card's color and calue as input and retuns true if that value OR color is inside the Card array
	public boolean contains(Color color, Value value)
	{
		for (int i = 0; i < this.next; i++)
		{
			if (isCardEqual(color, value, i))
			{
				return true;
			}
		}
		return false;
	}
	
	// contains method that takes a Card's color ONLY as input and retuns true if that color is inside the Card array
	// this method is used for Wld cards; if the value is BLANK, the user will be asked to pick a card
	public boolean contains(Color color)
	{
		for (int i = 0; i < this.next; i++)
		{
			if (get(i).getColor() == color)
			{
				return true;
			}
		}
		return false;
	}
	
	// this checks if one specific card in the DynamicCardArray is equal to eithe the color OR value of the Card given to the method
	public boolean isCardEqual(Color color, Value value, int index)
	{
		return(color == this.array[index].getColor() || value == this.array[index].getValue());
	}
	
	// ******************** MISC ******************** //
	
	// this method shuffles the deck to randomize cards
	public void shuffle()
	{
		Random ranGen = new Random();
		int index = 0;
		Card toSwap;
		for (int i = 0; i < this.next; i++)
		{
			index = ranGen.nextInt(this.next);
			toSwap = this.array[index];
			this.array[index] = this.array[i];
			this.array[i] = toSwap;
		}
	}
	
}