package org.example.sort;

import java.util.ArrayList;
import java.util.List;

public class SortingUtils {

    public static void quickSort(List<Integer> list, int start, int end) {
        if(start >= end) return;

        int pivot = partition(list, start, end);
        quickSort(list, start, pivot - 1);
        quickSort(list, pivot + 1, end);
    }

    private static int partition(List<Integer> list, int start, int end) {
        int pivot = list.get(end);
        int r = start;
        for(int l=start;l<end;l++){
            if(list.get(l) < pivot){
                int temp = list.get(l);
                list.set(l, list.get(r));
                list.set(r, temp);
                r++;
            }
        }

        int temp = list.get(end);
        list.set(end, list.get(r));
        list.set(r, temp);
        return r;
    }

    public static void mergeSort(List<Integer> list, int start, int end) {
        if(start+1 >= end) return;
        //System.out.println(start + " "+end);

        int mid = (start+end)/2;
        mergeSort(list, start, mid);
        mergeSort(list, mid, end);

        merge(list, start, end, mid);
    }

    private static void merge(List<Integer> list, int start, int end, int mid) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        //System.out.println("merge "+start +"~"+mid+" "+mid+"~"+end);

        for(int i=start; i < mid;i++){
            left.add(list.get(i));
        }
        for(int i=mid; i < end;i++){
            right.add(list.get(i));
        }

        int i=0,j=0,k=start;
        while(i < mid - start && j < end - mid){
            if(left.get(i) < right.get(j)){
                list.set(k, left.get(i));
                i++;
            }else {
                list.set(k, right.get(j));
                j++;
            }
            k++;
        }
        while(i < mid - start){
            list.set(k, left.get(i));
            i++;
            k++;
        }
        while(j < end - mid){
            list.set(k, right.get(j));
            j++;
            k++;
        }
    }

    public static void insertionSort(List<Integer> list) {
        int len = list.size();
        // 삽입 정렬
        for(int i=1;i<len;i++){
            int value = list.get(i);
            int j = i-1;
            while(j >= 0 && value < list.get(j)){
                list.set(j+1, list.get(j));
                j--;
            }
            list.set(j+1, value);
        }
    }

    public static void selectionSort(List<Integer> list) {
        int len = list.size();
        // 선택 정렬
        for(int i=0;i<len;i++) {
            int value = list.get(i);
            for(int j=i+1;j<len;j++){
                if(value > list.get(j)){
                    int temp = list.get(j);
                    list.set(j, value);
                    value = temp;
                }
            }
            list.set(i, value);
        }
    }

    public static void bubbleSort(List<Integer> list) {
        int len = list.size();
        // 버블 정렬
        for(int i=len-1;i>=0;i--){
            for(int j=0;j<i;j++){
                if(list.get(j) > list.get(j+1)){
                    int temp = list.get(j+1);
                    list.set(j+1, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }
}
