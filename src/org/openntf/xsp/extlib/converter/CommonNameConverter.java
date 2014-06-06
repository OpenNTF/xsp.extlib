package org.openntf.xsp.extlib.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.openntf.domino.Name;
import org.openntf.domino.utils.Factory;

public class CommonNameConverter implements Converter {

	private Name createName(String name) {
		Name n = null;
		n = Factory.getSession().createName(name);
		return n;
	}

	public Object getAsObject(FacesContext context, UIComponent component, String valueStr) {
		Name name = createName(valueStr);
		return name.getCanonical();
	}

	public String getAsString(FacesContext context, UIComponent component, Object valueObj) {
		Name name = createName(valueObj.toString());
		return name.getCommon();
	}

}