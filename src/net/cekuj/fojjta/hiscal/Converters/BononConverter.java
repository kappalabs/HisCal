package net.cekuj.fojjta.hiscal.Converters;

public class BononConverter extends Converter {

	@Override
	public String toStdYear(String fromYear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer[] toStdDate(Object[] params) {
		int year = Integer.parseInt(params[0].toString());
		int month = Integer.parseInt(params[1].toString());
		// intrants or exiens
		int mpart = Integer.parseInt(params[2].toString());
		// XI...
		int daybeac = Integer.parseInt(params[3].toString());
		//dies (pen)ultimus
		int beacon = Integer.parseInt(params[4].toString());
		
		Integer[] res = {daybeac, month, year};
		
		// intrants
		// ========
		if (mpart==0) return res;
		// exiens
		// ======
		if (beacon==0) daybeac = 2; // penultimus
		else if (beacon==1) daybeac = 1; // ultimus
		
		int ndays = Month.getIthMonth(month).days;
		int day = ndays - daybeac+1;
		res[0] = day;
		
		if (month==2 && isStepYear(year)) res[0]++;
			
		return res;
	}

}
