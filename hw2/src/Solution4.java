import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution4 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution4.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output4.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution4

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution4
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution4   // 0.5초 수행
       timeout 1 java Solution4     // 1초 수행
 */

class Solution4 {
    static final int max_n = 100000;

    static int n;
    static int[][] A = new int[3][max_n];
    static int Answer;
    static final int[][] coeff = {
            {1, 0, -1},
            {1, -1, 0},
            {0, -1, 1},
            {0, 1, -1},
            {-1, 1, 0},
            {-1, 0, 1}
    };
    static int[][] storeMax=new int[max_n][6];

    static int getColSum(int colIndex, int type) { // complexity of getColSum : Θ(1)
        if (type < 0 || type > 5) {
            System.out.println("Wrong type");
            System.exit(1);
        }
        int colSum = 0;
        for (int i = 0; i < 3; i++) {  // 3 * Θ(1)
            colSum += A[i][colIndex] * coeff[type][i];
        }
        return colSum;

    }

    static void updateMaxSum(int colIndex) {  // complexity of updateMaxSum : Θ(1)
        for (int type = 0; type < 6; type++) { // 6 * Θ(1)
            storeMax[colIndex][type] = Math.max(storeMax[colIndex-1][(type + 2) % 6], storeMax[colIndex-1][(type + 4) % 6])
                    + getColSum(colIndex, type);
        }
    }

    public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input4.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output4.txt 로 정답을 출력합니다.
		 */
        BufferedReader br = new BufferedReader(new FileReader("input4.txt"));
        StringTokenizer stk;
        PrintWriter pw = new PrintWriter("output4.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
        for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 놀이판의 열의 개수를 n에 읽어들입니다.
			   그리고 첫 번째 행에 쓰여진 n개의 숫자를 차례로 A[0][0], A[0][1], ... , A[0][n-1]에 읽어들입니다.
			   마찬가지로 두 번째 행에 쓰여진 n개의 숫자를 차례로 A[1][0], A[1][1], ... , A[1][n-1]에 읽어들이고,
			   세 번째 행에 쓰여진 n개의 숫자를 차례로 A[2][0], A[2][1], ... , A[2][n-1]에 읽어들입니다.
			 */
            stk = new StringTokenizer(br.readLine());
            n = Integer.parseInt(stk.nextToken());
            for (int i = 0; i < 3; i++) {
                stk = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    A[i][j] = Integer.parseInt(stk.nextToken());
                }
            }
            for (int type=0;type<6;type++){  // 6 * Θ(1)  ( getColSum takes Θ(1) time )
                storeMax[0][type]=getColSum(0,type);
            }

            for (int j = 1; j < n; j++) {   // (n-1) * Θ(1) (updateMaxSum takes Θ(1) time )
                updateMaxSum(j);
            }

            Answer=Integer.MIN_VALUE;
            for (int type=0;type<6;type++){ // 6 * Θ(1)
            	Answer=Math.max(Answer,storeMax[n-1][type]);
			}
            // Therefore, overall complexity = 6 * Θ(1) + (n-1) * Θ(1) + 6 * Θ(1) = Θ(n)


            /////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
			 */
            /////////////////////////////////////////////////////////////////////////////////////////////


            // output4.txt로 답안을 출력합니다.
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

