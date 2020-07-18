package com.example.webDemo3.service.impl.assignRedStarServiceImpl;

import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.ClassRedStar;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.ClassRedStarRepository;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CreateAssignRedStarServiceImpl {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRedStarRepository classRedStarRepository;

    private int[] indexClassOfRedStar;
    private List<Integer>[] indexRedStarOfClass;
    private int[][] flag;
    private Random ran = new Random();
    private static int n = 10000;
    int[][] population;//= new int [n][];
    int[] costValue;//= new int[n];
    int size;

    public void create(Date fromDate) {
        List<Class> classList = classRepository.findAll();
        List<User> redStarList = userRepository.finRedStar();
        Date beforDate = classRedStarRepository.getBiggestClosetDate(fromDate);
        List<ClassRedStar> assignList = new ArrayList<>();
        if (beforDate != null) {
            assignList = classRedStarRepository.findAllByDate(beforDate);
        }

        getIndex(classList, redStarList, assignList);
        khoitao(redStarList.size());

        for (int i = 0; i < 10000; i++) {
            int[][] flagcCopy = copyflag(classList.size() * 2,redStarList.size());
                    //flag.clone();
            danhgia(flagcCopy);
            Print();
            chonloc();
            laighep();
//            dotbien();
        }

//        for (int i = 0; i < 10000; i++) {
//            int[][] flagCopy = copyflag(classList.size() * 2,redStarList.size());
//            int kq = test(redStarList.size(),flagCopy);
//            if(kq ==1){
//                System.out.println(i);
//                break;
//            }
//        }

        int t = 0;
        //chuan bi

    }

    private int test(int redStarListSize,int[][] flagCopy) {
        int[] data = new int[redStarListSize];
        for (int i = 0; i < redStarListSize; i++) {
            data[i] = i;
        }
        int[] output = new int[size];
        for (int j=0;j<size;j++) output[j] = -1;
        int maxfirst = redStarListSize;
        int kq = 1;
        for (int classIndex = 0; classIndex < size; classIndex++) {
            int t =0;
            int[] copyData = data.clone();
            int max = maxfirst;
            if(classIndex == 82){
                int h=0;
            }
            while (true){
                int value ;//= ran.nextInt(max);
                if(max < 0){
                    if (output[classIndex] == -1)
                        kq =0;
                    break;
                }
                else if( max == 0){
                    value =0;
                }
                else {
                    value = ran.nextInt(max);
                }
                //output[i] = data[value];
                int redStar = copyData[value];
                if (flagCopy[classIndex][redStar] ==0) {
                   // System.out.println(classIndex + "===============================================");
                    output[classIndex] = redStar;
                    flagCopy[classIndex][redStar] = 1;
                    if(classIndex%2 == 0){
                        flagCopy[classIndex+1][redStar] = 1;
                    }
                    int k = 0;
                    if (classIndex != 0) k = classIndex/2;
                    for (int redStarOfClass : indexRedStarOfClass[k]){
                        flagCopy[indexClassOfRedStar[redStar]*2][redStarOfClass] = 1;
                        flagCopy[indexClassOfRedStar[redStar]*2+1][redStarOfClass] = 1;
                    }
                    if(maxfirst > 0){
                        for(int j =0;j<maxfirst;j++){

                            if(data[j] == redStar){
                                maxfirst--;
                                int temp = data[j];
                                data[j] = data[maxfirst];
                                data[maxfirst] = temp;
                                break;
                            }
                        }
                    }
                    else{
                        kq=2;
                        maxfirst--;
                    }
                    break;
                }
                else{
                    max--;
                    if(max>=0){
                        int temp = copyData[value];
                        copyData[value] = copyData[max];
                        copyData[max] = temp;
                    }
                }
            }
        }

        System.out.print("ket qua: "+kq+": ");
        for (int j=0;j<size;j++) {
            System.out.print(output[j] + " ,");
        }
        System.out.println();
        return kq;
    }

    private int[][] copyflag(int d,int c){
        int[][] flagcCopy = new int[d][c];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < c; j++) {
                int value = flag[i][j];
                flagcCopy[i][j] = value;
            }
        }
        return flagcCopy;
    }

    private void getIndex(List<Class> classList, List<User> redStarList, List<ClassRedStar> assignList) {
        //set up
        size = classList.size() * 2;
        population = new int[n][size];
        costValue = new int[n];
        indexClassOfRedStar = new int[redStarList.size()];
        indexRedStarOfClass = (List<Integer>[]) new List[classList.size()];
        flag = new int[classList.size() * 2][redStarList.size()];

        for (int i = 0; i < classList.size(); i++) {
            Class classi = classList.get(i);
            indexRedStarOfClass[i] = new ArrayList<>();
            for (int j = 0; j < redStarList.size(); j++) {
                User redstar = redStarList.get(j);
                if (classi.getClassId() == redstar.getClassSchool().getClassId()) {
                    indexRedStarOfClass[i].add(j);
                    indexClassOfRedStar[j] = i;
                }
                //fill all flag = 0
                flag[i * 2][j] = 0;
                flag[i * 2 + 1][j] = 0;
            }
        }

        for (int i = 0; i < classList.size(); i++) {
            Class classi = classList.get(i);
            for (int j = 0; j < redStarList.size(); j++) {
                User redstar = redStarList.get(j);
                if (classi.getGrade() == redstar.getClassSchool().getGrade()) {
                    flag[i * 2][j] = -1;
                    flag[i * 2 + 1][j] = -1;
                }
                for (int k = 0; k < assignList.size(); k++) {
                    ClassRedStar data = assignList.get(k);
                    if ((data.getClassId() == classi.getClassId())
                            && (data.getClassRedStarId().getRED_STAR() == redstar.getUsername())) {
                        flag[i * 2][j] = -1;
                        flag[i * 2 + 1][j] = -1;
                    }
                    User userData = userRepository.findUserByUsername(data.getClassRedStarId().getRED_STAR());
                    if ((userData.getClassSchool().getClassId() == classi.getClassId())
                            && (data.getClassId() == redstar.getClassSchool().getClassId())) {
                        flag[i * 2][j] = -1;
                        flag[i * 2 + 1][j] = -1;
                    }
                }
            }
        }
    }

    private void khoitao(int redStarListSize) {
        int[] data = new int[redStarListSize];
        for (int i = 0; i < redStarListSize; i++) {
            data[i] = i;
        }
        for (int i = 0; i < n; i++) {
            //population[i] = new int[size];
            int[] copyData = data.clone();
            int max = redStarListSize;
            for (int j = 0; j < size; j++) {
                int value = ran.nextInt(max);
                population[i][j] = copyData[value];
                max--;
                copyData[value] = copyData[max];
            }
        }
    }

    private void danhgia(int[][] flagcCopy) {
        for (int i = 0; i < n; i++) {
            costValue[i] = 0;
            for (int classIndex = 0; classIndex < size; classIndex++) {
                int redStar = population[i][classIndex];
                if (flagcCopy[classIndex][redStar] != 0) {
                    costValue[i]++;
                }
                else {
                    flagcCopy[classIndex][redStar] = 1;
                    if(classIndex%2 == 0){
                        flagcCopy[classIndex+1][redStar] = 1;
                    }
                    int k = 0;
                    if (classIndex != 0) k = classIndex/2;
                    for (int redStarOfClass : indexRedStarOfClass[k]){
                        flagcCopy[indexClassOfRedStar[redStar]*2][redStarOfClass] = 1;
                        flagcCopy[indexClassOfRedStar[redStar]*2+1][redStarOfClass] = 1;
                    }
                }
            }
        }
    }

    public void Print() {
        int [] temp = costValue.clone();
        Arrays.sort(temp);
        int best = temp[0];
        System.out.print(best+": ");
        for (int i=0;i<n;i++){
            if (costValue[i]==best){
                for (int j=0;j<population[i].length;j++)
                    System.out.print(population[i][j]+" ,");
                System.out.println();
                break;
            }
        }
    }

    public void chonloc()
    {
        int [] temp = costValue.clone();
        Arrays.sort(temp);
        int nguong = temp[n*80/100];
        for (int i=0;i<n;i++){
            if (costValue[i]>nguong){
                population[i]=population[ran.nextInt(n)].clone();
            }
        }
    }

    public void laighep() {
        for (int i=0;i<n/2;i++){
            int cha=ran.nextInt(n);
            int me = ran.nextInt(n);

            int[] indexCha = new int[size];
            int[] indexme = new int[size];
            for (int j=0;j<size;j++){
                indexCha[population[cha][j]] = j;
                indexme[population[me][j]] = j;
            }

            for (int j=0;j<size;j++)
                if (ran.nextInt(2)==1){
                    int temp=population[cha][j];
                    population[cha][j]=population[me][j];
                    population[me][j]=temp;

                    temp = population[cha][indexCha[population[cha][j]]];
                    population[cha][indexCha[population[cha][j]]]
                            = population[me][indexCha[population[me][j]]];
                    population[me][indexCha[population[me][j]]] = temp;
                }
        }
    }

}
