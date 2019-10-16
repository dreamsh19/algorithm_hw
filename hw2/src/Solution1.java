import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution1 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution1.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output1.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution1

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution1
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution1   // 0.5초 수행
       timeout 1 java Solution1     // 1초 수행
 */

class Solution1 {
	static final int max_n = 1000000;
	static int[] A = new int[max_n];
    static int[] copied = new int[max_n];
	static int n;
    static int Answer1, Answer2, Answer3;
	static long start;
	static double time1, time2, time3;



	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input1.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output1.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output1.txt");
		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 배열의 원소의 개수를 n에 읽어들입니다.
			   그리고 각 원소를 A[0], A[1], ... , A[n-1]에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				A[i] = Integer.parseInt(stk.nextToken());
			}


			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
               원본으로 주어진 입력 배열이 변경되는 것을 방지하기 위해,
               여러분이 구현한 각각의 함수에 복사한 배열을 입력으로 넣도록 코드가 작성되었습니다.

			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
               함수를 구현하면 Answer1, Answer2, Answer3에 해당하는 주석을 제거하고 제출하세요.

               문제 1은 java 프로그래밍 연습을 위한 과제입니다.
			 */

            /* Problem 1-1 */
            System.arraycopy(A, 0, copied, 0, n);
			start = System.currentTimeMillis();
            // Answer1 = merge1(copied);
            time1 = (System.currentTimeMillis() - start) / 1000.;

            /* Problem 1-2 */
            System.arraycopy(A, 0, copied, 0, n);
			start = System.currentTimeMillis();
            // Answer2 = merge2(copied);
            time2 = (System.currentTimeMillis() - start) / 1000.;

            /* Problem 1-3 */
            System.arraycopy(A, 0, copied, 0, n);
			start = System.currentTimeMillis();
            // Answer3 = merge3(copied);
            time3 = (System.currentTimeMillis() - start) / 1000.;


            /*
            	여러분의 답안 Answer1, Answer2, Answer3을 비교하는 코드를 아래에 작성.
             	세 개 답안이 동일하다면 System.out.println("YES");
             	만일 어느하나라도 답안이 다르다면 System.out.println("NO");
            */



			/////////////////////////////////////////////////////////////////////////////////////////////


			// output1.txt로 답안을 출력합니다. Answer1, Answer2, Answer3 중 구현된 함수의 답안 출력
			pw.println("#" + test_case + " " + Answer1 + " " + time1 + " " + time2 + " " + time3);
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

