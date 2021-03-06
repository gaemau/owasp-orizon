/**
 * This file is part of Owasp Orizon.
 * Owasp Orizon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Owasp Orizon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.owasp.orizon.mirage.java;

import org.apache.log4j.Logger;
import org.owasp.orizon.exceptions.OrizonXmlException;
import org.owasp.orizon.misc.AttributeList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Filter {
	private String name;
	private String className;
	private String mapping;
	private AttributeList params;
	private static Logger log = Logger.getLogger(Filter.class);
	
	public Filter(Node root) throws OrizonXmlException {
		if (! "filter".equalsIgnoreCase(root.getNodeName()))
			throw new OrizonXmlException("try to create a Filter object using a non \"filter\" xml node");
		
		params= new AttributeList();
		NodeList list = root.getChildNodes();
		for (int i=0; i<list.getLength(); i++) {
			if ("filter-name".equalsIgnoreCase(list.item(i).getNodeName()))
				name = list.item(i).getFirstChild().getNodeValue();
			if ("filter-class".equalsIgnoreCase(list.item(i).getNodeName()))
				className = list.item(i).getFirstChild().getNodeValue();
			
			if ("init-param".equalsIgnoreCase(list.item(i).getNodeName())) {
				Node paramRoot = list.item(i);
				NodeList paramList = paramRoot.getChildNodes();
				String pname=null, pvalue=null;
				for (int j=0; j<paramList.getLength(); j++) {
					Node nn = paramList.item(j);
					if ("param-name".equalsIgnoreCase(nn.getNodeName()))
						pname = nn.getFirstChild().getNodeValue();
					if ("param-value".equalsIgnoreCase(nn.getNodeName()))
						pvalue = nn.getFirstChild().getNodeValue();
				}
				if (pname != null )
					params.add(pname, pvalue);
			}
		}
	}
	public void addMapping(Node root) throws OrizonXmlException {
		boolean tome = false;
		if (! "filter-mapping".equalsIgnoreCase(root.getNodeName()))
			throw new OrizonXmlException("invalid servlet mapping xml tag");
		
		NodeList paramList = root.getChildNodes();
		
		for (int j=0; j<paramList.getLength(); j++) {
			Node nn = paramList.item(j);
			if ("filter-name".equalsIgnoreCase(nn.getNodeName()))
				if (nn.getFirstChild().getNodeValue().equalsIgnoreCase(name))
					tome=true;
			
			if ("url-pattern".equalsIgnoreCase(nn.getNodeName()) && tome) {
				mapping = nn.getFirstChild().getNodeValue();
			}
		}
	}
}
