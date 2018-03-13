package github.dyukusi;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Started!");
        Region targetRegion = Region.getById(Integer.parseInt(args[0]));
        new Clawler().exec(targetRegion);
        System.out.println("Finished!");
    }
}
