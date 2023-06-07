package cart.application.dto.response;

public class PointResponse {

    private final Integer points;

    public PointResponse(final Integer point) {
        this.points = point;
    }

    public Integer getPoints() {
        return points;
    }
}
