/* Generated by JTB 1.4.7 */
package flame.syntaxtree;

import flame.visitor.IRetArguVisitor;
import flame.visitor.IRetVisitor;
import flame.visitor.IVoidArguVisitor;
import flame.visitor.IVoidVisitor;

import java.util.*;

public class NodeSequence implements INodeList {

  public ArrayList<INode> nodes;

  private static final long serialVersionUID = 147L;

  public NodeSequence() {
    nodes = new ArrayList<INode>();
  }

  public NodeSequence(final int sz) {
    nodes = new ArrayList<INode>(sz);
  }

  public NodeSequence(final INode firstNode) {
    nodes = new ArrayList<INode>();
    addNode(firstNode);
  }

  public NodeSequence(final int sz, final INode firstNode) {
    nodes = new ArrayList<INode>(sz);
    addNode(firstNode);
  }

  public void addNode(final INode n) {
    nodes.add(n);
  }

  public INode elementAt(final int i) {
    return nodes.get(i); }

  public Iterator<INode> elements() {
    return nodes.iterator(); }

  public int size() {
    return nodes.size(); }

  public <R, A> R accept(final IRetArguVisitor<R, A> vis, final A argu) {
    return vis.visit(this, argu);
  }

  public <R> R accept(final IRetVisitor<R> vis) {
    return vis.visit(this);
  }

  public <A> void accept(final IVoidArguVisitor<A> vis, final A argu) {
    vis.visit(this, argu);
  }

  public void accept(final IVoidVisitor vis) {
    vis.visit(this);
  }

}