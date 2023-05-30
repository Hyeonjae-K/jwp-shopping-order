package cart.ui;

import cart.application.CartItemService;
import cart.domain.member.Member;
import cart.dto.request.CartItemQuantityUpdateRequest;
import cart.dto.request.CartItemRequest;
import cart.dto.request.PaymentRequest;
import cart.dto.response.CartItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemApiController {

    private final CartItemService cartItemService;

    public CartItemApiController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> showCartItems(final Member member) {
        return ResponseEntity.ok(cartItemService.findByMember(member));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItems(final Member member, @RequestBody final CartItemRequest cartItemRequest) {
        final Long cartItemId = cartItemService.add(member, cartItemRequest);

        return ResponseEntity.created(URI.create("/cart-items/" + cartItemId)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCartItemQuantity(final Member member, @PathVariable final Long id, @RequestBody final CartItemQuantityUpdateRequest request) {
        cartItemService.updateQuantity(member, id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItems(final Member member, @PathVariable final Long id) {
        cartItemService.remove(member, id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/payment")
    public ResponseEntity<String> paymentCartItems(final Member member, @Valid @RequestBody final PaymentRequest request) {
        final Long historyId = cartItemService.payment(member, request);

        return ResponseEntity.created(URI.create("redirect:/orders/histories/" + historyId)).build();
    }
}
