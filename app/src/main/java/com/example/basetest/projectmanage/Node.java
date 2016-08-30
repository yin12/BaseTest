package com.example.basetest.projectmanage;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * The class Node is a XML Node definition. <h4>Usage:</h4>
 * <p/>
 * <pre>
 * 	// create a TAG node with name "tagName1"::
 * 	Node tagNode = new Node(Node.NODE_TAG, "tagName1", null);
 * 	// create a TEXT node with text "TextValue":
 * 	Node textNode = new Node(Node.NODE_TEXT, null, "TextValue");
 * 	// create a CDATA node with text "CDataValue":
 * 	Node cdataNode = new Node(Node.NODE_CDATA, null, "CDataValue");
 * 	// set an attribute in a TAG node:
 * 	tagNode.setAttribute("attrName", "attrValue");
 * 	// set its child TEXT value:
 * 	tagNode.setChildText("childTextValue", false);
 * 	// create a child node and add it to parent node:
 * 	Node child = new Node(....);
 * 	oneNode.addChild(child);
 * 	// Another short-cut method for child TAG node, which may have a child TEXT node:
 * 	Node tagChild = tagNode.addChildTag("childTagName", "grandChildText");
 * 	//.....
 * 	// get a XML Node object from any where:
 * 	Node node = ...;
 * 	// if the node has some attributes, get an attribute value by name:
 * 	String attrValue = node.getAttribute("attrName");
 * 	// get all child node list:
 * 	Vector childNodes = node.getChildren(null);
 * 	// get all child tag nodes with name "tagName1":
 * 	Vector namedNodes = node.getChildren("tagName1");
 * 	// if the node has a text sub-node, read the sub-text:
 * 	String subText = node.getChildText();
 * 	// read more information from this Node object through the Node API:
 * 	//.....
 * </pre>
 * <hr>
 *
 * @author yiming
 * @version 2011.12.05
 */
public class Node {

    /**
     * Serialize all attributes into an output StringBuffer. This is one tool
     * method for internal usage.
     *
     * @param attrs   The all attributes will be output.
     * @param sbufTag The output StringBuffer object.
     * @param encoder The special character encoder.
     */
    protected static void serializeAttributes(Hashtable attrs,
                                              StringBuffer sbufTag) {
        boolean bEndSpace = false;
        Enumeration akeys = attrs.keys();
        while (akeys.hasMoreElements()) {
            String akey = (String) akeys.nextElement();
            String attrValue = XMLEncoder.exec((String) attrs.get(akey));
            sbufTag.append(' ');
            sbufTag.append(akey);
            sbufTag.append("=\"");
            sbufTag.append(attrValue);
            sbufTag.append('"');
            bEndSpace = true;
        }
        if (bEndSpace) {
            sbufTag.append(' ');
        }
    }

    /**
     * The node type constants:
     */
    public static final int NODE_UNKNOWN = 0;
    public static final int NODE_TAG = 1;
    public static final int NODE_TEXT = 2;
    public static final int NODE_CDATA = 3;
    private int _type = NODE_UNKNOWN;
    private Node _parent;
    private String _name;
    private String _value;
    private Hashtable _attrs = new Hashtable();
    private Vector _children = new Vector();

    // private final XMLEncoder _encoder = new XMLEncoder();

    /**
     * The default constructor.
     */
    public Node(int type, String name, String value) {
        this.initNode(type, name, value);
    }

    /**
     * Initializing the Node instance.
     *
     * @param type  Node type.
     * @param name  Node name. Only tag node has name now.
     * @param value Node value. If TAG it will be null.
     */
    private void initNode(int type, String name, String value) {
        _type = type;
        _name = name;
        _value = value;
    }

    /**
     * Set parent node. This method should be called in Node API internally.
     *
     * @param parent The parent Node instance.
     */
    protected void setParent(Node parent) {
        _parent = parent;
    }

    /**
     * Get parent node.
     *
     * @return The parent Node instance.
     */
    public Node getParent() {
        return _parent;
    }

    /**
     * Get node type.
     *
     * @return The node type.
     */
    public int getType() {
        return _type;
    }

    /**
     * Get the node name.
     *
     * @return If a Tag, the node name, otherwise null.
     */
    public String getName() {
        return _name;
    }

    /**
     * Get the node value.
     *
     * @return If a TEXT or CDATA, the node value, otherwise null.
     */
    public String getValue() {
        return _value;
    }

    /**
     * Get an attribute value by attribute name.
     *
     * @param attrName The attribute name.
     * @return The attribute value. Maybe null if no the attribute name.
     */
    public String getAttribute(String attrName) {
        return (String) _attrs.get(attrName);
    }

    /**
     * Set an attribute by name and value.
     *
     * @param attrName  The attribute name.
     * @param attrValue The attribute value.
     */
    public void setAttribute(String attrName, String attrValue) {
        _attrs.put(attrName, attrValue);
    }

