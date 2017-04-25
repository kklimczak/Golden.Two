class Application {
    public static void main(String[] args) {
        /*
            1: 4-1-4 + bias
            2: 4-2-4 + bias
            3: 4-3-4 + bias
            4: 4-1-4
            5: 4-2-4
            6: 4-3-4
         */

        int networkNumb = 3;

        double learningRate = 0.5;
        double momentum = 0.5;

        Transformation t = new Transformation(networkNumb, learningRate, momentum);
        t.train();
    }
}