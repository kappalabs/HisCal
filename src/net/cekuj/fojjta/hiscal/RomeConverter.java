package net.cekuj.fojjta.hiscal;

public class RomeConverter extends Converter {

	enum Romans {
		I(1), V(5), X(10), L(50), C(100), D(500), M(1000);
	    private final double value;
	    Romans(double value) { this.value = value; }
	};
	
	@Override
	String toStdYear(String romanYear) {
		int ret = 0;
		int strLen = romanYear.length();
		int magic = -32;
		
		for (int i=0; i<strLen; i++) {
			boolean is_next = (i+1 != strLen);
			char next = is_next ? romanYear.charAt(i+1) : 'Z';
			
			switch (romanYear.charAt(i)) {
				case 'M':
					ret += Romans.M.value; break;
				case 'D':
					if(!is_next || next != 'M')
						ret += Romans.D.value;
					else ret = magic; break;
				case 'C':
					if(next == 'M' || next == 'D')
						ret -= Romans.C.value;
					else
						ret += Romans.C.value; break;
				case 'L':
					if (!is_next || next == 'L' || next == 'X' || next == 'V' || next == 'I')
						ret += Romans.L.value;
					else ret = magic; break;
				case 'X':
					if (!is_next || next == 'X' || next == 'V' || next == 'I')
						ret += Romans.X.value;
					else if (next == 'L' || next == 'C')
						ret -= Romans.X.value;
					else ret = magic; break;
				case 'V':
					if (!is_next || next == 'V' || next == 'I')
						ret += Romans.V.value;
					else ret = magic; break;
				case 'I':
					if (!is_next || next == 'I')
						ret += Romans.I.value;
					else if (next == 'V' || next == 'X')
						ret -= Romans.I.value;
					else ret = magic; break;
			}
			if (ret == magic) return null;
		}
		
		return String.valueOf(ret);
	}

}
