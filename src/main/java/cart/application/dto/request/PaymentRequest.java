package cart.application.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public class PaymentRequest {

    @NotEmpty(message = "장바구니가 비어있습니다.")
    private final List<PayItemRequest> cartItemIds;

    @NotNull
    @PositiveOrZero(message = "가격은 음수일 수 없습니다.")
    private final Integer originalPrice;

    @PositiveOrZero(message = "포인트는 0원 이상 사용 가능합니다.")
    private final Integer points;

    public PaymentRequest(final List<PayItemRequest> cartItemIds, final Integer originalPrice, final Integer points) {
        this.cartItemIds = cartItemIds;
        this.originalPrice = originalPrice;
        this.points = points;
    }

    public List<PayItemRequest> getCartItemIds() {
        return cartItemIds;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public Integer getPoints() {
        return points;
    }
}
