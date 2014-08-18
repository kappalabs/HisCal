package net.cekuj.fojjta.hiscal;

public abstract class Converter {
	
	/**
	 * Enum type which keeps information about months, its order
	 * and std number of days in it.
	 * 
	 * @author kappa
	 *
	 */
	enum Month {
		Jan(1, 31), Feb(2, 28), Mar(3, 31), Apr(4, 30), May(5, 31), Jun(6, 30),
		Jul(7, 31), Aug(8, 31), Sep(9, 30), Oct(10, 31), Nov(11, 30), Dec(12, 31);
	    private final double order, days;
	    Month(int order, int days) { this.order = order; this.days = days; }
	};
	
	/**
	 * Method for converting different calendars years.
	 * Returns year in standard format of null if input is
	 * not in correct format.
	 * 
	 * @param fromYear input year for conversion to standard format
	 * @return String year if input is in correct format, null otherwise
	 */
	abstract String toStdYear(String fromYear);

}
