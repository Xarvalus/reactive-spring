package spring.reactive.market.trader;

import org.springframework.data.annotation.Id;

import java.util.Objects;

/**
 * R2DBC PostgreSQL entity of traders
 */
public class Trader {
    @Id
    private Integer id;

    private String name;

    public Trader() {
        super();
    }

    public Trader(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trader trader = (Trader) o;
        return Objects.equals(getId(), trader.getId()) &&
                Objects.equals(getName(), trader.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
