package de.liquidz.makeorbuy;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class NumberTextField extends JTextField {

	private static final long	serialVersionUID	= 6713440921911586391L;

	public NumberTextField(int value, int columns) {
		super(String.valueOf(value), columns);
	}

	@Override
	protected Document createDefaultModel() {
		return new UpperCaseDocument();
	}

	static class UpperCaseDocument extends PlainDocument {

		private static final long	serialVersionUID	= 903280927653210924L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

			if (str == null) {
				return;
			}

			char[] chars = str.toCharArray();
			boolean ok = true;

			for (int i = 0; i < chars.length; i++) {

				try {
					Integer.parseInt(String.valueOf(chars[i]));
				} catch (NumberFormatException exc) {
					ok = false;
					break;
				}

			}

			if (ok) super.insertString(offs, new String(chars), a);

		}
	}

}
