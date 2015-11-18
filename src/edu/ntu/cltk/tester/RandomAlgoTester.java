package edu.ntu.cltk.tester;

import edu.ntu.cltk.algo.RandomAlgo;

import java.util.List;
import java.util.Random;

public class RandomAlgoTester {

    public static void main(String[] args) {
        try {
            distributeCandyTester();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void distributeCandyTester() throws Exception {

        Random ran = new Random();
        System.out.println("***********Distribute Candy Test**********");
        for (int i = 0; i < 5; i++) {
            int baskNo = ran.nextInt(10) + 1;
            int canNo = ran.nextInt(10) + 1;
            List<List<Integer>> baskets = RandomAlgo.distributeCandy(baskNo, canNo);
            for (int k = 0; k < baskets.size(); k++) {
                System.out.print("Basket " + k + ": ");
                for (int j = 0; j < baskets.get(k).size(); j++) {
                    System.out.print(baskets.get(k).get(j) + " ");
                }
                System.out.println();
            }
            System.out.println("-----------------------------");
        }
        System.out.println("***********Distribute Candy Without Empty Basket Test********");
        for (int i = 0; i < 5; i++) {
            int baskNo = ran.nextInt(10) + 1;
            int canNo = ran.nextInt(11 - baskNo) + baskNo;
            List<List<Integer>> baskets = RandomAlgo.distributeCandyWithoutEmptyBasket(baskNo, canNo);
            for (int k = 0; k < baskets.size(); k++) {
                System.out.print("Basket " + k + ": ");
                for (int j = 0; j < baskets.get(k).size(); j++) {
                    System.out.print(baskets.get(k).get(j) + " ");
                }
                System.out.println();
            }
            System.out.println("-----------------------------");
        }

    }
}
