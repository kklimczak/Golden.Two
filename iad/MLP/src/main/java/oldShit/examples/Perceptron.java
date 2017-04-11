package oldShit.examples;

import static java.lang.Math.exp;
import static java.lang.Math.round;

public class Perceptron {
    private final static int MAX_ITER = 1000;
    private final static double LEARNING_RATE = 0.001;
    private final static int NUM_INSTANCES = 100;

    public static void run(){
        //three variables (features)
        double[] x = new double[NUM_INSTANCES];
        double[] y = new double[NUM_INSTANCES];
        int[] outputs = new int[NUM_INSTANCES];

        //fifty random points of class 1
        for(int i=0;i<NUM_INSTANCES/2;++i){
            x[i] = randomNumber(-1, 0);
            y[i] = randomNumber(-1, 0);
            outputs[i] = 1;
            System.out.println(x[i]+"\t"+y[i]+"\t"+outputs[i]);
        }

        //fifty random points of class 0
        for(int i=50;i<NUM_INSTANCES;i++){
            x[i] = randomNumber(0.01, 1);
            y[i] = randomNumber(0.01, 1);
            outputs[i] = 0;
            System.out.println(x[i]+"\t"+y[i]+"\t"+outputs[i]);
        }

        double[] weights = new double[3]; //2 for input variables and one for bias
        double localError, globalError, output;
        int p, iteration;

        weights[0] = randomNumber(0, 1);
        weights[1] = randomNumber(0, 1);
        weights[2] = randomNumber(0, 1);


        iteration = 0;
        do{
            iteration++;
            globalError = 0;
            //loop through all instances (complete one epoch)
            for(p = 0;p<NUM_INSTANCES;p++){
                //calculate predicted class
                output = calculateOutput(weights, x[p], y[p]);
                //difference between predicted and actual class values
                localError = outputs[p] - output;
                //update weights
                weights[0] += LEARNING_RATE*localError*x[p];
                weights[1] += LEARNING_RATE*localError*y[p];

                //update bias
                weights[2] += LEARNING_RATE*localError;

                globalError += (localError*localError);
            }
            /*Root Mean Squared Error*/
            System.out.println("Iteration "+iteration+" : RMSE = "+Math.sqrt(globalError/NUM_INSTANCES));
        }while(globalError != 0 && iteration < MAX_ITER);

        System.out.println("\n========\nDecision boundary equation:");
        System.out.println(weights[0]+"*x "+weights[1]+"*y + "+weights[2]+" = 0");


        //generate 10 new random pointns and check their classes
        //notice the range of -1 and 1 means the new point could be of class 1 or 0
        //-10 to 10 covers all the ranges we used in generating the 50 classes of 1s and 0s

        for(int j=0;j<100;++j){
            double x1 = randomNumber(-1, 1);
            double y1 = randomNumber(-1, 1);

            output = calculateOutput(weights, x1, y1);
            output = round(output);
            System.out.println("\n=======\nNew Random Point:");
            System.out.println("x = "+x1+",y = "+y1);
            System.out.println("class = "+output);
        }

        double avg1 = 0, avg2 = 0;
        for(int j=0;j<10;++j){

            int mis=0;

            //P[f(x)!=g(x) for N = 10
            //fifty random points of class 1
            for(int i=0;i<5;++i){
                double x1 = randomNumber(-1, 0);
                double y1 = randomNumber(-1, 0);
                output = calculateOutput(weights, x1, y1);
                output = round(output);
                if(output != 1)mis++;
            }

            //fifty random points of class 0
            for(int i=0;i<5;i++){
                double x1 = randomNumber(0.01, 1);
                double y1 = randomNumber(0.01, 1);
                output = calculateOutput(weights, x1, y1);
                output = round(output);
                if(output != 0)mis++;
            }
            avg1+=(double)mis/10;
//            System.out.println("P[f(x)!=g(x) (N=10) = "+((double)mis/10));


            mis = 0;
            //P[f(x)!=g(x) for N = 100
            //fifty random points of class 1
            for(int i=0;i<50;++i){
                double x1 = randomNumber(-1, 0);
                double y1 = randomNumber(-1, 0);
                output = calculateOutput(weights, x1, y1);
                output = round(output);
                if(output != 1)mis++;
            }

            //fifty random points of class 0
            for(int i=0;i<50;i++){
                double x1 = randomNumber(0.1, 1);
                double y1 = randomNumber(0.1, 1);
                output = calculateOutput(weights, x1, y1);
                output = round(output);
                if(output != 0)mis++;
            }

            avg2+=(double)mis/100;
        }
        System.out.println("P[f(x)!=g(x) (N=10) over 10*10 samples = "+avg1/10);
        System.out.println("P[f(x)!=g(x) (N=100) over 10*100 samples = "+avg2/10);
    }

    /**
     * returns a random double value within a given range
     * @param min the minimum value of the required range(int)
     * @param max the maximum value of the required range(int)
     * @return a random double value between min and max
     */
    private static double randomNumber(double min, double max){
        return min+Math.random()*(max-min);
    }

    /**
     * returns value of a SIGMOID activation function
     * @param weights the array of weights
     * @param x the x input value
     * @param y the y input value
     * @return double value
     */
    private static double calculateOutput(double weights[], double x, double y){
        double sum  = x*weights[0] + y*weights[1] + weights[2];
        return 1.0/(1.0+exp(-sum));
    }
}