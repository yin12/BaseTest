/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.basetest.projectmanage;

/**
 * The class XMLEncoder is a encoder definition for some special characters in
 * XML or HTML content.
 * 
 * @author winter yu
 */
public class XMLEncoder {
	private static String _specChars = "<>&'\"";
	private static String[] _arrEncoded = { "&lt;", "&gt;", "&amp;", "&apos;",
			"&quot;" };

	public String exec(char ch) {
		int j = _specChars.indexOf(ch);
		if (j >= 0)
			return _arrEncoded[j];
		else
			return "" + ch;
	}

	public static String exec(String text) {
		StringBuffer _ret = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			int j = _specChars.indexOf(text.charAt(i));
			if (j >= 0)
				_ret.append(_arrEncoded[j]);
			else
				_ret.append(text.charAt(i));
		}
		return _ret.toString();
	}
}
