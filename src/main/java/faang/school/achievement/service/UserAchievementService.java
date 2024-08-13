package faang.school.achievement.service;

import faang.school.achievement.dto.UserAchievementDto;
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
public class UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper userAchievementMapper;

    @Transactional(readOnly = true)
    public List<UserAchievementDto> getAchievementFinishedForUserById(long userId) {
        log.info("The user received all his achievements by id: {}", userId);
        return userAchievementRepository.findByUserId(userId).stream()
                .map(userAchievementMapper::toDto)
                .toList();
    }
}