    /**
     * Get child nodes by node name. It node name is null, get all child nodes.
     *
     * @param name The node name.
     * @return The child node list.
     */
    public Vector getChildren(String name) {
        if (name == null) {
            return _children;
        }
        Vector retNodes = new Vector();
        for (int i = 0; i < _children.size(); i++) {
            Node subNode = (Node) _children.elementAt(i);
            if (subNode.getName() != null && subNode.getName().equals(name)) {
                retNodes.addElement(subNode);
            }
        }
        return retNodes;
    }

    /**
     * Add a child node.
     *
     * @param childNode
     */
    public void addChild(Node childNode) {
        _children.addElement(childNode);
        childNode.setParent(this);
    }

    /**
     * Get the first child node with tag-name.
     *
     * @param tagName
     * @return
     */
    public Node getFirstChild(String tagName) {
        Vector vec = this.getChildren(tagName);
        if (vec.size() == 0) {
            return null;
        }
        return (Node) vec.elementAt(0);
    }

    /**
     * Remove a child node.
     *
     * @param childNode
     */
    public void removeChild(Node childNode) {
        _children.removeElement(childNode);
        childNode.setParent(null);
    }

    /**
     * Set child text node. This tag node should have only this text child node.
     *
     * @param childText The text value.
     * @param bCDATA    This is a CDATA or not.
     */
    public void setChildText(String childText, boolean bCDATA) {
        Node textNode = getChildTextNode();
        int type = Node.NODE_TEXT;
        if (bCDATA) {
            type = Node.NODE_CDATA;
        }
        if (textNode != null) {
            textNode.initNode(type, null, childText);
        } else {
            textNode = new Node(type, null, childText);
            _attrs.clear();
            _children.removeAllElements();
            this.addChild(textNode);
        }
    }

    public Node getChildTextNode() {
        if (_children.size() > 0) {
            Node subNode = (Node) _children.elementAt(0);
            if (subNode.getType() == NODE_TEXT
                    || subNode.getType() == NODE_CDATA) {
                return subNode;
            }
        }
        return null;
    }

    /**
     * Get child text.
     *
     * @return The child text. Maybe null if it has no text child node.
     */
    public String getChildText() {
        Node subNode = getChildTextNode();
        if (subNode != null) {
            return subNode.getValue();
        }
        return null;
    }

    /**
     * Get grand-child node text.
     *
     * @param childName
     * @return The grand-child text of the node.
     */
    public String getGrandChildText(String childName) {
        Vector children = this.getChildren(childName);
        if (children.size() == 0) {
            return null;
        }
        Node childTag = (Node) children.elementAt(0);
        return childTag.getChildText();
    }

    /**
     * Add a child TAG node by name. And if the grandChildText is not null, then
     * the child TAG node will add it as TEXT child node.
     *
     * @param childTagName   The child tag name.
     * @param grandChildText If not null, the new added tag node will add this
     *                       grandChildText as child TEXT node.
     * @return The new child TAG node.
     */
    public Node addChildTag(String childTagName, String grandChildText) {
        Node subTag = new Node(Node.NODE_TAG, childTagName, null);
        this.addChild(subTag);
        if (grandChildText != null) {
            subTag.setChildText(grandChildText, false);
        }
        return subTag;
    }

    /**
     * Get all tags by name.
     *
     * @param tagName
     * @param tags
     */
    public void getAllTagsByName(String tagName, Vector tags) {
        if (_type != NODE_TAG) {
            return;
        }
        if (this._name.equals(tagName)) {
            tags.addElement(this);
        }
        for (int i = 0; i < _children.size(); i++) {
            Node child = (Node) _children.elementAt(i);
            child.getAllTagsByName(tagName, tags);
        }
    }

    /**
     * Get the string representation of this node. It is primarily used in the
     * string representation of xml.XML.
     */
    @Override
    public String toString() {
        String szRet = "";
        switch (_type) {
            case NODE_TEXT:
                if (_value != null) {
                    szRet = XMLEncoder.exec(_value);
                }
                break;
            case NODE_CDATA:
                szRet = "<![CDATA[" + _value + "]]>";
                break;
            case NODE_TAG:
                szRet = getTagString();
                break;
            default:
                szRet = "{XXX Invalid type in node " + _name + "}";
        }
        return szRet;
    }

    private String getTagString() {
        StringBuffer sbufTag = new StringBuffer();
        // begin tag:
        sbufTag.append('<');
        sbufTag.append(_name);
        Node.serializeAttributes(_attrs, sbufTag);
        if (_children.size() > 0) {
            sbufTag.append('>');
            // descendant:
            for (int si = 0; si < _children.size(); si++) {
                Node child = (Node) _children.elementAt(si);
                sbufTag.append(child.toString());
            }
            // end tag:
            sbufTag.append("</");
            sbufTag.append(_name);
            sbufTag.append('>');
        } else {
            sbufTag.append("/>");
        }
        return sbufTag.toString();
    }
}
