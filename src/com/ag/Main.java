package com.ag;

import java.util.Random;

public class Main {


	   static int limSup = 600;
	   static int limInf = -600;
	   static int tamanhoPop = 1000;
	   static int precisao =1;
	   static int geracoes = 500;
	   static int percentualDeMultacao = 5; //%
	   static int tamanhoCromossomo;
	   static int tamtorneio = 15;
	   static  Population populacao;
	   static int otimo = 0;



	public static void main(String[] args) {
		int[][] IntegerList;
		double[] fitnessList = new double [tamanhoPop];
		double fitnessMedio = 0;

		tamanhoCromossomo= (int)calculaTamanho(limSup, limInf);

		populacao = new Population( tamanhoCromossomo, tamanhoPop);

		populacao.gerarIndividuos(limSup, limInf);



		for (int g = 0; g < geracoes; g++) {
			System.out.println("Geração:" + g);
			System.out.println("solving integer");

		IntegerList = listaInteiros(populacao);


		System.out.println("solving double");
		double[][] doubleList =  getDouble(IntegerList);


		System.out.println("solving Fitness");
		fitnessList = fitness(doubleList);

		 fitnessMedio =0;
	        if (g ==0){
	        	for (int i=0;i<tamanhoPop;i++  ){
	        		// System.out.println(i+"=("+fitnessList[i]+")");
	        		fitnessMedio+=fitnessList[i];
	        	}
	        	System.out.println("FITNESS MEDIO =============("+fitnessMedio/fitnessList.length+")");

	        }

	        	System.out.println("Selecting ");

	        	int [] selecionados = select(fitnessList);

	        	System.out.println("doing crossover");
	        	populacao = cruzamento(populacao, fitnessList);

	        	System.out.println("mutating");
	        	populacao = mutacao(populacao);


		}



		fitnessMedio =0;
        for (int i=0;i<tamanhoPop;i++  ){
          // System.out.println(i+"=("+fitnessList[i]+")");
            fitnessMedio+=fitnessList[i];
            if (fitnessList[i]<fitnessList[otimo] ){
               otimo = i;
            }

        }
         System.out.println("FITNESS MEDIO ª Populacao final =============("+fitnessMedio/tamanhoPop+")");

        System.out.println("Fitness MELHOR INDIVIDUO("+fitnessList[otimo]);





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
	            System.out.print("Tamanho dos cromossomos:'"+tamanhoBit*2+"'");
	        return (tamanhoBit*2);
	    }

	  static int[][] listaInteiros (Population pop){

		     //System.out.println("calculando inteiros:");
		     int [][] fitnessList = new int[tamanhoPop][2];
		     int [][] listaInteiros = new int[tamanhoPop][2];

		     for (int i=0; i<tamanhoPop; i++){
		         for (int j = (tamanhoCromossomo-1); j>(tamanhoCromossomo/2);j--){
		                 if (pop.cromossomos[i][j] ==1){
		                    listaInteiros[i][0]+=Math.pow(2,j) ;
		                 }
		            }
		         //System.out.println(listaInteiros[i]);
		     }

		      for (int i=0; i<tamanhoPop; i++){
		         for (int j = (tamanhoCromossomo/2); j>=0;j--){
		                 if (pop.cromossomos[i][j] ==1){
		                    listaInteiros[i][1]+=Math.pow(2,j) ;
		                 }
		            }
		         //System.out.println(listaInteiros[i]);
		     }
		    return listaInteiros;
		  }


	  static double[][] getDouble ( int[][] listaInteiros){
	        double[][] legendaReal = new double[tamanhoPop][2];

	        for (int i =0 ; i<tamanhoPop; i++){
	            legendaReal[i][0] =  + limInf + listaInteiros[i][0]*(limSup-limInf)/(Math.pow(2,tamanhoCromossomo)-1);


	            //System.out.println("Correspondente:"+legendaReal[i]);
	        }
	        for (int i =0 ; i<tamanhoPop; i++){
	            legendaReal[i][1] =  + limInf + listaInteiros[i][1]*(limSup-limInf)/(Math.pow(2,tamanhoCromossomo)-1);


	            //System.out.println("Correspondente:"+legendaReal[i]);
	        }

	    return legendaReal;
	    }


