public class Card
{
	private Color color;
	private Value value;
	
	// constructor
	public Card(Color color, Value value)
	{
		this.color = color;
		this.value = value;
	}
	
	// getter method that takes no input and returns the color of the Card
	public Color getColor()
	{
		return(this.color);
	}
	
	// getter method that takes no input and returns the value of the Card
	public Value getValue()
	{
		return(this.value);
	}
	
	// toString method that takes no input and prints the Card object in the form of an actual card
	// source: https://emojicombos.com/dot-art-editor#
	public String toString()
	{
		String s = "⡴⣟⢿⡹⣟⢿⡹⣟⠿⠙⠫⠟⣦" + "\n";
		s += "⣟⠾⣭⢷⣫⠫⣡⠴⣎⡿⣹⡖⡌" + "\n";
		s += "⣞⣯⢽⠚⣠⢞⣵⡻⣟⣞⢧⢿⣱"+ "\n";
		s += "⢯⡞ ⡼⢧⣟⢮⡽⣳⢞⡯⣞⣳" + "\n";
		s += "⡟⢠⣟⢿⣹⢮⣟⢷⣟⠿⣼⢫⡇"+ "\n";
		s += "⡌⣾⢾"+this.color+"⣟⣹⢷" + "\n";
		s += "⢸"+this.value+"⣼" + "\n";
		s += "⣞⡮⣗⢯⣳⢯⡞⣧⢟⡧⢋⡼⣳" + "\n";
		s += "⢹⣞⣭⠿⣜⣧⢿⡹⠎⣰⢞⡽⣳"+ "\n";
		s += "⡘⠾⣜⣻⣻⠎⢃⣴⢺⣟⡾⣹⣻"+ "\n";
		s += "⠛⣶⢤⡤⣖⢾⡻⡼⣟⡾⡵⣟⠷"+ "\n";
		return(s);
	}
}