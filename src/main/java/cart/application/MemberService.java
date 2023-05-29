package cart.application;

import cart.dao.MemberDao;
import cart.dto.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getAllMembers() {
        return memberDao.getAllMembers().stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}
