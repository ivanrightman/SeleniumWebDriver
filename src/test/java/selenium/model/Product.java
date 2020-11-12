package selenium.model;

public class Product {
    private String size;
    private int quantity;
    private String block;

    public static Builder newEntity() {
        return new Product().new Builder();
    }
    public String getSize() {
        return size;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getBlock() {
        return block;
    }

    public class Builder {
        private Builder() {}
        public Builder withSize(String size) {
            Product.this.size = size;
            return this;
        }
        public Builder withQuantity(int quantity) {
            Product.this.quantity = quantity;
            return this;
        }
        public Builder withBlock(String block) {
            Product.this.block = block;
            return this;
        }
        public Product build() {
            return Product.this;
        }

        public String getBlock() {
            return block;
        }

        public String getSize() {
            return block;
        }
    }
}
