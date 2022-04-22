package apriori;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Transaction {
    private final int[] items;

    public int[] items(){
        return items;
    }

    public Transaction(int[] items) {
        this.items = items;
        Arrays.sort(this.items);
    }

    public boolean hasItem(ArrayList<Integer> checkItem){
        Collections.sort(checkItem);

        if(checkItem.get(0) < items[0] || checkItem.get(checkItem.size()-1) > items[items.length-1]){
            return false;
        }

        int i=0,j=0;
        boolean flag = true;

        while(true){
            if(j==checkItem.size()){
                break;
            }
            if(i == items.length){
                flag = false;
                break;
            }

            if(items[i]<checkItem.get(j)) {
                i++;
            } else if(items[i]==checkItem.get(j)) {
                i++;
                j++;
            } else {
                flag = false;
                break;
            }

        }

        return flag;
    }

    public void print(){
        for (int i:items) {
            System.out.print(i);
        }
        System.out.println();
    }
}
