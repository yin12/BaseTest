package com.example.basetest.projectmanage;

/**
 * The class XMLDecoder is a decoder definition for some special characters in
 * XML or HTML content.
 * 
 * @author winter yu
 */
public class XMLDecoder {
	private static final String[] _arrEncoded = { "&lt;", "&gt;", "&amp;",
			"&apos;", "&quot;" };

	private static char[] _arrResult = { '<', '>', '&', '\'', '"' };

	public static String exec(String text) {
		StringBuffer _buf = new StringBuffer();
		int iBegin = 0, iAmp = 0;
		do {
			iAmp = text.indexOf('&', iBegin);
			if (iAmp < 0 || iAmp + 3 >= text.length()) {
				_buf.append(text.substring(iBegin));
				return _buf.toString();
			}
			int ri = -1;
			switch (text.charAt(iAmp + 1)) {
			case 'l':
				if (text.indexOf(_arrEncoded[0], iAmp) == iAmp)
					ri = 0;
				break;
			case 'g':
				if (text.indexOf(_arrEncoded[1], iAmp) == iAmp)
					ri = 1;
				break;
			case 'a':
				if (text.indexOf(_arrEncoded[2], iAmp) == iAmp)
					ri = 2;
				else if (text.indexOf(_arrEncoded[3], iAmp) == iAmp)
					ri = 3;
				break;
			case 'q':
				if (text.indexOf(_arrEncoded[4], iAmp) == iAmp)
					ri = 4;
				break;
			}
			if (ri >= 0) {
				_buf.append(text.substring(iBegin, iAmp));
				_buf.append(_arrResult[ri]);
				iBegin = iAmp + _arrEncoded[ri].length();
			} else {
				_buf.append(text.substring(iBegin, iAmp + 1));
				iBegin = iAmp + 1;
			}
		} while (iBegin < text.length());
		return _buf.toString();
	}
}
