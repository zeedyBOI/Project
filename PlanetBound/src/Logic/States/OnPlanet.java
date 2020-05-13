package Logic.States;

import Logic.Data.GameData;

import java.util.Scanner;

public class OnPlanet extends Adapter {
    public OnPlanet(GameData data) {
        super(data);
    }

    public void startExploration() {
        getData().cleanSpots();
        getData().spawnResource();
        getData().setTerrainSpots();
        getData().spawnAlien();
        System.out.println(getData().printTerrain());
        scanMine();
        System.out.println(getData().addResourceToShip(getData().getResourceMined()));
        checkForArmorRefill();
        getData().emptyDrone();
    }

    private void checkForArmorRefill() {
        System.out.println("Refill drone armor? (Y/N)");
        Scanner kb = new Scanner(System.in);
        switch (kb.next().toUpperCase().charAt(0)) {
            case 'Y':
                getData().refillDroneArmor();
                break;
            case 'N':
                break;
        }
    }

    private void scanMine() {
        boolean moved;
        Scanner kb = new Scanner(System.in);
        do {
            do {
                moved = getData().move(kb.next().toUpperCase().charAt(0), 'D');
            } while (!moved);
            System.out.println(getData().printTerrain());
            if(getData().isAlienNextToDrone()) {
                getData().fight();
                getData().spawnNewAlien();
            }
            getData().moveAlien();
            System.out.println(getData().printTerrain());
            if(getData().isAlienNextToDrone()) {
                getData().fight();
                getData().spawnNewAlien();
            }
            if (getData().droneAtResource()) {
                getData().putResourceInDrone(getData().getResourceOnTerrain());
                getData().setDroneReadyToLeave();
            }
            if (getData().droneAtSpawn())
                if(getData().isDroneReadyToReturnToShip())
                    return;
        }while(true);
    }

    @Override
    public IState returnToOrbit() {
        return new InOrbit(getData());
    }


}
