/* Generated by: FreeCC 0.9.3. Do not edit. JspExpressionInAttribute.java */
package org.owasp.orizon.mirage.jsp.parser;

public class JspExpressionInAttribute extends BaseNode {
    private String image;
    public String toString() {
        return super.toString()+" - "+getImage();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image=image;
    }

    public JspExpressionInAttribute(int id) {
        super(id);
    }

    public JspExpressionInAttribute() {
        super(JspConstants.JSPEXPRESSIONINATTRIBUTE);
    }

}