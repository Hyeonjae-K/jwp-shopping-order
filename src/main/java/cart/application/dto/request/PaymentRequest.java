package cart.application.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public class PaymentRequest {

    @NotEmpty(message = "장바구니가 비어있습니다.")
    private final List<PayItemRequest> cartItemIds;

    @PositiveOrZero(message = "포인트는 0원 이상 사용 가능합니다.")
    private final Integer points;

    public PaymentRequest(final List<PayItemRequest> cartItemIds, final Integer point) {
        this.cartItemIds = cartItemIds;
        this.points = point;
    }

    public List<PayItemRequest> getCartItemIds() {
        return cartItemIds;
    }

    public Integer getPoints() {
        return points;
    }
}
