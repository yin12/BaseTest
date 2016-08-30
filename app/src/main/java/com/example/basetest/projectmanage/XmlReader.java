package com.example.basetest.projectmanage;

import android.R.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

/**
 * The class XmlReader is one of the most simple XML-Pull-Parsers. It has the
 * following features:
 * <ul>
 * <li>It uses the buffered read. The read buffer size is 4096.
 * <li>It always uses a last tag ({@link xml.Node}) as parsed result cache.
 * <li>It may keep the parsed result, an object of {@link xml.XML}. Please NOTE:
 * in "do not keep the parsed result" case, you cannot get the whole root tag at
 * all.
 * </ul>
 * <h4>Usage:</h4>
 * <p/>
 * <pre>
 * 	// create an object of XmlReader from a reader (do not keep the parsed result):
 * 	XmlReader xRdr = new XmlReader(reader, false);
 * 	// read to the end of a tag that tag-name is &quot;TagName1&quot;:
 * 	int rEvt = XmlReader.END_DOCUMENT;
 * 	do {
 * 		rEvt = xRdr.next();
 *    } while(rEvt != XmlReader.END_DOCUMENT &amp;&amp; !&quot;TagName1&quot;.equals(xRdr.getName() &amp;&amp; rEvt
 *    != XmlReader.END_TAG);
 * 	// get the last parsed tag object:
 * 	Node lastTag = sRdr.lastTag();
 * 	// if the tag has a text child-node, read the child-text:
 * 	String childText = lastTag.getChildText();
 * 	// read more information from this Node object through the Node API:
 * 	//.....
 * 	// If you hope to get the whole parsed result (XML object):
 * 	xRdr = new XmlReader(reader, true);
 * 	do {
 * 		rEvt = xRdr.next();
 *    } while(rEvt != XmlReader.END_DOCUMENT);
 * 	XML myXML = xRdr.getXML();
 * 	// read more information from this XML object through the XML API:
 * 	//.....
 * </pre>
 * <hr>
 *
 * @author winter yu
 * @version 2009.05.28
 */
public class XmlReader {
    /**
     * The last parsing event type constants:
     */
    public static int START_DOCUMENT = 0;
    public static int END_DOCUMENT = 1;
    public static int START_TAG = 2;
    public static int END_TAG = 3;
    public static int TEXT = 4;

    // private InputStream is;
    private InputStreamReader _reader;
    private InputStream inputSteam;
    // read buffer:
    private char[] _buf = new char[2048];
    private int _blen = 0, _bi = 0;
    private int _evtType = START_DOCUMENT;
    private StringBuffer _readedChars = new StringBuffer();
    // private Decoder _decoder = new Decoder();
    private XML _mxo = new XML();
    private Stack _tagStack;
    private Node _lastTag;
    // for cache XML:
    private boolean _keepXML;

    boolean log = true;

    /**
     * The constructor.
     *
     * @param reader  The XML source reader.
     * @param keepXML Keep the parsed result if keepXML is true, don't keep result
     *                otherwise.
     */
    public XmlReader(InputStreamReader reader, boolean keepXML) {
        _reader = reader;
        _keepXML = keepXML;
    }

    /**
     * The constructor. This is for unit-test only.
     *
     * @param xml     The XML source string.
     * @param keepXML Keep the parsed result if keepXML is true, don't keep result
     *                otherwise.
     */
    public XmlReader(String xml, boolean keepXML)
            throws UnsupportedEncodingException {
        _reader = new InputStreamReader(
                new ByteArrayInputStream(xml.getBytes()), "UTF-8");
        _keepXML = keepXML;
    }

    public XmlReader(byte[] buf) throws UnsupportedEncodingException {
        _reader = new InputStreamReader(new ByteArrayInputStream(buf), "UTF-8");
        _keepXML = false;
    }

    // [API functions]:

    /**
     * Get the parsed result (object based XML).
     *
     * @return the XML object.
     */
    public XML getXML() {
        return _mxo;
    }

    /**
     * The last parsed event type.
     *
     * @return The event type.
     */
    public int eventType() {
        return this._evtType;
    }

