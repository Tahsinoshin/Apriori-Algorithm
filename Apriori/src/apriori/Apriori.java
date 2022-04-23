package apriori;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;


public class Apriori {
    public static ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> candidateItems = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> upToSupportValue = new ArrayList<>();
    private int[] countCandidateItems;
    private final int supportValue;

    private FileReader fr = new FileReader();

    public Apriori(int supportValue){
        this.supportValue = supportValue;
    }

    public void countBestItemSet() throws FileNotFoundException, InterruptedException {
        fr.readFile();

        addToItemSet();

        while (true){

            countCandidateItems = new int[candidateItems.size()];
            for (int i = 0; i< candidateItems.size(); i++) {
                for (Transaction ts : transactions) {
                    if (ts.hasItem(candidateItems.get(i)))
                        countCandidateItems[i]++;
                }
            }

            pruneItems();

            if(candidateItems.size() > 0){
                upToSupportValue = new ArrayList<>(candidateItems);
                candidateItems = new ArrayList<>();
            } else break;
            if(upToSupportValue.size() <= 1) break;

            joinItems();
        }

        print(upToSupportValue);
        System.out.println();
        new Confidence(upToSupportValue);
    }

    private void joinItems() {
        for(int i = 0; i< upToSupportValue.size(); i++){
            for (int j = i+1; j< upToSupportValue.size(); j++){
                if(containsCommonPrefix(upToSupportValue.get(i), upToSupportValue.get(j))){
                    ArrayList<Integer> tempCandidate = new ArrayList<>(upToSupportValue.get(i));
                    tempCandidate.add( upToSupportValue.get(j).get(upToSupportValue.get(j).size()-1) );
                    boolean flag= true;
                    for (int list=tempCandidate.size()-1;list>=0;list--){
                        ArrayList<Integer> checkSupportVal = new ArrayList<>(tempCandidate);
                        checkSupportVal.remove(list);
                        if(!upToSupportValue.contains(checkSupportVal)){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        candidateItems.add(tempCandidate);
                    }
                }
                else break;
            }
        }
    }


    private boolean containsCommonPrefix(ArrayList<Integer> array1, ArrayList<Integer> array2){
        if(array1.size() <= 1) return true;
        else {
            boolean flag = true;
            for (int i=0;i<array1.size()-1;i++){
                if(!array1.get(i).equals(array2.get(i))){
                    flag = false;
                    break;
                }
            }
            return flag;
        }
    }

    private void pruneItems() {
        for(int i = countCandidateItems.length-1; i>=0; i--){
            if(countCandidateItems[i]< supportValue){
                candidateItems.remove(i);
            }
        }
    }


    private void addToItemSet() {
        ArrayList<Integer> itemSet = new ArrayList<>();
        for (Transaction ts : transactions) {
            for (int i:ts.items()) {
                if(!itemSet.contains(i)) itemSet.add(i);
            }
        }
        Collections.sort(itemSet);
        for (Integer singleItem : itemSet) {
            ArrayList<Integer> allItems = new ArrayList<>();
            allItems.add(singleItem);
            candidateItems.add(allItems);
        }
    }






    private void print(ArrayList<ArrayList<Integer>> itemArray){
        for (ArrayList<Integer> i:itemArray) {
            for (int j:i) {
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }


}
