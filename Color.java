public enum Color
{
	
	// this ENUM Color class contains all the colors for UNO cards. 
	// Each toString method has been updated to print the color with proper spacing for the cards
	
	RED("\033[0;31m"){
		public String toString()
		{
			return(this.getEnumColor() + "⢞⡯RED⢞⡯" + this.getWhite());
		}
	},
	BLUE("\033[0;34m"){
		public String toString()
		{
			return(this.getEnumColor() + "⡯BLUE⢞⡯" + this.getWhite());
		}
	},
	GREEN("\033[0;32m"){
		public String toString()
		{
			return(this.getEnumColor() + "⡯GREEN⡯" + this.getWhite());
		}
	},
	YELLOW("\033[0;33m"){
		public String toString()
		{
			return(this.getEnumColor() +"YELLOW⣟"+ this.getWhite());
		}
	},
	BLANK("\033[0;37m"){
		public String toString()
		{
			return"⣟PICK⢞⡯";
		}
	};
	
	// initialize fields
	private final String color;
    private final String WHITE;
	
	// getEnumColor returns the String that represents the color of the current Enum
	public String getEnumColor()
	{
		return(this.color);
	}
	
	// getWhite returns the String representing the white color. needed for resetting when printing colors!
	public String getWhite()
	{
		return(this.WHITE);
	}
	
	private Color(String color)
	{ 
		this.color = color;   
		this.WHITE = "\033[0;37m"; 
	}
}