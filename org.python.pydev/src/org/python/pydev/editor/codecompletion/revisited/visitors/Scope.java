/*
 * Created on Jan 20, 2005
 *
 * @author Fabio Zadrozny
 */
package org.python.pydev.editor.codecompletion.revisited.visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.python.parser.SimpleNode;
import org.python.parser.ast.FunctionDef;
import org.python.pydev.editor.codecompletion.PyCodeCompletion;
import org.python.pydev.editor.codecompletion.revisited.IToken;
import org.python.pydev.editor.codecompletion.revisited.modules.SourceToken;

/**
 * @author Fabio Zadrozny
 */
public class Scope {

    public Stack scope = new Stack();
    
    public Scope(Stack scope){
        this.scope.addAll(scope);
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Scope)) {
            return false;
        }
        
        Scope s = (Scope) obj;
        
        if(this.scope.size() != s.scope.size()){
            return false;
        }
        
        return checkIfScopesMatch(s);
    }
    
    /**
     * Checks if this scope is an outer scope of the scope passed as a param (s).
     * Or if it is the same scope. 
     * 
     * @param s
     * @return
     */
    public boolean isOuterOrSameScope(Scope s){
        if(this.scope.size() > s.scope.size()){
            return false;
        }
 
        return checkIfScopesMatch(s);
    }

    /**
     * @param s
     * @return
     */
    private boolean checkIfScopesMatch(Scope s) {
        Iterator otIt = s.scope.iterator();
        
        for (Iterator iter = this.scope.iterator(); iter.hasNext();) {
            SimpleNode element = (SimpleNode) iter.next();
            SimpleNode otElement = (SimpleNode) otIt.next();
            
            if(element.beginColumn != otElement.beginColumn)
                return false;
            
            if(element.beginLine != otElement.beginLine)
                return false;
            
            if(! element.getClass().equals(otElement.getClass()))
                return false;
            
            if(! AbstractVisitor.getFullRepresentationString(element).equals( AbstractVisitor.getFullRepresentationString(otElement)))
                return false;
            
        }
        return true;
    }
    
    public IToken[] getLocalTokens(int line, int col){
        List comps = new ArrayList();
        
        for (Iterator iter = this.scope.iterator(); iter.hasNext();) {
            SimpleNode element = (SimpleNode) iter.next();
            
            if (element instanceof FunctionDef) {
                FunctionDef f = (FunctionDef) element;
                for (int i = 0; i < f.args.args.length; i++) {
                    String s = AbstractVisitor.getRepresentationString(f.args.args[i]);
                    comps.add(new SourceToken(f.args.args[i], s, "", "", "", PyCodeCompletion.TYPE_PARAM));
                }
            }
        }
        
        
        return (SourceToken[]) comps.toArray(new SourceToken[0]);
    }
}












