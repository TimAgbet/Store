package domain;

import java.util.List;

public class Result {
    private List<Product> results;
    private String total;

    public List<Product> getResults() {
        return results;
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
