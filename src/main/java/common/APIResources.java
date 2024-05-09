package common;

public enum APIResources {

    getCurrencyAPI("/v6/latest/USD");
    private String resource;

    APIResources(String resource) {
        this.resource=resource;
    }

    public String getResource() {
        return resource;
    }
}
