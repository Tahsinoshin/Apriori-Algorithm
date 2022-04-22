package apriori;

import java.util.ArrayList;


public class Confidence {
    private int givenCount = 0;
    private int expectedCount = 0;

    public  Confidence(ArrayList<ArrayList<Integer>> upToSupportValue ){

        for(ArrayList<Integer> eligibleSeq: upToSupportValue){
            combinationOfItemSet(eligibleSeq, -1, new ArrayList<>());
        }
    }

    private void combinationOfItemSet(ArrayList<Integer> sequence, int index, ArrayList<Integer> prefix) {
        int n = sequence.size();

        // base case
        if (index == n) {
            return;
        }

        ArrayList<Integer> newString = new ArrayList<>(sequence);
        // First print current subset
        if(prefix.size() != 0 && prefix.size() != sequence.size()) {
            newString.removeAll(prefix);
            System.out.println("confidence of " + prefix + " => " + newString+" is "+100*countConfidence(prefix,sequence)+" %");
        }

        // Try appending remaining characters
        // to current subset
        for (int i = index + 1; i < n; i++) {
            prefix.add(sequence.get(i));
            combinationOfItemSet(sequence, i, prefix);

            // Once all subsets beginning with
            // initial "curr" are printed, remove
            // last character to consider a different
            // prefix of subsets.
            prefix.remove(prefix.size()-1);
        }
    }


    public double countConfidence(ArrayList<Integer> given, ArrayList<Integer> expected) {

        for (Transaction t : Apriori.transactions) {
            if (t.hasItem(given)) givenCount++;
            if (t.hasItem(expected)) expectedCount++;
        }
        return ((double) expectedCount) / ((double) givenCount);
    }
}

