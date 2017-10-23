package com.ag;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;



public class Population {

	Integer[][] cromossomos;
    int tamanhoCromossomo, tamanhoPop;
    int limSup, limInf;

    //controe a população de acordo com os paramentros recebidos
    // nesse caso tamanhoPop representa quantidade de individuos
    // tamanhoCromossomo o tamanho do array para armazenar cada bit
    Population(int tamanhoCromossomo, int tamanhoPop){
        this.tamanhoCromossomo = tamanhoCromossomo;
        this.tamanhoPop = tamanhoPop;
        cromossomos = new Integer[tamanhoPop][tamanhoCromossomo];
    }

    // gera idividuo e insere dentro da matriz de cromossomos

    void gerarIndividuos(int limSup, int limInf){
        this.limSup = limSup;
        this.limInf = limInf;

        for (int i =0; i < tamanhoPop; i++){
            for(int j=0; j < tamanhoCromossomo; j++){
                cromossomos[i][j] = random();
                //System.out.println("creating :" + cromossomos[i][j]);
            }
        }


    }

    //metodo para gerar 1 ou 0 e preencher o array de bits do cromossomo
    public int random() {
        Random rand = new Random();
        int numero;
        numero = rand.nextInt();
            if ((numero%2)==0){
                return 1;
            }
            else{
                return 0;
            }
   }
    void showCromossomos (){

         for (int i =0; i < tamanhoPop; i++){
             System.out.print(i+":[");
            for(int j=0; j < tamanhoCromossomo; j++){
               System.out.print(cromossomos[i][j]+",");
            }
            System.out.println("]");
        }
    }
     int getTamanhoPopulacao(){

    return tamanhoPop;
    }


}
