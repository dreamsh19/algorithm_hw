import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution4 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
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

	/*
		** 주의사항
		정답의 숫자가 매우 크기 때문에 답안은 반드시 int 대신 long 타입을 사용합니다.
		그렇지 않으면 overflow가 발생해서 0점 처리가 됩니다.
		Answer[0]을 a의 개수, Answer[1]을 b의 개수, Answer[2]를 c의 개수라고 가정했습니다.
	*/
    static int n;                           // 문자열 길이
    static String s;                        // 문자열
	static long[] Answer = new long[3];     // 정답

	static long[][] a_num;
	static long[][] b_num;
	static long[][] c_num;


	static void cal(){ // cal takes Θ(n^3)

		a_num=new long[n][n]; // It takes n * n times = Θ(n^2)
		b_num=new long[n][n]; // It takes n * n times = Θ(n^2)
		c_num=new long[n][n]; // It takes n * n times = Θ(n^2)

		for(int i=0;i<n;i++){
			// It iterates n times
			// Inside for loop it takes Θ(1) time
			// Thus it takes n * Θ(1) = Θ(n)
			switch (s.charAt(i)){
				case 'a' : a_num[i][i]=1;break;
				case 'b' : b_num[i][i]=1;break;
				case 'c' : c_num[i][i]=1;break;
				default : break;
			}
		}

		for(int len=1;len<n;len++){ // 'len' iterates (n-1) times
			for(int start=0;start< n-len;start++){ // 'start' iterates (n-len) times
				int end=start+len;
				for (int mid=start;mid<end;mid++){ // 'mid' iterates 'len' times
					// Inside for loop it takes Θ(1) time
					// Thus it takes (n-1) * 1 + (n-2) * 2 + (n-3) * 3 + ... + 1 * (n-1) = Θ(n^3)
					a_num[start][end]+=(
							(a_num[start][mid]+b_num[start][mid])*c_num[mid+1][end]
							+c_num[start][mid]*a_num[mid+1][end]
					);
					b_num[start][end]+=(
							a_num[start][mid]*(a_num[mid+1][end]+b_num[mid+1][end])
							+b_num[start][mid]*b_num[mid+1][end]
					);
					c_num[start][end]+=(
							b_num[start][mid]*a_num[mid+1][end]
							+c_num[start][mid]*(b_num[mid+1][end]+c_num[mid+1][end])
							);

				}
			}
		}
		// Therefore, overall complexity = 3 * Θ(n^2) + Θ(n) + Θ(n^3) = Θ(n^3)


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
			   각 테스트 케이스를 파일에서 읽어옵니다.
               첫 번째 행에 쓰여진 문자열의 길이를 n에 읽어들입니다.
               그 다음 행에 쓰여진 문자열을 s에 한번에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			s = br.readLine();

			cal(); // cal takes Θ(n^3)
			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   정답을 구하기 위해 주어진 문자열 s를 여러분이 원하시는 대로 가공하셔도 좋습니다.
			   문제의 답을 계산하여 그 값을 Answer(long 타입!!)에 저장하는 것을 가정하였습니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer[0] = a_num[0][n-1];  // a 의 갯수
			Answer[1] = b_num[0][n-1];  // b 의 갯수
			Answer[2] = c_num[0][n-1];  // c 의 갯수


			// output2.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer[0] + " " + Answer[1] + " " + Answer[2]);
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

