package spring.reactive.market.transaction;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * MongoDB document of transactions
 */
@Document
public class Transaction {
    @Id
    private String id;

    // TODO: ensure type-specific `TransactionType`
    private String type;

    private Double value;

    /**
     * "Naive" implementation of relation between R2DBC's `Trader` and MongoDB's `Transaction`
     */
    private Integer trader;

    public Transaction() {
        super();
    }

    public Transaction(String id, String type, Double value, Integer trader) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.trader = trader;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getTrader() {
        return trader;
    }

    public void setTrader(Integer trader) {
        this.trader = trader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getTrader(), that.getTrader());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getValue(), getTrader());
    }
}
