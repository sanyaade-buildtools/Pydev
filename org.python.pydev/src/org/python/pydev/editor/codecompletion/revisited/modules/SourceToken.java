/*
 * Created on Nov 12, 2004
 *
 * @author Fabio Zadrozny
 */
package org.python.pydev.editor.codecompletion.revisited.modules;

import org.python.parser.SimpleNode;
import org.python.parser.ast.Attribute;
import org.python.parser.ast.ClassDef;
import org.python.parser.ast.FunctionDef;
import org.python.parser.ast.Import;
import org.python.parser.ast.ImportFrom;
import org.python.parser.ast.Name;
import org.python.parser.ast.keywordType;
import org.python.pydev.editor.codecompletion.PyCodeCompletion;
import org.python.pydev.editor.codecompletion.revisited.AbstractToken;

/**
 * @author Fabio Zadrozny
 */
public class SourceToken extends AbstractToken{

    private SimpleNode ast;

    /**
     * @param node
     */
    public SourceToken(SimpleNode node, String rep, String args, String doc, String parentPackage) {
        super(rep, doc, args, parentPackage, getType(node));
        this.ast = node;
    }

    /**
     * @param node
     */
    public SourceToken(SimpleNode node, String rep, String args, String doc, String parentPackage, int type) {
        super(rep, doc, args, parentPackage, type);
        this.ast = node;
    }

    /**
     * @param node
     */
    public SourceToken(SimpleNode node, String rep, String doc, String args, String parentPackage, String originalRep) {
        super(rep, doc, args, parentPackage, getType(node), originalRep);
        this.ast = node;
    }
    
    /**
     * 
     * @return the completion type depending on the syntax tree.
     */
    public static int getType(SimpleNode ast){
        if (ast instanceof ClassDef){
            return PyCodeCompletion.TYPE_CLASS; 
        
        }else if (ast instanceof FunctionDef){
            return PyCodeCompletion.TYPE_FUNCTION; 
        
        }else if (ast instanceof Name){
            return PyCodeCompletion.TYPE_ATTR;
            
        }else if (ast instanceof Import || ast instanceof ImportFrom){
            return PyCodeCompletion.TYPE_IMPORT; 

        }else if (ast instanceof keywordType){
            return PyCodeCompletion.TYPE_ATTR; 
        
        }else if (ast instanceof Attribute){
            return PyCodeCompletion.TYPE_ATTR; 
        }
        
        return  PyCodeCompletion.TYPE_UNKNOWN;
    }

    
    public SimpleNode getAst(){
        return ast;
    }
    
}
