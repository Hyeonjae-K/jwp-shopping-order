package cart.application.dto.response;

public class OrderIdResponse {

    private final Long orderId;

    public OrderIdResponse(final Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
