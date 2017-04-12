class Application {
    public static void main(String[] args) {

        int networkNumb = Integer.parseInt("3");
        if (networkNumb <= 0 || networkNumb > 6) {
            System.out.println("Wrong network number");
        } else {
            Transformation t = new Transformation(networkNumb);
            t.train();
        }
    }
}