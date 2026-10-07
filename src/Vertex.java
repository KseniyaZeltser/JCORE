public class Vertex {
    public char label;
    public boolean Visit;

    public Vertex(char dat) {
        label = dat;
        Visit = false;
    }
}

class Stack//для обхода в глубину
{
    private final int siz = 20;
    private int[] ar;
    private int top;

    public Stack() {
        ar = new int[siz];
        top = -1;
    }

    public void push(int x) { // Размещение элемента в стеке
        ar[++top] = x;
    }

    public int pop() { // Извлечение элемента из стека
        return ar[top--];
    }

    public boolean isEmpty() { // true, если стек пуст
        return (top == -1);
    }

    public int peek() { // Чтение с вершины стека
        return ar[top];
    }

}

class Queue //для обхода в ширину
{
    private final int siz = 20;
    private int[] qArr;
    private int front;
    private int rear;

    public Queue() {
        qArr = new int[siz];
        front = 0;
        rear = -1;
    }

    public void insert(int x) {
        if (rear == siz - 1)
            rear = -1;
        qArr[++rear] = x;
    }

    public int remove() {
        int temp = qArr[front++];
        if (front == siz)
            front = 0;
        return temp;
    }

    public boolean isEmpty() { // true, если очередь пуста
        return (rear + 1 == front || (front + siz - 1 == rear));
    }
}

class Graph {
    private final int c_vert = 20;//общее количество вершин
    private Vertex vertexList[]; // Список вершин
    private int matr[][]; // Матрица смежности
    private int nVerts; // Текущее количество вершин
    private Stack St;
    private Queue Qu;

    public Graph() {
        vertexList = new Vertex[c_vert];
// Матрица смежности
        matr = new int[c_vert][c_vert];//ребро
        nVerts = 0;
        for (int j = 0; j < c_vert; j++)// Матрица смежности заполняется нулями
            for (int k = 0; k < c_vert; k++)
                matr[j][k] = 0;
        St = new Stack();
        Qu = new Queue();
    }

    public void addVertex(char dat) {
        vertexList[nVerts++] = new Vertex(dat);
    }

    public void addEdge(int start, int end) {
        matr[start][end] = 1;
        matr[end][start] = 1;
    }

    public void depth() { // Обход в глубину
        StringBuffer x = new StringBuffer();
        x.append(vertexList[0].label);//добавление первой метки
        vertexList[0].Visit = true; // Пометка
        System.out.println(x);
        St.push(0);
        while (!St.isEmpty()) {
            int v = UnvisV(St.peek());
            if (v == -1) {
                x.deleteCharAt(x.length() - 1);
                St.pop();
                System.out.println(x);
            } else {
                vertexList[v].Visit = true; // Пометка
                x.append(vertexList[v].label);
                System.out.println(x);
                St.push(v);
            }

        }
        for (int j = 0; j < nVerts; j++)
            vertexList[j].Visit = false;//сброс всех флагов посещения
    }

    public void width() { // Обход в ширину
        StringBuffer y = new StringBuffer();
        y.append(vertexList[0].label);
        vertexList[0].Visit = true;
        System.out.println(y);
        Qu.insert(0);
        int v2;
        while (!Qu.isEmpty()) {
            int v1 = Qu.remove(); // Извлечение вершины в начале очереди
// Пока остаются непосещенные соседи
            while ((v2 = UnvisV(v1)) != -1) {
                vertexList[v2].Visit = true;
                Qu.insert(v2);
                y.append(vertexList[v2].label);

            }
            System.out.println(y);
            y.deleteCharAt(0);
            System.out.println(y);
        }

        for (int j = 0; j < nVerts; j++)
            vertexList[j].Visit = false;
    }

    public int UnvisV(int v) {
        for (int j = 0; j < nVerts; j++)
            if (matr[v][j] == 1 && vertexList[j].Visit == false)
                return j;
        return -1;
    }
}

class bypass {
    public static void main(String[] args) {
        Graph theGraph = new Graph();
        theGraph.addVertex('A');
        theGraph.addVertex('B');
        theGraph.addVertex('C');
        theGraph.addVertex('D');
        theGraph.addVertex('E');
        theGraph.addVertex('F');
        theGraph.addVertex('G');
        theGraph.addEdge(0, 1);
        theGraph.addEdge(0, 2);
        theGraph.addEdge(0, 6);
        theGraph.addEdge(1, 2);
        theGraph.addEdge(1, 3);
        theGraph.addEdge(3, 4);
        theGraph.addEdge(4, 5);
        theGraph.addEdge(5, 6);

        System.out.print("depth: ");
        theGraph.depth();
        System.out.println();

        System.out.print("width: ");
        theGraph.width();
        System.out.println();
    }
}