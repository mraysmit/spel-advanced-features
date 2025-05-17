package model;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a trade with client, instrument, and other trade-related information.
 */
public class Trade {
    private final UUID uuid;
    private String id;
    private String value;
    private String category;

    // New attributes
    private Customer client;
    private Product instrument;
    private int quantity;
    private double price;
    private Date tradeDate;
    private String status; // PENDING, EXECUTED, SETTLED, CANCELLED, FAILED

    /**
     * Basic constructor for backward compatibility.
     * 
     * @param id The trade ID
     * @param value The trade value
     * @param category The trade category
     */
    public Trade(String id, String value, String category) {
        this.uuid = UUID.randomUUID();
        this.id = id;
        this.value = value;
        this.category = category;
    }

    /**
     * Full constructor with all attributes.
     * 
     * @param id The trade ID
     * @param value The trade value
     * @param category The trade category
     * @param client The client involved in the trade
     * @param instrument The financial instrument being traded
     * @param quantity The quantity of the instrument
     * @param price The price per unit of the instrument
     * @param tradeDate The date of the trade
     * @param status The status of the trade
     */
    public Trade(String id, String value, String category, Customer client, Product instrument, 
                int quantity, double price, Date tradeDate, String status) {
        this.uuid = UUID.randomUUID();
        this.id = id;
        this.value = value;
        this.category = category;
        this.client = client;
        this.instrument = instrument;
        this.quantity = quantity;
        this.price = price;
        this.tradeDate = tradeDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Customer getClient() {
        return client;
    }

    public Product getInstrument() {
        return instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Calculate the total value of the trade.
     * 
     * @return The total value (price * quantity)
     */
    public double calculateTotalValue() {
        return price * quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trade{id='").append(id).append("', value='").append(value)
          .append("', category='").append(category).append("'");

        // Add new attributes to toString if they are set
        if (client != null) {
            sb.append(", client='").append(client.getName()).append("'");
        }
        if (instrument != null) {
            sb.append(", instrument='").append(instrument.getName()).append("'");
        }
        if (quantity > 0) {
            sb.append(", quantity=").append(quantity);
        }
        if (price > 0) {
            sb.append(", price=").append(price);
        }
        if (tradeDate != null) {
            sb.append(", tradeDate='").append(tradeDate).append("'");
        }
        if (status != null) {
            sb.append(", status='").append(status).append("'");
        }

        sb.append("}");
        return sb.toString();
    }
}
