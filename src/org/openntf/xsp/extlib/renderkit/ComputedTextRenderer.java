package org.openntf.xsp.extlib.renderkit;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.ajax.AjaxUtil;
import com.ibm.xsp.component.FacesAttrsObject;
import com.ibm.xsp.component.xp.XspOutputText;
import com.ibm.xsp.renderkit.ContentTypeRendererUtil;
import com.ibm.xsp.renderkit.html_basic.AttrsUtil;
import com.ibm.xsp.renderkit.html_basic.OutputTextRenderer;
import com.ibm.xsp.renderkit.html_extended.RenderUtil;
import com.ibm.xsp.util.FacesUtil;
import com.ibm.xsp.util.HtmlUtil;
import com.ibm.xsp.util.JSUtil;

public class ComputedTextRenderer extends OutputTextRenderer {

	@Override
	public void encodeEnd(FacesContext paramFacesContext, UIComponent paramUIComponent) throws IOException {
		if ((paramFacesContext == null) || (paramUIComponent == null)) {
			throw new IOException();
		}
		if (!(paramUIComponent.isRendered())) {
			return;
		}

		ResponseWriter localResponseWriter = paramFacesContext.getResponseWriter();
		if (AjaxUtil.isAjaxNullResponseWriter(localResponseWriter)) {
			return;
		}

		XspOutputText localXspOutputText = null;
		if (paramUIComponent instanceof XspOutputText) {
			localXspOutputText = (XspOutputText) paramUIComponent;
		}

		boolean disableOutputTag = false;
		if (localXspOutputText != null) {
			disableOutputTag = localXspOutputText.isDisableOutputTag();
		}

		String tagName = null;
		if (localXspOutputText != null)
			tagName = localXspOutputText.getTagName();
		else {
			tagName = (String) paramUIComponent.getAttributes().get("tagName");
		}
		if (StringUtil.isEmpty(tagName)) {
			tagName = "span";
		}

		String styleClass2 = null;
		if (localXspOutputText != null)
			styleClass2 = localXspOutputText.getOuterStyleClass();
		else {
			styleClass2 = (String) paramUIComponent.getAttributes().get("outerStyleClass");
		}

		String clientId = paramUIComponent.getClientId(paramFacesContext);

		String componentValue = FacesUtil.convertValue(paramFacesContext, paramUIComponent);

		boolean preventBlankVal = false;
		if (StringUtil.isEmpty(componentValue)) {
			if (localXspOutputText != null)
				preventBlankVal = ((XspOutputText) paramUIComponent).isPreventBlank();
			else {
				preventBlankVal = RenderUtil.getBooleanValue(paramUIComponent.getAttributes().get("preventBlank"));
			}
		}

		int i = 0;
		Object localObject1;
		if (!disableOutputTag) {
			if (StringUtil.isNotEmpty(styleClass2)) {
				localResponseWriter.startElement("span", paramUIComponent);
				localResponseWriter.writeAttribute("class", styleClass2, "outerStyleClass");

				if (i == 0) {
					localResponseWriter.startElement(tagName, paramUIComponent);
					i = 1;
				}

			}

			String str5 = paramUIComponent.getId();
			if (HtmlUtil.isUserId(str5)) {
				if (i == 0) {
					localResponseWriter.startElement(tagName, paramUIComponent);
					i = 1;
				}
				localResponseWriter.writeAttribute("id", clientId, "id");
			}
			String str6;
			if (localXspOutputText != null)
				str6 = localXspOutputText.getStyle();
			else {
				str6 = (String) paramUIComponent.getAttributes().get("style");
			}
			if (str6 != null) {
				if (i == 0) {
					localResponseWriter.startElement(tagName, paramUIComponent);
					i = 1;
				}
				localResponseWriter.writeAttribute("style", str6, "style");
			}
			String str7;
			if (localXspOutputText != null)
				str7 = localXspOutputText.getStyleClass();
			else {
				str7 = (String) paramUIComponent.getAttributes().get("styleClass");
			}
			if (str7 != null) {
				if (i == 0) {
					localResponseWriter.startElement(tagName, paramUIComponent);
					i = 1;
				}
				localResponseWriter.writeAttribute("class", str7, "class");
			}
			String str8;
			if (localXspOutputText != null)
				str8 = localXspOutputText.getTitle();
			else {
				str8 = (String) paramUIComponent.getAttributes().get("title");
			}
			if (str8 != null) {
				if (i == 0) {
					localResponseWriter.startElement(tagName, paramUIComponent);
					i = 1;
				}
				localResponseWriter.writeAttribute("title", str8, "title");
			}

			String str9 = null;
			if (localXspOutputText != null)
				str9 = localXspOutputText.getDir();
			else {
				str9 = (String) paramUIComponent.getAttributes().get("dir");
			}
			if (str9 != null) {
				if (i == 0) {
					localResponseWriter.startElement(tagName, paramUIComponent);
					i = 1;
				}
				localResponseWriter.writeAttribute("dir", str9, "dir");
			}

			String str10 = null;
			if (localXspOutputText != null)
				str10 = localXspOutputText.getRole();
			else {
				str10 = (String) paramUIComponent.getAttributes().get("role");
			}
			if (str10 != null) {
				if (i == 0) {
					localResponseWriter.startElement(tagName, paramUIComponent);
					i = 1;
				}
				localResponseWriter.writeAttribute("role", str10, "role");
			}
			String str11;
			if (localXspOutputText != null)
				str11 = localXspOutputText.getFor();
			else
				str11 = (String) paramUIComponent.getAttributes().get("for");
			Object localObject2;
			if (str11 != null) {
				localObject1 = getFor(paramFacesContext, paramUIComponent, str11);
				localObject2 = null;
				if (localObject1 == null) {
					UIComponent localUIComponent = (UIComponent) FacesUtil.getNamingContainer(paramUIComponent);
					if (localUIComponent != null)
						localObject2 = localUIComponent.getClientId(paramFacesContext) + ':' + str11;
				} else {
					localObject2 = ((UIComponent) localObject1).getClientId(paramFacesContext);
				}
				if (localObject2 != null) {
					if (i == 0) {
						localResponseWriter.startElement(tagName, paramUIComponent);
						i = 1;
					}
					localResponseWriter.writeAttribute("for", localObject2, "for");
				}

			}

			if (paramUIComponent instanceof FacesAttrsObject) {
				localObject1 = (FacesAttrsObject) paramUIComponent;
				localObject2 = ((FacesAttrsObject) localObject1).getAttrs();
				int j = AttrsUtil.getFirstRenderedAttrIndex((List) localObject2);
				if (-1 != j) {
					if (i == 0) {
						localResponseWriter.startElement(tagName, paramUIComponent);
						i = 1;
					}
					AttrsUtil.encodeAttrs(paramFacesContext, localResponseWriter, (List) localObject2, j);
				}

			}
		}
		if (preventBlankVal) {
			JSUtil.writeTextBlank(localResponseWriter);
		} else if (componentValue != null) {
			if (localXspOutputText != null)
				localObject1 = localXspOutputText.getContentType();
			else {
				localObject1 = (String) paramUIComponent.getAttributes().get("contentType");
			}
			if (StringUtil.isEmpty((String) localObject1)) {
				boolean bool3;
				if (localXspOutputText != null) {
					bool3 = localXspOutputText.isEscape();
				} else {
					Boolean localBoolean = (Boolean) paramUIComponent.getAttributes().get("escape");

					bool3 = (localBoolean != null) ? localBoolean.booleanValue() : true;
				}
				localObject1 = (bool3) ? "text" : "html";
			}

			ContentTypeRendererUtil.render(paramFacesContext, paramUIComponent, localResponseWriter,
					(String) localObject1, componentValue);
		}

		if (!disableOutputTag) {
			if (i != 0) {
				localResponseWriter.endElement(tagName);
			}

			if (StringUtil.isNotEmpty(styleClass2))
				localResponseWriter.endElement("span");
		}
	}

}
