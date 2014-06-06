package org.openntf.xsp.extlib.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.openntf.xsp.extlib.converter.util.DataScrubber;

import lotus.domino.MIMEEntity;
import lotus.domino.NotesException;

import com.ibm.xsp.http.MimeMultipart;
import com.ibm.xsp.model.domino.wrapped.DominoRichTextItem;

public class RichTextConverter implements Converter {

	private static final Logger logger_ = Logger.getLogger(RichTextConverter.class.getName());

	public Object getAsObject(FacesContext context, UIComponent component, String valueStr) {
		// TODO Auto-generated method stub
		logger_.log(Level.FINER, "getAsObject returning " + valueStr);
		return valueStr;
	}

	public String getAsString(FacesContext context, UIComponent component, Object valueObj) {
		// TODO Auto-generated method stub
		String result = null;
		logger_.log(Level.FINER, "getAsString valueObj is a " + valueObj.getClass().getName());
		String[] fontParams = { "color", "family" };
		String[] tdParams = { "width" };
		String[] tableParams = { "border" };
		String tempValue = null;
		if (valueObj instanceof MimeMultipart) {
			MimeMultipart mime = (MimeMultipart) valueObj;
			tempValue = mime.getHTML();
		} else if (valueObj instanceof DominoRichTextItem) {
			DominoRichTextItem rtItem = (DominoRichTextItem) valueObj;
			try {
				MIMEEntity mime = rtItem.getMIMEEntity();
				if (mime != null) {
					tempValue = mime.getContentAsText();
				} else {
					tempValue = rtItem.getHTML();
				}
			} catch (NotesException e) {
				e.printStackTrace();
			}
		}
		if (tempValue != null) {
			tempValue = DataScrubber.removeFontParameter(tempValue, fontParams);
			tempValue = DataScrubber.removeTdParameter(tempValue, tdParams);
			tempValue = DataScrubber.removeTableParameter(tempValue, tableParams);
			result = tempValue;
		}
		logger_.log(Level.FINER, "getAsString returning" + result);
		return result;
	}

}
