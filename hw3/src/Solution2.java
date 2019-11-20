import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution2 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution2.java -encoding UTF8

   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output2.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution2

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution2
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution2   // 0.5초 수행
       timeout 1 java Solution2     // 1초 수행
 */

class Solution2 {
	static final int MAX_N = 20000;
	static final int MAX_E = 80000;
	static final int LEFT = 0;
	static final int RIGHT =1;
	static final int ROOT =1;
	static final int START = 1;
	static final int WEIGHT_MIN = -1;
	static int N, E;
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int Answer;
	static Vertex[] Vertices = new Vertex[MAX_N+1];

	static void prim(){
		Answer=0;
		heapNode[] init = buildInitialHeap();
		maxHeap maxHeap = new maxHeap(init);

		maxHeap.update(START,0);
		while(!maxHeap.isEmpty()){
			heapNode maxNode = maxHeap.extractMax();
//			System.out.printf("VID[%d]KEY[%d]\n",maxNode.vertexID,maxNode.key);
			Answer+=maxNode.key;
			Vertices[maxNode.vertexID].inMST=true;
			adjListNode adjListNode=Vertices[maxNode.vertexID].head;
			while(adjListNode!=null){
				int VID=adjListNode.vertexID;
				Vertex v=Vertices[VID];
				if(!v.inMST && maxHeap.heap[maxHeap.indexOf[VID]].key < adjListNode.weight){
					maxHeap.update(VID,adjListNode.weight);
				}
				adjListNode=adjListNode.next;
			}
//			maxHeap.printHeap();
//			break;
		}


	}
	static heapNode[] buildInitialHeap(){
		heapNode[] init = new heapNode[N+1];
		for(int i=1;i<=N;i++){
			init[i]=new heapNode(i,WEIGHT_MIN);
		}
		return init;
	}
	static class maxHeap{
		private heapNode[] heap;
		int len;
		int[] indexOf;


		maxHeap(heapNode[] init){
			heap = init;
			len = init.length-1;
			indexOf = new int[len+1];
			for(int i=1;i<=len;i++){
				indexOf[heap[i].vertexID]=i;
			}
		}
		boolean isEmpty(){
			return len==0;
		}
		int parent(int i){
			return i/2;
		}
		int child(int i, int is_right){
			return 2*i + is_right;
		}

		void printHeap(){
			String s="===========printHeap() start=============\n";
			for(int i=1;i<=len;i++){
				s+=String.format("IDX[%d]VID[%d]KEY[%d]\n",i,heap[i].vertexID,heap[i].key);
			}
			s+="===========printHeap() end==============\n";
			System.out.println(s);

		}
		void swap(int parent, int child){

			heapNode childNode = heap[child];
			heapNode parentNode = heap[parent];
			indexOf[childNode.vertexID] = parent;
			indexOf[parentNode.vertexID] = child;
			heap[child]=parentNode;
			heap[parent]=childNode;

		}
		boolean hasParent(int i){
			return i>1;
		}

		void update(int id,int newKey){
			int heapIdx= indexOf[id];
			heapNode node = heap[heapIdx];
			node.key=newKey;
			bubbleUp(heapIdx);
		}
		void bubbleUp(int i){
			if(!hasParent(i)) return;

			heapNode child = heap[i];
			heapNode parent = heap[parent(i)];

			if(child.compareTo(parent)>0){
				swap(parent(i),i);
				bubbleUp(parent(i));
			}
		}
		void bubbleDown(int i){
			int maxIdx = i;
			int leftIdx = child(i,LEFT);
			int rightIdx = child(i,RIGHT);

			if(leftIdx <= len && heap[leftIdx].compareTo(heap[maxIdx])>0){
				maxIdx = leftIdx;
			}
			if(rightIdx <= len && heap[rightIdx].compareTo(heap[maxIdx])>0){
				maxIdx = rightIdx;
			}
			if(maxIdx!=i){
				swap(i,maxIdx);
				bubbleDown(maxIdx);
			}

		}
		heapNode extractMax(){
			heapNode maxNode=heap[ROOT];
			swap(ROOT,len--);
			bubbleDown(ROOT);
			return maxNode;
		}
	}

	static class adjListNode{
		int vertexID;
		int weight;
		adjListNode next;

		adjListNode(int vertexID, int weight){
			this.vertexID=vertexID;
			this.weight=weight;
			this.next=null;
		}
	}
	static class Vertex{
		adjListNode head;
		boolean inMST;

		Vertex(){
			this(null);
		}
		Vertex(adjListNode head){
			this.head=head;
			inMST=false;
		}
	}
	static class heapNode implements Comparable<heapNode>{

		int vertexID;
		int key;

		heapNode(int vertexID,int key){
			this.vertexID=vertexID;
			this.key=key;
		}
		@Override
		public int compareTo(heapNode o) {
			return this.key-o.key;
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input2.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output2.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 양 끝점의 번호를 U[i], V[i]에 읽어들이고, i번째 간선의 가중치를 W[i]에 읽어들입니다. (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
			}

			for(int i=1;i<=N;i++){
				Vertices[i]=new Vertex();
			}

			for( int i=0;i<E;i++){
				Vertex u = Vertices[U[i]];
				Vertex v = Vertices[V[i]];
				int weight = W[i];

				adjListNode node_v=new adjListNode(V[i],weight);
				adjListNode node_u=new adjListNode(U[i],weight);

				node_v.next = u.head;
				u.head = node_v;
				node_u.next = v.head;
				v.head = node_u;
			}

//			for(int i=1;i<=N;i++){
//				Vertex v = Vertices[i];
//				System.out.printf("\nvertex[%d]",i);
//				adjListNode head= v.head;
//				while(head!=null){
//					System.out.printf("[%d-%d]",head.vertexID,head.weight);
//					head=head.next;
//				}
//			}
			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			prim();

			// output2.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);
			/*
			   아래 코드를 수행하지 않으면 여러분의 프로그램이 제한 시간 초과로 강제 종료 되었을 때,
			   출력한 내용이 실제로 파일에 기록되지 않을 수 있습니다.
			   따라서 안전을 위해 반드시 flush() 를 수행하시기 바랍니다.
			 */
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

