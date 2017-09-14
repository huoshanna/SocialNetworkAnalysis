package SNA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import extra.*;

//邻接矩阵带权图类
public class MatrixGraph<T> extends AbstractGraph<T> {
	protected Matrix matrix;
	// 二维矩阵数组
	// protected int adjmatrix[][];
	// 访问数组
	protected int visit[];
	database database = new database();
	private Vector rowData;

	// 构造空图，顶点数为0，边数为0，length指定顶点顺序表容量和邻接矩阵的容量
	public MatrixGraph(int length) {
		super(length);
		this.matrix = new Matrix(length);
	}

	// 构造空图，顶点数为0，边数为0
	public MatrixGraph() {
		this(100);
	}

	// 以vertices顶点集构造图，边数为0
	public MatrixGraph(T[] vertices) {
		this(vertices.length);
		for (int i = 0; i < vertices.length; i++)
			this.insertVertex(vertices[i]);
	}

	public MatrixGraph(T[] vertices, Triple[] edges) {
		this(vertices);
		for (int j = 0; j < edges.length; j++) {// 插入一条边
			this.insertEdges(edges[j]);
		}
	}

	public String toString() {
		String str = super.toString();
		int n = this.vertexCount();// 顶点数
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (this.matrix.get(i, j) == MAX_WEIGHT)
					str += "     ∞";
				else
					str += String.format("%6d", this.matrix.get(i, j));
			str += "\n";
		}
		return str;
	}

	//
	public T get(int i) {
		return this.vertextlist.get(i);
	}

	//获得顶点集
	public SeqList<T> getVertex(){
		return this.vertextlist;
	}
	// 插入<vi,vj>,权值为weight
	public void insertEdges(int i, int j, int weight) {
		if (i != j) { // 不能表示自身环
			if (weight < 0 || weight > MAX_WEIGHT) // 边的权值容错，视为无边，取值∞
				weight = MAX_WEIGHT;
			// 设置矩阵元素[i,j]为weight，若i,j越界，抛出异常
			this.matrix.set(i, j, weight);
		} else
			throw new IllegalArgumentException("不能插入自身环，i=" + i + ",j=" + j);
	}

	public void insertEdges(Triple edges) { // 插入一条边
		this.insertEdges(edges.row, edges.column, edges.value);
	}

	// 插入元素为x的顶点
	public int insertVertex(T x) {
		int i = this.vertextlist.insert(x);
		if (i >= this.matrix.getRows())
			this.matrix.setRowsColumns(i + 1, i + 1);
		for (int j = 0; j < i; j++) {
			this.matrix.set(i, j, MAX_WEIGHT);
			this.matrix.set(j, i, MAX_WEIGHT);
		}
		return i;
	}

	// 修改边的权值
	public void change(int i, int j, int weight) {
		if (weight < 0 || weight > MAX_WEIGHT) // 边的权值容错，视为无边，取值∞
			weight = MAX_WEIGHT;
		this.matrix.set(i, j, weight);
	}

	// 删除边
	public void removeEdges(int i, int j) {
		if (i != j)
			this.matrix.set(i, j, MAX_WEIGHT);
	}

	// 删除一条边，忽略权值
	public void removeEdges(Triple edge) {
		this.removeEdges(edge.row, edge.column);
	}

	// 删除顶点
	public void removeVertex(int i) {
		int n = this.vertexCount();
		if (i >= 0 && i < n) {
			this.vertextlist.remove(i); // 删除顶点顺序表第i个元素，顶点减1
			for (int j = i + 1; j < n; j++) // 第i+1~n-1元素上移
				for (int k = 0; k < n; k++)
					this.matrix.set(j - 1, k, this.matrix.get(j, k));

			for (int j = 0; j < n; j++) // 第i+1~n-1元素左移
				for (int k = 0; k < n; k++)
					this.matrix.set(j, k - 1, this.matrix.get(j, k));
			this.matrix.setRowsColumns(n - 1, n - 1);
		} else
			throw new IndexOutOfBoundsException("i=" + i);
	}

	// 计算出度
	public int[] outdegree() {
		int temp[] = new int[this.matrix.getColumns()];
		for (int i = 0; i < this.matrix.getColumns(); i++) {
			for (int j = 0; j < this.matrix.getRows(); j++) {
				if (this.matrix.get(i, j) != MAX_WEIGHT)
					temp[i] = temp[i] + this.matrix.get(i, j);
			}
		}
		return temp;
	}

	// 计算入度
	public int[] indegree() {
		int temp[] = new int[this.matrix.getColumns()];
		for (int i = 0; i < this.matrix.getColumns(); i++) {
			for (int j = 0; j < this.matrix.getRows(); j++) {
				if (this.matrix.get(j, i) != MAX_WEIGHT)
					temp[i] = temp[i] + this.matrix.get(j, i);
			}
		}
		return temp;
	}

	/*
	 * 核心人物 入度和出度总和最大的人
	 */
	public String mainperson() {
		// 出入度总和数组
		int temp[] = new int[this.vertexCount()];
		// 最大值初始化
		int max = -1;
		int line = -1;
		for (int i = 0; i < temp.length; i++) {
			temp[i] = this.outdegree()[i] + this.indegree()[i];
		}
		max = temp[0];
		for (int i = 1; i < temp.length; i++) {
			if (temp[i] > max) {
				max = temp[i];
				line = i;
			}
		}
		return (String) this.get(line);
	}

	/*
	 * 活跃人物 出度总和最大的人
	 */
	public String activeperson() {
		int max = -1;
		int line = -1;
		for (int i = 0; i < this.vertexCount(); i++) {
			if (this.outdegree()[i] > max) {
				max = this.outdegree()[i];
				line = i;
			}
		}
		return (String) this.get(line);
	}

	/*
	 * 边缘人物 出入度总和最小的人
	 */
	public String marginperson() {
		int temp[] = new int[this.vertexCount()];
		int min = 9999;
		int line = -1;
		for (int i = 0; i < temp.length; i++)
			temp[i] = this.outdegree()[i] + this.indegree()[i];
		for (int i = 0; i <temp.length; i++) {
			if (temp[i] < min) {
				min = temp[i];
				line = i;
			}
		}
		return (String) this.get(line);
	}

	// 判断与两个人之间是否有联系
	public boolean isConnect(int temp[][], int i, int j, int p) {
		int t = 0;
		for (int m = 0; m < p; m++) {
			if (this.matrix.get(temp[i][m] - 1, j) != 0 && this.matrix.get(temp[i][m] - 1, j) != MAX_WEIGHT
					&& this.matrix.get(j, temp[i][m] - 1) != 0 && this.matrix.get(j, temp[i][m] - 1) != MAX_WEIGHT)
				t++;
		}
		if (t == p)
			return true;
		else
			return false;
	}

	// 团体
	public int[][] team() {
		visit = new int[this.vertexCount()];
		int temp[][] = new int[this.vertexCount()][this.vertexCount()];
		for (int i = 0; i < this.vertexCount(); i++) {
			temp[i][0] = i + 1;
			visit[i]++;
			int p = 1;
			for (int j = 0; j < this.vertexCount(); j++) {
				if (this.isConnect(temp, i, j, p)) {
					temp[i][p] = j + 1;
					visit[j]++;
					p++;
				}
			}
		}
		return temp;
	}

	// 小团体
	public int[] group() {
		int temp[][] = this.team();
		int count = 0;
		for (int i = 0; i < temp.length; i++) {
			if (!(temp[i][0] == 0 || temp[i][1] == 0))
				count++;
		}
		int t = count;
		count = 0;
		int mm = 0;
		int[] ids = new int[temp.length * temp.length];
		int idc = 0;
		for (int i = 0; i < temp.length && count < t; i++) {
			if (!(temp[i][0] == 0 || temp[i][1] == 0)) {
				for (int j = 0; temp[i][j] != 0; j++) {
					String a= (String) this.get(temp[i][j] - 1);
					mm = j;
					double id = Double.parseDouble(a);
					ids[idc] = (int) id;
					idc++;
				}
				ids[idc] = -1;
				idc++;
				count++;
			}
		}
		return ids;
	}

	public int[] Connecter() {// 桥接人
		int count = 0;
		this.team();
		for (int i = 0; i < this.visit.length; i++) {
			if (visit[i] >= 2) {
				count++;				
			}
		}
		int ids[] = new int[count];
		int idc = 0;
		count = 0;
		for (int i = 0; i < this.visit.length / 2; i++) {
			if (visit[i] >= 2) {
				String a = (String) this.get(i);
				double id = Double.parseDouble(a);
				ids[idc] = (int) id;
				idc++;
				count++;
			}
		}
		return ids;
	}

	// 判断是否存在
	public boolean isExist(String temp[], String str, int t) {
		for (int i = 0; i < t; i++) {
			if (temp[i].equals(str))
				return true;
		}
		return false;
	}

	// 圈子
	public String[] circle(String[] vertices, String str) {
		String temp[] = new String[vertices.length];
		int location = -1;
		int count = 0;
		for (int i = 0; i < vertexCount(); i++) {
			if (vertices[i].equals(str)) {
				location = i;
				break;
			}
		}
		for (int i = 0; i < vertexCount(); i++)
			if (this.matrix.get(location, i) != 0 && this.matrix.get(location, i) != MAX_WEIGHT) {
				temp[count] = (String) this.get(i);
				count++;
			}
		for (int i = 0; i < vertexCount(); i++)
			if (this.matrix.get(i, location) != 0 && this.matrix.get(i, location) != MAX_WEIGHT) {
				if (!this.isExist(temp, (String) this.get(i), count)) {
					temp[count] = (String) this.get(i);
					count++;
				}
			}
		return temp;
	}

	protected int next(int i, int j) {
		int n = this.vertexCount();
		if (i >= 0 && i < n && j >= -1 && j < n && i != j)
			for (int k = j + 1; k < n; k++)
				if (this.matrix.get(i, k) > 0 && this.matrix.get(i, k) < MAX_WEIGHT)
					return k;
		return -1;
	}
}
