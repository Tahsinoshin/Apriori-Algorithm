package apriori;

import java.util.ArrayList;


public class Confidence {
    private int givenCount ;
    private int expectedCount ;

    public  Confidence(ArrayList<ArrayList<Integer>> upToSupportValue ){

        for(ArrayList<Integer> supportingItemSets: upToSupportValue){
            combinationOfItemSet(supportingItemSets, -1, new ArrayList<>());
        }
    }

    private void combinationOfItemSet(ArrayList<Integer> itemSetList, int index, ArrayList<Integer> subSetOfItem) {
        int len = itemSetList.size();

        // length=0
        if (index == len) {
            return;
        }

        ArrayList<Integer> complementaryItemSet = new ArrayList<>(itemSetList);

        if(subSetOfItem.size() != 0 && subSetOfItem.size() != itemSetList.size()) {
            complementaryItemSet.removeAll(subSetOfItem);
            System.out.println("confidence of " + subSetOfItem + "with respect of-> " + complementaryItemSet+" is  "+100*countConfidence(subSetOfItem,itemSetList)+" %");
        }


        for (int i = index + 1; i < len; i++) {
            subSetOfItem.add(itemSetList.get(i));
            combinationOfItemSet(itemSetList, i, subSetOfItem);

            subSetOfItem.remove(subSetOfItem.size()-1);
        }
    }


    public double countConfidence(ArrayList<Integer> existingCount, ArrayList<Integer> expectedCount) {

        givenCount=0;
        this.expectedCount =0;

        for (Transaction ts : Apriori.transactions) {
            if (ts.hasItem(existingCount)) givenCount++;
            if (ts.hasItem(expectedCount)) this.expectedCount++;
        }
        return ((double) this.expectedCount) / ((double) givenCount);
    }
}

