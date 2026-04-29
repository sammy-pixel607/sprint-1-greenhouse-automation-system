import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== GREENHOUSE SIMULATION STARTED ===");

        GreenhouseController controller = new GreenhouseController();

        while (true) {

            System.out.println("\nEnter Environmental Values:");

            double temp = getValidInput(sc, "Temperature (°C): ");
            double soil = getValidInput(sc, "Soil Moisture (%): ");
            double light = getValidInput(sc, "Light Intensity (lux): ");
            double humidity = getValidInput(sc, "Humidity (%): ");
            double co2 = getValidInput(sc, "CO2 Level (ppm): ");

            Environment env = new Environment(temp, soil, light, humidity, co2);

            controller.regulate(env);

            System.out.print("\nContinue simulation? (yes/no): ");
            String choice = sc.next();

            if (!choice.equalsIgnoreCase("yes")) {
                System.out.println("Simulation Ended.");
                break;
            }
        }

        sc.close();
    }

    // ✅ Input validation
    public static double getValidInput(Scanner sc, String prompt) {
        double value;

        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Value must be positive!");
                }
            } else {
                System.out.println("Invalid input! Enter a number.");
                sc.next(); // clear invalid input
            }
        }
    }
}

// 🌍 Environment
class Environment {
    double temperature;
    double soilMoisture;
    double lightIntensity;
    double humidity;
    double co2Level;

    public Environment(double temperature, double soilMoisture,
                       double lightIntensity, double humidity, double co2Level) {

        this.temperature = temperature;
        this.soilMoisture = soilMoisture;
        this.lightIntensity = lightIntensity;
        this.humidity = humidity;
        this.co2Level = co2Level;
    }
}

// ⚙️ Config
class Config {

    static final double MAX_TEMPERATURE = 28.0;
    static final double MIN_TEMPERATURE = 18.0;

    static final double MIN_SOIL_MOISTURE = 35.0;

    static final double MIN_LIGHT = 500.0;
    static final double MAX_LIGHT = 2000.0;

    static final double MIN_HUMIDITY = 40.0;
    static final double MAX_HUMIDITY = 70.0;

    static final double MIN_CO2 = 400.0;
}

// 🧠 Controller
class GreenhouseController {

    private Fan fan = new Fan();
    private Heater heater = new Heater();
    private Irrigation irrigation = new Irrigation();
    private GrowLight light = new GrowLight();
    private Ventilation vent = new Ventilation();
    private MistSystem mist = new MistSystem();
    private CO2Injector co2Injector = new CO2Injector();
    private ShadeSystem shade = new ShadeSystem();

    public void regulate(Environment env) {

        System.out.println("\n=== SYSTEM RESPONSE ===");

        controlTemperature(env);
        controlSoilMoisture(env);
        controlLight(env);
        controlHumidity(env);
        controlCO2(env);
    }

    private void controlTemperature(Environment env) {
        System.out.println("\n[Temperature]");

        if (env.temperature > Config.MAX_TEMPERATURE) {
            fan.on();
            heater.off();
        } else if (env.temperature < Config.MIN_TEMPERATURE) {
            heater.on();
            fan.off();
        } else {
            fan.off();
            heater.off();
        }
    }

    private void controlSoilMoisture(Environment env) {
        System.out.println("\n[Soil Moisture]");

        if (env.soilMoisture < Config.MIN_SOIL_MOISTURE) {
            irrigation.on();
        } else {
            irrigation.off();
        }
    }

    private void controlLight(Environment env) {
        System.out.println("\n[Light]");

        if (env.lightIntensity < Config.MIN_LIGHT) {
            light.on();
            shade.off();
        } else if (env.lightIntensity > Config.MAX_LIGHT) {
            shade.on();
            light.off();
        } else {
            light.off();
            shade.off();
        }
    }

    private void controlHumidity(Environment env) {
        System.out.println("\n[Humidity]");

        if (env.humidity > Config.MAX_HUMIDITY) {
            vent.on();
            mist.off();
        } else if (env.humidity < Config.MIN_HUMIDITY) {
            mist.on();
            vent.off();
        } else {
            vent.off();
            mist.off();
        }
    }

    private void controlCO2(Environment env) {
        System.out.println("\n[CO2]");

        if (env.co2Level < Config.MIN_CO2) {
            co2Injector.on();
        } else {
            co2Injector.off();
        }
    }
}

// 🔧 Components

class Fan {
    void on() { System.out.println("Fan ON"); }
    void off() { System.out.println("Fan OFF"); }
}

class Heater {
    void on() { System.out.println("Heater ON"); }
    void off() { System.out.println("Heater OFF"); }
}

class Irrigation {
    void on() { System.out.println("Irrigation ON"); }
    void off() { System.out.println("Irrigation OFF"); }
}

class GrowLight {
    void on() { System.out.println("Grow Lights ON"); }
    void off() { System.out.println("Grow Lights OFF"); }
}

class Ventilation {
    void on() { System.out.println("Ventilation ON"); }
    void off() { System.out.println("Ventilation OFF"); }
}

class MistSystem {
    void on() { System.out.println("Mist System ON"); }
    void off() { System.out.println("Mist System OFF"); }
}

class CO2Injector {
    void on() { System.out.println("CO2 Injector ON"); }
    void off() { System.out.println("CO2 Injector OFF"); }
}

class ShadeSystem {
    void on() { System.out.println("Shade System ON"); }
    void off() { System.out.println("Shade System OFF"); }
}