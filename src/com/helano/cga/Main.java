package com.helano.cga;

import java.util.Random;

import com.helano.cga.Population;

public class Main {

	   static int limSup = 5;
	   static int limInf = -5;
	   static int tamanhoPop = 10;
	   static int precisao =1;
	   static int geracoes = 100;
	   static int percentualDeMultacao = 5; //%
	   static int tamanhoCromossomo;
	   static int tamtorneio = 15;
	   static  Population populacao;
	   static int otimo = 0;
	   static double[] p;
	   static int[][] cromossomo;

	public static void main(String[] args) {
	    double[] fitness = new double[2];
	    boolean convergencia = false;

		tamanhoCromossomo = (int) calculaTamanho(limSup, limInf);
		cromossomo = new int[2][tamanhoCromossomo];
		p = new double[tamanhoCromossomo];
		p = inicializeProbability(p);
		 int winner=0;

 for (int g =0;(( g < geracoes )&&(convergencia == false)); g++) {

	  		convergencia = true;
		cromossomo[0] = gerarIndividuo(p);
		cromossomo[1] = gerarIndividuo(p);

		fitness[0] = fitness(cromossomo[0]);
		fitness[1] = fitness(cromossomo[1]);

		for (int i = 0; i < tamanhoCromossomo; i++) {

			System.out.print(" |"+ p[i]);

		}
		System.out.println();
		for (int i = 0; i < tamanhoCromossomo; i++) {

			  System.out.print(""+ cromossomo[1][i]);

		}


        System.out.println("fitness: "+ fitness[0]);
        System.out.println("fitness: "+ fitness[1]);


        int[]resultado = 	competition(fitness);
         winner = resultado[0];
       int loser = resultado[1];

       for (int i=0; i< tamanhoCromossomo; i++) {
    	   if (cromossomo[winner][i]!=cromossomo[loser][i]) {
    		   if (cromossomo[winner][i]==1) {
    			   p[i]= p[i] + 1.0/tamanhoPop;
    		   }else {
    			   p[i]= p[i] - 1.0/tamanhoPop;
    		   }
    	   }
       }
 for ( int i = 0; i < cromossomo.length; i++) {
	 if ((p[i]>0) && (p[i]<1) ) {
		 convergencia = false;

	 }

 }

  }

for (int i = 0; i < tamanhoCromossomo; i++) {

	  System.out.print(""+ cromossomo[winner][i]);

}

System.out.println("Vetor de probabilidade:");
for (int i = 0; i < tamanhoCromossomo; i++) {

	  System.out.print(" |"+ p[i]);

}


System.out.println("Resultado final : "+ fitness[winner]);

	}





	static double fitness(int[]cromossomo) {

		double fitness=0;
		int x = listaInteiros(cromossomo);
		double y = 	getDouble(x);

		fitness = Math.pow(y,2);



		return fitness;
	}


















	static int[] competition (double[] fitness) {
		int[] resultado = new int[2];

        if (fitness[0]<fitness[1]) {
        	resultado[0]=0;
        	resultado[1]=1;



        }
        if (fitness[1]<fitness[0]) {
        	resultado[0]=1;
        	resultado[1]=0;




        }else {
        	if (fitness[0]==fitness[1]) {
        		resultado[0]=1;
            	resultado[1]=0;
        	}
        }
        return resultado;

	}









	static double[] inicializeProbability(double[] p) {
		for (int i =0; i< p.length; i++) {
			p[i]=0.5;
		}
		return p;
	}


	static int[] gerarIndividuo ( double[]p) {
		int[] ind = new int[tamanhoCromossomo];
		int[]indaux = new int [tamanhoPop];

		for (int i = 0; i< ind.length; i++) {

			int qt1 = (int) ((p[i]*100)*tamanhoPop/100);
			//System.out.println("probabilidade :" +qt1);

			if (p[i]>=1) {
				ind[i]=1;
			}else {
				if (p[i]<=0) {
					ind[i]=0;
				}else {
					indaux = gerarIndAux(qt1,indaux);
					 Random rand = new Random();

				        ind[i]= indaux[ rand.nextInt(indaux.length)];
				}
			}



		}
	return ind;
	}

	static int[] gerarIndAux (int qt1, int[] indaux) {
		for (int i = 0; i<indaux.length; i++) {
			if (i<qt1) {
				indaux[i]=1;
			}else {
				indaux[i]=0;
			}

		}

	return indaux;
	}













	static double calculaTamanho(int limS, int limI){
        int tamanho;
        int  tamanhoBit=0;
        tamanho  = (limS - limI);
        //calculando potencias de 2 para encontrar quantos bits precisam ser usados para
        //representar o intervalo
            for (int i = 0; ((Math.pow(10, precisao)) * tamanho) > Math.pow(2, i) ; ++i ){
                tamanhoBit = i;
            }
            System.out.print("Tamanho dos cromossomos:'"+tamanhoBit+1+"'");
        return (tamanhoBit+1);
    }


	  static int listaInteiros (int[] cromossomo){

		     //System.out.println("calculando inteiros:");
		    // int [][] fitnessList = new int[tamanhoPop][2];
		     int Inteiro =0;


		         for (int j = (tamanhoCromossomo-1); j>(-1);j--){
		                 if (cromossomo[j] ==1){
		                    Inteiro+=Math.pow(2,j) ;
		                 }


		     }



		    return Inteiro;
		  }

	  static double getDouble ( int Inteiro){
	        double legendaReal =0 ;

	        //for (int i =0 ; i<tamanhoPop; i++){
	            legendaReal =  + limInf + Inteiro*(limSup-limInf)/(Math.pow(2,tamanhoCromossomo)-1);


	            //System.out.println("Correspondente:"+legendaReal[i]);


	    return legendaReal;
	    }

}
