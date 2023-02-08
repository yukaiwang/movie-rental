package movierental;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        String result = "Rental Record for " + getName() + "\n";

        for (Rental each : rentals) {
            // show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" + getAmountFor(each) + "\n";
        }

        // add footer lines
        result += "Amount owed is " + getTotalAmount() + "\n";
        result += "You earned " + getTotalFrequentRenterPoints() + " frequent renter points";

        return result;
    }

    private int getTotalFrequentRenterPoints() {
        return rentals.stream().mapToInt(Rental::calculateFrequentRenterPoints).sum();
    }

    private double getTotalAmount() {
        return rentals.stream().mapToDouble(Customer::getAmountFor).sum();
    }

    private static double getAmountFor(Rental rental) {
        double amount = 0;

        //determine amounts for rental line
        return switch (rental.getMovie().getType()) {
            case REGULAR -> {
                amount += 2;
                if (rental.getDaysRented() > 2)
                    amount += (rental.getDaysRented() - 2) * 1.5;
                yield amount;
            }
            case NEW_RELEASE -> {

                amount += rental.getDaysRented() * 3;
                yield amount;
            }
            case CHILDRENS -> {
                amount += 1.5;
                if (rental.getDaysRented() > 3)
                    amount += (rental.getDaysRented() - 3) * 1.5;
             yield amount;
            }

        };
    }
}
