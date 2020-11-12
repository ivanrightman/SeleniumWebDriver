package selenium.model;

public class Product {
    private String size;

    public String getSize() {
        return size;
    }

    public class Builder {
        private Builder() {}
        public Builder withSize(String size) {
            Product.this.size = size;
            return this;
        }
    }
}
