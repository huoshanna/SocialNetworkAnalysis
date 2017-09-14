package SNA;
import extra.*;
public abstract class AbstractGraph<T> {
	protected static final int MAX_WEIGHT = 0x0000ffff; // 最大权值表示无穷大
	protected SeqList<T> vertextlist; // 顶点顺序表
	public String[] vertices;
	// 构造空图，顶点数为0，length指定顶点顺序表容量
	public AbstractGraph(int length) {
		this.vertextlist = new SeqList<T>(length);
	}
	// 构造空图，顶点数为0
	public AbstractGraph() {
		this(10);
	}
	// 返回顶点数
	public int vertexCount() {
		return this.vertextlist.size();
	}
	public String toString() {
		return "顶点集合：" + this.vertextlist.toString() + "\n";
	}
	// 返回顶点元素vi
	public T getVertex(int i) {
		return this.vertextlist.get(i);
	}
	// 设置顶点元素vi元素为x
	public void setVertex(int i, T x) {
		this.vertextlist.set(i, x);
	}
}