    /**
     * The last parsed tag (Node object).
     *
     * @return The last tag (Node object).
     */
    public Node lastTag() {
        return _lastTag;
    }

    /**
     * Get the last parsed tag name.
     *
     * @return The last tag name.
     */
    public String getName() {
        if (_lastTag == null)
            return null;
        return _lastTag.getName();
    }

    /**
     * Get an attribute by name in the last parsed tag.
     *
     * @param name
     * @return The attribute value (String).
     */
    public String getAttribute(String name) {
        if (_lastTag == null)
            return null;
        return _lastTag.getAttribute(name);
    }

    /**
     * Get text in the last parsed tag.
     *
     * @return The text value (String).
     */
    public String getText() {
        if (_lastTag == null)
            return null;
        return _lastTag.getChildText();
    }

    /**
     * Reset the XmlReader object. Please NOTE: this method does not reset the
     * current reader pointer.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        reset();
        _reader.close();
    }

    public void reset() {
        _bi = 0;
        _blen = 0;
        _mxo = new XML();
        _tagStack = null;
        _lastTag = null;
    }

    /**
     * Read next content and try to parse a tag part.
     *
     * @return The last parsed event value.
     * @throws Exception
     */
    public int next() throws Exception {
        if (!ignoreSpaceAndInvisibleChars()) {
            return this._evtType;
        }
        char ch = (char) readChar(true);
        if (ch == '<' || ch == '/') // tag (node-name or comment or cdata):
        {
            if (ch == '<')
                ch = (char) readChar(false);
            if (ch == '/') // node tag end:
            {
                if (!endTagNode())
                    return this._evtType;
            } else if (ch == '?') // xml heading & misc:
            { // ignore
                if (readToChar(new char[]{'>'}, true) == null) {
                    return this._evtType;
                }
                this.next();
            } else if (ch == '!') // DOCTYPE or node CDATA:
            { // ignore
                if (readToChar(new char[]{'>'}, true) == null) {
                    return this._evtType;
                }
                this.next();
            } else // new tag (node):
            {
                if (!newTagNode())
                    return this._evtType;
                if (readChar(false) == '>')
                    _bi++;
            }
        } else if (ch == '>') // tag name close:
        {
            System.err.println("XXXXXXXXXXX");
            // this._evtType = END_TAG;
            // this.inside_tag = false;
        } else // node text:
        {
            // dumpMsg("read text...");
            this._evtType = TEXT;
            String text = XMLDecoder.exec(ch
                    + this.readToChar(new char[]{'<'}, false));
            Node textNode = new Node(Node.NODE_TEXT, null, text);
            _lastTag.addChild(textNode);
        }
        // dumpMsg("END of next.");
        return this._evtType;
    }

    // [Implementation of interface Runnable]:

    // Check read buffer and return the current character.
    private int readChar(boolean movePointer) throws Exception {
        if (_bi >= _blen) {
            _blen = _reader.read(_buf, 0, 2048);
            if (log) {
                StringBuffer sbBuffer = new StringBuffer();
                for (int i = 0; i < 300; i++) {
                    sbBuffer.append(_buf[i]);
                }
            }
            log = false;

            _bi = 0;
            if (_blen < 0)
                return -1;
        }
        char ret = _buf[_bi];
        if (movePointer)
            _bi++;
        return ret;
    }

    private boolean ignoreSpaceAndInvisibleChars() throws Exception {
        // dumpMsg("ignoreSpaceAndInvisibleChars...");
        while (readChar(false) != -1) {
            if (_buf[_bi] > ' ') {
                return true;
            }
            _bi++;
        }
        this._evtType = END_DOCUMENT;
        // dumpMsg("### END_DOCUMENT in ignoreSpaceAndInvisibleChars()!");
        return false;
    }

