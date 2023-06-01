package cart.persistence.repository;

import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import cart.persistence.dao.CartItemDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.CartItemEntity;
import cart.persistence.entity.MemberEntity;
import cart.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cart.persistence.repository.Mapper.*;

@Component
public class CartItemRepository {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public CartItemRepository(final CartItemDao cartItemDao, final ProductDao productDao, final MemberDao memberDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    public Product getProductById(final Long productId) {
        final ProductEntity productEntity = productDao.getProductById(productId);
        return productMapper(productEntity);
    }

    public List<CartItem> findCartItemsByMemberId(final Long memberId) {
        final MemberEntity memberEntity = memberDao.getMemberById(memberId);
        final List<CartItemEntity> cartItemEntities = cartItemDao.findByMemberId(memberId);
        final List<ProductEntity> productEntities = getProductEntitiesFromCartItemEntities(cartItemEntities);

        final List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < cartItemEntities.size(); i++) {
            final CartItemEntity cartItemEntity = cartItemEntities.get(i);
            final ProductEntity productEntity = productEntities.get(i);
            cartItems.add(Mapper.cartItemMapper(cartItemEntity, memberEntity, productEntity));
        }
        return cartItems;
    }

    private List<ProductEntity> getProductEntitiesFromCartItemEntities(final List<CartItemEntity> cartItemEntities) {
        final List<Long> productIds = cartItemEntities.stream()
                .map(CartItemEntity::getProductId)
                .collect(Collectors.toList());
        return productDao.getProductsByIds(productIds);
    }

    public Long saveCartItem(final CartItem cartItem) {
        final CartItemEntity cartItemEntity = Mapper.cartItemEntityMapper(cartItem);
        return cartItemDao.save(cartItemEntity);
    }

    public CartItem findCartItemById(final Long id) {
        final CartItemEntity cartItemEntity = cartItemDao.findById(id);
        final MemberEntity memberEntity = memberDao.getMemberById(cartItemEntity.getMemberId());
        final ProductEntity productEntity = productDao.getProductById(cartItemEntity.getProductId());
        return cartItemMapper(cartItemEntity, memberEntity, productEntity);
    }

    public void deleteCartItemById(final Long id) {
        cartItemDao.deleteById(id);
    }

    public void updateCartItemQuantity(final CartItem cartItem) {
        final CartItemEntity cartItemEntity = cartItemEntityMapper(cartItem);
        cartItemDao.updateQuantity(cartItemEntity);
    }
}
