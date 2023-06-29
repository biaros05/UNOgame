public enum Value
{
	// this ENUM Value class contains all the values for UNO cards. 
	// Each toString method has been updated to print the value with proper spacing for the cards
	ZERO{
		public String toString()
		{
			return "⣳⢞⡯ZERO⣳⢞⡯ ";
		}
	},
	ONE{
		public String toString()
		{
			return "⡽⣳⢞⡯ONE⣳⢞⡯ ";
		}
	},
	TWO{
		public String toString()
		{
			return"⡽⣳⢞⡯TWO⣳⢞⡯ ";
		}
	},
	THREE{
		public String toString()
		{
			return"⣳⢞⡯THREE⢞⡯ ";
		}
	},
	FOUR{
		public String toString()
		{
			return"⣳⢞⡯FOUR⣳⢞⡯ ";
		}
	},
	FIVE{
		public String toString()
		{
			return "⣳⢞⡯FIVE⣳⢞⡯ ";
		}
	},
	SIX{
		public String toString()
		{
			return"⣳⢞⡯SIX⡽⣳⢞⡯ ";
		}
	},
	SEVEN{
		public String toString()
		{
			return "⣳⢞⡯SEVEN⢞⡯ ";
		}
	},
	EIGHT{
		public String toString()
		{
			return"⣳⢞⡯EIGHT⢞⡯ ";
		}
	},
	NINE{
		public String toString()
		{
			return"⣳⢞⡯NINE⣳⢞⡯ ";
		}
	},
	SKIP{
		public String toString()
		{
			return "⣳⢞⡯SKIP⣳⢞⡯ ";
		}
	},
	DRAW2{
		public String toString()
		{
			return"⣳⢞⡯DRAW2⢞⡯ ";
		}
	},
	WILDDRAW4{
		public String toString()
		{
			return "⣟WILDDRAW4 ";
		}
	},
	WILDCARD{
		public String toString()
		{
			return"⣟WILD CARD ";
		}
	}
}