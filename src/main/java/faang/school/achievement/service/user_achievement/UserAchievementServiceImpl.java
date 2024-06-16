package faang.school.achievement.service.user_achievement;

import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAchievementServiceImpl implements UserAchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper userAchievementMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserAchievementDto> getAchievementsByUserId(long userId) {
        return userAchievementRepository.findByUserId(userId).stream()
                .map(userAchievementMapper::toDto)
                .toList();
    }
}
