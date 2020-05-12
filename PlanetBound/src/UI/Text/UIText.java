package UI.Text;

import Logic.StateChanger;
import Logic.States.InOrbit;
import Logic.States.VisitSS;

import java.util.Scanner;

public class UIText {
    StateChanger machine;
    boolean shutdown = false;
    Scanner kb;

    public UIText(StateChanger machine) {
        this.machine = machine;
        kb = new Scanner(System.in);
    }

    public void start() {
        machine.start();
        showShipOptions();
    }

    private void showShipOptions() {
        System.out.println("Select your ship:");
        System.out.println("1 - Mining Ship");
        System.out.println("2 - Military Ship");
        System.out.print("Option: ");
        int x;
        do{
            x = kb.nextInt();
        }while(x < 1 || x > 2);
        machine.selectShip(x);
        listOptions();
    }

    public void listOptions() {
        machine.showGameState();
        System.out.println("Options:");
        if(machine.getCurrentState() instanceof InOrbit) {
            System.out.println("1 - Go to Planet and Explore it");
            System.out.println("2 - Go to Next Sector");
            if(machine.getData().hasSS()) {
                System.out.println("3 - Go to Space Station");
            }
            System.out.print("Your choice: ");
            switch (kb.nextInt()) {
                case 1 -> machine.landOnPlanet();
                case 2 -> machine.nextTurn();
                case 3 -> machine.visitSS();
                default -> System.out.println("Invalid Option!");
            }
        }
        else if(machine.getCurrentState() instanceof VisitSS) {
            if(((VisitSS) machine.getCurrentState()).enoughResourcesForAmmo())
                System.out.println("1 - Make Ammo");
            if(((VisitSS) machine.getCurrentState()).enoughResourcesForFuel())
                System.out.println("2 - Make Fuel cells");
            if(((VisitSS) machine.getCurrentState()).enoughResourcesForShield())
                System.out.println("3 - Make Shield cells");
            System.out.print("Your choice: ");
            switch (kb.nextInt()) {
                case 1 -> ((VisitSS) machine.getCurrentState()).convertToAmmo();
                case 2 -> ((VisitSS) machine.getCurrentState()).convertToFuel();
                case 3 -> ((VisitSS) machine.getCurrentState()).convertToShield();
                default -> System.out.println("Invalid Option!");
            }
        }
    }
}