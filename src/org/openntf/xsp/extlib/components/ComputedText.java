package org.openntf.xsp.extlib.components;

import com.ibm.xsp.component.xp.XspOutputText;

public class ComputedText extends XspOutputText {

	private final String FAMILY = "org.openntf.xsp.extlib";
	private final String RENDERER_TYPE = "org.openntf.xsp.ComputedText";
	private final String COMPONENT_TYPE = "org.openntf.xsp.ComputedText";

	public ComputedText() {
		setRendererType(RENDERER_TYPE);
	}

	public String getFamily() {
		return FAMILY;
	}

}
