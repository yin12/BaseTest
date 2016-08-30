package com.example.basetest.projectmanage;

import java.util.Hashtable;
import java.util.Vector;

/**
 * The class XML is one of the most simple XML documents. <h4>Usage:</h4>
 * 
 * <pre>
 * 	// create a XML object:
 * 	XML xobj = new XML();
 * 	// set an attribute:
 * 	xobj.setDocAttribute("attrName", "attrValue");
 * 	// set thr root node:
 * 	Node root = new Node(....);
 * 	xobj.setRoot(root);
 * 	//.....
 * 	// get a XML object from one source, i.e. from one XmlReader object:
 * 	xobj = ...;
 * 	// if the XML has some attributes, get an attribute value by name:
 * 	String attrValue = xobj.getDocAttribute("attrName");
 * 	// get the root node:
 * 	root = xobj.getRoot();
 * 	//.....
 * </pre>
 * <hr>
 * 
 * @author Winter Yu
 * @version 2009.05.25
 */
public class XML {
	private Hashtable _miscAttrs = new Hashtable();
	private Node _root;

	// private final Encoder _encoder = new Encoder();

	/**
	 * The default constructor.
	 */
	public XML() {
		this("1.0", "UTF-8");
	}

	/**
	 * The constructor for optional in version and encoding attributes.
	 * 
	 * @param ver
	 *            The XML version.
	 * @param encode
	 *            The XML encode.
	 */
	public XML(String ver, String encode) {
		_miscAttrs.put("version", ver);
		_miscAttrs.put("encoding", encode);
	}

	/**
	 * Set an attribute by name and value.
	 * 
	 * @param attrName
	 *            The document attribute name.
	 * @param attrValue
	 *            The attribute value.
	 */
	public void setDocAttribute(String attrName, String attrValue) {
		_miscAttrs.put(attrName, attrValue);
	}

	/**
	 * Get an attribute value by name.
	 * 
	 * @param attrName
	 *            The document attribute name.
	 * @return The attribute value. Maybe null if it has no this attribute.
	 */
	public String getDocAttribute(String attrName) {
		return (String) _miscAttrs.get(attrName);
	}

	/**
	 * Set root node.
	 * 
	 * @param root
	 *            The root Node object.
	 */
	public void setRoot(Node root) {
		_root = root;
	}

	/**
	 * Get root node.
	 * 
	 * @return The root Node object.
	 */
	public Node getRoot() {
		return _root;
	}

	/**
	 * Get all tags by name.
	 * 
	 * @param tagName
	 * @return The all tags list.
	 */
	public Vector getAllTagsByName(String tagName) {
		Vector tags = new Vector();
		if (_root != null)
			_root.getAllTagsByName(tagName, tags);
		return tags;
	}

	/**
	 * Get the string representation of this XML object.
	 */
	@Override
	public String toString() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		if (_root != null) {
			sbuf.append(_root.toString());
		} else {
			sbuf.append("[XXX No root node!]");
		}
		return sbuf.toString();
	}

	public String toStringWithoutHead() {
		StringBuffer sbuf = new StringBuffer();
		if (_root != null) {
			sbuf.append(_root.toString());
		} else {
			sbuf.append("[XXX No root node!]");
		}
		return sbuf.toString();
	}
}