    private String readToChar(char[] arrChar, boolean includeToChar)
            throws Exception {
        if (_readedChars.length() > 0)
            _readedChars.delete(0, _readedChars.length());
        while (readChar(false) != -1) {
            int length = _blen - _bi;
            // String preRead = new String(_buf, _bi,length);
            char[] prRead = new char[length];
            System.arraycopy(_buf, _bi, prRead, 0, length);
            int ci = -1;
            for (int i = 0; i < arrChar.length; i++) {
                char ch = arrChar[i];
                // int cia = preRead.indexOf(ch);
                int cia = -1;
                // here can compare with the souce code of String,
                // it will remove some operation.
                for (int j = 0; j < prRead.length; j++) {
                    if (prRead[j] != ch) {
                        continue;
                    } else {
                        cia = j;
                    }
                    if (cia >= 0) {
                        if (ci < 0)
                            ci = cia;
                        else
                            ci = Math.min(cia, ci);
                    }
                }
            }
            if (ci < 0) {
                // _readedChars.append(preRead);
                _readedChars.append(prRead);
                _bi = _blen;
            } else {
                if (includeToChar)
                    ci++;
                _readedChars.append(prRead, 0, ci);
                // _readedChars.append(preRead.substring(0, ci));
                _bi += ci;
                return _readedChars.toString();
            }
            // Thread.sleep(1);
        }
        this._evtType = END_DOCUMENT;
        // dumpMsg("### END_DOCUMENT in readToChar()!");
        return null;
    }

    private boolean newTagNode() throws Exception {
        this._evtType = START_TAG;
        String tagName = this.readToChar(new char[]{' ', '/', '>'}, false);
        // dumpMsg("tagName = " + tagName);
        if (tagName == null) {
            // dumpMsg("XXX Fatal error in newTagNode()!");
            return false;
        }
        _lastTag = new Node(Node.NODE_TAG, tagName, null);
        if (_tagStack == null) {
            _tagStack = new Stack();
            _mxo.setRoot(_lastTag);
        }
        if (!readNodeAttributes()) {
            // dumpMsg("XXX Fatal error in readNodeAttributes()!");
            return false;
        }

        Node parTag = (Node) (_tagStack.empty() ? null : _tagStack.peek());
        if (parTag != null) {
            parTag.addChild(_lastTag);
        }
        // dumpMsg("tag stack <= " + _lastTag.getName());
        _tagStack.push(_lastTag);
        return true;
    }

    // NOTE: you must ensure the current this.c is ' ' character, otherwise do
    // nothing !
    private boolean readNodeAttributes() throws Exception {
        // read attributes:
        String attribute = "";
        String value = "";
        while (readChar(false) == ' ') // check ' ' char
        {
            // skip ' ' char
            if (!ignoreSpaceAndInvisibleChars())
                return false;
            attribute = this.readToChar(new char[]{'=', '/', '>'}, false);
            if (attribute == null)
                return false;
            if (readChar(false) != '=')
                break;
            // skip '=' char:
            _bi++;
            int quote = readChar(true);
            if (quote == -1) {
                return false;
            }
            boolean noQuote = ('"' != (char) quote && '\'' != (char) quote);
            char[] endChars = new char[]{(char) quote};
            if (noQuote) {
                _bi--;
                endChars = new char[]{' ', '/', '>'};
            }
            value = this.readToChar(endChars, false);
            if (value == null) {
                return false;
            }
            _lastTag.setAttribute(attribute, XMLDecoder.exec(value));
            // dumpMsg("attribute name: " + attribute + ", value: " + value);
            if (!noQuote) {
                // skip '"' or '\'':
                _bi++;
            }
        }
        return true;
    }

    private boolean endTagNode() throws Exception {
        this._evtType = END_TAG;
        if (readToChar(new char[]{'>'}, true) == null) {
            return false;
        }
        if (_tagStack != null && !_tagStack.empty()) {
            _lastTag = (Node) _tagStack.pop();
            // dumpMsg("tag stack => " + _lastTag.getName());
            Node parNode = _lastTag.getParent();
            if (!_keepXML && parNode != null && parNode.getParent() == null) {
                parNode.removeChild(_lastTag);
            }
            if (_tagStack.isEmpty()) {
                _evtType = END_DOCUMENT;
                return false;
            }
        }
        return true;
    }

    public void setReader(InputStreamReader reader) {
        this._reader = reader;
    }
}
