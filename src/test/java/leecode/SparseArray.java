package leecode;

import org.junit.jupiter.api.Test;

/**
 * 稀疏数组--数组相互转换
 */
public class SparseArray {


    @Test
    public void spaserArray(){


        //创建普通二维数组
        int chessArr1[][] = new int[11][11];

         chessArr1[2][3] = 1;
         chessArr1[3][4] = 2;
         chessArr1[4][5] = 1;

         //打印二维数组
         for (int[] row : chessArr1){
             for (int data : row){
                 System.out.printf("%d\t",data);
             }
             System.out.println();
         }

        //二维数组转稀疏数组
        int sum = 0;
        for (int[] row : chessArr1){
            for (int data : row){
               if(data!=0){
                   sum++;
               }
            }
        }

        int[][] SparseArr = new int[sum + 1][3];
        SparseArr[0][0] = 11;
        SparseArr[0][1] = 11;
        SparseArr[0][2] = sum;
        int count = 0;
        for (int j=0;j<11;j++){
            for (int k=0;k<11;k++){
                if(chessArr1[j][k]!=0){
                    count++;
                    SparseArr[count][0] = j;
                    SparseArr[count][1] = k;
                    SparseArr[count][2] = chessArr1[j][k];
                }
            }
        }
        //打印稀疏数组
        for (int[] row : SparseArr){
            System.out.printf("%d\t%d\t%d\t",row[0],row[1],row[2]);
            System.out.println();
        }

        //稀疏数组转二维数组
        int jj = SparseArr[0][0];
        int kk = SparseArr[0][1];
        int num1 = SparseArr[0][2];

        int[][] changeArry = new int[jj][kk];

        for (int i =0;i<num1;i++){
                changeArry[SparseArr[i+1][0]] [SparseArr[i+1][1]] = SparseArr[i+1][2];
        }

        //打印转换后的二维数组
        for (int[] row : changeArry){
            for (int data : row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }




    }



//    @Test
//    public void spaserArray2(){
//
//        int chessArr1[][][] = new int[11][11][11];
//
//        chessArr1[2][3][3] = 1;
//        chessArr1[3][4][3] = 2;
//
//        for (int[][] row : chessArr1){
//            for (int[] row1 : row){
//                for (int data : row1){
//                    System.out.printf("%d\t",data);
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
//
//
//    }

}