	  static double[] fitness ( double[][] doubleList  ){
	       double[] fitnessList = new double[tamanhoPop];
	            for (int i = 0; i< tamanhoPop; i++){

	                           double sum = 0.0;
	                           double mult = 1.0;
	                           double d = 4000.0;

	                           for (int var = 0; var < 2; var++) {
	                             sum += doubleList[i][var] * doubleList[i][var];
	                             mult *= Math.cos(doubleList[i][var] / Math.sqrt(var + 1));
	                           }

	                           fitnessList[i]=  1+ (sum / d) - mult;
	                        		  // (1.0 / d * sum - mult + 1.0);
	                     }



	   return fitnessList;
	   }
	  static int[] select (double [] fitnessList) {


		  int [] selecionados = new int[tamtorneio];
		  int [] Parents = new int[2];

		  		for (int i = 0; i<tamtorneio; i++) {
		  			Random rand = new Random ();
		  			selecionados[i] = rand.nextInt(tamanhoPop);
		  			if (i==0) {
			  				Parents[0] = selecionados[i];
			  				Parents[1] = selecionados[i+1];
		  			}else {

		  			  if (fitnessList[Parents[0]] < fitnessList[selecionados[i]]){
			  				Parents[1] = Parents[0];
			  				Parents[0]= selecionados[i];

                       }

		  			}

		  		}


		  return Parents;
	  }




	  static Population cruzamento ( Population pop, double [] fitnessList ){
          Population novaPopulacao  = new Population(tamanhoCromossomo, tamanhoPop);

          			for ( int i=0; i<tamanhoPop; i++) {
          				int[] Parents =  select ( fitnessList);

          				novaPopulacao.cromossomos[i] = reproduce (pop.cromossomos[Parents[0]], pop.cromossomos[Parents[0]]);
          			}



			return novaPopulacao;
			}


	  static public Integer[] reproduce(Integer[] father, Integer[] mother) {
	        Integer[] child=new Integer[father.length];
	        int crossPoint = (father.length/4); //(int) (Math.random()*father.length);//make a crossover point


	            for (int i=0;i<crossPoint;++i){
	                           child[i]=mother[i];

	            }
	            for (int i =crossPoint; i<(crossPoint*2);i++ ){
	                    child[i]=father[i];

	            }
	            for(int i=(crossPoint*2); i<(crossPoint*3); i++){
	                    child[i]=mother[i];

	            }
	            for(int i=(crossPoint*3); i<father.length; i++){
	                    child[i]=father[i];

	            }


	     return child;
	    }

	  static Population mutacao ( Population pop) {
		  int percentualMutacao = (int) (tamanhoPop*percentualDeMultacao)/100;
	       // individuosMutados = new int[percentualMutacao];
	        double auxmut =  Math.round( tamanhoCromossomo*percentualDeMultacao);
	            int auxrandom;


	             for( int i =0;i<tamanhoPop;i++){
	                //  numeros aleatorios apatir do tamanho do cromossomo
	                 auxrandom = (int)Math.floor(Math.random()*tamanhoCromossomo);

	                    for(int j=0;j<auxmut;j++){
	                      if(pop.cromossomos[i][auxrandom]==0)
	                          pop.cromossomos[i][auxrandom]=1;
	                      else
	                          pop.cromossomos[i][auxrandom]=0;



	        }

	     }

//	            for (int i =0; i<percentualMutacao; i++){
//	                System.out.print(i+":");
//	                for (int j =0; j<tamanhoCromossomo; j++){
//	                     System.out.print( pop.cromossomos[individuosMutados[i]][j]);
//	                }
//	              System.out.println();
//	            }

	     return pop;
	     }

}
